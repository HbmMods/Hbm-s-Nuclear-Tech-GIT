package com.hbm.inventory.gui;

import java.awt.Color;
import java.util.List;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerMachineRotaryFurnace;
import com.hbm.inventory.material.Mats;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.machine.TileEntityMachineRotaryFurnace;
import com.hbm.util.i18n.I18nUtil;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;

public class GUIMachineRotaryFurnace extends GuiInfoContainer {

	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/processing/gui_rotary_furnace.png");
	private TileEntityMachineRotaryFurnace furnace;

	public GUIMachineRotaryFurnace(InventoryPlayer playerInv, TileEntityMachineRotaryFurnace tile) {
		super(new ContainerMachineRotaryFurnace(playerInv, tile));
		
		this.furnace = tile;
		this.xSize = 176;
		this.ySize = 186;
	}
	
	@Override
	public void drawScreen(int x, int y, float interp) {
		super.drawScreen(x, y, interp);

		furnace.tanks[0].renderTankInfo(this, x, y, guiLeft + 8, guiTop + 36, 52, 16);
		furnace.tanks[1].renderTankInfo(this, x, y, guiLeft + 134, guiTop + 18, 16, 52);
		furnace.tanks[2].renderTankInfo(this, x, y, guiLeft + 152, guiTop + 18, 16, 52);
		
		Slot slot = (Slot) this.inventorySlots.inventorySlots.get(4);
		if(this.isMouseOverSlot(slot, x, y) && !slot.getHasStack()) {
			List<String> bonuses = this.furnace.burnModule.getDesc();
			if(!bonuses.isEmpty()) this.func_146283_a(bonuses, x, y);
		}
		
		if(furnace.output == null) {
			this.drawCustomInfoStat(x, y, guiLeft + 98, guiTop + 18, 16, 52, x, y, EnumChatFormatting.RED + "Empty");
		} else {
			this.drawCustomInfoStat(x, y, guiLeft + 98, guiTop + 18, 16, 52, x, y,EnumChatFormatting.YELLOW +
					I18nUtil.resolveKey(furnace.output.material.getUnlocalizedName()) + ": " + Mats.formatAmount(furnace.output.amount, Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)));
		}
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String name = this.furnace.hasCustomInventoryName() ? this.furnace.getInventoryName() : I18n.format(this.furnace.getInventoryName());
		this.fontRendererObj.drawString(name, (this.xSize - 54) / 2 - this.fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float interp, int x, int y) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		
		int p = (int) Math.ceil(furnace.progress * 33);
		drawTexturedModalRect(guiLeft + 63, guiTop + 30, 176, 0, p, 10);
		
		if(furnace.maxBurnTime > 0) {
			int b = furnace.burnTime * 14 / furnace.maxBurnTime;
			drawTexturedModalRect(guiLeft + 26, guiTop + 69 - b, 176, 24 - b, 14, b);
		}
		
		if(furnace.output != null) {
			
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
			
			int hex = furnace.output.material.moltenColor;
			int amount = furnace.output.amount * 52 / furnace.maxOutput;
			Color color = new Color(hex);
			GL11.glColor3f(color.getRed() / 255F, color.getGreen() / 255F, color.getBlue() / 255F);
			drawTexturedModalRect(guiLeft + 98, guiTop + 70 - amount, 176, 76 - amount, 16, amount);
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glColor4f(1F, 1F, 1F, 0.3F);
			drawTexturedModalRect(guiLeft + 98, guiTop + 70 - amount, 176, 76 - amount, 16, amount);
			GL11.glDisable(GL11.GL_BLEND);

			OpenGlHelper.glBlendFunc(770, 771, 1, 0);
			GL11.glColor3f(255, 255, 255);
		}

		furnace.tanks[0].renderTank(guiLeft + 8, guiTop + 52, this.zLevel, 52, 16, 1);
		furnace.tanks[1].renderTank(guiLeft + 134, guiTop + 70, this.zLevel, 16, 52);
		furnace.tanks[2].renderTank(guiLeft + 152, guiTop + 70, this.zLevel, 16, 52);
	}
}
