package com.hbm.blocks.machine.rbmk;

import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.handler.threading.PacketThreading;
import com.hbm.main.MainRegistry;
import com.hbm.packet.toclient.AuxParticlePacketNT;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

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
				PacketThreading.createAllAroundThreadedPacket(new AuxParticlePacketNT(data, x + 0.25 + rand.nextDouble() * 0.5, y + 1.75, z + 0.25 + rand.nextDouble() * 0.5), new TargetPoint(world.provider.dimensionId, x + 0.5, y + 1.75, z + 0.5, 75));
				MainRegistry.proxy.effectNT(data);
				world.playSoundEffect(x + 0.5F, y + 0.5, z + 0.5, "fire.fire", 1.0F + rand.nextFloat(), rand.nextFloat() * 0.7F + 0.3F);
			}

			ForgeDirection dir = ForgeDirection.getOrientation(rand.nextInt(6));
			Block block = world.getBlock(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ);

			if(rand.nextInt(10) == 0 && block == Blocks.air) {
				world.setBlock(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ, ModBlocks.gas_meltdown);
			}

			//Foam helps stop the fire; Boron smothers it. 1.66% chance every 100-120 seconds for one side
			int chance = block == ModBlocks.foam_layer || block == ModBlocks.block_foam ||
					block == ModBlocks.sand_boron_layer || block == ModBlocks.sand_boron ? 10 : 100;

			if(rand.nextInt(chance) == 0) {
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
				PacketThreading.createAllAroundThreadedPacket(new AuxParticlePacketNT(data, x + 0.25 + world.rand.nextDouble() * 0.5, y + 1.75, z + 0.25 + world.rand.nextDouble() * 0.5), new TargetPoint(world.provider.dimensionId, x + 0.5, y + 1.75, z + 0.5, 75));
				MainRegistry.proxy.effectNT(data);
			}
		}

		world.scheduleBlockUpdate(x, y, z, this, this.tickRate(world));
	}
}
