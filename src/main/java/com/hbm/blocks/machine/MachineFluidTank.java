package com.hbm.blocks.machine;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.hbm.blocks.IPersistentInfoProvider;
import com.hbm.blocks.ModBlocks;
import com.hbm.handler.MultiblockHandler;
import com.hbm.interfaces.IMultiblock;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.IPersistentNBT;
import com.hbm.tileentity.machine.TileEntityDummy;
import com.hbm.tileentity.machine.storage.TileEntityMachineFluidTank;
import com.hbm.util.I18nUtil;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class MachineFluidTank extends BlockContainer implements IMultiblock, IPersistentInfoProvider {

	public MachineFluidTank(Material p_i45386_1_) {
		super(p_i45386_1_);
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileEntityMachineFluidTank();
	}

	@Override
	public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_) {
		return Item.getItemFromBlock(ModBlocks.machine_fluidtank);
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
    		TileEntityMachineFluidTank entity = (TileEntityMachineFluidTank) world.getTileEntity(x, y, z);
    		if(entity != null)
    		{
    			FMLNetworkHandler.openGui(player, MainRegistry.instance, ModBlocks.guiID_machine_fluidtank, world, x, y, z);
    		}
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack itemStack) {
		int i = MathHelper.floor_double(player.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;
		
		ItemStack drop = itemStack.copy();
		drop.stackSize = 1;

		if (i == 0) {
			world.setBlockMetadataWithNotify(x, y, z, 5, 2);
			if(MultiblockHandler.checkSpace(world, x, y, z, MultiblockHandler.fluidTankDimensionEW)) {
				MultiblockHandler.fillUp(world, x, y, z, MultiblockHandler.fluidTankDimensionEW, ModBlocks.dummy_block_fluidtank);
				
				//
				DummyBlockFluidTank.safeBreak = true;
				world.setBlock(x + 1, y, z + 1, ModBlocks.dummy_port_fluidtank);
				TileEntity te = world.getTileEntity(x + 1, y, z + 1);
				if(te instanceof TileEntityDummy) {
					TileEntityDummy dummy = (TileEntityDummy)te;
					dummy.targetX = x;
					dummy.targetY = y;
					dummy.targetZ = z;
				}
				world.setBlock(x + 1, y, z - 1, ModBlocks.dummy_port_fluidtank);
				TileEntity te1 = world.getTileEntity(x + 1, y, z - 1);
				if(te1 instanceof TileEntityDummy) {
					TileEntityDummy dummy = (TileEntityDummy)te1;
					dummy.targetX = x;
					dummy.targetY = y;
					dummy.targetZ = z;
				}
				world.setBlock(x - 1, y, z + 1, ModBlocks.dummy_port_fluidtank);
				TileEntity te2 = world.getTileEntity(x - 1, y, z + 1);
				if(te2 instanceof TileEntityDummy) {
					TileEntityDummy dummy = (TileEntityDummy)te2;
					dummy.targetX = x;
					dummy.targetY = y;
					dummy.targetZ = z;
				}
				world.setBlock(x - 1, y, z - 1, ModBlocks.dummy_port_fluidtank);
				TileEntity te3 = world.getTileEntity(x - 1, y, z - 1);
				if(te3 instanceof TileEntityDummy) {
					TileEntityDummy dummy = (TileEntityDummy)te3;
					dummy.targetX = x;
					dummy.targetY = y;
					dummy.targetZ = z;
				}
				DummyBlockFluidTank.safeBreak = false;
				//
				
			} else {
				this.dropBlockAsItem(world, x, y, z, drop);
				world.func_147480_a(x, y, z, false);
			}
		}
		if (i == 1) {
			world.setBlockMetadataWithNotify(x, y, z, 3, 2);
			if(MultiblockHandler.checkSpace(world, x, y, z, MultiblockHandler.fluidTankDimensionNS)) {
				MultiblockHandler.fillUp(world, x, y, z, MultiblockHandler.fluidTankDimensionNS, ModBlocks.dummy_block_fluidtank);

				//
				DummyBlockFluidTank.safeBreak = true;
				world.setBlock(x + 1, y, z + 1, ModBlocks.dummy_port_fluidtank);
				TileEntity te = world.getTileEntity(x + 1, y, z + 1);
				if(te instanceof TileEntityDummy) {
					TileEntityDummy dummy = (TileEntityDummy)te;
					dummy.targetX = x;
					dummy.targetY = y;
					dummy.targetZ = z;
				}
				world.setBlock(x + 1, y, z - 1, ModBlocks.dummy_port_fluidtank);
				TileEntity te1 = world.getTileEntity(x + 1, y, z - 1);
				if(te1 instanceof TileEntityDummy) {
					TileEntityDummy dummy = (TileEntityDummy)te1;
					dummy.targetX = x;
					dummy.targetY = y;
					dummy.targetZ = z;
				}
				world.setBlock(x - 1, y, z + 1, ModBlocks.dummy_port_fluidtank);
				TileEntity te2 = world.getTileEntity(x - 1, y, z + 1);
				if(te2 instanceof TileEntityDummy) {
					TileEntityDummy dummy = (TileEntityDummy)te2;
					dummy.targetX = x;
					dummy.targetY = y;
					dummy.targetZ = z;
				}
				world.setBlock(x - 1, y, z - 1, ModBlocks.dummy_port_fluidtank);
				TileEntity te3 = world.getTileEntity(x - 1, y, z - 1);
				if(te3 instanceof TileEntityDummy) {
					TileEntityDummy dummy = (TileEntityDummy)te3;
					dummy.targetX = x;
					dummy.targetY = y;
					dummy.targetZ = z;
				}
				DummyBlockFluidTank.safeBreak = false;
				//
				
			} else {
				this.dropBlockAsItem(world, x, y, z, drop);
				world.func_147480_a(x, y, z, false);
			}
		}
		if (i == 2) {
			world.setBlockMetadataWithNotify(x, y, z, 4, 2);
			if(MultiblockHandler.checkSpace(world, x, y, z, MultiblockHandler.fluidTankDimensionEW)) {
				MultiblockHandler.fillUp(world, x, y, z, MultiblockHandler.fluidTankDimensionEW, ModBlocks.dummy_block_fluidtank);

				//
				DummyBlockFluidTank.safeBreak = true;
				world.setBlock(x + 1, y, z + 1, ModBlocks.dummy_port_fluidtank);
				TileEntity te = world.getTileEntity(x + 1, y, z + 1);
				if(te instanceof TileEntityDummy) {
					TileEntityDummy dummy = (TileEntityDummy)te;
					dummy.targetX = x;
					dummy.targetY = y;
					dummy.targetZ = z;
				}
				world.setBlock(x + 1, y, z - 1, ModBlocks.dummy_port_fluidtank);
				TileEntity te1 = world.getTileEntity(x + 1, y, z - 1);
				if(te1 instanceof TileEntityDummy) {
					TileEntityDummy dummy = (TileEntityDummy)te1;
					dummy.targetX = x;
					dummy.targetY = y;
					dummy.targetZ = z;
				}
				world.setBlock(x - 1, y, z + 1, ModBlocks.dummy_port_fluidtank);
				TileEntity te2 = world.getTileEntity(x - 1, y, z + 1);
				if(te2 instanceof TileEntityDummy) {
					TileEntityDummy dummy = (TileEntityDummy)te2;
					dummy.targetX = x;
					dummy.targetY = y;
					dummy.targetZ = z;
				}
				world.setBlock(x - 1, y, z - 1, ModBlocks.dummy_port_fluidtank);
				TileEntity te3 = world.getTileEntity(x - 1, y, z - 1);
				if(te3 instanceof TileEntityDummy) {
					TileEntityDummy dummy = (TileEntityDummy)te3;
					dummy.targetX = x;
					dummy.targetY = y;
					dummy.targetZ = z;
				}
				DummyBlockFluidTank.safeBreak = false;
				//
				
			} else {
				this.dropBlockAsItem(world, x, y, z, drop);
				world.func_147480_a(x, y, z, false);
			}
		}
		if (i == 3) {
			world.setBlockMetadataWithNotify(x, y, z, 2, 2);
			if(MultiblockHandler.checkSpace(world, x, y, z, MultiblockHandler.fluidTankDimensionNS)) {
				MultiblockHandler.fillUp(world, x, y, z, MultiblockHandler.fluidTankDimensionNS, ModBlocks.dummy_block_fluidtank);

				//
				DummyBlockFluidTank.safeBreak = true;
				world.setBlock(x + 1, y, z + 1, ModBlocks.dummy_port_fluidtank);
				TileEntity te = world.getTileEntity(x + 1, y, z + 1);
				if(te instanceof TileEntityDummy) {
					TileEntityDummy dummy = (TileEntityDummy)te;
					dummy.targetX = x;
					dummy.targetY = y;
					dummy.targetZ = z;
				}
				world.setBlock(x + 1, y, z - 1, ModBlocks.dummy_port_fluidtank);
				TileEntity te1 = world.getTileEntity(x + 1, y, z - 1);
				if(te1 instanceof TileEntityDummy) {
					TileEntityDummy dummy = (TileEntityDummy)te1;
					dummy.targetX = x;
					dummy.targetY = y;
					dummy.targetZ = z;
				}
				world.setBlock(x - 1, y, z + 1, ModBlocks.dummy_port_fluidtank);
				TileEntity te2 = world.getTileEntity(x - 1, y, z + 1);
				if(te2 instanceof TileEntityDummy) {
					TileEntityDummy dummy = (TileEntityDummy)te2;
					dummy.targetX = x;
					dummy.targetY = y;
					dummy.targetZ = z;
				}
				world.setBlock(x - 1, y, z - 1, ModBlocks.dummy_port_fluidtank);
				TileEntity te3 = world.getTileEntity(x - 1, y, z - 1);
				if(te3 instanceof TileEntityDummy) {
					TileEntityDummy dummy = (TileEntityDummy)te3;
					dummy.targetX = x;
					dummy.targetY = y;
					dummy.targetZ = z;
				}
				DummyBlockFluidTank.safeBreak = false;
				//
				
			} else {
				this.dropBlockAsItem(world, x, y, z, drop);
				world.func_147480_a(x, y, z, false);
			}
		}

		IPersistentNBT.restoreData(world, x, y, z, itemStack);
	}
	
    private final Random field_149933_a = new Random();
	private static boolean keepInventory;
	
	@Override
	public void breakBlock(World p_149749_1_, int p_149749_2_, int p_149749_3_, int p_149749_4_, Block p_149749_5_, int p_149749_6_)
    {		
        if (!keepInventory)
        {
        	ISidedInventory tileentityfurnace = (ISidedInventory)p_149749_1_.getTileEntity(p_149749_2_, p_149749_3_, p_149749_4_);

            if (tileentityfurnace != null)
            {
                for (int i1 = 0; i1 < tileentityfurnace.getSizeInventory(); ++i1)
                {
                    ItemStack itemstack = tileentityfurnace.getStackInSlot(i1);

                    if (itemstack != null)
                    {
                        float f = this.field_149933_a.nextFloat() * 0.8F + 0.1F;
                        float f1 = this.field_149933_a.nextFloat() * 0.8F + 0.1F;
                        float f2 = this.field_149933_a.nextFloat() * 0.8F + 0.1F;

                        while (itemstack.stackSize > 0)
                        {
                            int j1 = this.field_149933_a.nextInt(21) + 10;

                            if (j1 > itemstack.stackSize)
                            {
                                j1 = itemstack.stackSize;
                            }

                            itemstack.stackSize -= j1;
                            EntityItem entityitem = new EntityItem(p_149749_1_, p_149749_2_ + f, p_149749_3_ + f1, p_149749_4_ + f2, new ItemStack(itemstack.getItem(), j1, itemstack.getItemDamage()));

                            if (itemstack.hasTagCompound())
                            {
                                entityitem.getEntityItem().setTagCompound((NBTTagCompound)itemstack.getTagCompound().copy());
                            }

                            float f3 = 0.05F;
                            entityitem.motionX = (float)this.field_149933_a.nextGaussian() * f3;
                            entityitem.motionY = (float)this.field_149933_a.nextGaussian() * f3 + 0.2F;
                            entityitem.motionZ = (float)this.field_149933_a.nextGaussian() * f3;
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
	
	/*
	 * Called after the block and TE are already gone, so this method is of no use to us.
	 */
	@Override
	public void harvestBlock(World world, EntityPlayer player, int x, int y, int z, int meta) {
		player.addStat(StatList.mineBlockStatArray[getIdFromBlock(this)], 1);
		player.addExhaustion(0.025F);
	}

	@Override
	public void addInformation(ItemStack stack, NBTTagCompound persistentTag, EntityPlayer player, List list, boolean ext) {
		FluidTank tank = new FluidTank(Fluids.NONE, 0, 0);
		tank.readFromNBT(persistentTag, "tank");
		list.add(EnumChatFormatting.YELLOW + "" + tank.getFill() + "/" + tank.getMaxFill() + "mB " + I18nUtil.resolveKey(tank.getTankType().getUnlocalizedName()));
	}
}
