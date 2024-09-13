package api.hbm.fluid;

import com.hbm.inventory.fluid.FluidType;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.toclient.AuxParticlePacketNT;
import com.hbm.util.Compat;

import api.hbm.tile.ILoadedTile;
import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public interface IFluidConnector extends ILoadedTile {
	
	/**
	 * Returns the amount of fluid that remains
	 * @param power
	 * @return
	 */
	public long transferFluid(FluidType type, int pressure, long fluid);
	
	/**
	 * Whether the given side can be connected to
	 * @param dir
	 * @return
	 */
	public default boolean canConnect(FluidType type, ForgeDirection dir) {
		return dir != ForgeDirection.UNKNOWN;
	}
	
	/**
	 * Returns the amount of fluid that this machine is able to receive
	 * @param type
	 * @return
	 */
	public long getDemand(FluidType type, int pressure);
	
	/**
	 * Basic implementation of subscribing to a nearby power grid
	 * @param world
	 * @param x
	 * @param y
	 * @param z
	 */
	public default void trySubscribe(FluidType type, World world, int x, int y, int z, ForgeDirection dir) {

		TileEntity te = Compat.getTileStandard(world, x, y, z);
		boolean red = false;
		
		if(te instanceof IFluidConductor) {
			IFluidConductor con = (IFluidConductor) te;
			
			if(!con.canConnect(type, dir))
				return;
			
			if(con.getPipeNet(type) != null && !con.getPipeNet(type).isSubscribed(this))
				con.getPipeNet(type).subscribe(this);
			
			if(con.getPipeNet(type) != null)
				red = true;
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
	
	public default void tryUnsubscribe(FluidType type, World world, int x, int y, int z) {

		TileEntity te = world.getTileEntity(x, y, z);
		
		if(te instanceof IFluidConductor) {
			IFluidConductor con = (IFluidConductor) te;
			
			if(con.getPipeNet(type) != null && con.getPipeNet(type).isSubscribed(this))
				con.getPipeNet(type).unsubscribe(this);
		}
	}
	
	public static final boolean particleDebug = false;
}
