package com.hbm.render.icon;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;

import com.hbm.main.MainRegistry;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.data.AnimationMetadataSection;
import net.minecraft.client.resources.data.TextureMetadataSection;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

/**
 * TODO: using this with a missing texture for some reason crashes the game
 * TexMan's mip map levels seem to be -1 for some reason, run debugger
 * 
 * @author hbm
 */
@SideOnly(Side.CLIENT)
public class TextureAtlasSpriteMutatable extends TextureAtlasSprite {
	
	private RGBMutator mutator;
	public String basePath = "textures/items";
	private int mipmap = 0;
	private int anisotropic = 0;

	public TextureAtlasSpriteMutatable(String iconName, RGBMutator mutator) {
		super(iconName);
		this.mutator = mutator;
	}
	
	public TextureAtlasSpriteMutatable setBlockAtlas() {
		this.mipmap = Minecraft.getMinecraft().gameSettings.mipmapLevels;
		this.anisotropic = Minecraft.getMinecraft().gameSettings.anisotropicFiltering;
		this.basePath = "textures/blocks";
		return this;
	}

	@Override
	public void loadSprite(BufferedImage[] frames, AnimationMetadataSection animMeta, boolean anisotropicFiltering) {
		
		if(mutator != null) {
			for(int i = 0; i < frames.length; i++) {
				BufferedImage frame = frames[i];
				
				if(frame != null) mutator.mutate(frame, i, frames.length);
			}
		}
		
		super.loadSprite(frames, animMeta, anisotropicFiltering);
	}

	@Override
	public boolean hasCustomLoader(IResourceManager manager, ResourceLocation location) {
		return true; //YES!
	}

	@Override
	public boolean load(IResourceManager man, ResourceLocation resourcelocation) {

		String pathName = resourcelocation.getResourcePath();
		String tunkatedPath = pathName.substring(0, pathName.indexOf('-')); //fuck regex
		//so basically we remove the dash and everything trailing it (see ItemAutogen.java), this allows us to have unique icon names for what is actually the same icon file
		resourcelocation = new ResourceLocation(resourcelocation.getResourceDomain(), tunkatedPath);
		ResourceLocation resourcelocation1 = this.completeResourceLocation(resourcelocation, 0);
		
		//garbage vanilla code, copy pasted because there's no proper hooks for this
		try {

			IResource iresource = man.getResource(resourcelocation1);
			BufferedImage[] abufferedimage = new BufferedImage[1 + mipmap];
			abufferedimage[0] = ImageIO.read(iresource.getInputStream());
			TextureMetadataSection texturemetadatasection = (TextureMetadataSection) iresource.getMetadata("texture");

			if(texturemetadatasection != null) {
				List list = texturemetadatasection.getListMipmaps();
				int l;

				if(!list.isEmpty()) {
					int k = abufferedimage[0].getWidth();
					l = abufferedimage[0].getHeight();

					if(MathHelper.roundUpToPowerOfTwo(k) != k || MathHelper.roundUpToPowerOfTwo(l) != l) {
						throw new RuntimeException("Unable to load extra miplevels, source-texture is not power of two");
					}
				}

				Iterator iterator3 = list.iterator();

				while(iterator3.hasNext()) {
					l = ((Integer) iterator3.next()).intValue();

					if(l > 0 && l < abufferedimage.length - 1 && abufferedimage[l] == null) {
						ResourceLocation resourcelocation2 = this.completeResourceLocation(resourcelocation, l);

						try {
							abufferedimage[l] = ImageIO.read(man.getResource(resourcelocation2).getInputStream());
						} catch(IOException ioexception) {
							MainRegistry.logger.error("Unable to load miplevel {} from: {}", new Object[] { Integer.valueOf(l), resourcelocation2, ioexception });
						}
					}
				}
			}

			AnimationMetadataSection animationmetadatasection = (AnimationMetadataSection) iresource.getMetadata("animation");
			loadSprite(abufferedimage, animationmetadatasection, (float) anisotropic > 1.0F);
		} catch(RuntimeException runtimeexception) {
			cpw.mods.fml.client.FMLClientHandler.instance().trackBrokenTexture(resourcelocation1, runtimeexception.getMessage());
			return true; //return TRUE to prevent stitching non-existent texture, vanilla loading will deal with that!
		} catch(IOException ioexception1) {
			cpw.mods.fml.client.FMLClientHandler.instance().trackMissingTexture(resourcelocation1);
			return true;
		}

		return false; //FALSE! prevents vanilla loading (we just did that ourselves)
	}
	
	//whatever the fuck this is
	private ResourceLocation completeResourceLocation(ResourceLocation loc, int mipmap) {
		return mipmap == 0 ? new ResourceLocation(loc.getResourceDomain(), String.format("%s/%s%s", new Object[] { this.basePath, loc.getResourcePath(), ".png" }))
				: new ResourceLocation(loc.getResourceDomain(), String.format("%s/mipmaps/%s.%d%s", new Object[] { this.basePath, loc.getResourcePath(), Integer.valueOf(mipmap), ".png" }));
	}
}
