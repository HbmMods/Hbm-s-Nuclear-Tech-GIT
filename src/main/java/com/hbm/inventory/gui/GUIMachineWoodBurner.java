package com.hbm.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerMachineWoodBurner;
import com.hbm.lib.RefStrings;
import com.hbm.packet.NBTControlPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.tileentity.machine.TileEntityMachineWoodBurner;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

public class GUIMachineWoodBurner extends GuiInfoContainer {
	
	private TileEntityMachineWoodBurner burner;
	private final ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/generators/gui_wood_burner_alt.png");

	public GUIMachineWoodBurner(InventoryPlayer invPlayer, TileEntityMachineWoodBurner tedf) {
		super(new ContainerMachineWoodBurner(invPlayer, tedf));
		burner = tedf;
		
		this.xSize = 176;
		this.ySize = 186;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);
		this.drawElectricityInfo(this, mouseX, mouseY, guiLeft + 143, guiTop + 18, 16, 34, burner.power, burner.maxPower);
	}

	@Override
	protected void mouseClicked(int x, int y, int i) {
		super.mouseClicked(x, y, i);

		if(guiLeft + 53 <= x && guiLeft + 53 + 16 > x && guiTop + 17 < y && guiTop + 17 + 15 >= y) {
			mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
			NBTTagCompound data = new NBTTagCompound();
			data.setBoolean("toggle", false);
			PacketDispatcher.wrapper.sendToServer(new NBTControlPacket(data, burner.xCoord, burner.yCoord, burner.zCoord));
		}

		if(guiLeft + 46 <= x && guiLeft + 46 + 30 > x && guiTop + 37 < y && guiTop + 37 + 14 >= y) {
			mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
			NBTTagCompound data = new NBTTagCompound();
			data.setBoolean("switch", false);
			PacketDispatcher.wrapper.sendToServer(new NBTControlPacket(data, burner.xCoord, burner.yCoord, burner.zCoord));
		}
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String name = this.burner.hasCustomInventoryName() ? this.burner.getInventoryName() : I18n.format(this.burner.getInventoryName());
		
		this.fontRendererObj.drawString(name, 70 - this.fontRendererObj.getStringWidth(name) / 2, 6, 0xffffff);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float interp, int x, int y) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		
		if(burner.liquidBurn) {
			drawTexturedModalRect(guiLeft + 16, guiTop + 17, 176, 52, 60, 54);
			drawTexturedModalRect(guiLeft + 79, guiTop + 17, 176, 106, 36, 54);
		}
		
		if(burner.isOn) {
			drawTexturedModalRect(guiLeft + 53, guiTop + 17, 196, 0, 16, 15);
		}
	}
}
