package com.hbm.inventory.gui;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import com.hbm.items.tool.ItemToolAbility;
import com.hbm.lib.RefStrings;
import com.hbm.util.EnumUtil;
import com.hbm.util.I18nUtil;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class GUIScreenToolAbility extends GuiScreen {

	public static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/tool/gui_tool_ability.png");
	
	protected int guiLeft;
	protected int guiTop;
	protected int xSize;
	protected int ySize;
	protected int insetWidth;

	protected ItemToolAbility toolDef;
	protected ItemStack toolStack;

	// TODO: Move elsewhere?
	public static class AbilityInfo {
		String name;
		int textureU, textureV;
		boolean hasLevels;

		public AbilityInfo(String name, int textureU, int textureV, boolean hasLevels) {
			this.name = name;
			this.textureU = textureU;
			this.textureV = textureV;
			this.hasLevels = hasLevels;
		}
	}

	public static final List<AbilityInfo> abilitiesArea = new ArrayList<>();
	public static final List<AbilityInfo> abilitiesHarvest = new ArrayList<>();

	static {
		abilitiesArea.add(new AbilityInfo(null, 0, 91, false));
		abilitiesArea.add(new AbilityInfo("tool.ability.recursion", 32, 91, false));
		abilitiesArea.add(new AbilityInfo("tool.ability.hammer", 64, 91, true));
		abilitiesArea.add(new AbilityInfo("tool.ability.explosion", 96, 91, true));
	
		abilitiesHarvest.add(new AbilityInfo(null, 0, 107, false));
		abilitiesHarvest.add(new AbilityInfo("tool.ability.silktouch", 32, 107, false));
		abilitiesHarvest.add(new AbilityInfo("tool.ability.luck", 64, 107, false));
		abilitiesHarvest.add(new AbilityInfo("tool.ability.smelter", 96, 107, false));
		abilitiesHarvest.add(new AbilityInfo("tool.ability.shredder", 128, 107, false));
		abilitiesHarvest.add(new AbilityInfo("tool.ability.centrifuge", 160, 107, false));
		abilitiesHarvest.add(new AbilityInfo("tool.ability.crystallizer", 192, 107, false));
		abilitiesHarvest.add(new AbilityInfo("tool.ability.mercury", 224, 107, false));
	}

	// TODO: availability status for abilities; list of presets; selected preset index;
	// TODO: Remove this in favor of current preset
	int selectionIdxArea = 0;
	int selectedLevelArea = 0;
	int selectionIdxHarvest = 0;
	int selectedLevelHarvest = 0;
	int selectedPreset = 0;
	int totalPresets = 1;
	
	public GUIScreenToolAbility(ItemToolAbility toolDef) {
		super();
		
		this.toolDef = toolDef;
		this.xSize = 186;  // Note: increased dynamically
		this.ySize = 76;

		this.insetWidth = 20 * Math.max(abilitiesArea.size() - 4, abilitiesHarvest.size() - 8);
		this.xSize += insetWidth;
	}

	@Override
	public void initGui() {
		this.toolStack = this.mc.thePlayer.getHeldItem();
		
		if(this.toolStack == null) {
			doClose();
		}
		
		guiLeft = (width - xSize) / 2;
		guiTop = (height - ySize) / 2;
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		this.drawDefaultBackground();

		drawGuiContainerBackgroundLayer(mouseX, mouseY);

		if(!Mouse.isButtonDown(0) && !Mouse.isButtonDown(1) && Mouse.next()) {
			int scroll = Mouse.getEventDWheel() / 6;
			
			// TODO
			selectedPreset = (selectedPreset + totalPresets + scroll) % totalPresets;
		}
	}

	protected void drawGuiContainerBackgroundLayer(int mouseX, int mouseY) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);

		// Draw window background
		drawStretchedRect(guiLeft, guiTop, 0, 0, xSize, xSize - insetWidth, ySize, 74, 76);
		
		// Draw the switches
		drawSwitches(abilitiesArea, selectionIdxArea, selectedLevelArea, guiLeft + 15, guiTop + 25, mouseX, mouseY);
		drawSwitches(abilitiesHarvest, selectionIdxHarvest, selectedLevelHarvest, guiLeft + 15, guiTop + 45, mouseX, mouseY);

		// Draw preset indicator
		drawNumber(selectedPreset + 1, guiLeft + insetWidth + 115, guiTop + 25);
		drawNumber(totalPresets, guiLeft + insetWidth + 149, guiTop + 25);

		// Draw extra buttons hover highlights
		int extraBtnsX = guiLeft + xSize - 75;

		for (int i = 0; i < 6; ++i) {
			if (isInAABB(mouseX, mouseY, extraBtnsX + i * 11, guiTop + 11, 9, 9)) {
				drawTexturedModalRect(extraBtnsX + i * 11, guiTop + 11, 186 + i * 9, 18, 9, 9);
			}
		}

		// Draw tooltip
		// TODO
	}

	protected void drawStretchedRect(int x, int y, int u, int v, int realWidth, int width, int height, int keepLeft, int keepRight) {
		int midWidth = width - keepLeft - keepRight;
		int realMidWidth = realWidth - keepLeft - keepRight;
		drawTexturedModalRect(x, y, u, v, keepLeft, height);
		for (int i = 0; i < realMidWidth; i += midWidth) {
			drawTexturedModalRect(x + keepLeft + i, y, u + keepLeft, v, Math.min(midWidth, realMidWidth - i), height);
		}
		drawTexturedModalRect(x + keepLeft + realMidWidth, y, u + keepLeft + midWidth, v, keepRight, height);
	}

	protected void drawSwitches(List<AbilityInfo> abilities, int selectionIdx, int selectedLevel, int x, int y, int mouseX, int mouseY) {
		// TODO: Store hovered ability, use it in click handling and the dynamic tooltip
		
		for (int i = 0; i < abilities.size(); ++i) {
			AbilityInfo abilityInfo = abilities.get(i);
			boolean available = true;  // TODO

			// Draw switch
			drawTexturedModalRect(x + 20 * i, y, abilityInfo.textureU + (available ? 16 : 0), abilityInfo.textureV, 16, 16);
		
			// Draw level LEDs
			if (abilityInfo.hasLevels) {
				int level = 0;  // TODO

				if (i == selectionIdx) {
					level = selectedLevel + 1;
				}

				drawTexturedModalRect(x + 20 * i + 17, y + 1, 222 + level * 2, 0, 2, 14);
			}

			if (i == selectionIdx) {
				// Draw selection highlight
				drawTexturedModalRect(x + 20 * i - 1, y - 1, 186, 0, 18, 18);
			} else if (available && isInAABB(mouseX, mouseY, x + 20 * i, y, 16, 16)) {
				// Draw hover highlight
				drawTexturedModalRect(x + 20 * i - 1, y - 1, 204, 0, 18, 18);
			}
		}
	}

	protected void drawNumber(int number, int x, int y) {
		number += 100;  // Against accidental negatives
		drawDigit((number / 10) % 10, x, y);
		drawDigit(number % 10, x + 12, y);
	}

	protected void drawDigit(int digit, int x, int y) {
		drawTexturedModalRect(x, y, digit * 10, 123, 10, 15);
	}

	private boolean isInAABB(int mouseX, int mouseY, int x, int y, int width, int height) {
		return x <= mouseX && x + width > mouseX && y <= mouseY && y + height > mouseY;
	}

	@Override
	public void updateScreen() {
		EntityPlayer player = this.mc.thePlayer;

		if(player.getHeldItem() == null || player.getHeldItem().getItem() != toolDef)
			player.closeScreen();
	}

	@Override
	protected void mouseClicked(int mouseX, int mouseY, int button) {
		// TODO
		boolean clicked = false;
		
		// TODO: Encapsulate?
		for (int i = 0; i < abilitiesArea.size(); ++i) {
			AbilityInfo abilityInfo = abilitiesArea.get(i);
			boolean available = true;  // TODO

			if (available && isInAABB(mouseX, mouseY, guiLeft + 15 + 20 * i, guiTop + 25, 16, 16)) {
				if (abilityInfo.hasLevels) {
					int availableLevels = 5;

					if (i == selectionIdxArea) {
						selectedLevelArea = (selectedLevelArea + 1) % availableLevels;
					} else {
						selectedLevelArea = 0;
					}
				}

				selectionIdxArea = i;
				clicked = true;
				break;
			}
		}

		for (int i = 0; i < abilitiesHarvest.size(); ++i) {
			AbilityInfo abilityInfo = abilitiesHarvest.get(i);
			boolean available = true;  // TODO

			if (available && isInAABB(mouseX, mouseY, guiLeft + 15 + 20 * i, guiTop + 45, 16, 16)) {
				if (abilityInfo.hasLevels) {
					int availableLevels = 5;

					if (i == selectionIdxArea) {
						selectedLevelHarvest = (selectedLevelHarvest + 1) % availableLevels;
					} else {
						selectedLevelHarvest = 0;
					}
				}

				selectionIdxHarvest = i;
				clicked = true;
				break;
			}
		}

		if (clicked) {
			mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("hbm:item.techBoop"), 1F));
		}
		
		// TODO
		int extraBtnsX = guiLeft + xSize - 75;

		clicked = false;

		if (isInAABB(mouseX, mouseY, extraBtnsX + 0 * 11, guiTop + 11, 9, 9)) {
			doDelPreset();
			clicked = true;
		}

		if (isInAABB(mouseX, mouseY, extraBtnsX + 1 * 11, guiTop + 11, 9, 9)) {
			doAddPreset();
			clicked = true;
		}

		if (isInAABB(mouseX, mouseY, extraBtnsX + 2 * 11, guiTop + 11, 9, 9)) {
			doZeroPreset();
			clicked = true;
		}

		if (isInAABB(mouseX, mouseY, extraBtnsX + 3 * 11, guiTop + 11, 9, 9)) {
			doNextPreset();
			clicked = true;
		}

		if (isInAABB(mouseX, mouseY, extraBtnsX + 4 * 11, guiTop + 11, 9, 9)) {
			doPrevPreset();
			clicked = true;
		}

		if (isInAABB(mouseX, mouseY, extraBtnsX + 5 * 11, guiTop + 11, 9, 9)) {
			doClose();
			clicked = true;
		}

		if (clicked) {
			mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 0.5F));
		}
	}

	@Override
	protected void keyTyped(char c, int key) {
		if(key == 1 || key == this.mc.gameSettings.keyBindInventory.getKeyCode()) {
			doClose();
			return;
		}
		
		super.keyTyped(c, key);
	}
	
	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}

	protected void doDelPreset() {
		// TODO
		if (totalPresets <= 1) {
			return;
		}
		totalPresets -= 1;
		selectedPreset -= 1;
	}

	protected void doAddPreset() {
		// TODO
		totalPresets += 1;
		selectedPreset += 1;
	}

	protected void doZeroPreset() {
		// TODO
		selectedPreset = 0;
	}

	protected void doNextPreset() {
		// TODO
		selectedPreset = (selectedPreset + 1) % totalPresets;
	}

	protected void doPrevPreset() {
		// TODO
		selectedPreset = (selectedPreset + totalPresets - 1) % totalPresets;
	}

	protected void doClose() {
		this.mc.thePlayer.closeScreen();
	}
}