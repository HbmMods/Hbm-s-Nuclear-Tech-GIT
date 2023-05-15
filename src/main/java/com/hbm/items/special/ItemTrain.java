package com.hbm.items.special;

import com.hbm.blocks.rail.IRailNTM;
import com.hbm.entity.train.EntityRailCarBase;
import com.hbm.entity.train.TrainCargoTram;
import com.hbm.items.ItemEnumMulti;
import com.hbm.util.EnumUtil;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemTrain extends ItemEnumMulti {

	public ItemTrain() {
		super(EnumTrainType.class, true, true);
	}

	public static enum EnumTrainType {
		
		CARGO_TRAM(TrainCargoTram.class);
		
		public Class<? extends EntityRailCarBase> train;
		private EnumTrainType(Class<? extends EntityRailCarBase> train) {
			this.train = train;
		}
	}

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer entity, World world, int x, int y, int z, int side, float fx, float fy, float fz) {
		
		Block b = world.getBlock(x, y, z);
		
		if(b instanceof IRailNTM) {
			
			EnumTrainType type = EnumUtil.grabEnumSafely(theEnum, stack.getItemDamage());
			EntityRailCarBase train = null;
			try { train = type.train.getConstructor(World.class).newInstance(world); } catch(Exception e) { }
			
			if(train != null && train.getGauge() == ((IRailNTM) b).getGauge(world, x, y, z)) {
				if(!world.isRemote) {
					train.setPosition(x + fx, y + fy, z + fz);
					train.rotationYaw = entity.rotationYaw;
					world.spawnEntityInWorld(train);
				}
				
				stack.stackSize--;
				return true;
			}
		}
		
		return false;
	}
}
