package com.hbm.render.item.weapon;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;
import com.hbm.render.anim.HbmAnimations;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

public class ItemRenderUACPistol implements IItemRenderer {
	Minecraft mc = Minecraft.getMinecraft();

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		return type == ItemRenderType.FIRST_PERSON_MAP ? false : true;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
		return type == ItemRenderType.ENTITY && (helper == ItemRendererHelper.ENTITY_BOBBING || helper == ItemRendererHelper.ENTITY_ROTATION);
	}

	static final String lower = "lower_pistol_0";
	static final String mag = "mag_pistol_0";
	static final String slide = "slide_pistol_0";
	static final String trigger = "trigger_pistol_0";
	static final String hammer = "bool_obj_bool_obj_data.002";

	static final float scale1 = 0.05F;
	static final float scale2 = 0.025F;
	static final float scale3 = 0.05F;
	static final float scale4 = 0.55F;

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		GL11.glPushMatrix();
		mc.renderEngine.bindTexture(ResourceManager.uac_pistol_tex);
		EntityPlayer player = mc.thePlayer;
		GL11.glShadeModel(GL11.GL_SMOOTH);

		double[] slideAnim = HbmAnimations.getRelevantTransformation("SLIDE");
		double[] hammerAnim = HbmAnimations.getRelevantTransformation("HAMMER");

		switch(type) {
		case ENTITY:// Dropped item
			GL11.glScalef(scale1, scale1, scale1);
			break;
		case EQUIPPED:// In hand from other's POV
			GL11.glScalef(scale2, scale2, scale2);
			GL11.glRotatef(90F, 0F, 1F, 0F);
			GL11.glRotatef(-45F, 1F, 0F, 0F);
			GL11.glTranslatef(0F, -12F, 30F);

			GL11.glPushMatrix();
			ResourceManager.uac_pistol.renderOnly(trigger, mag, lower);
			GL11.glPopMatrix();

			GL11.glPushMatrix();
			GL11.glTranslated(slideAnim[0], slideAnim[1], slideAnim[2] * 2);
			ResourceManager.uac_pistol.renderPart(slide);
			GL11.glPopMatrix();

			GL11.glPushMatrix();
			GL11.glRotated(hammerAnim[0], 1F, 0F, 0F);
			ResourceManager.uac_pistol.renderPart(hammer);
			GL11.glPopMatrix();
			break;
		case EQUIPPED_FIRST_PERSON:// In hand from POV
			GL11.glScalef(scale3, scale3, scale3);
			GL11.glRotatef(90F, 0F, 1F, 0F);
			if(player.isSneaking()) {
				GL11.glRotatef(-25F, 1F, 0F, 0F);
				GL11.glRotatef(-5F, 0F, 1F, 0F);
				GL11.glTranslatef(20.15F, 3.5F, 18F);
			} else {
				GL11.glRotatef(-25F, 1F, 0F, 0F);
				GL11.glTranslatef(-3F, -5F, 20F);
			}
			GL11.glPushMatrix();
			GL11.glTranslated(slideAnim[0], slideAnim[1], slideAnim[2] * 2);
			ResourceManager.uac_pistol.renderPart(slide);
			GL11.glPopMatrix();

			GL11.glPushMatrix();
			GL11.glRotated(hammerAnim[0], 1F, 0F, 0F);
			ResourceManager.uac_pistol.renderPart(hammer);
			GL11.glPopMatrix();
			break;
		case INVENTORY:
			GL11.glScalef(scale4, scale4, scale4);
			GL11.glRotatef(90F, 0F, 1F, 0F);
			GL11.glRotatef(150F, 1F, 0F, 0F);
			GL11.glTranslatef(0F, -8F, -24F);
			break;
		default:
			break;
		}
		if(type != ItemRenderType.EQUIPPED_FIRST_PERSON && type != ItemRenderType.EQUIPPED)
			ResourceManager.uac_pistol.renderAll();
		else
			ResourceManager.uac_pistol.renderOnly(trigger, mag, lower);
		GL11.glShadeModel(GL11.GL_FLAT);
		GL11.glPopMatrix();
	}

}