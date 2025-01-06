package com.hbm.blocks.machine;

import java.util.Random;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.ModBlocks;
import com.hbm.handler.BossSpawnHandler;
import com.hbm.handler.MultiblockHandlerXR;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.TileEntityProxyCombo;
import com.hbm.tileentity.machine.TileEntitySoyuzLauncher;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class SoyuzLauncher extends BlockDummyable {

	public SoyuzLauncher(Material p_i45386_1_) {
		super(p_i45386_1_);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {

		if(meta >= 12) return new TileEntitySoyuzLauncher();
		if(meta >= 6) return new TileEntityProxyCombo().power().fluid();
		
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
			
			BossSpawnHandler.markFBI(player);
			
			TileEntitySoyuzLauncher entity = (TileEntitySoyuzLauncher) world.getTileEntity(pos[0], pos[1], pos[2]);
			if(entity != null)
			{
				FMLNetworkHandler.openGui(player, MainRegistry.instance, 0, world, pos[0], pos[1], pos[2]);
			}
			return true;
		} else {
			return false;
		}
	}
	
	public static final int height = 4;
	
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack itemStack) {
		
		if(!(player instanceof EntityPlayer))
			return;
		
		EntityPlayer pl = (EntityPlayer) player;
		
		int o = -getOffset();
		
		ForgeDirection dir = ForgeDirection.EAST;
		
		if(!checkRequirement(world, x, y, z, dir, o)) {
			world.setBlockToAir(x, y, z);
			
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
		fillSpace(world, x, y, z, dir, o);
		world.scheduleBlockUpdate(x, y, z, this, 1);
		world.scheduleBlockUpdate(x, y, z, this, 2);

		super.onBlockPlacedBy(world, x, y, z, player, itemStack);
	}
	
	public boolean checkRequirement(World world, int x, int y, int z, ForgeDirection dir, int o) {
		
		x = x + dir.offsetX * o;
		y = y + dir.offsetY * o + height;
		z = z + dir.offsetZ * o;

		if(!MultiblockHandlerXR.checkSpace(world, x, y, z, new int[] { 0, 1, 6, 6, 6, 6 }, x, y, z, dir)) return false;
		if(!MultiblockHandlerXR.checkSpace(world, x, y, z, new int[] { -2, 4, -3, 6, -3, 6 }, x, y, z, dir)) return false;
		if(!MultiblockHandlerXR.checkSpace(world, x, y, z, new int[] { -2, 4, 6, -3, -3, 6 }, x, y, z, dir)) return false;
		if(!MultiblockHandlerXR.checkSpace(world, x, y, z, new int[] { -2, 4, 6, -3, 6, -3 }, x, y, z, dir)) return false;
		if(!MultiblockHandlerXR.checkSpace(world, x, y, z, new int[] { -2, 4, -3, 6, 6, -3 }, x, y, z, dir)) return false;
		if(!MultiblockHandlerXR.checkSpace(world, x, y, z, new int[] { 0, 4, 1, 1, -6, 8 }, x, y, z, dir)) return false;
		if(!MultiblockHandlerXR.checkSpace(world, x, y, z, new int[] { 0, 4, 2, 2, 9, -5 }, x, y, z, dir)) return false;
		
		return true;
	}
	
	public void fillSpace(World world, int x, int y, int z, ForgeDirection dir, int o) {
		
		x = x + dir.offsetX * o;
		y = y + dir.offsetY * o + height;
		z = z + dir.offsetZ * o;

		MultiblockHandlerXR.fillSpace(world, x, y, z, new int[] { 0, 1, 6, 6, 6, 6 }, this, dir);
		MultiblockHandlerXR.fillSpace(world, x, y, z, new int[] { -2, 4, -3, 6, -3, 6 }, this, dir);
		MultiblockHandlerXR.fillSpace(world, x, y, z, new int[] { -2, 4, 6, -3, -3, 6 }, this, dir);
		MultiblockHandlerXR.fillSpace(world, x, y, z, new int[] { -2, 4, 6, -3, 6, -3 }, this, dir);
		MultiblockHandlerXR.fillSpace(world, x, y, z, new int[] { -2, 4, -3, 6, 6, -3 }, this, dir);
		MultiblockHandlerXR.fillSpace(world, x, y, z, new int[] { 0, 4, 1, 1, -6, 8 }, this, dir);
		MultiblockHandlerXR.fillSpace(world, x, y, z, new int[] { 0, 4, 2, 2, 9, -5 }, this, dir);
		
		keepInventory = true;
		for(int ix = -6; ix <= 6; ix++) {
			for(int iz = -6; iz <= 6; iz++) {
				
				if(ix == 6 || ix == -6 || iz == 6 || iz == -6) {
					this.makeExtra(world, x + ix, y, z + iz);
					this.makeExtra(world, x + ix, y + 1, z + iz);
				}
			}
		}
		keepInventory = false;
	}

	@Override
	public int[] getDimensions() {
		//because we'll implement our own gnarly behavior here
		return new int[] { 0, 0, 0, 0, 0, 0 };
	}

	@Override
	public int getOffset() {
		return 0;
	}
	
	private final Random field_149933_a = new Random();
	private static boolean keepInventory;
	
	@Override
	public void breakBlock(World world, int x, int y, int z, Block p_149749_5_, int i) {
		if(!keepInventory) {
			ISidedInventory tileentityfurnace = (ISidedInventory) world.getTileEntity(x, y, z);

			if(tileentityfurnace instanceof TileEntitySoyuzLauncher) {
				for(int i1 = 0; i1 < tileentityfurnace.getSizeInventory(); ++i1) {
					ItemStack itemstack = tileentityfurnace.getStackInSlot(i1);

					if(itemstack != null) {
						float f = this.field_149933_a.nextFloat() * 0.8F + 0.1F;
						float f1 = this.field_149933_a.nextFloat() * 0.8F + 0.1F;
						float f2 = this.field_149933_a.nextFloat() * 0.8F + 0.1F;

						while(itemstack.stackSize > 0) {
							int j1 = this.field_149933_a.nextInt(21) + 10;

							if(j1 > itemstack.stackSize) {
								j1 = itemstack.stackSize;
							}

							itemstack.stackSize -= j1;
							EntityItem entityitem = new EntityItem(world, x + f, y + f1, z + f2, new ItemStack(itemstack.getItem(), j1, itemstack.getItemDamage()));

							if(itemstack.hasTagCompound()) {
								entityitem.getEntityItem().setTagCompound((NBTTagCompound) itemstack.getTagCompound().copy());
							}

							float f3 = 0.05F;
							entityitem.motionX = (float) this.field_149933_a.nextGaussian() * f3;
							entityitem.motionY = (float) this.field_149933_a.nextGaussian() * f3 + 0.2F;
							entityitem.motionZ = (float) this.field_149933_a.nextGaussian() * f3;
							world.spawnEntityInWorld(entityitem);
						}
					}
				}

				for(int l = 0; l < 6; l++)
					world.spawnEntityInWorld(new EntityItem(world, x + 0.5, y + 0.5, z + 0.5, new ItemStack(ModBlocks.struct_launcher, 64)));
				for(int l = 0; l < 4; l++)
					world.spawnEntityInWorld(new EntityItem(world, x + 0.5, y + 0.5, z + 0.5, new ItemStack(ModBlocks.concrete_smooth, 64)));
				for(int l = 0; l < 6; l++)
					world.spawnEntityInWorld(new EntityItem(world, x + 0.5, y + 0.5, z + 0.5, new ItemStack(ModBlocks.struct_scaffold, 64)));
				world.spawnEntityInWorld(new EntityItem(world, x + 0.5, y + 0.5, z + 0.5, new ItemStack(ModBlocks.struct_launcher, 30)));
				world.spawnEntityInWorld(new EntityItem(world, x + 0.5, y + 0.5, z + 0.5, new ItemStack(ModBlocks.struct_scaffold, 63)));
				world.spawnEntityInWorld(new EntityItem(world, x + 0.5, y + 0.5, z + 0.5, new ItemStack(ModBlocks.concrete_smooth, 38)));
				world.spawnEntityInWorld(new EntityItem(world, x + 0.5, y + 0.5, z + 0.5, new ItemStack(ModBlocks.struct_soyuz_core, 1)));

				world.func_147453_f(x, y, z, p_149749_5_);
			}
		}

		super.breakBlock(world, x, y, z, p_149749_5_, i);
	}

}
