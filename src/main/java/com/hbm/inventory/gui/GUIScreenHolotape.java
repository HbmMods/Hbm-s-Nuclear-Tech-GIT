package com.hbm.inventory.gui;

import java.util.List;

import org.lwjgl.opengl.GL11;

import com.hbm.items.ModItems;
import com.hbm.items.special.ItemHolotapeImage.EnumHoloImage;
import com.hbm.util.EnumUtil;
import com.hbm.util.i18n.I18nUtil;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class GUIScreenHolotape extends GuiScreen {
	
	EnumHoloImage holo;

	@Override
	public void initGui() {
		mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("hbm:block.bobble"), 1.0F));
		
		ItemStack stack = Minecraft.getMinecraft().thePlayer.getHeldItem();
		
		if(stack != null && stack.getItem() == ModItems.holotape_image) {
			this.holo = EnumUtil.grabEnumSafely(EnumHoloImage.class, stack.getItemDamage());
		} else {
			this.mc.thePlayer.closeScreen();
		}
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		
		if(this.holo == null)
			return;
		
		this.drawDefaultBackground();
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_ALPHA_TEST);
		OpenGlHelper.glBlendFunc(770, 771, 1, 0);
		GL11.glDisable(GL11.GL_TEXTURE_2D);

		double sizeX = 300;
		double sizeY = 150;
		double left = (this.width - sizeX) / 2;
		double top = (this.height - sizeY) / 2;
		
		Tessellator tess = Tessellator.instance;
		tess.startDrawingQuads();
		tess.setColorRGBA_F(0F, 0.2F, 0F, 0.8F);
		tess.addVertex(left + sizeX, top, this.zLevel);
		tess.addVertex(left, top, this.zLevel);
		tess.addVertex(left, top + sizeY, this.zLevel);
		tess.addVertex(left + sizeX, top + sizeY, this.zLevel);
		tess.draw();

		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		GL11.glDisable(GL11.GL_BLEND);
		
		int nextLevel = (int)top + 30;
		
		if(this.holo.getText() != null) {
			
			List<String> lines = I18nUtil.autoBreak(this.fontRendererObj, this.holo.getText(), 275);
			
			for(String text : lines) {
				this.fontRendererObj.drawStringWithShadow(text, (int)(left + sizeX / 2 - this.fontRendererObj.getStringWidth(text) / 2), nextLevel, 0x009900);
				nextLevel += 10;
			}

			nextLevel += 10;
		}
		
		GL11.glEnable(GL11.GL_LIGHTING);
	}

	@Override
	protected void keyTyped(char c, int key) {
		if(key == 1 || key == this.mc.gameSettings.keyBindInventory.getKeyCode()) {
			this.mc.thePlayer.closeScreen();
		}
	}
	
	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}
}
