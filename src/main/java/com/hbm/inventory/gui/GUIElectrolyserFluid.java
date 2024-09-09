package com.hbm.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerElectrolyserFluid;
import com.hbm.lib.RefStrings;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.toserver.NBTControlPacket;
import com.hbm.tileentity.machine.TileEntityElectrolyser;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

public class GUIElectrolyserFluid extends GuiInfoContainer {
	
	public static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/processing/gui_electrolyser_fluid.png");
	private TileEntityElectrolyser electrolyser;

	public GUIElectrolyserFluid(InventoryPlayer invPlayer, TileEntityElectrolyser electrolyser) {
		super(new ContainerElectrolyserFluid(invPlayer, electrolyser));
		this.electrolyser = electrolyser;

		this.xSize = 210;
		this.ySize = 204;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);
		
		electrolyser.tanks[0].renderTankInfo(this, mouseX, mouseY, guiLeft + 42, guiTop + 18, 16, 52);
		electrolyser.tanks[1].renderTankInfo(this, mouseX, mouseY, guiLeft + 96, guiTop + 18, 16, 52);
		electrolyser.tanks[2].renderTankInfo(this, mouseX, mouseY, guiLeft + 116, guiTop + 18, 16, 52);
		
		this.drawElectricityInfo(this, mouseX, mouseY, guiLeft + 186, guiTop + 18, 16, 89, electrolyser.power, electrolyser.maxPower);
	}
	
	protected void mouseClicked(int x, int y, int i) {
		super.mouseClicked(x, y, i);

		if(guiLeft + 8 <= x && guiLeft + 8 + 54 > x && guiTop + 82 < y && guiTop + 82 + 12 >= y) {
			mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
			NBTTagCompound data = new NBTTagCompound();
			data.setBoolean("sgm", true);
			PacketDispatcher.wrapper.sendToServer(new NBTControlPacket(data, electrolyser.xCoord, electrolyser.yCoord, electrolyser.zCoord));
		}
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String name = this.electrolyser.hasCustomInventoryName() ? this.electrolyser.getInventoryName() : I18n.format(this.electrolyser.getInventoryName());

		this.fontRendererObj.drawString(name, (this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2) - 16, 7, 0xffffff);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 94, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		
		int p = (int) (electrolyser.power * 89 / electrolyser.maxPower);
		drawTexturedModalRect(guiLeft + 186, guiTop + 107 - p, 210, 89 - p, 16, p);
		
		if(electrolyser.power >= electrolyser.usageFluid)
			drawTexturedModalRect(guiLeft + 190, guiTop + 4, 226, 40, 9, 12);
		
		int e = electrolyser.progressFluid * 41 / electrolyser.processFluidTime;
		drawTexturedModalRect(guiLeft + 62, guiTop + 26, 226, 0, 12, e);

		electrolyser.tanks[0].renderTank(guiLeft + 42, guiTop + 70, this.zLevel, 16, 52);
		electrolyser.tanks[1].renderTank(guiLeft + 96, guiTop + 70, this.zLevel, 16, 52);
		electrolyser.tanks[2].renderTank(guiLeft + 116, guiTop + 70, this.zLevel, 16, 52);
	}
}
