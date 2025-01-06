package com.hbm.inventory.gui;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import com.hbm.lib.RefStrings;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class GUIScreenPreview extends GuiScreen {

	protected static final ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/nei/gui_nei.png");
	protected ItemStack preview;
	protected int zoom = 1;

	public GUIScreenPreview(ItemStack stack) {
		this.preview = stack;
	}

	public void drawScreen(int mouseX, int mouseY, float f) {
		
		if(this.mc.theWorld != null) {
			this.drawRect(0, 0, this.width, this.height, 0xFFC6C6C6);
		} else {
			this.drawBackground(0);
		}
		
		if(!Mouse.isButtonDown(0) && !Mouse.isButtonDown(1) && Mouse.next()) {
			int scroll = Mouse.getEventDWheel();
			
			if(scroll < 0 && this.zoom > 1) this.zoom--;
			if(scroll > 0 && this.zoom < 15) this.zoom++;
		}
		
		this.drawGuiContainerBackgroundLayer(f, mouseX, mouseY);
		GL11.glDisable(GL11.GL_LIGHTING);
		this.drawGuiContainerForegroundLayer(mouseX, mouseY);
		GL11.glEnable(GL11.GL_LIGHTING);
	}

	protected void drawGuiContainerBackgroundLayer(float f, int mouseX, int mouseY) {
		GL11.glPushMatrix();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(texture);
		ScaledResolution res = new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);
		GL11.glScaled(zoom, zoom, zoom);
		this.drawTexturedModalRect(res.getScaledWidth_double() / 2D / zoom - 9D, res.getScaledHeight_double() / 2D / zoom - 9D, 5, 87, 18, 18);
		GL11.glPopMatrix();

		String nameString = Item.itemRegistry.getNameForObject(preview.getItem()) + ", " + preview.getItemDamage();
		String zoomString = "Zoom: " + zoom;
		String scaleString = "Windows Scale: " + res.getScaleFactor();

		GL11.glPushMatrix();
		GL11.glScaled(0.5, 0.5, 1);
		this.fontRendererObj.drawString(zoomString, this.width * 2 - this.fontRendererObj.getStringWidth(zoomString) - 2, this.height * 2 - 35, 0xff0000);
		this.fontRendererObj.drawString(scaleString, this.width * 2 - this.fontRendererObj.getStringWidth(scaleString) - 2, this.height * 2 - 25, 0xff0000);
		this.fontRendererObj.drawString(nameString, this.width * 2 - this.fontRendererObj.getStringWidth(nameString) - 2, this.height * 2 - 15, 0xff0000);
		GL11.glPopMatrix();
	}

	public void drawTexturedModalRect(double x, double y, int sourceX, int sourceY, int sizeX, int sizeY) {
		double f = 0.00390625D;
		double f1 = 0.00390625D;
		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV((double) (x + 0), (double) (y + sizeY), (double) this.zLevel, (double) ((float) (sourceX + 0) * f), (double) ((float) (sourceY + sizeY) * f1));
		tessellator.addVertexWithUV((double) (x + sizeX), (double) (y + sizeY), (double) this.zLevel, (double) ((float) (sourceX + sizeX) * f), (double) ((float) (sourceY + sizeY) * f1));
		tessellator.addVertexWithUV((double) (x + sizeX), (double) (y + 0), (double) this.zLevel, (double) ((float) (sourceX + sizeX) * f), (double) ((float) (sourceY + 0) * f1));
		tessellator.addVertexWithUV((double) (x + 0), (double) (y + 0), (double) this.zLevel, (double) ((float) (sourceX + 0) * f), (double) ((float) (sourceY + 0) * f1));
		tessellator.draw();
	}

	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		
		if(preview == null) return;
		
		GL11.glPushMatrix();
		RenderHelper.enableGUIStandardItemLighting();
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240F, 240F);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		
		GL11.glScaled(zoom, zoom, zoom);
		
		ScaledResolution res = new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);
		GL11.glTranslated(res.getScaledWidth_double() / 2D / zoom, res.getScaledHeight_double() / 2D / zoom, -200);

		this.zLevel = 200.0F;
		itemRender.zLevel = 200.0F;

		GL11.glEnable(GL11.GL_DEPTH_TEST);
		itemRender.renderItemAndEffectIntoGUI(this.fontRendererObj, this.mc.getTextureManager(), preview, -8, -8);
		itemRender.renderItemOverlayIntoGUI(this.fontRendererObj, this.mc.getTextureManager(), preview, -8, -8, null);

		itemRender.zLevel = 0.0F;
		this.zLevel = 0.0F;
		
		GL11.glPopMatrix();
	}

	@Override
	protected void keyTyped(char c, int key) {
		if(key == 1 || key == this.mc.gameSettings.keyBindInventory.getKeyCode()) {
			this.mc.thePlayer.closeScreen();
		}
	}
}
