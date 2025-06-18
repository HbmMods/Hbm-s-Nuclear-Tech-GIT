package com.hbm.inventory.gui;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerCrucible;
import com.hbm.inventory.material.Mats;
import com.hbm.inventory.material.Mats.MaterialStack;
import com.hbm.inventory.material.NTMMaterial.SmeltingBehavior;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.machine.TileEntityCrucible;
import com.hbm.util.i18n.I18nUtil;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;

public class GUICrucible extends GuiInfoContainer {
	
	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/processing/gui_crucible.png");
	private TileEntityCrucible crucible;

	public GUICrucible(InventoryPlayer invPlayer, TileEntityCrucible tedf) {
		super(new ContainerCrucible(invPlayer, tedf));
		crucible = tedf;
		
		this.xSize = 176;
		this.ySize = 214;
	}
	
	@Override
	public void drawScreen(int x, int y, float interp) {
		super.drawScreen(x, y, interp);

		drawStackInfo(crucible.wasteStack, x, y, 16, 17);
		drawStackInfo(crucible.recipeStack, x, y, 61, 17);
		
		this.drawCustomInfoStat(x, y, guiLeft + 125, guiTop + 81, 34, 7, x, y, new String[] { String.format(Locale.US, "%,d", crucible.progress) + " / " + String.format(Locale.US, "%,d", crucible.processTime) + "TU" });
		this.drawCustomInfoStat(x, y, guiLeft + 125, guiTop + 90, 34, 7, x, y, new String[] { String.format(Locale.US, "%,d", crucible.heat) + " / " + String.format(Locale.US, "%,d", crucible.maxHeat) + "TU" });
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String name = this.crucible.hasCustomInventoryName() ? this.crucible.getInventoryName() : I18n.format(this.crucible.getInventoryName());
		
		this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, 6, 0xffffff);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		
		int pGauge = crucible.progress * 33 / crucible.processTime;
		if(pGauge > 0) drawTexturedModalRect(guiLeft + 126, guiTop + 82, 176, 0, pGauge, 5);
		int hGauge = crucible.heat * 33 / crucible.maxHeat;
		if(hGauge > 0) drawTexturedModalRect(guiLeft + 126, guiTop + 91, 176, 5, hGauge, 5);

		if(!crucible.recipeStack.isEmpty()) drawStack(crucible.recipeStack, crucible.recipeZCapacity, 62, 97);
		if(!crucible.wasteStack.isEmpty()) drawStack(crucible.wasteStack, crucible.wasteZCapacity, 17, 97);
	}
	
	protected void drawStackInfo(List<MaterialStack> stack, int mouseX, int mouseY, int x, int y) {
		
		List<String> list = new ArrayList();
		
		if(stack.isEmpty())
			list.add(EnumChatFormatting.RED + "Empty");
		
		for(MaterialStack sta : stack) {
			list.add(EnumChatFormatting.YELLOW + I18nUtil.resolveKey(sta.material.getUnlocalizedName()) + ": " + Mats.formatAmount(sta.amount, Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)));
		}
		
		this.drawCustomInfoStat(mouseX, mouseY, guiLeft + x, guiTop + y, 36, 81, mouseX, mouseY, list);
	}
	
	protected void drawStack(List<MaterialStack> stack, int capacity, int x, int y) {
		
		if(stack.isEmpty()) return;
		
		int lastHeight = 0;
		int lastQuant = 0;
		
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
		
		for(MaterialStack sta : stack) {
			
			int targetHeight = (lastQuant + sta.amount) * 79 / capacity;
			
			if(lastHeight == targetHeight) continue; //skip draw calls that would be 0 pixels high
			
			int offset = sta.material.smeltable == SmeltingBehavior.ADDITIVE ? 34 : 0; //additives use a differnt texture
			
			int hex = sta.material.moltenColor;
			//hex = 0xC18336;
			Color color = new Color(hex);
			GL11.glColor3f(color.getRed() / 255F, color.getGreen() / 255F, color.getBlue() / 255F);
			drawTexturedModalRect(guiLeft + x, guiTop + y - targetHeight, 176 + offset, 89 - targetHeight, 34, targetHeight - lastHeight);
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glColor4f(1F, 1F, 1F, 0.3F);
			drawTexturedModalRect(guiLeft + x, guiTop + y - targetHeight, 176 + offset, 89 - targetHeight, 34, targetHeight - lastHeight);
			GL11.glDisable(GL11.GL_BLEND);
			
			lastQuant += sta.amount;
			lastHeight = targetHeight;
		}

		OpenGlHelper.glBlendFunc(770, 771, 1, 0);
		GL11.glColor3f(255, 255, 255);
	}
}
