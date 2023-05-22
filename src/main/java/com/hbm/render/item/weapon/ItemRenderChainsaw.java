package com.hbm.render.item.weapon;

import org.lwjgl.opengl.GL11;
import com.hbm.items.tool.ItemToolAbilityFueled;
import com.hbm.main.ResourceManager;
import com.hbm.render.anim.HbmAnimations;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraftforge.client.IItemRenderer;

public class ItemRenderChainsaw implements IItemRenderer {

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		switch(type) {
		case EQUIPPED:
		case EQUIPPED_FIRST_PERSON:
		case ENTITY:
		case INVENTORY:
			return true;
		default: return false;
		}
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
		return type == ItemRenderType.ENTITY && (helper == ItemRendererHelper.ENTITY_ROTATION || helper == ItemRendererHelper.ENTITY_BOBBING);
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		
		GL11.glPushMatrix();
		
		EntityPlayer player = Minecraft.getMinecraft().thePlayer;
		
		GL11.glEnable(GL11.GL_CULL_FACE);
		Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.chainsaw_tex);
		
		switch(type) {
		
		case EQUIPPED_FIRST_PERSON:
			
			player.isSwingInProgress = false;
			
			double s0 = 0.35D;
			GL11.glTranslated(0.5, 0.25, -0.25F);
			GL11.glRotated(45, 0, 0, 1);
			GL11.glRotated(80, 0, 1, 0);
			GL11.glScaled(s0, s0, s0);
			
			if(!player.isBlocking()) {
				double[] sRot = HbmAnimations.getRelevantTransformation("SWING_ROT");
				double[] sTrans = HbmAnimations.getRelevantTransformation("SWING_TRANS");
				GL11.glTranslated(sTrans[0], sTrans[1], sTrans[2]);
				GL11.glRotated(sRot[2], 0, 0, 1);
				GL11.glRotated(sRot[1], 0, 1, 0);
				GL11.glRotated(sRot[0], 1, 0, 0);
			}
			
			break;
			
		case EQUIPPED:

			double scale = -0.375D;
			GL11.glScaled(scale, scale, scale);
			GL11.glRotated(85, 0, 1, 0);
			GL11.glRotated(135D, 1.0D, 0.0D, 0.0D);
			GL11.glTranslated(-0.125, -2.0, 1.75);
			
			break;
			
		case ENTITY:

			double s1 = 0.5D;
			GL11.glScaled(s1, s1, s1);
			break;
			
		case INVENTORY:

            GL11.glEnable(GL11.GL_ALPHA_TEST);
			GL11.glEnable(GL11.GL_LIGHTING);
			
			double s = 4D;
			GL11.glTranslated(8, 10, 0);
			GL11.glRotated(-90, 0, 1, 0);
			GL11.glRotated(-135, 1, 0, 0);
			GL11.glScaled(s, s, -s);
			
			break;
			
		default: break;
		}

		ResourceManager.chainsaw.renderPart("Saw");
		
		for(int i = 0; i < 20; i++) {

			double run = ((ItemToolAbilityFueled) item.getItem()).canOperate(item) ? System.currentTimeMillis() % 100D * 0.25D / 100D : 0.0625D;
			double forward = i * 0.25 + (run) - 2.0625;
			
			GL11.glPushMatrix();

			GL11.glTranslated(0, 0, 1.9375);
			GL11.glTranslated(0, 0.375, 0.5625);
			double angle = MathHelper.clamp_double(forward, 0, 0.25 * Math.PI);
			GL11.glRotated(angle * 180D / (Math.PI * 0.25), 1, 0, 0);
			GL11.glTranslated(0, -0.375, -0.5625);
			if(forward < 0) GL11.glTranslated(0, 0, forward);
			if(forward > Math.PI * 0.25) GL11.glTranslated(0, 0, forward - Math.PI * 0.25);
			ResourceManager.chainsaw.renderPart("Tooth");
			GL11.glPopMatrix();
		}
		
		GL11.glPopMatrix();
	}
}
