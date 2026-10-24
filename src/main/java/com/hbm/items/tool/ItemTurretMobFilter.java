package com.hbm.items.tool;

import java.util.List;

import com.hbm.blocks.BlockDummyable;
import com.hbm.inventory.gui.GUITurretMobFilter;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.turret.TileEntityTurretBaseNT;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

public class ItemTurretMobFilter extends Item implements IGUIProvider {
	private TileEntityTurretBaseNT turret;

	public ItemTurretMobFilter() {
		this.setMaxStackSize(1);
	}

	@Override 
	public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float fX, float fY, float fZ) {
		if(!player.isSneaking()) {
			return false;
		}

		Block b = world.getBlock(x, y, z);

		if(b instanceof BlockDummyable) {
			int[] corePos = ((BlockDummyable) b).findCore(world, x, y, z);

			if(corePos == null) return false;

			TileEntity tile = world.getTileEntity(corePos[0], corePos[1], corePos[2]);

			if(tile instanceof TileEntityTurretBaseNT) {
				this.turret = (TileEntityTurretBaseNT) tile;
				player.openGui(MainRegistry.instance, 0, world, 0, -1, 0);
				return true;
			}
		}

		return false;
	}

	@Override 
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return null;
	}

	@Override 
	@SideOnly(Side.CLIENT) 
	public Object provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUITurretMobFilter(turret);
	}

	@Override 
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean ext) {
		list.add(EnumChatFormatting.GRAY + "Shift-click on turret to open mob filter list");
	}
}
