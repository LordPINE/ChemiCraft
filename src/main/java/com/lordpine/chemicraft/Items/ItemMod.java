package com.lordpine.chemicraft.Items;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;

public class ItemMod extends Item {

    public ItemMod(String name) {
        super();
        GameRegistry.registerItem(this, name);
    }

}
