package com.hbm.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerMiningLaser;
import com.hbm.lib.RefStrings;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.toserver.AuxButtonPacket;
import com.hbm.tileentity.machine.TileEntityMachineMiningLaser;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUIMiningLaser extends GuiInfoContainer {

	public static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/machine/gui_laser_miner.png");
	private TileEntityMachineMiningLaser laser;
	
	public GUIMiningLaser(InventoryPlayer invPlayer, TileEntityMachineMiningLaser laser) {
		super(new ContainerMiningLaser(invPlayer, laser));
		this.laser = laser;

		this.xSize = 176;
		this.ySize = 222;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);
		
		this.drawElectricityInfo(this, mouseX, mouseY, guiLeft + 8, guiTop + 106 - 88, 16, 88, laser.power, laser.maxPower);

		String[] text = new String[] { "Acceptable upgrades:",
				" -Speed (stacks to level 12)",
				" -Effectiveness (stacks to level 12)",
				" -Overdrive (stacks to level 3)",
				" -Fortune (stacks to level 3)",
				" -Smelter (exclusive)",
				" -Shredder (exclusive)",
				" -Centrifuge (exclusive)",
				" -Crystallizer (exclusive)",
				" -Nullifier"};
		this.drawCustomInfoStat(mouseX, mouseY, guiLeft + 87, guiTop + 31, 8, 8, guiLeft + 141, guiTop + 39 + 16, text);

		laser.tank.renderTankInfo(this, mouseX, mouseY, guiLeft + 35, guiTop + 124 - 52, 7, 52);
	}

	protected void mouseClicked(int x, int y, int i) {
    	super.mouseClicked(x, y, i);
		
    	if(guiLeft + 61 <= x && guiLeft + 61 + 18 > x && guiTop + 17 < y && guiTop + 17 + 18 >= y) {
    		
			mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
    		PacketDispatcher.wrapper.sendToServer(new AuxButtonPacket(laser.xCoord, laser.yCoord, laser.zCoord, 0, 0));
    	}
    }
	
	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String name = this.laser.hasCustomInventoryName() ? this.laser.getInventoryName() : I18n.format(this.laser.getInventoryName());
		
		this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, 4, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
		
		String width = "" + laser.getWidth();
		this.fontRendererObj.drawString(width, 43 - this.fontRendererObj.getStringWidth(width) / 2, 26, 0xffffff);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		
		if(laser.isOn)
			drawTexturedModalRect(guiLeft + 61, guiTop + 17, 200, 0, 18, 18);

		int i = laser.getPowerScaled(88);
		drawTexturedModalRect(guiLeft + 8, guiTop + 106 - i, 176, 88 - i, 16, i);

		int j = laser.getProgressScaled(34);
		drawTexturedModalRect(guiLeft + 66, guiTop + 36, 192, 0, 8, j);
		
		this.drawInfoPanel(guiLeft + 87, guiTop + 31, 8, 8, 8);
		
		laser.tank.renderTank(guiLeft + 35, guiTop + 124, this.zLevel, 7, 52);
	}
}
