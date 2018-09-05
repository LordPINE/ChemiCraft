package com.lordpine.chemicraft;

import com.lordpine.chemicraft.Renders.RenderChemicalReactor;
import com.lordpine.chemicraft.TileEntities.TileEntityChemicalReactor;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;


@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy {

    @Override
    public void registerRenderers() {
        RenderChemicalReactor chemicalreactorrenderer = new RenderChemicalReactor();
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityChemicalReactor.class, chemicalreactorrenderer);
    }

}
