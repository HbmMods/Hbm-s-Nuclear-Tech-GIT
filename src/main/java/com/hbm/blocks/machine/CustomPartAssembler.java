package com.hbm.blocks.machine;

import com.hbm.lib.RefStrings;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.machine.TileEntityCustomPartAssembler;

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

public class CustomPartAssembler extends BlockContainer {

	public CustomPartAssembler(Material mat) {
		super(mat);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int type) {
		return new TileEntityCustomPartAssembler();
	}
	
	@SideOnly(Side.CLIENT)
	private IIcon iconTop;
	private IIcon iconBottom;

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg) {
		this.blockIcon = reg.registerIcon(RefStrings.MODID + ":block_steel_machine");
		this.iconTop = reg.registerIcon(RefStrings.MODID + ":block_steel");
		//this.blockIcon = reg.registerIcon(RefStrings.MODID + ":custom_part_assembler_side");
		//this.iconTop = reg.registerIcon(RefStrings.MODID + ":custom_part_assembler_top");
		this.iconBottom = reg.registerIcon(RefStrings.MODID + ":block_steel");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int metadata) {
		
		switch(side) {
			case 0:
				return iconBottom;
			case 1:
				return iconTop;
			default:
				return blockIcon;
		}
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if(world.isRemote) {
			return true;
		} else if(!player.isSneaking()) {
			FMLNetworkHandler.openGui(player, MainRegistry.instance, 0, world, x, y, z);
			return true;
		} else {
			return true;
		}
	}
	
	@Override
	public void breakBlock(World world, int x, int y, int z, Block b, int i) {

		TileEntity te = world.getTileEntity(x, y, z);

		if(te instanceof ISidedInventory) {

			ISidedInventory sidedinv = (ISidedInventory) te;

			if(sidedinv != null) {
				for(int i1 = 0; i1 < sidedinv.getSizeInventory(); ++i1) {
					ItemStack itemstack = sidedinv.getStackInSlot(i1);

					if(itemstack != null) {
						float f = world.rand.nextFloat() * 0.8F + 0.1F;
						float f1 = world.rand.nextFloat() * 0.8F + 0.1F;
						float f2 = world.rand.nextFloat() * 0.8F + 0.1F;

						while(itemstack.stackSize > 0) {
							int j1 = world.rand.nextInt(21) + 10;

							if(j1 > itemstack.stackSize) {
								j1 = itemstack.stackSize;
							}

							itemstack.stackSize -= j1;
							EntityItem entityitem = new EntityItem(world, x + f, y + f1, z + f2, new ItemStack(itemstack.getItem(), j1, itemstack.getItemDamage()));

							if(itemstack.hasTagCompound()) {
								entityitem.getEntityItem().setTagCompound((NBTTagCompound) itemstack.getTagCompound().copy());
							}

							float f3 = 0.05F;
							entityitem.motionX = (float) world.rand.nextGaussian() * f3;
							entityitem.motionY = (float) world.rand.nextGaussian() * f3 + 0.2F;
							entityitem.motionZ = (float) world.rand.nextGaussian() * f3;
							world.spawnEntityInWorld(entityitem);
						}
					}
				}

				world.func_147453_f(x, y, z, b);
			}
		}

		super.breakBlock(world, x, y, z, b, i);
	}
}
