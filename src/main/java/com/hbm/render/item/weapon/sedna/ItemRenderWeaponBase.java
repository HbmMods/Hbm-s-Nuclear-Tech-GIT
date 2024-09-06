package com.hbm.render.item.weapon.sedna;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.Project;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

public abstract class ItemRenderWeaponBase implements IItemRenderer {

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		return type != ItemRenderType.FIRST_PERSON_MAP;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
		return helper == ItemRendererHelper.ENTITY_BOBBING || helper == ItemRendererHelper.ENTITY_ROTATION;
	}

	@SuppressWarnings("incomplete-switch") //shut the fuck up
	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		GL11.glPushMatrix();
		switch(type) {
		case EQUIPPED_FIRST_PERSON:	setupFirstPerson(item);	renderFirstPerson(item); break;
		case EQUIPPED:				setupThirdPerson(item);	renderOther(item, type); break;
		case INVENTORY:				setupInv(item);			renderOther(item, type); break;
		case ENTITY:				setupEntity(item);		renderOther(item, type); break;
		}
		GL11.glPopMatrix();
	}
	
	public void setupFirstPerson(ItemStack stack) {
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        float f1 = 0.07F;
        Minecraft mc = Minecraft.getMinecraft();
        float farPlaneDistance = mc.gameSettings.renderDistanceChunks * 16;
        float interp = 0;

        Project.gluPerspective(1, (float)mc.displayWidth / (float)mc.displayHeight, 0.05F, farPlaneDistance * 2.0F);


        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glLoadIdentity();

        GL11.glPushMatrix();
        //this.hurtCameraEffect(interp);

        if (mc.gameSettings.thirdPersonView == 0 && !mc.renderViewEntity.isPlayerSleeping() && !mc.gameSettings.hideGUI && !mc.playerController.enableEverythingIsScrewedUpMode())
        {
            //this.enableLightmap((double)p_78476_1_);
            this.renderItem(ItemRenderType.EQUIPPED_FIRST_PERSON, stack, null, mc.thePlayer);
            //this.disableLightmap((double)p_78476_1_);
        }

        GL11.glPopMatrix();
        
		//GL11.glRotated(90, 0, 1, 0);
		//GL11.glRotated(40, -1, 0, 0);
	}
	
	public void setupThirdPerson(ItemStack stack) {
		
	}
	
	public void setupInv(ItemStack stack) {
		GL11.glScaled(1, 1, -1);
		GL11.glTranslated(8, 8, 0);
		GL11.glRotated(225, 0, 0, 1);
		GL11.glRotated(90, 0, 1, 0);
	}
	
	public void setupEntity(ItemStack stack) {
		
	}

	public abstract void renderFirstPerson(ItemStack stack);
	public abstract void renderOther(ItemStack stack, ItemRenderType type);
}
