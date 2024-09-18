package com.hbm.tileentity.bomb;

import com.hbm.entity.effect.EntityNukeTorex;
import com.hbm.entity.logic.EntityBalefire;
import com.hbm.inventory.container.ContainerNukeFstbmb;
import com.hbm.inventory.gui.GUINukeFstbmb;
import com.hbm.items.ModItems;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachineBase;

import api.hbm.energymk2.IBatteryItem;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class TileEntityNukeBalefire extends TileEntityMachineBase implements IGUIProvider {

	public boolean loaded;
	public boolean started;
	public int timer;

	public TileEntityNukeBalefire() {
		super(2);
		timer = 18000;
	}

	@Override
	public String getName() {
		return "container.nukeFstbmb";
	}

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
			if(!this.isLoaded()) {
				started = false;
			}
			
			if(started) {
				timer--;
				
				if(timer % 20 == 0)
					worldObj.playSoundEffect(xCoord, yCoord, zCoord, "hbm:weapon.fstbmbPing", 5.0F, 1.0F);
			}
			
			if(timer <= 0) {
				explode();
			}
			
			NBTTagCompound data = new NBTTagCompound();
			data.setInteger("timer", timer);
			data.setBoolean("loaded", this.isLoaded());
			data.setBoolean("started", started);
			networkPack(data, 250);
		}
	}
	
	public void networkUnpack(NBTTagCompound data) {
		super.networkUnpack(data);
		timer = data.getInteger("timer");
		started = data.getBoolean("started");
		loaded = data.getBoolean("loaded");
	}
	
	public void handleButtonPacket(int value, int meta) {
		
		if(meta == 0 && this.isLoaded()) {
			worldObj.playSoundEffect(xCoord, yCoord, zCoord, "hbm:weapon.fstbmbStart", 5.0F, 1.0F);
			started = true;
		}
		
		if(meta == 1)
			timer = value * 20;
	}
	
	public boolean isLoaded() {
		
		return hasEgg() && hasBattery();
	}
	
	public boolean hasEgg() {
		
		if(slots[0] != null && slots[0].getItem() == ModItems.egg_balefire) {
			return true;
		}
		
		return false;
	}
	
	public boolean hasBattery() {
		
		return getBattery() > 0;
	}
	
	public int getBattery() {
		
		if(slots[1] != null && slots[1].getItem() == ModItems.battery_spark &&
				((IBatteryItem)ModItems.battery_spark).getCharge(slots[1]) == ((IBatteryItem)ModItems.battery_spark).getMaxCharge(slots[1])) {
			return 1;
		}
		
		if(slots[1] != null && slots[1].getItem() == ModItems.battery_trixite &&
				((IBatteryItem)ModItems.battery_trixite).getCharge(slots[1]) == ((IBatteryItem)ModItems.battery_trixite).getMaxCharge(slots[1])) {
			return 2;
		}
		
		return 0;
	}
	
	public void explode() {
		
		for(int i = 0; i < slots.length; i++)
			slots[i] = null;
		
		worldObj.func_147480_a(xCoord, yCoord, zCoord, false);
		
		EntityBalefire bf = new EntityBalefire(worldObj);
		bf.posX = xCoord + 0.5;
		bf.posY = yCoord + 0.5;
		bf.posZ = zCoord + 0.5;
		bf.destructionRange = (int) 250;
		worldObj.spawnEntityInWorld(bf);
		EntityNukeTorex.statFacBale(worldObj, xCoord + 0.5, yCoord + 0.5, zCoord + 0.5, 250);
	}
	
	public String getMinutes() {
		
		String mins = "" + (timer / 1200);
		
		if(mins.length() == 1)
			mins = "0" + mins;
		
		return mins;
	}
	
	public String getSeconds() {
		
		String mins = "" + ((timer / 20) % 60);
		
		if(mins.length() == 1)
			mins = "0" + mins;
		
		return mins;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		
		started = nbt.getBoolean("started");
		timer = nbt.getInteger("timer");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
		nbt.setBoolean("started", started);
		nbt.setInteger("timer", timer);
	}
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return TileEntity.INFINITE_EXTENT_AABB;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared()
	{
		return 65536.0D;
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerNukeFstbmb(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Object provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUINukeFstbmb(player.inventory, this);
	}
}
