package com.hbm.world.gen.util;

import com.hbm.blocks.generic.LogicBlock;
import com.hbm.blocks.generic.LogicBlock.TileEntityLogicBlock;
import com.hbm.items.ModItems;
import com.hbm.potion.HbmPotion;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.function.Consumer;

/**Interactions are called when the player right-clicks the block**/
public class LogicBlockInteractions {

	/**Consumer consists of world instance, tile entity instance, three ints for coordinates, one int for block side, and player instance,
	 * in that order **/
	public static LinkedHashMap<String, Consumer<Object[]>> interactions = new LinkedHashMap<>();

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



	public static List<String> getInteractionNames(){
		return new ArrayList<>(interactions.keySet());
	}

	//register new interactions here
	static{
		interactions.put("TEST", TEST);
		interactions.put("RADAWAY_INJECTOR", RAD_CONTAINMENT_SYSTEM);
	}



}
