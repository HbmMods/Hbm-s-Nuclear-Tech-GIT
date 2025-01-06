package com.hbm.inventory.gui;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerMachineArcFurnaceLarge;
import com.hbm.inventory.material.Mats;
import com.hbm.inventory.material.Mats.MaterialStack;
import com.hbm.lib.RefStrings;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.toserver.NBTControlPacket;
import com.hbm.tileentity.machine.TileEntityMachineArcFurnaceLarge;
import com.hbm.util.I18nUtil;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;

public class GUIMachineArcFurnaceLarge extends GuiInfoContainer {

	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/processing/gui_arc_furnace.png");
	private TileEntityMachineArcFurnaceLarge arc;

	public GUIMachineArcFurnaceLarge(InventoryPlayer invPlayer, TileEntityMachineArcFurnaceLarge arc) {
		super(new ContainerMachineArcFurnaceLarge(invPlayer, arc));
		this.arc = arc;
		
		this.xSize = 176;
		this.ySize = 240;
	}
	
	@Override
	public void drawScreen(int x, int y, float interp) {
		super.drawScreen(x, y, interp);

		drawStackInfo(arc.liquids, x, y, 152, 36);
		
		this.drawElectricityInfo(this, x, y, guiLeft + 8, guiTop + 36, 7, 70, arc.getPower(), arc.getMaxPower());
	}
	
	@Override
	protected void mouseClicked(int x, int y, int k) {
		super.mouseClicked(x, y, k);
		
		if(this.checkClick(x, y, 151, 17, 18, 18)) {
			mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
			NBTTagCompound data = new NBTTagCompound();
			data.setBoolean("liquid", true);
			PacketDispatcher.wrapper.sendToServer(new NBTControlPacket(data, arc.xCoord, arc.yCoord, arc.zCoord));
		}
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String name = this.arc.hasCustomInventoryName() ? this.arc.getInventoryName() : I18n.format(this.arc.getInventoryName());
		this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, 6, 0xffffff);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float interp, int x, int y) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

		if(arc.liquidMode) drawTexturedModalRect(guiLeft + 151, guiTop + 17, 190, 18, 18, 18);
		if(arc.isProgressing) drawTexturedModalRect(guiLeft + 7, guiTop + 17, 190, 0, 18, 18);
		
		int p = (int) (arc.power * 70 / arc.maxPower);
		drawTexturedModalRect(guiLeft + 8, guiTop + 106 - p, 176, 70 - p, 7, p);
		
		int o = (int) (arc.progress * 70);
		drawTexturedModalRect(guiLeft + 17, guiTop + 106 - o, 183, 70 - o, 7, o);
		
		drawStack(arc.liquids, arc.maxLiquid, 152, 106);
	}
	
	protected void drawStackInfo(List<MaterialStack> stack, int mouseX, int mouseY, int x, int y) {
		List<String> list = new ArrayList();
		if(stack.isEmpty()) list.add(EnumChatFormatting.RED + "Empty");
		for(MaterialStack sta : stack) list.add(EnumChatFormatting.YELLOW + I18nUtil.resolveKey(sta.material.getUnlocalizedName()) + ": " + Mats.formatAmount(sta.amount, Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)));
		this.drawCustomInfoStat(mouseX, mouseY, guiLeft + x, guiTop + y, 16, 70, mouseX, mouseY, list);
	}
	
	protected void drawStack(List<MaterialStack> stack, int capacity, int x, int y) {
		
		if(stack.isEmpty()) return;
		
		int lastHeight = 0;
		int lastQuant = 0;
		
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
		
		for(MaterialStack sta : stack) {
			
			int targetHeight = (lastQuant + sta.amount) * 70 / capacity;
			
			if(lastHeight == targetHeight) continue; //skip draw calls that would be 0 pixels high
			
			int hex = sta.material.moltenColor;
			//hex = 0xC18336;
			Color color = new Color(hex);
			GL11.glColor3f(color.getRed() / 255F, color.getGreen() / 255F, color.getBlue() / 255F);
			drawTexturedModalRect(guiLeft + x, guiTop + y - targetHeight, 208, 70 - targetHeight, 16, targetHeight - lastHeight);
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glColor4f(1F, 1F, 1F, 0.3F);
			drawTexturedModalRect(guiLeft + x, guiTop + y - targetHeight, 208, 70 - targetHeight, 16, targetHeight - lastHeight);
			GL11.glDisable(GL11.GL_BLEND);
			
			lastQuant += sta.amount;
			lastHeight = targetHeight;
		}

		OpenGlHelper.glBlendFunc(770, 771, 1, 0);
		GL11.glColor3f(255, 255, 255);
	}
}
