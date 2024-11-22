package com.hbm.blocks.machine;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.ModBlocks;
import com.hbm.handler.MultiblockHandlerXR;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.TileEntityProxyCombo;
import com.hbm.tileentity.machine.TileEntityMachinePlasmaHeater;
import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.Random;

public class MachinePlasmaHeater extends BlockDummyable {

	public MachinePlasmaHeater() {
		super(Material.iron);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {

		if(meta >= 12)
			return new TileEntityMachinePlasmaHeater();

		if(meta >= 6)
			return new TileEntityProxyCombo(false, true, true);

		return null;
	}

	@Override
	public Item getItemDropped(int i, Random rand, int j) {
		return null;
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if(world.isRemote)
		{
			return true;
		} else if(!player.isSneaking())
		{
			int[] pos = this.findCore(world, x, y, z);

			if(pos == null)
				return false;

			TileEntityMachinePlasmaHeater entity = (TileEntityMachinePlasmaHeater) world.getTileEntity(pos[0], pos[1], pos[2]);
			if(entity != null)
			{
				FMLNetworkHandler.openGui(player, MainRegistry.instance, 0, world, pos[0], pos[1], pos[2]);
			}
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void fillSpace(World world, int x, int y, int z, ForgeDirection dir, int o) {
		super.fillSpace(world, x, y, z, dir, o);

		MultiblockHandlerXR.fillSpace(world, x + dir.offsetX * o , y + dir.offsetY * o, z + dir.offsetZ * o, new int[] {4, -3, 2, 1, 1, 1}, this, dir);
		MultiblockHandlerXR.fillSpace(world, x + dir.offsetX * o , y + 2, z + dir.offsetZ * o, new int[] {0, 1, 10, -8, 0, 0}, this, dir);

		ForgeDirection side = dir.getRotation(ForgeDirection.UP);

		for(int i = 1; i < 4; i++) {
			for(int j = -1; j < 2; j++) {

				this.makeExtra(world, x + side.offsetX * j, y + i, z + side.offsetZ * j);
			}
		}
	}

    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {

        float f = 0.0625F;

        if(world.getBlockMetadata(x, y, z) == ForgeDirection.UP.ordinal() && world.getBlock(x, y + 1, z) != this) {
    		return AxisAlignedBB.getBoundingBox(x, y, z, x + 1, y + f * 8F, z + 1);
    	} else if(world.getBlockMetadata(x, y, z) == ForgeDirection.DOWN.ordinal() && world.getBlock(x, y - 1, z) != this) {
    		return AxisAlignedBB.getBoundingBox(x, y + f * 8F, z, x + 1, y + 1, z + 1);
    	} else {
    		return AxisAlignedBB.getBoundingBox(x, y, z, x + 1, y + 1, z + 1);
    	}
    }

    public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {

        float f = 0.0625F;

    	if(world.getBlockMetadata(x, y, z) == ForgeDirection.UP.ordinal() && world.getBlock(x, y + 1, z) != this) {
            this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, f * 8F, 1.0F);
    	} else if(world.getBlockMetadata(x, y, z) == ForgeDirection.DOWN.ordinal() && world.getBlock(x, y - 1, z) != this) {
    		this.setBlockBounds(0.0F, f * 8F, 0.0F, 1, 1.0F, 1.0F);
    	} else  {
            this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
    	}
    }

	protected boolean checkRequirement(World world, int x, int y, int z, ForgeDirection dir, int o) {

		if(!MultiblockHandlerXR.checkSpace(world, x + dir.offsetX * o , y + dir.offsetY * o, z + dir.offsetZ * o, getDimensions(), x, y, z, dir))
			return false;

		if(!MultiblockHandlerXR.checkSpace(world, x + dir.offsetX * o , y + dir.offsetY * o, z + dir.offsetZ * o, new int[] {4, -3, 1, 1, 1, 1}, x, y, z, dir))
			return false;

		if(!MultiblockHandlerXR.checkSpace(world, x + dir.offsetX * o , y + 2, z + dir.offsetZ * o, new int[] {0, 1, 10, -8, 0, 0}, x, y, z, dir))
			return false;

		return true;
	}

	@Override
	public int[] getDimensions() {
		return new int[] {3, 0, 8, 1, 1, 1};
	}

	@Override
	public int getOffset() {
		return 1;
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int i) {

    	if(i >= 12) {

            for(int l = 0; l < 2; l++)
            	world.spawnEntityInWorld(new EntityItem(world, x + 0.5, y + 0.5, z + 0.5, new ItemStack(ModBlocks.fusion_heater, 64)));

        	world.spawnEntityInWorld(new EntityItem(world, x + 0.5, y + 0.5, z + 0.5, new ItemStack(ModBlocks.fusion_heater, 7)));
        	world.spawnEntityInWorld(new EntityItem(world, x + 0.5, y + 0.5, z + 0.5, new ItemStack(ModBlocks.struct_plasma_core, 1)));
    	}

		super.breakBlock(world, x, y, z, block, i);
    }
}
