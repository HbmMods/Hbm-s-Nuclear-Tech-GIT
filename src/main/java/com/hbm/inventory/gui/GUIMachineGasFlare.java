package com.hbm.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerMachineGasFlare;
import com.hbm.inventory.fluid.trait.FT_Flammable;
import com.hbm.lib.RefStrings;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.toserver.NBTControlPacket;
import com.hbm.tileentity.machine.oil.TileEntityMachineGasFlare;
import com.hbm.util.i18n.I18nUtil;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

public class GUIMachineGasFlare extends GuiInfoContainer {

	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/generators/gui_flare_stack.png");
	private TileEntityMachineGasFlare flare;
	
	public GUIMachineGasFlare(InventoryPlayer invPlayer, TileEntityMachineGasFlare tedf) {
		super(new ContainerMachineGasFlare(invPlayer, tedf));
		flare = tedf;
		
		this.xSize = 176;
		this.ySize = 203;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);

		this.drawCustomInfoStat(mouseX, mouseY, guiLeft + 79, guiTop + 16, 35, 10, mouseX, mouseY, I18nUtil.resolveKeyArray("flare.valve"));
		this.drawCustomInfoStat(mouseX, mouseY, guiLeft + 79, guiTop + 50, 35, 14, mouseX, mouseY, I18nUtil.resolveKeyArray("flare.ignition"));
		
		flare.tank.renderTankInfo(this, mouseX, mouseY, guiLeft + 35, guiTop + 69 - 52, 16, 52);
		this.drawElectricityInfo(this, mouseX, mouseY, guiLeft + 143, guiTop + 69 - 52, 16, 52, flare.power, flare.maxPower);
	}

	protected void mouseClicked(int x, int y, int i) {
		super.mouseClicked(x, y, i);

		if(guiLeft + 89 <= x && guiLeft + 89 + 16 > x && guiTop + 16 < y && guiTop + 16 + 10 >= y) {
			mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
			NBTTagCompound data = new NBTTagCompound();
			data.setBoolean("valve", true);
			PacketDispatcher.wrapper.sendToServer(new NBTControlPacket(data, flare.xCoord, flare.yCoord, flare.zCoord));
			
		} else if(guiLeft + 89 <= x && guiLeft + 89 + 16 > x && guiTop + 50 < y && guiTop + 50 + 14 >= y) {
			mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
			NBTTagCompound data = new NBTTagCompound();
			data.setBoolean("dial", true);
			PacketDispatcher.wrapper.sendToServer(new NBTControlPacket(data, flare.xCoord, flare.yCoord, flare.zCoord));
		}
		
	}

	@Override
	protected void drawGuiContainerForegroundLayer( int i, int j) {
		//String name = this.flare.hasCustomInventoryName() ? this.flare.getInventoryName() : I18n.format(this.flare.getInventoryName());
		
		//this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

		int j = (int)flare.getPowerScaled(52);
		drawTexturedModalRect(guiLeft + 143, guiTop + 69 - j, 176, 94 - j, 16, j);

		if(flare.isOn)  drawTexturedModalRect(guiLeft + 79, guiTop + 15, 176, 0, 35, 10);
		if(flare.doesBurn)  drawTexturedModalRect(guiLeft + 79, guiTop + 49, 176, 10, 35, 14);
		
		if(flare.isOn && flare.doesBurn && flare.tank.getFill() > 0 && flare.tank.getTankType().hasTrait(FT_Flammable.class))
			drawTexturedModalRect(guiLeft + 88, guiTop + 29, 176, 24, 18, 18);
		
		flare.tank.renderTank(guiLeft + 35, guiTop + 69, this.zLevel, 16, 52);
	}
}
