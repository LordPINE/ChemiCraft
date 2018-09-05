package com.lordpine.chemicraft;

import com.lordpine.chemicraft.Fluids.ModFluids;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.event.entity.player.FillBucketEvent;
import cpw.mods.fml.common.eventhandler.Event.Result;
import net.minecraftforge.fluids.Fluid;

import java.util.HashMap;
import java.util.Map;

public class ModEventHandler {

    public static ModEventHandler INSTANCE = new ModEventHandler();

    public Map<Block, Item> buckets = new HashMap<Block, Item>();

    @SubscribeEvent
    public void onBucketFill(FillBucketEvent event) {
        ItemStack result = fillCustomBucket(event.world, event.target);

        if (result == null)

            return;

        event.result = result;

        event.setResult(Result.ALLOW);

    }



    private ItemStack fillCustomBucket(World world, MovingObjectPosition pos) {
                Block block = world.getBlock(pos.blockX, pos.blockY, pos.blockZ);
        Item bucket = buckets.get(block);

        if (bucket != null && world.getBlockMetadata(pos.blockX, pos.blockY, pos.blockZ) == 0) {

            world.setBlockToAir(pos.blockX, pos.blockY, pos.blockZ);

            return new ItemStack(bucket);

        } else

            return null;



    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void setupExtraIcons(TextureStitchEvent.Pre event) {
        if (event.map.getTextureType() == 0) {
            registerFluidTexture(ModFluids.PVC, event);
            registerFluidTexture(ModFluids.sulfur_dioxide, event);
        }
    }

    public static void registerFluidTexture(Fluid fluid, TextureStitchEvent.Pre event) {
        fluid.setIcons(event.map.registerIcon(ChemiCraft.MODID + ":fluid/" + fluid.getUnlocalizedName().substring(6) + "_liquidStill"),event.map.registerIcon(ChemiCraft.MODID + ":fluid/" + fluid.getUnlocalizedName().substring(6) + "_liquidFlowing"));
    }
}
