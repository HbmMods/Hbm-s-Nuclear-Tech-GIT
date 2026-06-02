package com.hbm.world.gen.util;

import api.hbm.energymk2.IEnergyHandlerMK2;
import com.hbm.blocks.generic.LogicBlock;
import com.hbm.items.ModItems;
import com.hbm.potion.HbmPotion;
import com.hbm.tileentity.machine.TileEntityLockableBase;
import com.hbm.tileentity.machine.storage.TileEntityCrateBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.Facing;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.function.Consumer;

/**Interactions are called when the player right-clicks the block**/
public class LogicBlockInteractions {

	/**Consumer consists of world instance, tile entity instance, three ints for coordinates, one int for block side, and player instance,
	 * in that order **/
	public static LinkedHashMap<String, Consumer<Object[]>> interactions;

	public static Consumer<Object[]> TEST = (array) -> {
		World world = (World) array[0];
		LogicBlock.TileEntityLogicBlock logic = (LogicBlock.TileEntityLogicBlock) array[1];
		int x = (int) array[2];
		int y = (int) array[3];
		int z = (int) array[4];
		EntityPlayer player = (EntityPlayer) array[5];
		int side = (int) array[6];

		if(logic.phase > 1) return;

		if(player.getHeldItem() != null)
			player.getHeldItem().stackSize--;

		logic.phase++;
	};

	public static Consumer<Object[]> RAD_CONTAINMENT_SYSTEM = (array) -> {
		LogicBlock.TileEntityLogicBlock logic = (LogicBlock.TileEntityLogicBlock) array[1];
		EntityPlayer player = (EntityPlayer) array[5];

		if(player.getHeldItem() != null && player.getHeldItem().getItem() == ModItems.key){
			player.getHeldItem().stackSize--;
			player.addChatMessage(new ChatComponentText(
					EnumChatFormatting.LIGHT_PURPLE + "[RAD CONTAINMENT SYSTEM]" +
						EnumChatFormatting.RESET + " Radiation treatment administered"));
			player.addPotionEffect(new PotionEffect(HbmPotion.radaway.getId(), 3 * 60 * 20, 4));
			player.addPotionEffect(new PotionEffect(HbmPotion.radx.getId(), 3 * 60 * 20, 4));
			logic.phase = 2;
			logic.timer = 0;
		}
	};

	public static Consumer<Object[]> POWER_LOCK = (array) -> {
		World world = (World) array[0];
		LogicBlock.TileEntityLogicBlock logic = (LogicBlock.TileEntityLogicBlock) array[1];
		EntityPlayer player = (EntityPlayer) array[5];
		int x = (int) array[2];
		int y = (int) array[3];
		int z = (int) array[4];

		IEnergyHandlerMK2 handler = null;
		ForgeDirection parallel = logic.direction.getRotation(ForgeDirection.UP);

		for (int i1 = 0; i1 < 6; ++i1) {
			if(world.getTileEntity(x + Facing.offsetsXForSide[i1], y + Facing.offsetsYForSide[i1], z + Facing.offsetsZForSide[i1]) instanceof IEnergyHandlerMK2) {
				handler = (IEnergyHandlerMK2) world.getTileEntity(x + Facing.offsetsXForSide[i1], y + Facing.offsetsYForSide[i1], z + Facing.offsetsZForSide[i1]);
				break;
			}
		}

		if(handler == null || !(handler.getPower() > 500_000))
				player.addChatMessage(new ChatComponentText(
				EnumChatFormatting.LIGHT_PURPLE + "[POWER LOCK]" +
					EnumChatFormatting.RESET + " Charge adjacent energy storage to at least 500KHE to release emergency lock"));
		else {
			player.addChatMessage(new ChatComponentText(
				EnumChatFormatting.LIGHT_PURPLE + "[POWER LOCK]" +
					EnumChatFormatting.RESET + " Power Restorted! Safe Unlocked!"));

			TileEntityLockableBase safe = null;
			for (int i1 = 0; i1 < 6; ++i1) {
				if (world.getTileEntity(x + Facing.offsetsXForSide[i1], y + Facing.offsetsYForSide[i1], z + Facing.offsetsZForSide[i1]) instanceof TileEntityLockableBase) {
					safe = (TileEntityLockableBase) world.getTileEntity(x + Facing.offsetsXForSide[i1], y + Facing.offsetsYForSide[i1], z + Facing.offsetsZForSide[i1]);
					break;
				}
			}
			if(safe != null){
				safe.unlock();
				world.playSoundAtEntity(player, "hbm:block.lockOpen", 3.0F, 0.8F);
			}

		}

	};



	public static List<String> getInteractionNames(){
		return new ArrayList<>(interactions.keySet());
	}

	//register new interactions here
	static{
		initialize();
	}
	public static void initialize() {
		interactions = new LinkedHashMap<>();

		interactions.put("POWER_LOCK", POWER_LOCK);

		//example interactions
		interactions.put("TEST", TEST);
		interactions.put("RADAWAY_INJECTOR", RAD_CONTAINMENT_SYSTEM);
	}



}
