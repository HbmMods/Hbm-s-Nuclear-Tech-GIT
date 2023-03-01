package com.hbm.blocks.machine;

import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.machine.TileEntityMachineRTG;

import cpw.mods.fml.client.registry.RenderingRegistry;
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

public class MachineRTG extends BlockContainer {

	private static boolean keepInventory;
	private final Random field_149933_a = new Random();

    public MachineRTG(Material p_i45386_1_) {
		super(p_i45386_1_);
	}
    
    public static int renderID = RenderingRegistry.getNextAvailableRenderId();

	@Override
	public int getRenderType() {
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
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		
		if(this == ModBlocks.machine_rtg_grey)
			return new TileEntityMachineRTG();
		
		return null;
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if(world.isRemote)
		{
			return true;
		} else if(!player.isSneaking())
		{
			if(this == ModBlocks.machine_rtg_grey) {
				TileEntityMachineRTG entity = (TileEntityMachineRTG) world.getTileEntity(x, y, z);
				if(entity != null)
				{
					FMLNetworkHandler.openGui(player, MainRegistry.instance, 0, world, x, y, z);
				}
			}
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public void breakBlock(World p_149749_1_, int p_149749_2_, int p_149749_3_, int p_149749_4_, Block p_149749_5_, int p_149749_6_)
    {
        if (!keepInventory)
        {
			if (this == ModBlocks.machine_rtg_grey) {
				TileEntityMachineRTG tileentityfurnace = (TileEntityMachineRTG) p_149749_1_.getTileEntity(p_149749_2_,
						p_149749_3_, p_149749_4_);

				if (tileentityfurnace != null) {
					for (int i1 = 0; i1 < tileentityfurnace.getSizeInventory(); ++i1) {
						ItemStack itemstack = tileentityfurnace.getStackInSlot(i1);

						if (itemstack != null) {
							float f = this.field_149933_a.nextFloat() * 0.8F + 0.1F;
							float f1 = this.field_149933_a.nextFloat() * 0.8F + 0.1F;
							float f2 = this.field_149933_a.nextFloat() * 0.8F + 0.1F;

							while (itemstack.stackSize > 0) {
								int j1 = this.field_149933_a.nextInt(21) + 10;

								if (j1 > itemstack.stackSize) {
									j1 = itemstack.stackSize;
								}

								itemstack.stackSize -= j1;
								EntityItem entityitem = new EntityItem(p_149749_1_, p_149749_2_ + f, p_149749_3_ + f1,
										p_149749_4_ + f2,
										new ItemStack(itemstack.getItem(), j1, itemstack.getItemDamage()));

								if (itemstack.hasTagCompound()) {
									entityitem.getEntityItem()
											.setTagCompound((NBTTagCompound) itemstack.getTagCompound().copy());
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
		}

        super.breakBlock(p_149749_1_, p_149749_2_, p_149749_3_, p_149749_4_, p_149749_5_, p_149749_6_);
    }
}
