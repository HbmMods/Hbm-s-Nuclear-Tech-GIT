package com.hbm.world.gen.util;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.generic.BlockPedestal;
import com.hbm.blocks.generic.BlockSkeletonHolder;
import com.hbm.blocks.generic.DungeonSpawner;
import com.hbm.entity.item.EntityFallingBlockNT;
import com.hbm.entity.mob.EntityUndeadSoldier;
import com.hbm.items.ItemEnums;
import com.hbm.items.ModItems;
import com.hbm.main.ModEventHandler;
import com.hbm.tileentity.machine.storage.TileEntityCrateBase;
import com.hbm.util.MobUtil;
import com.hbm.util.Vec3NT;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;

public class DungeonSpawnerActions {

	public static HashMap<String, Consumer<DungeonSpawner.TileEntityDungeonSpawner>> actions = new HashMap<>();

	public static Consumer<DungeonSpawner.TileEntityDungeonSpawner> PHASE_ABERRATOR = (tile) -> {
		World world = tile.getWorldObj();
		int x = tile.xCoord;
		int y = tile.yCoord;
		int z = tile.zCoord;
		if (tile.phase == 1 || tile.phase == 2) {
			if (tile.timer == 0) {
				Vec3NT vec = new Vec3NT(10, 0, 0);
				for (int i = 0; i < 10; i++) {
					EntityUndeadSoldier mob = new EntityUndeadSoldier(world);
					for (int j = 0; j < 7; j++) {
						mob.setPositionAndRotation(x + 0.5 + vec.xCoord, y - 5, z + 0.5 + vec.zCoord, i * 36F, 0);
						if (mob.getCanSpawnHere()) {
							mob.onSpawnWithEgg(null);
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

	public static Consumer<DungeonSpawner.TileEntityDungeonSpawner> COLLAPSE_ROOF_RAD_5 = (tile) -> {
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

	public static Consumer<DungeonSpawner.TileEntityDungeonSpawner> FODDER_WAVE = (tile) -> {
		World world = tile.getWorldObj();
		int x = tile.xCoord;
		int y = tile.yCoord;
		int z = tile.zCoord;
		if (tile.phase == 1) {
			Vec3NT vec = new Vec3NT(5, 0, 0);
			for (int i = 0; i < 10; i++) {
				EntityZombie mob = new EntityZombie(world);
				for (int j = 0; j < 7; j++) {
					mob.setPositionAndRotation(x + 0.5 + vec.xCoord, world.getHeightValue(x,z), z + 0.5 + vec.zCoord, i * 36F, 0);
					MobUtil.assignItemsToEntity(mob, MobUtil.slotPoolAdv, new Random());
					if (mob.getCanSpawnHere()) {
						world.spawnEntityInWorld(mob);
						break;
					}
				}
				vec.rotateAroundYDeg(36D);
			}
			world.setBlock(x, y, z, ModBlocks.block_steel);
		}
	};

	public static Consumer<DungeonSpawner.TileEntityDungeonSpawner> PUZZLE_TEST = (tile) -> {
		World world = tile.getWorldObj();
		int x = tile.xCoord;
		int y = tile.yCoord;
		int z = tile.zCoord;

		if(tile.phase == 2){
			world.setBlock(x,y,z, ModBlocks.crate_steel);

			EntityLightningBolt blitz = new EntityLightningBolt(world, x, world.getHeightValue(x, z) + 1, z);
			world.spawnEntityInWorld(blitz);

			TileEntityCrateBase crate = (TileEntityCrateBase) world.getTileEntity(x,y,z);
			((IInventory)crate).setInventorySlotContents(15, new ItemStack(ModItems.gun_bolter));
		}
	};

	public static List<String> getActionNames(){
		return new ArrayList<>(actions.keySet());
	}

	//register new actions here
	static{
		actions.put("PHASE_ABERRATOR", PHASE_ABERRATOR);
		actions.put("COLLAPSE_ROOF_RAD_5", COLLAPSE_ROOF_RAD_5);
		actions.put("FODDER_WAVE", FODDER_WAVE);
		actions.put("PUZZLE_TEST", PUZZLE_TEST);
	}



}
