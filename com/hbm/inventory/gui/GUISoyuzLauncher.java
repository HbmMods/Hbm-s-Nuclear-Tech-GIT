package com.hbm.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.FluidTank;
import com.hbm.inventory.container.ContainerSoyuzLauncher;
import com.hbm.lib.RefStrings;
import com.hbm.packet.AuxButtonPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.tileentity.machine.TileEntitySoyuzLauncher;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUISoyuzLauncher extends GuiInfoContainer {

	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/gui_soyuz.png");
	private TileEntitySoyuzLauncher launcher;
	
	public GUISoyuzLauncher(InventoryPlayer invPlayer, TileEntitySoyuzLauncher tedf) {
		super(new ContainerSoyuzLauncher(invPlayer, tedf));
		launcher = tedf;
		
		this.xSize = 176;
		this.ySize = 222;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);

		launcher.tanks[0].renderTankInfo(this, mouseX, mouseY, guiLeft + 8, guiTop + 36, 16, 52);
		launcher.tanks[1].renderTankInfo(this, mouseX, mouseY, guiLeft + 26, guiTop + 36, 16, 52);
		this.drawElectricityInfo(this, mouseX, mouseY, guiLeft + 49, guiTop + 72, 6, 34, launcher.power, launcher.maxPower);

		//this.drawCustomInfoStat(mouseX, mouseY, guiLeft + 43, guiTop + 17, 18, 18, mouseX, mouseY, new String[]{"The Soyuz goes here"} );
		//this.drawCustomInfoStat(mouseX, mouseY, guiLeft + 43, guiTop + 35, 18, 18, mouseX, mouseY, new String[]{"Designator for cargo mode"} );
	}

	protected void mouseClicked(int x, int y, int i) {
    	super.mouseClicked(x, y, i);
		
    	if(guiLeft + 88 <= x && guiLeft + 88 + 18 > x && guiTop + 17 < y && guiTop + 17 + 18 >= y) {
    		
			mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
    		PacketDispatcher.wrapper.sendToServer(new AuxButtonPacket(launcher.xCoord, launcher.yCoord, launcher.zCoord, 0, 0));
    	}
		
    	if(guiLeft + 88 <= x && guiLeft + 88 + 18 > x && guiTop + 35 < y && guiTop + 35 + 18 >= y) {
    		
			mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
    		PacketDispatcher.wrapper.sendToServer(new AuxButtonPacket(launcher.xCoord, launcher.yCoord, launcher.zCoord, 1, 0));
    	}
    }

	@Override
	protected void drawGuiContainerForegroundLayer( int i, int j) {
		String name = this.launcher.hasCustomInventoryName() ? this.launcher.getInventoryName() : I18n.format(this.launcher.getInventoryName());
		
		this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		
		int i = (int)launcher.getPowerScaled(34);
		drawTexturedModalRect(guiLeft + 49, guiTop + 106 - i, 194, 52 - i, 6, i);
		
		drawTexturedModalRect(guiLeft + 61, guiTop + 17, 176 + (launcher.hasRocket() ? 18 : 0), 0, 18, 18);
		int j = launcher.designator();
		
		if(j > 0)
			drawTexturedModalRect(guiLeft + 61, guiTop + 35, 192 + (j - 1), 0, 18, 18);
		
		Minecraft.getMinecraft().getTextureManager().bindTexture(launcher.tanks[0].getSheet());
		launcher.tanks[0].renderTank(this, guiLeft + 8, guiTop + 88, launcher.tanks[0].getTankType().textureX() * FluidTank.x, launcher.tanks[0].getTankType().textureY() * FluidTank.y, 16, 52);
		
		Minecraft.getMinecraft().getTextureManager().bindTexture(launcher.tanks[1].getSheet());
		launcher.tanks[1].renderTank(this, guiLeft + 26, guiTop + 88, launcher.tanks[1].getTankType().textureX() * FluidTank.x, launcher.tanks[1].getTankType().textureY() * FluidTank.y, 16, 52);
	}
}
