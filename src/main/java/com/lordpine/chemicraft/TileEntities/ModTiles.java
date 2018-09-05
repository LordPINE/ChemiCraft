package com.lordpine.chemicraft.TileEntities;

import Reika.ReactorCraft.ReactorCraft;
import com.lordpine.chemicraft.Fluids.ModFluids;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraftforge.fluids.FluidRegistry;

public class ModTiles {

    public static void registerTiles() {
        GameRegistry.registerTileEntity(TileEntityChemicalReactor.class, "te_chemical_reactor");
        TileEntityChemicalReactor.addValidFluid(ModFluids.PVC);
        TileEntityChemicalReactor.addValidFluid(ModFluids.sulfur_dioxide);
        TileEntityChemicalReactor.addValidFluid(FluidRegistry.WATER);
        TileEntityChemicalReactor.addValidFluid(ReactorCraft.O);
    }

}
