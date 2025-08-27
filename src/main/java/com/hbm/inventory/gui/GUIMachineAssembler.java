package com.hbm.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerMachineAssembler;
import com.hbm.items.ModItems;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.machine.TileEntityMachineAssembler;
import com.hbm.util.i18n.I18nUtil;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUIMachineAssembler extends GuiInfoContainer {

	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/gui_assembler.png");
	private TileEntityMachineAssembler assembler;
	
	public GUIMachineAssembler(InventoryPlayer invPlayer, TileEntityMachineAssembler tedf) {
		super(new ContainerMachineAssembler(invPlayer, tedf));
		assembler = tedf;
		
		this.xSize = 176;
		this.ySize = 222;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);

		this.drawElectricityInfo(this, mouseX, mouseY, guiLeft + 116, guiTop + 70 - 52, 16, 52, assembler.power, assembler.getMaxPower());
		
		if(assembler.getStackInSlot(4) == null || assembler.getStackInSlot(4).getItem()!= ModItems.assembly_template) {

			String[] warnText = I18nUtil.resolveKeyArray("desc.gui.assembler.warning");
			this.drawCustomInfoStat(mouseX, mouseY, guiLeft - 16, guiTop + 36, 16, 16, guiLeft - 8, guiTop + 36 + 16, warnText);
		}
		
		String[] templateText = I18nUtil.resolveKeyArray("desc.gui.template");
		this.drawCustomInfoStat(mouseX, mouseY, guiLeft - 16, guiTop + 16, 16, 16, guiLeft - 8, guiTop + 16 + 16, templateText);
		
		this.drawCustomInfoStat(mouseX, mouseY, guiLeft + 141, guiTop + 40, 8, 8, guiLeft + 225, guiTop + 40 + 16 + 8, this.getUpgradeInfo(assembler));
	}

	@Override
	protected void drawGuiContainerForegroundLayer( int i, int j) {
		String name = this.assembler.hasCustomInventoryName() ? this.assembler.getInventoryName() : I18n.format(this.assembler.getInventoryName());
		
		this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		
		int i = (int) (assembler.power * 52 / assembler.getMaxPower());
		drawTexturedModalRect(guiLeft + 116, guiTop + 70 - i, 176, 52 - i, 16, i);

		if(assembler.isProgressing) {
			int j = assembler.progress[0] * 83 / assembler.maxProgress[0];
			drawTexturedModalRect(guiLeft + 45, guiTop + 82, 2, 222, j, 32);
		}
		
		if(assembler.getStackInSlot(4) == null || assembler.getStackInSlot(4).getItem()!= ModItems.assembly_template) {
			this.drawInfoPanel(guiLeft - 16, guiTop + 36, 16, 16, 6);
		}
		
		this.drawInfoPanel(guiLeft - 16, guiTop + 16, 16, 16, 11);
		this.drawInfoPanel(guiLeft + 141, guiTop + 40, 8, 8, 8);
	}
}
