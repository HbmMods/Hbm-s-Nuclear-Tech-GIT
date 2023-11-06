package api.hbm.energy;

import com.hbm.packet.AuxParticlePacketNT;
import com.hbm.packet.PacketDispatcher;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

/**
 * For machines and things that have an energy buffer and are affected by EMPs
 * @author hbm
 */
public interface IEnergyUser extends IEnergyConnector {
	
	/**
	 * Not to be used for actual energy transfer, rather special external things like EMPs and sync packets
	 */
	public void setPower(long power);
	
	/**
	 * Standard implementation for power transfer.
	 * Turns out you can override interfaces to provide a default implementation. Neat.
	 * @param long power
	 */
	@Override
	public default long transferPower(long power) {
		
		if(this.getPower() + power > this.getMaxPower()) {
			
			long overshoot = this.getPower() + power - this.getMaxPower();
			this.setPower(this.getMaxPower());
			return overshoot;
		}
		
		if(this.getPower() + power < 0) return 0; //safeguard for negative energy or overflows
		
		this.setPower(this.getPower() + power);
		
		return 0;
	}
	
	/**
	 * Standard implementation of sending power
	 * @param world
	 * @param x
	 * @param y
	 * @param z
	 * @param dir
	 */
	public default void sendPower(World world, int x, int y, int z, ForgeDirection dir) {
		
		TileEntity te = world.getTileEntity(x, y, z);
		boolean wasSubscribed = false;
		boolean red = false;
		
		// first we make sure we're not subscribed to the network that we'll be supplying
		if(te instanceof IEnergyConductor) {
			IEnergyConductor con = (IEnergyConductor) te;
			
			if(con.canConnect(dir.getOpposite()) && con.getPowerNet() != null && con.getPowerNet().isSubscribed(this)) {
				con.getPowerNet().unsubscribe(this);
				wasSubscribed = true;
			}
		}
		
		//then we add energy
		if(te instanceof IEnergyConnector) {
			IEnergyConnector con = (IEnergyConnector) te;
			
			if(con.canConnect(dir.getOpposite())) {
				long oldPower = this.getPower();
				long transfer = oldPower - con.transferPower(oldPower);
				this.setPower(oldPower - transfer);
				red = true;
			}
		}
		
		//then we subscribe if possible
		if(wasSubscribed && te instanceof IEnergyConductor) {
			IEnergyConductor con = (IEnergyConductor) te;
			
			if(con.getPowerNet() != null && !con.getPowerNet().isSubscribed(this)) {
				con.getPowerNet().subscribe(this);
			}
		}
		
		if(particleDebug) {
			NBTTagCompound data = new NBTTagCompound();
			data.setString("type", "network");
			data.setString("mode", "power");
			double posX = x + 0.5 - dir.offsetX * 0.5 + world.rand.nextDouble() * 0.5 - 0.25;
			double posY = y + 0.5 - dir.offsetY * 0.5 + world.rand.nextDouble() * 0.5 - 0.25;
			double posZ = z + 0.5 - dir.offsetZ * 0.5 + world.rand.nextDouble() * 0.5 - 0.25;
			data.setDouble("mX", dir.offsetX * (red ? 0.025 : 0.1));
			data.setDouble("mY", dir.offsetY * (red ? 0.025 : 0.1));
			data.setDouble("mZ", dir.offsetZ * (red ? 0.025 : 0.1));
			PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacketNT(data, posX, posY, posZ), new TargetPoint(world.provider.dimensionId, posX, posY, posZ, 25));
		}
	}
	
	public default void updateStandardConnections(World world, TileEntity te) {
		updateStandardConnections(world, te.xCoord, te.yCoord, te.zCoord);
	}
		
	public default void updateStandardConnections(World world, int x, int y, int z) {
		
		for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
			this.trySubscribe(world, x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ, dir);
		}
	}
}
