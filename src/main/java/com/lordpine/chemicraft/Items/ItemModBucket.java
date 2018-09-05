package com.lordpine.chemicraft.Items;

import com.lordpine.chemicraft.ChemiCraft;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBucket;

public class ItemModBucket extends ItemBucket {

    public ItemModBucket(Block fluidblock, String name) {
        super(fluidblock);
        this.setContainerItem(Items.bucket);
        this.maxStackSize = 1;
        this.setUnlocalizedName(name).setContainerItem(Items.bucket);
        GameRegistry.registerItem(this, name);

    }

}
