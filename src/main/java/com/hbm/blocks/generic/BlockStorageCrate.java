package com.hbm.blocks.generic;

import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.items.ModItems;
import com.hbm.items.tool.ItemLock;
import com.hbm.lib.RefStrings;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.machine.TileEntityLockableBase;
import com.hbm.tileentity.machine.storage.TileEntityCrateBase;
import com.hbm.tileentity.machine.storage.TileEntityCrateDesh;
import com.hbm.tileentity.machine.storage.TileEntityCrateIron;
import com.hbm.tileentity.machine.storage.TileEntityCrateSteel;
import com.hbm.tileentity.machine.storage.TileEntityCrateTungsten;
import com.hbm.tileentity.machine.storage.TileEntitySafe;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class BlockStorageCrate extends BlockContainer {

	@SideOnly(Side.CLIENT)
	private IIcon iconTop;

	public BlockStorageCrate(Material p_i45394_1_) {
		super(p_i45394_1_);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		if(this == ModBlocks.crate_iron) {
			this.iconTop = iconRegister.registerIcon(RefStrings.MODID + ":crate_iron_top");
			this.blockIcon = iconRegister.registerIcon(RefStrings.MODID + ":crate_iron_side");
		}
		if(this == ModBlocks.crate_steel) {
			this.iconTop = iconRegister.registerIcon(RefStrings.MODID + ":crate_steel_top");
			this.blockIcon = iconRegister.registerIcon(RefStrings.MODID + ":crate_steel_side");
		}
		if(this == ModBlocks.crate_tungsten) {
			this.iconTop = iconRegister.registerIcon(RefStrings.MODID + ":crate_tungsten_top");
			this.blockIcon = iconRegister.registerIcon(RefStrings.MODID + ":crate_tungsten_side");
		}
		if(this == ModBlocks.crate_desh) {
			this.iconTop = iconRegister.registerIcon(RefStrings.MODID + ":crate_desh_top");
			this.blockIcon = iconRegister.registerIcon(RefStrings.MODID + ":crate_desh_side");
		}
		if(this == ModBlocks.safe) {
			this.iconTop = iconRegister.registerIcon(RefStrings.MODID + ":safe_front");
			this.blockIcon = iconRegister.registerIcon(RefStrings.MODID + ":safe_side");
		}
		if(this == ModBlocks.mass_storage) {
			this.iconTop = iconRegister.registerIcon(RefStrings.MODID + ":mass_storage_top");
			this.blockIcon = iconRegister.registerIcon(RefStrings.MODID + ":mass_storage_side");
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int metadata) {

		if(this == ModBlocks.safe)
			return metadata == 0 && side == 3 ? this.iconTop : (side == metadata ? this.iconTop : this.blockIcon);

		return side == 1 ? this.iconTop : (side == 0 ? this.iconTop : this.blockIcon);
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		if(this == ModBlocks.crate_iron) return new TileEntityCrateIron();
		if(this == ModBlocks.crate_steel) return new TileEntityCrateSteel();
		if(this == ModBlocks.crate_desh) return new TileEntityCrateDesh();
		if(this == ModBlocks.crate_tungsten) return new TileEntityCrateTungsten();
		if(this == ModBlocks.safe) return new TileEntitySafe();
		return null;
	}
	
	private static boolean dropInv = true;
	
	@Override
	public boolean removedByPlayer(World world, EntityPlayer player, int x, int y, int z, boolean willHarvest) {
		
		if(!player.capabilities.isCreativeMode && !world.isRemote && willHarvest) {
			
			ItemStack drop = new ItemStack(this);
			ISidedInventory inv = (ISidedInventory)world.getTileEntity(x, y, z);
			
			NBTTagCompound nbt = new NBTTagCompound();
			
			if(inv != null) {
				
				for(int i = 0; i < inv.getSizeInventory(); i++) {
					
					ItemStack stack = inv.getStackInSlot(i);
					if(stack == null)
						continue;
					
					NBTTagCompound slot = new NBTTagCompound();
					stack.writeToNBT(slot);
					nbt.setTag("slot" + i, slot);
				}
			}
			
			if(inv instanceof TileEntityLockableBase) {
				TileEntityLockableBase lockable = (TileEntityLockableBase) inv;
				
				if(lockable.isLocked()) {
					nbt.setInteger("lock", lockable.getPins());
					nbt.setDouble("lockMod", lockable.getMod());
				}
			}
			
			if(!nbt.hasNoTags()) {
				drop.stackTagCompound = nbt;
			}
			
			world.spawnEntityInWorld(new EntityItem(world, x + 0.5, y + 0.5, z + 0.5, drop));
		}
		
		dropInv = false;
		boolean flag = world.setBlockToAir(x, y, z);
		dropInv = true;
		
		return flag;
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if(world.isRemote) {
			return true;
		} else if(player.getHeldItem() != null && (player.getHeldItem().getItem() instanceof ItemLock || player.getHeldItem().getItem() == ModItems.key_kit)) {
			return false;

		} else if(!player.isSneaking()) {
			TileEntity entity = world.getTileEntity(x, y, z);
			if(entity instanceof TileEntityCrateBase && ((TileEntityCrateBase) entity).canAccess(player)) {
				FMLNetworkHandler.openGui(player, MainRegistry.instance, 0, world, x, y, z);
			}
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack stack) {
		
		ISidedInventory inv = (ISidedInventory)world.getTileEntity(x, y, z);
		
		if(inv != null && stack.hasTagCompound()) {
			
			for(int i = 0; i < inv.getSizeInventory(); i++) {
				inv.setInventorySlotContents(i, ItemStack.loadItemStackFromNBT(stack.stackTagCompound.getCompoundTag("slot" + i)));
			}
			
			if(inv instanceof TileEntityLockableBase) {
				TileEntityLockableBase lockable = (TileEntityLockableBase) inv;
				
				if(stack.stackTagCompound.hasKey("lock")) {
					lockable.setPins(stack.stackTagCompound.getInteger("lock"));
					lockable.setMod(stack.stackTagCompound.getDouble("lockMod"));
					lockable.lock();
				}
			}
		}

		if(this != ModBlocks.safe)
			super.onBlockPlacedBy(world, x, y, z, player, stack);

		int i = MathHelper.floor_double(player.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;

		if(i == 0) {
			world.setBlockMetadataWithNotify(x, y, z, 2, 2);
		}
		if(i == 1) {
			world.setBlockMetadataWithNotify(x, y, z, 5, 2);
		}
		if(i == 2) {
			world.setBlockMetadataWithNotify(x, y, z, 3, 2);
		}
		if(i == 3) {
			world.setBlockMetadataWithNotify(x, y, z, 4, 2);
		}
	}
	
	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int meta) {

		if(dropInv) {
			ISidedInventory sided = (ISidedInventory) world.getTileEntity(x, y, z);
			Random rand = world.rand;
	
			if(sided != null) {
				for(int i1 = 0; i1 < sided.getSizeInventory(); ++i1) {
					ItemStack itemstack = sided.getStackInSlot(i1);
	
					if(itemstack != null) {
						float f = rand.nextFloat() * 0.8F + 0.1F;
						float f1 = rand.nextFloat() * 0.8F + 0.1F;
						float f2 = rand.nextFloat() * 0.8F + 0.1F;
	
						while(itemstack.stackSize > 0) {
							int j1 = rand.nextInt(21) + 10;
	
							if(j1 > itemstack.stackSize) {
								j1 = itemstack.stackSize;
							}
	
							itemstack.stackSize -= j1;
							EntityItem entityitem = new EntityItem(world, x + f, y + f1, z + f2, new ItemStack(itemstack.getItem(), j1, itemstack.getItemDamage()));
	
							if(itemstack.hasTagCompound()) {
								entityitem.getEntityItem().setTagCompound((NBTTagCompound) itemstack.getTagCompound().copy());
							}
	
							float f3 = 0.05F;
							entityitem.motionX = (float) rand.nextGaussian() * f3;
							entityitem.motionY = (float) rand.nextGaussian() * f3 + 0.2F;
							entityitem.motionZ = (float) rand.nextGaussian() * f3;
							world.spawnEntityInWorld(entityitem);
						}
					}
				}
	
				world.func_147453_f(x, y, z, block);
			}
		}

		super.breakBlock(world, x, y, z, block, meta);
	}

	@Override
	public Item getItemDropped(int i, Random rand, int j) {
		return null;
	}
}
