package com.hbm.render.item.weapon;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import com.hbm.handler.BulletConfigSyncingUtil;
import com.hbm.handler.BulletConfiguration;
import com.hbm.items.weapon.ItemGunBase;
import com.hbm.main.ResourceManager;
import com.hbm.render.anim.HbmAnimations;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

public class ItemRenderWeaponKS23 implements IItemRenderer {
	
	public ItemRenderWeaponKS23() { }

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
		GL11.glDisable(GL11.GL_CULL_FACE);
		
		EntityPlayer player = Minecraft.getMinecraft().thePlayer;

        Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.ks23_tex);
		
		switch(type) {
		
		case EQUIPPED_FIRST_PERSON:
			
            GL11.glTranslatef(1.0F, 0.5F, -0.25F);
            GL11.glRotatef(25F, 0.0F, 0.0F, 1.0F);
            GL11.glRotatef(-100, 0.0F, 1.0F, 0.0F);
            GL11.glScaled(0.75, 0.75, 0.75);
            
            if(player.isSneaking()) {
                GL11.glRotatef(4.5F, 0.0F, 1.0F, 0.0F);
                GL11.glRotatef(-2F, 0.0F, 0.0F, 1.0F);
                GL11.glTranslatef(-0.9F, 0.2F, 0.325F);
            }

            HbmAnimations.applyRelevantTransformation("Body");

            GL11.glShadeModel(GL11.GL_SMOOTH);

            ResourceManager.ks23.renderPart("Body");
            ResourceManager.ks23.renderPart("Trigger");

            GL11.glPushMatrix();
            HbmAnimations.applyRelevantTransformation("Bolt");
            ResourceManager.ks23.renderPart("Bolt");
            GL11.glPopMatrix();

            GL11.glPushMatrix();
            HbmAnimations.applyRelevantTransformation("Guard");
            ResourceManager.ks23.renderPart("Guard");
            GL11.glPopMatrix();


            GL11.glPushMatrix();
            Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.casings_tex);

            HbmAnimations.applyRelevantTransformation("Shell");

            ItemGunBase gun = (ItemGunBase)item.getItem();
            BulletConfiguration bullet = BulletConfigSyncingUtil.pullConfig(gun.mainConfig.config.get(ItemGunBase.getMagType(item)));
            int[] colors = bullet.spentCasing.getColors();

            Color shellColor = new Color(colors[1]);
            GL11.glColor3f(shellColor.getRed() / 255F, shellColor.getGreen() / 255F, shellColor.getBlue() / 255F);
            ResourceManager.ks23.renderPart("Shell");
            
            Color shellForeColor = new Color(colors[0]);
            GL11.glColor3f(shellForeColor.getRed() / 255F, shellForeColor.getGreen() / 255F, shellForeColor.getBlue() / 255F);
            ResourceManager.ks23.renderPart("ShellFore");

            GL11.glColor3f(1F, 1F, 1F);
            GL11.glPopMatrix();


            GL11.glShadeModel(GL11.GL_FLAT);
    
            GL11.glEnable(GL11.GL_CULL_FACE);
            GL11.glPopMatrix();
			
			return;
			
		case EQUIPPED:

            GL11.glRotatef(20F, 1.0F, 0.0F, 1.0F);
            GL11.glRotatef(-170F, 0.0F, 1.0F, 0.0F);
            GL11.glTranslatef(-0.5F, 0.0F, -0.9F);
            GL11.glScaled(0.5, 0.5, 0.5);
			
			break;
			
		case ENTITY:
			
            GL11.glTranslatef(0.3F, 0.2F, 0.0F);
            GL11.glScaled(0.5, 0.5, 0.5);
			
			break;
			
		case INVENTORY:

			GL11.glEnable(GL11.GL_LIGHTING);

            GL11.glTranslatef(7F, 8F, 0.0F);
            GL11.glScaled(4, 4, -4);
            GL11.glRotatef(-90, 0, 1, 0);
            GL11.glRotatef(-135F, 1.0F, 0.0F, 0.0F);
			
			break;
			
		default: break;
		}

        GL11.glShadeModel(GL11.GL_SMOOTH);
        ResourceManager.ks23.renderAll();
        GL11.glShadeModel(GL11.GL_FLAT);

		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glPopMatrix();
	}

}
