package api.hbm.energy;

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
		
		this.setPower(this.getPower() + power);
		
		if(this.getPower() > this.getMaxPower()) {
			
			long overshoot = this.getPower() - this.getMaxPower();
			this.setPower(this.getMaxPower());
			return overshoot;
		}
		
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
		
		// first we make sure we're not subscribed to the network that we'll be supplying
		if(te instanceof IEnergyConductor) {
			IEnergyConductor con = (IEnergyConductor) te;
			
			if(con.getPowerNet() != null && con.getPowerNet().isSubscribed(this)) {
				con.getPowerNet().unsubscribe(this);
				wasSubscribed = true;
			}
		}
		
		//then we add energy
		if(te instanceof IEnergyConnector) {
			IEnergyConnector con = (IEnergyConnector) te;
			
			if(con.canConnect(dir)) {
				long oldPower = this.getPower();
				long transfer = oldPower - con.transferPower(oldPower);
				this.setPower(oldPower - transfer);
			}
		}
		
		//then we subscribe if possible
		if(wasSubscribed && te instanceof IEnergyConductor) {
			IEnergyConductor con = (IEnergyConductor) te;
			
			if(con.getPowerNet() != null && !con.getPowerNet().isSubscribed(this)) {
				con.getPowerNet().subscribe(this);
			}
		}
	}
	
	public default void updateStandardConnections(World world, int x, int y, int z) {
		
		for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS)
			this.trySubscribe(world, x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ);
	}
}
