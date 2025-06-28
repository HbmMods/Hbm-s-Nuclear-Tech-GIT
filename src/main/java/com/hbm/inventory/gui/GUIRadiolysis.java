package com.hbm.inventory.gui;

import java.util.List;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerRadiolysis;
import com.hbm.items.machine.ItemRTGPellet;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.machine.TileEntityMachineRadiolysis;
import com.hbm.util.i18n.I18nUtil;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUIRadiolysis extends GuiInfoContainer {
	
	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/gui_radiolysis.png");
	private TileEntityMachineRadiolysis radiolysis;

	public GUIRadiolysis(InventoryPlayer invPlayer, TileEntityMachineRadiolysis tedf) {
		super(new ContainerRadiolysis(invPlayer, tedf));
		radiolysis = tedf;
		
		this.xSize = 230;
		this.ySize = 166;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);
		
		radiolysis.tanks[0].renderTankInfo(this, mouseX, mouseY, guiLeft + 61, guiTop + 17, 8, 52);
		radiolysis.tanks[1].renderTankInfo(this, mouseX, mouseY, guiLeft + 87, guiTop + 17, 12, 16);
		radiolysis.tanks[2].renderTankInfo(this, mouseX, mouseY, guiLeft + 87, guiTop + 53, 12, 16);
		
		this.drawElectricityInfo(this, mouseX, mouseY, guiLeft + 8, guiTop + 17, 16, 34, radiolysis.power, radiolysis.maxPower);
		
		String[] descText = I18nUtil.resolveKeyArray("desc.gui.radiolysis.desc");
		this.drawCustomInfoStat(mouseX, mouseY, guiLeft - 16, guiTop + 16, 16, 16, guiLeft - 8, guiTop + 16 + 16, descText);
		
		String[] heatText = I18nUtil.resolveKeyArray("desc.gui.rtg.heat", radiolysis.heat);
		this.drawCustomInfoStat(mouseX, mouseY, guiLeft - 16, guiTop + 16 + 18, 16, 16, guiLeft - 8, guiTop + 16 + 18 + 16, heatText);
		
		List<ItemRTGPellet> pellets = ItemRTGPellet.pelletList;
		String[] pelletText = new String[pellets.size() + 1];
		pelletText[0] = I18nUtil.resolveKey("desc.gui.rtg.pellets");
		
		for(int i = 0; i < pellets.size(); i++) {
			ItemRTGPellet pellet = pellets.get(i);
			pelletText[i + 1] = I18nUtil.resolveKey("desc.gui.rtg.pelletPower", I18nUtil.resolveKey(pellet.getUnlocalizedName() + ".name"), pellet.getHeat() * 10);
		}
		
		this.drawCustomInfoStat(mouseX, mouseY, guiLeft - 16, guiTop + 16 + 36, 16, 16, guiLeft - 8, guiTop + 16 + 36 + 16, pelletText);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		
		String name = this.radiolysis.hasCustomInventoryName() ? this.radiolysis.getInventoryName() : I18n.format(this.radiolysis.getInventoryName());
		
		this.fontRendererObj.drawString(name, 88 - this.fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		
		int i = (int)(radiolysis.getPower() * 34 / radiolysis.getMaxPower());
		drawTexturedModalRect(guiLeft + 8, guiTop + 51 - i, 240, 34 - i, 16, i);
		
		radiolysis.tanks[0].renderTank(guiLeft + 61, guiTop + 69, this.zLevel, 8, 52);
		
		for(byte j = 0; j < 2; j++) {
			radiolysis.tanks[j + 1].renderTank(guiLeft + 87, guiTop + 33 + j * 36, this.zLevel, 12, 16);
		}
		
		this.drawInfoPanel(guiLeft - 16, guiTop + 16, 16, 16, 10);
		this.drawInfoPanel(guiLeft - 16, guiTop + 16 + 18, 16, 16, 2);
		this.drawInfoPanel(guiLeft - 16, guiTop + 16 + 36, 16, 16, 3);
	}
}
