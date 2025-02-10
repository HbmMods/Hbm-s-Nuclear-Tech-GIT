package com.hbm.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerPADetector;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.machine.albion.TileEntityPADetector;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;

public class GUIPADetector extends GuiInfoContainer {

	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/particleaccelerator/gui_detector.png");
	private TileEntityPADetector source;

	public GUIPADetector(InventoryPlayer player, TileEntityPADetector source) {
		super(new ContainerPADetector(player, source));
		this.source = source;
		
		this.xSize = 176;
		this.ySize = 204;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);

		source.tanks[0].renderTankInfo(this, mouseX, mouseY, guiLeft + 134, guiTop + 36, 16, 52);
		source.tanks[1].renderTankInfo(this, mouseX, mouseY, guiLeft + 152, guiTop + 36, 16, 52);
		this.drawElectricityInfo(this, mouseX, mouseY, guiLeft + 8, guiTop + 18, 16, 52, source.power, source.getMaxPower());
	}

	@Override
	protected void drawGuiContainerForegroundLayer( int i, int j) {
		
		String name = this.source.hasCustomInventoryName() ? this.source.getInventoryName() : I18n.format(this.source.getInventoryName());
		this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2 - 9, 4, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);

		this.fontRendererObj.drawString(EnumChatFormatting.AQUA + "/123K", 136, 22, 4210752);
		int heat = (int) Math.ceil(source.temperature);
		String label = (heat > 123 ? EnumChatFormatting.RED : EnumChatFormatting.AQUA) + "" + heat + "K";
		this.fontRendererObj.drawString(label, 166 - this.fontRendererObj.getStringWidth(label), 12, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float interp, int x, int y) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		
		int j = (int) (source.power * 52 / source.getMaxPower());
		drawTexturedModalRect(guiLeft + 8, guiTop + 70 - j, 184, 52 - j, 16, j);

		int heat = (int) Math.ceil(source.temperature);
		if(heat <= 123) drawTexturedModalRect(guiLeft + 44, guiTop + 18, 176, 8, 8, 8);
		if(source.power >= source.usage) drawTexturedModalRect(guiLeft + 44, guiTop + 43, 176, 8, 8, 8);

		source.tanks[0].renderTank(guiLeft + 134, guiTop + 88, this.zLevel, 16, 52);
		source.tanks[1].renderTank(guiLeft + 152, guiTop + 88, this.zLevel, 16, 52);
	}
}
