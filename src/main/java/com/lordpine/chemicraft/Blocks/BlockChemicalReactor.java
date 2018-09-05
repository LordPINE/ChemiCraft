package com.lordpine.chemicraft.Blocks;

import com.lordpine.chemicraft.ChemiCraft;
import com.lordpine.chemicraft.TileEntities.TileEntityChemicalReactor;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockChemicalReactor extends Block implements ITileEntityProvider {

    private static final String name = "chemical_reactor";

    public BlockChemicalReactor(Material material) {
        super(material);
        setBlockName(name);
        GameRegistry.registerBlock(this, name);
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float lx, float ly, float lz)
    {
        if (world.isRemote) return true;

        TileEntity te = world.getTileEntity(x, y, z);
        if (te != null && te instanceof TileEntityChemicalReactor)
        {
            //System.out.println("HAS TE!");
            player.openGui(ChemiCraft.instance, 0, world, x, y, z);
            return true;
        }
        return false;
    }

    /**
     * Returns a new instance of a block's tile entity class. Called on placing the block.
     *
     * @param p_149915_1_
     * @param p_149915_2_
     */
    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
        return new TileEntityChemicalReactor();
    }

    @Override
    public boolean shouldSideBeRendered(IBlockAccess world, int x, int y, int z, int side)
    {
        return false;
    }

    @Override
    public boolean isOpaqueCube()
    {
        return false;
    }

    @Override
    public boolean renderAsNormalBlock()
    {
        return false;
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack blockitem) {
        world.setBlockMetadataWithNotify(x, y, z, getOrientation(player), 2);
        System.out.println(getOrientation(player));
    }


    /***
     North = 0
     East = 1
     South = 2
     West = 3
     ***/
    public int getOrientation(EntityLivingBase placer) {
        if (placer != null && placer instanceof EntityPlayer) {
            int l = MathHelper.floor_double((double) (placer.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
            return l;
        }
        return 0;
    }
}
