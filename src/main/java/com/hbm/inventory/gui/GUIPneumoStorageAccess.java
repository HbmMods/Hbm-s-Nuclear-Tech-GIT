package com.hbm.inventory.gui;

import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import static com.hbm.inventory.gui.element.GUIElements.*;
import com.hbm.inventory.container.ContainerPneumoStorageAccess;
import com.hbm.inventory.container.ContainerPneumoStorageAccess.SlotPneumo;
import com.hbm.inventory.gui.element.GUIElements;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.network.pneumatic.TileEntityPneumoStorageAccess;
import com.hbm.util.BobMathUtil;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;

public class GUIPneumoStorageAccess extends GuiInfoContainer {

	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/storage/gui_pneumatic_access.png");
	protected TileEntityPneumoStorageAccess access;

	public GUIPneumoStorageAccess(InventoryPlayer invPlayer, TileEntityPneumoStorageAccess access) {
		super(new ContainerPneumoStorageAccess(invPlayer, access));
		this.access = access;
		
		this.xSize = 176;
		this.ySize = 251;
	}
	
	@Override
	public void drawScreen(int x, int y, float interp) {
		super.drawScreen(x, y, interp);
	}

	@Override
	protected void mouseClicked(int x, int y, int i) {
		super.mouseClicked(x, y, i);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String name = "container.pneumoStorageAccess";
		
		this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, 5, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
		
		GL11.glPushMatrix();
		RenderHelper.disableStandardItemLighting();
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		double scale = 0.5D;
		GL11.glScaled(scale, scale, 1);
		
		for(Object o : this.inventorySlots.inventorySlots) {
			if(!(o instanceof SlotPneumo)) continue;
			SlotPneumo pneumoSlot = (SlotPneumo) o;
			if(pneumoSlot.getHasStack()) {
				String label = BobMathUtil.getShortNumber(pneumoSlot.amount);
				int ix = (int) ((pneumoSlot.xDisplayPosition + 16) / scale) - this.fontRendererObj.getStringWidth(label);
				int iy = (int) ((pneumoSlot.yDisplayPosition + 16) / scale) - this.fontRendererObj.FONT_HEIGHT;
				this.fontRendererObj.drawStringWithShadow(label, ix, iy, -1);
			}
		}
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glPopMatrix();
		
		RenderHelper.enableGUIStandardItemLighting();
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
	}

	@Override
	protected void renderToolTip(ItemStack stack, int x, int y) {
		List list = stack.getTooltip(this.mc.thePlayer, this.mc.gameSettings.advancedItemTooltips);

		for(int line = 0; line < list.size(); ++line) {
			if(line == 0) {
				list.set(line, stack.getRarity().rarityColor + (String) list.get(line));
			} else {
				list.set(line, EnumChatFormatting.GRAY + (String) list.get(line));
			}
		}

		FontRenderer font = stack.getItem().getFontRenderer(stack);
		if(font == null) font = this.fontRendererObj;
		GUIElements.drawHoveringText(list, x, y, font, itemRender, width, height, STANDARD_HEADER_OFFSET, STANDARD_LINE_DIST, STANDARD_COLOR_BACKGROUND, STANDARD_COLOR_BACKGROUND, 0xD57C4F, 0xAB4223);
	}
}
