package com.hbm.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerDroneCrate;
import com.hbm.lib.RefStrings;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.toserver.NBTControlPacket;
import com.hbm.tileentity.network.TileEntityDroneCrate;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

public class GUIDroneCrate extends GuiInfoContainer {
	
	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/storage/gui_crate_drone.png");
	private TileEntityDroneCrate crate;

	public GUIDroneCrate(InventoryPlayer invPlayer, TileEntityDroneCrate crate) {
		super(new ContainerDroneCrate(invPlayer, crate));
		this.crate = crate;
		
		this.xSize = 176;
		this.ySize = 185;
	}
	
	@Override
	public void drawScreen(int x, int y, float interp) {
		super.drawScreen(x, y, interp);
		
		crate.tank.renderTankInfo(this, x, y, guiLeft + 125, guiTop + 17, 16, 34);
	}

	@Override
	protected void mouseClicked(int x, int y, int i) {
		super.mouseClicked(x, y, i);
		
		String op = null;

		// Toggle type
		if(guiLeft + 151 <= x && guiLeft + 151 + 18 > x && guiTop + 16 < y && guiTop + 16 + 18 >= y) op = "type";
		// Toggle mode
		if(guiLeft + 151 <= x && guiLeft + 151 + 18 > x && guiTop + 52 < y && guiTop + 52 + 18 >= y) op = "mode";
		
		if(op != null) {
			mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
			NBTTagCompound data = new NBTTagCompound();
			data.setBoolean(op, true);
			PacketDispatcher.wrapper.sendToServer(new NBTControlPacket(data, crate.xCoord, crate.yCoord, crate.zCoord));
		}
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String name = this.crate.hasCustomInventoryName() ? this.crate.getInventoryName() : I18n.format(this.crate.getInventoryName());
		this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float interp, int x, int y) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

		drawTexturedModalRect(guiLeft + 151, guiTop + 16, 194, crate.itemType ? 0 : 18, 18, 18);
		drawTexturedModalRect(guiLeft + 151, guiTop + 52, 176, crate.sendingMode ? 18 : 0, 18, 18);
		
		crate.tank.renderTank(guiLeft + 125, guiTop + 51, this.zLevel, 16, 34);
	}
}
