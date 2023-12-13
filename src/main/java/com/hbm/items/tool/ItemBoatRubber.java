package com.hbm.items.tool;

import java.util.List;

import com.hbm.entity.item.EntityBoatRubber;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class ItemBoatRubber extends Item {
	
	public ItemBoatRubber() {
		this.maxStackSize = 1;
		this.setCreativeTab(CreativeTabs.tabTransport);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		
		float f = 1.0F;
		float pitch = player.prevRotationPitch + (player.rotationPitch - player.prevRotationPitch) * f;
		float yaw = player.prevRotationYaw + (player.rotationYaw - player.prevRotationYaw) * f;
		double posX = player.prevPosX + (player.posX - player.prevPosX) * (double) f;
		double posY = player.prevPosY + (player.posY - player.prevPosY) * (double) f + 1.62D - (double) player.yOffset;
		double posZ = player.prevPosZ + (player.posZ - player.prevPosZ) * (double) f;
		float compZ = MathHelper.cos(-yaw * 0.017453292F - (float) Math.PI);
		float compX = MathHelper.sin(-yaw * 0.017453292F - (float) Math.PI);
		float mult = -MathHelper.cos(-pitch * 0.017453292F);
		float lookY = MathHelper.sin(-pitch * 0.017453292F);
		float lookX = compX * mult;
		float lookZ = compZ * mult;
		double reach = 5.0D;
		
		Vec3 pos = Vec3.createVectorHelper(posX, posY, posZ);
		Vec3 target = pos.addVector((double) lookX * reach, (double) lookY * reach, (double) lookZ * reach);
		MovingObjectPosition mop = world.rayTraceBlocks(pos, target, true);

		if(mop == null) {
			return stack;
			
		} else {
			Vec3 look = player.getLook(f);
			boolean flag = false;
			double width = 1.0D;
			List list = world.getEntitiesWithinAABBExcludingEntity(player, player.boundingBox.addCoord(look.xCoord * reach, look.yCoord * reach, look.zCoord * reach).expand(width, width, width));

			for(int i = 0; i < list.size(); ++i) {
				Entity entity = (Entity) list.get(i);

				if(entity.canBeCollidedWith()) {
					float f10 = entity.getCollisionBorderSize();
					AxisAlignedBB axisalignedbb = entity.boundingBox.expand((double) f10, (double) f10, (double) f10);

					if(axisalignedbb.isVecInside(pos)) {
						flag = true;
					}
				}
			}

			if(flag) {
				return stack;
				
			} else {
				if(mop.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
					int x = mop.blockX;
					int y = mop.blockY;
					int z = mop.blockZ;

					if(world.getBlock(x, y, z) == Blocks.snow_layer) {
						--y;
					}

					EntityBoatRubber entityboat = new EntityBoatRubber(world, (double) ((float) x + 0.5F), (double) ((float) y + 1.0F), (double) ((float) z + 0.5F));
					entityboat.rotationYaw = (float) (((MathHelper.floor_double((double) (player.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3) - 1) * 90);

					if(!world.getCollidingBoundingBoxes(entityboat, entityboat.boundingBox.expand(-0.1D, -0.1D, -0.1D)).isEmpty()) {
						return stack;
					}

					if(!world.isRemote) {
						world.spawnEntityInWorld(entityboat);
					}

					if(!player.capabilities.isCreativeMode) {
						--stack.stackSize;
					}
				}

				return stack;
			}
		}
	}
}
