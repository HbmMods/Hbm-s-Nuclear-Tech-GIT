package com.hbm.tileentity.network;

import com.hbm.explosion.vanillant.ExplosionVNT;
import com.hbm.explosion.vanillant.standard.EntityProcessorCrossSmooth;
import com.hbm.explosion.vanillant.standard.ExplosionEffectWeapon;
import com.hbm.explosion.vanillant.standard.PlayerProcessorStandard;
import com.hbm.handler.CompatHandler;
import com.hbm.interfaces.IControlReceiver;
import com.hbm.tileentity.TileEntityLoadedBase;
import com.hbm.tileentity.network.RTTYSystem.RTTYChannel;
import com.hbm.util.BufferUtil;
import com.hbm.util.Compat;

import api.hbm.redstoneoverradio.IRORInteractive;
import api.hbm.redstoneoverradio.RORFunctionException;
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
public class TileEntityRadioTorchController extends TileEntityLoadedBase implements IControlReceiver, SimpleComponent, CompatHandler.OCComponent {

	public String channel = "";
	public String prev;
	public boolean polling = true;

	@Override
	public void updateEntity() {

		if(!worldObj.isRemote) {

			if(channel != null && !channel.isEmpty()) {
				ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata()).getOpposite();

				TileEntity tile = Compat.getTileStandard(worldObj, xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ);

				if(tile instanceof IRORInteractive) {
					IRORInteractive ror = (IRORInteractive) tile;

					RTTYChannel chan = RTTYSystem.listen(worldObj, channel);
					if(chan != null) {
						String rec = "" + chan.signal;
						if("selfdestruct".equals(rec)) {
							worldObj.func_147480_a(xCoord, yCoord, zCoord, false);
							ExplosionVNT vnt = new ExplosionVNT(worldObj, xCoord + 0.5, yCoord + 0.5, zCoord + 0.5, 5, null);
							vnt.setEntityProcessor(new EntityProcessorCrossSmooth(1, 50).setupPiercing(5F, 0.5F));
							vnt.setPlayerProcessor(new PlayerProcessorStandard());
							vnt.setSFX(new ExplosionEffectWeapon(10, 2.5F, 1F));
							vnt.explode();
							return;
						}
						if((this.polling && chan.timeStamp >= worldObj.getTotalWorldTime() - 1) || !rec.equals(prev)) {
							try {
								if(rec != null && !rec.isEmpty()) ror.runRORFunction(IRORInteractive.PREFIX_FUNCTION + IRORInteractive.getCommand(rec), IRORInteractive.getParams(rec));
							} catch(RORFunctionException ex) { }
							prev = rec;
						}
					}
				}
			}

			networkPackNT(50);
		}
	}

	@Override
	public void serialize(ByteBuf buf) {
		buf.writeBoolean(this.polling);
		BufferUtil.writeString(buf, channel);
	}

	@Override
	public void deserialize(ByteBuf buf) {
		this.polling = buf.readBoolean();
		channel = BufferUtil.readString(buf);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.polling = nbt.getBoolean("p");
		channel = nbt.getString("c");
		this.prev = nbt.getString("prev");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setBoolean("p", polling);
		nbt.setString("c", channel);
		if(prev != null) nbt.setString("prev", prev);
	}

	@Override
	public void receiveControl(NBTTagCompound data) {
		if(data.hasKey("p")) this.polling = data.getBoolean("p");
		if(data.hasKey("c")) channel = data.getString("c");

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
		return "radio_controller";
	}

	@Callback(direct = true, limit = 4, doc = "function(channel: string) -- Set the channel the torch is broadcasting to")
	@Optional.Method(modid = "OpenComputers")
	public Object[] setChannel(Context context, Arguments args) {
		channel = args.checkString(0);
		return new Object[] {};
	}

	@Callback(direct = true, doc = "function():string -- Gets current channel the torch is broadcasting to")
	@Optional.Method(modid = "OpenComputers")
	public Object[] getChannel(Context context, Arguments args) {
		return new Object[] { channel };
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

	@Callback(direct = true, limit = 4, doc = "function(command: string) -- Sends a command")
	@Optional.Method(modid = "OpenComputers")
	public Object[] send(Context context, Arguments args) {
		String cmd = args.checkString(0);
		if (channel != null && !channel.isEmpty() && cmd != null && !cmd.isEmpty())
			RTTYSystem.broadcast(worldObj, channel, cmd);
		return new Object[] {};
	}
}
