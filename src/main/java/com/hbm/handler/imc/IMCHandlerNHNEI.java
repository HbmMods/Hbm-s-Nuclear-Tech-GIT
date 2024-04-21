package com.hbm.handler.imc;

import codechicken.nei.recipe.TemplateRecipeHandler;
import com.hbm.lib.RefStrings;
import com.hbm.main.NEIRegistry;

import cpw.mods.fml.common.event.FMLInterModComms;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class IMCHandlerNHNEI {

	public static void IMCSender() {

		for(TemplateRecipeHandler handler : NEIRegistry.listAllHandlers()) {

			Class<? extends TemplateRecipeHandler> handlerClass = handler.getClass();

			if(handler instanceof ICompatNHNEI && ((ICompatNHNEI) handler).getMachinesForRecipe() != null) {
				String blockName = "hbm:" + ((ICompatNHNEI) handler).getMachinesForRecipe()[0].getUnlocalizedName();
				String hClass = handlerClass.getName();
				sendHandler(hClass, ((ICompatNHNEI) handler).getRecipeID(), blockName);
				for(ItemStack stack : ((ICompatNHNEI) handler).getMachinesForRecipe()) {
					sendCatalyst(hClass, "hbm:" + stack.getUnlocalizedName());
				}
			}
		}
	}

	private static void sendHandler(String aName, String handlerID, String aBlock) {
		sendHandler(aName, handlerID, aBlock, 3);
	}

	private static void sendHandler(String aName, String handlerID, String aBlock, int maxRecipesPerPage) {
		NBTTagCompound aNBT = new NBTTagCompound();
		aNBT.setString("handler", aName);
		aNBT.setString("handlerID", handlerID);
		aNBT.setString("modName", RefStrings.NAME);
		aNBT.setString("modId", RefStrings.MODID);
		aNBT.setBoolean("modRequired", true);
		aNBT.setString("itemName", aBlock);
		aNBT.setInteger("handlerHeight", 65);
		aNBT.setInteger("handlerWidth", 166);
		aNBT.setInteger("maxRecipesPerPage", maxRecipesPerPage);
		aNBT.setInteger("yShift", 6);
		FMLInterModComms.sendMessage("NotEnoughItems", "registerHandlerInfo", aNBT);
	}

	private static void sendCatalyst(String aName, String aStack, int aPriority) {
		NBTTagCompound aNBT = new NBTTagCompound();
		aNBT.setString("handlerID", aName);
		aNBT.setString("catalystHandlerID", aName);
		aNBT.setString("itemName", aStack);
		aNBT.setInteger("priority", aPriority);
		FMLInterModComms.sendMessage("NotEnoughItems", "registerCatalystInfo", aNBT);
	}

	private static void sendCatalyst(String aName, String aStack) {
		sendCatalyst(aName, aStack, 0);
	}
}
