package com.hbm.inventory.gui;

import java.util.Arrays;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerDiFurnace;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.machine.TileEntityDiFurnace;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.ForgeDirection;

public class GUIDiFurnace extends GuiContainer {

	public static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/GUIDiFurnace.png");
	private TileEntityDiFurnace diFurnace;

	public GUIDiFurnace(InventoryPlayer invPlayer, TileEntityDiFurnace tedf) {
		super(new ContainerDiFurnace(invPlayer, tedf));
		diFurnace = tedf;

		this.xSize = 176;
		this.ySize = 166;
	}
	
	@Override
	public void drawScreen(int x, int y, float interp) {
		super.drawScreen(x, y, interp);

		if(this.mc.thePlayer.inventory.getItemStack() == null) {
			for(int i = 0; i < 3; i++) {
				Slot slot = (Slot) this.inventorySlots.inventorySlots.get(i);
				
				if(this.isMouseOverSlot(slot, x, y)) {
					
					String label = EnumChatFormatting.YELLOW + "Accepts items from: ";
					byte dir = i == 0 ? diFurnace.sideUpper : i == 1 ? diFurnace.sideLower : diFurnace.sideFuel;
					label += ForgeDirection.getOrientation(dir);
					
					this.func_146283_a(Arrays.asList(new String[] { label }), x, y - (slot.getHasStack() ? 15 : 0));
					
					return;
				}
			}
		}
	}
	
	protected boolean isMouseOverSlot(Slot slot, int x, int y) {
		return this.func_146978_c(slot.xDisplayPosition, slot.yDisplayPosition, 16, 16, x, y);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String name = this.diFurnace.hasCustomInventoryName() ? this.diFurnace.getInventoryName() : I18n.format(this.diFurnace.getInventoryName());

		this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

		if(diFurnace.isInvalid() && diFurnace.getWorldObj().getTileEntity(diFurnace.xCoord, diFurnace.yCoord, diFurnace.zCoord) instanceof TileEntityDiFurnace)
			diFurnace = (TileEntityDiFurnace) diFurnace.getWorldObj().getTileEntity(diFurnace.xCoord, diFurnace.yCoord, diFurnace.zCoord);

		if(diFurnace.hasPower()) {
			int i1 = diFurnace.getPowerRemainingScaled(52);
			drawTexturedModalRect(guiLeft + 44, guiTop + 70 - i1, 201, 53 - i1, 16, i1);
		}

		int j1 = diFurnace.getDiFurnaceProgressScaled(24);
		drawTexturedModalRect(guiLeft + 101, guiTop + 35, 176, 14, j1 + 1, 17);

		if(diFurnace.hasPower() && diFurnace.canProcess()) {
			drawTexturedModalRect(guiLeft + 63, guiTop + 37, 176, 0, 14, 14);
		}
	}

}
