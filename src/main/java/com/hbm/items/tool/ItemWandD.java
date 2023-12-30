package com.hbm.items.tool;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.config.WorldConfig;
import com.hbm.entity.effect.EntityNukeTorex;
import com.hbm.entity.mob.EntityDoner;
import com.hbm.entity.mob.EntityGhost;
import com.hbm.handler.ImpactWorldHandler;
import com.hbm.lib.Library;
import com.hbm.main.ModEventHandlerClient;
import com.hbm.saveddata.TomSaveData;
import com.hbm.util.TrackerUtil;
import com.hbm.world.feature.OilBubble;
import com.hbm.world.generator.DungeonToolbox;
import com.hbm.lib.Library;
import com.hbm.saveddata.TomSaveData;

import cpw.mods.fml.common.eventhandler.Event.Result;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLiving;
import com.hbm.entity.mob.EntityDoner;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.event.ForgeEventFactory;

public class ItemWandD extends Item {
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		if(world.isRemote)
			return stack;
		
		MovingObjectPosition pos = Library.rayTrace(player, 500, 1, false, true, false);
		
		if(pos != null) {
	
			
			
			
			/*ExplosionVNT vnt = new ExplosionVNT(world, pos.hitVec.xCoord, pos.hitVec.yCoord, pos.hitVec.zCoord, 7);
			vnt.setBlockAllocator(new BlockAllocatorBulkie(60));
			vnt.setBlockProcessor(new BlockProcessorStandard().withBlockEffect(new BlockMutatorBulkie(ModBlocks.block_slag)).setNoDrop());
			vnt.setEntityProcessor(new EntityProcessorStandard());
			vnt.setPlayerProcessor(new PlayerProcessorStandard());
			vnt.setSFX(new ExplosionEffectStandard());
			vnt.explode();*/
			
			//PollutionHandler.incrementPollution(world, pos.blockX, pos.blockY, pos.blockZ, PollutionType.SOOT, 15);
			
			/*TimeAnalyzer.startCount("setBlock");
			world.setBlock(pos.blockX, pos.blockY, pos.blockZ, Blocks.dirt);
			TimeAnalyzer.startEndCount("getBlock");
			world.getBlock(pos.blockX, pos.blockY, pos.blockZ);
			TimeAnalyzer.endCount();
			TimeAnalyzer.dump();*/
			//trySpawn(world,  (float)player.posX, (float)player.posY, (float)player.posZ, new EntityDoner(world));
			TomSaveData data = TomSaveData.forWorld(world);
			data.stime = 1;
			data.markDirty();
			if(data.divinity == true) {
				data.divinity = false;
				data.stime = 1;
				data.flash = 0;
				data.markDirty();
			}
			//ModEventHandlerClient.flashTimestamp = System.currentTimeMillis() - 1000;
			MinecraftServer.getServer().getConfigurationManager().sendChatMsg(new ChatComponentText(EnumChatFormatting.RED + "Stellar Event Imminent!"));
			/*EntityTomBlast tom = new EntityTomBlast(world);
			tom.posX = pos.blockX;
			tom.posY = pos.blockY;
			tom.posZ = pos.blockZ;
			tom.destructionRange = 600;
			world.spawnEntityInWorld(tom);*/
			
			/*EntityNukeTorex torex = new EntityNukeTorex(world);
			torex.setPositionAndRotation(pos.blockX, pos.blockY + 1, pos.blockZ, 0, 0);
			torex.setScale(1.5F);
			torex.setType(2);
			world.spawnEntityInWorld(torex);
			TrackerUtil.setTrackingRange(world, torex, 1000);*/
			
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

	public void generateVein(World world, int startX, int startY, int startZ, Block oreBlock, int veinSize) {
	    Random rand = new Random();
	    
	    LinkedList<int[]> blocksToCheck = new LinkedList<>();
	    blocksToCheck.add(new int[]{ startX, startY, startZ });
	    
	    int blocksChanged = 0;

	    while (!blocksToCheck.isEmpty() && blocksChanged < veinSize) {
	        int[] coords = blocksToCheck.poll();
	        int x = coords[0], y = coords[1], z = coords[2];

	        if (world.getBlock(x, y, z) == Blocks.stone) {
	            world.setBlock(x, y, z, oreBlock);
	            blocksChanged++;

	            for (int dx = -1; dx <= 1; dx++) {
	                for (int dy = -1; dy <= 1; dy++) {
	                    for (int dz = -1; dz <= 1; dz++) {
	                        if (rand.nextInt(100) < 20) {  // Reduced to 20% chance to continue the vein
	                            blocksToCheck.add(new int[]{ x + dx, y + dy, z + dz });
	                        }
	                    }
	                }
	            }
	        }
	    }
	}
	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool)
	{
		list.add("Used for debugging purposes.");
	}
}
