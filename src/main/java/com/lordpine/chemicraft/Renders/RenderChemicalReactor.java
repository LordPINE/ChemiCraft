package com.lordpine.chemicraft.Renders;

import Reika.DragonAPI.Base.TileEntityBase;
import Reika.DragonAPI.Base.TileEntityRenderBase;
import Reika.RotaryCraft.Auxiliary.IORenderer;
import com.lordpine.chemicraft.ChemiCraft;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

@SideOnly(Side.CLIENT)
public class RenderChemicalReactor extends TileEntityRenderBase {

    private final ResourceLocation texture = new ResourceLocation("chemicraft", "textures/blocks/pvc_liquidFlowing.png");
    private IModelCustom model = AdvancedModelLoader.loadModel(new ResourceLocation(ChemiCraft.MODID, "obj/snowball.obj"));

    @Override
    protected boolean doRenderModel(TileEntityBase te) {
        return true;
    }

    @Override
    public String getTextureFolder() {
        return "/com/lordpine/chemicraft/Textures/Tile";
    }

    @Override
    protected Class getModClass() {
        return ChemiCraft.class;
    }

    @Override
    public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float par8) {
        //if (((TileEntityBase) tile).isInWorld() && MinecraftForgeClient.getRenderPass() == 1) {
            IORenderer.renderIO(tile, x, y, z);
        //}
    }
}
