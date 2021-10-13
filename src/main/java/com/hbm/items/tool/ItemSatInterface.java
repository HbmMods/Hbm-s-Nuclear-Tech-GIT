package com.hbm.items.tool;

import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemSatChip;
import com.hbm.main.MainRegistry;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.SatPanelPacket;
import com.hbm.saveddata.SatelliteSavedData;
import com.hbm.saveddata.satellites.Satellite;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemSatInterface extends ItemSatChip {
	
	@SideOnly(Side.CLIENT)
	public static Satellite currentSat;

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		
		if(world.isRemote) {

			if(this == ModItems.sat_interface)
				player.openGui(MainRegistry.instance, ModItems.guiID_item_sat_interface, world, 0, 0, 0);
			if(this == ModItems.sat_coord)
				player.openGui(MainRegistry.instance, ModItems.guiID_item_sat_coord, world, 0, 0, 0);
		}
		
		return stack;
	}
	
    public void onUpdate(ItemStack stack, World world, Entity entity, int i, boolean b) {
    	
    	if(world.isRemote || !(entity instanceof EntityPlayerMP))
    		return;
    	
    	if(((EntityPlayerMP)entity).getHeldItem() != stack)
    		return;
    	
    	Satellite sat = SatelliteSavedData.getData(world).getSatFromFreq(this.getFreq(stack));
    	
    	if(sat != null && entity.ticksExisted % 2 == 0) {
    		PacketDispatcher.wrapper.sendTo(new SatPanelPacket(sat), (EntityPlayerMP) entity); //making this one sat that is static might not have been a good idea
    	}
    }

}
