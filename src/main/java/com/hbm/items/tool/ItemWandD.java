package com.hbm.items.tool;

import java.util.List;

import com.hbm.blocks.rail.IRailNTM;
import com.hbm.blocks.rail.IRailNTM.RailLeaveInfo;
import com.hbm.lib.Library;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.PlayerInformPacket;
import com.hbm.util.ParticleUtil;
import com.hbm.util.fauxpointtwelve.BlockPos;
import com.hbm.world.feature.OilSpot;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class ItemWandD extends Item {

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		
		if(world.isRemote)
			return stack;
		
		MovingObjectPosition pos = Library.rayTrace(player, 500, 1, false, true, false);
		
		if(pos != null) {
			
			float yaw = player.rotationYaw;
			
			Vec3 next = Vec3.createVectorHelper(pos.hitVec.xCoord, pos.hitVec.yCoord, pos.hitVec.zCoord);
			int it = 0;
			
			BlockPos anchor = new BlockPos(pos.blockX, pos.blockY, pos.blockZ);
			
			double distanceToCover = 2D;
			
			ParticleUtil.spawnGasFlame(world, pos.hitVec.xCoord, pos.hitVec.yCoord, pos.hitVec.zCoord, 0, 0.2, 0);
			
			do {
				
				it++;
				
				if(it > 30) {
					world.createExplosion(player, pos.hitVec.xCoord, pos.hitVec.yCoord, pos.hitVec.zCoord, 5F, false);
					return stack;
				}
				
				int x = anchor.getX();
				int y = anchor.getY();
				int z = anchor.getZ();
				Block block = world.getBlock(x, y, z);
				
				Vec3 rot = Vec3.createVectorHelper(0, 0, 1);
				rot.rotateAroundY((float) (-yaw * Math.PI / 180D));
				
				if(block instanceof IRailNTM) {
					IRailNTM rail = (IRailNTM) block;
					
					RailLeaveInfo info = new RailLeaveInfo();
					Vec3 prev = next;
					next = rail.getTravelLocation(world, x, y, z, prev.xCoord, prev.yCoord, prev.zCoord, rot.xCoord, rot.yCoord, rot.zCoord, distanceToCover, info);
					distanceToCover = info.overshoot;
					anchor = info.pos;
					
					ParticleUtil.spawnGasFlame(world, next.xCoord, next.yCoord, next.zCoord, 0, 0.2 * it, 0);
					
					double deltaX = next.xCoord - prev.xCoord;
					double deltaZ = next.zCoord - prev.zCoord;
					double radians = -Math.atan2(deltaX, deltaZ);
					yaw = (float) MathHelper.wrapAngleTo180_double(radians * 180D / Math.PI);
					
					PacketDispatcher.wrapper.sendTo(new PlayerInformPacket(new ChatComponentText("Yaw: " + yaw), 0, 3000), (EntityPlayerMP) player);

					//if(info.overshoot > 0) System.out.println("[" + (worldObj.getTotalWorldTime() % 100) + "] Left track " + ((Block) rail).getUnlocalizedName() + " with " + ((int)(info.overshoot * 100) / 100D) + "m more to go!");
					
				} else {
					return stack;
				}
				
			} while(distanceToCover != 0); //if there's still length to cover, keep going
			
			/*TimeAnalyzer.startCount("setBlock");
			world.setBlock(pos.blockX, pos.blockY, pos.blockZ, Blocks.dirt);
			TimeAnalyzer.startEndCount("getBlock");
			world.getBlock(pos.blockX, pos.blockY, pos.blockZ);
			TimeAnalyzer.endCount();
			TimeAnalyzer.dump();*/
			
			/*TomSaveData data = TomSaveData.forWorld(world);
			data.impact = false;
			data.fire = 0F;
			data.dust = 0F;
			data.markDirty();*/
			
			/*EntityTomBlast tom = new EntityTomBlast(world);
			tom.posX = pos.blockX;
			tom.posY = pos.blockY;
			tom.posZ = pos.blockZ;
			tom.destructionRange = 600;
			world.spawnEntityInWorld(tom);*/
			
			/*ItemStack itemStack = new ItemStack(ModItems.book_lore);
			BookLoreType.setTypeForStack(itemStack, BookLoreType.BOOK_IODINE);
			
			player.inventory.addItemStackToInventory(itemStack);
			player.inventoryContainer.detectAndSendChanges();*/
			
			//use sparingly
			/*int k = ((pos.blockX >> 4) << 4) + 8;
			int l = ((pos.blockZ >> 4) << 4) + 8;
			
			MapGenBunker.Start start = new MapGenBunker.Start(world, world.rand, pos.blockX >> 4, pos.blockZ >> 4);
			start.generateStructure(world, world.rand, new StructureBoundingBox(k - 124, l - 124, k + 15 + 124, l + 15 + 124));*/
			//MapGenStronghold.Start startS = new MapGenStronghold.Start(world, world.rand, pos.blockX >> 4, pos.blockZ >> 4);
			//startS.generateStructure(world, world.rand, new StructureBoundingBox(k - 124, l - 124, k + 15 + 124, l + 15 + 124));
			
			/*EntityNukeTorex torex = new EntityNukeTorex(world);
			torex.setPositionAndRotation(pos.blockX, pos.blockY + 1, pos.blockZ, 0, 0);
			torex.getDataWatcher().updateObject(10, 1.5F);
			world.spawnEntityInWorld(torex);
			EntityTracker entitytracker = ((WorldServer) world).getEntityTracker();
			IntHashMap map = ReflectionHelper.getPrivateValue(EntityTracker.class, entitytracker, "trackedEntityIDs", "field_72794_c");
			EntityTrackerEntry entry = (EntityTrackerEntry) map.lookup(torex.getEntityId());
			entry.blocksDistanceThreshold = 1000;
			world.spawnEntityInWorld(EntityNukeExplosionMK5.statFacNoRad(world, 150, pos.blockX, pos.blockY + 1, pos.blockZ));*/
			
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
