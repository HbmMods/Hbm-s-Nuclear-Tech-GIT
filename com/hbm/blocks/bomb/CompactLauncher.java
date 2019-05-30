package com.hbm.blocks.bomb;

import com.hbm.blocks.ModBlocks;
import com.hbm.interfaces.IBomb;
import com.hbm.interfaces.IMultiblock;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.bomb.TileEntityCompactLauncher;
import com.hbm.tileentity.machine.TileEntityDummy;
import com.hbm.tileentity.machine.TileEntityVaultDoor;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class CompactLauncher extends BlockContainer implements IMultiblock, IBomb {

	public CompactLauncher(Material p_i45386_1_) {
		super(p_i45386_1_);
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileEntityCompactLauncher();
	}

	@Override
	public int getRenderType() {
		return -1;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if(world.isRemote)
		{
			return true;
		} else if(!player.isSneaking())
		{
			TileEntityCompactLauncher entity = (TileEntityCompactLauncher) world.getTileEntity(x, y, z);
			if(entity != null)
			{
				FMLNetworkHandler.openGui(player, MainRegistry.instance, ModBlocks.guiID_compact_launcher, world, x, y, z);
			}
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack itemStack) {
		
		TileEntityCompactLauncher te = (TileEntityCompactLauncher) world.getTileEntity(x, y, z);
		
		if(!(world.getBlock(x + 1, y, z + 1).getMaterial().isReplaceable() &&
				world.getBlock(x + 1, y, z).getMaterial().isReplaceable() &&
				world.getBlock(x + 1, y, z - 1).getMaterial().isReplaceable() &&
				world.getBlock(x, y, z - 1).getMaterial().isReplaceable() &&
				world.getBlock(x - 1, y, z - 1).getMaterial().isReplaceable() &&
				world.getBlock(x - 1, y, z).getMaterial().isReplaceable() &&
				world.getBlock(x - 1, y, z + 1).getMaterial().isReplaceable() &&
				world.getBlock(x, y, z + 1).getMaterial().isReplaceable())) {
			world.func_147480_a(x, y, z, true);
			return;
		}

		placeDummy(world, x + 1, y, z + 1, x, y, z, ModBlocks.dummy_port_compact_launcher);
		placeDummy(world, x + 1, y, z, x, y, z, ModBlocks.dummy_plate_compact_launcher);
		placeDummy(world, x + 1, y, z - 1, x, y, z, ModBlocks.dummy_port_compact_launcher);
		placeDummy(world, x, y, z - 1, x, y, z, ModBlocks.dummy_plate_compact_launcher);
		placeDummy(world, x - 1, y, z - 1, x, y, z, ModBlocks.dummy_port_compact_launcher);
		placeDummy(world, x - 1, y, z, x, y, z, ModBlocks.dummy_plate_compact_launcher);
		placeDummy(world, x - 1, y, z + 1, x, y, z, ModBlocks.dummy_port_compact_launcher);
		placeDummy(world, x, y, z + 1, x, y, z, ModBlocks.dummy_plate_compact_launcher);
		
		super.onBlockPlacedBy(world, x, y, z, player, itemStack);
		
	}
	
	private void placeDummy(World world, int x, int y, int z, int xCoord, int yCoord, int zCoord, Block block) {
		
		world.setBlock(x, y, z, block);
		
		TileEntity te = world.getTileEntity(x, y, z);
		
		if(te instanceof TileEntityDummy) {
			TileEntityDummy dummy = (TileEntityDummy)te;
			dummy.targetX = xCoord;
			dummy.targetY = yCoord;
			dummy.targetZ = zCoord;
		}
	}
	
	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess p_149719_1_, int p_149719_2_, int p_149719_3_, int p_149719_4_)
    {
        this.setBlockBounds(0, 1, 0, 1, 1, 1);
    }
	
	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {

        this.setBlockBounds(0, 1, 0, 1, 1, 1);
		return AxisAlignedBB.getBoundingBox(x + this.minX, y + this.minY, z + this.minZ, x + this.maxX, y + this.maxY, z + this.maxZ);
	}

	@Override
	public void explode(World world, int x, int y, int z) {
		TileEntityCompactLauncher entity = (TileEntityCompactLauncher) world.getTileEntity(x, y, z);
		
		if(entity.canLaunch())
			entity.launch();
	}

}
