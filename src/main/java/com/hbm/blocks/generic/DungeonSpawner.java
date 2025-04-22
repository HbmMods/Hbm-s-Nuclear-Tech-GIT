package com.hbm.blocks.generic;

import java.util.function.Consumer;
import java.util.function.Function;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.generic.BlockSkeletonHolder.TileEntitySkeletonHolder;
import com.hbm.entity.mob.EntityUndeadSoldier;
import com.hbm.items.ItemEnums.EnumSecretType;
import com.hbm.items.ModItems;
import com.hbm.util.EnumUtil;
import com.hbm.util.Vec3NT;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
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
		public EnumSpawnerType type = EnumSpawnerType.NONE;
		
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
		
		NONE(CON_TEST, PHASE_TEST);

		public Function<TileEntityDungeonSpawner, Boolean> phaseCondition;
		public Consumer<TileEntityDungeonSpawner> phase;
		
		private EnumSpawnerType(Function<TileEntityDungeonSpawner, Boolean> con, Consumer<TileEntityDungeonSpawner> ph) {
			this.phaseCondition = con;
			this.phase = ph;
		}
	}
	
	public static Function<TileEntityDungeonSpawner, Boolean> CON_TEST = (tile) -> {
		World world = tile.getWorldObj();
		int x = tile.xCoord;
		int y = tile.yCoord;
		int z = tile.zCoord;
		if(tile.phase == 0) {
			if(world.getTotalWorldTime() % 20 != 0) return false;
			//return !world.getEntitiesWithinAABB(EntityPlayer.class, AxisAlignedBB.getBoundingBox(x, y, z, x + 1, y + 1, z + 1).expand(20, 10, 20)).isEmpty();
		}
		if(tile.phase < 3) {
			if(world.getTotalWorldTime() % 20 != 0 || tile.timer < 60) return false;
			//return world.getEntitiesWithinAABB(EntityUndeadSoldier.class, AxisAlignedBB.getBoundingBox(x, y, z, x + 1, y + 1, z + 1).expand(50, 20, 50)).isEmpty();
		}
		return false;
	};
	
	public static Consumer<TileEntityDungeonSpawner> PHASE_TEST = (tile) -> {
		World world = tile.getWorldObj();
		int x = tile.xCoord;
		int y = tile.yCoord;
		int z = tile.zCoord;
		if(tile.phase == 1 || tile.phase == 2) {
			if(tile.timer == 0) {
				Vec3NT vec = new Vec3NT(10, 0, 0);
				for(int i = 0; i < 10; i++) {
					EntityUndeadSoldier mob = new EntityUndeadSoldier(world);
					mob.setPositionAndRotation(x + 0.5 + vec.xCoord, y, z + 0.5 + vec.zCoord, i * 36F, 0);
					vec.rotateAroundYDeg(36D);
					mob.onSpawnWithEgg(null);
					world.spawnEntityInWorld(mob);
				}
			}
		}
		if(tile.phase > 2) {
			world.setBlock(x, y + 1, z, ModBlocks.skeleton_holder, 2 + world.rand.nextInt(4), 3);
			TileEntity te = world.getTileEntity(x, y + 1, z);
			if(te instanceof TileEntitySkeletonHolder) {
				TileEntitySkeletonHolder skeleton = (TileEntitySkeletonHolder) te;
				skeleton.item = new ItemStack(ModItems.item_secret, 1, EnumSecretType.ABERRATOR.ordinal());
				skeleton.markDirty();
				world.markBlockForUpdate(x, y, z);
			}
			world.setBlock(x, y, z, Blocks.obsidian);
		}
	};
}
