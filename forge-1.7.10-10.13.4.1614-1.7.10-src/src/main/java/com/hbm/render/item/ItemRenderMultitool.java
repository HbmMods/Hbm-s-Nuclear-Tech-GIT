package com.hbm.render.item;

import org.lwjgl.opengl.GL11;

import com.hbm.items.ModItems;
import com.hbm.lib.RefStrings;
import com.hbm.render.model.ModelMultitoolClaw;
import com.hbm.render.model.ModelMultitoolFist;
import com.hbm.render.model.ModelMultitoolOpen;
import com.hbm.render.model.ModelMultitoolPointer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;

public class ItemRenderMultitool implements IItemRenderer {

	protected ModelMultitoolOpen open;
	protected ModelMultitoolClaw claw;
	protected ModelMultitoolFist fist;
	protected ModelMultitoolPointer pointer;
    public RenderPlayer renderPlayer;
	
	public ItemRenderMultitool() {
		open = new ModelMultitoolOpen();
		claw = new ModelMultitoolClaw();
		fist = new ModelMultitoolFist();
		pointer = new ModelMultitoolPointer();
		renderPlayer = new RenderPlayer();
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
		switch(type) {
		case EQUIPPED_FIRST_PERSON:
			GL11.glPushMatrix();
				GL11.glEnable(GL11.GL_CULL_FACE);
				
				Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(RefStrings.MODID +":textures/models/ModelMultitool.png"));
				GL11.glRotatef(-135.0F, 0.0F, 0.0F, 1.0F);
				GL11.glTranslatef(-0.5F, 0.0F, -0.2F);
				//GL11.glScalef(2.0F, 2.0F, 2.0F);
				GL11.glScalef(0.5F, 0.5F, 0.5F);
				//GL11.glTranslatef(-0.4F, -0.1F, 0.1F);
				GL11.glTranslatef(-0.2F, -0.1F, -0.1F);
				if(item != null && item.getItem() == ModItems.multitool_dig)
					claw.render((Entity)data[1], 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
				if(item != null && item.getItem() == ModItems.multitool_silk)
					claw.render((Entity)data[1], 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
				if(item != null && item.getItem() == ModItems.multitool_ext)
					open.render((Entity)data[1], 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
				if(item != null && item.getItem() == ModItems.multitool_miner)
					pointer.render((Entity)data[1], 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
				if(item != null && item.getItem() == ModItems.multitool_hit)
					fist.render((Entity)data[1], 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
				if(item != null && item.getItem() == ModItems.multitool_beam)
					pointer.render((Entity)data[1], 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
				if(item != null && item.getItem() == ModItems.multitool_sky)
					open.render((Entity)data[1], 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
				if(item != null && item.getItem() == ModItems.multitool_mega)
					fist.render((Entity)data[1], 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
				if(item != null && item.getItem() == ModItems.multitool_joule)
					fist.render((Entity)data[1], 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
				if(item != null && item.getItem() == ModItems.multitool_decon)
					open.render((Entity)data[1], 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
				
				GL11.glScalef(2.0F, 2.0F, 2.0F);
				GL11.glRotatef(90.0F, 0.0F, 0.0F, 1.0F);
				GL11.glTranslatef(6 * 0.0625F, -12 * 0.0625F, 0 * 0.0625F);
				Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation("textures/entity/steve.png"));
		        renderPlayer.modelBipedMain.bipedRightArm.render(0.0625F);

			GL11.glPopMatrix();
			break;
		case EQUIPPED:
		case ENTITY:
			GL11.glPushMatrix();
				GL11.glEnable(GL11.GL_CULL_FACE);
				Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(RefStrings.MODID +":textures/models/ModelMultitool.png"));
				GL11.glScalef(0.75F, 0.75F, 0.75F);

				GL11.glRotatef(-200.0F, 0.0F, 0.0F, 1.0F);
				GL11.glRotatef(75.0F, 0.0F, 1.0F, 0.0F);
				GL11.glRotatef(-30.0F, 1.0F, 0.0F, 0.0F);
				GL11.glRotatef(-5.0F, 0.0F, 0.0F, 1.0F);

				GL11.glTranslatef(-4 * 0.0625F, 2 * 0.0625F, -9 * 0.0625F);
				
				if(item != null && item.getItem() == ModItems.multitool_dig)
					claw.render((Entity)data[1], 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
				if(item != null && item.getItem() == ModItems.multitool_silk)
					claw.render((Entity)data[1], 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
				if(item != null && item.getItem() == ModItems.multitool_ext)
					open.render((Entity)data[1], 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
				if(item != null && item.getItem() == ModItems.multitool_miner)
					pointer.render((Entity)data[1], 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
				if(item != null && item.getItem() == ModItems.multitool_hit)
					fist.render((Entity)data[1], 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
				if(item != null && item.getItem() == ModItems.multitool_beam)
					pointer.render((Entity)data[1], 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
				if(item != null && item.getItem() == ModItems.multitool_sky)
					open.render((Entity)data[1], 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
				if(item != null && item.getItem() == ModItems.multitool_mega)
					fist.render((Entity)data[1], 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
				if(item != null && item.getItem() == ModItems.multitool_joule)
					fist.render((Entity)data[1], 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
				if(item != null && item.getItem() == ModItems.multitool_decon)
					open.render((Entity)data[1], 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
				
			GL11.glPopMatrix();
		default: break;
		}
	}
}
