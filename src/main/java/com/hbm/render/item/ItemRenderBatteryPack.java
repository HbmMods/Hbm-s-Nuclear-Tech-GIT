package com.hbm.render.item;

import org.lwjgl.opengl.GL11;

import com.hbm.items.machine.ItemBatteryPack.EnumBatteryPack;
import com.hbm.main.ResourceManager;
import com.hbm.util.EnumUtil;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;

public class ItemRenderBatteryPack extends ItemRenderBase {

	@Override
	public void renderInventory() {
		GL11.glTranslated(0, -3, 0);
		GL11.glScaled(5, 5, 5);
	}

	@Override
	public void renderCommonWithStack(ItemStack item) {
		EnumBatteryPack pack = EnumUtil.grabEnumSafely(EnumBatteryPack.class, item.getItemDamage());
		GL11.glShadeModel(GL11.GL_SMOOTH);
		Minecraft.getMinecraft().getTextureManager().bindTexture(pack.texture);
		ResourceManager.battery_socket.renderPart("Battery");
		GL11.glShadeModel(GL11.GL_FLAT);
	}
}
