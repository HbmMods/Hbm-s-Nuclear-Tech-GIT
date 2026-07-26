package com.hbm.items.tool;

import com.hbm.inventory.gui.GUIScreenSatCoord;
import com.hbm.items.IItemControlReceiver;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemSatChip;
import com.hbm.main.MainRegistry;
import com.hbm.saveddata.SatelliteSavedData;
import com.hbm.saveddata.satellites.SatelliteBase;
import com.hbm.tileentity.IGUIProvider;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class ItemSatInterface extends ItemSatChip implements IGUIProvider, IItemControlReceiver {
	
	public static final String KEY_NBT_CONNECTED = "connected";

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		if(world.isRemote) player.openGui(MainRegistry.instance, 0, world, 0, 0, 0);
		return stack;
	}

	public void onUpdate(ItemStack stack, World world, Entity entity, int i, boolean b) {

		if(world.isRemote || !(entity instanceof EntityPlayerMP))
			return;

		if(((EntityPlayerMP) entity).getHeldItem() != stack)
			return;

		SatelliteBase sat = SatelliteSavedData.getData(world).getSatFromFreq(this.getFreq(stack));
		if(!stack.hasTagCompound()) stack.stackTagCompound = new NBTTagCompound();
		
		stack.stackTagCompound.setBoolean(KEY_NBT_CONNECTED, sat != null);
	}

	@Override public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) { return null; }

	@Override
	@SideOnly(Side.CLIENT)
	public Object provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if(this == ModItems.sat_coord) return new GUIScreenSatCoord(player);
		return null;
	}

	@Override
	public void receiveControl(EntityPlayer player, ItemStack stack, NBTTagCompound data) {
		SatelliteBase sat = SatelliteSavedData.getData(player.worldObj).getSatFromFreq(this.getFreq(stack));
		if(sat != null) sat.onCoordAction(player.worldObj, player, data.getInteger("x"), data.hasKey("y") ? data.getInteger("y") : -1, data.getInteger("z"));
	}
}
