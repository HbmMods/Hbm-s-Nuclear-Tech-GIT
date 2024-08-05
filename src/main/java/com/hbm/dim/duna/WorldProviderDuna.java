package com.hbm.dim.duna;

import com.hbm.blocks.ModBlocks;
import com.hbm.dim.WorldChunkManagerCelestial;
import com.hbm.dim.WorldProviderCelestial;
import com.hbm.dim.WorldTypeTeleport;
import com.hbm.dim.WorldChunkManagerCelestial.BiomeGenLayers;
import com.hbm.dim.duna.GenLayerDuna.GenLayerDiversifyDuna;
import com.hbm.dim.duna.GenLayerDuna.GenLayerDunaBiomes;
import com.hbm.dim.duna.GenLayerDuna.GenLayerDunaLowlands;
import com.hbm.util.ParticleUtil;

import io.netty.buffer.ByteBuf;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Vec3;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.GenLayerFuzzyZoom;
import net.minecraft.world.gen.layer.GenLayerRiver;
import net.minecraft.world.gen.layer.GenLayerRiverMix;
import net.minecraft.world.gen.layer.GenLayerSmooth;
import net.minecraft.world.gen.layer.GenLayerVoronoiZoom;
import net.minecraft.world.gen.layer.GenLayerZoom;

public class WorldProviderDuna extends WorldProviderCelestial {

	@Override
	public void registerWorldChunkManager() {
		this.worldChunkMgr = new WorldChunkManagerCelestial(createBiomeGenerators(worldObj.getSeed()));
	}

	@Override
	public String getDimensionName() {
		return "Duna";
	}
	
	@Override
	public IChunkProvider createChunkGenerator() {
		return new ChunkProviderDuna(this.worldObj, this.getSeed(), false);
	}


	private int dustStormTimer = 0;
	private float dustStormIntensity = 1;

	@Override
	public void updateWeather() {
		super.updateWeather();

		if(!worldObj.isRemote) {
			if(dustStormTimer <= 0) {
				if(dustStormIntensity >= 0.5F) {
					dustStormIntensity = 0;
					dustStormTimer = worldObj.rand.nextInt(168000) + 12000;
				} else {
					dustStormIntensity = worldObj.rand.nextFloat() * 0.5F + 0.5F;
					dustStormTimer = worldObj.rand.nextInt(12000) + 12000;
				}
			}

			dustStormTimer--;
		} else {
			if(dustStormIntensity >= 0.5F) {
				EntityLivingBase viewEntity = Minecraft.getMinecraft().renderViewEntity;
				Vec3 vec = Vec3.createVectorHelper(20, 0, 50);
				vec.rotateAroundZ((float)(worldObj.rand.nextDouble() * Math.PI * 10));
				vec.rotateAroundY((float)(worldObj.rand.nextDouble() * Math.PI * 2 * 5));
				ParticleUtil.spawnDustFlame(worldObj, viewEntity.posX + vec.xCoord, viewEntity.posY, viewEntity.posZ + vec.zCoord, -4, 0, 0);
			}
		}
	}

	@Override
	public float fogDensity() {
		if(dustStormIntensity >= 0.5F)
			return dustStormIntensity * dustStormIntensity * 0.05F;

		return super.fogDensity();
	}

	@Override
	public boolean isDaytime() {
		if(dustStormIntensity >= 0.5F) return false;
		return super.isDaytime();
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setInteger("dustStormTimer", dustStormTimer);
		nbt.setFloat("dustStormIntensity", dustStormIntensity);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		dustStormTimer = nbt.getInteger("dustStormTimer");
		dustStormIntensity = nbt.getFloat("dustStormIntensity");
	}

	@Override
	public void serialize(ByteBuf buf) {
		super.serialize(buf);
		buf.writeFloat(dustStormIntensity);
	}

	@Override
	public void deserialize(ByteBuf buf) {
		super.deserialize(buf);
		dustStormIntensity = buf.readFloat();
	}

	@Override
	public void resetRainAndThunder() {
		super.resetRainAndThunder();
		dustStormIntensity = 0;
		dustStormTimer = worldObj.rand.nextInt(168000) + 12000;
	}

	@Override
	public Block getStone() {
		return ModBlocks.duna_rock;
	}

	@Override
	public double getHorizon() {
		return 52;
	}

	@Override
	public int getRespawnDimension(EntityPlayerMP player) {
		// BRING
		//  HIM
		// HOMIE
		if(worldObj.getWorldInfo().getTerrainType() == WorldTypeTeleport.martian)
			return dimensionId;

		return super.getRespawnDimension(player);
	}

	private static BiomeGenLayers createBiomeGenerators(long seed) {
		GenLayer biomes = new GenLayerDunaBiomes(seed);
		
		biomes = new GenLayerFuzzyZoom(2000L, biomes);
		biomes = new GenLayerZoom(2001L, biomes);
		biomes = new GenLayerDiversifyDuna(1000L, biomes);
		biomes = new GenLayerZoom(1000L, biomes);
		biomes = new GenLayerDiversifyDuna(1001L, biomes);
		biomes = new GenLayerZoom(1001L, biomes);
		biomes = new GenLayerDunaLowlands(1300L, biomes);
		biomes = new GenLayerZoom(1003L, biomes);
		biomes = new GenLayerSmooth(700L, biomes);
		biomes = new GenLayerZoom(1005L, biomes);
		biomes = new GenLayerSmooth(703L, biomes);
		biomes = new GenLayerFuzzyZoom(1000L, biomes);
		biomes = new GenLayerSmooth(705L, biomes);
		biomes = new GenLayerFuzzyZoom(1001L, biomes);
		biomes = new GenLayerSmooth(706L, biomes);
		biomes = new GenLayerFuzzyZoom(1002L, biomes);
		biomes = new GenLayerZoom(1006L, biomes);
		
		GenLayer genlayerVoronoiZoom = new GenLayerVoronoiZoom(10L, biomes);

		GenLayer genlayerRiverZoom = new GenLayerZoom(1000L, biomes);
		GenLayer genlayerRiver = new GenLayerRiver(1004L, genlayerRiverZoom); // Your custom river layer
		genlayerRiver = new GenLayerZoom(105L, genlayerRiver);

		GenLayer genlayerRiverMix = new GenLayerRiverMix(100L, biomes, genlayerRiver);

		return new BiomeGenLayers(genlayerRiverMix, genlayerVoronoiZoom, seed);
	}

}