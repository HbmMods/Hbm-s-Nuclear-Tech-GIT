package com.hbm.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerMachinePlasmaForge;
import com.hbm.inventory.recipes.PlasmaForgeRecipe;
import com.hbm.inventory.recipes.PlasmaForgeRecipes;
import com.hbm.inventory.recipes.loader.GenericRecipe;
import com.hbm.items.machine.ItemBlueprints;
import com.hbm.lib.RefStrings;
import com.hbm.render.util.GaugeUtil;
import com.hbm.tileentity.machine.fusion.TileEntityFusionPlasmaForge;
import com.hbm.util.BobMathUtil;
import com.hbm.util.i18n.I18nUtil;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;

public class GUIMachinePlasmaForge extends GuiInfoContainer {

	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/reactors/gui_fusion_plasmaforge.png");
	private TileEntityFusionPlasmaForge forge;

	public GUIMachinePlasmaForge(InventoryPlayer invPlayer, TileEntityFusionPlasmaForge tedf) {
		super(new ContainerMachinePlasmaForge(invPlayer, tedf));
		forge = tedf;

		this.xSize = 176;
		this.ySize = 244;
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);

		forge.inputTank.renderTankInfo(this, mouseX, mouseY, guiLeft + 80, guiTop + 18, 16, 52);

		this.drawElectricityInfo(this, mouseX, mouseY, guiLeft + 152, guiTop + 18, 16, 62, forge.power, forge.maxPower);

		if(guiLeft + 7 <= mouseX && guiLeft + 7 + 18 > mouseX && guiTop + 80 < mouseY && guiTop + 80 + 18 >= mouseY) {
			if(this.forge.plasmaModule.recipe != null && PlasmaForgeRecipes.INSTANCE.recipeNameMap.containsKey(this.forge.plasmaModule.recipe)) {
				GenericRecipe recipe = (GenericRecipe) PlasmaForgeRecipes.INSTANCE.recipeNameMap.get(this.forge.plasmaModule.recipe);
				this.func_146283_a(recipe.print(), mouseX, mouseY);
			} else {
				this.drawCreativeTabHoveringText(EnumChatFormatting.YELLOW + I18nUtil.resolveKey("gui.recipe.setRecipe"), mouseX, mouseY);
			}
		}

		PlasmaForgeRecipe recipe = (PlasmaForgeRecipe) PlasmaForgeRecipes.INSTANCE.recipeNameMap.get(forge.plasmaModule.recipe);
		
		if(recipe != null) {
			drawCustomInfoStat(mouseX, mouseY, guiLeft + 25, guiTop + 115, 18, 18, mouseX, mouseY, EnumChatFormatting.GREEN + "-> " + EnumChatFormatting.RESET + BobMathUtil.getShortNumber(forge.plasmaEnergySync) + "TU / " + BobMathUtil.getShortNumber(recipe.ignitionTemp) + "TU");
		} else {
			drawCustomInfoStat(mouseX, mouseY, guiLeft + 25, guiTop + 115, 18, 18, mouseX, mouseY, "0TU / 0TU");
		}
	}

	@Override
	protected void mouseClicked(int x, int y, int button) {
		super.mouseClicked(x, y, button);

		if(this.checkClick(x, y, 7, 80, 18, 18)) GUIScreenRecipeSelector.openSelector(PlasmaForgeRecipes.INSTANCE, forge, forge.plasmaModule.recipe, 0, ItemBlueprints.grabPool(forge.slots[1]), this);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String name = this.forge.hasCustomInventoryName() ? this.forge.getInventoryName() : I18n.format(this.forge.getInventoryName());

		this.fontRendererObj.drawString(name, 70 - this.fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

		int p = (int) (forge.power * 61 / forge.maxPower);
		drawTexturedModalRect(guiLeft + 152, guiTop + 80 - p, 176, 62 - p, 16, p);

		if(forge.plasmaModule.progress > 0) {
			int j = (int) Math.ceil(70 * forge.plasmaModule.progress);
			drawTexturedModalRect(guiLeft + 62, guiTop + 81, 176, 62, j, 16);
		}

		PlasmaForgeRecipe recipe = (PlasmaForgeRecipe) PlasmaForgeRecipes.INSTANCE.recipeNameMap.get(forge.plasmaModule.recipe);

		/// LEFT LED
		if(forge.didProcess) {
			drawTexturedModalRect(guiLeft + 51, guiTop + 76, 195, 0, 3, 6);
		} else if(recipe != null) {
			drawTexturedModalRect(guiLeft + 51, guiTop + 76, 192, 0, 3, 6);
		}

		/// RIGHT LED
		if(forge.didProcess) {
			drawTexturedModalRect(guiLeft + 56, guiTop + 76, 195, 0, 3, 6);
		} else if(recipe != null && forge.power >= recipe.power) {
			drawTexturedModalRect(guiLeft + 56, guiTop + 76, 192, 0, 3, 6);
		}
		
		double inputGauge = recipe == null ? 0 : Math.min(((double) forge.plasmaEnergySync / (double) recipe.ignitionTemp), 1.5) / 1.5D;
		double boosterGauge = 0;
		
		// input energy
		GaugeUtil.drawSmoothGauge(guiLeft + 34, guiTop + 124, this.zLevel, inputGauge, 5, 2, 1, 0xA00000);
		// output genergy
		GaugeUtil.drawSmoothGauge(guiLeft + 70, guiTop + 124, this.zLevel, boosterGauge, 5, 2, 1, 0xA00000);

		this.renderItem(recipe != null ? recipe.getIcon() : TEMPLATE_FOLDER, 8, 81);

		if(recipe != null && recipe.inputItem != null) {
			for(int i = 0; i < recipe.inputItem.length; i++) {
				Slot slot = (Slot) this.inventorySlots.inventorySlots.get(forge.plasmaModule.inputSlots[i]);
				if(!slot.getHasStack()) this.renderItem(recipe.inputItem[i].extractForCyclingDisplay(20), slot.xDisplayPosition, slot.yDisplayPosition, 10F);
			}

			Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
			OpenGlHelper.glBlendFunc(770, 771, 1, 0);
			GL11.glColor4f(1F, 1F, 1F, 0.5F);
			GL11.glEnable(GL11.GL_BLEND);
			this.zLevel = 300F;
			for(int i = 0; i < recipe.inputItem.length; i++) {
				Slot slot = (Slot) this.inventorySlots.inventorySlots.get(forge.plasmaModule.inputSlots[i]);
				if(!slot.getHasStack()) drawTexturedModalRect(guiLeft + slot.xDisplayPosition, guiTop + slot.yDisplayPosition, slot.xDisplayPosition, slot.yDisplayPosition, 16, 16);
			}
			this.zLevel = 0F;
			GL11.glColor4f(1F, 1F, 1F, 1F);
			GL11.glDisable(GL11.GL_BLEND);
		}

		forge.inputTank.renderTank(guiLeft + 80, guiTop + 70, this.zLevel, 16, 52);
	}
}
