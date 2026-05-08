package com.oneof90.main;

import com.oneof90.render.block.*;
import com.oneof90.tileentity.*;

import cpw.mods.fml.client.registry.ClientRegistry;
import net.minecraftforge.client.MinecraftForgeClient;
import com.oneof90.render.item.ItemRenderNTMSteelBeam;
import com.oneof90.render.item.ItemRenderNTMSteelBeamVertical;
import net.minecraft.item.Item;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * 1of90 Edition Main Registry
 *
 * Central initialization point for the 1of90 mod extension.
 * This allows adding new content independently from HBM's code.
 */
public class MainRegistry1of90 {

	/**
	 * Initialize all 1of90 content (called during mod pre-initialization)
	 */
	public static void init() {
		ModBlocks1of90.init();
		ModItems1of90.init();
	}

	/**
	 * Register client-side renderers (called during client initialization)
	 */
	@SideOnly(Side.CLIENT)
	public static void initClient() {
		registerTileEntityRenderers();
		registerItemRenderers();
	}

	/**
	 * Register all tile entity special renderers
	 */
	@SideOnly(Side.CLIENT)
	private static void registerTileEntityRenderers() {
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityNTMSteelBeam.class, new RenderNTMSteelBeam());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityNTMSteelBeamVertical.class, new RenderNTMSteelBeamVertical());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCageLadder.class, new RenderTileEntityCageLadder());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCageLadderBase.class, new RenderTileEntityCageLadderBase());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityDieselGen.class, new RenderDieselGen());
		// Marble Saw renderer removed (saw was deleted)
	}

	/**
	 * Register item renderers for the NTM blocks so their item forms
	 * use the custom OBJ-based item renderers.
	 */
	@SideOnly(Side.CLIENT)
	private static void registerItemRenderers() {
		// Horizontal beam item
		Item itemBeam = Item.getItemFromBlock(ModBlocks1of90.ntm_steel_beam);
		if(itemBeam != null)
			MinecraftForgeClient.registerItemRenderer(itemBeam, new ItemRenderNTMSteelBeam());

		// Vertical beam item
		Item itemBeamV = Item.getItemFromBlock(ModBlocks1of90.ntm_steel_beam_vertical);
		if(itemBeamV != null)
			MinecraftForgeClient.registerItemRenderer(itemBeamV, new ItemRenderNTMSteelBeamVertical());
	}
}
