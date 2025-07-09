package com.hbm.world.gen.util;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.generic.BlockSkeletonHolder;
import com.hbm.blocks.generic.DungeonSpawner;
import com.hbm.blocks.generic.LogicBlock;
import com.hbm.entity.item.EntityFallingBlockNT;
import com.hbm.entity.missile.EntityMissileTier2;
import com.hbm.entity.mob.EntityUndeadSoldier;
import com.hbm.items.ItemEnums;
import com.hbm.items.ModItems;
import com.hbm.tileentity.TileEntityDoorGeneric;
import com.hbm.tileentity.machine.storage.TileEntityCrateBase;
import com.hbm.util.ContaminationUtil;
import com.hbm.util.MobUtil;
import com.hbm.util.Vec3NT;
import com.hbm.world.WorldUtil;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.*;
import java.util.function.Consumer;

public class LogicBlockActions {

	public static LinkedHashMap<String, Consumer<LogicBlock.TileEntityLogicBlock>> actions = new LinkedHashMap<>();

	public static Consumer<LogicBlock.TileEntityLogicBlock> PHASE_ABERRATOR = (tile) -> {
		World world = tile.getWorldObj();
		int x = tile.xCoord;
		int y = tile.yCoord;
		int z = tile.zCoord;
		if (tile.phase == 1 || tile.phase == 2) {
			tile.player = world.getClosestPlayer(x,y,z, 25);
			if (tile.timer == 0) {
				Vec3NT vec = new Vec3NT(20, 0, 0);
				for (int i = 0; i < 10; i++) {

					if(vec.xCoord > 8) vec.xCoord += world.rand.nextInt(10) - 5;

					EntityUndeadSoldier mob = new EntityUndeadSoldier(world);
					for (int j = 0; j < 7; j++) {
						mob.setPositionAndRotation(x + 0.5 + vec.xCoord, world.getHeightValue((int) (x + 0.5 + vec.xCoord),(int) (z + 0.5 + vec.zCoord)), z + 0.5 + vec.zCoord, i * 36F, 0);
						if (mob.getCanSpawnHere()) {
							mob.onSpawnWithEgg(null);
							if(tile.player != null){
								mob.setTarget(tile.player);
							}
							world.spawnEntityInWorld(mob);
							break;
						}
					}
					vec.rotateAroundYDeg(36D);
				}
			}
		}
		if (tile.phase > 2) {
			TileEntity te = world.getTileEntity(x, y + 18, z);
			if (te instanceof BlockSkeletonHolder.TileEntitySkeletonHolder) {
				BlockSkeletonHolder.TileEntitySkeletonHolder skeleton = (BlockSkeletonHolder.TileEntitySkeletonHolder) te;
				if (world.rand.nextInt(5) == 0) {
					skeleton.item = new ItemStack(ModItems.item_secret, 1, ItemEnums.EnumSecretType.ABERRATOR.ordinal());
				} else {
					skeleton.item = new ItemStack(ModItems.clay_tablet, 1, 1);
				}
				skeleton.markDirty();
				world.markBlockForUpdate(x, y + 18, z);
			}
			world.setBlock(x, y, z, Blocks.obsidian);
		}
	};

	public static Consumer<LogicBlock.TileEntityLogicBlock> COLLAPSE_ROOF_RAD_5 = (tile) -> {
		World world = tile.getWorldObj();
		int x = tile.xCoord;
		int y = tile.yCoord;
		int z = tile.zCoord;

		if(tile.phase == 0) return;

		//from explosionChaos because i cannot be assed
		int r = 4;
		int r2 = r * r;
		int r22 = r2 / 2;

		for (int xx = -r; xx < r; xx++) {
			int X = xx + x;
			int XX = xx * xx;
			for (int yy = -r; yy < r; yy++) {
				int Y = yy + y;
				int YY = XX + yy * yy;
				for (int zz = -r; zz < r; zz++) {
					int Z = zz + z;
					int ZZ = YY + zz * zz;
					if (ZZ < r22) {

						if (world.getBlock(X, Y, Z).getExplosionResistance(null) <= 70) {
							EntityFallingBlockNT entityfallingblock = new EntityFallingBlockNT(world, X + 0.5, Y + 0.5, Z + 0.5, world.getBlock(X, Y, Z), world.getBlockMetadata(X, Y, Z));
							world.spawnEntityInWorld(entityfallingblock);
						}
					}
				}
			}
		}
		world.setBlock(x, y, z, ModBlocks.block_steel);

	};

	public static Consumer<LogicBlock.TileEntityLogicBlock> FODDER_WAVE = (tile) -> {
		World world = tile.getWorldObj();
		int x = tile.xCoord;
		int y = tile.yCoord;
		int z = tile.zCoord;
		if (tile.phase == 1) {
			Vec3NT vec = new Vec3NT(5, 0, 0);
			for (int i = 0; i < 10; i++) {
				EntityZombie mob = new EntityZombie(world);
				mob.setPositionAndRotation(x + 0.5 + vec.xCoord, world.getHeightValue(x,z), z + 0.5 + vec.zCoord, i * 36F, 0);
				MobUtil.assignItemsToEntity(mob, MobUtil.slotPoolAdv, new Random());
				world.spawnEntityInWorld(mob);

				vec.rotateAroundYDeg(36D);
			}
			world.setBlock(x, y, z, ModBlocks.block_steel);
		}
	};

	public static Consumer<LogicBlock.TileEntityLogicBlock> PUZZLE_TEST = (tile) -> {
		World world = tile.getWorldObj();
		int x = tile.xCoord;
		int y = tile.yCoord;
		int z = tile.zCoord;

		if(tile.phase == 2){
			world.setBlock(x,y,z, ModBlocks.crate_steel);

			EntityLightningBolt blitz = new EntityLightningBolt(world, x, world.getHeightValue(x, z) + 2, z);
			world.spawnEntityInWorld(blitz);

			TileEntityCrateBase crate = (TileEntityCrateBase) world.getTileEntity(x,y,z);
			((IInventory)crate).setInventorySlotContents(15, new ItemStack(ModItems.gun_bolter));
		}
	};

	public static Consumer<LogicBlock.TileEntityLogicBlock> MISSILE_STRIKE = (tile) -> {
		World world = tile.getWorldObj();
		int x = tile.xCoord;
		int y = tile.yCoord;
		int z = tile.zCoord;

		if(tile.phase != 1) return;

		world.getClosestPlayer(x,y,z, 25).addChatMessage(new ChatComponentText(EnumChatFormatting.LIGHT_PURPLE + "[COMMAND UNIT]"+ EnumChatFormatting.RESET + " Missile Fired"));

		ForgeDirection parallel = tile.direction.getRotation(ForgeDirection.UP);

		EntityMissileTier2.EntityMissileStrong missile =
				new EntityMissileTier2.EntityMissileStrong(
						world,
						x + tile.direction.offsetX * 300,
						200,
						z + tile.direction.offsetZ * 300,
						x + parallel.offsetX * 30 + tile.direction.offsetX * 30,
						z + parallel.offsetZ * 30 + tile.direction.offsetZ * 30);
		WorldUtil.loadAndSpawnEntityInWorld(missile);

		world.setBlock(x,y,z, ModBlocks.block_electrical_scrap);
	};

	public static Consumer<LogicBlock.TileEntityLogicBlock> RAD_CONTAINMENT_SYSTEM = (tile) -> {
		World world = tile.getWorldObj();
		int x = tile.xCoord;
		int y = tile.yCoord;
		int z = tile.zCoord;

		ForgeDirection direction = tile.direction.getOpposite();
		ForgeDirection rot = direction.getRotation(ForgeDirection.UP);

		AxisAlignedBB bb = AxisAlignedBB.getBoundingBox(x - rot.offsetX, y - 1, z - rot.offsetZ, x + rot.offsetX + direction.offsetX * 15, y + 1, z + rot.offsetZ + direction.offsetZ * 15).expand(2,2,2);

		List<EntityLivingBase> entities = world.getEntitiesWithinAABB(EntityLivingBase.class, bb);

		for(EntityLivingBase e : entities) {

			Vec3 vec = Vec3.createVectorHelper(e.posX - (x + 0.5), (e.posY + e.getEyeHeight()) - (y + 0.5), e.posZ - (z + 0.5));
			double len = vec.lengthVector();
			vec = vec.normalize();

			len = Math.max(len,1D);

			float res = 0;

			for(int i = 1; i < len; i++) {

				int ix = (int)Math.floor(x + 0.5 + vec.xCoord * i);
				int iy = (int)Math.floor(y + 0.5 + vec.yCoord * i);
				int iz = (int)Math.floor(z + 0.5 + vec.zCoord * i);

				res += world.getBlock(ix, iy, iz).getExplosionResistance(null);
			}

			if(res < 1)
				res = 1;

			float eRads = 100F;
			eRads /= (float)res;
			eRads /= (float)(len * len);

			ContaminationUtil.contaminate(e, ContaminationUtil.HazardType.RADIATION, ContaminationUtil.ContaminationType.HAZMAT2, eRads);
		}

		if (tile.phase == 2 && tile.timer > 40){
			world.getClosestPlayer(x,y,z, 25).addChatMessage(new ChatComponentText(
				EnumChatFormatting.LIGHT_PURPLE + "[RAD CONTAINMENT SYSTEM]" +
					EnumChatFormatting.RESET + " Diagnostics found containment failure, commencing lockdown"));

			for(int i = 1; i < 20; i++) {
				int checkX, checkY, checkZ;
				checkX = x + direction.offsetX * i;
				checkY = y + 1;
				checkZ = z + direction.offsetZ * i;
				Block block = world.getBlock(checkX, checkY,checkZ);
				TileEntity te = null;
				if(block instanceof  BlockDummyable){
					int[] coreCoords = ((BlockDummyable) block).findCore(world,checkX,checkY,checkZ);
					te = world.getTileEntity(coreCoords[0], coreCoords[1], coreCoords[2]);
				}

				if (te instanceof TileEntityDoorGeneric) {
					TileEntityDoorGeneric door = (TileEntityDoorGeneric) te;
					door.setPins(456);
					door.close();
					door.lock();
					break;
				}
			}

			tile.phase = 3;
		}
	};

	public static List<String> getActionNames(){
		return new ArrayList<>(actions.keySet());
	}

	//register new actions here
	static{
		//example actions
		actions.put("FODDER_WAVE", FODDER_WAVE);
		actions.put("ABERRATOR", PHASE_ABERRATOR);
		actions.put("COLLAPSE_ROOF_RAD_5", COLLAPSE_ROOF_RAD_5);
		actions.put("PUZZLE_TEST", PUZZLE_TEST);
		actions.put("MISSILE_STRIKE", MISSILE_STRIKE);
		actions.put("IRRADIATE_ENTITIES_AOE", RAD_CONTAINMENT_SYSTEM);
	}



}
