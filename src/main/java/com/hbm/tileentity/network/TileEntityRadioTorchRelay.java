package com.hbm.tileentity.network;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.hbm.interfaces.IControlReceiver;
import com.hbm.tileentity.TileEntityLoadedBase;
import com.hbm.tileentity.network.RTTYSystem.RTTYChannel;
import com.hbm.util.BufferUtil;
import com.hbm.util.Calculator;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

public class TileEntityRadioTorchRelay extends TileEntityLoadedBase implements IControlReceiver {

	public boolean forceUpdate = false;
	public boolean polling = false;
	public String channel = "";
	public String expression = "";
	public NBTTagCompound variables = new NBTTagCompound();
	public double lastState = 0D;

	@Override
	public void updateEntity() {

		if(!worldObj.isRemote) {
			
			if(!this.expression.isEmpty() && !this.channel.isEmpty()) {
				Set<String> vars = variables.func_150296_c();
				
				boolean sendUpdate = false;
				
				for(String freq : vars) {
					
					RTTYChannel chan = RTTYSystem.listen(worldObj, freq);
					if(chan == null) continue;
					
					String rec = "" + chan.signal;
					
					if(this.polling || this.forceUpdate || !rec.equals(variables.getString(freq))) {
						if(this.polling && chan.timeStamp < worldObj.getTotalWorldTime() - 1) rec = "0";
						variables.setString(freq, rec);
						sendUpdate = true;
					}
				}
				
				// if at least once value has changed, either through polling or actual state change, caluculate and send the result
				if(sendUpdate) {
					this.lastState = Calculator.evaluateExpression(this.generateExpression());
					RTTYSystem.broadcast(worldObj, channel, "" + this.lastState);
				}
			}

			networkPackNT(15);
		}
	}

	@Override
	public void serialize(ByteBuf buf) {
		buf.writeBoolean(polling);
		BufferUtil.writeString(buf, channel);
		BufferUtil.writeString(buf, expression);
		buf.writeDouble(lastState);
	}

	@Override
	public void deserialize(ByteBuf buf) {
		this.polling = buf.readBoolean();
		this.channel = BufferUtil.readString(buf);
		this.expression = BufferUtil.readString(buf);
		this.lastState = buf.readDouble();
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		
		this.polling = nbt.getBoolean("p");
		this.channel = nbt.getString("c");
		this.expression = nbt.getString("e");
		this.lastState = nbt.getDouble("l");
		
		this.variables = nbt.getCompoundTag("v");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);

		nbt.setBoolean("p", polling);
		nbt.setString("c", channel);
		nbt.setString("e", expression);
		nbt.setDouble("l", lastState);
		
		nbt.setTag("v", variables);
	}

	public void setChannel(String channel) {
		if(this.channel.equals(channel)) return;

		this.channel = channel;
		this.forceUpdate = true;
	}
	
	public void setExpression(String expression) {
		if(this.expression.equals(expression)) return;
		
		this.expression = expression;
		this.forceUpdate = true;
		
		this.variables = new NBTTagCompound();
		
		List<String> vars = getVariablesFromExpression();
		for(String var : vars) this.variables.setString(var, "");
	}

	@Override
	public boolean hasPermission(EntityPlayer player) {
		return player.getDistance(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5) < 16D;
	}

	@Override
	public void receiveControl(EntityPlayer player, NBTTagCompound data) {
		if(data.hasKey("p")) this.polling = data.getBoolean("p");
		if(data.hasKey("e")) this.setExpression(data.getString("e"));
		if(data.hasKey("c")) this.setChannel(data.getString("c"));
		
		this.markDirty();
	}
	
	/** Substitutes all varnames with the actual variable values */
	public String generateExpression() {
		if(!expression.contains("$")) return expression;
		
		StringBuilder joined = new StringBuilder("");
		StringBuilder var = new StringBuilder("");
		boolean readingVar = false;
		
		for(char c : expression.toCharArray()) {
			
			if(c == '$') {
				if(!readingVar) {
					readingVar = true;
				} else {
					String varName = var.toString();
					String variable = variables.getString(varName);
					if(variable.isEmpty()) variable = "0";
					joined.append(variable);
					var.delete(0, var.length());
					readingVar = false;
				}
			} else {
				if(readingVar) {
					var.append(c);
				} else {
					joined.append(c);
				}
			}
		}
		
		return joined.toString();
	}
	
	/** Extracts all the variable names from the current expression */
	public List<String> getVariablesFromExpression() {
		List<String> vars = new ArrayList();
		if(!expression.contains("$")) return vars;
		
		StringBuilder var = new StringBuilder("");
		boolean readingVar = false;
		
		for(char c : expression.toCharArray()) {
			
			if(c == '$') {
				if(!readingVar) {
					readingVar = true;
				} else {
					String varName = var.toString();
					vars.add(varName);
					var.delete(0, var.length());
					readingVar = false;
				}
			} else {
				if(readingVar) {
					var.append(c);
				}
			}
		}
		
		return vars;
	}
}
