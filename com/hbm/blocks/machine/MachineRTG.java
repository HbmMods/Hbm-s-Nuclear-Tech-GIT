package com.hbm.blocks.machine;

import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.lib.RefStrings;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.TileEntityMachineRTG;
import com.hbm.tileentity.TileEntityRtgFurnace;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class MachineRTG extends BlockContainer {

	private static boolean keepInventory;
	private final Random field_149933_a = new Random();
	private Random rand;
	
	@SideOnly(Side.CLIENT)
	//private IIcon iconFront;
	private IIcon iconTop;
	private IIcon iconBottom;

    public MachineRTG(Material p_i45386_1_) {
		super(p_i45386_1_);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		
		String s;
		//if(this == ModBlocks.machine_rtg_blue)
		//	s = "blue";
		/*else*/ if(this == ModBlocks.machine_rtg_cyan)
			s = "cyan";
		//else if(this == ModBlocks.machine_rtg_green)
		//	s = "green";
		else if(this == ModBlocks.machine_rtg_grey)
			s = "grey";
		//else if(this == ModBlocks.machine_rtg_orange)
		//	s = "orange";
		//else if(this == ModBlocks.machine_rtg_purple)
		//	s = "purple";
		//else if(this == ModBlocks.machine_rtg_red)
		//	s = "red";
		//else if(this == ModBlocks.machine_rtg_yellow)
		//	s = "yellow";
		else 
			s = "null";
		
		this.iconTop = iconRegister.registerIcon(RefStrings.MODID + (":machine_rtg_top_" + s));
		//this.iconFront = iconRegister.registerIcon(RefStrings.MODID + (":reactor_front"));
		this.iconBottom = iconRegister.registerIcon(RefStrings.MODID + (":red_wire_coated"));
		this.blockIcon = iconRegister.registerIcon(RefStrings.MODID + ":machine_rtg_side_" + s);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int metadata) {
		return side == 1 ? this.iconTop : (side == 0 ? this.iconBottom : this.blockIcon);
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		if(this == ModBlocks.machine_rtg_grey)
			return new TileEntityMachineRTG();
		if(this == ModBlocks.machine_rtg_cyan)
			return null;
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
					FMLNetworkHandler.openGui(player, MainRegistry.instance, ModBlocks.guiID_machine_rtg, world, x, y, z);
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
