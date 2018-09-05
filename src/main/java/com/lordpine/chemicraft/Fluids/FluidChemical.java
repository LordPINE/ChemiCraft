package com.lordpine.chemicraft.Fluids;

import com.lordpine.chemicraft.ChemiCraft;
import com.lordpine.chemicraft.Chemistry.Chemical;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraftforge.fluids.Fluid;

public class FluidChemical extends Fluid {

    private int mapColor = 0xFFFFFF;
    private static float overlayAlpha = 0.2F;

    private Chemical chemical;


    public FluidChemical(String fluidname, Chemical chemical) {
        super(fluidname);
        this.chemical = chemical;
        this.setViscosity((int)(chemical.getViscosity()*1000));
    }

    @Override
    public int getColor()
    {
        return mapColor;
    }

    public FluidChemical setColor(int parColor)
    {
        mapColor = parColor;
        return this;
    }

    public float getAlpha()
    {
        return overlayAlpha;
    }

    public FluidChemical setAlpha(float parOverlayAlpha)
    {
        overlayAlpha = parOverlayAlpha;
        return this;
    }

    public FluidChemical setTemperatureC(int temperatureC) {
        this.setTemperature(temperatureC + 273);
        return this;
    }

    public Chemical getChemical() {
        return chemical;
    }

}
