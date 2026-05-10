package com.hbm.tileentity.machine.rbmk;

import com.hbm.explosion.vanillant.ExplosionVNT;
import com.hbm.explosion.vanillant.standard.EntityProcessorCrossSmooth;
import com.hbm.explosion.vanillant.standard.ExplosionEffectWeapon;
import com.hbm.explosion.vanillant.standard.PlayerProcessorStandard;
import com.hbm.interfaces.IControlReceiver;
import com.hbm.inventory.gui.GUIScreenRBMKTerminal;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityLoadedBase;
import com.hbm.tileentity.network.RTTYSystem;
import com.hbm.util.BufferUtil;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class TileEntityRBMKTerminal extends TileEntityLoadedBase implements IGUIProvider, IControlReceiver {
	
	public String[] history = new String[17];
	public String channel = "";
	public String repeatCmd = "";
	public boolean doesRepeat = false;
	
	@Override
	public void updateEntity() {
		if(!worldObj.isRemote) {
			
			if(!this.channel.isEmpty() && !this.repeatCmd.isEmpty())
				RTTYSystem.broadcast(worldObj, this.channel, this.repeatCmd + "");
			
			this.networkPackNT(50);
		}
	}
	
	public void eval(String cmd) {
		if(cmd == null) return;
		
		push(cmd);
		if(cmd.isEmpty()) return;
		
		if(cmd.startsWith("chan ")) {
			this.channel = cmd.substring(5);
			push("Set channel to " + (this.channel.isEmpty() ? "<none>" : this.channel));
			this.markChanged();
			return;
		}
		
		if(cmd.equals("chan")) {
			this.channel = "";
			push("Set channel to <none>");
			this.markChanged();
			return;
		}
		
		if(cmd.startsWith("start ")) {
			this.repeatCmd = cmd.substring(6);
			push("Repeating signal on " + this.channel);
			this.markChanged();
			return;
		}
		
		if(cmd.equals("stop")) {
			this.repeatCmd = "";
			push("Stopping repeat signal");
			this.markChanged();
			return;
		}
		
		if(cmd.startsWith("send ")) {
			if(this.channel.isEmpty()) {
				push("Cannot send - no channel set");
				return;
			}
			RTTYSystem.broadcast(worldObj, this.channel, cmd.substring(5));
			push("Sent signal on " + this.channel);
			return;
		}
		
		if(cmd.equals("horse")) {
			push("Horse.");
			return;
		}
		
		if(cmd.equals("selfdestruct")) {
			worldObj.func_147480_a(xCoord, yCoord, zCoord, false);
			ExplosionVNT vnt = new ExplosionVNT(worldObj, xCoord + 0.5, yCoord + 0.5, zCoord + 0.5, 5, null);
			vnt.setEntityProcessor(new EntityProcessorCrossSmooth(1, 50).setupPiercing(5F, 0.5F));
			vnt.setPlayerProcessor(new PlayerProcessorStandard());
			vnt.setSFX(new ExplosionEffectWeapon(10, 2.5F, 1F));
			vnt.explode();
			return;
		}
		
		if(cmd.equals("clear")) {
			for(int i = 0; i < history.length; i++) history[i] = "";
			return;
		}
		
		push("Unrecognized command!");
	}
	
	public void push(String msg) {
		for(int i = history.length - 2; i > 0; i--) {
			history[i] = history[i - 1];
		}
		history[0] = msg;
	}

	@Override
	public void serialize(ByteBuf buf) {
		super.serialize(buf);
		buf.writeBoolean(!this.repeatCmd.isEmpty());
		for(int i = 0; i < history.length; i++) BufferUtil.writeString(buf, history[i]);
	}

	@Override
	public void deserialize(ByteBuf buf) {
		super.deserialize(buf);
		this.doesRepeat = buf.readBoolean();
		for(int i = 0; i < history.length; i++) this.history[i] = BufferUtil.readString(buf);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.channel = nbt.getString("channel");
		this.repeatCmd = nbt.getString("repeatCmd");
		for(int i = 0; i < history.length; i++) this.history[i] = nbt.getString("history" + i);
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setString("channel", channel);
		nbt.setString("repeatCmd", repeatCmd);
		for(int i = 0; i < history.length; i++) nbt.setString("history" + i, history[i]);
	}
	
	@Override
	public boolean hasPermission(EntityPlayer player) {
		return player.getDistanceSq(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5) < 15 * 15;
	}

	@Override
	public void receiveControl(NBTTagCompound data) {
		if(data.hasKey("cmd")) {
			eval(data.getString("cmd"));
			this.markChanged();
		}
	}

	@Override public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) { return null; }
	@Override public Object provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) { return new GUIScreenRBMKTerminal(this); }
}
