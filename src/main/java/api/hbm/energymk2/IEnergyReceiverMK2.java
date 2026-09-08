package api.hbm.energymk2;

import com.hbm.handler.threading.PacketThreading;
import com.hbm.packet.toclient.AuxParticlePacketNT;
import com.hbm.util.fauxpointtwelve.DirPos;

import api.hbm.energymk2.Nodespace.PowerNode;
import api.hbm.tile.EnumTransferAction;
import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

/** If it receives energy, use this */
public interface IEnergyReceiverMK2 extends IEnergyHandlerMK2 {

	public default long transferPower(long power) {
		if(power + this.getPower() <= this.getMaxPower()) {
			this.setPower(power + this.getPower());
			return 0;
		}
		long capacity = this.getMaxPower() - this.getPower();
		long overshoot = power - capacity;
		this.setPower(this.getMaxPower());
		return overshoot;
	}

	public default long getReceiverSpeed() {
		return this.getMaxPower();
	}
	
	/** Whether a provider can provide power by touching the block (i.e. via proxies), bypassing the need for a network entirely */
	public default boolean allowDirectProvision() { return true; }

	public default EnumTransferAction trySubscribe(World world, DirPos pos) { return trySubscribe(world, pos.getX(), pos.getY(), pos.getZ(), pos.getDir()); }

	public default EnumTransferAction trySubscribe(World world, int x, int y, int z, ForgeDirection dir) {

		TileEntity te = TileAccessCache.getTileOrCache(world, x, y, z);
		boolean red = false;
		EnumTransferAction action = EnumTransferAction.NOTHING;

		if(te instanceof IEnergyConductorMK2) {
			IEnergyConductorMK2 con = (IEnergyConductorMK2) te;
			if(!con.canConnect(dir.getOpposite())) return action;

			PowerNode node = Nodespace.getNode(world, x, y, z);

			if(node != null && node.net != null) {
				node.net.addReceiver(this);
				action = EnumTransferAction.CONNECT_NET;
				red = true;
			}
		}

		if(particleDebug) {
			NBTTagCompound data = new NBTTagCompound();
			data.setString("type", "network");
			data.setString("mode", "power");
			double posX = x + 0.5 + dir.offsetX * 0.5 + world.rand.nextDouble() * 0.5 - 0.25;
			double posY = y + 0.5 + dir.offsetY * 0.5 + world.rand.nextDouble() * 0.5 - 0.25;
			double posZ = z + 0.5 + dir.offsetZ * 0.5 + world.rand.nextDouble() * 0.5 - 0.25;
			data.setDouble("mX", -dir.offsetX * (red ? 0.025 : 0.1));
			data.setDouble("mY", -dir.offsetY * (red ? 0.025 : 0.1));
			data.setDouble("mZ", -dir.offsetZ * (red ? 0.025 : 0.1));
			PacketThreading.createAllAroundThreadedPacket(new AuxParticlePacketNT(data, posX, posY, posZ), new TargetPoint(world.provider.dimensionId, posX, posY, posZ, 25));
		}
		
		return action;
	}

	public default void tryUnsubscribe(World world, int x, int y, int z) {

		TileEntity te = world.getTileEntity(x, y, z);

		if(te instanceof IEnergyConductorMK2) {
			IEnergyConductorMK2 con = (IEnergyConductorMK2) te;
			PowerNode node = con.createNode();

			if(node != null && node.net != null) {
				node.net.removeReceiver(this);
			}
		}
	}

	public enum ConnectionPriority {
		LOWEST,
		LOW,
		NORMAL,
		HIGH,
		HIGHEST
	}
	
	public default ConnectionPriority getPriority() {
		return ConnectionPriority.NORMAL;
	}
}
