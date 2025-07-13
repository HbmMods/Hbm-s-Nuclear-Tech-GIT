package com.hbm.inventory.gui;

import java.util.Arrays;
import java.util.List;

import org.lwjgl.opengl.GL11;

import com.hbm.interfaces.Untested;
import com.hbm.inventory.container.ContainerMachineDiFurnaceRTG;
import com.hbm.items.machine.ItemRTGPellet;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.machine.TileEntityDiFurnaceRTG;
import com.hbm.util.i18n.I18nUtil;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.ForgeDirection;

public class GUIMachineDiFurnaceRTG extends GuiInfoContainer {
	public static ResourceLocation texture = new ResourceLocation(RefStrings.MODID, "textures/gui/processing/gui_rtg_difurnace.png");
	private TileEntityDiFurnaceRTG bFurnace;

	public GUIMachineDiFurnaceRTG(InventoryPlayer playerInv, TileEntityDiFurnaceRTG te) {
		super(new ContainerMachineDiFurnaceRTG(playerInv, te));
		bFurnace = te;
		this.xSize = 176;
		this.ySize = 166;
	}

	@Override
	@Untested
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);
		String[] descText = I18nUtil.resolveKeyArray("desc.gui.rtgBFurnace.desc");
		String[] heatText = I18nUtil.resolveKeyArray("desc.gui.rtg.heat", bFurnace.getPower());
		this.drawCustomInfoStat(mouseX, mouseY, guiLeft - 15, guiTop + 36 + 16, 16, 16, guiLeft - 8, guiTop + 36 + 16, descText);
		this.drawCustomInfoStat(mouseX, mouseY, guiLeft + 58, guiTop + 36, 18, 16, mouseX, mouseY, heatText);
		
		List<ItemRTGPellet> pellets = ItemRTGPellet.pelletList;
		String[] pelletText = new String[pellets.size() + 1];
		pelletText[0] = I18nUtil.resolveKey("desc.gui.rtg.pellets");
		
		for(int i = 0; i < pellets.size(); i++) {
			ItemRTGPellet pellet = pellets.get(i);
			pelletText[i + 1] = I18nUtil.resolveKey("desc.gui.rtg.pelletHeat", I18nUtil.resolveKey(pellet.getUnlocalizedName() + ".name"), pellet.getHeat());
		}
		
		this.drawCustomInfoStat(mouseX, mouseY, guiLeft - 15, guiTop + 36, 16, 16, guiLeft - 8, guiTop + 36 + 16, pelletText);

		if(this.mc.thePlayer.inventory.getItemStack() == null) {
			for(int i = 0; i < 2; i++) {
				Slot slot = (Slot) this.inventorySlots.inventorySlots.get(i);
				
				if(this.isMouseOverSlot(slot, mouseX, mouseY)) {
					
					String label = EnumChatFormatting.YELLOW + "Accepts items from: ";
					byte dir = i == 0 ? bFurnace.sideUpper : bFurnace.sideLower;
					label += ForgeDirection.getOrientation(dir);
					
					this.func_146283_a(Arrays.asList(new String[] { label }), mouseX, mouseY - (slot.getHasStack() ? 15 : 0));
					
					return;
				}
			}
		}
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int p_146979_1_, int p_146979_2_) {
		String name = this.bFurnace.hasCustomInventoryName() ? this.bFurnace.getInventoryName() : I18n.format(this.bFurnace.getInventoryName());

		this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		
		if(bFurnace.isInvalid()) {
			TileEntity te = bFurnace.getWorldObj().getTileEntity(bFurnace.xCoord, bFurnace.yCoord, bFurnace.zCoord);
			if(te instanceof TileEntityDiFurnaceRTG) {
				bFurnace = (TileEntityDiFurnaceRTG) te;
			}
		}

		if(bFurnace.getPower() >= 15)
			drawTexturedModalRect(guiLeft + 58, guiTop + 36, 176, 31, 18, 16);

		int p = bFurnace.getDiFurnaceProgressScaled(24);
		drawTexturedModalRect(guiLeft + 101, guiTop + 35, 176, 14, p + 1, 17);
		this.drawInfoPanel(guiLeft - 15, guiTop + 36, 16, 16, 2);
		this.drawInfoPanel(guiLeft - 15, guiTop + 36 + 16, 16, 16, 3);
	}

}
