package com.lordpine.chemicraft.GUI;

import Reika.DragonAPI.Instantiable.HybridTank;
import Reika.DragonAPI.Libraries.IO.ReikaColorAPI;
import Reika.DragonAPI.Libraries.IO.ReikaGuiAPI;
import Reika.DragonAPI.Libraries.IO.ReikaLiquidRenderer;
import Reika.DragonAPI.Libraries.IO.ReikaTextureHelper;
import Reika.RotaryCraft.RotaryCraft;
import com.lordpine.chemicraft.ChemiCraft;
import com.lordpine.chemicraft.TileEntities.TileEntityChemicalReactor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class GuiChemicalReactor extends GuiContainer{

    protected static final ReikaGuiAPI api = ReikaGuiAPI.instance;

    private ResourceLocation texture = new ResourceLocation(ChemiCraft.MODID, "textures/gui/machine/chemical_reactor2.png");

    private InventoryPlayer inventory;
    private TileEntityChemicalReactor te;
    private int correction = 52;

    public GuiChemicalReactor(TileEntityChemicalReactor te, EntityPlayer player)
    {
        super(new ChemicalReactorContainer(te, player));
        this.te = te;
        this.inventory = player.inventory;
        this.ySize = 218;
        this.xSize = 176;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
    {
        Minecraft.getMinecraft().renderEngine.bindTexture(texture);

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;

        drawTexturedModalRect(x, y-correction, 0, 0, xSize, ySize);
        drawPowerTab(x, y-correction);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2)
    {
        fontRendererObj.drawString(I18n.format(te.getInventoryName()), (xSize / 2) - (fontRendererObj.getStringWidth(I18n.format(te.getInventoryName())) / 2), 6-correction, 4210752, false);
        fontRendererObj.drawString(I18n.format(inventory.getInventoryName()), 8, ySize - 96 + 2-correction, 4210752);

        if (!te.getInput_tanks()[0].isEmpty()) {
            HybridTank tank = te.getInput_tanks()[0];
            renderLiquid(tank, 21, 58-correction);
        }
        if (!te.getInput_tanks()[1].isEmpty()) {
            HybridTank tank = te.getInput_tanks()[1];
            renderLiquid(tank, 139, 58-correction);
        }

        if (!te.getOutput_tank().isEmpty()) {
            HybridTank tank = te.getOutput_tank();
            renderLiquid(tank, 80, 130-correction);
        }

        int j = (width - xSize) / 2;
        int k = (height - ySize) / 2;

        if (api.isMouseInBox(j + 21, j + 37, k + 15 - correction, k + 58 - correction)) {
            int mx = api.getMouseRealX();
            int my = api.getMouseRealY();
            if (te.getInput_tanks()[0].getFluid() != null) {
                api.drawTooltipAt(fontRendererObj, String.format("%s, %d/%d", te.getInput_tanks()[0].getFluid().getLocalizedName(), te.getInput_tanks()[0].getLevel(), te.getInput_tanks()[0].getCapacity()), mx - j, my - k);
            } else {
                api.drawTooltipAt(fontRendererObj, String.format("%d/%d", te.getInput_tanks()[1].getLevel(), te.getInput_tanks()[1].getCapacity()), mx - j, my - k);
            }
        }
        if (api.isMouseInBox(j + 139, j + 155, k + 15 - correction, k + 58 - correction)) {
            int mx = api.getMouseRealX();
            int my = api.getMouseRealY();
            if (te.getInput_tanks()[1].getFluid() != null) {
                api.drawTooltipAt(fontRendererObj, String.format("%s, %d/%d", te.getInput_tanks()[1].getFluid().getLocalizedName(), te.getInput_tanks()[1].getLevel(), te.getInput_tanks()[1].getCapacity()), mx - j, my - k);
            } else {
                api.drawTooltipAt(fontRendererObj, String.format("%d/%d", te.getInput_tanks()[1].getLevel(), te.getInput_tanks()[1].getCapacity()), mx - j, my - k);

            }
        }

        if (api.isMouseInBox(j + 79, j + 95, k + 86 - correction, k + 129 - correction)) {
            int mx = api.getMouseRealX();
            int my = api.getMouseRealY();
            if (te.getOutput_tank().getFluid() != null) {
                api.drawTooltipAt(fontRendererObj, String.format("%s, %d/%d", te.getOutput_tank().getFluid().getLocalizedName(), te.getOutput_tank().getLevel(), te.getOutput_tank().getCapacity()), mx - j, my - k);
            } else {
                api.drawTooltipAt(fontRendererObj, String.format("%d/%d", te.getOutput_tank().getLevel(), te.getOutput_tank().getCapacity()), mx - j, my - k);
            }
        }
    }

    private void renderLiquid(HybridTank tank, int x, int y) {
        int i2 = getLiquidScaled(tank, 41);
        int x2 = x;
        int y2 = y-i2;
        IIcon ico = ReikaLiquidRenderer.getFluidIconSafe(tank.getActualFluid());
        ReikaLiquidRenderer.bindFluidTexture(tank.getActualFluid());
        int clr = 0xffffffff;
        if (tank.getActualFluid().canBePlacedInWorld()) {
            clr = tank.getActualFluid().getBlock().colorMultiplier(te.worldObj, te.xCoord*2, te.yCoord*2, te.zCoord*2);
        }
        GL11.glColor4f(ReikaColorAPI.HextoColorMultiplier(clr, 0), ReikaColorAPI.HextoColorMultiplier(clr, 1), ReikaColorAPI.HextoColorMultiplier(clr, 2), 1);
        this.drawTexturedModelRectFromIcon(x2, y2, ico, 16, i2);
    }

    private int getLiquidScaled(HybridTank tank, int scaling) {
        return tank.getLevel() * scaling / tank.getCapacity();
    }


    /**Stolen from Reika**/
    protected void drawPowerTab(int var5, int var6) {
        String var4 = "/Reika/RotaryCraft/Textures/GUI/powertab.png";
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        ReikaTextureHelper.bindTexture(RotaryCraft.class, var4);
        this.drawTexturedModalRect(xSize+var5, var6+4, 0, 4, 42, 162);

        long frac = (te.getPower()*29L)/te.MIN_POWER;
        if (frac > 29)
            frac = 29;
        this.drawTexturedModalRect(xSize+var5+5, ySize+var6-144-correction, 0, 0, (int)frac, 4);

        frac = (int)(te.getOmega()*29L)/te.MIN_SPEED;
        if (frac > 29)
            frac = 29;
        this.drawTexturedModalRect(xSize+var5+5, ySize+var6-84-correction, 0, 0, (int)frac, 4);

        frac = (int)(te.getTorque()*29L)/te.MIN_TORQUE;
        if (frac > 29)
            frac = 29;
        this.drawTexturedModalRect(xSize+var5+5, ySize+var6-24-correction, 0, 0, (int)frac, 4);

        api.drawCenteredStringNoShadow(fontRendererObj, "Power:", xSize+var5+20, var6+9, 0xff000000);
        api.drawCenteredStringNoShadow(fontRendererObj, "Speed:", xSize+var5+20, var6+69, 0xff000000);
        api.drawCenteredStringNoShadow(fontRendererObj, "Torque:", xSize+var5+20, var6+129, 0xff000000);
        //this.drawCenteredStringNoShadow(fontRendererObj, String.format("%d/%d", ferm.power, ferm.MINPOWER), xSize+var5+16, var6+16, 0xff000000);
    }

}
