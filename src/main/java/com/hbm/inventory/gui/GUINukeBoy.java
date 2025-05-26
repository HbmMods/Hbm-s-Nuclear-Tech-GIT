package com.hbm.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerNukeBoy;
import com.hbm.items.ModItems;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.bomb.TileEntityNukeBoy;
import com.hbm.util.i18n.I18nUtil;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUINukeBoy extends GuiInfoContainer {
	
	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/weapon/lilBoySchematic.png");
	private TileEntityNukeBoy testNuke;

	public GUINukeBoy(InventoryPlayer invPlayer, TileEntityNukeBoy tedf) {
		super(new ContainerNukeBoy(invPlayer, tedf));
		testNuke = tedf;
		
		this.xSize = 176;
		this.ySize = 222;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);
		
		String[] descText = I18nUtil.resolveKeyArray("desc.gui.nukeBoy.desc");
		this.drawCustomInfoStat(mouseX, mouseY, guiLeft - 16, guiTop + 16, 16, 16, guiLeft - 8, guiTop + 16 + 16, descText);
	}

	@Override
	protected void drawGuiContainerForegroundLayer( int i, int j) {
		String name = this.testNuke.hasCustomInventoryName() ? this.testNuke.getInventoryName() : I18n.format(this.testNuke.getInventoryName());
		
		this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

		if(testNuke.isReady())
		{
			drawTexturedModalRect(guiLeft + 142, guiTop + 90, 176, 0, 16, 16);
		}

		if(testNuke.getStackInSlot(0) != null && testNuke.getStackInSlot(0).getItem() == ModItems.boy_shielding)
			drawTexturedModalRect(guiLeft + 27, guiTop + 87, 176, 16, 21, 22);
		if(testNuke.getStackInSlot(1) != null && testNuke.getStackInSlot(1).getItem() == ModItems.boy_target)
			drawTexturedModalRect(guiLeft + 27, guiTop + 89, 176, 38, 21, 18);
		if(testNuke.getStackInSlot(2) != null && testNuke.getStackInSlot(2).getItem() == ModItems.boy_bullet)
			drawTexturedModalRect(guiLeft + 74, guiTop + 94, 176, 57, 19, 8);
		if(testNuke.getStackInSlot(3) != null && testNuke.getStackInSlot(3).getItem() == ModItems.boy_propellant)
			drawTexturedModalRect(guiLeft + 92, guiTop + 95, 176, 66, 12, 6);
		if(testNuke.getStackInSlot(4) != null && testNuke.getStackInSlot(4).getItem() == ModItems.boy_igniter)
			drawTexturedModalRect(guiLeft + 107, guiTop + 91, 176, 75, 16, 14);
		
		this.drawInfoPanel(guiLeft - 16, guiTop + 16, 16, 16, 2);
	}

}
