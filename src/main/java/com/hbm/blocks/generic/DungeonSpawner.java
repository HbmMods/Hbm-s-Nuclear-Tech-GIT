package com.hbm.blocks.generic;

import java.util.function.Consumer;
import java.util.function.Function;

import com.hbm.util.EnumUtil;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
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
		public EnumSpawnerType type = EnumSpawnerType.NONE;
		
		@Override
		public void updateEntity() {
			
			if(!worldObj.isRemote) {
				type.phase.accept(this);
				if(type.phaseCondition.apply(this)) phase++;
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
		
		NONE((te) -> { return false; }, (te) -> {});

		public Function<TileEntityDungeonSpawner, Boolean> phaseCondition;
		public Consumer<TileEntityDungeonSpawner> phase;
		
		private EnumSpawnerType(Function<TileEntityDungeonSpawner, Boolean> con, Consumer<TileEntityDungeonSpawner> ph) {
			this.phaseCondition = con;
			this.phase = ph;
		}
	}
}
