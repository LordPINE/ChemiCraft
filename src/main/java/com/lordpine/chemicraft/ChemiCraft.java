package com.lordpine.chemicraft;

import com.lordpine.chemicraft.Blocks.ModBlocks;
import com.lordpine.chemicraft.Fluids.ModFluids;
import com.lordpine.chemicraft.GUI.GuiHandler;
import com.lordpine.chemicraft.Items.ModItems;
import com.lordpine.chemicraft.Recipes.RecipeRegistry;
import com.lordpine.chemicraft.TileEntities.ModTiles;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.common.MinecraftForge;

@Mod(modid = ChemiCraft.MODID, name = "ChemiCraft", version = ChemiCraft.VERSION)
public class ChemiCraft
{
    public static final String MODID = "chemicraft";
    public static final String VERSION = "0.01";

    @SidedProxy(clientSide="com.lordpine.chemicraft.ClientProxy", serverSide="com.lordpine.chemicraft.CommonProxy")
    public static CommonProxy proxy;

    @Mod.Instance(MODID)
    public static ChemiCraft instance;

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        ModFluids.registerFluids();
        ModItems.registerItems();
        ModBlocks.registerBlocks();
        proxy.registerTiles();
        proxy.registerRenderers();
        MinecraftForge.EVENT_BUS.register(ModEventHandler.INSTANCE);
        System.out.println(ModFluids.pvcfluidblock.getUnlocalizedName());
        NetworkRegistry.INSTANCE.registerGuiHandler(ChemiCraft.instance, new GuiHandler());
        RecipeRegistry.registerRecipes();
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {

    }
}
