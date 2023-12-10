package com.hbm.blocks.machine;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.hbm.blocks.IPersistentInfoProvider;
import com.hbm.blocks.ITooltipProvider;
import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.items.machine.IItemFluidIdentifier;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.IPersistentNBT;
import com.hbm.tileentity.machine.storage.TileEntityBarrel;

import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockFluidBarrel extends BlockContainer implements ITooltipProvider, IPersistentInfoProvider {
	
	int capacity;

	public BlockFluidBarrel(Material p_i45386_1_, int capacity) {
		super(p_i45386_1_);
		this.capacity = capacity;
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileEntityBarrel(capacity);
	}
    
    public static int renderID = RenderingRegistry.getNextAvailableRenderId();
	
	@Override
	public int getRenderType(){
		return renderID;
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
		if(world.isRemote) {
			return true;
			
		} else if(!player.isSneaking()) {
			FMLNetworkHandler.openGui(player, MainRegistry.instance, 0, world, x, y, z);
			return true;
			
		} else if(player.isSneaking()){
			TileEntityBarrel mileEntity = (TileEntityBarrel) world.getTileEntity(x, y, z);

			if(player.getHeldItem() != null && player.getHeldItem().getItem() instanceof IItemFluidIdentifier) {
				FluidType type = ((IItemFluidIdentifier) player.getHeldItem().getItem()).getType(world, x, y, z, player.getHeldItem());

				mileEntity.tank.setTankType(type);
				mileEntity.markDirty();
				player.addChatComponentMessage(new ChatComponentText("Changed type to ").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.YELLOW)).appendSibling(new ChatComponentTranslation(type.getConditionalName())).appendSibling(new ChatComponentText("!")));
				}
			return true;

		}else {
			return false;
		}
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess p_149719_1_, int p_149719_2_, int p_149719_3_, int p_149719_4_) {
		float f = 0.0625F;
		this.setBlockBounds(2 * f, 0.0F, 2 * f, 14 * f, 1.0F, 14 * f);
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
		float f = 0.0625F;
		this.setBlockBounds(2 * f, 0.0F, 2 * f, 14 * f, 1.0F, 14 * f);
		return AxisAlignedBB.getBoundingBox(x + this.minX, y + this.minY, z + this.minZ, x + this.maxX, y + this.maxY, z + this.maxZ);
	}

	private final Random field_149933_a = new Random();
	public static boolean keepInventory;

	@Override
	public void breakBlock(World p_149749_1_, int p_149749_2_, int p_149749_3_, int p_149749_4_, Block p_149749_5_, int p_149749_6_) {
		if(!keepInventory) {
			ISidedInventory tileentityfurnace = (ISidedInventory) p_149749_1_.getTileEntity(p_149749_2_, p_149749_3_, p_149749_4_);

			if(tileentityfurnace != null) {
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
							EntityItem entityitem = new EntityItem(p_149749_1_, p_149749_2_ + f, p_149749_3_ + f1, p_149749_4_ + f2, new ItemStack(itemstack.getItem(), j1, itemstack.getItemDamage()));

							if(itemstack.hasTagCompound()) {
								entityitem.getEntityItem().setTagCompound((NBTTagCompound) itemstack.getTagCompound().copy());
							}

							float f3 = 0.05F;
							entityitem.motionX = (float) this.field_149933_a.nextGaussian() * f3;
							entityitem.motionY = (float) this.field_149933_a.nextGaussian() * f3 + 0.2F;
							entityitem.motionZ = (float) this.field_149933_a.nextGaussian() * f3;
							p_149749_1_.spawnEntityInWorld(entityitem);
						}
					}
				}

				p_149749_1_.func_147453_f(p_149749_2_, p_149749_3_, p_149749_4_, p_149749_5_);
			}
		}

		super.breakBlock(p_149749_1_, p_149749_2_, p_149749_3_, p_149749_4_, p_149749_5_, p_149749_6_);
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack stack) {
		super.onBlockPlacedBy(world, x, y, z, player, stack);
		IPersistentNBT.restoreData(world, x, y, z, stack);
	}
	
	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
		return IPersistentNBT.getDrops(world, x, y, z, this);
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
	public boolean hasComparatorInputOverride() {
		return true;
	}

	@Override
	public int getComparatorInputOverride(World world, int x, int y, int z, int side) {

		TileEntity te = world.getTileEntity(x, y, z);

		if(!(te instanceof TileEntityBarrel))
			return 0;

		TileEntityBarrel barrel = (TileEntityBarrel) te;
		return barrel.getComparatorPower();
	}

	@Override
	public void addInformation(ItemStack stack, NBTTagCompound persistentTag, EntityPlayer player, List list, boolean ext) {
		FluidTank tank = new FluidTank(Fluids.NONE, 0);
		tank.readFromNBT(persistentTag, "tank");
		list.add(EnumChatFormatting.YELLOW + "" + tank.getFill() + "/" + tank.getMaxFill() + "mB " + tank.getTankType().getLocalizedName());
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean ext) {
		
		if(this == ModBlocks.barrel_plastic) {
			list.add(EnumChatFormatting.AQUA + "Capacity: 12,000mB");
			list.add(EnumChatFormatting.YELLOW + "Cannot store hot fluids");
			list.add(EnumChatFormatting.YELLOW + "Cannot store corrosive fluids");
			list.add(EnumChatFormatting.YELLOW + "Cannot store antimatter");
		}
		
		if(this == ModBlocks.barrel_corroded) {
			list.add(EnumChatFormatting.AQUA + "Capacity: 6,000mB");
			list.add(EnumChatFormatting.GREEN + "Can store hot fluids");
			list.add(EnumChatFormatting.GREEN + "Can store highly corrosive fluids");
			list.add(EnumChatFormatting.YELLOW + "Cannot store antimatter");
			list.add(EnumChatFormatting.RED + "Leaky");
		}
		
		if(this == ModBlocks.barrel_iron) {
			list.add(EnumChatFormatting.AQUA + "Capacity: 8,000mB");
			list.add(EnumChatFormatting.GREEN + "Can store hot fluids");
			list.add(EnumChatFormatting.YELLOW + "Cannot store corrosive fluids properly");
			list.add(EnumChatFormatting.YELLOW + "Cannot store antimatter");
		}
		
		if(this == ModBlocks.barrel_steel) {
			list.add(EnumChatFormatting.AQUA + "Capacity: 16,000mB");
			list.add(EnumChatFormatting.GREEN + "Can store hot fluids");
			list.add(EnumChatFormatting.GREEN + "Can store corrosive fluids");
			list.add(EnumChatFormatting.YELLOW + "Cannot store highly corrosive fluids properly");
			list.add(EnumChatFormatting.YELLOW + "Cannot store antimatter");
		}
		
		if(this == ModBlocks.barrel_antimatter) {
			list.add(EnumChatFormatting.AQUA + "Capacity: 16,000mB");
			list.add(EnumChatFormatting.GREEN + "Can store hot fluids");
			list.add(EnumChatFormatting.GREEN + "Can store highly corrosive fluids");
			list.add(EnumChatFormatting.GREEN + "Can store antimatter");
		}
		
		if(this == ModBlocks.barrel_tcalloy) {
			list.add(EnumChatFormatting.AQUA + "Capacity: 24,000mB");
			list.add(EnumChatFormatting.GREEN + "Can store hot fluids");
			list.add(EnumChatFormatting.GREEN + "Can store highly corrosive fluids");
			list.add(EnumChatFormatting.YELLOW + "Cannot store antimatter");
		}
	}
}
