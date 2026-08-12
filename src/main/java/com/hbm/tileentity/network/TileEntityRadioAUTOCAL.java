package com.hbm.tileentity.network;

import com.hbm.handler.CompatHandler;
import com.hbm.interfaces.IControlReceiver;
import com.hbm.inventory.gui.GUIScreenRadioAUTOCAL;
import com.hbm.module.IParse;
import com.hbm.module.IParse.EnumStatementReturn;
import com.hbm.module.IParse.ParseContext;
import com.hbm.module.ParseMSES1Ext1;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityTickingBase;
import com.hbm.util.BufferUtil;

import cpw.mods.fml.common.Optional;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import li.cil.oc.api.machine.Arguments;
import li.cil.oc.api.machine.Callback;
import li.cil.oc.api.machine.Context;
import li.cil.oc.api.network.SimpleComponent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

@Optional.InterfaceList({@Optional.Interface(iface = "li.cil.oc.api.network.SimpleComponent", modid = "OpenComputers")})
public class TileEntityRadioAUTOCAL extends TileEntityTickingBase implements IControlReceiver, IGUIProvider, SimpleComponent, CompatHandler.OCComponent {

	public boolean isOn = false;
	public boolean ignoreError = false;
	public boolean autoReboot = false;

	public String[] script = new String[0];
	public IParse msesv1ext = new ParseMSES1Ext1();
	public ParseContext ctx;

	public String[] history = new String[] {"", "", "", "", "", ""};

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
						String line = this.script[index];
						EnumStatementReturn ret = msesv1ext.eval(ctx, line);
						if(ret != EnumStatementReturn.SKIP) pushMsg(index + ": " + line);
						this.history[0] = "Buffer: " + ctx.readBuffer();
						if(ret == EnumStatementReturn.END_TICK) break;
						if(ret == EnumStatementReturn.SHUTDOWN) this.stop("Program requested shutdown");
						if(!this.ignoreError) {
							if(ret == EnumStatementReturn.UNRECOGNIZED_COMMAND) this.stop("Unrecognized command");
							if(ret == EnumStatementReturn.PARAMETER_ERROR) this.stop("Parameter error");
							if(ret == EnumStatementReturn.UNDEFINED) this.stop("Undefined behavior");
							if(ret == EnumStatementReturn.STACK_EXCEEDED) this.stop("Stack exceeded capacity");
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

	public void pushMsg(String msg) {

		for(int i = 2; i < history.length; i++) {
			history[i - 1] = history[i];
		}

		history[history.length - 1] = msg;
	}

	public void stop(String reason) {
		this.isOn = false;
		this.ctx.turnOff();
		this.pushMsg(reason);
	}

	@Override
	public void serialize(ByteBuf buf) {
		super.serialize(buf);
		buf.writeBoolean(isOn);
		buf.writeBoolean(ignoreError);
		buf.writeBoolean(autoReboot);
		for(int i = 0; i < history.length; i++) BufferUtil.writeString(buf, this.history[i]);
	}

	@Override
	public void deserialize(ByteBuf buf) {
		super.deserialize(buf);
		this.isOn = buf.readBoolean();
		this.ignoreError = buf.readBoolean();
		this.autoReboot = buf.readBoolean();
		for(int i = 0; i < history.length; i++) this.history[i] = BufferUtil.readString(buf);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);

		this.isOn = nbt.getBoolean("isOn");
		this.ignoreError = nbt.getBoolean("ignoreError");
		this.autoReboot = nbt.getBoolean("autoReboot");

		NBTTagList lineList = nbt.getTagList("script", 8);
		this.script = new String[lineList.tagList.size()];
		for(int i = 0; i < script.length; i++) {
			this.script[i] = lineList.getStringTagAt(i);
		}

		this.ctx = new ParseContext(null);
		this.ctx.readFromNBT(nbt, script, msesv1ext);
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);

		nbt.setBoolean("isOn", isOn);
		nbt.setBoolean("ignoreError", ignoreError);
		nbt.setBoolean("autoReboot", autoReboot);

		NBTTagList lineList = new NBTTagList();
		for(String line : this.script) {
			lineList.appendTag(new NBTTagString(line));
		}
		nbt.setTag("script", lineList);

		this.ctx.writeToNBT(nbt);
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
				this.msesv1ext.generateJumpPoints(ctx, script[i], i);
			}
			if(this.isOn) stop("Script has changed");
		}

		this.markChanged();
	}

	AxisAlignedBB bb = null;

	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		if(bb == null) bb = AxisAlignedBB.getBoundingBox(xCoord, yCoord, zCoord, xCoord + 1, yCoord + 2, zCoord + 1);
		return bb;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared() {
		return 65536.0D;
	}

	// yay opencomputer stuff
	@Override
	@Optional.Method(modid = "OpenComputers")
	public String getComponentName() {
		return "radio_autocal";
	}

	@Callback(direct = true, limit = 4, doc = "function(buffer: string):boolean -- Changes buffer. This can allow for inter-computer communication. Returns false if buffer was truncated")
	@Optional.Method(modid = "OpenComputers")
	public Object[] setBuffer(Context context, Arguments args) {
		if (ctx != null)
			return new Object[] { ctx.writeBuffer(args.checkString(0)) };
		return new Object[] {};
	}

	@Callback(direct = true, limit = 4, doc = "function():string -- Reads current buffer")
	@Optional.Method(modid = "OpenComputers")
	public Object[] getBuffer(Context context, Arguments args) {
		if (ctx == null)
			return new Object[] { "" };
		return new Object[] { ctx.readBuffer() };
	}

	@Callback(direct = true, limit = 1, doc = "function(script: string) -- Changes the script")
	@Optional.Method(modid = "OpenComputers")
	public Object[] setScript(Context context, Arguments args) {
		String data = args.checkString(0);
		this.ctx.jmp.clear();
		this.script = data.split("\n");
		for(int i = 0; i < script.length; i++) {
			script[i] = script[i].trim();
			this.msesv1ext.generateJumpPoints(ctx, script[i], i);
		}
		if(this.isOn) stop("Script has changed");
		return new Object[] {};
	}

	@Callback(direct = true, limit = 1, doc = "function():string -- Returns current script")
	@Optional.Method(modid = "OpenComputers")
	public Object[] getScript(Context context, Arguments args) {
		String joinedScript = "";
		for(int i = 0; i < script.length; i++) {
			joinedScript = joinedScript + script[i] + "\n";
		}
		return new Object[] { joinedScript };
	}

	@Callback(direct = true, limit = 4, doc = "function(state: boolean) -- Turns on or off the machine")
	@Optional.Method(modid = "OpenComputers")
	public Object[] setState(Context context, Arguments args) {
		boolean state = args.checkBoolean(0);
		if (state) isOn = true;
		else stop("User requested shutdown");
		return new Object[] {};
	}

	@Callback(direct = true, doc = "function():boolean -- Whenever the machine is currently on")
	@Optional.Method(modid = "OpenComputers")
	public Object[] getState(Context context, Arguments args) {
		return new Object[] { isOn };
	}

	@Callback(direct = true, limit = 4, doc = "function(ignore: boolean) -- Makes the machine ignore errors")
	@Optional.Method(modid = "OpenComputers")
	public Object[] setIgnoreError(Context context, Arguments args) {
		ignoreError = args.checkBoolean(0);
		return new Object[] {};
	}

	@Callback(direct = true, doc = "function():boolean -- Whenever the machine is currently ignoring errors")
	@Optional.Method(modid = "OpenComputers")
	public Object[] getIgnoreError(Context context, Arguments args) {
		return new Object[] { ignoreError };
	}

	@Callback(direct = true, limit = 4, doc = "function(auto: boolean) -- Makes the machine automatically reboot")
	@Optional.Method(modid = "OpenComputers")
	public Object[] setAutoReboot(Context context, Arguments args) {
		autoReboot = args.checkBoolean(0);
		return new Object[] {};
	}

	@Callback(direct = true, doc = "function():boolean -- Whenever the machine automatically reboots")
	@Optional.Method(modid = "OpenComputers")
	public Object[] getAutoReboot(Context context, Arguments args) {
		return new Object[] { autoReboot };
	}

	@Callback(direct = true, limit = 4, doc = "function(index: number):string -- Returns a line in history")
	@Optional.Method(modid = "OpenComputers")
	public Object[] getHistory(Context context, Arguments args) {
		int index = args.checkInteger(0);
		if (index >= 0 && index < history.length) return new Object[] { history[index] };
		else return new Object[] {};
	}
}
