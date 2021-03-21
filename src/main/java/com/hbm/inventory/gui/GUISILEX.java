package com.hbm.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.handler.FluidTypeHandler.FluidType;
import com.hbm.inventory.container.ContainerSILEX;
import com.hbm.lib.RefStrings;
import com.hbm.packet.AuxButtonPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.tileentity.machine.TileEntitySILEX;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUISILEX extends GuiInfoContainer {

	public static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/processing/gui_silex.png");
	private TileEntitySILEX silex;

	public GUISILEX(InventoryPlayer invPlayer, TileEntitySILEX laser) {
		super(new ContainerSILEX(invPlayer, laser));
		this.silex = laser;

		this.xSize = 176;
		this.ySize = 222;
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);
		
		silex.tank.renderTankInfo(this, mouseX, mouseY, guiLeft + 44, guiTop + 54, 52, 7);
		
		if(silex.current != null) {
			this.drawCustomInfoStat(mouseX, mouseY, guiLeft + 27, guiTop + 72, 16, 52, mouseX, mouseY, new String[] { silex.currentFill + "/" + silex.maxFill + "mB", silex.current.toStack().getDisplayName() });
		}
		
		this.drawCustomInfoStat(mouseX, mouseY, guiLeft + 10, guiTop + 92, 10, 10, mouseX, mouseY, new String[] { "Void contents" });
	}

	protected void mouseClicked(int x, int y, int i) {
		super.mouseClicked(x, y, i);

		if(guiLeft + 10 <= x && guiLeft + 10 + 12 > x && guiTop + 92 < y && guiTop + 92 + 12 >= y) {

			mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
			PacketDispatcher.wrapper.sendToServer(new AuxButtonPacket(silex.xCoord, silex.yCoord, silex.zCoord, 0, 0));
		}
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String name = this.silex.hasCustomInventoryName() ? this.silex.getInventoryName() : I18n.format(this.silex.getInventoryName());

		this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		
		if(silex.tank.getFill() > 0) {
			
			if(silex.tank.getTankType() == FluidType.ACID || silex.fluidConversion.containsKey(silex.tank.getTankType())) {
				drawTexturedModalRect(guiLeft + 43, guiTop + 53, 176, 118, 54, 9);
			} else {
				drawTexturedModalRect(guiLeft + 43, guiTop + 53, 176, 109, 54, 9);
			}
		}

		int p = silex.getProgressScaled(69);
		drawTexturedModalRect(guiLeft + 45, guiTop + 82, 176, 0, p, 43);

		int f = silex.getFillScaled(52);
		drawTexturedModalRect(guiLeft + 26, guiTop + 124 - f, 176, 109 - f, 16, f);

		int i = silex.getFluidScaled(52);
		drawTexturedModalRect(guiLeft + 44, guiTop + 54, 176, silex.tank.getTankType() == FluidType.ACID ? 43 : 50, i, 7);
	}
}
