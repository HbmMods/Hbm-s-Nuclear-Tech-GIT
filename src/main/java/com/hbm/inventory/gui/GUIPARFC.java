package com.hbm.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerPARFC;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.machine.albion.TileEntityPARFC;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;

public class GUIPARFC extends GuiInfoContainer {

	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/particleaccelerator/gui_rfc.png");
	private TileEntityPARFC quadrupole;

	public GUIPARFC(InventoryPlayer player, TileEntityPARFC slopper) {
		super(new ContainerPARFC(player, slopper));
		this.quadrupole = slopper;
		
		this.xSize = 176;
		this.ySize = 204;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);

		quadrupole.tanks[0].renderTankInfo(this, mouseX, mouseY, guiLeft + 89, guiTop + 36, 16, 52);
		quadrupole.tanks[1].renderTankInfo(this, mouseX, mouseY, guiLeft + 107, guiTop + 36, 16, 52);
		this.drawElectricityInfo(this, mouseX, mouseY, guiLeft + 53, guiTop + 18, 16, 52, quadrupole.power, quadrupole.getMaxPower());
	}

	@Override
	protected void drawGuiContainerForegroundLayer( int i, int j) {
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);

		this.fontRendererObj.drawString(EnumChatFormatting.AQUA + "/123K", 91, 22, 4210752);
		int heat = (int) Math.ceil(quadrupole.temperature);
		String label = (heat > 123 ? EnumChatFormatting.RED : EnumChatFormatting.AQUA) + "" + heat + "K";
		this.fontRendererObj.drawString(label, 121 - this.fontRendererObj.getStringWidth(label), 12, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float interp, int x, int y) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		
		int j = (int) (quadrupole.power * 52 / quadrupole.getMaxPower());
		drawTexturedModalRect(guiLeft + 53, guiTop + 70 - j, 184, 52 - j, 16, j);
		
		quadrupole.tanks[0].renderTank(guiLeft + 89, guiTop + 88, this.zLevel, 16, 52);
		quadrupole.tanks[1].renderTank(guiLeft + 107, guiTop + 88, this.zLevel, 16, 52);
	}
}
