package com.hbm.blocks.machine;

import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.items.ModItems;
import com.hbm.lib.RefStrings;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.machine.TileEntityMachineTeleporter;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class MachineTeleporter extends BlockContainer {

	@SideOnly(Side.CLIENT)
	private IIcon iconTop;
	private IIcon iconBottom;

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		this.iconTop = iconRegister.registerIcon(RefStrings.MODID + ":teleporter_top");
		this.iconBottom = iconRegister.registerIcon(RefStrings.MODID + ":teleporter_bottom");
		this.blockIcon = iconRegister.registerIcon(RefStrings.MODID + ":teleporter_side");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int metadata) {
		return side == 1 ? this.iconTop : (side == 0 ? this.iconBottom : this.blockIcon);
	}

	@Override
	public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_) {
		return Item.getItemFromBlock(ModBlocks.machine_teleporter);
	}

	public MachineTeleporter(Material p_i45386_1_) {
		super(p_i45386_1_);
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileEntityMachineTeleporter();
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		
		if(player.getHeldItem() != null && player.getHeldItem().getItem() == ModItems.linker) {
			return false;
			
		} else if(!player.isSneaking()) {
			TileEntityMachineTeleporter entity = (TileEntityMachineTeleporter) world.getTileEntity(x, y, z);
			if(entity != null && world.isRemote) {
				FMLNetworkHandler.openGui(player, MainRegistry.instance, ModBlocks.guiID_machine_teleporter, world, x, y, z);
			}
			return true;
		} else {
			return false;
		}
	}
}
