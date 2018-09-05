package com.lordpine.chemicraft.GUI;

import Reika.DragonAPI.Base.CoreContainer;
import com.lordpine.chemicraft.TileEntities.TileEntityChemicalReactor;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class ChemicalReactorContainer extends CoreContainer {

    private TileEntityChemicalReactor te;

    public ChemicalReactorContainer(TileEntityChemicalReactor te, EntityPlayer player)
    {
        super(player, te);
        this.te = te;

        this.addPlayerInventory(player);
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return te.isUseableByPlayer(player);
    }

    @Override
    public boolean allowShiftClicking(EntityPlayer player, int slot, ItemStack stack) {
        return false;
    }
}
