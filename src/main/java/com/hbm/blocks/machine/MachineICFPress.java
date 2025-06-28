package com.hbm.blocks.machine;

import java.util.List;

import com.hbm.blocks.ITooltipProvider;
import com.hbm.lib.RefStrings;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.machine.TileEntityICFPress;
import com.hbm.util.i18n.I18nUtil;

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
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class MachineICFPress extends BlockContainer implements ITooltipProvider {
	
	@SideOnly(Side.CLIENT) private IIcon iconTop;

	public MachineICFPress() {
		super(Material.iron);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityICFPress();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		this.iconTop = iconRegister.registerIcon(RefStrings.MODID + ":machine_icf_press_top");
		this.blockIcon = iconRegister.registerIcon(RefStrings.MODID + ":machine_icf_press_side");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int metadata) {
		return side == 1 ? this.iconTop : (side == 0 ? this.iconTop : this.blockIcon);
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
	
	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int meta) {

		TileEntity te = world.getTileEntity(x, y, z);
		if(!(te instanceof ISidedInventory)) return;
		ISidedInventory tileentityfurnace = (ISidedInventory) te;
		if(tileentityfurnace != null) {
			for(int i1 = 0; i1 < tileentityfurnace.getSizeInventory(); ++i1) {
				ItemStack itemstack = tileentityfurnace.getStackInSlot(i1);
				if(itemstack != null) {
					float f = world.rand.nextFloat() * 0.8F + 0.1F;
					float f1 = world.rand.nextFloat() * 0.8F + 0.1F;
					float f2 = world.rand.nextFloat() * 0.8F + 0.1F;
					while(itemstack.stackSize > 0) {
						int j1 = world.rand.nextInt(21) + 10;
						if(j1 > itemstack.stackSize) j1 = itemstack.stackSize;
						itemstack.stackSize -= j1;
						EntityItem entityitem = new EntityItem(world, x + f, y + f1, z + f2, new ItemStack(itemstack.getItem(), j1, itemstack.getItemDamage()));
						if(itemstack.hasTagCompound()) entityitem.getEntityItem().setTagCompound((NBTTagCompound) itemstack.getTagCompound().copy());
						float f3 = 0.05F;
						entityitem.motionX = (float) world.rand.nextGaussian() * f3;
						entityitem.motionY = (float) world.rand.nextGaussian() * f3 + 0.2F;
						entityitem.motionZ = (float) world.rand.nextGaussian() * f3;
						world.spawnEntityInWorld(entityitem);
					}
				}
			}
			world.func_147453_f(x, y, z, block);
		}
		super.breakBlock(world, x, y, z, block, meta);
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean ext) {
		for(String s : I18nUtil.resolveKeyArray(((Block)this).getUnlocalizedName() + ".desc")) list.add(EnumChatFormatting.YELLOW + s);
	}
}
