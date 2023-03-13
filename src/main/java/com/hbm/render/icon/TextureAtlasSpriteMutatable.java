package com.hbm.render.icon;

import java.awt.image.BufferedImage;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.data.AnimationMetadataSection;

@SideOnly(Side.CLIENT)
public class TextureAtlasSpriteMutatable extends TextureAtlasSprite {
	
	private RGBMutator mutator;

	public TextureAtlasSpriteMutatable(String iconName, RGBMutator mutator) {
		super(iconName);
		this.mutator = mutator;
	}

	@Override
	public void loadSprite(BufferedImage[] frames, AnimationMetadataSection animMeta, boolean anisotropicFiltering) {
		
		if(mutator != null) {
			for(int i = 0; i < frames.length; i++) {
				BufferedImage frame = frames[i];
				mutator.mutate(frame, i, frames.length);
			}
		}
		
		super.loadSprite(frames, animMeta, anisotropicFiltering);
	}
}
