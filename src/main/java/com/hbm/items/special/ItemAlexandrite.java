package com.hbm.items.special;

import com.hbm.main.MainRegistry;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.world.EnumSkyBlock;

public class ItemAlexandrite extends Item {

	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister register) {
		if(register instanceof TextureMap) {
			TextureMap map = (TextureMap) register;
			TextureAlexandrite gemTexture = new TextureAlexandrite(this.getIconString());
			map.setTextureEntry(this.getIconString(), gemTexture);
			this.itemIcon = gemTexture;
		} else {
			this.itemIcon = register.registerIcon(this.getIconString());
		}
	}

	@SideOnly(Side.CLIENT)
	public class TextureAlexandrite extends TextureAtlasSprite {

		protected TextureAlexandrite(String texture) {
			super(texture);
		}
		
		private int lastLight = 0;

		@Override
		public void updateAnimation() {
			
			if(Minecraft.getMinecraft().theWorld == null)
				return;
			
			EntityPlayer player = MainRegistry.proxy.me();
			
			if(player == null)
				return;
			
			int x = (int) Math.floor(player.posX);
			int y = (int) Math.floor(player.posY);
			int z = (int) Math.floor(player.posZ);
			
			int light = player.worldObj.getSavedLightValue(EnumSkyBlock.Block, x, y, z);

			if(light != lastLight) {
				lastLight = light;

				this.frameCounter = light;
				TextureUtil.uploadTextureMipmap((int[][]) this.framesTextureData.get(this.frameCounter), this.width, this.height, this.originX, this.originY, false, false);
			}
		}
	}
}
