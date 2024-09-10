package com.hbm.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerRBMKGeneric;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.lib.RefStrings;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.toserver.NBTControlPacket;
import com.hbm.tileentity.machine.rbmk.TileEntityRBMKBoiler;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

public class GUIRBMKBoiler extends GuiInfoContainer {
	
	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/reactors/gui_rbmk_boiler.png");
	private TileEntityRBMKBoiler boiler;

	public GUIRBMKBoiler(InventoryPlayer invPlayer, TileEntityRBMKBoiler tile) {
		super(new ContainerRBMKGeneric(invPlayer));
		boiler = tile;
		
		this.xSize = 176;
		this.ySize = 186;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);

		boiler.feed.renderTankInfo(this, mouseX, mouseY, guiLeft + 126, guiTop + 24, 16, 56);
		boiler.steam.renderTankInfo(this, mouseX, mouseY, guiLeft + 89, guiTop + 39, 8, 28);
	}

	@Override
	protected void mouseClicked(int x, int y, int i) {
		super.mouseClicked(x, y, i);

		if(guiLeft + 33 <= x && guiLeft + 33 + 20 > x && guiTop + 21 < y && guiTop + 21 + 64 >= y) {

			mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
			NBTTagCompound data = new NBTTagCompound();
			data.setBoolean("compression", true); //we only need to send on bit, so boolean it is
			PacketDispatcher.wrapper.sendToServer(new NBTControlPacket(data, boiler.xCoord, boiler.yCoord, boiler.zCoord));
		}
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String name = this.boiler.hasCustomInventoryName() ? this.boiler.getInventoryName() : I18n.format(this.boiler.getInventoryName());
		
		this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		
		int i = boiler.feed.getFill() * 58 / boiler.feed.getMaxFill();
		drawTexturedModalRect(guiLeft + 126, guiTop + 82 - i, 176, 58 - i, 14, i);
		
		int j = boiler.steam.getFill() * 22 / boiler.steam.getMaxFill();

		if(j > 0) j++;
		if(j > 22) j++;
		
		drawTexturedModalRect(guiLeft + 91, guiTop + 65 - j, 190, 24 - j, 4, j);
		
		FluidType type = boiler.steam.getTankType();
		if(type == Fluids.STEAM) drawTexturedModalRect(guiLeft + 36, guiTop + 24, 194, 0, 14, 58);
		if(type == Fluids.HOTSTEAM) drawTexturedModalRect(guiLeft + 36, guiTop + 24, 208, 0, 14, 58);
		if(type == Fluids.SUPERHOTSTEAM) drawTexturedModalRect(guiLeft + 36, guiTop + 24, 222, 0, 14, 58);
		if(type == Fluids.ULTRAHOTSTEAM) drawTexturedModalRect(guiLeft + 36, guiTop + 24, 236, 0, 14, 58);
	}
}
