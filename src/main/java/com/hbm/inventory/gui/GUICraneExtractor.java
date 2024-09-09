package com.hbm.inventory.gui;

import java.util.Arrays;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerCraneExtractor;
import com.hbm.lib.RefStrings;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.toserver.NBTControlPacket;
import com.hbm.tileentity.network.TileEntityCraneExtractor;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;

public class GUICraneExtractor extends GuiInfoContainer {
	
	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/storage/gui_crane_ejector.png");
	private TileEntityCraneExtractor ejector;

	public GUICraneExtractor(InventoryPlayer invPlayer, TileEntityCraneExtractor tedf) {
		super(new ContainerCraneExtractor(invPlayer, tedf));
		ejector = tedf;
		
		this.xSize = 176;
		this.ySize = 185;
	}
	
	@Override
	public void drawScreen(int x, int y, float interp) {
		super.drawScreen(x, y, interp);

		if(this.mc.thePlayer.inventory.getItemStack() == null) {
			for(int i = 0; i < 9; ++i) {
				Slot slot = (Slot) this.inventorySlots.inventorySlots.get(i);
	
				if(this.isMouseOverSlot(slot, x, y) && ejector.matcher.modes[i] != null) {
					
					String label = EnumChatFormatting.YELLOW + "";
					
					switch(ejector.matcher.modes[i]) {
					case "exact": label += "Item and meta match"; break;
					case "wildcard": label += "Item matches"; break;
					default: label += "Ore dict key matches: " + ejector.matcher.modes[i]; break;
					}
					
					this.func_146283_a(Arrays.asList(new String[] { EnumChatFormatting.RED + "Right click to change", label }), x, y - 30);
				}
			}
		}
	}

	@Override
	protected void mouseClicked(int x, int y, int i) {
		super.mouseClicked(x, y, i);

		if(guiLeft + 128 <= x && guiLeft + 128 + 14 > x && guiTop + 30 < y && guiTop + 30 + 26 >= y) {

			mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
			NBTTagCompound data = new NBTTagCompound();
			data.setBoolean("whitelist", true);
			PacketDispatcher.wrapper.sendToServer(new NBTControlPacket(data, ejector.xCoord, ejector.yCoord, ejector.zCoord));
		}
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String name = this.ejector.hasCustomInventoryName() ? this.ejector.getInventoryName() : I18n.format(this.ejector.getInventoryName());
		this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		
		if(ejector.isWhitelist) {
			drawTexturedModalRect(guiLeft + 139, guiTop + 33, 176, 0, 3, 6);
		} else {
			drawTexturedModalRect(guiLeft + 139, guiTop + 47, 176, 0, 3, 6);
		}
	}
}
