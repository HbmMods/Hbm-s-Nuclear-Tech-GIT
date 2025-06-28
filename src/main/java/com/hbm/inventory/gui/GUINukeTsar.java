package com.hbm.inventory.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerNukeTsar;
import com.hbm.items.ModItems;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.bomb.TileEntityNukeTsar;
import com.hbm.util.i18n.I18nUtil;

public class GUINukeTsar extends GuiInfoContainer {
	
	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/weapon/tsarBombaSchematic.png");
	private static ResourceLocation textureMike = new ResourceLocation(RefStrings.MODID + ":textures/gui/weapon/ivyMikeSchematic.png");
	private TileEntityNukeTsar tsar;
	
	public GUINukeTsar(InventoryPlayer invPlayer, TileEntityNukeTsar tedf) {
		super(new ContainerNukeTsar(invPlayer, tedf));
		tsar = tedf;
		
		this.xSize = 256;
		this.ySize = 233;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);
		
		String[] descText = I18nUtil.resolveKeyArray("desc.gui.nukeTsar.desc");
		this.drawCustomInfoStat(mouseX, mouseY, guiLeft - 16, guiTop + 16, 16, 16, guiLeft - 8, guiTop + 16 + 16, descText);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer( int i, int j) {
		String name = this.tsar.hasCustomInventoryName() ? this.tsar.getInventoryName() : I18n.format(this.tsar.getInventoryName());
		
		this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 48, this.ySize - 96 + 2, 4210752);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		
		Minecraft.getMinecraft().getTextureManager().bindTexture(textureMike);

		if(tsar.isFilled()) drawTexturedModalRect(guiLeft + 18, guiTop + 50, 176, 18, 16, 16);
		else if(tsar.isReady()) drawTexturedModalRect(guiLeft + 18, guiTop + 50, 176, 0, 16, 16);

		for(int i = 0; i < 4; i++) {
			if(tsar.getStackInSlot(i) != null && tsar.getStackInSlot(i).getItem() == ModItems.explosive_lenses) switch(i) {
				case 0: drawTexturedModalRect(guiLeft + 24 + 16, guiTop + 20 + 16, 209, 1, 23, 23); break;
				case 2: drawTexturedModalRect(guiLeft + 47 + 16, guiTop + 20 + 16, 232, 1, 23, 23); break;
				case 1: drawTexturedModalRect(guiLeft + 24 + 16, guiTop + 43 + 16, 209, 24, 23, 23); break;
				case 3: drawTexturedModalRect(guiLeft + 47 + 16, guiTop + 43 + 16, 232, 24, 23, 23); break;
			}
		}
			
		if(tsar.getStackInSlot(5) != null && tsar.getStackInSlot(5).getItem() == ModItems.tsar_core)
			drawTexturedModalRect(guiLeft + 75 + 16, guiTop + 25 + 16, 176, 220, 80, 36);
		
		this.drawInfoPanel(guiLeft - 16, guiTop + 16, 16, 16, 2);
	}
}
