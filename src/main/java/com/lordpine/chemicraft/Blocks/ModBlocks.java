package com.lordpine.chemicraft.Blocks;

import Reika.DragonAPI.ModInteract.LegacyWailaHelper;
import Reika.DragonAPI.ModList;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class ModBlocks {

    public static Block chemical_reactor;

    public static void registerBlocks() {
        chemical_reactor = new BlockChemicalReactor(Material.iron);
        if (ModList.WAILA.isLoaded()) {
            LegacyWailaHelper.registerLegacyWAILACompat(chemical_reactor);
        }
    }

}
