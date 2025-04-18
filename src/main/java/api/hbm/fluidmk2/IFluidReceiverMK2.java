package api.hbm.fluidmk2;

import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.toclient.AuxParticlePacketNT;
import com.hbm.uninos.GenNode;
import com.hbm.uninos.UniNodespace;
import com.hbm.util.Compat;
import com.hbm.util.fauxpointtwelve.DirPos;

import api.hbm.energymk2.IEnergyReceiverMK2.ConnectionPriority;
import api.ntm1of90.compat.fluid.HBMForgeFluidCompat;
import api.ntm1of90.compat.fluid.bridge.NTMFluidNetworkBridge;
import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.IFluidHandler;

public interface IFluidReceiverMK2 extends IFluidUserMK2 {

	/** Sends fluid of the desired type and pressure to the receiver, returns the remainder */
	public long transferFluid(FluidType type, int pressure, long amount);
	public default long getReceiverSpeed(FluidType type, int pressure) { return 1_000_000_000; }
	public long getDemand(FluidType type, int pressure);

	public default int[] getReceivingPressureRange(FluidType type) { return DEFAULT_PRESSURE_RANGE; }

	public default void trySubscribe(FluidType type, World world, DirPos pos) { trySubscribe(type, world, pos.getX(), pos.getY(), pos.getZ(), pos.getDir()); }

	public default void trySubscribe(FluidType type, World world, int x, int y, int z, ForgeDirection dir) {

		TileEntity te = Compat.getTileStandard(world, x, y, z);
		boolean red = false;

		if(te instanceof IFluidConnectorMK2) {
			IFluidConnectorMK2 con = (IFluidConnectorMK2) te;
			if(!con.canConnect(type, dir.getOpposite())) return;

			GenNode node = UniNodespace.getNode(world, x, y, z, type.getNetworkProvider());

			if(node != null && node.net != null) {
				node.net.addReceiver(this);
				red = true;
			}
		}

		// Check if the tile entity is a Forge fluid handler
		if(te != this && HBMForgeFluidCompat.isForgeFluidHandler(te)) {
			IFluidHandler forgeHandler = HBMForgeFluidCompat.getForgeFluidHandler(te);

			// Check if the Forge handler can provide this fluid
			if(NTMFluidNetworkBridge.canForgeHandlerProvideFluid(forgeHandler, type, dir.getOpposite())) {
				// Get the demand for this fluid
				long demand = this.getDemand(type, 0);
				if(demand <= 0) return;

				// Get the receiving tanks
				if(this instanceof IFluidStandardReceiverMK2) {
					IFluidStandardReceiverMK2 receiver = (IFluidStandardReceiverMK2) this;
					FluidTank[] tanks = receiver.getReceivingTanks();

					// Find a tank that can accept this fluid
					for(FluidTank tank : tanks) {
						if(tank.getTankType() == type || tank.getTankType() == Fluids.NONE) {
							// Transfer the fluid from the Forge handler
							int transferred = NTMFluidNetworkBridge.transferFromForge(forgeHandler, tank, dir.getOpposite(), tank.getMaxFill() - tank.getFill());
							if(transferred > 0) {
								// Update the tank
								if(tank.getTankType() == Fluids.NONE) {
									tank.setTankType(type);
								}
								red = true;
							}
						}
					}
				}
			}
		}

		if(particleDebug) {
			NBTTagCompound data = new NBTTagCompound();
			data.setString("type", "network");
			data.setString("mode", "fluid");
			data.setInteger("color", type.getColor());
			double posX = x + 0.5 + dir.offsetX * 0.5 + world.rand.nextDouble() * 0.5 - 0.25;
			double posY = y + 0.5 + dir.offsetY * 0.5 + world.rand.nextDouble() * 0.5 - 0.25;
			double posZ = z + 0.5 + dir.offsetZ * 0.5 + world.rand.nextDouble() * 0.5 - 0.25;
			data.setDouble("mX", -dir.offsetX * (red ? 0.025 : 0.1));
			data.setDouble("mY", -dir.offsetY * (red ? 0.025 : 0.1));
			data.setDouble("mZ", -dir.offsetZ * (red ? 0.025 : 0.1));
			PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacketNT(data, posX, posY, posZ), new TargetPoint(world.provider.dimensionId, posX, posY, posZ, 25));
		}
	}

	public default ConnectionPriority getFluidPriority() {
		return ConnectionPriority.NORMAL;
	}
}
