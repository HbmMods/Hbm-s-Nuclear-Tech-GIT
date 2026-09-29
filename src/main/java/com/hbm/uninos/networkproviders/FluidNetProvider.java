package com.hbm.uninos.networkproviders;

import com.hbm.handler.threading.PacketThreading;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.packet.toclient.AuxParticlePacketNT;
import com.hbm.uninos.INetworkProvider;
import com.hbm.util.fauxpointtwelve.BlockPos;
import com.hbm.util.fauxpointtwelve.DirPos;

import api.hbm.fluidmk2.FluidNetMK2;
import api.hbm.fluidmk2.FluidNode;
import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class FluidNetProvider implements INetworkProvider<FluidNetMK2> {
	
	protected FluidType type;
	
	public FluidNetProvider(FluidType type) {
		this.type = type;
	}

	@Override
	public FluidNetMK2 provideNetwork() {
		return new FluidNetMK2(type);
	}

	@Override
	public FluidNode provideNode(BlockPos... positions) {
		return new FluidNode(this, positions);
	}

	@Override
	public void spawnDebugParticles(World world, DirPos pos, boolean receive) {
		ForgeDirection dir = pos.getDir();
		BlockPos offset = pos.offset(dir);

		int x = offset.getX();
		int y = offset.getY();
		int z = offset.getZ();
		
		double motion = receive ? -0.1 : 0.1;
		
		if(!receive) {
			x -= dir.offsetX;
			y -= dir.offsetY;
			z -= dir.offsetZ;
		}
		
		NBTTagCompound data = new NBTTagCompound();
		data.setString("type", "network");
		data.setString("mode", "fluid");
		data.setInteger("color", type.getColor());
		double posX = x + 0.5 - dir.offsetX * 0.5 + world.rand.nextDouble() * 0.5 - 0.25;
		double posY = y + 0.5 - dir.offsetY * 0.5 + world.rand.nextDouble() * 0.5 - 0.25;
		double posZ = z + 0.5 - dir.offsetZ * 0.5 + world.rand.nextDouble() * 0.5 - 0.25;
		data.setDouble("mX", dir.offsetX * motion);
		data.setDouble("mY", dir.offsetY * motion);
		data.setDouble("mZ", dir.offsetZ * motion);
		PacketThreading.createAllAroundThreadedPacket(new AuxParticlePacketNT(data, posX, posY, posZ), new TargetPoint(world.provider.dimensionId, posX, posY, posZ, 25));
	}
}
