package com.hbm.items.tool;

import java.util.List;
import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.config.GeneralConfig;
import com.hbm.itempool.ItemPool;
import com.hbm.itempool.ItemPoolsSingle;
import com.hbm.lib.Library;
import com.hbm.main.MainRegistry;

import com.hbm.tileentity.machine.storage.TileEntitySafe;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;

public class ItemWandD extends Item {

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {

		if(world.isRemote)
			return stack;

		MovingObjectPosition pos = Library.rayTrace(player, 500, 1, false, true, false);

		if(pos != null) {

			int y = world.getHeightValue(pos.blockX, pos.blockZ);

			Random rand = new Random();

			if(world.getBlock(pos.blockX, y - 1, pos.blockZ).canPlaceTorchOnTop(world, pos.blockX, y - 1, pos.blockZ)) {
				world.setBlock(pos.blockX, y, pos.blockZ, ModBlocks.safe, rand.nextInt(4) + 2, 2);
				TileEntitySafe safe = (TileEntitySafe) world.getTileEntity(pos.blockX, y, pos.blockZ);

				switch(rand.nextInt(10)) {
					case 0: case 1: case 2: case 3:
						safe.setMod(1);
						WeightedRandomChestContent.generateChestContents(rand, ItemPool.getPool(ItemPoolsSingle.POOL_VAULT_RUSTY), safe, rand.nextInt(4) + 3);
						break;
					case 4: case 5: case 6:
						safe.setMod(0.1);
						WeightedRandomChestContent.generateChestContents(rand, ItemPool.getPool(ItemPoolsSingle.POOL_VAULT_STANDARD), safe, rand.nextInt(3) + 2);
						break;
					case 7: case 8:
						safe.setMod(0.02);
						WeightedRandomChestContent.generateChestContents(rand, ItemPool.getPool(ItemPoolsSingle.POOL_VAULT_REINFORCED), safe, rand.nextInt(3) + 1);
						break;
					case 9:
						safe.setMod(0.0);
						WeightedRandomChestContent.generateChestContents(rand, ItemPool.getPool(ItemPoolsSingle.POOL_VAULT_UNBREAKABLE), safe, rand.nextInt(2) + 1);
						break;
				}

				safe.setPins(rand.nextInt(999) + 1);
				safe.lock();

				safe.fillWithSpiders(); // debug

				if(GeneralConfig.enableDebugMode)
					MainRegistry.logger.info("[Debug] Successfully spawned safe at " + pos.blockX + " " + (y + 1) +" " + pos.blockZ);
			}

			/*ExplosionVNT vnt = new ExplosionVNT(world, pos.hitVec.xCoord, pos.hitVec.yCoord, pos.hitVec.zCoord, 7);
			vnt.setBlockAllocator(new BlockAllocatorBulkie(60));
			vnt.setBlockProcessor(new BlockProcessorStandard().withBlockEffect(new BlockMutatorBulkie(ModBlocks.block_slag)).setNoDrop());
			vnt.setEntityProcessor(new EntityProcessorStandard());
			vnt.setPlayerProcessor(new PlayerProcessorStandard());
			vnt.setSFX(new ExplosionEffectStandard());
			vnt.explode();*/

			//PollutionHandler.incrementPollution(world, pos.blockX, pos.blockY, pos.blockZ, PollutionType.SOOT, 15);

			/*int i = pos.blockX >> 4;
			int j = pos.blockZ >> 4;

			i = (i << 4) + 8;
			j = (j << 4) + 8;
			Component comp = new RuralHouse1(world.rand, i, j);
			comp.addComponentParts(world, world.rand, new StructureBoundingBox(i, j, i + 32, j + 32));*/

			/*
			ExplosionVNT vnt = new ExplosionVNT(world, pos.blockX + 0.5, pos.blockY + 1, pos.blockZ + 0.5, 15F);
			vnt.makeStandard();
			vnt.setSFX();
			vnt.setBlockAllocator(new BlockAllocatorStandard(32));
			vnt.explode();

			ExplosionCreator.composeEffectStandard(world, pos.blockX + 0.5, pos.blockY + 0.5, pos.blockZ + 0.5);
			*/

			/*for(int i = 0; i < 10; i++) {
				NBTTagCompound data = new NBTTagCompound();
				data.setString("type", "debris");
				PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacketNT(data, pos.blockX + world.rand.nextGaussian() * 3, pos.blockY - 2, pos.blockZ + world.rand.nextGaussian() * 3), new TargetPoint(world.provider.dimensionId, pos.blockX, pos.blockY, pos.blockZ, 100));
			}
			NBTTagCompound data = new NBTTagCompound();
			data.setString("type", "oomph");
			PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacketNT(data, pos.blockX, pos.blockY, pos.blockZ), new TargetPoint(world.provider.dimensionId, pos.blockX, pos.blockY, pos.blockZ, 100));*/

			/*TimeAnalyzer.startCount("setBlock");
			world.setBlock(pos.blockX, pos.blockY, pos.blockZ, Blocks.dirt);
			TimeAnalyzer.startEndCount("getBlock");
			world.getBlock(pos.blockX, pos.blockY, pos.blockZ);
			TimeAnalyzer.endCount();
			TimeAnalyzer.dump();*/

			/*TomSaveData data = TomSaveData.forWorld(world);
			data.impact = true;
			data.fire = 0F;
			data.dust = 0F;
			data.markDirty();*/

			/*for(int i = -5; i <= 5; i++) {
				for(int j = -5; j <= 5; j++) {
					WorldUtil.setBiome(world, pos.blockX + i, pos.blockZ + j, BiomeGenCraterBase.craterBiome);
				}
			}

			WorldUtil.syncBiomeChange(world, pos.blockX, pos.blockZ);*/

			/*EntityTomBlast tom = new EntityTomBlast(world);
			tom.posX = pos.blockX;
			tom.posY = pos.blockY;
			tom.posZ = pos.blockZ;
			tom.destructionRange = 600;
			world.spawnEntityInWorld(tom);*/

			/*List<EntityNukeTorex> del = world.getEntitiesWithinAABB(EntityNukeTorex.class, AxisAlignedBB.getBoundingBox(pos.blockX, pos.blockY + 1, pos.blockZ, pos.blockX, pos.blockY + 1, pos.blockZ).expand(50, 50, 50));

			if(!del.isEmpty()) {
				for(EntityNukeTorex torex : del) torex.setDead();
			} else {
				EntityNukeTorex.statFac(world, pos.blockX, pos.blockY + 1, pos.blockZ, 150);
			}*/

			/*EntityTracker entitytracker = ((WorldServer) world).getEntityTracker();
			IntHashMap map = ReflectionHelper.getPrivateValue(EntityTracker.class, entitytracker, "trackedEntityIDs", "field_72794_c");
			EntityTrackerEntry entry = (EntityTrackerEntry) map.lookup(torex.getEntityId());
			entry.blocksDistanceThreshold = 1000;*/
			//world.spawnEntityInWorld(EntityNukeExplosionMK5.statFacNoRad(world, 150, pos.blockX, pos.blockY + 1, pos.blockZ));

			//DungeonToolbox.generateBedrockOreWithChance(world, world.rand, pos.blockX, pos.blockZ, EnumBedrockOre.TITANIUM,	new FluidStack(Fluids.SULFURIC_ACID, 500), 2, 1);

			/*EntitySiegeTunneler tunneler = new EntitySiegeTunneler(world);
			tunneler.setPosition(pos.blockX, pos.blockY + 1, pos.blockZ);
			tunneler.onSpawnWithEgg(null);
			world.spawnEntityInWorld(tunneler);*/

			//CellularDungeonFactory.meteor.generate(world, x, y, z, world.rand);

			/*int r = 5;

			int x = pos.blockX;
			int y = pos.blockY;
			int z = pos.blockZ;
			for(int i = x - r; i <= x + r; i++) {
				for(int j = y - r; j <= y + r; j++) {
					for(int k = z - r; k <= z + r; k++) {
						if(world.getBlock(i, j, k) == ModBlocks.concrete_super)
							world.getBlock(i, j, k).updateTick(world, i, j, k, world.rand);
					}
				}
			}*/

			//new Bunker().generate(world, world.rand, x, y, z);

			/*EntityBlockSpider spider = new EntityBlockSpider(world);
			spider.setPosition(x + 0.5, y, z + 0.5);
			spider.makeBlock(world.getBlock(x, y, z), world.getBlockMetadata(x, y, z));
			world.setBlockToAir(x, y, z);
			world.spawnEntityInWorld(spider);*/


    		/*NBTTagCompound data = new NBTTagCompound();
    		data.setString("type", "rift");
    		data.setDouble("posX", x);
    		data.setDouble("posY", y + 1);
    		data.setDouble("posZ", z);

    		MainRegistry.proxy.effectNT(data);*/

			//new Spaceship().generate_r0(world, world.rand, x - 4, y, z - 8);

			//new Ruin001().generate_r0(world, world.rand, x, y - 8, z);

			//CellularDungeonFactory.jungle.generate(world, x, y, z, world.rand);
			//CellularDungeonFactory.jungle.generate(world, x, y + 4, z, world.rand);
			//CellularDungeonFactory.jungle.generate(world, x, y + 8, z, world.rand);

			//new AncientTomb().build(world, world.rand, x, y + 10, z);

			//new ArcticVault().trySpawn(world, x, y, z);

			/*for(int ix = x - 10; ix <= x + 10; ix++) {
				for(int iz = z - 10; iz <= z + 10; iz++) {

					if(ix % 2 == 0 && iz % 2 == 0) {
						for(int iy = y; iy < y + 4; iy++)
							world.setBlock(ix, iy, iz, ModBlocks.brick_dungeon_flat);
						world.setBlock(ix, y + 4, iz, ModBlocks.brick_dungeon_tile);
					} else if(ix % 2 == 1 && iz % 2 == 1) {
						world.setBlock(ix, y, iz, ModBlocks.reinforced_stone);
						world.setBlock(ix, y + 1, iz, ModBlocks.spikes);
					} else if(world.rand.nextInt(3) == 0) {
						for(int iy = y; iy < y + 4; iy++)
							world.setBlock(ix, iy, iz, ModBlocks.brick_dungeon_flat);
						world.setBlock(ix, y + 4, iz, ModBlocks.brick_dungeon_tile);
					} else {
						world.setBlock(ix, y, iz, ModBlocks.reinforced_stone);
						world.setBlock(ix, y + 1, iz, ModBlocks.spikes);
					}
				}
			}*/
		}

		return stack;
	}

	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool)
	{
		list.add("Used for debugging purposes.");
	}
}
