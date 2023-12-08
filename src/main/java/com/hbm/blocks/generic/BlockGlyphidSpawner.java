package com.hbm.blocks.generic;

import java.util.*;
import java.util.function.Function;

import com.hbm.config.MobConfig;
import com.hbm.entity.mob.EntityGlyphid;
import com.hbm.entity.mob.EntityGlyphidBehemoth;
import com.hbm.entity.mob.EntityGlyphidBlaster;
import com.hbm.entity.mob.EntityGlyphidBombardier;
import com.hbm.entity.mob.EntityGlyphidBrawler;
import com.hbm.entity.mob.EntityGlyphidBrenda;
import com.hbm.entity.mob.EntityGlyphidNuclear;
import com.hbm.entity.mob.EntityGlyphidScout;
import com.hbm.handler.pollution.PollutionHandler;
import com.hbm.handler.pollution.PollutionHandler.PollutionType;
import com.hbm.items.ModItems;


import com.hbm.util.Tuple.Pair;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

public class BlockGlyphidSpawner extends BlockContainer {

	public BlockGlyphidSpawner(Material p_i45386_1_) {
		super(p_i45386_1_);
	}

	@Override
	public Item getItemDropped(int meta, Random rand, int fortune) {
		return ModItems.egg_glyphid;
	}
	private static final ArrayList<Pair<Function<World, EntityGlyphid>, int[]>> spawnMap = new ArrayList<>();

	static{
			//big thanks to martin for the suggestion of using functions
			spawnMap.add(new Pair<>(EntityGlyphid::new, MobConfig.glyphidChance));
			spawnMap.add(new Pair<>(EntityGlyphidBombardier::new, MobConfig.bombardierChance));
			spawnMap.add(new Pair<>(EntityGlyphidBrawler::new, MobConfig.brawlerChance));
			spawnMap.add(new Pair<>(EntityGlyphidBlaster::new, MobConfig.blasterChance));
			spawnMap.add(new Pair<>(EntityGlyphidBehemoth::new, MobConfig.behemothChance));
			spawnMap.add(new Pair<>(EntityGlyphidBrenda::new, MobConfig.brendaChance));
			spawnMap.add(new Pair<>(EntityGlyphidNuclear::new, MobConfig.johnsonChance));
	}

	@Override
	public int quantityDropped(int meta, int fortune, Random rand) {
		return 1 + rand.nextInt(3) + fortune;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityGlpyhidSpawner();
	}

	public static class TileEntityGlpyhidSpawner extends TileEntity {

		boolean initialSpawn = true;

		@Override
		public void updateEntity() {
            float soot;

			if(!worldObj.isRemote && this.worldObj.difficultySetting != EnumDifficulty.PEACEFUL) {

				if (initialSpawn || worldObj.getTotalWorldTime() % MobConfig.swarmCooldown == 0) {

					if (worldObj.getBlock(xCoord, yCoord + 1, zCoord) != Blocks.air) {
						return;
					}
					int count = 0;

					for (Object e : worldObj.loadedEntityList) {
						if (e instanceof EntityGlyphid) {
							count++;
							if (count >= MobConfig.spawnMax) return;
						}
					}

					List<EntityGlyphid> list = worldObj.getEntitiesWithinAABB(EntityGlyphid.class, AxisAlignedBB.getBoundingBox(xCoord - 6, yCoord + 1, zCoord - 6, xCoord + 7, yCoord + 9, zCoord + 7));
					soot = PollutionHandler.getPollution(worldObj, xCoord, yCoord, zCoord, PollutionType.SOOT);

					if (list.size() <= 3) {

						ArrayList<EntityGlyphid> currentSwarm = createSwarm(soot);

						for (EntityGlyphid glyphid : currentSwarm) {
							glyphid.setLocationAndAngles(xCoord + 0.5, yCoord + 1, zCoord + 0.5, worldObj.rand.nextFloat() * 360.0F, 0.0F);
							worldObj.spawnEntityInWorld(glyphid);
							glyphid.moveEntity(worldObj.rand.nextGaussian(), 0, worldObj.rand.nextGaussian());
						}

						if (!initialSpawn && worldObj.rand.nextInt(MobConfig.scoutSwarmSpawnChance + 1) == 0 && soot >= MobConfig.scoutThreshold) {
							EntityGlyphidScout scout = new EntityGlyphidScout(worldObj);
							scout.setLocationAndAngles(xCoord + 0.5, yCoord + 1, zCoord + 0.5, worldObj.rand.nextFloat() * 360.0F, 0.0F);
							worldObj.spawnEntityInWorld(scout);
						}

						initialSpawn = false;

					}

				}
			}
		}

		public ArrayList<EntityGlyphid> createSwarm(float soot) {

			Random rand = new Random();

			ArrayList<EntityGlyphid> currentSpawns = new ArrayList<>();
			
			int swarmAmount = (int) Math.min(MobConfig.baseSwarmSize * Math.max(MobConfig.swarmScalingMult * (soot / MobConfig.sootStep), 1), 10);

			while(currentSpawns.size() <= swarmAmount) {
				 //(dys)functional programing
				 for (Pair<Function<World, EntityGlyphid>, int[]> glyphid : spawnMap) {

					 int[] chance = glyphid.getValue();
					 int adjustedChance = (int) (chance[0] + (chance[1] - chance[1] / Math.max(((soot + 1)/3 ), 1)));
					 if (rand.nextInt(100) <= adjustedChance) {
						 currentSpawns.add(glyphid.getKey().apply(worldObj));
					 }
				 }
			}
			return currentSpawns;
		}

		@Override
		public void writeToNBT(NBTTagCompound nbt) {
			super.writeToNBT(nbt);
			nbt.setBoolean("initialSpawn", initialSpawn);
		}

		@Override
		public void readFromNBT(NBTTagCompound nbt) {
			super.readFromNBT(nbt);
			this.initialSpawn = nbt.getBoolean("initialSpawn");
		}
	}
}
