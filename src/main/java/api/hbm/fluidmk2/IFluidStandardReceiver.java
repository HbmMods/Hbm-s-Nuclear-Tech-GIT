package api.hbm.fluidmk2;

import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.toclient.AuxParticlePacketNT;
import com.hbm.util.Compat;
import com.hbm.util.fauxpointtwelve.DirPos;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public interface IFluidStandardReceiver extends IFluidReceiverMK2 {
	
	public default void trySubscribe(FluidType type, World world, DirPos pos) { trySubscribe(type, world, pos.getX(), pos.getY(), pos.getZ(), pos.getDir()); }
	
	public default void trySubscribe(FluidType type, World world, int x, int y, int z, ForgeDirection dir) {

		TileEntity te = Compat.getTileStandard(world, x, y, z);
		boolean red = false;
		
		if(te instanceof IFluidConductorMK2) {
			IFluidConductorMK2 con = (IFluidConductorMK2) te;
			
			if(!con.canConnect(type, dir)) return;
			
			if(con.getPipeNet(type) != null && !con.getPipeNet(type).isSubscribed(this))
				con.getPipeNet(type).addReceiver(this);
			
			if(con.getPipeNet(type) != null) red = true;
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
	
	public FluidTank[] getReceivingTanks();

	@Override
	public default long getDemand(FluidType type, int pressure) {
		long amount = 0;
		for(FluidTank tank : getReceivingTanks()) {
			if(tank.getTankType() == type && tank.getPressure() == pressure) amount += (tank.getMaxFill() - tank.getFill());
		}
		return amount;
	}

	@Override
	public default long transferFluid(FluidType type, int pressure, long amount) {
		int tanks = 0;
		for(FluidTank tank : getReceivingTanks()) {
			if(tank.getTankType() == type && tank.getPressure() == pressure) tanks++;
		}
		if(tanks > 1) {
			int firstRound = (int) Math.floor((double) amount / (double) tanks);
			for(FluidTank tank : getReceivingTanks()) {
				if(tank.getTankType() == type && tank.getPressure() == pressure) {
					int toAdd = Math.min(firstRound, tank.getMaxFill() - tank.getFill());
					tank.setFill(tank.getFill() + toAdd);
					amount -= toAdd;
				}
			}
		}
		if(amount > 0) for(FluidTank tank : getReceivingTanks()) {
			if(tank.getTankType() == type && tank.getPressure() == pressure) {
				int toAdd = (int) Math.min(amount, tank.getMaxFill() - tank.getFill());
				tank.setFill(tank.getFill() + toAdd);
				amount -= toAdd;
			}
		}
		return amount;
	}
}
