package com.lordpine.chemicraft;

import com.lordpine.chemicraft.TileEntities.ModTiles;
import cpw.mods.fml.common.registry.GameRegistry;

public class CommonProxy {

    public void registerRenderers()
    {
        //unused server side. -- see ClientProxy for implementation
    }

    public void registerTiles() {
        ModTiles.registerTiles();
    }

}
