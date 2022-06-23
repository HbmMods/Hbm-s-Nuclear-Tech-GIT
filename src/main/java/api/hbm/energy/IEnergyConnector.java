package api.hbm.energy;

import com.hbm.packet.AuxParticlePacketNT;
import com.hbm.packet.PacketDispatcher;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

/**
 * For anything that connects to power and can be transferred power to, the bottom-level interface.
 * This is mean for TILE ENTITIES
 * @author hbm
 */
public interface IEnergyConnector extends ILoadedTile {
	
	/**
	 * Returns the amount of power that remains in the source after transfer
	 * @param power
	 * @return
	 */
	public long transferPower(long power);
	
	/**
	 * Whether the given side can be connected to
	 * @param dir
	 * @return
	 */
	public default boolean canConnect(ForgeDirection dir) {
		return dir != ForgeDirection.UNKNOWN;
	}
	
	/**
	 * The current power of either the machine or an entire network
	 * @return
	 */
	public long getPower();
	
	/**
	 * The capacity of either the machine or an entire network
	 * @return
	 */
	public long getMaxPower();
	
	public default long getTransferWeight() {
		return Math.max(getMaxPower() - getPower(), 0);
	}
	
	/**
	 * Basic implementation of subscribing to a nearby power grid
	 * @param world
	 * @param x
	 * @param y
	 * @param z
	 */
	public default void trySubscribe(World world, int x, int y, int z, ForgeDirection dir) {

		TileEntity te = world.getTileEntity(x, y, z);
		boolean red = false;
		
		if(te instanceof IEnergyConductor) {
			IEnergyConductor con = (IEnergyConductor) te;
			
			if(!con.canConnect(dir.getOpposite().getOpposite()))
				return;
			
			if(con.getPowerNet() != null && !con.getPowerNet().isSubscribed(this))
				con.getPowerNet().subscribe(this);
			
			if(con.getPowerNet() != null)
				red = true;
		}
		
		if(particleDebug) {//
			NBTTagCompound data = new NBTTagCompound();
			data.setString("type", "network");
			data.setString("mode", "power");
			double posX = x + 0.5 + dir.offsetX * 0.5 + world.rand.nextDouble() * 0.5 - 0.25;
			double posY = y + 0.5 + dir.offsetY * 0.5 + world.rand.nextDouble() * 0.5 - 0.25;
			double posZ = z + 0.5 + dir.offsetZ * 0.5 + world.rand.nextDouble() * 0.5 - 0.25;
			data.setDouble("mX", -dir.offsetX * (red ? 0.025 : 0.1));
			data.setDouble("mY", -dir.offsetY * (red ? 0.025 : 0.1));
			data.setDouble("mZ", -dir.offsetZ * (red ? 0.025 : 0.1));
			PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacketNT(data, posX, posY, posZ), new TargetPoint(world.provider.dimensionId, posX, posY, posZ, 25));
		}
	}
	
	public default void tryUnsubscribe(World world, int x, int y, int z) {

		TileEntity te = world.getTileEntity(x, y, z);
		
		if(te instanceof IEnergyConductor) {
			IEnergyConductor con = (IEnergyConductor) te;
			
			if(con.getPowerNet() != null && con.getPowerNet().isSubscribed(this))
				con.getPowerNet().unsubscribe(this);
		}
	}
	
	public static final boolean particleDebug = false;
	
	public default Vec3 getDebugParticlePos() {
		TileEntity te = (TileEntity) this;
		Vec3 vec = Vec3.createVectorHelper(te.xCoord + 0.5, te.yCoord + 1, te.zCoord + 0.5);
		return vec;
	}
}
