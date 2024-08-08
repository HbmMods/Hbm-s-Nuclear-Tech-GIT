package com.hbm.render.model;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;

import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.Entity;

public class ModelScutter extends ModelBase {

	
	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		super.render(entity, f, f1, f2, f3, f4, f5);

		GL11.glPushMatrix();
		{

			GL11.glRotatef(0, 0, 0, 0);
			GL11.glTranslatef(0, 0.5F, 0);

			ResourceManager.scutterfish.renderAll(); //remind me to give its individual bits animations

		}
		GL11.glPopMatrix();
	}
}

