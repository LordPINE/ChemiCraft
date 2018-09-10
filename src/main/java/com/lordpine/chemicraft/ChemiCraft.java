package com.lordpine.chemicraft;

import com.lordpine.chemicraft.Blocks.ModBlocks;
import com.lordpine.chemicraft.Fluids.ModFluids;
import com.lordpine.chemicraft.GUI.GuiHandler;
import com.lordpine.chemicraft.Items.ModItems;
import com.lordpine.chemicraft.Recipes.RecipeRegistry;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import net.minecraftforge.common.MinecraftForge;

@Mod(modid = ChemiCraft.MODID, name = "ChemiCraft", version = ChemiCraft.VERSION)
public class ChemiCraft
{
    public static final String packetChannel = "ChemicraftData";
    public static final String MODID = "chemicraft";
    public static final String VERSION = "0.01";

    @SidedProxy(clientSide="com.lordpine.chemicraft.ClientProxy", serverSide="com.lordpine.chemicraft.CommonProxy")
    public static CommonProxy proxy;

    @Mod.Instance(MODID)
    public static ChemiCraft instance;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        ModBlocks.registerBlocks();
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        ModFluids.registerFluids();
        ModItems.registerItems();
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
