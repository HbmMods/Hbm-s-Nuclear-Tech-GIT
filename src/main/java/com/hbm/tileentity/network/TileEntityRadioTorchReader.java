package com.hbm.tileentity.network;

import java.util.Locale;

import com.hbm.handler.CompatHandler;
import com.hbm.interfaces.IControlReceiver;
import com.hbm.tileentity.TileEntityLoadedBase;
import com.hbm.util.BufferUtil;
import com.hbm.util.Compat;

import api.hbm.redstoneoverradio.IRORValueProvider;
import cpw.mods.fml.common.Optional;
import io.netty.buffer.ByteBuf;
import li.cil.oc.api.machine.Arguments;
import li.cil.oc.api.machine.Callback;
import li.cil.oc.api.machine.Context;
import li.cil.oc.api.network.SimpleComponent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

@Optional.InterfaceList({@Optional.Interface(iface = "li.cil.oc.api.network.SimpleComponent", modid = "OpenComputers")})
public class TileEntityRadioTorchReader extends TileEntityLoadedBase implements IControlReceiver, SimpleComponent, CompatHandler.OCComponent {

	public String[] channels = new String[8];
	public String[] names = new String[8];
	public String[] prev = new String[8];
	public boolean polling = false;

	public TileEntityRadioTorchReader() {
		for(int i = 0; i < channels.length; i++) channels[i] = "";
		for(int i = 0; i < names.length; i++) names[i] = "";
		for(int i = 0; i < prev.length; i++) prev[i] = "";
	}

	@Override
	public void updateEntity() {

		if(!worldObj.isRemote) {
			ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata()).getOpposite();

			TileEntity tile = Compat.getTileStandard(worldObj, xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ);

			if(tile instanceof IRORValueProvider) {
				IRORValueProvider prov = (IRORValueProvider) tile;

				for(int i = 0; i < 8; i++) {
					String channel = channels[i];
					String name = names[i];
					String previous = prev[i];

					if(channel == null || channel.isEmpty()) continue;
					if(name == null || name.isEmpty()) continue;

					String value = prov.provideRORValue(IRORValueProvider.PREFIX_VALUE + name.toLowerCase(Locale.US));
					if(value == null) continue;

					if(polling || !value.equals(previous)) {
						RTTYSystem.broadcast(worldObj, channel, value);
						this.prev[i] = value;
					}
				}
			}

			networkPackNT(50);
		}
	}

	@Override
	public void serialize(ByteBuf buf) {
		buf.writeBoolean(this.polling);
		for(int i = 0; i < channels.length; i++) BufferUtil.writeString(buf, channels[i]);
		for(int i = 0; i < names.length; i++) BufferUtil.writeString(buf, names[i]);
	}

	@Override
	public void deserialize(ByteBuf buf) {
		this.polling = buf.readBoolean();
		for(int i = 0; i < channels.length; i++) channels[i] = BufferUtil.readString(buf);
		for(int i = 0; i < names.length; i++) names[i] = BufferUtil.readString(buf);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.polling = nbt.getBoolean("p");
		for(int i = 0; i < channels.length; i++) channels[i] = nbt.getString("c" + i);
		for(int i = 0; i < names.length; i++) names[i] = nbt.getString("n" + i);
		for(int i = 0; i < prev.length; i++) prev[i] = nbt.getString("p" + i);
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setBoolean("p", polling);
		for(int i = 0; i < channels.length; i++) nbt.setString("c" + i, channels[i]);
		for(int i = 0; i < names.length; i++) nbt.setString("n" + i, names[i]);
		for(int i = 0; i < prev.length; i++) nbt.setString("p" + i, prev[i]);
	}

	@Override
	public void receiveControl(NBTTagCompound data) {
		if(data.hasKey("p")) this.polling = data.getBoolean("p");
		for(int i = 0; i < channels.length; i++) if(data.hasKey("c" + i)) channels[i] = data.getString("c" + i);
		for(int i = 0; i < names.length; i++) if(data.hasKey("n" + i)) names[i] = data.getString("n" + i);

		this.markDirty();
	}

	@Override
	public boolean hasPermission(EntityPlayer player) {
		return player.getDistance(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5) < 16D;
	}

	// yay opencomputer stuff
	@Override
	@Optional.Method(modid = "OpenComputers")
	public String getComponentName() {
		return "radio_reader";
	}

	@Callback(direct = true, limit = 4, doc = "function(index: number, channel: string) -- Sets channel name at a index")
	@Optional.Method(modid = "OpenComputers")
	public Object[] setChannel(Context context, Arguments args) {
		int index = args.checkInteger(0);
		if (index >= 0 && index < channels.length) channels[args.checkInteger(0)] = args.checkString(1);
		return new Object[] {};
	}

	@Callback(direct = true, doc = "function(index: number):string -- Gets channel name at a index")
	@Optional.Method(modid = "OpenComputers")
	public Object[] getChannel(Context context, Arguments args) {
		int index = args.checkInteger(0);
		if (index >= 0 && index < channels.length) return new Object[] { channels[args.checkInteger(0)] };
		else return new Object[] {};
	}

	@Callback(direct = true, limit = 4, doc = "function(index: number, channel: string) -- Sets function at a index")
	@Optional.Method(modid = "OpenComputers")
	public Object[] setName(Context context, Arguments args) {
		int index = args.checkInteger(0);
		if (index >= 0 && index < names.length) names[args.checkInteger(0)] = args.checkString(1);
		return new Object[] {};
	}

	@Callback(direct = true, doc = "function(index: number):string -- Gets function at a index")
	@Optional.Method(modid = "OpenComputers")
	public Object[] getName(Context context, Arguments args) {
		int index = args.checkInteger(0);
		if (index >= 0 && index < names.length) return new Object[] { names[args.checkInteger(0)] };
		else return new Object[] {};
	}

	@Callback(direct = true, limit = 4, doc = "function(value: boolean) -- Switches state change mode to tick-based polling")
	@Optional.Method(modid = "OpenComputers")
	public Object[] setPolling(Context context, Arguments args) {
		polling = args.checkBoolean(0);
		return new Object[] {};
	}

	@Callback(direct = true, doc = "function():boolean -- Whenever the torch is set to tick-based polling")
	@Optional.Method(modid = "OpenComputers")
	public Object[] getPolling(Context context, Arguments args) {
		return new Object[] { polling };
	}

	@Callback(direct = true, doc = "function(index: number):string -- Gets last received value at a index")
	@Optional.Method(modid = "OpenComputers")
	public Object[] read(Context context, Arguments args) {
		int index = args.checkInteger(0);
		if (index >= 0 && index < channels.length) return new Object[] { prev[index] };
		else return new Object[] {};
	}
}
