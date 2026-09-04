package com.hbm.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerLaunchpadSoyuz;
import com.hbm.lib.RefStrings;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.toserver.NBTControlPacket;
import com.hbm.tileentity.machine.TileEntityLaunchpadSoyuz;
import com.hbm.tileentity.machine.TileEntityLaunchpadSoyuz.SoyuzStatus;
import com.hbm.util.i18n.I18nUtil;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

public class GUILaunchpadSoyuz extends GuiInfoContainer {

	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/machine/gui_launchpad_soyuz.png");
	private TileEntityLaunchpadSoyuz launcher;
	
	public GUILaunchpadSoyuz(InventoryPlayer invPlayer, TileEntityLaunchpadSoyuz tedf) {
		super(new ContainerLaunchpadSoyuz(invPlayer, tedf));
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

	@Override
	protected void mouseClicked(int x, int y, int i) {
		super.mouseClicked(x, y, i);
		
		NBTTagCompound data = null;

		if(guiLeft + 79 <= x && guiLeft + 79 + 18 > x && guiTop + 52 < y && guiTop + 52 + 18 >= y) {
			data = new NBTTagCompound();
			data.setBoolean("cargo", true);
		}

		if(guiLeft + 97 <= x && guiLeft + 97 + 18 > x && guiTop + 52 < y && guiTop + 52 + 18 >= y) {
			data = new NBTTagCompound();
			data.setBoolean("cargo", false);
		}

		if(guiLeft + 88 <= x && guiLeft + 88 + 18 > x && guiTop + 97 < y && guiTop + 97 + 18 >= y && launcher.soyuzStatus == SoyuzStatus.READY) {
			data = new NBTTagCompound();
			data.setBoolean("launch", true);
		}
		
		if(data != null) {
			this.click();
			PacketDispatcher.wrapper.sendToServer(new NBTControlPacket(data, launcher.xCoord, launcher.yCoord, launcher.zCoord));
		}
	}

	@Override
	protected void drawGuiContainerForegroundLayer( int i, int j) {
		String name = this.launcher.hasCustomInventoryName() ? this.launcher.getInventoryName() : I18n.format(this.launcher.getInventoryName());
		
		this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, 4, 0xffffff);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 17, this.ySize - 96 + 2, 4210752);
		
		if(launcher.soyuzStatus == SoyuzStatus.LAUNCHING) {
			
			int countdown = launcher.countdown;
			
			String secs = "" + countdown / 20;
			String cents = "" + (countdown % 20) * 5;
			if(secs.length() == 1) secs = "0" + secs;
			if(cents.length() == 1) cents += "0";
			
			float scale = 1;
			GL11.glScalef(scale, scale, 1);
			this.fontRendererObj.drawString(secs + ":" + cents, (int)(85 / scale), (int)(121 / scale), 0xff0000);
			GL11.glScalef(1/scale, 1/scale, 1);
			
		} else if(launcher.soyuzStatus == SoyuzStatus.ABSENT) {
			drawIndicator(I18nUtil.resolveKey("desc.gui.soyuz.idle"), 0xff0000);
		} else if(launcher.soyuzStatus == SoyuzStatus.LOADING) {
			drawIndicator(I18nUtil.resolveKey("desc.gui.soyuz.loading"), 0xff8000);
		} else if(launcher.soyuzStatus == SoyuzStatus.FUELING) {
			drawIndicator(I18nUtil.resolveKey("desc.gui.soyuz.fueling"), 0xffff00);
		} else if(launcher.soyuzStatus == SoyuzStatus.READY) {
			drawIndicator(I18nUtil.resolveKey("desc.gui.soyuz.ready"), 0x00ff00);
		}
	}
	
	protected void drawIndicator(String label, int color) {

		float scale = Math.min(1F, 22F / this.fontRendererObj.getStringWidth(label));
		
		GL11.glScalef(scale, scale, 1);
		this.fontRendererObj.drawString(label, (int)(97 / scale - this.fontRendererObj.getStringWidth(label) / 2F), (int)(125 / scale - this.fontRendererObj.FONT_HEIGHT / 2F), color);
		GL11.glScalef(1 / scale, 1 / scale, 1);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float interp, int x, int y) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		
		int power = (int) (launcher.power * 52 / launcher.maxPower);
		drawTexturedModalRect(guiLeft + 134, guiTop + 96 - power, 194, 52 - power, 16, power);
		
		drawTexturedModalRect(guiLeft + 97 - (launcher.cargoMode ? 18 : 0), guiTop + 52, 228 - (launcher.cargoMode ? 18 : 0), 26, 18, 18);
		
		drawTexturedModalRect(guiLeft + 157, guiTop + 31, launcher.hasJetFuel() ? 210 : 216, 0, 6, 8);
		drawTexturedModalRect(guiLeft + 175, guiTop + 31, launcher.hasOxidizer() ? 210 : 216, 0, 6, 8);
		drawTexturedModalRect(guiLeft + 139, guiTop + 31, launcher.power >= launcher.CONSUMPTION ? 210 : 216, 0, 6, 8);
		
		int l = launcher.orbital();
		if(l > 0) drawTexturedModalRect(guiLeft + 79, guiTop + 25, 210 + (l - 1) * 18, 8, 18, 18);
		
		if(launcher.soyuzStatus == SoyuzStatus.LAUNCHING)
			drawTexturedModalRect(guiLeft + 88, guiTop + 97, 210, 44, 18, 18);
		
		launcher.tanks[0].renderTank(guiLeft + 152, guiTop + 96, this.zLevel, 16, 52);
		launcher.tanks[1].renderTank(guiLeft + 170, guiTop + 96, this.zLevel, 16, 52);
		
		this.drawInfoPanel(guiLeft - 16, guiTop + 53, 16, 16, 2);
	}
}
