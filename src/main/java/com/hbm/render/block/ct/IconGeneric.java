package com.hbm.render.block.ct;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;

@SideOnly(Side.CLIENT)
public class IconGeneric extends TextureAtlasSprite {

	public IconGeneric(String name) {
		super(name);
	}
}
