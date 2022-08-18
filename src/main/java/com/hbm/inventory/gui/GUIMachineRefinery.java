package com.hbm.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerMachineRefinery;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.lib.RefStrings;
import com.hbm.packet.NBTControlPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.tileentity.machine.oil.TileEntityMachineRefinery;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

public class GUIMachineRefinery extends GuiInfoContainer {
	
	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/processing/gui_refinery.png");
	private TileEntityMachineRefinery refinery;

	public GUIMachineRefinery(InventoryPlayer invPlayer, TileEntityMachineRefinery tedf) {
		super(new ContainerMachineRefinery(invPlayer, tedf));
		refinery = tedf;
		
		this.xSize = 176;
		this.ySize = 222;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);

		refinery.tanks[0].renderTankInfo(this, mouseX, mouseY, guiLeft + 26, guiTop + 70 - 52, 34, 52);
		refinery.tanks[1].renderTankInfo(this, mouseX, mouseY, guiLeft + 80, guiTop + 70 - 52, 16, 52);
		refinery.tanks[2].renderTankInfo(this, mouseX, mouseY, guiLeft + 98, guiTop + 70 - 52, 16, 52);
		refinery.tanks[3].renderTankInfo(this, mouseX, mouseY, guiLeft + 116, guiTop + 70 - 52, 16, 52);
		refinery.tanks[4].renderTankInfo(this, mouseX, mouseY, guiLeft + 134, guiTop + 70 - 52, 16, 52);
		this.drawElectricityInfo(this, mouseX, mouseY, guiLeft + 8, guiTop + 70 - 52, 16, 52, refinery.power, refinery.maxPower);
	}

	@Override
	protected void mouseClicked(int x, int y, int i) {
		super.mouseClicked(x, y, i);
		
		if(guiLeft + 64 <= x && guiLeft + 76 > x && guiTop + 20 < y && guiTop + 46 >= y) {
			mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
			NBTTagCompound data = new NBTTagCompound();
			data.setBoolean("toggle", true); //we only need to send one bit, so boolean it is
			PacketDispatcher.wrapper.sendToServer(new NBTControlPacket(data, refinery.xCoord, refinery.yCoord, refinery.zCoord));
		}
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String name = this.refinery.hasCustomInventoryName() ? this.refinery.getInventoryName() : I18n.format(this.refinery.getInventoryName());
		
		this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		
		if(refinery.tanks[0].getTankType() == Fluids.HOTCRACKOIL)
			drawTexturedModalRect(guiLeft + 64, guiTop + 20, 192, 0, 12, 26);

		int j = (int)refinery.getPowerScaled(52);
		drawTexturedModalRect(guiLeft + 8, guiTop + 70 - j, 176, 52 - j, 16, j);
		
		refinery.tanks[0].renderTank(guiLeft + 26, guiTop + 70, this.zLevel, 34, 52);
		refinery.tanks[1].renderTank(guiLeft + 80, guiTop + 70, this.zLevel, 16, 52);
		refinery.tanks[2].renderTank(guiLeft + 98, guiTop + 70, this.zLevel, 16, 52);
		refinery.tanks[3].renderTank(guiLeft + 116, guiTop + 70, this.zLevel, 16, 52);
		refinery.tanks[4].renderTank(guiLeft + 134, guiTop + 70, this.zLevel, 16, 52);
	}
}
