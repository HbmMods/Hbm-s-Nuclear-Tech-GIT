package com.hbm.inventory.gui;

import java.util.Arrays;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerMachineRadarNT;
import com.hbm.lib.RefStrings;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.machine.TileEntityMachineRadarNT;
import com.hbm.util.i18n.I18nUtil;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.network.play.client.C0DPacketCloseWindow;
import net.minecraft.util.ResourceLocation;

public class GUIMachineRadarNTSlots extends GuiInfoContainer {
	
	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/machine/gui_radar_link.png");
	private TileEntityMachineRadarNT radar;

	public GUIMachineRadarNTSlots(InventoryPlayer invPlayer, TileEntityMachineRadarNT tedf) {
		super(new ContainerMachineRadarNT(invPlayer, tedf));
		radar = tedf;
		
		this.xSize = 176;
		this.ySize = 184;
	}

	@Override
	protected void mouseClicked(int x, int y, int i) {
		super.mouseClicked(x, y, i);
		
		if(checkClick(x, y, 5, 5, 8, 8)) {
			mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
			this.mc.thePlayer.sendQueue.addToSendQueue(new C0DPacketCloseWindow(this.mc.thePlayer.openContainer.windowId)); // closes the server-side GUI component without resetting the client's cursor position
			FMLNetworkHandler.openGui(this.mc.thePlayer, MainRegistry.instance, 0, radar.getWorldObj(), radar.xCoord, radar.yCoord, radar.zCoord);
		}
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);
		
		if(checkClick(mouseX, mouseY, 5, 5, 8, 8)) this.func_146283_a(Arrays.asList(I18nUtil.resolveKeyArray("radar.toggleGui")), mouseX, mouseY);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String name = this.radar.hasCustomInventoryName() ? this.radar.getInventoryName() : I18n.format(this.radar.getInventoryName());
		if(MainRegistry.polaroidID == 11) name = "Reda";
		this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		
		if(radar.power > 0) {
			int i = (int) (radar.power * 160 / radar.maxPower);
			drawTexturedModalRect(guiLeft + 8, guiTop + 64, 0, 185, i, 16);
		}
	}

}
