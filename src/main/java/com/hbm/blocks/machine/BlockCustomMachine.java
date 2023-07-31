package com.hbm.blocks.machine;

import java.util.ArrayList;
import java.util.Random;

import com.hbm.config.CustomMachineConfigJSON;
import com.hbm.config.CustomMachineConfigJSON.MachineConfiguration;
import com.hbm.items.ModItems;
import com.hbm.lib.RefStrings;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.machine.TileEntityCustomMachine;

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
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class BlockCustomMachine extends BlockContainer {
	
	@SideOnly(Side.CLIENT)
	private IIcon iconFront;

	public BlockCustomMachine() {
		super(Material.iron);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityCustomMachine();
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		this.iconFront = iconRegister.registerIcon(RefStrings.MODID + ":cm_terminal_front");
		this.blockIcon = iconRegister.registerIcon(RefStrings.MODID + ":cm_terminal_side");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int metadata) {
		if(metadata >= 100) return side == 3 ? this.iconFront : this.blockIcon;
		return metadata == 0 && side == 3 ? this.iconFront : (side == metadata ? this.iconFront : this.blockIcon);
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		
		if(world.isRemote) {
			return true;
		} else if(!player.isSneaking()) {
			
			TileEntityCustomMachine tile = (TileEntityCustomMachine) world.getTileEntity(x, y, z);
			
			if(tile != null) {
				
				if(tile.checkStructure()) {
					FMLNetworkHandler.openGui(player, MainRegistry.instance, 0, world, x, y, z);
				} else if(player.getHeldItem() != null && player.getHeldItem().getItem() == ModItems.wand_s) {
					tile.buildStructure();
				}
			}
			return true;
		}
		
		return false;
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack stack) {
		int i = MathHelper.floor_double(player.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;

		if(i == 0) world.setBlockMetadataWithNotify(x, y, z, 2, 2);
		if(i == 1) world.setBlockMetadataWithNotify(x, y, z, 5, 2);
		if(i == 2) world.setBlockMetadataWithNotify(x, y, z, 3, 2);
		if(i == 3) world.setBlockMetadataWithNotify(x, y, z, 4, 2);
		
		TileEntityCustomMachine tile = (TileEntityCustomMachine) world.getTileEntity(x, y, z);
		
		if(tile != null) {
			int id = stack.getItemDamage() - 100;
			
			if(id >= 0 && id < CustomMachineConfigJSON.customMachines.size()) {
				
				MachineConfiguration config = CustomMachineConfigJSON.niceList.get(id);
				
				if(config != null) {
					tile.machineType = config.unlocalizedName;
					tile.init();
					tile.markChanged();
				}
			}
		}
	}

	@Override
	public void onBlockHarvested(World world, int x, int y, int z, int meta, EntityPlayer player) {
		
		if(!player.capabilities.isCreativeMode) {
			harvesters.set(player);
			this.dropBlockAsItem(world, x, y, z, meta, 0);
			harvesters.set(null);
		}
	}
	
	@Override
	public void harvestBlock(World world, EntityPlayer player, int x, int y, int z, int meta) {
		player.addStat(StatList.mineBlockStatArray[getIdFromBlock(this)], 1);
		player.addExhaustion(0.025F);
	}
	
	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
		
		ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
		Item item = getItemDropped(metadata, world.rand, fortune);
		if(item != null) {

			TileEntityCustomMachine tile = (TileEntityCustomMachine) world.getTileEntity(x, y, z);
			
			if(tile != null) {
				ItemStack stack = new ItemStack(item, 1, CustomMachineConfigJSON.niceList.indexOf(tile.config) + 100);
				ret.add(stack);
			}
		}
		
		return ret;
	}
	
	@Override
	public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z) { //using the deprecated one to make NEI happy
		
		TileEntityCustomMachine tile = (TileEntityCustomMachine) world.getTileEntity(x, y, z);
		
		if(tile != null && tile.machineType != null && !tile.machineType.isEmpty()) {
			ItemStack stack = new ItemStack(this, 1, CustomMachineConfigJSON.niceList.indexOf(tile.config) + 100);
			return stack;
		}
		
		return super.getPickBlock(target, world, x, y, z);
	}
	
	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int meta) {

		ISidedInventory sided = (ISidedInventory) world.getTileEntity(x, y, z);
		Random rand = world.rand;

		if(sided != null) {
			for(int i1 = 0; i1 < sided.getSizeInventory(); ++i1) {

				if(i1 >= 10 && i1 <= 15)
					continue; // do NOT drop the filters

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

		super.breakBlock(world, x, y, z, block, meta);
	}
}
