package com.hbm.items.tool;

import java.util.EnumSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import com.hbm.config.SpaceConfig;
import com.hbm.dim.DebugTeleporter;
import com.hbm.lib.Library;
import com.hbm.saveddata.TomSaveData;
import com.hbm.util.PlanetaryTraitUtil;
import com.hbm.util.PlanetaryTraitUtil.Hospitality;
import com.hbm.util.PlanetaryTraitWorldSavedData;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IntHashMap;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class ItemWandD extends Item {

	
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		
		if(world.isRemote)
			return stack;
		
		MovingObjectPosition pos = Library.rayTrace(player, 500, 1, false, true, false);
		
		if(pos != null) {
	
			EntityPlayerMP thePlayer = (EntityPlayerMP) player;
				
			//if(!player.isSneaking())
			//thePlayer.mcServer.getConfigurationManager().transferPlayerToDimension(thePlayer, WorldConfig.ikeDimension, new DebugTeleporter(thePlayer.getServerForPlayer()));
			//else
			//System.out.println(player.dimension);
			
			if(stack.stackTagCompound == null)
			{
				stack.stackTagCompound = new NBTTagCompound();
				stack.stackTagCompound.setInteger("building", 0);
			}
			
			boolean up = player.rotationPitch <= 0.5F;
			
			if(!player.isSneaking())
			{
				Random rand = new Random();
				
				switch(stack.stackTagCompound.getInteger("dim"))
				{
				case 0:
					DebugTeleporter.teleport(player, SpaceConfig.moonDimension, player.posX, 300, player.posZ);
					//thePlayer.mcServer.getConfigurationManager().transferPlayerToDimension(thePlayer, WorldConfig.moonDimension, new DebugTeleporter(thePlayer.getServerForPlayer()));
					break;
				case 1:
					DebugTeleporter.teleport(player, SpaceConfig.ikeDimension, player.posX, 300, player.posZ);
					//thePlayer.mcServer.getConfigurationManager().transferPlayerToDimension(thePlayer, WorldConfig.ikeDimension, new DebugTeleporter(thePlayer.getServerForPlayer()));
					break;
				case 2:
					DebugTeleporter.teleport(player, SpaceConfig.dunaDimension, player.posX, 300, player.posZ);
					//thePlayer.mcServer.getConfigurationManager().transferPlayerToDimension(thePlayer, WorldConfig.dunaDimension, new DebugTeleporter(thePlayer.getServerForPlayer()));
					break;
				case 3:
					DebugTeleporter.teleport(player, 0, player.posX, 300, player.posZ);
				case 4:
					DebugTeleporter.teleport(player, SpaceConfig.eveDimension, player.posX, 300, player.posZ);
					//thePlayer.mcServer.getConfigurationManager().transferPlayerToDimension(thePlayer, WorldConfig.dunaDimension, new DebugTeleporter(thePlayer.getServerForPlayer()));
					break;
				case 5:
					DebugTeleporter.teleport(player, SpaceConfig.dresDimension, player.posX, 300, player.posZ);
					//DebugTeleporter.teleport(player, WorldConfig.eveDimension, player.posX, 300, player.posZ);
					//thePlayer.mcServer.getConfigurationManager().transferPlayerToDimension(thePlayer, WorldConfig.dunaDimension, new DebugTeleporter(thePlayer.getServerForPlayer()));
					break;
				case 6:
					DebugTeleporter.teleport(player, SpaceConfig.mohoDimension, player.posX, 300, player.posZ);
					//DebugTeleporter.teleport(player, WorldConfig.eveDimension, player.posX, 300, player.posZ);
					//thePlayer.mcServer.getConfigurationManager().transferPlayerToDimension(thePlayer, WorldConfig.dunaDimension, new DebugTeleporter(thePlayer.getServerForPlayer()));
					break;
				case 7:
					DebugTeleporter.teleport(player, SpaceConfig.minmusDimension, player.posX, 300, player.posZ);
					break;
				case 8:
					DebugTeleporter.teleport(player, SpaceConfig.laytheDimension, player.posX, 300, player.posZ);
				}
				
				
				
			}
			if(player.isSneaking())
			{
				if(stack.stackTagCompound == null)
				{
					stack.stackTagCompound = new NBTTagCompound();
					stack.stackTagCompound.setInteger("dim", 0);
				} else {
					int i = stack.stackTagCompound.getInteger("dim");
					i++;
					stack.stackTagCompound.setInteger("dim", i);
					if(i >= 9) {
						stack.stackTagCompound.setInteger("dim", 0);
					}
					
					switch(i)
					{
						case 0:
							player.addChatMessage(new ChatComponentText("Dim: Moon"));
							break;
						case 1:
							player.addChatMessage(new ChatComponentText("Dim: Ike"));
							break;
						case 2:
							player.addChatMessage(new ChatComponentText("Dim: Duna"));
							break;
						case 3:
							player.addChatMessage(new ChatComponentText("Dim: Kerbin"));
							break;
						case 4:
							player.addChatMessage(new ChatComponentText("Dim: Eve"));
							break;
						case 5:
							player.addChatMessage(new ChatComponentText("Dim: Dres"));
							break;
						case 6:
							player.addChatMessage(new ChatComponentText("Dim: Moho"));
							break;
						case 7:
							player.addChatMessage(new ChatComponentText("Dim: Minmus"));
							break;
						case 8:
							player.addChatMessage(new ChatComponentText("Dim: Laythe"));
							break;
						default:
							player.addChatMessage(new ChatComponentText("Dim: Moon"));
							break;
						}
					}
				}
			}
		//what this code SHOULD do is strip the traits from moho, and then add the trait that makes it breatheable
			if(world.provider.dimensionId == SpaceConfig.mohoDimension) {
				Set<Hospitality> traits = EnumSet.of(Hospitality.HOT, Hospitality.OXYNEG);
				Set<Hospitality> newtraits = EnumSet.of(Hospitality.BREATHEABLE);
				PlanetaryTraitUtil.removeTraitsFromDimension(world.provider.dimensionId, traits);
				PlanetaryTraitUtil.addTraitsToDimension(world.provider.dimensionId, newtraits);
				
			    // Get the PlanetaryTraitWorldSavedData instance for the world
			    PlanetaryTraitWorldSavedData traitsData = PlanetaryTraitWorldSavedData.get(world);

			    // Set the updated traits in the saved data
			    traitsData.setTraits(world.provider.dimensionId, newtraits);

			    // Mark the saved data as dirty to ensure changes are saved
			    traitsData.markDirty();
				player.addChatMessage(new ChatComponentText("added!" + newtraits));
			}
			if(world.provider.dimensionId == SpaceConfig.moonDimension) {
				Set<Hospitality> traits = EnumSet.of(Hospitality.OXYNEG);
				Set<Hospitality> newtraits = EnumSet.of(Hospitality.BREATHEABLE);
				PlanetaryTraitUtil.removeTraitsFromDimension(world.provider.dimensionId, traits);
				PlanetaryTraitUtil.addTraitsToDimension(world.provider.dimensionId, newtraits);
				
			    // Get the PlanetaryTraitWorldSavedData instance for the world
			    PlanetaryTraitWorldSavedData traitsData = PlanetaryTraitWorldSavedData.get(world);

			    // Set the updated traits in the saved data
			    traitsData.setTraits(world.provider.dimensionId, newtraits);

			    // Mark the saved data as dirty to ensure changes are saved
			    traitsData.markDirty();
				player.addChatMessage(new ChatComponentText("added!" + newtraits));
			}
			/*
			return stack;
			
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
			
			/*TomSaveData data = TomSaveData.forWorld(world);
			/*
			TomSaveData data = TomSaveData.forWorld(world);
			data.impact = true;
			data.fire = 0F;
			data.dust = 0F;
			//data.dtime=(600-pos.blockY);
			//data.time=3600;
			//data.x=pos.blockX;
			//data.z=pos.blockZ;
			data.markDirty();*/
			
			/*EntityTomBlast tom = new EntityTomBlast(world);
			tom.posX = pos.blockX;
			tom.posY = pos.blockY;
			tom.posZ = pos.blockZ;
			tom.destructionRange = 600;
			world.spawnEntityInWorld(tom);
			
			ItemStack itemStack = new ItemStack(ModItems.book_lore);
			BookLoreType.setTypeForStack(itemStack, BookLoreType.TEST_LORE);
			
			player.inventory.addItemStackToInventory(itemStack);
			player.inventoryContainer.detectAndSendChanges();
			
			//use sparingly
			/*int k = ((pos.blockX >> 4) << 4) + 8;
			int l = ((pos.blockZ >> 4) << 4) + 8;
			
			MapGenBunker.Start start = new MapGenBunker.Start(world, world.rand, pos.blockX >> 4, pos.blockZ >> 4);
			start.generateStructure(world, world.rand, new StructureBoundingBox(k - 124, l - 124, k + 15 + 124, l + 15 + 124));*/
			//MapGenStronghold.Start startS = new MapGenStronghold.Start(world, world.rand, pos.blockX >> 4, pos.blockZ >> 4);
			//startS.generateStructure(world, world.rand, new StructureBoundingBox(k - 124, l - 124, k + 15 + 124, l + 15 + 124));
			
			//OilSpot.generateOilSpot(world, pos.blockX, pos.blockZ, 3, 50, true);
			/*
			EntityNukeTorex torex = new EntityNukeTorex(world);
			torex.setPositionAndRotation(pos.blockX, pos.blockY + 1, pos.blockZ, 0, 0);
			torex.setScale(1.5F);
			torex.setType(2);
			world.spawnEntityInWorld(torex);
			TrackerUtil.setTrackingRange(world, torex, 1000);*/
			
			/*EntityTracker entitytracker = ((WorldServer) world).getEntityTracker();
			IntHashMap map = ReflectionHelper.getPrivateValue(EntityTracker.class, entitytracker, "trackedEntityIDs", "field_72794_c");
			EntityTrackerEntry entry = (EntityTrackerEntry) map.lookup(torex.getEntityId());
			entry.blocksDistanceThreshold = 1000;*/
			//TrackerUtil.setTrackingRange(world, torex, 1000);
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
		return stack;
		}
		

	
	



	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool)
	{
		list.add("Used for debugging purposes.");

		if(itemstack.stackTagCompound != null)
		{
			switch(itemstack.stackTagCompound.getInteger("dim"))
			{
			case 0:
				list.add("Dim: Moon");
				break;
			case 1:
				list.add("Dim: Ike");
				break;
			case 2:
				list.add("Dim: Duna");
				break;
			case 3:
				list.add("Dim: Kerbin");
				break;
			case 4:
				list.add("Dim: Eve");
				break;
			case 5:
				list.add("Dim: Dres");
				break;
			case 6:
				list.add("Dim: Moho");
				break;
			case 7:
				list.add("Dim: Minmus");
				break;
	}
}
	}
}