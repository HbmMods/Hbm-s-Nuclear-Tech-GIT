package com.hbm.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerMachineSolderingStation;
import com.hbm.lib.RefStrings;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.toserver.NBTControlPacket;
import com.hbm.tileentity.machine.TileEntityMachineSolderingStation;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;

public class GUIMachineSolderingStation extends GuiInfoContainer {

	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/processing/gui_soldering_station.png");
	private TileEntityMachineSolderingStation solderer;

	public GUIMachineSolderingStation(InventoryPlayer playerInv, TileEntityMachineSolderingStation tile) {
		super(new ContainerMachineSolderingStation(playerInv, tile));
		
		this.solderer = tile;
		this.xSize = 176;
		this.ySize = 204;
	}
	
	@Override
	public void drawScreen(int x, int y, float interp) {
		super.drawScreen(x, y, interp);

		solderer.tank.renderTankInfo(this, x, y, guiLeft + 35, guiTop + 63, 34, 16);
		this.drawElectricityInfo(this, x, y, guiLeft + 152, guiTop + 18, 16, 52, solderer.getPower(), solderer.getMaxPower());
		
		this.drawCustomInfoStat(x, y, guiLeft + 78, guiTop + 67, 8, 8, guiLeft + 78, guiTop + 67, this.getUpgradeInfo(solderer));
		

		this.drawCustomInfoStat(x, y, guiLeft + 5, guiTop + 66, 10, 10, x, y,
				"Recipe Collision Prevention: " + (solderer.collisionPrevention ? EnumChatFormatting.GREEN + "ON" : EnumChatFormatting.RED + "OFF"),
				"Prevents no-fluid recipes from being processed",
				"when fluid is present.");
	}

	@Override
	protected void mouseClicked(int x, int y, int i) {
		super.mouseClicked(x, y, i);

		if(guiLeft + 5 <= x && guiLeft + 5 + 10 > x && guiTop + 66 < y && guiTop + 66 + 10 >= y) {
			mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
			NBTTagCompound data = new NBTTagCompound();
			data.setBoolean("collision", true);
			PacketDispatcher.wrapper.sendToServer(new NBTControlPacket(data, solderer.xCoord, solderer.yCoord, solderer.zCoord));
		}
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String name = this.solderer.hasCustomInventoryName() ? this.solderer.getInventoryName() : I18n.format(this.solderer.getInventoryName());
		this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2 - 18, 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float interp, int x, int y) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		
		if(solderer.collisionPrevention) {
			drawTexturedModalRect(guiLeft + 5, guiTop + 66, 192, 14, 10, 10);
		}

		int p = (int) (solderer.power * 52 / Math.max(solderer.maxPower, 1));
		drawTexturedModalRect(guiLeft + 152, guiTop + 70 - p, 176, 52 - p, 16, p);

		int i = solderer.progress * 33 / Math.max(solderer.processTime, 1);
		drawTexturedModalRect(guiLeft + 72, guiTop + 28, 192, 0, i, 14);
		
		if(solderer.power >= solderer.consumption) {
			drawTexturedModalRect(guiLeft + 156, guiTop + 4, 176, 52, 9, 12);
		}

		this.drawInfoPanel(guiLeft + 78, guiTop + 67, 8, 8, 8);
		solderer.tank.renderTank(guiLeft + 35, guiTop + 79, this.zLevel, 34, 16, 1);
	}
}
