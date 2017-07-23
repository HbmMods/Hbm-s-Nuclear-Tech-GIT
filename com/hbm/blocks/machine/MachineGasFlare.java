package com.hbm.blocks.machine;

import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.handler.MultiblockHandler;
import com.hbm.interfaces.IMultiblock;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.TileEntityDummy;
import com.hbm.tileentity.TileEntityMachineGasFlare;
import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class MachineGasFlare extends BlockContainer implements IMultiblock {

    private final Random field_149933_a = new Random();
	private Random rand;
	private static boolean keepInventory;

	public MachineGasFlare(Material p_i45386_1_) {
		super(p_i45386_1_);
		rand = new Random();
	}
	
	@Override
	public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_)
    {
        return Item.getItemFromBlock(ModBlocks.machine_flare);
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
			TileEntityMachineGasFlare entity = (TileEntityMachineGasFlare) world.getTileEntity(x, y, z);
			if(entity != null)
			{
				FMLNetworkHandler.openGui(player, MainRegistry.instance, ModBlocks.guiID_machine_flare, world, x, y, z);
			}
			return true;
		} else {
			return false;
		}
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileEntityMachineGasFlare();
	}
	
	@Override
	public void breakBlock(World p_149749_1_, int p_149749_2_, int p_149749_3_, int p_149749_4_, Block p_149749_5_, int p_149749_6_)
    {
        if (!keepInventory)
        {
        	TileEntityMachineGasFlare tileentityfurnace = (TileEntityMachineGasFlare)p_149749_1_.getTileEntity(p_149749_2_, p_149749_3_, p_149749_4_);

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
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack itemStack) {

		if(MultiblockHandler.checkSpace(world, x, y, z, MultiblockHandler.flareDimension)) {
			MultiblockHandler.fillUp(world, x, y, z, MultiblockHandler.flareDimension, ModBlocks.dummy_block_flare);

			DummyBlockFlare.safeBreak = true;
			world.setBlock(x, y, z + 1, ModBlocks.dummy_port_flare);
			TileEntity te = world.getTileEntity(x, y, z + 1);
			if(te instanceof TileEntityDummy) {
				TileEntityDummy dummy = (TileEntityDummy)te;
				dummy.targetX = x;
				dummy.targetY = y;
				dummy.targetZ = z;
			}
			world.setBlock(x, y, z - 1, ModBlocks.dummy_port_flare);
			TileEntity te2 = world.getTileEntity(x, y, z - 1);
			if(te instanceof TileEntityDummy) {
				TileEntityDummy dummy = (TileEntityDummy)te2;
				dummy.targetX = x;
				dummy.targetY = y;
				dummy.targetZ = z;
			}
			world.setBlock(x + 1, y, z, ModBlocks.dummy_port_flare);
			TileEntity te3 = world.getTileEntity(x + 1, y, z);
			if(te3 instanceof TileEntityDummy) {
				TileEntityDummy dummy = (TileEntityDummy)te3;
				dummy.targetX = x;
				dummy.targetY = y;
				dummy.targetZ = z;
			}
			world.setBlock(x - 1, y, z, ModBlocks.dummy_port_flare);
			TileEntity te4 = world.getTileEntity(x - 1, y, z);
			if(te4 instanceof TileEntityDummy) {
				TileEntityDummy dummy = (TileEntityDummy)te4;
				dummy.targetX = x;
				dummy.targetY = y;
				dummy.targetZ = z;
			}
			DummyBlockFlare.safeBreak = false;
			
		} else
			world.func_147480_a(x, y, z, true);
	}
}
