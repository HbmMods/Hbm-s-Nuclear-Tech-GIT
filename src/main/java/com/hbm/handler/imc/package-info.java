package com.hbm.handler.imc;

/*
	Howdy! This is the documentation for NTM's inter mod comms. This interface allows other mods to tell NTM to do all sorts of things.
	IMC messages are handled in the IMCEvent which is called between the mod's init and post-init phases. Make sure to send your IMC
	messages before they are processed!
	
	
	######################################## RECIPES ########################################
	
	####################### ACIDIZER #######################
	KEY: crystallizer
	VALUE: NBT
	
	[MANDATORY]
	To set the output, set a tag compound named "output" that holds the itemstack info (set via ItemStack.writeToNBT)
	
	[MANDATORY]
	To set the input, there are two choices:
	
	- Set another tag compound like the output called "output". This will make the input a fixed item.
	- Set a string called "oredict". This will make the input an ore dict entry and allow processing of genericized items.
	
	EXAMPLES:
	
	Recipe with fixed item, acidizing an alloy chestplate into 8 ingots:
		NBTTagCompound msg0 = new NBTTagCompound();
		NBTTagCompound ing0 = new NBTTagCompound();
		new ItemStack(ModItems.alloy_plate).writeToNBT(ing0);
		msg0.setTag("input", ing0);
		NBTTagCompound out0 = new NBTTagCompound();
		new ItemStack(ModItems.ingot_advanced_alloy, 8).writeToNBT(out0);
		msg0.setTag("output", out0);
		FMLInterModComms.sendMessage("hbm", "crystallizer", msg0);

	Recipe with an ore dict item, acidizing all "plateSteel" into a steel ingot:
		NBTTagCompound msg1 = new NBTTagCompound();
		msg1.setString("oredict", "plateSteel");
		NBTTagCompound out1 = new NBTTagCompound();
		new ItemStack(ModItems.ingot_steel, 1).writeToNBT(out1);
		msg1.setTag("output", out1);
		FMLInterModComms.sendMessage("hbm", "crystallizer", msg1);
*/