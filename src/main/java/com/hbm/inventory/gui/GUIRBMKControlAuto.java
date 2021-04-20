package com.hbm.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerRBMKControlAuto;
import com.hbm.lib.RefStrings;
import com.hbm.packet.NBTControlPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.tileentity.machine.rbmk.TileEntityRBMKControlAuto;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

public class GUIRBMKControlAuto extends GuiInfoContainer {
	
	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/reactors/gui_rbmk_control_auto.png");
	private TileEntityRBMKControlAuto rod;

	public GUIRBMKControlAuto(InventoryPlayer invPlayer, TileEntityRBMKControlAuto tedf) {
		super(new ContainerRBMKControlAuto(invPlayer, tedf));
		rod = tedf;
		
		this.xSize = 176;
		this.ySize = 186;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);
		
		this.drawCustomInfoStat(mouseX, mouseY, guiLeft + 124, guiTop + 29, 16, 56, mouseX, mouseY, new String[]{ (int)(rod.level * 100) + "%" } );
	}

	@Override
	protected void mouseClicked(int x, int y, int i) {
		super.mouseClicked(x, y, i);
		
		/*for(int k = 0; k < 5; k++) {

			//manual rod control
			if(guiLeft + 118 <= x && guiLeft + 118 + 30 > x && guiTop + 26 + k * 11 < y && guiTop + 26 + 10 + k * 11 >= y) {
	
				mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
				NBTTagCompound data = new NBTTagCompound();
				data.setDouble("level", 1.0D - (k * 0.25D));
				PacketDispatcher.wrapper.sendToServer(new NBTControlPacket(data, rod.xCoord, rod.yCoord, rod.zCoord));
			}

			//color groups
			if(guiLeft + 28 <= x && guiLeft + 28 + 12 > x && guiTop + 26 + k * 11 < y && guiTop + 26 + 10 + k * 11 >= y) {
	
				mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
				NBTTagCompound data = new NBTTagCompound();
				data.setInteger("color", k);
				PacketDispatcher.wrapper.sendToServer(new NBTControlPacket(data, rod.xCoord, rod.yCoord, rod.zCoord));
			}
		}*/
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String name = this.rod.hasCustomInventoryName() ? this.rod.getInventoryName() : I18n.format(this.rod.getInventoryName());
		
		this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		
		int height = (int)(56 * (1D - rod.level));
		
		if(height > 0)
			drawTexturedModalRect(guiLeft + 124, guiTop + 29, 176, 56 - height, 8, height);
		
		drawTexturedModalRect(guiLeft + 59, guiTop + 27, 184, 0, 26, 19);
	}
}
