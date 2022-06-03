package com.hbm.blocks.machine;

import java.util.Random;

import com.hbm.lib.RefStrings;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.machine.TileEntityMachineAutocrafter;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class MachineAutocrafter extends BlockContainer {

	@SideOnly(Side.CLIENT)
	private IIcon iconTop;
	@SideOnly(Side.CLIENT)
	private IIcon iconBottom;

	public MachineAutocrafter() {
		super(Material.iron);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		this.iconTop = iconRegister.registerIcon(RefStrings.MODID + ":machine_autocrafter_top");
		this.blockIcon = iconRegister.registerIcon(RefStrings.MODID + ":machine_autocrafter_side");
		this.iconBottom = iconRegister.registerIcon(RefStrings.MODID + ":machine_autocrafter_bottom");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int metadata) {
		return side == 1 ? this.iconTop : (side == 0 ? this.iconBottom : this.blockIcon);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityMachineAutocrafter();
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if(world.isRemote) {
			return true;
		} else if(!player.isSneaking()) {
			TileEntity entity = world.getTileEntity(x, y, z);
			if(entity instanceof TileEntityMachineAutocrafter) {
				FMLNetworkHandler.openGui(player, MainRegistry.instance, 0, world, x, y, z);
			}
			return true;
		} else {
			return false;
		}
	}

	private final Random field_149933_a = new Random();

	@Override
	public void breakBlock(World p_149749_1_, int p_149749_2_, int p_149749_3_, int p_149749_4_, Block p_149749_5_, int p_149749_6_) {
		ISidedInventory tileentityfurnace = (ISidedInventory) p_149749_1_.getTileEntity(p_149749_2_, p_149749_3_, p_149749_4_);

		if(tileentityfurnace != null) {
			
			for(int i1 = 10; i1 < tileentityfurnace.getSizeInventory(); ++i1) {
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

		super.breakBlock(p_149749_1_, p_149749_2_, p_149749_3_, p_149749_4_, p_149749_5_, p_149749_6_);
	}
}
