package com.hbm.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerReactorZirnox;
import com.hbm.lib.RefStrings;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.toserver.NBTControlPacket;
import com.hbm.tileentity.machine.TileEntityReactorZirnox;
import com.hbm.util.I18nUtil;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

public class GUIReactorZirnox extends GuiInfoContainer {

	// fuck you
	private static final ResourceLocation texture = new ResourceLocation(RefStrings.MODID, "textures/gui/reactors/gui_zirnox.png");
	private TileEntityReactorZirnox zirnox;

	public GUIReactorZirnox(InventoryPlayer invPlayer, TileEntityReactorZirnox tile) {
		super(new ContainerReactorZirnox(invPlayer, tile));
		zirnox = tile;

		this.xSize = 203;
		this.ySize = 256;
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);

		zirnox.steam.renderTankInfo(this, mouseX, mouseY, guiLeft + 160, guiTop + 108, 18, 12);
		zirnox.carbonDioxide.renderTankInfo(this, mouseX, mouseY, guiLeft + 142, guiTop + 108, 18, 12);
		zirnox.water.renderTankInfo(this, mouseX, mouseY, guiLeft + 178, guiTop + 108, 18, 12);
		this.drawCustomInfo(this, mouseX, mouseY, guiLeft + 160, guiTop + 33, 18, 17, new String[] { "Temperature:", "   " + Math.round((zirnox.heat) * 0.00001 * 780 + 20) + "°C" });
		this.drawCustomInfo(this, mouseX, mouseY, guiLeft + 178, guiTop + 33, 18, 17, new String[] { "Pressure:", "   " + Math.round((zirnox.pressure) * 0.00001 * 30) + " bar" });
		
		String[] coolantText = I18nUtil.resolveKeyArray("desc.gui.zirnox.coolant");
		this.drawCustomInfoStat(mouseX, mouseY, guiLeft - 16, guiTop + 36, 16, 16, guiLeft - 8, guiTop + 36 + 16, coolantText);
		
		String[] pressureText = I18nUtil.resolveKeyArray("desc.gui.zirnox.pressure");
		this.drawCustomInfoStat(mouseX, mouseY, guiLeft - 16, guiTop + 36 + 16, 16, 16, guiLeft - 8, guiTop + 36 + 16 + 16, pressureText);

		if(zirnox.water.getFill() <= 0) {
			String[] warning1 = I18nUtil.resolveKeyArray("desc.gui.zirnox.warning1");
			this.drawCustomInfoStat(mouseX, mouseY, guiLeft - 16, guiTop + 36 + 32, 16, 16, guiLeft - 8, guiTop + 36 + 32 + 16, warning1);
		}

		if(zirnox.carbonDioxide.getFill() < 4000) {
			String[] warning2 = I18nUtil.resolveKeyArray("desc.gui.zirnox.warning2");
			this.drawCustomInfoStat(mouseX, mouseY, guiLeft - 16, guiTop + 36 + 32 + 16, 16, 16, guiLeft - 8, guiTop + 36 + 32 + 16 + 16, warning2);
		}

	}

	protected void mouseClicked(int x, int y, int i) {
		super.mouseClicked(x, y, i);

		NBTTagCompound control = new NBTTagCompound();

		if(guiLeft + 144 <= x && guiLeft + 144 + 14 > x && guiTop + 35 < y && guiTop + 35 + 14 >= y) {
			control.setBoolean("control", true);

			PacketDispatcher.wrapper.sendToServer(new NBTControlPacket(control, zirnox.xCoord, zirnox.yCoord, zirnox.zCoord));
			mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("hbm:block.rbmk_az5_cover"), 0.5F));
		}

		if(guiLeft + 151 <= x && guiLeft + 151 + 36 > x && guiTop + 51 < y && guiTop + 51 + 36 >= y) {
			control.setBoolean("vent", true); // sus impostre like amogus

			PacketDispatcher.wrapper.sendToServer(new NBTControlPacket(control, zirnox.xCoord, zirnox.yCoord, zirnox.zCoord));
			mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("hbm:block.rbmk_az5_cover"), 0.5F));
		}
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String name = this.zirnox.hasCustomInventoryName() ? this.zirnox.getInventoryName() : I18n.format(this.zirnox.getInventoryName());

		this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

		int s = zirnox.getGaugeScaled(6, 0);
		drawTexturedModalRect(guiLeft + 160, guiTop + 108, 238, 0 + 12 * s, 18, 12);

		int c = zirnox.getGaugeScaled(6, 1);
		drawTexturedModalRect(guiLeft + 142, guiTop + 108, 238, 0 + 12 * c, 18, 12);

		int w = zirnox.getGaugeScaled(6, 2);
		drawTexturedModalRect(guiLeft + 178, guiTop + 108, 238, 0 + 12 * w, 18, 12);

		int h = zirnox.getGaugeScaled(12, 3);
		drawTexturedModalRect(guiLeft + 160, guiTop + 33, 220, 0 + 18 * h, 18, 17);

		int p = zirnox.getGaugeScaled(12, 4);
		drawTexturedModalRect(guiLeft + 178, guiTop + 33, 220, 0 + 18 * p, 18, 17);

		if(zirnox.isOn) {
			for(int x = 0; x < 4; x++)
				for(int y = 0; y < 4; y++)
					drawTexturedModalRect(guiLeft + 7 + 36 * x, guiTop + 15 + 36 * y, 238, 238, 18, 18);
			for(int x = 0; x < 3; x++)
				for(int y = 0; y < 3; y++)
					drawTexturedModalRect(guiLeft + 25 + 36 * x, guiTop + 33 + 36 * y, 238, 238, 18, 18);
			drawTexturedModalRect(guiLeft + 142, guiTop + 15, 220, 238, 18, 18);
		}

		this.drawInfoPanel(guiLeft - 16, guiTop + 36, 16, 16, 2);
		this.drawInfoPanel(guiLeft - 16, guiTop + 36 + 16, 16, 16, 3);

		if(zirnox.water.getFill() <= 0)
			this.drawInfoPanel(guiLeft - 16, guiTop + 36 + 32, 16, 16, 6);

		if(zirnox.carbonDioxide.getFill() <= 4000)
			this.drawInfoPanel(guiLeft - 16, guiTop + 36 + 32 + 16, 16, 16, 6);
	}

}