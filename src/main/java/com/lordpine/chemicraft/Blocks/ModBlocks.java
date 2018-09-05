package com.lordpine.chemicraft.Blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class ModBlocks {

    public static Block chemical_reactor;

    public static void registerBlocks() {

        chemical_reactor = new BlockChemicalReactor(Material.iron);
    }

}
