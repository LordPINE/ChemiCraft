package com.lordpine.chemicraft.Items;

import buildcraft.energy.BucketHandler;
import com.lordpine.chemicraft.ChemiCraft;
import com.lordpine.chemicraft.Fluids.ModFluids;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;

public class ModItems {

    public static Item pvc_bucket;
    public static Item sulfur_dioxide_bucket;

    public static void registerItems() {
        pvc_bucket = new ItemModBucket(ModFluids.pvcfluidblock, "pvc_bucket");
        FluidContainerRegistry.registerFluidContainer(new FluidStack(ModFluids.PVC, FluidContainerRegistry.BUCKET_VOLUME), new ItemStack(pvc_bucket), new ItemStack(Items.bucket));
        BucketHandler.INSTANCE.buckets.put(ModFluids.pvcfluidblock, pvc_bucket);

        sulfur_dioxide_bucket = new ItemModBucket(ModFluids.sulfur_dioxideblock, "sulfur_dioxide_bucket").setTextureName(ChemiCraft.MODID + ":bucket_sulfur_dioxide");
        FluidContainerRegistry.registerFluidContainer(new FluidStack(ModFluids.sulfur_dioxide, FluidContainerRegistry.BUCKET_VOLUME), new ItemStack(sulfur_dioxide_bucket), new ItemStack(Items.bucket));
        BucketHandler.INSTANCE.buckets.put(ModFluids.sulfur_dioxideblock, sulfur_dioxide_bucket);
    }

}
