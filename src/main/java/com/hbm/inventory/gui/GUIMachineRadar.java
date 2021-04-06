package com.hbm.inventory.gui;

import java.util.Arrays;

import org.lwjgl.opengl.GL11;

import com.hbm.config.WeaponConfig;
import com.hbm.inventory.container.ContainerMachineRadar;
import com.hbm.lib.RefStrings;
import com.hbm.packet.AuxButtonPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.tileentity.machine.TileEntityMachineRadar;
import com.hbm.util.I18nUtil;

import api.hbm.entity.IRadarDetectable.RadarTargetType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUIMachineRadar extends GuiInfoContainer {
	
	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/machine/gui_radar.png");
	private TileEntityMachineRadar diFurnace;

	public GUIMachineRadar(InventoryPlayer invPlayer, TileEntityMachineRadar tedf) {
		super(new ContainerMachineRadar(invPlayer, tedf));
		diFurnace = tedf;
		texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/machine/gui_radar.png");
		
		this.xSize = 216;
		this.ySize = 234;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);

		this.drawElectricityInfo(this, mouseX, mouseY, guiLeft + 8, guiTop + 221, 200, 7, diFurnace.power, diFurnace.maxPower);

		this.drawCustomInfoStat(mouseX, mouseY, guiLeft - 10, guiTop + 98, 8, 8, mouseX, mouseY, I18nUtil.resolveKeyArray("radar.detectMissiles") );
		this.drawCustomInfoStat(mouseX, mouseY, guiLeft - 10, guiTop + 108, 8, 8, mouseX, mouseY, I18nUtil.resolveKeyArray("radar.detectPlayers"));
		this.drawCustomInfoStat(mouseX, mouseY, guiLeft - 10, guiTop + 118, 8, 8, mouseX, mouseY, I18nUtil.resolveKeyArray("radar.smartMode"));
		this.drawCustomInfoStat(mouseX, mouseY, guiLeft - 10, guiTop + 128, 8, 8, mouseX, mouseY, I18nUtil.resolveKeyArray("radar.redMode"));

		if(!diFurnace.nearbyMissiles.isEmpty()) {
			for(int[] m : diFurnace.nearbyMissiles) {
				int x = guiLeft + (int)((m[0] - diFurnace.xCoord) / ((double)WeaponConfig.radarRange * 2 + 1) * (200D - 8D)) + 108;
				int z = guiTop + (int)((m[1] - diFurnace.zCoord) / ((double)WeaponConfig.radarRange * 2 + 1) * (200D - 8D)) + 117;
				
				if(mouseX + 4 > x && mouseX - 4 < x && 
						mouseY + 4 > z && mouseY - 4 < z) {

					
					String[] text = new String[] { RadarTargetType.values()[m[2]].name, m[0] + " / " + m[1], "Alt.: " + m[3] };
					
					this.func_146283_a(Arrays.asList(text), x, z);
					
					return;
				}
			}
		}
	}

	@Override
	protected void mouseClicked(int x, int y, int i) {
		super.mouseClicked(x, y, i);

		if(guiLeft -10 <= x && guiLeft + -10 + 8 > x && guiTop + 98 < y && guiTop + 98 + 8 >= y) {
			mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
			PacketDispatcher.wrapper.sendToServer(new AuxButtonPacket(diFurnace.xCoord, diFurnace.yCoord, diFurnace.zCoord, 0, 0));
		}

		if(guiLeft -10 <= x && guiLeft + -10 + 8 > x && guiTop + 108 < y && guiTop + 108 + 8 >= y) {
			mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
			PacketDispatcher.wrapper.sendToServer(new AuxButtonPacket(diFurnace.xCoord, diFurnace.yCoord, diFurnace.zCoord, 0, 1));
		}

		if(guiLeft -10 <= x && guiLeft + -10 + 8 > x && guiTop + 118 < y && guiTop + 118 + 8 >= y) {
			mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
			PacketDispatcher.wrapper.sendToServer(new AuxButtonPacket(diFurnace.xCoord, diFurnace.yCoord, diFurnace.zCoord, 0, 2));
		}

		if(guiLeft -10 <= x && guiLeft + -10 + 8 > x && guiTop + 128 < y && guiTop + 128 + 8 >= y) {
			mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
			PacketDispatcher.wrapper.sendToServer(new AuxButtonPacket(diFurnace.xCoord, diFurnace.yCoord, diFurnace.zCoord, 0, 3));
		}
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String name = I18n.format("container.radar");
		
		this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		drawTexturedModalRect(guiLeft - 14, guiTop + 94, 216, 198, 14, 46);
		
		if(diFurnace.scanMissiles)
			drawTexturedModalRect(guiLeft - 10, guiTop + 98, 230, 202, 8, 8);
		
		if(diFurnace.scanPlayers)
			drawTexturedModalRect(guiLeft - 10, guiTop + 108, 230, 212, 8, 8);
		
		if(diFurnace.smartMode)
			drawTexturedModalRect(guiLeft - 10, guiTop + 118, 230, 222, 8, 8);
		
		if(diFurnace.redMode)
			drawTexturedModalRect(guiLeft - 10, guiTop + 128, 230, 232, 8, 8);
		
		if(diFurnace.power > 0) {
			int i = (int)diFurnace.getPowerScaled(200);
			drawTexturedModalRect(guiLeft + 8, guiTop + 221, 0, 234, i, 16);
		}
		
		if(!diFurnace.nearbyMissiles.isEmpty()) {
			for(int[] m : diFurnace.nearbyMissiles) {
				int x = (int)((m[0] - diFurnace.xCoord) / ((double)WeaponConfig.radarRange * 2 + 1) * (200D - 8D)) - 4;
				int z = (int)((m[1] - diFurnace.zCoord) / ((double)WeaponConfig.radarRange * 2 + 1) * (200D - 8D)) - 4;
				int t = m[2];

				drawTexturedModalRect(guiLeft + 108 + x, guiTop + 117 + z, 216, 8 * t, 8, 8);
			}
		}
	}
}
