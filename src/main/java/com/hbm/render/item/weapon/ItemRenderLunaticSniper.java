package com.hbm.render.item.weapon;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;
import com.hbm.render.anim.HbmAnimations;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

public class ItemRenderLunaticSniper implements IItemRenderer
{

	public ItemRenderLunaticSniper() {}

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type)
	{
		switch (type)
		{
		case EQUIPPED:
		case EQUIPPED_FIRST_PERSON:
		case ENTITY:
		case INVENTORY:
			return true;
		default:
			return false;
		}
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper)
	{
		return type == ItemRenderType.ENTITY && (helper == ItemRendererHelper.ENTITY_BOBBING || helper == ItemRendererHelper.ENTITY_ROTATION);
	}
	static final String slide = "Slide_Cube.020_Cube.007";
	static final String everythingElse = "Full_Cylinder.007";
	static final String spentShell = "Spent_Casing_Casing";
	static final String fullRound = "Full_Round_Bullet";
	
	static final float scale1 = 0.2F;
	static final float scale2 = 0.15F;
	static final float scale3 = 0.3F;
	static final float scale4 = 0.8F;
	
	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data)
	{
		GL11.glPushMatrix();
		double[] recoil = HbmAnimations.getRelevantTransformation("RECOIL");
		double[] eject = HbmAnimations.getRelevantTransformation("EJECT");
		double[] tilt = HbmAnimations.getRelevantTransformation("TILT");
//		double[] insert = HbmAnimations.getRelevantTransformation("INSERT_ROUND");
		Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.lunatic_sniper_tex);
		
		GL11.glShadeModel(GL11.GL_SMOOTH);

		switch (type)
		{
		case EQUIPPED_FIRST_PERSON:// In hand from POV
			GL11.glRotatef(90F, 1F, 0F, 0F);
			GL11.glRotatef(-58.5F, 0F, 1F, 0F);
			GL11.glRotatef(90F, 0F, 0F, 1F);
			GL11.glTranslatef(-0.5F, -0.15F, 0F);
			GL11.glScalef(scale1, scale1, scale1);
			
			/// Begin animations ///
			
			// Move on recoil
			GL11.glTranslated(0, 0, recoil[1] * 10);
			GL11.glRotated(recoil[0] * 10, 0, 0, 1);
			// Move on reload
			GL11.glPushMatrix();
			GL11.glRotated(tilt[0] * 2, 0, 0, 1);
			GL11.glRotated(-tilt[1] * 2, 0, 1, 0);
			GL11.glTranslated(0, 0, -tilt[1] / 8);
			ResourceManager.lunatic_sniper.renderPart(everythingElse);
			GL11.glPopMatrix();
			// Release slide
			GL11.glPushMatrix();
			GL11.glRotated(tilt[0] * 2, 0, 0, 1);
			GL11.glRotated(-tilt[1] * 2, 0, 1, 0);
			GL11.glTranslated(0, 0, -tilt[2] * 15);
			ResourceManager.lunatic_sniper.renderPart(slide);
			GL11.glPopMatrix();
			// Drop in new round
			GL11.glPushMatrix();
			GL11.glRotated(tilt[0], 0, 0, 1);
			GL11.glTranslated(0, tilt[1], tilt[2]);
			ResourceManager.lunatic_sniper.renderPart(fullRound);
			GL11.glPopMatrix();
			// Eject casing
			GL11.glPushMatrix();
			GL11.glTranslated(0, 2, 0);//FIXME Where on earth is it?!
			ResourceManager.lunatic_sniper.renderPart(spentShell);
			GL11.glPopMatrix();
			break;
		case EQUIPPED:// In hand from other's POV
			GL11.glRotatef(90F, 1F, 0F, 0F);
			GL11.glRotatef(-50F, 0F, 1F, 0F);
			GL11.glRotatef(90F, 0F, 0F, 1F);
			GL11.glTranslatef(0F, -0.25F, -0.76F);
			GL11.glScalef(scale2 - scale2 * 2, scale2, scale2);
			GL11.glPushMatrix();
			GL11.glTranslated(eject[0] / 2, 0, -5);
			ResourceManager.lunatic_sniper.renderPart(spentShell);
			GL11.glPopMatrix();
			break;
		case ENTITY:// Dropped item
			GL11.glScalef(scale3, scale3, scale3);
			break;
		case INVENTORY:
			GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
			GL11.glRotatef(132F, 0.0F, 1.0F, 0.0F);
			GL11.glRotatef(90.0F, 0.0F, 0.0F, 1.0F);
			GL11.glTranslatef(0.0F, -2F, 15F);
			GL11.glScalef(scale4, scale4, scale4);
			break;
		default:
			break;
		}
		if (type != ItemRenderType.EQUIPPED_FIRST_PERSON)
			ResourceManager.lunatic_sniper.renderAllExcept(fullRound, spentShell);
		
		GL11.glShadeModel(GL11.GL_FLAT);
		GL11.glPopMatrix();
	}

}
