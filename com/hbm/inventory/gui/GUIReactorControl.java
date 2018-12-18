package com.hbm.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerReactorControl;
import com.hbm.lib.RefStrings;
import com.hbm.packet.AuxButtonPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.tileentity.machine.TileEntityReactorControl;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUIReactorControl extends GuiInfoContainer {

	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/gui_reactor_control.png");
	private TileEntityReactorControl control;
	
	public GUIReactorControl(InventoryPlayer invPlayer, TileEntityReactorControl tedf) {
		super(new ContainerReactorControl(invPlayer, tedf));
		control = tedf;
		
		this.xSize = 176;
		this.ySize = 166;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);
		
		this.drawCustomInfo(this, mouseX, mouseY, guiLeft + 80, guiTop + 114, 88, 4, new String[] { "Hull Temperature:", "   " + Math.round((control.hullHeat) * 0.00001 * 980 + 20) + "°C" });
	}

	protected void mouseClicked(int x, int y, int i) {
    	super.mouseClicked(x, y, i);
		
    	if(guiLeft + 7 <= x && guiLeft + 7 + 18 > x && guiTop + 16 < y && guiTop + 16 + 18 >= y) {
    		
			mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
    		PacketDispatcher.wrapper.sendToServer(new AuxButtonPacket(control.xCoord, control.yCoord, control.zCoord, control.isOn ? 0 : 1, 0));
    	}
    }
	
	@Override
	protected void drawGuiContainerForegroundLayer( int i, int j) {
		String name = this.control.hasCustomInventoryName() ? this.control.getInventoryName() : I18n.format(this.control.getInventoryName());
		
		this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		
		if(control.hullHeat > 0) {
			int i = (control.hullHeat * 88 / 100000);
			
			i = (int) Math.min(i, 160);
			
			drawTexturedModalRect(guiLeft + 80, guiTop + 59, 0, 166, i, 4);
		}
		
		if(control.coreHeat > 0) {
			int i = (control.coreHeat * 88 / 50000);
			
			i = (int) Math.min(i, 160);
			
			drawTexturedModalRect(guiLeft + 80, guiTop + 65, 0, 170, i, 4);
		}
		
		if(control.steam > 0) {
			int i = (control.steam * 88 / control.maxSteam);
			
			i = (int) Math.min(i, 160);
			
			drawTexturedModalRect(guiLeft + 80, guiTop + 53, 0, 174 + 4 * control.compression, i, 4);
		}
		
		if(control.cool > 0) {
			int i = (control.cool * 88 / control.maxCool);
			
			i = (int) Math.min(i, 160);
			
			drawTexturedModalRect(guiLeft + 80, guiTop + 47, 0, 194, i, 4);
		}
		
		if(control.water > 0) {
			int i = (control.water * 88 / control.maxWater);
			
			i = (int) Math.min(i, 160);
			
			drawTexturedModalRect(guiLeft + 80, guiTop + 41, 0, 190, i, 4);
		}
		
		if(control.fuel > 0) {
			int i = (control.fuel * 88 / 100);
			
			i = (int) Math.min(i, 160);
			
			drawTexturedModalRect(guiLeft + 80, guiTop + 35, 0, 186, i, 4);
		}
		
		if(control.isOn) {
			drawTexturedModalRect(guiLeft + 7, guiTop + 16, 218, 0, 18, 18);
		}
		
		if(control.auto) {
			drawTexturedModalRect(guiLeft + 43, guiTop + 16, 236, 0, 18, 18);
		}
		
		drawTexturedModalRect(guiLeft + 63, guiTop + 52, 176 + 14 * control.compression, 0, 14, 18);
		
		if(!control.isLinked) {
			drawTexturedModalRect(guiLeft + 79, guiTop + 16, 88, 166, 18, 18);
		}

		if(control.water < control.maxWater * 0.1) {
			drawTexturedModalRect(guiLeft + 79 + 18, guiTop + 16, 88 + 18, 166, 18, 18);
		}
		
		if(control.cool < control.maxCool * 0.1) {
			drawTexturedModalRect(guiLeft + 79 + 18 * 2, guiTop + 16, 88 + 18 * 2, 166, 18, 18);
		}
		
		if(control.steam > control.maxSteam * 0.9) {
			drawTexturedModalRect(guiLeft + 79 + 18 * 3, guiTop + 16, 88 + 18 * 3, 166, 18, 18);
		}
		
		if(control.coreHeat > (50000 * 0.9)) {
			drawTexturedModalRect(guiLeft + 79 + 18 * 4, guiTop + 16, 88 + 18 * 4, 166, 18, 18);
		}
		
		if(control.rods == control.maxRods) {
			drawTexturedModalRect(guiLeft + 25, guiTop + 16, 176, 18, 18, 18);
		} else if(control.rods > 0) {
			drawTexturedModalRect(guiLeft + 25, guiTop + 16, 194, 18, 18, 18);
		}
	}
}
