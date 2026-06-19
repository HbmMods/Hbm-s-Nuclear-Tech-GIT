package com.hbm.inventory.gui;

import java.util.List;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import static com.hbm.inventory.gui.element.GUIElements.*;
import com.hbm.inventory.container.ContainerPneumoStorageAccess;
import com.hbm.inventory.container.ContainerPneumoStorageAccess.SlotPneumo;
import com.hbm.inventory.gui.element.GUIElements;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.network.pneumatic.TileEntityPneumoStorageAccess;
import com.hbm.util.BobMathUtil;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

public class GUIPneumoStorageAccess extends GuiInfoContainer {

	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/storage/gui_pneumatic_access.png");
	protected TileEntityPneumoStorageAccess access;
	protected ContainerPneumoStorageAccess container;
	protected GuiTextField search;
	
	protected int scrollIndex = 0;
	protected int scrollBounds = 1;
	protected boolean wasClicking = false;
	protected boolean draggingScroll = false;
	protected boolean wasMouseinGUI = false;

	protected static int sorting = 0;
	protected static boolean startFocussed = false;

	public GUIPneumoStorageAccess(InventoryPlayer invPlayer, TileEntityPneumoStorageAccess access) {
		super(new ContainerPneumoStorageAccess(invPlayer, access));
		this.container = (ContainerPneumoStorageAccess) this.inventorySlots;
		this.access = access;
		
		this.xSize = 176 + 34;
		this.ySize = 251;
	}

	@Override
	public void initGui() {
		super.initGui();
		
		Keyboard.enableRepeatEvents(true);
		search = new GuiTextField(this.fontRendererObj, guiLeft + 45 + 34, guiTop + 127, 86, 12);
		search.setTextColor(0xffffff);
		search.setDisabledTextColour(0xa0a0a0);
		search.setEnableBackgroundDrawing(false);
		search.setMaxStringLength(50);
		search.setText("");
		
		if(this.startFocussed) search.setFocused(true);
	}
	
	@Override
	public void drawScreen(int x, int y, float interp) {
		
		this.wasMouseinGUI = this.checkClick(x, y, 0, 0, xSize, ySize);
		
		this.scrollBounds = (int) Math.ceil(container.getStackCount() / 8D - 6D);
		if(this.scrollBounds < 1) this.scrollBounds = 1;
		if(this.scrollIndex < 0) this.setScroll(0);
		if(this.scrollIndex > scrollBounds) this.setScroll(scrollBounds);

		boolean isClicking = Mouse.isButtonDown(0);
		if(!isClicking) this.draggingScroll = false;
		
		if(!wasClicking && isClicking && guiLeft + 153 + 34 <= x && guiLeft + 153 + 34 + 14 > x && guiTop + 16 < y && guiTop + 16 + 108 >= y) {
			draggingScroll = true;
		}
		
		if(draggingScroll) {
			int range = 92; // 106 scroll bar size, -7 pixels on top and bottom
			int sY = MathHelper.clamp_int(y - guiTop - 24, 0, 92);
			double scrollFrac = (double) sY / (double) range;
			int row = (int) Math.round(scrollBounds * scrollFrac);
			this.setScroll(row);
		}
		
		this.wasClicking = isClicking;
		
		super.drawScreen(x, y, interp);

		//TODO localization
		this.drawCustomInfoStat(x, y, guiLeft + 7, guiTop + 7, 18, 18, x, y, "Sorting: " + EnumChatFormatting.YELLOW + "Amount");
		this.drawCustomInfoStat(x, y, guiLeft + 7, guiTop + 25, 18, 18, x, y, "Sorting: " + EnumChatFormatting.YELLOW + "Item ID");
		this.drawCustomInfoStat(x, y, guiLeft + 7, guiTop + 43, 18, 18, x, y, "Sorting: " + EnumChatFormatting.YELLOW + "Name");
		this.drawCustomInfoStat(x, y, guiLeft + 7, guiTop + 61, 18, 18, x, y, "Sorting: " + EnumChatFormatting.YELLOW + "Internal Name");
		
		this.drawCustomInfoStat(x, y, guiLeft + 7, guiTop + 79, 18, 18, x, y, "Focus search by default: " + (this.startFocussed ? EnumChatFormatting.GREEN + "ON" : EnumChatFormatting.RED + "OFF"));
		this.drawCustomInfoStat(x, y, guiLeft + 7, guiTop + 97, 18, 18, x, y, "Inlude tooltips in search: " + (this.container.detailedSearch ? EnumChatFormatting.GREEN + "ON" : EnumChatFormatting.RED + "OFF"));
	}

	@Override
	protected void mouseClicked(int x, int y, int i) {
		super.mouseClicked(x, y, i);

		if(this.checkClick(x, y, 7, 7, 18, 18)) { this.click(); this.sorting = 0; this.scrollIndex = 0; this.container.setSorter(this.container.SORT_BY_STACK_SIZE); }
		if(this.checkClick(x, y, 7, 25, 18, 18)) { this.click(); this.sorting = 1; this.scrollIndex = 0; this.container.setSorter(this.container.SORT_BY_ID); }
		if(this.checkClick(x, y, 7, 43, 18, 18)) { this.click(); this.sorting = 2; this.scrollIndex = 0; this.container.setSorter(this.container.SORT_BY_LOCALIZED); }
		if(this.checkClick(x, y, 7, 61, 18, 18)) { this.click(); this.sorting = 3; this.scrollIndex = 0; this.container.setSorter(this.container.SORT_BY_INTERNAL); }

		if(this.checkClick(x, y, 7, 79, 18, 18)) { this.click(); this.startFocussed = !this.startFocussed; }
		if(this.checkClick(x, y, 7, 97, 18, 18)) { this.click(); this.container.detailedSearch = !this.container.detailedSearch; container.setSearchString(search.getText()); }
		
		search.mouseClicked(x, y, i);
	}

	@Override
	public void handleMouseInput() {
		
		int scrollDir = Mouse.getEventDWheel();

		if(scrollDir != 0 && wasMouseinGUI) {

			if(scrollDir > 0) scrollDir = 1;
			if(scrollDir < 0) scrollDir = -1;
			this.setScroll(this.getScroll() - scrollDir);
			return;
		}
		
		super.handleMouseInput();
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String name = "container.pneumoStorageAccess";
		
		this.fontRendererObj.drawString(name, 34 + 176 / 2 - this.fontRendererObj.getStringWidth(name) / 2, 5, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 34 + 8, this.ySize - 96 + 2, 4210752);
		
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
		drawTexturedModalRect(guiLeft + 34, guiTop, 0, 0, 176, ySize);
		
		drawTexturedModalRect(guiLeft, guiTop, 176, 15, 32, 122);

		drawTexturedModalRect(guiLeft + 7, guiTop + 7 + this.sorting * 18, 208, 0, 18, 18);
		if(this.startFocussed) drawTexturedModalRect(guiLeft + 7, guiTop + 79, 208, 18, 18, 18);
		if(this.container.detailedSearch) drawTexturedModalRect(guiLeft + 7, guiTop + 97, 208, 18, 18, 18);
		
		drawTexturedModalRect(guiLeft + 34 + getScrollBarXPos(), guiTop + getScrollBarYPos(), draggingScroll ? 188 : 176, 0, 12, 15);
		
		GL11.glPushMatrix();
		GL11.glTranslated(0, 2, 0);
		search.drawTextBox();
		GL11.glPopMatrix();
	}

	public int getScrollBarXPos() {
		return 154;
	}
	
	public int getScrollBarYPos() {
		int scrollArea = 106 - 15; // bar height minus the scroll knob's height
		double scrollProgress = (double) container.listingStart / (double) scrollBounds;
		if(scrollProgress > 1) {
			scrollProgress = 1;
			this.setScroll(scrollBounds);
			refreshContainer();
		}
		int scrollYPos = 17 + (int) (scrollProgress * scrollArea);
		return scrollYPos;
	}
	
	public int getScroll() {
		return MathHelper.clamp_int(scrollIndex, 0, scrollBounds);
	}
	
	public void setScroll(int scroll) {
		int prevScroll = getScroll();
		this.scrollIndex = MathHelper.clamp_int(scroll, 0, scrollBounds);
		if(prevScroll != this.scrollIndex) refreshContainer();
	}
	
	public void refreshContainer() {
		this.mc.playerController.windowClick(this.inventorySlots.windowId, ContainerPneumoStorageAccess.SLOT_CLICK_ID_REFRESH, 0, this.scrollIndex, this.mc.thePlayer);
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

	@Override
	protected void keyTyped(char c, int b) {
		
		if(search.textboxKeyTyped(c, b)) {
			this.scrollIndex = 0;
			container.setSearchString(search.getText());
			return;
		}
		
		super.keyTyped(c, b);
	}
	
	@Override
	public void onGuiClosed() {
		super.onGuiClosed();
		Keyboard.enableRepeatEvents(false);
	}
}
