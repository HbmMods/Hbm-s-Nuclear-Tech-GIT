package com.hbm.render.model;

import com.hbm.main.ResourceManager;
import com.hbm.render.loader.ModelRendererObj;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;

public class ModelArmorTailPeep extends ModelArmorBase {

	ModelRendererObj tail;
	
	public ModelArmorTailPeep() {
		super(0);
		tail = new ModelRendererObj(ResourceManager.armor_tail, "FaggyAssFuckingTailThing");
	}

	@Override
	public void render(Entity entity, float par2, float par3, float par4, float par5, float par6, float par7) {
		setRotationAngles(par2, par3, par4, par5, par6, par7, entity);
		body.copyTo(tail);
		
		Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.tail_peep);
		tail.render(par7);
	}
}
