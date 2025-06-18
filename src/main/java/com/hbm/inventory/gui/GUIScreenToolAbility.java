package com.hbm.inventory.gui;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import com.hbm.handler.ability.AvailableAbilities;
import com.hbm.handler.ability.IBaseAbility;
import com.hbm.handler.ability.IToolAreaAbility;
import com.hbm.handler.ability.IToolHarvestAbility;
import com.hbm.handler.ability.ToolPreset;
import com.hbm.items.tool.ItemToolAbility;
import com.hbm.lib.RefStrings;
import com.hbm.main.MainRegistry;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.toserver.NBTItemControlPacket;
import com.hbm.util.Tuple.Pair;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class GUIScreenToolAbility extends GuiScreen {

	public static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/tool/gui_tool_ability.png");

	protected int guiLeft;
	protected int guiTop;
	protected int xSize;
	protected int ySize;
	protected int insetWidth;

	public static class AbilityInfo {
		public IBaseAbility ability;
		public int textureU, textureV;

		public AbilityInfo(IBaseAbility ability, int textureU, int textureV) {
			this.ability = ability;
			this.textureU = textureU;
			this.textureV = textureV;
		}
	}

	public static final List<AbilityInfo> abilitiesArea = new ArrayList<>();
	public static final List<AbilityInfo> abilitiesHarvest = new ArrayList<>();

	static {
		abilitiesArea.add(new AbilityInfo(IToolAreaAbility.NONE, 0, 91));
		abilitiesArea.add(new AbilityInfo(IToolAreaAbility.RECURSION, 32, 91));
		abilitiesArea.add(new AbilityInfo(IToolAreaAbility.HAMMER, 64, 91));
		abilitiesArea.add(new AbilityInfo(IToolAreaAbility.EXPLOSION, 96, 91));

		abilitiesHarvest.add(new AbilityInfo(IToolHarvestAbility.NONE, 0, 107));
		abilitiesHarvest.add(new AbilityInfo(IToolHarvestAbility.SILK, 32, 107));
		abilitiesHarvest.add(new AbilityInfo(IToolHarvestAbility.LUCK, 64, 107));
		abilitiesHarvest.add(new AbilityInfo(IToolHarvestAbility.SMELTER, 96, 107));
		abilitiesHarvest.add(new AbilityInfo(IToolHarvestAbility.SHREDDER, 128, 107));
		abilitiesHarvest.add(new AbilityInfo(IToolHarvestAbility.CENTRIFUGE, 160, 107));
		abilitiesHarvest.add(new AbilityInfo(IToolHarvestAbility.CRYSTALLIZER, 192, 107));
		abilitiesHarvest.add(new AbilityInfo(IToolHarvestAbility.MERCURY, 224, 107));
	}

	protected ItemStack toolStack;
	protected AvailableAbilities availableAbilities;
	protected ItemToolAbility.Configuration config;

	protected int hoverIdxHarvest = -1;
	protected int hoverIdxArea = -1;
	protected int hoverIdxExtraBtn = -1;

	public GUIScreenToolAbility(AvailableAbilities availableAbilities) {
		super();

		this.availableAbilities = availableAbilities;

		this.xSize = 186; // Note: increased dynamically
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

		this.config = ((ItemToolAbility) this.toolStack.getItem()).getConfiguration(this.toolStack);

		guiLeft = (width - xSize) / 2;
		guiTop = (height - ySize) / 2;
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		this.drawDefaultBackground();

		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);

		// Draw window background
		drawStretchedRect(guiLeft, guiTop, 0, 0, xSize, xSize - insetWidth, ySize, 74, 87);

		// Draw the switches
		ToolPreset activePreset = config.getActivePreset();
		hoverIdxArea = drawSwitches(abilitiesArea, activePreset.areaAbility, activePreset.areaAbilityLevel, guiLeft + 15, guiTop + 25, mouseX, mouseY);
		hoverIdxHarvest = drawSwitches(abilitiesHarvest, activePreset.harvestAbility, activePreset.harvestAbilityLevel, guiLeft + 15, guiTop + 45, mouseX, mouseY);

		// Draw preset indicator
		drawNumber(config.currentPreset + 1, guiLeft + insetWidth + 115, guiTop + 25);
		drawNumber(config.presets.size(), guiLeft + insetWidth + 149, guiTop + 25);

		// Draw extra buttons hover highlights
		int extraBtnsX = guiLeft + xSize - 86;

		hoverIdxExtraBtn = -1;
		for(int i = 0; i < 7; ++i) {
			if(isInAABB(mouseX, mouseY, extraBtnsX + i * 11, guiTop + 11, 9, 9)) {
				hoverIdxExtraBtn = i;
				drawTexturedModalRect(extraBtnsX + i * 11, guiTop + 11, 193 + i * 9, 0, 9, 9);
			}
		}

		// Draw tooltip
		String tooltipValue = "";

		if(hoverIdxArea != -1) {
			int level = 0;
			if(abilitiesArea.get(hoverIdxArea).ability == activePreset.areaAbility) {
				level = activePreset.areaAbilityLevel;
			}
			tooltipValue = abilitiesArea.get(hoverIdxArea).ability.getFullName(level);
		} else if(hoverIdxHarvest != -1) {
			int level = 0;
			if(abilitiesHarvest.get(hoverIdxHarvest).ability == activePreset.harvestAbility) {
				level = activePreset.harvestAbilityLevel;
			}
			tooltipValue = abilitiesHarvest.get(hoverIdxHarvest).ability.getFullName(level);
		} else if(hoverIdxExtraBtn != -1) {
			switch(hoverIdxExtraBtn) {
			case 0: tooltipValue = "Reset all presets"; break;
			case 1: tooltipValue = "Delete current preset"; break;
			case 2: tooltipValue = "Add new preset"; break;
			case 3: tooltipValue = "Select first preset"; break;
			case 4: tooltipValue = "Next preset"; break;
			case 5: tooltipValue = "Previous preset"; break;
			case 6: tooltipValue = "Close window"; break;
			}
		}

		if(!tooltipValue.isEmpty()) {
			int tooltipWidth = Math.max(6, fontRendererObj.getStringWidth(tooltipValue));
			int tooltipX = guiLeft + xSize / 2 - tooltipWidth / 2;
			int tooltipY = guiTop + ySize + 1 + 4;
			drawStretchedRect(tooltipX - 5, tooltipY - 4, 0, 76, tooltipWidth + 10, 186, 15, 3, 3);
			fontRendererObj.drawString(tooltipValue, tooltipX, tooltipY, 0xffffffff);
		}
	}

	protected void drawStretchedRect(int x, int y, int u, int v, int realWidth, int width, int height, int keepLeft, int keepRight) {
		int midWidth = width - keepLeft - keepRight;
		int realMidWidth = realWidth - keepLeft - keepRight;
		drawTexturedModalRect(x, y, u, v, keepLeft, height);
		for(int i = 0; i < realMidWidth; i += midWidth) {
			drawTexturedModalRect(x + keepLeft + i, y, u + keepLeft, v, Math.min(midWidth, realMidWidth - i), height);
		}
		drawTexturedModalRect(x + keepLeft + realMidWidth, y, u + keepLeft + midWidth, v, keepRight, height);
	}

	protected int drawSwitches(List<AbilityInfo> abilities, IBaseAbility selectedAbility, int selectedLevel, int x, int y, int mouseX, int mouseY) {
		int hoverIdx = -1;

		for(int i = 0; i < abilities.size(); ++i) {
			AbilityInfo abilityInfo = abilities.get(i);
			boolean available = abilityAvailable(abilityInfo.ability);
			boolean selected = abilityInfo.ability == selectedAbility;

			// Draw switch
			drawTexturedModalRect(x + 20 * i, y, abilityInfo.textureU + (available ? 16 : 0), abilityInfo.textureV, 16, 16);

			// Draw level LEDs
			if(abilityInfo.ability.levels() > 1) {
				int level = 0;

				if(selected) {
					level = selectedLevel + 1;
				}

				// Note: only visual effect for the LEDs
				// int maxLevel = Math.min(abilityInfo.ability.levels(), 5);
				int maxLevel = 5;

				if(level > 10 || level < 0) {
					// All-red LEDs for invalid levels
					level = -1;
				}

				drawTexturedModalRect(x + 20 * i + 17, y + 1, 188 + level * 2, maxLevel * 14, 2, 14);
			}

			boolean isHovered = isInAABB(mouseX, mouseY, x + 20 * i, y, 16, 16);

			if(isHovered) {
				hoverIdx = i;
			}

			if(selected) {
				// Draw selection highlight
				drawTexturedModalRect(x + 20 * i - 1, y - 1, 220, 9, 18, 18);
			} else if(available && isHovered) {
				// Draw hover highlight
				drawTexturedModalRect(x + 20 * i - 1, y - 1, 238, 9, 18, 18);
			}
		}

		return hoverIdx;
	}

	protected void drawNumber(int number, int x, int y) {
		number += 100; // Against accidental negatives
		drawDigit((number / 10) % 10, x, y);
		drawDigit(number % 10, x + 12, y);
	}

	protected void drawDigit(int digit, int x, int y) {
		drawTexturedModalRect(x, y, digit * 10, 123, 10, 15);
	}

	private boolean isInAABB(int mouseX, int mouseY, int x, int y, int width, int height) {
		return x <= mouseX && x + width > mouseX && y <= mouseY && y + height > mouseY;
	}

	private boolean abilityAvailable(IBaseAbility ability) {
		if(!availableAbilities.supportsAbility(ability)) {
			return false;
		}

		ToolPreset activePreset = config.getActivePreset();
		if(ability instanceof IToolHarvestAbility && ability != IToolHarvestAbility.NONE && !activePreset.areaAbility.allowsHarvest(activePreset.areaAbilityLevel)) {
			return false;
		}

		return true;
	}

	@Override
	public void handleMouseInput() {
		super.handleMouseInput();

		if(Mouse.getEventButton() == -1) {
			int scroll = Mouse.getEventDWheel();

			if(scroll < 0) doPrevPreset(true);
			if(scroll > 0) doNextPreset(true);
		}
	}

	@Override
	protected void mouseClicked(int mouseX, int mouseY, int button) {
		ToolPreset activePreset = config.getActivePreset();

		// Process switches
		Pair<IBaseAbility, Integer> clickResult;

		clickResult = handleSwitchesClicked(abilitiesArea, activePreset.areaAbility, activePreset.areaAbilityLevel, hoverIdxArea, mouseX, mouseY);
		activePreset.areaAbility = (IToolAreaAbility) clickResult.key;
		activePreset.areaAbilityLevel = clickResult.value;

		clickResult = handleSwitchesClicked(abilitiesHarvest, activePreset.harvestAbility, activePreset.harvestAbilityLevel, hoverIdxHarvest, mouseX, mouseY);
		activePreset.harvestAbility = (IToolHarvestAbility) clickResult.key;
		activePreset.harvestAbilityLevel = clickResult.value;

		if(!activePreset.areaAbility.allowsHarvest(activePreset.areaAbilityLevel)) {
			activePreset.harvestAbility = IToolHarvestAbility.NONE;
			activePreset.harvestAbilityLevel = 0;
		}

		// Process extra buttons
		if(hoverIdxExtraBtn != -1) {
			switch(hoverIdxExtraBtn) {
			case 0: doResetPresets(); break;
			case 1: doDelPreset(); break;
			case 2: doAddPreset(); break;
			case 3: doZeroPreset(); break;
			case 4: doNextPreset(false); break;
			case 5: doPrevPreset(false); break;
			case 6: doClose(); break;
			}

			mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 0.5F));
		}

		// Allow quick-closing
		if(!isInAABB(mouseX, mouseY, guiLeft, guiTop, xSize, ySize)) {
			doClose();
		}
	}

	protected Pair<IBaseAbility, Integer> handleSwitchesClicked(List<AbilityInfo> abilities, IBaseAbility selectedAbility, int selectedLevel, int hoverIdx, int mouseX, int mouseY) {
		if(hoverIdx != -1) {
			IBaseAbility hoveredAbility = abilities.get(hoverIdx).ability;
			boolean available = abilityAvailable(hoveredAbility);

			if(available) {
				int availableLevels = availableAbilities.maxLevel(hoveredAbility) + 1;

				if(hoveredAbility != selectedAbility || availableLevels > 1) {
					mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("hbm:item.techBoop"), 2F));
				}

				if(hoveredAbility == selectedAbility) {
					selectedLevel = (selectedLevel + 1) % availableLevels;
				} else {
					selectedLevel = 0;
				}

				selectedAbility = hoveredAbility;
			}
		}

		return new Pair<>(selectedAbility, selectedLevel);
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

	protected void doResetPresets() {
		config.reset(availableAbilities);
	}

	protected void doDelPreset() {
		if(config.presets.size() <= 1) {
			return;
		}
		config.presets.remove(config.currentPreset);
		config.currentPreset = Math.min(config.currentPreset, config.presets.size() - 1);
	}

	protected void doAddPreset() {
		if(config.presets.size() >= 99) {
			return;
		}

		config.presets.add(config.currentPreset + 1, new ToolPreset());
		config.currentPreset += 1;
	}

	protected void doZeroPreset() {
		config.currentPreset = 0;
	}

	protected void doNextPreset(boolean bound) {
		if(bound) {
			if(config.currentPreset < config.presets.size() - 1) {
				config.currentPreset += 1;
			}
		} else {
			config.currentPreset = (config.currentPreset + 1) % config.presets.size();
		}
	}

	protected void doPrevPreset(boolean bound) {
		if(bound) {
			if(config.currentPreset > 0) {
				config.currentPreset -= 1;
			}
		} else {
			config.currentPreset = (config.currentPreset + config.presets.size() - 1) % config.presets.size();
		}
	}

	protected void doClose() {
		// A bit messy, but I suppose it works
		((ItemToolAbility) this.toolStack.getItem()).setConfiguration(toolStack, config);
		PacketDispatcher.wrapper.sendToServer(new NBTItemControlPacket(this.toolStack.getTagCompound()));

		this.mc.thePlayer.closeScreen();

		MainRegistry.proxy.displayTooltip(config.getActivePreset().getMessage().getFormattedText(), MainRegistry.proxy.ID_TOOLABILITY);

		this.mc.theWorld.playSoundAtEntity(this.mc.thePlayer, "random.orb", 0.25F, config.getActivePreset().isNone() ? 0.75F : 1.25F);
	}
}