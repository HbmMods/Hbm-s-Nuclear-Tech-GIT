package com.hbm.blocks.machine.rbmk;

import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.main.MainRegistry;
import com.hbm.packet.AuxParticlePacketNT;
import com.hbm.packet.PacketDispatcher;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class RBMKDebrisBurning extends RBMKDebris {

	@Override
	public int tickRate(World world) {

		return 100 + world.rand.nextInt(20);
	}

	@Override
	public void updateTick(World world, int x, int y, int z, Random rand) {
		
		if(!world.isRemote) {
			
			if(rand.nextInt(5) == 0) {
				NBTTagCompound data = new NBTTagCompound();
				data.setString("type", "rbmkflame");
				data.setInteger("maxAge", 300);
				PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacketNT(data, x + 0.25 + rand.nextDouble() * 0.5, y + 1.75, z + 0.25 + rand.nextDouble() * 0.5), new TargetPoint(world.provider.dimensionId, x + 0.5, y + 1.75, z + 0.5, 75));
				MainRegistry.proxy.effectNT(data);
			}
			
			if(rand.nextInt(100) == 0) {
				world.setBlock(x, y, z, ModBlocks.pribris);
			} else {
				world.scheduleBlockUpdate(x, y, z, this, this.tickRate(world));
			}
		}
	}

	public void onBlockAdded(World world, int x, int y, int z) {
		super.onBlockAdded(world, x, y, z);

		if(!world.isRemote) {
			if(world.rand.nextInt(3) == 0) {
				NBTTagCompound data = new NBTTagCompound();
				data.setString("type", "rbmkflame");
				data.setInteger("maxAge", 300);
				PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacketNT(data, x + 0.25 + world.rand.nextDouble() * 0.5, y + 1.75, z + 0.25 + world.rand.nextDouble() * 0.5), new TargetPoint(world.provider.dimensionId, x + 0.5, y + 1.75, z + 0.5, 75));
				MainRegistry.proxy.effectNT(data);
			}
		}

		world.scheduleBlockUpdate(x, y, z, this, this.tickRate(world));
	}
}
