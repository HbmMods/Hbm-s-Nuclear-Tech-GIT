package api.hbm.fluid;

import com.hbm.inventory.fluid.FluidType;
import com.hbm.packet.AuxParticlePacketNT;
import com.hbm.packet.PacketDispatcher;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public interface IFluidConnector {
	
	/**
	 * Returns the amount of fluid that remains
	 * @param power
	 * @return
	 */
	public long transferFluid(FluidType type, long fluid);
	
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
	public long getDemand(FluidType type);
	
	/**
	 * Basic implementation of subscribing to a nearby power grid
	 * @param world
	 * @param x
	 * @param y
	 * @param z
	 */
	public default void trySubscribe(FluidType type, World world, int x, int y, int z, ForgeDirection dir) {

		TileEntity te = world.getTileEntity(x, y, z);
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
			data.setString("type", "vanillaExt");
			data.setString("mode", red ? "reddust" : "bluedust");
			PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacketNT(data, x + world.rand.nextDouble(), y + world.rand.nextDouble(), z + world.rand.nextDouble()), new TargetPoint(world.provider.dimensionId, x + 0.5, y + 0.5, z + 0.5, 25));
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
	
	public static final boolean particleDebug = true;
}
