package api.hbm.fluidmk2;

import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.toclient.AuxParticlePacketNT;
import com.hbm.uninos.GenNode;
import com.hbm.uninos.UniNodespace;
import com.hbm.util.Compat;
import com.hbm.util.fauxpointtwelve.DirPos;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

/**
 * IFluidProviderMK2 with standard implementation for fluid provision and fluid removal.
 * @author hbm
 */
public interface IFluidStandardSenderMK2 extends IFluidProviderMK2 {

	public default void tryProvide(FluidTank tank, World world, DirPos pos) { tryProvide(tank.getTankType(), tank.getPressure(), world, pos.getX(), pos.getY(), pos.getZ(), pos.getDir()); }
	public default void tryProvide(FluidType type, World world, DirPos pos) { tryProvide(type, 0, world, pos.getX(), pos.getY(), pos.getZ(), pos.getDir()); }
	public default void tryProvide(FluidType type, int pressure, World world, DirPos pos) { tryProvide(type, pressure, world, pos.getX(), pos.getY(), pos.getZ(), pos.getDir()); }
	
	public default void tryProvide(FluidTank tank, World world, int x, int y, int z, ForgeDirection dir) { tryProvide(tank.getTankType(), tank.getPressure(), world, x, y, z, dir); }
	public default void tryProvide(FluidType type, World world, int x, int y, int z, ForgeDirection dir) { tryProvide(type, 0, world, x, y, z, dir); }
	
	public default void tryProvide(FluidType type, int pressure, World world, int x, int y, int z, ForgeDirection dir) {

		TileEntity te = Compat.getTileStandard(world, x, y, z);
		boolean red = false;
		
		if(te instanceof IFluidConnectorMK2) {
			IFluidConnectorMK2 con = (IFluidConnectorMK2) te;
			if(con.canConnect(type, dir.getOpposite())) {
				
				GenNode<FluidNetMK2> node = UniNodespace.getNode(world, x, y, z, type.getNetworkProvider());
				
				if(node != null && node.net != null) {
					node.net.addProvider(this);
					red = true;
				}
			}
		}
		
		if(te instanceof IFluidReceiverMK2 && te != this) {
			IFluidReceiverMK2 rec = (IFluidReceiverMK2) te;
			if(rec.canConnect(type, dir.getOpposite())) {
				long provides = Math.min(this.getFluidAvailable(type, pressure), this.getProviderSpeed(type, pressure));
				long receives = Math.min(rec.getDemand(type, pressure), rec.getReceiverSpeed(type, pressure));
				long toTransfer = Math.min(provides, receives);
				toTransfer -= rec.transferFluid(type, pressure, toTransfer);
				this.useUpFluid(type, pressure, toTransfer);
			}
		}
		
		if(particleDebug) {
			NBTTagCompound data = new NBTTagCompound();
			data.setString("type", "network");
			data.setString("mode", "fluid");
			data.setInteger("color", type.getColor());
			double posX = x + 0.5 - dir.offsetX * 0.5 + world.rand.nextDouble() * 0.5 - 0.25;
			double posY = y + 0.5 - dir.offsetY * 0.5 + world.rand.nextDouble() * 0.5 - 0.25;
			double posZ = z + 0.5 - dir.offsetZ * 0.5 + world.rand.nextDouble() * 0.5 - 0.25;
			data.setDouble("mX", dir.offsetX * (red ? 0.025 : 0.1));
			data.setDouble("mY", dir.offsetY * (red ? 0.025 : 0.1));
			data.setDouble("mZ", dir.offsetZ * (red ? 0.025 : 0.1));
			PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacketNT(data, posX, posY, posZ), new TargetPoint(world.provider.dimensionId, posX, posY, posZ, 25));
		}
	}
	
	public FluidTank[] getSendingTanks();
	
	@Override
	public default long getFluidAvailable(FluidType type, int pressure) {
		long amount = 0;
		for(FluidTank tank : getSendingTanks()) {
			if(tank.getTankType() == type && tank.getPressure() == pressure) amount += tank.getFill();
		}
		return amount;
	}

	@Override
	public default void useUpFluid(FluidType type, int pressure, long amount) {
		int tanks = 0;
		for(FluidTank tank : getSendingTanks()) {
			if(tank.getTankType() == type && tank.getPressure() == pressure) tanks++;
		}
		if(tanks > 1) {
			int firstRound = (int) Math.floor((double) amount / (double) tanks);
			for(FluidTank tank : getSendingTanks()) {
				if(tank.getTankType() == type && tank.getPressure() == pressure) {
					int toRem = Math.min(firstRound, tank.getFill());
					tank.setFill(tank.getFill() - toRem);
					amount -= toRem;
				}
			}
		}
		if(amount > 0) for(FluidTank tank : getSendingTanks()) {
			if(tank.getTankType() == type && tank.getPressure() == pressure) {
				int toRem = (int) Math.min(amount, tank.getFill());
				tank.setFill(tank.getFill() - toRem);
				amount -= toRem;
			}
		}
	}

	@Override
	public default int[] getProvidingPressureRange(FluidType type) {
		int lowest = HIGHEST_VALID_PRESSURE;
		int highest = 0;
		
		for(FluidTank tank : getSendingTanks()) {
			if(tank.getTankType() == type) {
				if(tank.getPressure() < lowest) lowest = tank.getPressure();
				if(tank.getPressure() > highest) highest = tank.getPressure();
			}
		}
		
		return lowest <= highest ? new int[] {lowest, highest} : DEFAULT_PRESSURE_RANGE;
	}

	@Override
	public default long getProviderSpeed(FluidType type, int pressure) {
		return 1_000_000_000;
	}
}
