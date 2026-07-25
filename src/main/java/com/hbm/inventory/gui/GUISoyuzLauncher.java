package com.hbm.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerSoyuzLauncher;
import com.hbm.lib.RefStrings;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.toserver.AuxButtonPacket;
import com.hbm.tileentity.machine.TileEntitySoyuzLauncher;
import com.hbm.util.i18n.I18nUtil;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUISoyuzLauncher extends GuiInfoContainer {

	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/machine/gui_soyuz.png");
	private TileEntitySoyuzLauncher launcher;
	
	public GUISoyuzLauncher(InventoryPlayer invPlayer, TileEntitySoyuzLauncher tedf) {
		super(new ContainerSoyuzLauncher(invPlayer, tedf));
		launcher = tedf;
		
		this.xSize = 194;
		this.ySize = 244;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);

		launcher.tanks[0].renderTankInfo(this, mouseX, mouseY, guiLeft + 152, guiTop + 44, 16, 52);
		launcher.tanks[1].renderTankInfo(this, mouseX, mouseY, guiLeft + 170, guiTop + 44, 16, 52);
		this.drawElectricityInfo(this, mouseX, mouseY, guiLeft + 134, guiTop + 44, 16, 52, launcher.power, launcher.maxPower);
		
		String[] descText = I18nUtil.resolveKeyArray("desc.gui.soyuz.desc");
		this.drawCustomInfoStat(mouseX, mouseY, guiLeft - 16, guiTop + 53, 16, 16, guiLeft - 8, guiTop + 53 + 16, descText);

		String[] cargoText = I18nUtil.resolveKeyArray("desc.gui.soyuz.cargo");
		this.drawCustomInfoStat(mouseX, mouseY, guiLeft + 79, guiTop + 52, 18, 18, mouseX, mouseY, cargoText );
		String[] satelliteText = I18nUtil.resolveKeyArray("desc.gui.soyuz.satellite");
		this.drawCustomInfoStat(mouseX, mouseY, guiLeft + 97, guiTop + 52, 18, 18, mouseX, mouseY, satelliteText );
	}

	protected void mouseClicked(int x, int y, int i) {
    	super.mouseClicked(x, y, i);
		
    	if(guiLeft + 97 <= x && guiLeft + 97 + 18 > x && guiTop + 52 < y && guiTop + 52 + 18 >= y) {
    		
			mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
    		PacketDispatcher.wrapper.sendToServer(new AuxButtonPacket(launcher.xCoord, launcher.yCoord, launcher.zCoord, 0, 0));
    	}
		
    	if(guiLeft + 79 <= x && guiLeft + 79 + 18 > x && guiTop + 52 < y && guiTop + 52 + 18 >= y) {
    		
			mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
    		PacketDispatcher.wrapper.sendToServer(new AuxButtonPacket(launcher.xCoord, launcher.yCoord, launcher.zCoord, 1, 0));
    	}
		
    	if(guiLeft + 88 <= x && guiLeft + 88 + 18 > x && guiTop + 97 < y && guiTop + 97 + 18 >= y) {
    		
			mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
    		PacketDispatcher.wrapper.sendToServer(new AuxButtonPacket(launcher.xCoord, launcher.yCoord, launcher.zCoord, 0, 1));
    	}
    }

	@Override
	protected void drawGuiContainerForegroundLayer( int i, int j) {
		String name = this.launcher.hasCustomInventoryName() ? this.launcher.getInventoryName() : I18n.format(this.launcher.getInventoryName());
		
		this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, 4, 0xffffff);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 17, this.ySize - 96 + 2, 4210752);
		
		String secs = "" + launcher.countdown / 20;
		String cents = "" + (launcher.countdown % 20) * 5;
		if(secs.length() == 1)
			secs = "0" + secs;
		if(cents.length() == 1)
			cents += "0";
		
		float scale = 1;
		GL11.glScalef(scale, scale, 1);
		this.fontRendererObj.drawString(secs + ":" + cents, (int)(85 / scale), (int)(121 / scale), 0xff0000);
		GL11.glScalef(1/scale, 1/scale, 1);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		
		int i = (int)launcher.getPowerScaled(52);
		drawTexturedModalRect(guiLeft + 134, guiTop + 96 - i, 194, 52 - i, 16, i);
		
		drawTexturedModalRect(guiLeft + 97, guiTop + 79, 210 + (launcher.hasRocket() ? 18 : 0), 8, 18, 18);
		int j = launcher.designator();
		
		if(j > 0)
			drawTexturedModalRect(guiLeft + 79, guiTop + 79, 210 + (j - 1) * 18, 8, 18, 18);
		
		int k = launcher.mode;
		drawTexturedModalRect(guiLeft + 97 - k * 18, guiTop + 52, 228 - k * 18, 26, 18, 18);
		
		int l = launcher.orbital();
		
		if(l > 0)
			drawTexturedModalRect(guiLeft + 79, guiTop + 25, 210 + (l - 1) * 18, 8, 18, 18);
		
		int m = launcher.satellite();
		
		if(m > 0)
			drawTexturedModalRect(guiLeft + 97, guiTop + 25, 210 + (m - 1) * 18, 8, 18, 18);
		
		if(launcher.starting)
			drawTexturedModalRect(guiLeft + 88, guiTop + 97, 210, 44, 18, 18);
		
		if(launcher.hasFuel())
			drawTexturedModalRect(guiLeft + 157, guiTop + 31, 210, 0, 6, 8);
		else
			drawTexturedModalRect(guiLeft + 157, guiTop + 31, 216, 0, 6, 8);
		
		if(launcher.hasOxy())
			drawTexturedModalRect(guiLeft + 175, guiTop + 31, 210, 0, 6, 8);
		else
			drawTexturedModalRect(guiLeft + 175, guiTop + 31, 216, 0, 6, 8);
		
		if(launcher.hasPower())
			drawTexturedModalRect(guiLeft + 139, guiTop + 31, 210, 0, 6, 8);
		else
			drawTexturedModalRect(guiLeft + 139, guiTop + 31, 216, 0, 6, 8);
		
		launcher.tanks[0].renderTank(guiLeft + 152, guiTop + 96, this.zLevel, 16, 52);
		launcher.tanks[1].renderTank(guiLeft + 170, guiTop + 96, this.zLevel, 16, 52);
		
		this.drawInfoPanel(guiLeft - 16, guiTop + 53, 16, 16, 2);
	}
}
