package com.hbm.blocks.network;

import java.util.Random;

import com.hbm.items.ModItems;

import net.minecraft.item.Item;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockConveyorTriple extends BlockConveyorBendable {

	@Override
	public Vec3 getClosestSnappingPosition(World world, int x, int y, int z, Vec3 itemPos) {

		ForgeDirection dir = this.getTravelDirection(world, x, y, z, itemPos);

		itemPos.xCoord = MathHelper.clamp_double(itemPos.xCoord, x, x + 1);
		itemPos.zCoord = MathHelper.clamp_double(itemPos.zCoord, z, z + 1);

		double posX = x + 0.5;
		double posZ = z + 0.5;

		if(dir.offsetX != 0) {
			posX = itemPos.xCoord;
			posZ += (itemPos.zCoord > posZ + 0.15 ? 0.3125 : itemPos.zCoord < posZ - 0.15 ? -0.3125 : 0);
		}
		if(dir.offsetZ != 0) {
			posZ = itemPos.zCoord;
			posX += (itemPos.xCoord > posX + 0.15 ? 0.3125 : itemPos.xCoord < posX - 0.15 ? -0.3125 : 0);
		}

		return Vec3.createVectorHelper(posX, y + 0.25, posZ);
	}

	@Override
	public Item getItemDropped(int meta, Random rand, int fortune) {
		return ModItems.conveyor_wand;
	}

	@Override
	public int damageDropped(int meta) {
		return 3;
	}

}