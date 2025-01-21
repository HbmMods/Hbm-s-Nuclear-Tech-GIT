package com.hbm.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerPADipole;
import com.hbm.items.ModItems;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.machine.albion.TileEntityPADipole;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;

public class GUIPADipole extends GuiInfoContainer {

	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/particleaccelerator/gui_dipole.png");
	private TileEntityPADipole dipole;

	public GUIPADipole(InventoryPlayer player, TileEntityPADipole dipole) {
		super(new ContainerPADipole(player, dipole));
		this.dipole = dipole;
		
		this.xSize = 176;
		this.ySize = 204;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);

		dipole.tanks[0].renderTankInfo(this, mouseX, mouseY, guiLeft + 134, guiTop + 36, 16, 52);
		dipole.tanks[1].renderTankInfo(this, mouseX, mouseY, guiLeft + 152, guiTop + 36, 16, 52);
		this.drawElectricityInfo(this, mouseX, mouseY, guiLeft + 8, guiTop + 18, 16, 52, dipole.power, dipole.getMaxPower());
	}

	@Override
	protected void drawGuiContainerForegroundLayer( int i, int j) {
		
		String name = this.dipole.hasCustomInventoryName() ? this.dipole.getInventoryName() : I18n.format(this.dipole.getInventoryName());
		this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2 - 9, 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);

		this.fontRendererObj.drawString(EnumChatFormatting.AQUA + "/123K", 136, 22, 4210752);
		int heat = (int) Math.ceil(dipole.temperature);
		String label = (heat > 123 ? EnumChatFormatting.RED : EnumChatFormatting.AQUA) + "" + heat + "K";
		this.fontRendererObj.drawString(label, 166 - this.fontRendererObj.getStringWidth(label), 12, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float interp, int x, int y) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		
		int j = (int) (dipole.power * 52 / dipole.getMaxPower());
		drawTexturedModalRect(guiLeft + 8, guiTop + 70 - j, 184, 52 - j, 16, j);

		int heat = (int) Math.ceil(dipole.temperature);
		if(heat <= 123) drawTexturedModalRect(guiLeft + 93, guiTop + 64, 176, 8, 8, 8);
		if(dipole.slots[1] != null && dipole.slots[1].getItem() == ModItems.pa_coil) drawTexturedModalRect(guiLeft + 103, guiTop + 64, 176, 8, 8, 8);

		dipole.tanks[0].renderTank(guiLeft + 134, guiTop + 88, this.zLevel, 16, 52);
		dipole.tanks[1].renderTank(guiLeft + 152, guiTop + 88, this.zLevel, 16, 52);
	}
}
