package com.lordpine.chemicraft.Fluids;

import Reika.DragonAPI.ModInteract.DeepInteract.SensitiveFluidRegistry;
import Reika.RotaryCraft.Registry.ItemRegistry;

import com.lordpine.chemicraft.ChemiCraft;
import com.lordpine.chemicraft.Chemistry.Chemical;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.*;

public class ModFluids {

    public static FluidChemical PVC;
    public static FluidChemical sulfur_dioxide;
    public static FluidChemical sulfur_trioxide;
    public static Block pvcfluidblock;
    public static Block sulfur_dioxideblock;
    public static Block sulfur_trioxideblock;

    public static final float AIR_DENSITY = 1.225f;

    public static void registerFluids() {
        PVC = new FluidChemical("pvc", new Chemical("PVC", 10000, 10000)).setColor(0xffffff).setTemperatureC(260);
        register(PVC);
        pvcfluidblock = new BlockModFluid(PVC, Material.water).setBlockName("pvc_liquid");
        registerFluidBlock(pvcfluidblock);

        sulfur_dioxide = (FluidChemical)new FluidChemical("sulfur_dioxide", new Chemical("sulfur_dioxide", 64.066f, 0.403f)).setColor(0xffffff).setTemperatureC(20).setDensity(calcDensity(2.6288f)).setGaseous(true);
        register(sulfur_dioxide);
        sulfur_dioxideblock = new BlockModFluid(sulfur_dioxide, Material.water).setBlockName("sulfur_dioxide_liquid");
        registerFluidBlock(sulfur_dioxideblock);

        sulfur_trioxide = (FluidChemical)new FluidChemical("sulfur_trioxide", new Chemical("sulfur_trioxide", 80.066f, 0.403f)).setColor(0xffffff).setTemperatureC(20).setDensity(calcDensity(2.6288f)).setGaseous(true);
        register(sulfur_trioxide);
        sulfur_trioxideblock = new BlockModFluid(sulfur_trioxide, Material.water).setBlockName("sulfur_trioxide_liquid");
        registerFluidBlock(sulfur_trioxideblock);
    }


    private static void register(Fluid fluid) {
        FluidRegistry.registerFluid(fluid);
        SensitiveFluidRegistry.instance.registerFluid(fluid);
    }

    private static void registerFluidBlock(Block block) {
        GameRegistry.registerBlock(block, ChemiCraft.MODID + "_" + block.getUnlocalizedName().substring(5));
    }

    public static int calcDensity(float realDensity) {
        float temp = 1000/(1000 - AIR_DENSITY);
        float temp2 = temp * 1000 - 1000;
        int density = (int)(temp*realDensity + temp2);
        return density;
    }

}
