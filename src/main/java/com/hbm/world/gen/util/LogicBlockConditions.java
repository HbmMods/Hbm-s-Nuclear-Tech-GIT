package com.hbm.world.gen.util;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.generic.BlockPedestal;
import com.hbm.blocks.generic.LogicBlock;
import com.hbm.entity.mob.EntityUndeadSoldier;
import com.hbm.items.ModItems;
import com.hbm.tileentity.bomb.TileEntityCharge;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.function.Function;

public class LogicBlockConditions {

	public static LinkedHashMap<String, Function<LogicBlock.TileEntityLogicBlock, Boolean>> conditions;

	/**For use with interactions, for having them handle all conditional tasks*/
	public static Function<LogicBlock.TileEntityLogicBlock, Boolean> EMPTY = (tile) -> false;

	public static Function<LogicBlock.TileEntityLogicBlock, Boolean> ABERRATOR = (tile) -> {
		World world = tile.getWorldObj();
		if(world.difficultySetting.ordinal() == 0) return false;
		int x = tile.xCoord;
		int y = tile.yCoord;
		int z = tile.zCoord;

		boolean aoeCheck = !world.getEntitiesWithinAABB(EntityPlayer.class, AxisAlignedBB.getBoundingBox(x, y, z, x + 1, y - 2, z + 1).expand(10, 10, 10)).isEmpty();
		if(tile.phase == 0) {
			if(world.getTotalWorldTime() % 20 != 0) return false;
			return aoeCheck;
		}
		if(tile.phase < 3) {
			if(world.getTotalWorldTime() % 20 != 0 || tile.timer < 60) return false;
			return world.getEntitiesWithinAABB(EntityUndeadSoldier.class, AxisAlignedBB.getBoundingBox(x, y, z, x - 2, y + 1, z + 1).expand(50, 20, 50)).isEmpty() && aoeCheck;
		}
		return false;
	};

	public static Function<LogicBlock.TileEntityLogicBlock, Boolean> PLAYER_CUBE_3 = (tile) -> {
		World world = tile.getWorldObj();
		int x = tile.xCoord;
		int y = tile.yCoord;
		int z = tile.zCoord;
		return !world.getEntitiesWithinAABB(EntityPlayer.class, AxisAlignedBB.getBoundingBox(x, y, z, x + 1, y - 2, z + 1).expand(3, 3, 3)).isEmpty();
	};

	public static Function<LogicBlock.TileEntityLogicBlock, Boolean> PLAYER_CUBE_5 = (tile) -> {
		World world = tile.getWorldObj();
		int x = tile.xCoord;
		int y = tile.yCoord;
		int z = tile.zCoord;
		return !world.getEntitiesWithinAABB(EntityPlayer.class, AxisAlignedBB.getBoundingBox(x, y, z, x + 1, y - 2, z + 1).expand(5, 5, 5)).isEmpty();
	};

	public static Function<LogicBlock.TileEntityLogicBlock, Boolean> PLAYER_CUBE_25 = (tile) -> {
		World world = tile.getWorldObj();
		int x = tile.xCoord;
		int y = tile.yCoord;
		int z = tile.zCoord;
		return !world.getEntitiesWithinAABB(EntityPlayer.class, AxisAlignedBB.getBoundingBox(x, y, z, x + 1, y - 2, z + 1).expand(25, 25, 25)).isEmpty();
	};

	public static Function<LogicBlock.TileEntityLogicBlock, Boolean> REDSTONE = (tile) -> {
		World world = tile.getWorldObj();
		int x = tile.xCoord;
		int y = tile.yCoord;
		int z = tile.zCoord;

		return world.isBlockIndirectlyGettingPowered(x,y,z);
	};

	public static Function<LogicBlock.TileEntityLogicBlock, Boolean> PUZZLE_TEST = (tile) -> {
		World world = tile.getWorldObj();
		int x = tile.xCoord;
		int y = tile.yCoord;
		int z = tile.zCoord;

		if(tile.phase == 0 &&  world.isBlockIndirectlyGettingPowered(x,y,z)){
			world.getClosestPlayer(x,y,z, 25).addChatMessage(new ChatComponentText("Find a " + EnumChatFormatting.GOLD + "great" + EnumChatFormatting.RESET + " ancient weapon, of questionable use in the modern age"));
			world.setBlock(x,y + 1,z, ModBlocks.pedestal);
			return true;
		}

		TileEntity pedestal = world.getTileEntity(x,y + 1,z);

		return tile.phase == 1
			&& pedestal instanceof BlockPedestal.TileEntityPedestal
			&& ((BlockPedestal.TileEntityPedestal) pedestal).item != null
			&& ((BlockPedestal.TileEntityPedestal) pedestal).item.getItem() == ModItems.big_sword;
	};

	public static Function<LogicBlock.TileEntityLogicBlock, Boolean> BOMB_CRANE = (tile) -> {
		World world = tile.getWorldObj();
		int x = tile.xCoord;
		int y = tile.yCoord;
		int z = tile.zCoord;

		if(tile.phase == 0) {
			world.setBlock(x, y + 1, z, ModBlocks.charge_c4, ForgeDirection.UP.ordinal(), 3);

			TileEntity te = world.getTileEntity(x, y + 1, z);
			if (te instanceof TileEntityCharge) {
				TileEntityCharge bomb = (TileEntityCharge) te;
				bomb.timer = 200;
			}
		}

		return !world.getEntitiesWithinAABB(EntityPlayer.class, AxisAlignedBB.getBoundingBox(x, y, z, x + 1, y - 2, z + 1).expand(10, 10, 10)).isEmpty();
	};




	public static List<String> getConditionNames(){
		return new ArrayList<>(conditions.keySet());
	}

	//register new conditions here
	static {
		initialize();
	}

	public static void initialize() {
		conditions = new LinkedHashMap<>();

		conditions.put("EMPTY", EMPTY);
		conditions.put("PLAYER_CUBE_3", PLAYER_CUBE_3);
		conditions.put("PLAYER_CUBE_5", PLAYER_CUBE_5);
		conditions.put("PLAYER_CUBE_25", PLAYER_CUBE_25);

		conditions.put("BOMB_CRANE", BOMB_CRANE);

		//example conditions
		conditions.put("ABERRATOR", ABERRATOR);
		conditions.put("REDSTONE", REDSTONE);
		conditions.put("PUZZLE_TEST", PUZZLE_TEST);
	}

}
