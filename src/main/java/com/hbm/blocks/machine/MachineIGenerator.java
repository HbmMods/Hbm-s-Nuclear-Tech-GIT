package com.hbm.blocks.machine;

import java.util.ArrayList;
import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.handler.MultiblockHandler;
import com.hbm.interfaces.IMultiblock;
import com.hbm.items.ModItems;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.machine.TileEntityDummy;
import com.hbm.tileentity.machine.TileEntityMachineIGenerator;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class MachineIGenerator extends BlockContainer implements IMultiblock {

	public MachineIGenerator(Material p_i45386_1_) {
		super(p_i45386_1_);
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileEntityMachineIGenerator();

	}

	@Override
    public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
		
        ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
        
        //front
        ret.add(new ItemStack(ModItems.ingot_steel, 3));
        ret.add(new ItemStack(ModItems.plate_steel, 6));
        ret.add(new ItemStack(ModItems.tank_steel, 4));
        ret.add(new ItemStack(ModItems.turbine_titanium, 1));
        ret.add(new ItemStack(ModItems.wire_red_copper, 6));
        ret.add(new ItemStack(ModItems.wire_gold, 4));
        
        //body
        ret.add(new ItemStack(ModItems.wire_gold, 42));
        ret.add(new ItemStack(Items.iron_ingot, 6));
        ret.add(new ItemStack(ModItems.ingot_steel, 3));
        
        ret.add(new ItemStack(ModItems.plate_iron, 1));
        ret.add(new ItemStack(ModItems.wire_gold, 42));
        ret.add(new ItemStack(ModItems.ingot_steel, 3));
        
        //rotor
        ret.add(new ItemStack(ModItems.wire_gold, 42));
        ret.add(new ItemStack(Items.iron_ingot, 6));
        ret.add(new ItemStack(ModItems.ingot_steel, 3));

        ret.add(new ItemStack(ModItems.ingot_steel, 6));
        ret.add(new ItemStack(ModItems.board_copper, 4));
        ret.add(new ItemStack(ModItems.wire_gold, 8));
        ret.add(new ItemStack(ModBlocks.red_wire_coated, 2));
        ret.add(new ItemStack(ModItems.pedestal_steel, 2));
        ret.add(new ItemStack(ModItems.circuit_copper, 4));
        
        return ret;
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
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack itemStack) {
		int i = MathHelper.floor_double(player.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;

		if (i == 0) {
			world.setBlockMetadataWithNotify(x, y, z, 5, 2);
			if(MultiblockHandler.checkSpace(world, x, y, z, MultiblockHandler.iGenDimensionEast)) {
				MultiblockHandler.fillUp(world, x, y, z, MultiblockHandler.iGenDimensionEast, ModBlocks.dummy_block_igenerator);
				
				//
				DummyBlockIGenerator.safeBreak = true;
				world.setBlock(x + 2, y, z, ModBlocks.dummy_port_igenerator);
				TileEntity te = world.getTileEntity(x + 2, y, z);
				if(te instanceof TileEntityDummy) {
					TileEntityDummy dummy = (TileEntityDummy)te;
					dummy.targetX = x;
					dummy.targetY = y;
					dummy.targetZ = z;
				}
				world.setBlock(x - 3, y, z, ModBlocks.dummy_port_igenerator);
				TileEntity te2 = world.getTileEntity(x - 3, y, z);
				if(te2 instanceof TileEntityDummy) {
					TileEntityDummy dummy = (TileEntityDummy)te2;
					dummy.targetX = x;
					dummy.targetY = y;
					dummy.targetZ = z;
				}
				DummyBlockIGenerator.safeBreak = false;
				//
				
			} else
				world.func_147480_a(x, y, z, true);
		}
		if (i == 1) {
			world.setBlockMetadataWithNotify(x, y, z, 3, 2);
			if(MultiblockHandler.checkSpace(world, x, y, z, MultiblockHandler.iGenDimensionSouth)) {
				MultiblockHandler.fillUp(world, x, y, z, MultiblockHandler.iGenDimensionSouth, ModBlocks.dummy_block_igenerator);
				
				//
				DummyBlockIGenerator.safeBreak = true;
				world.setBlock(x, y, z + 2, ModBlocks.dummy_port_igenerator);
				TileEntity te = world.getTileEntity(x, y, z + 2);
				if(te instanceof TileEntityDummy) {
					TileEntityDummy dummy = (TileEntityDummy)te;
					dummy.targetX = x;
					dummy.targetY = y;
					dummy.targetZ = z;
				}
				world.setBlock(x, y, z - 3, ModBlocks.dummy_port_igenerator);
				TileEntity te2 = world.getTileEntity(x, y, z - 3);
				if(te2 instanceof TileEntityDummy) {
					TileEntityDummy dummy = (TileEntityDummy)te2;
					dummy.targetX = x;
					dummy.targetY = y;
					dummy.targetZ = z;
				}
				DummyBlockIGenerator.safeBreak = false;
				//
				
			} else
				world.func_147480_a(x, y, z, true);
		}
		if (i == 2) {
			world.setBlockMetadataWithNotify(x, y, z, 4, 2);
			if(MultiblockHandler.checkSpace(world, x, y, z, MultiblockHandler.iGenDimensionWest)) {
				MultiblockHandler.fillUp(world, x, y, z, MultiblockHandler.iGenDimensionWest, ModBlocks.dummy_block_igenerator);
				
				//
				DummyBlockIGenerator.safeBreak = true;
				world.setBlock(x + 3, y, z, ModBlocks.dummy_port_igenerator);
				TileEntity te = world.getTileEntity(x + 3, y, z);
				if(te instanceof TileEntityDummy) {
					TileEntityDummy dummy = (TileEntityDummy)te;
					dummy.targetX = x;
					dummy.targetY = y;
					dummy.targetZ = z;
				}
				world.setBlock(x - 2, y, z, ModBlocks.dummy_port_igenerator);
				TileEntity te2 = world.getTileEntity(x - 2, y, z);
				if(te2 instanceof TileEntityDummy) {
					TileEntityDummy dummy = (TileEntityDummy)te2;
					dummy.targetX = x;
					dummy.targetY = y;
					dummy.targetZ = z;
				}
				DummyBlockIGenerator.safeBreak = false;
				//
				
			} else
				world.func_147480_a(x, y, z, true);
		}
		if (i == 3) {
			world.setBlockMetadataWithNotify(x, y, z, 2, 2);
			if(MultiblockHandler.checkSpace(world, x, y, z, MultiblockHandler.iGenDimensionNorth)) {
				MultiblockHandler.fillUp(world, x, y, z, MultiblockHandler.iGenDimensionNorth, ModBlocks.dummy_block_igenerator);
				
				//
				DummyBlockIGenerator.safeBreak = true;
				world.setBlock(x, y, z + 3, ModBlocks.dummy_port_igenerator);
				TileEntity te = world.getTileEntity(x, y, z + 3);
				if(te instanceof TileEntityDummy) {
					TileEntityDummy dummy = (TileEntityDummy)te;
					dummy.targetX = x;
					dummy.targetY = y;
					dummy.targetZ = z;
				}
				world.setBlock(x, y, z - 2, ModBlocks.dummy_port_igenerator);
				TileEntity te2 = world.getTileEntity(x, y, z - 2);
				if(te2 instanceof TileEntityDummy) {
					TileEntityDummy dummy = (TileEntityDummy)te2;
					dummy.targetX = x;
					dummy.targetY = y;
					dummy.targetZ = z;
				}
				DummyBlockIGenerator.safeBreak = false;
				//
				
			} else
				world.func_147480_a(x, y, z, true);
		}
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		
		if(world.isRemote) {
			return true;
		} else if(!player.isSneaking()) {
			
			player.addChatComponentMessage(new ChatComponentText("The IGen has been retired, you may break it for recycling."));
			return true;
		} else {
			return false;
		}
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
}
