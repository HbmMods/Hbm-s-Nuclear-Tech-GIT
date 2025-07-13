package com.hbm.main;

import codechicken.nei.api.API;
import codechicken.nei.api.IConfigureNEI;
import codechicken.nei.api.IHighlightHandler;
import codechicken.nei.api.ItemInfo.Layout;
import codechicken.nei.recipe.*;
import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.generic.BlockPlushie.TileEntityPlushie;
import com.hbm.config.ClientConfig;
import com.hbm.config.CustomMachineConfigJSON;
import com.hbm.handler.nei.CustomMachineHandler;
import com.hbm.items.ItemEnums.EnumIngotMetal;
import com.hbm.items.ItemEnums.EnumSecretType;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemBattery;
import com.hbm.items.weapon.sedna.ItemGunBaseNT;
import com.hbm.items.weapon.sedna.factory.GunFactory.EnumAmmoSecret;
import com.hbm.lib.RefStrings;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

import java.util.List;

public class NEIConfig implements IConfigureNEI {

	@Override
	public void loadConfig() {
		for (TemplateRecipeHandler handler: NEIRegistry.listAllHandlers()) {
			registerHandler(handler);
		}

		for(CustomMachineConfigJSON.MachineConfiguration conf : CustomMachineConfigJSON.niceList) {
			registerHandlerBypass(new CustomMachineHandler(conf));
		}

		if(ClientConfig.NEI_HIDE_SECRETS.get()) {
			for(Item item : ItemGunBaseNT.secrets) API.hideItem(new ItemStack(item));
			for(int i = 0; i < EnumAmmoSecret.values().length; i++) API.hideItem(new ItemStack(ModItems.ammo_secret, 1, i));
			for(int i = 0; i < EnumSecretType.values().length; i++) API.hideItem(new ItemStack(ModItems.item_secret, 1, i));
		}
		
		for(int i = 0; i < EnumIngotMetal.values().length; i++) API.hideItem(new ItemStack(ModItems.ingot_metal, 1, i));

		//Some things are even beyond my control...or are they?
		API.hideItem(ItemBattery.getEmptyBattery(ModItems.memory));
		API.hideItem(ItemBattery.getFullBattery(ModItems.memory));

		API.hideItem(new ItemStack(ModBlocks.machine_electric_furnace_on));
		API.hideItem(new ItemStack(ModBlocks.machine_difurnace_on));
		API.hideItem(new ItemStack(ModBlocks.machine_rtg_furnace_on));
		API.hideItem(new ItemStack(ModBlocks.reinforced_lamp_on));
		API.hideItem(new ItemStack(ModBlocks.statue_elb_f));
		API.hideItem(new ItemStack(ModBlocks.cheater_virus));
		API.hideItem(new ItemStack(ModBlocks.cheater_virus_seed));
		API.hideItem(new ItemStack(ModItems.euphemium_kit));
		API.hideItem(new ItemStack(ModItems.bobmazon_hidden));
		API.hideItem(new ItemStack(ModItems.book_lore)); //the broken nbt-less one shouldn't show up in normal play anyway
		if(MainRegistry.polaroidID != 11) {
			API.hideItem(new ItemStack(ModItems.book_secret));
			API.hideItem(new ItemStack(ModItems.book_of_));
			API.hideItem(new ItemStack(ModItems.burnt_bark));
			API.hideItem(new ItemStack(ModItems.ams_core_thingy));
		}
		API.hideItem(new ItemStack(ModBlocks.dummy_block_vault));
		API.hideItem(new ItemStack(ModBlocks.dummy_block_blast));
		API.hideItem(new ItemStack(ModBlocks.dummy_port_compact_launcher));
		API.hideItem(new ItemStack(ModBlocks.dummy_port_launch_table));
		API.hideItem(new ItemStack(ModBlocks.dummy_plate_compact_launcher));
		API.hideItem(new ItemStack(ModBlocks.dummy_plate_launch_table));
		API.hideItem(new ItemStack(ModBlocks.dummy_plate_cargo));

		API.hideItem(new ItemStack(ModBlocks.pink_log));
		API.hideItem(new ItemStack(ModBlocks.pink_planks));
		API.hideItem(new ItemStack(ModBlocks.pink_slab));
		API.hideItem(new ItemStack(ModBlocks.pink_double_slab));
		API.hideItem(new ItemStack(ModBlocks.pink_stairs));

		API.hideItem(new ItemStack(ModBlocks.spotlight_incandescent_off));
		API.hideItem(new ItemStack(ModBlocks.spotlight_fluoro_off));
		API.hideItem(new ItemStack(ModBlocks.spotlight_halogen_off));
		API.hideItem(new ItemStack(ModBlocks.spotlight_beam));

		API.hideItem(new ItemStack(ModBlocks.conveyor));
		API.hideItem(new ItemStack(ModBlocks.conveyor_chute));
		API.hideItem(new ItemStack(ModBlocks.conveyor_lift));
		API.hideItem(new ItemStack(ModBlocks.conveyor_express));
		API.hideItem(new ItemStack(ModBlocks.conveyor_double));
		API.hideItem(new ItemStack(ModBlocks.conveyor_triple));

		API.registerHighlightIdentifier(ModBlocks.plushie, new IHighlightHandler() {
			@Override public ItemStack identifyHighlight(World world, EntityPlayer player, MovingObjectPosition mop) {
				int x = mop.blockX;
				int y = mop.blockY;
				int z = mop.blockZ;
				TileEntity te = world.getTileEntity(x, y, z);
				if(te instanceof TileEntityPlushie) {
					TileEntityPlushie plush = (TileEntityPlushie) te;
					return new ItemStack(ModBlocks.plushie, 1, plush.type.ordinal());
				}
				return null;
			}
			@Override public List<String> handleTextData(ItemStack itemStack, World world, EntityPlayer player, MovingObjectPosition mop, List<String> currenttip, Layout layout) { return currenttip; }
		});
	}

	public static void registerHandler(Object o) {
		API.registerRecipeHandler((ICraftingHandler) o);
		API.registerUsageHandler((IUsageHandler) o);
	}

	/** Bypasses the utterly useless restriction of one registered handler per class */
	public static void registerHandlerBypass(Object o) {
		GuiCraftingRecipe.craftinghandlers.add((ICraftingHandler) o);
		GuiUsageRecipe.usagehandlers.add((IUsageHandler) o);
	}

	@Override
	public String getName() {
		return "Nuclear Tech NEI Plugin";
	}

	@Override
	public String getVersion() {
		return RefStrings.VERSION;
	}
}
