package com.hbm.tileentity.bomb;

import com.hbm.entity.effect.EntityNukeCloudSmall;
import com.hbm.entity.effect.EntityNukeTorex;
import com.hbm.entity.logic.EntityBalefire;
import com.hbm.inventory.container.ContainerNukeAntimatter;
import com.hbm.inventory.container.ContainerNukeAntimatter;
import com.hbm.inventory.gui.GUINukeAntimatter;
import com.hbm.inventory.gui.GUINukeAntimatter;
import com.hbm.items.ModItems;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachineBase;

import api.hbm.energy.IBatteryItem;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class TileEntityAntimatter extends TileEntityMachineBase implements IGUIProvider {

	public boolean loaded;
	public boolean started;
	public int timer;

	public TileEntityAntimatter() {
		super(8);
		timer = 18000;
	}

	@Override
	public String getName() {
		return "container.nukeAntimatter";
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
		
		return hasAmatCore() && hasRest();
	}
	
	public boolean hasAmatCore() {
		
		if(slots[2] != null && slots[2].getItem() == ModItems.pellet_antimatter) {
			return true;
		}
		
		return false;
	}
	
	public boolean hasRest() {
		
		return getRest() > 0;
	}
	
	public int getRest() {
		
		if(slots[0] != null && slots[1] != null && slots[3] != null && slots[4] != null && slots[1].getItem() == ModItems.particle_lead && slots[0].getItem() == ModItems.particle_lead && 
				slots[3].getItem() == ModItems.ingot_hafnium && slots[4].getItem() == ModItems.ingot_hafnium) {
			return 1;
		}
		
		return 0;
	}
	
	public void explode() {
		
		for(int i = 0; i < slots.length; i++)
			slots[i] = null;
		
		worldObj.func_147480_a(xCoord, yCoord, zCoord, false);
		
		EntityBalefire bf = new EntityBalefire(worldObj);
		bf.antimatter();
		bf.posX = xCoord + 0.5;
		bf.posY = yCoord + 0.5;
		bf.posZ = zCoord + 0.5;
		bf.destructionRange = (int) 150;
		worldObj.spawnEntityInWorld(bf);
		EntityNukeTorex.startFacAnti(worldObj, xCoord + 0.5, yCoord + 5, zCoord + 0.5, 1000);
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
		return new ContainerNukeAntimatter(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUINukeAntimatter(player.inventory, this);
	}
}
