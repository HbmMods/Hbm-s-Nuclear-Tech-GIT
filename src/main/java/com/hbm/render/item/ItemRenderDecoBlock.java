package com.hbm.render.item;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.ModBlocks;
import com.hbm.lib.RefStrings;
import com.hbm.render.model.ModelSteelBeam;
import com.hbm.render.model.ModelSteelCorner;
import com.hbm.render.model.ModelSteelRoof;
import com.hbm.render.model.ModelSteelScaffold;
import com.hbm.render.model.ModelSteelWall;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;

public class ItemRenderDecoBlock implements IItemRenderer {
	
	protected ModelSteelWall wall;
	protected ModelSteelCorner corner;
	protected ModelSteelRoof roof;
	protected ModelSteelBeam beam;
	protected ModelSteelScaffold scaffold;
	
	public ItemRenderDecoBlock() {
		wall = new ModelSteelWall();
		corner = new ModelSteelCorner();
		roof = new ModelSteelRoof();
		beam = new ModelSteelBeam();
		scaffold = new ModelSteelScaffold();
	}
	
	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		switch(type) {
		case EQUIPPED:
		case EQUIPPED_FIRST_PERSON:
		case ENTITY:
			return true;
		default: return false;
		}
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
		return false;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		if(item.getItem() == Item.getItemFromBlock(ModBlocks.steel_wall))
			Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(RefStrings.MODID +":textures/models/SteelWall.png"));
		if(item.getItem() == Item.getItemFromBlock(ModBlocks.steel_corner))
			Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(RefStrings.MODID +":textures/models/SteelCorner.png"));
		if(item.getItem() == Item.getItemFromBlock(ModBlocks.steel_roof))
			Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(RefStrings.MODID +":textures/models/SteelRoof.png"));
		if(item.getItem() == Item.getItemFromBlock(ModBlocks.steel_beam))
			Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(RefStrings.MODID +":textures/models/SteelBeam.png"));
		if(item.getItem() == Item.getItemFromBlock(ModBlocks.steel_scaffold))
			Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(RefStrings.MODID +":textures/models/SteelScaffold.png"));
		
		switch(type) {
		case ENTITY:
			GL11.glPushMatrix();
			GL11.glScalef(0.5F, 0.5F, 0.5F);
			GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
			GL11.glTranslatef(0.0F, -1.0F, 0.0F);
			if(item.getItem() == Item.getItemFromBlock(ModBlocks.steel_wall))
				wall.render((Entity)data[1], 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
			if(item.getItem() == Item.getItemFromBlock(ModBlocks.steel_corner))
				corner.render((Entity)data[1], 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
			if(item.getItem() == Item.getItemFromBlock(ModBlocks.steel_roof))
				roof.render((Entity)data[1], 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
			if(item.getItem() == Item.getItemFromBlock(ModBlocks.steel_beam))
				beam.render((Entity)data[1], 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
			if(item.getItem() == Item.getItemFromBlock(ModBlocks.steel_scaffold))
				scaffold.render((Entity)data[1], 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
		GL11.glPopMatrix();
			break;
		case EQUIPPED:
			GL11.glPushMatrix();
				GL11.glScalef(0.5F, 0.5F, 0.5F);
				GL11.glTranslatef(0.8F, -0.3F, 0.2F);
				if(item.getItem() == Item.getItemFromBlock(ModBlocks.steel_wall))
					wall.render((Entity)data[1], 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
				if(item.getItem() == Item.getItemFromBlock(ModBlocks.steel_corner))
					corner.render((Entity)data[1], 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
				if(item.getItem() == Item.getItemFromBlock(ModBlocks.steel_roof))
					roof.render((Entity)data[1], 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
				if(item.getItem() == Item.getItemFromBlock(ModBlocks.steel_beam))
					beam.render((Entity)data[1], 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
				if(item.getItem() == Item.getItemFromBlock(ModBlocks.steel_scaffold))
					scaffold.render((Entity)data[1], 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
			GL11.glPopMatrix();
			break;
		case EQUIPPED_FIRST_PERSON:
			GL11.glPushMatrix();
				GL11.glRotatef(-135.0F, 0.0F, 0.0F, 1.0F);
				GL11.glTranslatef(-0.6F, -0.6F, -0.1F);
				GL11.glScalef(0.5F, 0.5F, 0.5F);
				if(item.getItem() == Item.getItemFromBlock(ModBlocks.steel_wall))
					wall.render((Entity)data[1], 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
				if(item.getItem() == Item.getItemFromBlock(ModBlocks.steel_corner))
					corner.render((Entity)data[1], 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
				if(item.getItem() == Item.getItemFromBlock(ModBlocks.steel_roof))
					roof.render((Entity)data[1], 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
				if(item.getItem() == Item.getItemFromBlock(ModBlocks.steel_beam))
					beam.render((Entity)data[1], 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
				if(item.getItem() == Item.getItemFromBlock(ModBlocks.steel_scaffold))
					scaffold.render((Entity)data[1], 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
		GL11.glPopMatrix();
			break;
		default: break;
		}
	}

}
