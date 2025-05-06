package com.hbm.blocks.generic;

import java.util.function.Consumer;
import java.util.function.Function;

import com.hbm.blocks.generic.BlockSkeletonHolder.TileEntitySkeletonHolder;
import com.hbm.entity.mob.EntityUndeadSoldier;
import com.hbm.items.ItemEnums.EnumSecretType;
import com.hbm.items.ModItems;
import com.hbm.util.EnumUtil;
import com.hbm.util.Vec3NT;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class DungeonSpawner extends BlockContainer {

	public DungeonSpawner() {
		super(Material.rock);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityDungeonSpawner();
	}
	
	public static class TileEntityDungeonSpawner extends TileEntity {
		
		public int phase = 0;
		public int timer = 0;
		public EnumSpawnerType type = EnumSpawnerType.ABERRATOR;
		
		@Override
		public void updateEntity() {
			
			if(!worldObj.isRemote) {
				type.phase.accept(this);
				if(type.phaseCondition.apply(this)) {
					phase++;
					timer = 0;
				} else {
					timer++;
				}
			}
		}

		@Override
		public void writeToNBT(NBTTagCompound nbt) {
			super.writeToNBT(nbt);
			nbt.setInteger("phase", phase);
			nbt.setByte("type", (byte) type.ordinal());
		}

		@Override
		public void readFromNBT(NBTTagCompound nbt) {
			super.readFromNBT(nbt);
			this.phase = nbt.getInteger("phase");
			this.type = EnumUtil.grabEnumSafely(EnumSpawnerType.class, nbt.getByte("type"));
		}
	}
	
	public static enum EnumSpawnerType {
		
		ABERRATOR(CON_ABERRATOR, PHASE_ABERRATOR);

		public Function<TileEntityDungeonSpawner, Boolean> phaseCondition;
		public Consumer<TileEntityDungeonSpawner> phase;
		
		private EnumSpawnerType(Function<TileEntityDungeonSpawner, Boolean> con, Consumer<TileEntityDungeonSpawner> ph) {
			this.phaseCondition = con;
			this.phase = ph;
		}
	}
	
	public static Function<TileEntityDungeonSpawner, Boolean> CON_ABERRATOR = (tile) -> {
		World world = tile.getWorldObj();
		int x = tile.xCoord;
		int y = tile.yCoord;
		int z = tile.zCoord;
		if(tile.phase == 0) {
			if(world.getTotalWorldTime() % 20 != 0) return false;
			return !world.getEntitiesWithinAABB(EntityPlayer.class, AxisAlignedBB.getBoundingBox(x, y, z, x + 1, y - 2, z + 1).expand(20, 10, 20)).isEmpty();
		}
		if(tile.phase < 3) {
			if(world.getTotalWorldTime() % 20 != 0 || tile.timer < 60) return false;
			return world.getEntitiesWithinAABB(EntityUndeadSoldier.class, AxisAlignedBB.getBoundingBox(x, y, z, x - 2, y + 1, z + 1).expand(50, 20, 50)).isEmpty();
		}
		return false;
	};
	
	public static Consumer<TileEntityDungeonSpawner> PHASE_ABERRATOR = (tile) -> {
		World world = tile.getWorldObj();
		int x = tile.xCoord;
		int y = tile.yCoord;
		int z = tile.zCoord;
		if(tile.phase == 1 || tile.phase == 2) {
			if(tile.timer == 0) {
				Vec3NT vec = new Vec3NT(10, 0, 0);
				for(int i = 0; i < 10; i++) {
					EntityUndeadSoldier mob = new EntityUndeadSoldier(world);
					for(int j = 0; j < 7; j++) {
						mob.setPositionAndRotation(x + 0.5 + vec.xCoord, y - 5, z + 0.5 + vec.zCoord, i * 36F, 0);
						if(mob.getCanSpawnHere()) {
							mob.onSpawnWithEgg(null);
							world.spawnEntityInWorld(mob);
							break;
						}
					}
					
					vec.rotateAroundYDeg(36D);
				}
			}
		}
		if(tile.phase > 2) {
			TileEntity te = world.getTileEntity(x, y + 18, z);
			if(te instanceof TileEntitySkeletonHolder) {
				TileEntitySkeletonHolder skeleton = (TileEntitySkeletonHolder) te;
				skeleton.item = new ItemStack(ModItems.item_secret, 1, EnumSecretType.ABERRATOR.ordinal());
				skeleton.markDirty();
				world.markBlockForUpdate(x, y + 18, z);
			}
			world.setBlock(x, y, z, Blocks.obsidian);
		}
	};
}
