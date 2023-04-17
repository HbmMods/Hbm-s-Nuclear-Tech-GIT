package com.hbm.particle.psys.engine;

import java.util.List;
import java.util.Random;

import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class ParticleEngine {

	public static ParticleEngine INSTANCE;

	public World world;
	public TextureManager texman;
	public FXLayer[] layers;
	protected Random rand = new Random();
	
	public ParticleEngine(World world, TextureManager texman) {
		this.world = world;
		this.texman = texman;
		setupLayers();
	}
	
	private void setupLayers() {
		layers = new FXLayer[] {
				
		};
	}
	
	public void updateParticles() {
		for(FXLayer layer : layers) layer.updateLayer();
	}
	
	public void renderParticles(float interp) {
		for(FXLayer layer : layers) layer.renderLayer(interp);
	}
	
	public static class FXLayer {
		
		protected ResourceLocation batchTexture;
		protected List<PSysFX> particles;
		
		public FXLayer() { }
		
		public FXLayer(ResourceLocation batchTexture) {
			this.batchTexture = batchTexture;
		}
		
		protected void updateLayer() {
			
		}
		
		protected void renderLayer(float interp) {
			
		}
	}
}
