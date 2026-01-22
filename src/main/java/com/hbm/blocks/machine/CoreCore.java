package com.hbm.blocks.machine;

import java.util.Random;

import com.hbm.main.MainRegistry;
import com.hbm.tileentity.machine.TileEntityCore;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class CoreCore extends BlockContainer {

	public CoreCore(Material p_i45386_1_) {
		super(p_i45386_1_);
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileEntityCore();
	}
	
	@Override
	public int getRenderType(){
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
		
		if(world.isRemote) {
			return true;
		} else if(!player.isSneaking()) {
			
			FMLNetworkHandler.openGui(player, MainRegistry.instance, 0, world, x, y, z);
			return true;
			
		} else {
			return false;
		}
	}
	
	private Random rand = new Random();

	// shitty copy pasted crap for the 50th time because i hate this block
	@Override
	public void breakBlock(World world, int x, int y, int z, Block b, int m) {
		TileEntityCore core = (TileEntityCore) world.getTileEntity(x, y, z);

		if(core != null) {
			for(int i1 = 0; i1 < core.getSizeInventory(); ++i1) {
				ItemStack itemstack = core.getStackInSlot(i1);

				if(itemstack != null) {
					float f = this.rand.nextFloat() * 0.8F + 0.1F;
					float f1 = this.rand.nextFloat() * 0.8F + 0.1F;
					float f2 = this.rand.nextFloat() * 0.8F + 0.1F;

					while(itemstack.stackSize > 0) {
						int j1 = this.rand.nextInt(21) + 10;
						if(j1 > itemstack.stackSize) j1 = itemstack.stackSize;

						itemstack.stackSize -= j1;
						EntityItem entityitem = new EntityItem(world, x + f, y + f1, z + f2, new ItemStack(itemstack.getItem(), j1, itemstack.getItemDamage()));
						if(itemstack.hasTagCompound()) entityitem.getEntityItem().setTagCompound((NBTTagCompound) itemstack.getTagCompound().copy());

						float f3 = 0.05F;
						entityitem.motionX = (float) this.rand.nextGaussian() * f3;
						entityitem.motionY = (float) this.rand.nextGaussian() * f3 + 0.2F;
						entityitem.motionZ = (float) this.rand.nextGaussian() * f3;
						world.spawnEntityInWorld(entityitem);
					}
				}
			}

			world.func_147453_f(x, y, z, b);
		}

		super.breakBlock(world, x, y, z, b, m);
	}
}
