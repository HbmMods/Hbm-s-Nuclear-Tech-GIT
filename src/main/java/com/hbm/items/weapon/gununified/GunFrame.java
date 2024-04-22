package com.hbm.items.weapon.gununified;

import java.util.ArrayList;
import java.util.List;

import com.hbm.util.Tuple.Pair;
import com.hbm.util.Tuple.Triplet;

import api.hbm.item.IClickReceiver;
import api.hbm.item.IGunHUDProvider;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

@Deprecated
public abstract class GunFrame extends Item implements IGunHUDProvider, IClickReceiver {
	
	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int slot, boolean isCurrentItem) {
		
		if(entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) entity;
			
			if(world.isRemote) {
				this.updatePlayerClient(stack, world, player, slot, isCurrentItem);
			} else {
				this.updatePlayerServer(stack, world, player, slot, isCurrentItem);
			}
		}
	}
	
	public void updatePlayerClient(ItemStack stack, World world, EntityPlayer player, int slot, boolean isCurrentItem) { }
	public void updatePlayerServer(ItemStack stack, World world, EntityPlayer player, int slot, boolean isCurrentItem) { }

	@Override
	@SideOnly(Side.CLIENT)
	public boolean handleMouseInput(ItemStack stack, EntityPlayer player, int button, boolean state) {
		return false;
	}

	@Override
	public List<Triplet<Double, Integer, Integer>> getStatusBars(ItemStack stack, EntityPlayer player) {
		return new ArrayList();
	}

	@Override
	public List<Pair<IIcon, String>> getAmmoInfo(ItemStack stack, EntityPlayer player) {
		return null;
	}
	
	public static double getDurabilityBar(ItemStack stack) {
		return 1.0D;
	}
}
