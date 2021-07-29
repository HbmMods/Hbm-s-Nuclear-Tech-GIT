package com.hbm.blocks.bomb;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.entity.projectile.EntityShrapnel;
import com.hbm.explosion.ExplosionNT;
import com.hbm.explosion.ExplosionNT.ExAttrib;
import com.hbm.packet.AuxParticlePacketNT;
import com.hbm.packet.PacketDispatcher;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class BlockVolcano extends Block {

	public BlockVolcano() {
		super(Material.iron);
	}
	
	@Override
	public int tickRate(World world) {
		return 5;
	}

	@Override
	public void onBlockAdded(World world, int x, int y, int z) {
		
		if(!world.isRemote)
			world.scheduleBlockUpdate(x, y, z, this, this.tickRate(world));
	}

	@Override
	public void updateTick(World world, int x, int y, int z, Random rand) {

		if(!world.isRemote) {
			List<ExAttrib> attribs = Arrays.asList(new ExAttrib[] {ExAttrib.NODROP, ExAttrib.LAVA_V, ExAttrib.NOSOUND, ExAttrib.ALLMOD, ExAttrib.NOHURT});
			
			ExplosionNT explosion = new ExplosionNT(world, null, x + 0.5, y + rand.nextInt(15) + 1.5, z + 0.5, 7);
			explosion.addAllAttrib(attribs);
			explosion.explode();
			
			ExplosionNT explosion2 = new ExplosionNT(world, null, x + 0.5 + rand.nextGaussian() * 3, rand.nextInt(y), z + 0.5 + rand.nextGaussian() * 3, 10);
			explosion2.addAllAttrib(attribs);
			explosion2.explode();
			
			world.scheduleBlockUpdate(x, y, z, this, this.tickRate(world));

			int rX = x - 10 + rand.nextInt(21);
			int rY = y + rand.nextInt(11);
			int rZ = z - 10 + rand.nextInt(21);
			
			if(world.getBlock(rX, rY, rZ) == Blocks.air && world.getBlock(rX, rY - 1, rZ) == ModBlocks.volcanic_lava_block)
				world.setBlock(rX, rY, rZ, ModBlocks.volcanic_lava_block);
			
			for(int i = 0; i < 3; i++) {
				EntityShrapnel frag = new EntityShrapnel(world);
				frag.setLocationAndAngles(x + 0.5, y + 1.5, z + 0.5, 0.0F, 0.0F);
				frag.motionY = 1D + rand.nextDouble();
				frag.motionX = rand.nextGaussian() * 0.2D;
				frag.motionZ = rand.nextGaussian() * 0.2D;
				frag.setVolcano(true);
				world.spawnEntityInWorld(frag);
			}
			
			NBTTagCompound dPart = new NBTTagCompound();
			dPart.setString("type", "vanillaExt");
			dPart.setString("mode", "volcano");
			PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacketNT(dPart, x + 0.5, y + 10, z + 0.5), new TargetPoint(world.provider.dimensionId, x + 0.5, y + 10, z + 0.5, 250));
		}
	}
}
