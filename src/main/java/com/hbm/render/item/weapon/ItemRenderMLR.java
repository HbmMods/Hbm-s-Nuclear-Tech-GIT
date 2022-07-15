package com.hbm.render.item.weapon;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;
import com.hbm.render.anim.HbmAnimations;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

public class ItemRenderMLR implements IItemRenderer
{

	public ItemRenderMLR() {}

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type)
	{
		switch (type)
		{
		case FIRST_PERSON_MAP:
			return false;
		default:
			return true;
		}
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper)
	{
		return type == ItemRenderType.ENTITY && (helper == ItemRendererHelper.ENTITY_BOBBING || helper == ItemRendererHelper.ENTITY_ROTATION);
	}

	static final String stock = "Stock_stock_stock_thing.000";
	static final String recieverAndMag = "Reciever_Mesh_0";
	static final String sight = "Rear_sights_Cylinder";
	static final String barrelAndGrip = "Barrel_and_grip_Upper_reciever_low_Low_0";
	
	static final float scale1 = 0.25F;
	static final float scale2 = 0.15F;
	static final float scale3 = 0.5F;
	static final float scale4 = 1.2F;
	
	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data)
	{
		GL11.glPushMatrix();
		Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.lunatic_tex);
		EntityPlayer player = Minecraft.getMinecraft().thePlayer;
		GL11.glShadeModel(GL11.GL_SMOOTH);
		
		double[] recoil = HbmAnimations.getRelevantTransformation("RECOIL");
		
		switch(type)
		{
		case ENTITY:// Dropped item
			GL11.glScalef(scale1, scale1, scale1);
			break;
		case EQUIPPED:// In hand from other's POV
			GL11.glRotatef(45F, 0F, 0F, 1F);
			GL11.glTranslatef(1.48F, -0.27F, 0F);
			
			// Animations
			GL11.glTranslated(recoil[0] / 2, recoil[1], recoil[2]);
			GL11.glScalef(scale2, scale2, scale2);
			break;
		case EQUIPPED_FIRST_PERSON:// In hand from POV
			GL11.glRotatef(30F, 0F, 0F, 1F);
			GL11.glTranslatef(3F, -1F, 0F);
			// Makes the recoil look better between sneaking and normal
			byte s = 1;
			if (player.isSneaking())
			{
				GL11.glTranslatef(0F, 0.32F, -0.75F);
				GL11.glRotatef(-5.05F, 0F, 1F, 0F);
				GL11.glRotatef(-5F, 0F, 0F, 1F);
				s = 2;
			}
			
			// Animations
			GL11.glTranslated(recoil[0] / s, recoil[1], recoil[2]);
			GL11.glScalef(scale3, scale3, scale3);
			break;
		case INVENTORY:
			GL11.glTranslatef(5.5F, 7F, 0F);
			GL11.glRotatef(-140F, 0F, 0F, 1F);
			GL11.glScalef(scale4, scale4, scale4);
			break;
		default:
			break;
		}
		
		ResourceManager.lunatic.renderAll();
		GL11.glShadeModel(GL11.GL_FLAT);
		GL11.glPopMatrix();
	}

}
