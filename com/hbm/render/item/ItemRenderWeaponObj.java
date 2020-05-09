package com.hbm.render.item;

import org.lwjgl.opengl.GL11;

import com.hbm.items.ModItems;
import com.hbm.main.ResourceManager;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

public class ItemRenderWeaponObj implements IItemRenderer {
	
	public ItemRenderWeaponObj() { }

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		switch(type) {
		case EQUIPPED:
		case EQUIPPED_FIRST_PERSON:
		case ENTITY:
			return true;
		case INVENTORY:
			return item.getItem() == ModItems.gun_ks23 || item.getItem() == ModItems.gun_hk69;
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

		if(item.getItem() == ModItems.gun_hk69)
			Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.hk69_tex);

		if(item.getItem() == ModItems.gun_deagle)
			Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.turbofan_blades_tex);

		if(item.getItem() == ModItems.gun_supershotgun)
			Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.turbofan_blades_tex);

		if(item.getItem() == ModItems.gun_ks23)
			Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.ks23_tex);
		
		switch(type) {
		
		case EQUIPPED_FIRST_PERSON:
			

			if(item.getItem() == ModItems.gun_hk69) {
				GL11.glTranslatef(1.0F, 0.5F, 0.0F);
				GL11.glRotatef(90F, 0.0F, 1.0F, 0.0F);
				GL11.glRotatef(-25F, 1.0F, 0.0F, 0.0F);
				GL11.glRotatef(-10F, 0.0F, 1.0F, 0.0F);
				GL11.glScaled(0.75, 0.75, 0.75);
				
				if(player.isSneaking()) {
					GL11.glTranslatef(1.16F, 0.35F, -0.8F);
					GL11.glRotatef(5.5F, 0.0F, 1.0F, 0.0F);
					GL11.glRotatef(4F, 1.0F, 0.0F, 0.0F);
				}
			}

			if(item.getItem() == ModItems.gun_deagle) {
				GL11.glRotatef(90F, 0.0F, 1.0F, 0.0F);
				GL11.glRotatef(25F, -1.0F, 0.0F, 0.0F);
				GL11.glTranslatef(0.2F, 0.2F, 0.8F);
				GL11.glScaled(0.2, 0.2, 0.2);
				
				if(player.isSneaking()) {
					GL11.glTranslatef(3.7F, 1.7F, 0F);
					GL11.glRotatef(-5F, 0.0F, 1.0F, 0.0F);
				}
			}

			if(item.getItem() == ModItems.gun_supershotgun) {
				GL11.glRotatef(25F, 0.0F, 0.0F, 1.0F);
				GL11.glTranslatef(-0.5F, -0.2F, -0.3F);
				GL11.glScalef(1.5F, 1.5F, 1.5F);
				
				if(player.isSneaking()) {
					GL11.glTranslatef(0F, 0.25F, -0.555F);
					GL11.glRotatef(-5F, 0.0F, 1.0F, 0.0F);
				}
			}

			if(item.getItem() == ModItems.gun_ks23) {
				GL11.glTranslatef(1.0F, 0.85F, -0.25F);
				GL11.glRotatef(90F, 0.0F, 1.0F, 0.0F);
				GL11.glRotatef(-25F, 1.0F, 0.0F, 0.0F);
				GL11.glRotatef(-10F, 0.0F, 1.0F, 0.0F);
				GL11.glScaled(1.25, 1.25, 1.25);
				
				if(player.isSneaking()) {
					GL11.glRotatef(4.5F, 0.0F, 1.0F, 0.0F);
					GL11.glTranslatef(0.51F, 0.2F, 0.3F);
				}
			}
			
			break;
			
		case EQUIPPED:

			if(item.getItem() == ModItems.gun_hk69) {
				GL11.glRotatef(20F, 1.0F, 0.0F, 1.0F);
				GL11.glRotatef(10F, 0.0F, 1.0F, 0.0F);
				GL11.glTranslatef(0.4F, 0.0F, 0.55F);
				GL11.glScaled(0.75, 0.75, 0.75);
			}

			if(item.getItem() == ModItems.gun_deagle) {
				GL11.glRotatef(90F, 0.0F, 1.0F, 0.0F);
				GL11.glRotatef(35F, -1.0F, 0.0F, 0.0F);
				GL11.glTranslatef(0.0F, -0.1F, 0.8F);
				GL11.glScaled(0.15, 0.15, 0.15);
			}

			if(item.getItem() == ModItems.gun_supershotgun) {
				GL11.glRotatef(20F, 1.0F, 0.0F, 1.0F);
				GL11.glRotatef(-80F, 0.0F, 1.0F, 0.0F);
				GL11.glTranslatef(-0.5F, -0.3F, -0.5F);
				GL11.glScaled(1.5, 1.5, 1.5);
			}

			if(item.getItem() == ModItems.gun_ks23) {
				GL11.glRotatef(20F, 1.0F, 0.0F, 1.0F);
				GL11.glRotatef(10F, 0.0F, 1.0F, 0.0F);
				GL11.glTranslatef(0.4F, 0.2F, 1.2F);
				GL11.glScaled(1.25, 1.25, 1.25);
			}
			
			break;
			
		case ENTITY:
			
			if(item.getItem() == ModItems.gun_hk69) {
				GL11.glTranslatef(0.0F, 0.2F, 0.0F);
				GL11.glRotatef(90F, 0.0F, 1.0F, 0.0F);
				GL11.glScaled(0.75, 0.75, 0.75);
			}

			if(item.getItem() == ModItems.gun_deagle) {
				GL11.glTranslatef(0.0F, 0.2F, 0.0F);
				GL11.glScaled(0.25, 0.25, 0.25);
			}

			if(item.getItem() == ModItems.gun_supershotgun) {
				GL11.glTranslatef(-1.0F, -0.2F, 0.0F);
			}
			
			if(item.getItem() == ModItems.gun_ks23) {
				GL11.glTranslatef(0.5F, 0.2F, 0.0F);
				GL11.glRotatef(90F, 0.0F, 1.0F, 0.0F);
				GL11.glScaled(0.75, 0.75, 0.75);
			}
			
			break;
			
		case INVENTORY:

			GL11.glEnable(GL11.GL_LIGHTING);
			
			if(item.getItem() == ModItems.gun_hk69) {
				GL11.glScaled(7.5, 7.5, -7.5);
				GL11.glTranslatef(0.85F, 1.2F, 0.0F);
				GL11.glRotatef(180F, 1.0F, 0.0F, 0.0F);
				GL11.glRotatef(-90F, 0.0F, 1.0F, 0.0F);
				GL11.glRotatef(-45F, 1.0F, 0.0F, 0.0F);
			}
			
			if(item.getItem() == ModItems.gun_ks23) {
				GL11.glScaled(7.5, 7.5, -7.5);
				GL11.glTranslatef(0.65F, 0.4F, 0.0F);
				GL11.glRotatef(180F, 1.0F, 0.0F, 0.0F);
				GL11.glRotatef(-90F, 0.0F, 1.0F, 0.0F);
				GL11.glRotatef(-45F, 1.0F, 0.0F, 0.0F);
			}
			
			break;
			
		default: break;
		}

		if(item.getItem() == ModItems.gun_hk69) {
			GL11.glShadeModel(GL11.GL_SMOOTH);
			ResourceManager.hk69.renderAll();
			GL11.glShadeModel(GL11.GL_FLAT);
		}

		if(item.getItem() == ModItems.gun_deagle) {
			GL11.glShadeModel(GL11.GL_SMOOTH);
			ResourceManager.deagle.renderAll();
			GL11.glShadeModel(GL11.GL_FLAT);
		}

		if(item.getItem() == ModItems.gun_supershotgun) {
			GL11.glShadeModel(GL11.GL_SMOOTH);
			ResourceManager.shotty.renderAll();
			GL11.glShadeModel(GL11.GL_FLAT);
		}

		if(item.getItem() == ModItems.gun_ks23) {
			GL11.glShadeModel(GL11.GL_SMOOTH);
			ResourceManager.ks23.renderAll();
			GL11.glShadeModel(GL11.GL_FLAT);
		}
		
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glPopMatrix();
	}
}
