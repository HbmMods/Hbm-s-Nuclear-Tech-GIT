package com.hbm.blocks.generic;

import java.util.function.Consumer;
import java.util.function.Function;

import com.hbm.blocks.generic.BlockSkeletonHolder.TileEntitySkeletonHolder;
import com.hbm.entity.mob.EntityUndeadSoldier;
import com.hbm.items.ItemEnums.EnumSecretType;
import com.hbm.items.ModItems;
import com.hbm.world.gen.util.DungeonSpawnerActions;
import com.hbm.world.gen.util.DungeonSpawnerConditions;
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
import net.minecraftforge.common.util.ForgeDirection;

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

		public String conditionID = "ABERRATOR";
		//actions always get called before conditions, use the phase timer in order to control behavior via condition
		public String actionID = "ABERRATOR";

		public Function<TileEntityDungeonSpawner, Boolean> condition;
		public Consumer<TileEntityDungeonSpawner> action;

		public ForgeDirection direction = ForgeDirection.UNKNOWN;
		@Override
		public void updateEntity() {

			if(!worldObj.isRemote) {
				if(action == null){
					action = DungeonSpawnerActions.actions.get(actionID);
				}
				if(condition == null){
					condition = DungeonSpawnerConditions.conditions.get(conditionID);
				}
				if(action == null || condition == null){
					worldObj.setBlock(xCoord,yCoord,zCoord, Blocks.air);
					return;
				}
				action.accept(this);
				if(condition.apply(this)) {
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
			nbt.setString("conditionID", conditionID);
			nbt.setString("actionID", actionID);
			nbt.setInteger("direction", direction.ordinal());
		}

		@Override
		public void readFromNBT(NBTTagCompound nbt) {
			super.readFromNBT(nbt);
			this.phase = nbt.getInteger("phase");
			this.conditionID = nbt.getString("conditionID");
			this.direction = ForgeDirection.getOrientation(nbt.getInteger("direction"));
		}
	}

}
