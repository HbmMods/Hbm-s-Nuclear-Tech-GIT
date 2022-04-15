package api.hbm.fluid;

import com.hbm.inventory.fluid.FluidType;
import com.hbm.packet.AuxParticlePacketNT;
import com.hbm.packet.PacketDispatcher;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public interface IFluidUser extends IFluidConnector {
	
	public default void sendFluid(FluidType type, World world, int x, int y, int z, ForgeDirection dir) {
		
		TileEntity te = world.getTileEntity(x, y, z);
		boolean wasSubscribed = false;
		boolean red = false;
		
		if(te instanceof IFluidConductor) {
			IFluidConductor con = (IFluidConductor) te;
			
			if(con.getPipeNet(type) != null && con.getPipeNet(type).isSubscribed(this)) {
				con.getPipeNet(type).unsubscribe(this);
				wasSubscribed = true;
			}
		}
		
		if(te instanceof IFluidConnector) {
			IFluidConnector con = (IFluidConnector) te;
			
			if(con.canConnect(type, dir.getOpposite())) {
				long toSend = this.getTotalFluidForSend(type);
				long transfer = toSend - con.transferFluid(type, toSend);
				this.removeFluidForTransfer(type, transfer);
				red = true;
			}
		}
		
		if(wasSubscribed && te instanceof IFluidConductor) {
			IFluidConductor con = (IFluidConductor) te;
			
			if(con.getPipeNet(type) != null && !con.getPipeNet(type).isSubscribed(this)) {
				con.getPipeNet(type).subscribe(this);
			}
		}
		
		if(particleDebug) {
			NBTTagCompound data = new NBTTagCompound();
			data.setString("type", "vanillaExt");
			data.setString("mode", red ? "reddust" : "greendust");
			PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacketNT(data, x + world.rand.nextDouble(), y + world.rand.nextDouble(), z + world.rand.nextDouble()), new TargetPoint(world.provider.dimensionId, x + 0.5, y + 0.5, z + 0.5, 25));
		}
	}
	
	public default void sendFluidToAll(FluidType type, TileEntity te) {
		
		for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
			sendFluid(type, te.getWorldObj(), te.xCoord + dir.offsetX, te.yCoord + dir.offsetY, te.zCoord + dir.offsetZ, dir);
		}
	}

	public default long getTotalFluidForSend(FluidType type) { return 0; }
	public default void removeFluidForTransfer(FluidType type, long amount) { }
	
	public default void updateStandardPipes(FluidType type, World world, int x, int y, int z) {
		
		for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS)
			this.trySubscribe(type, world, x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ, dir);
	}
}
