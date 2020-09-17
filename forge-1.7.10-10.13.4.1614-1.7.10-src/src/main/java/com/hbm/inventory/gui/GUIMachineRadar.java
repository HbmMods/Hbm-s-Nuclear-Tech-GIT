package com.hbm.inventory.gui;

import java.util.Arrays;

import org.lwjgl.opengl.GL11;

import com.hbm.config.WeaponConfig;
import com.hbm.inventory.container.ContainerMachineRadar;
import com.hbm.lib.RefStrings;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.machine.TileEntityMachineRadar;

import api.hbm.entity.IRadarDetectable.RadarTargetType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUIMachineRadar extends GuiInfoContainer {
	
	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/gui_radar.png");
	private TileEntityMachineRadar diFurnace;

	public GUIMachineRadar(InventoryPlayer invPlayer, TileEntityMachineRadar tedf) {
		super(new ContainerMachineRadar(invPlayer, tedf));
		diFurnace = tedf;
		
		this.xSize = 216;
		this.ySize = 234;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);

		this.drawElectricityInfo(this, mouseX, mouseY, guiLeft + 8, guiTop + 221, 200, 7, diFurnace.power, diFurnace.maxPower);

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
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String name = I18n.format("container.radar");
		
		this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		
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
