package com.hbm.inventory.gui.element;

import static codechicken.lib.gui.GuiDraw.drawTexturedModalRect;

import java.io.IOException;
import java.util.List;
import java.util.function.Function;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

public class GUIScrollingList {
	private static final long DOUBLE_CLICK_TIME = 250L;

	private final int left;
	private final int top;
	private final int x;
	private final int y;
	private final int offsetEntryX;
	private final int offsetEntryY;
	private final int width;
	private final int height;
	private final int length;
	private final int scrollerX;
	private final int scrollerY;
	private final int scrollerWidth;
	private final int scrollerHeight;
	private final int scrollBarHeight;
	private final int borderSize;
	private final int entryHeight;
	private final float entryScale;
	private final List<String> entries;
	private final Function<String, String> displayMapper;
	private final FontRenderer fontRenderer;
	private final ResourceLocation texture;

	private int calculatedScrollerY;
	private int scrollDistance = 0;
	private float currentScroll = 0F;
	private float targetScroll = 0F;
	public int selectedSlot = -1;
	private boolean isHovered;
	private boolean isHoveredViewBox;
	private boolean draggingScroller;
	private int lastClickedSlot;
	private long lastClickTime = 0L;

	public GUIScrollingList(FontRenderer fontRenderer, List<String> entries, Function<String, String> displayMapper, ResourceLocation texture, int left, int top, int x, int y, int offsetEntryX, int offsetEntryY, int width, int height, int length, int scrollerX, int scrollerY, int scrollerWidth, int scrollerHeight, int scrollBarHeight, int borderSize, int entryHeight, float entryScale) {
		this.fontRenderer = fontRenderer;
		this.entries = entries;
		this.displayMapper = displayMapper;
		this.texture = texture;
		this.left = left;
		this.top = top;
		this.x = x;
		this.y = y;
		this.offsetEntryX = offsetEntryX;
		this.offsetEntryY = offsetEntryY;
		this.width = width;
		this.height = height;
		this.length = length;
		this.scrollerX = scrollerX;
		this.scrollerY = scrollerY;
		this.scrollerWidth = scrollerWidth;
		this.scrollerHeight = scrollerHeight;
		this.scrollBarHeight = scrollBarHeight;
		this.borderSize = borderSize;
		this.entryHeight = entryHeight;
		this.entryScale = entryScale;
	}

	public GUIScrollingList(FontRenderer fontRenderer, List<String> entries, ResourceLocation texture, int left, int top, int x, int y, int offsetSlotX, int offsetEntryY, int width, int height, int length, int scrollerX, int scrollerY, int scrollerWidth, int scrollerHeight, int scrollBarHeight, int borderSize, int entryHeight, float entryScale) {
		this(fontRenderer, entries, Object::toString, texture, left, top, x, y, offsetSlotX, offsetEntryY, width, height, length, scrollerX, scrollerY, scrollerWidth, scrollerHeight, scrollBarHeight, borderSize, entryHeight, entryScale);
	}

	public void drawScreen(int mouseX, int mouseY) {
		isHovered = mouseX >= left + x && mouseX <= left + scrollerX + scrollerWidth + borderSize && mouseY >= top + y && mouseY <= top + y + scrollBarHeight + borderSize * 2;
		isHoveredViewBox = mouseX >= left + x && mouseX <= left + x + width && mouseY >= top + y && mouseY <= top + y + height;

		if(draggingScroller) {
			handleScroller(mouseY);
		}

		handleDrawList();
		drawScroller();
	}

	private void drawSlot(int slot, int index) {
		int slotX = (int) ((left + x + offsetEntryX) / entryScale);
		int slotY = (int) ((top + y + offsetEntryY + index * entryHeight) / entryScale);

		String displayName = displayMapper.apply(entries.get(slot));

		while (fontRenderer.getStringWidth(displayName + "...") > (int) (width / entryScale) && displayName.length() > 1) {
			displayName = displayName.substring(0, displayName.length() - 1);
		}

		if(!displayName.equals(displayMapper.apply(entries.get(slot)))) {
			displayName += "...";
		}

		boolean isSelectedSlot = selectedSlot == slot;

		fontRenderer.drawString(displayName, slotX, slotY, isSelectedSlot ? -1 : 0);
	}

	private void drawScroller() {
		int maxScroll = Math.max(0, entries.size() - length);

		currentScroll += targetScroll - currentScroll;
		scrollDistance = Math.round(currentScroll);

		int calculatedY = scrollerY;

		if(maxScroll > 0) {
			float progress = currentScroll / maxScroll;
			calculatedY += Math.round(progress * (scrollBarHeight - scrollerHeight));
		}

		this.calculatedScrollerY = calculatedY;

		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		drawTexturedModalRect(left + scrollerX, top + calculatedY, 20, 124, scrollerWidth, scrollerHeight);
	}

	private void handleDrawList() {
		GL11.glPushMatrix();
		GL11.glScalef(entryScale, entryScale, 1.0F);

		for(int i = 0; i < length; i++) {
			int slot = scrollDistance + i;

			if(slot >= entries.size()) {
				break;
			}

			drawSlot(slot, i);
		}

		GL11.glPopMatrix();
	}

	private void handleScroller(int mouseY) {
		int maxScroll = Math.max(0, entries.size() - length);

		if(maxScroll == 0) {
			return;
		}

		int mouse = mouseY - top - scrollerY - scrollerHeight / 2;

		float progress = (float) mouse / (scrollBarHeight - scrollerHeight);
		progress = MathHelper.clamp_float(progress, 0F, 1F);

		targetScroll = progress * maxScroll;
	}

	private boolean handleElementSelection(int mouseX, int mouseY) {
		if(!isHoveredViewBox) {
			return false;
		}

		int mouseListY = mouseY - top - y - offsetEntryY;

		if(mouseListY < 0) {
			return false;
		}

		int visibleSlot = mouseListY / entryHeight;

		if(mouseX < left + x || mouseX >= left + x + width || visibleSlot < 0 || visibleSlot >= length) {
			return false;
		}

		int clickedSlot = scrollDistance + visibleSlot;

		if(clickedSlot < 0 || clickedSlot >= entries.size()) {
			return false;
		}

		long currentTime = System.currentTimeMillis();
		boolean doubleClick = clickedSlot == lastClickedSlot && currentTime - lastClickTime <= DOUBLE_CLICK_TIME;

		selectedSlot = clickedSlot;
		lastClickedSlot = clickedSlot;
		lastClickTime = currentTime;

		return doubleClick;
	}

	public void resetScroll() {
		scrollDistance = 0;
		currentScroll = 0;
		targetScroll = 0;
	}

	public boolean mouseClicked(int mouseX, int mouseY, int mouseButton) {
		if(mouseButton == 0 && mouseX >= left + scrollerX && mouseX <= left + scrollerX + scrollerWidth && mouseY >= top + calculatedScrollerY && mouseY <= top + calculatedScrollerY + scrollerHeight) {
			draggingScroller = true;
		}

		return handleElementSelection(mouseX, mouseY);
	}

	public void mouseReleased() {
		draggingScroller = false;
	}

	public void handleMouseInput() {
		if(!isHovered) {
			return;
		}

		int maxScroll = Math.max(0, entries.size() - length);
		int wheel = Mouse.getEventDWheel();

		if(wheel > 0) {
			targetScroll--;
		} else if(wheel < 0) {
			targetScroll++;
		}

		targetScroll = MathHelper.clamp_float(targetScroll, 0F, (float) maxScroll);
	}
}
