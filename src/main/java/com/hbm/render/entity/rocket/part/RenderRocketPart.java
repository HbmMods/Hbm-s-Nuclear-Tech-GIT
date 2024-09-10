package com.hbm.render.entity.rocket.part;

import com.hbm.entity.missile.EntityRideableRocket;

import net.minecraft.client.renderer.texture.TextureManager;

public abstract class RenderRocketPart {
	
	/**
	 * Handles custom rendering, animations, all that fun stuff
	 * Early version, only handles pods at the moment
	 */

	public abstract void render(TextureManager tex, EntityRideableRocket rocket, float interp);
	
}
