package com.hbm.uninos.networkproviders;

import com.hbm.handler.threading.PacketThreading;
import com.hbm.packet.toclient.AuxParticlePacketNT;
import com.hbm.uninos.INetworkProvider;
import com.hbm.util.fauxpointtwelve.BlockPos;
import com.hbm.util.fauxpointtwelve.DirPos;

import api.hbm.energymk2.Nodespace.PowerNode;
import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import api.hbm.energymk2.PowerNetMK2;

public class PowerNetProvider implements INetworkProvider<PowerNetMK2> {

	@Override
	public PowerNetMK2 provideNetwork() {
		return new PowerNetMK2();
	}

	@Override
	public PowerNode provideNode(BlockPos... positions) {
		return new PowerNode(positions);
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
		data.setString("mode", "power");
		double posX = x + 0.5 - dir.offsetX * 0.5 + world.rand.nextDouble() * 0.5 - 0.25;
		double posY = y + 0.5 - dir.offsetY * 0.5 + world.rand.nextDouble() * 0.5 - 0.25;
		double posZ = z + 0.5 - dir.offsetZ * 0.5 + world.rand.nextDouble() * 0.5 - 0.25;
		data.setDouble("mX", dir.offsetX * motion);
		data.setDouble("mY", dir.offsetY * motion);
		data.setDouble("mZ", dir.offsetZ * motion);
		PacketThreading.createAllAroundThreadedPacket(new AuxParticlePacketNT(data, posX, posY, posZ), new TargetPoint(world.provider.dimensionId, posX, posY, posZ, 25));
	}
}
