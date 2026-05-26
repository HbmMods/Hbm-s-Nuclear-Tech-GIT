package com.hbm.tileentity.network;

import com.hbm.interfaces.IControlReceiver;
import com.hbm.inventory.gui.GUIScreenRadioAUTOCAL;
import com.hbm.module.IParse;
import com.hbm.module.IParse.EnumStatementReturn;
import com.hbm.module.IParse.ParseContext;
import com.hbm.module.ParseMSES1;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityTickingBase;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.world.World;

public class TileEntityRadioAUTOCAL extends TileEntityTickingBase implements IControlReceiver, IGUIProvider {

	public boolean isOn = false;
	public boolean ignoreError = false;
	public boolean autoReboot = false;
	
	public String[] script = new String[0];
	public IParse msesv1 = new ParseMSES1();
	public ParseContext ctx;

	@Override
	public String getInventoryName() {
		return "container.autocal";
	}

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
			if(this.worldObj.getTotalWorldTime() % 60 == 0) this.markChanged(); // ensure we're always saved to disk
			
			if(this.ctx == null) {
				this.ctx = new ParseContext(worldObj);
			}
			if(this.ctx.world != this.worldObj) this.ctx.world = this.worldObj;
			
			if(!this.isOn && this.autoReboot) {
				this.isOn = true;
			}
			
			if(this.isOn) {
				
				int emergencyBrake = 100;
				for(int i = 0; i < this.ctx.clockSpeed && emergencyBrake > 0; i++) {
					emergencyBrake--;
					
					if(this.ctx.current == this.script.length) { this.stop("Program has terminated"); break; }
					if(this.ctx.current < 0 || this.ctx.current >= this.script.length) { this.stop("Program index is out of bounds"); break; }
					
					try {
						int index = this.ctx.current;
						this.ctx.current ++;
						EnumStatementReturn ret = msesv1.eval(ctx, this.script[index]);
						if(ret == EnumStatementReturn.END_TICK) break;
						if(ret == EnumStatementReturn.SHUTDOWN) this.stop("Program requested shutdown");
						if(!this.ignoreError) {
							if(ret == EnumStatementReturn.UNRECOGNIZED_COMMAND) this.stop("Unrecognized command");
							if(ret == EnumStatementReturn.PARAMETER_ERROR) this.stop("Parameter error");
							if(ret == EnumStatementReturn.UNDEFINED) this.stop("Undefined behavior");
						}
						if(ret == EnumStatementReturn.SKIP) i--;
					} catch(Exception ex) {
						this.stop("Evaluation unsuccessful");
					}
				}
			}
			
			this.networkPackNT(15);
		}
	}
	
	public void stop(String reason) {
		this.isOn = false;
		this.ctx.turnOff();
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

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);

		this.isOn = nbt.getBoolean("isOn");
		this.ignoreError = nbt.getBoolean("ignoreError");
		this.autoReboot = nbt.getBoolean("autoReboot");
		
		this.ctx = new ParseContext(null);
		this.ctx.current = nbt.getInteger("current");
		this.ctx.clockSpeed = nbt.getInteger("clockSpeed");
		this.ctx.buffer = nbt.getString("buffer");
		
		NBTTagList lineList = nbt.getTagList("script", 8);
		this.script = new String[lineList.tagList.size()];
		for(int i = 0; i < script.length; i++) {
			this.script[i] = lineList.getStringTagAt(i);
			this.msesv1.generateJumpPoints(ctx, script[i], i);
		}
		
		this.ctx.variables = nbt.getCompoundTag("variables");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);

		nbt.setBoolean("isOn", isOn);
		nbt.setBoolean("ignoreError", ignoreError);
		nbt.setBoolean("autoReboot", autoReboot);

		nbt.setInteger("current", ctx.current);
		nbt.setInteger("clockSpeed", ctx.clockSpeed);
		nbt.setString("buffer", ctx.buffer);
		
		NBTTagList lineList = new NBTTagList();
		for(String line : this.script) {
			lineList.appendTag(new NBTTagString(line));
		}
		nbt.setTag("script", lineList);
		nbt.setTag("variables", this.ctx.variables);
	}

	@Override public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) { return null; }
	@Override public Object provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) { return new GUIScreenRadioAUTOCAL(this); }

	@Override
	public boolean hasPermission(EntityPlayer player) {
		return player.getDistanceSq(xCoord + 0.5, yCoord + 1, zCoord + 0.5) <= 15 * 15;
	}

	@Override
	public void receiveControl(NBTTagCompound data) {
		if(data.hasKey("on")) {
			if(this.isOn) stop("User requested shutdown");
			else this.isOn = true;
		}
		if(data.hasKey("ignore")) this.ignoreError = !this.ignoreError;
		if(data.hasKey("auto")) this.autoReboot = !this.autoReboot;
		
		if(data.hasKey("payload")) {
			this.ctx.jmp.clear();
			this.script = data.getString("payload").split("\n");
			for(int i = 0; i < script.length; i++) {
				script[i] = script[i].trim();
				this.msesv1.generateJumpPoints(ctx, script[i], i);
			}
			if(this.isOn) stop("Script has changed");
		}
		
		this.markChanged();
	}
}
