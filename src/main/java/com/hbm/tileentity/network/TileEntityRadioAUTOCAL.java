package com.hbm.tileentity.network;

import java.util.HashMap;

import com.hbm.interfaces.IControlReceiver;
import com.hbm.inventory.gui.GUIScreenRadioAUTOCAL;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityTickingBase;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class TileEntityRadioAUTOCAL extends TileEntityTickingBase implements IControlReceiver, IGUIProvider {

	public boolean isOn = false;
	public boolean ignoreError = false;
	public boolean autoReboot = false;
	
	public Object buffer;
	public NBTTagCompound variables;
	public String[] script;
	public HashMap<String, Integer> jmp = new HashMap();

	@Override
	public String getInventoryName() {
		return "container.autocal";
	}

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			this.networkPackNT(15);
		}
	}

	@Override
	public void serialize(ByteBuf buf) {
		super.serialize(buf);
		buf.writeBoolean(isOn);
		buf.writeBoolean(ignoreError);
		buf.writeBoolean(autoReboot);
	}

	@Override
	public void deserialize(ByteBuf buf) {
		super.deserialize(buf);
		this.isOn = buf.readBoolean();
		this.ignoreError = buf.readBoolean();
		this.autoReboot = buf.readBoolean();
	}

	@Override public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) { return null; }
	@Override public Object provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) { return new GUIScreenRadioAUTOCAL(this); }

	@Override
	public boolean hasPermission(EntityPlayer player) {
		return player.getDistanceSq(xCoord + 0.5, yCoord + 1, zCoord + 0.5) <= 15 * 15;
	}

	@Override
	public void receiveControl(NBTTagCompound data) {
		if(data.hasKey("on")) this.isOn = !this.isOn;
		if(data.hasKey("ignore")) this.ignoreError = !this.ignoreError;
		if(data.hasKey("auto")) this.autoReboot = !this.autoReboot;
		
		this.markChanged();
	}
}
