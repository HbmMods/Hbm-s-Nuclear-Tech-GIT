package com.hbm.render.item.weapon;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;
import com.hbm.render.anim.HbmAnimations;
import com.hbm.render.item.ItemRenderBase;

import net.minecraft.item.ItemStack;

public class ItemRenderG36 extends ItemRenderBase
{
	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data)
	{
//		super.renderItem(type, item, data);
		double[] recoil = HbmAnimations.getRelevantTransformation("recoil");
		switch (type)
		{
		case ENTITY:
			GL11.glScalef(0.3F, 0.3F, 0.3F);
			break;
		case EQUIPPED:
			GL11.glScalef(0.3f, 0.3f, 0.3f);
			GL11.glRotatef(90f, 0, 1, 0);
			break;
		case EQUIPPED_FIRST_PERSON:
			GL11.glScalef(0.5f, 0.5f, 0.5f);
			break;
		case INVENTORY:
			GL11.glScalef(1.75f, 1.75f, 1.75f);
			GL11.glRotatef(-140, 0, 0, 1);
			GL11.glTranslatef(-7f, -2f, 0);
			break;
		default:
			break;
		}
		ResourceManager.g36.renderAll();
	}
}
