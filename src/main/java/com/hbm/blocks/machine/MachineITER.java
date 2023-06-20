package com.hbm.blocks.machine;

import java.util.Random;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.material.Mats;
import com.hbm.items.ModItems;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.TileEntityProxyCombo;
import com.hbm.tileentity.machine.TileEntityITER;
import com.hbm.tileentity.machine.TileEntityITERStruct;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class MachineITER extends BlockDummyable {

	public MachineITER() {
		super(Material.iron);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		
		if(meta >= 12)
			return new TileEntityITER();

		if(meta >= 6)
			return new TileEntityProxyCombo(true, true, true);
		
		return null;
	}

	@Override
	public Item getItemDropped(int i, Random rand, int j) {
		return null;
	}

	@Override
	public int[] getDimensions() {
		//because we'll implement our own gnarly behavior here
		return new int[] { 0, 0, 0, 0, 0, 0 };
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
			
			TileEntityITER entity = (TileEntityITER) world.getTileEntity(pos[0], pos[1], pos[2]);
			if(entity != null)
			{
				FMLNetworkHandler.openGui(player, MainRegistry.instance, 0, world, pos[0], pos[1], pos[2]);
			}
			return true;
		} else {
			return false;
		}
	}
	
	public static final int height = 2;
	
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack itemStack) {
		
		if(!(player instanceof EntityPlayer))
			return;

		int i = MathHelper.floor_double(player.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;
		EntityPlayer pl = (EntityPlayer) player;
		
		int o = getOffset();
		
		ForgeDirection dir = ForgeDirection.NORTH;
		
		if(i == 0)
		{
			dir = ForgeDirection.getOrientation(2);
		}
		if(i == 1)
		{
			dir = ForgeDirection.getOrientation(5);
		}
		if(i == 2)
		{
			dir = ForgeDirection.getOrientation(3);
		}
		if(i == 3)
		{
			dir = ForgeDirection.getOrientation(4);
		}
		
		dir = dir.getOpposite();
		
		world.setBlockToAir(x, y, z);
		
		if(!checkRequirement(world, x, y, z, dir, o)) {
			
			if(!pl.capabilities.isCreativeMode) {
				ItemStack stack = pl.inventory.mainInventory[pl.inventory.currentItem];
				Item item = Item.getItemFromBlock(this);
				
				if(stack == null) {
					pl.inventory.mainInventory[pl.inventory.currentItem] = new ItemStack(this);
				} else {
					if(stack.getItem() != item || stack.stackSize == stack.getMaxStackSize()) {
						pl.inventory.addItemStackToInventory(new ItemStack(this));
					} else {
						pl.getHeldItem().stackSize++;
					}
				}
			}
			
			return;
		}
		
		pl.getHeldItem().stackSize--;
		
		world.setBlock(x + dir.offsetX * o , y + dir.offsetY * o + height, z + dir.offsetZ * o, this, dir.ordinal() + offset, 3);
		this.safeRem = true;
		fillSpace(world, x, y, z, dir, o);
		this.safeRem = false;
		world.scheduleBlockUpdate(x, y, z, this, 1);
		world.scheduleBlockUpdate(x, y, z, this, 2);

		super.onBlockPlacedBy(world, x, y, z, player, itemStack);
	}

	@Override
	public boolean checkRequirement(World world, int x, int y, int z, ForgeDirection dir, int o) {
		
		x = x + dir.offsetX * o;
		z = z + dir.offsetZ * o;
		
		int[][][] layout = TileEntityITERStruct.collisionMask;
		
		for(int iy = 0; iy < 5; iy++) {
			
			int l = iy > 2 ? 4 - iy : iy;
			int[][] layer = layout[l];
			
			for(int ix = 0; ix < layer.length; ix++) {

				for(int iz = 0; iz < layer.length; iz++) {

					int ex = ix - layer.length / 2;
					int ez = iz - layer.length / 2;
					
					if(ex == 0 && y == 2 && ez == 0)
						continue;
					
					if(!world.getBlock(x + ex, y + iy, z + ez).canPlaceBlockAt(world, x + ex, y + iy, z + ez)) {
						return false;
					}
				}
			}
		}
		
		return true;
	}

	@Override
	public void fillSpace(World world, int x, int y, int z, ForgeDirection dir, int o) {
		
		x = x + dir.offsetX * o;
		z = z + dir.offsetZ * o;
		
		int[][][] layout = TileEntityITERStruct.collisionMask;
		
		for(int iy = 0; iy < 5; iy++) {
			
			int l = iy > 2 ? 4 - iy : iy;
			int[][] layer = layout[l];
			
			for(int ix = 0; ix < layer.length; ix++) {

				for(int iz = 0; iz < layer[0].length; iz++) {

					int ex = ix - layer.length / 2;
					int ez = iz - layer.length / 2;
					
					int meta = 0;
					
					if(iy < 2) {
						meta = ForgeDirection.DOWN.ordinal();
					} else if(iy > 2) {
						meta = ForgeDirection.UP.ordinal();
					} else if(ex < 0) {
						meta = ForgeDirection.WEST.ordinal();
					} else if(ex > 0) {
						meta = ForgeDirection.EAST.ordinal();
					} else if(ez < 0) {
						meta = ForgeDirection.NORTH.ordinal();
					} else if(ez > 0) {
						meta = ForgeDirection.SOUTH.ordinal();
					} else {
						continue;
					}
					
					if(layout[l][ix][iz] > 0)
						world.setBlock(x + ex, y + iy, z + ez, this, meta, 3);
				}
			}
		}
		
		this.makeExtra(world, x, y, z);
		this.makeExtra(world, x, y + 4, z);
		
		Vec3 vec = Vec3.createVectorHelper(5.75, 0, 0);
		
		for(int i = 0; i < 16; i++) {
			vec.rotateAroundY((float) (Math.PI / 8));
			this.makeExtra(world, x + (int)vec.xCoord, y, z + (int)vec.zCoord);
			this.makeExtra(world, x + (int)vec.xCoord, y + 4, z + (int)vec.zCoord);
		}
	}

	@Override
	public int getOffset() {
		return 7;
	}
	
	public static boolean drop = true;
	
	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int i) {

		if(i >= 12 && drop) {

			for(int l = 0; l < 4; l++) {
				world.spawnEntityInWorld(new EntityItem(world, x + 0.5, y + 0.5, z + 0.5, new ItemStack(ModBlocks.fusion_conductor, 64)));
				world.spawnEntityInWorld(new EntityItem(world, x + 0.5, y + 0.5, z + 0.5, new ItemStack(ModItems.plate_cast, 64, Mats.MAT_STEEL.id)));
			}

			world.spawnEntityInWorld(new EntityItem(world, x + 0.5, y + 0.5, z + 0.5, new ItemStack(ModBlocks.fusion_conductor, 36)));
			world.spawnEntityInWorld(new EntityItem(world, x + 0.5, y + 0.5, z + 0.5, new ItemStack(ModItems.plate_cast, 36, Mats.MAT_STEEL.id)));
			world.spawnEntityInWorld(new EntityItem(world, x + 0.5, y + 0.5, z + 0.5, new ItemStack(ModBlocks.fusion_center, 64)));
			world.spawnEntityInWorld(new EntityItem(world, x + 0.5, y + 0.5, z + 0.5, new ItemStack(ModBlocks.fusion_motor, 4)));
			world.spawnEntityInWorld(new EntityItem(world, x + 0.5, y + 0.5, z + 0.5, new ItemStack(ModBlocks.reinforced_glass, 8)));
			world.spawnEntityInWorld(new EntityItem(world, x + 0.5, y + 0.5, z + 0.5, new ItemStack(ModBlocks.struct_iter_core, 1)));
		}

		super.breakBlock(world, x, y, z, block, i);
	}

}
