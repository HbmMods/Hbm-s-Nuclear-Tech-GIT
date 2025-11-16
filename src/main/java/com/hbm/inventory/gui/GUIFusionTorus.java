package com.hbm.inventory.gui;

import com.hbm.inventory.container.ContainerFusionTorus;
import com.hbm.inventory.recipes.FusionRecipe;
import com.hbm.inventory.recipes.FusionRecipes;
import com.hbm.items.machine.ItemBlueprints;
import com.hbm.lib.RefStrings;
import com.hbm.render.util.GaugeUtil;
import com.hbm.tileentity.machine.fusion.TileEntityFusionTorus;
import com.hbm.util.BobMathUtil;
import com.hbm.util.i18n.I18nUtil;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;

public class GUIFusionTorus extends GuiInfoContainer {

	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/reactors/gui_fusion_torus.png");
	private TileEntityFusionTorus torus;

	public GUIFusionTorus(InventoryPlayer invPlayer, TileEntityFusionTorus torus) {
		super(new ContainerFusionTorus(invPlayer, torus));
		this.torus = torus;
		
		this.xSize = 230;
		this.ySize = 244;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float interp) {
		super.drawScreen(mouseX, mouseY, interp);
		
		this.drawElectricityInfo(this, mouseX, mouseY, guiLeft + 8, guiTop + 18, 16, 62, torus.power, torus.getMaxPower());
		torus.tanks[0].renderTankInfo(this, mouseX, mouseY, guiLeft + 44, guiTop + 18, 16, 52);
		torus.tanks[1].renderTankInfo(this, mouseX, mouseY, guiLeft + 62, guiTop + 18, 16, 52);
		torus.tanks[2].renderTankInfo(this, mouseX, mouseY, guiLeft + 80, guiTop + 18, 16, 52);
		torus.tanks[3].renderTankInfo(this, mouseX, mouseY, guiLeft + 152, guiTop + 18, 16, 52);
		torus.coolantTanks[0].renderTankInfo(this, mouseX, mouseY, guiLeft + 188, guiTop + 46, 16, 52);
		torus.coolantTanks[1].renderTankInfo(this, mouseX, mouseY, guiLeft + 206, guiTop + 46, 16, 52);

		if(guiLeft + 43 <= mouseX && guiLeft + 43 + 18 > mouseX && guiTop + 80 < mouseY && guiTop + 80 + 18 >= mouseY) {
			if(this.torus.fusionModule.recipe != null && FusionRecipes.INSTANCE.recipeNameMap.containsKey(this.torus.fusionModule.recipe)) {
				FusionRecipe recipe = (FusionRecipe) FusionRecipes.INSTANCE.recipeNameMap.get(this.torus.fusionModule.recipe);
				this.func_146283_a(recipe.print(), mouseX, mouseY);
			} else {
				this.drawCreativeTabHoveringText(EnumChatFormatting.YELLOW + I18nUtil.resolveKey("gui.recipe.setRecipe"), mouseX, mouseY);
			}
		}
	}

	@Override
	protected void mouseClicked(int x, int y, int button) {
		super.mouseClicked(x, y, button);

		if(this.checkClick(x, y, 43, 80, 18, 18)) GUIScreenRecipeSelector.openSelector(FusionRecipes.INSTANCE, torus, torus.fusionModule.recipe, 0, ItemBlueprints.grabPool(torus.slots[1]), this);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String name = this.torus.hasCustomInventoryName() ? this.torus.getInventoryName() : I18n.format(this.torus.getInventoryName());
		this.fontRendererObj.drawString(name, 106 - this.fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 35, this.ySize - 93, 4210752);

		this.fontRendererObj.drawString(EnumChatFormatting.AQUA + "/123K", 136 + 54, 32, 4210752);
		int heat = (int) Math.ceil(300);
		String label = (heat > 123 ? EnumChatFormatting.RED : EnumChatFormatting.AQUA) + "" + heat + "K";
		this.fontRendererObj.drawString(label, 166 + 54 - this.fontRendererObj.getStringWidth(label), 22, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float interp, int x, int y) {
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

		int p = (int) (torus.power * 62 / torus.getMaxPower());
		drawTexturedModalRect(guiLeft + 8, guiTop + 80 - p, 230, 62 - p, 16, p);

		if(torus.fusionModule.progress > 0) {
			int j = (int) Math.ceil(70 * torus.fusionModule.progress);
			drawTexturedModalRect(guiLeft + 98, guiTop + 81, 0, 244, j, 6);
		}

		if(torus.fusionModule.bonus > 0) {
			int j = (int) Math.min(Math.ceil(70 * torus.fusionModule.bonus), 70);
			drawTexturedModalRect(guiLeft + 98, guiTop + 91, 0, 250, j, 6);
		}

		FusionRecipe recipe = FusionRecipes.INSTANCE.recipeNameMap.get(torus.fusionModule.recipe);

		// power LED
		if(recipe != null && torus.power >= recipe.power) drawTexturedModalRect(guiLeft + 160, guiTop + 115, 246, 14, 8, 8);
		// coolant LED
		int heat = (int) Math.ceil(torus.temperature);
		if(heat <= 123) drawTexturedModalRect(guiLeft + 170, guiTop + 115, 246, 14, 8, 8);
		// plasma LED
		if(torus.didProcess) drawTexturedModalRect(guiLeft + 180, guiTop + 115, 246, 14, 8, 8);

		/// LEFT LED
		if(torus.didProcess) {
			drawTexturedModalRect(guiLeft + 87, guiTop + 76, 249, 0, 3, 6);
		} else if(recipe != null) {
			drawTexturedModalRect(guiLeft + 87, guiTop + 76, 246, 0, 3, 6);
		}

		/// RIGHT LED
		if(torus.didProcess) {
			drawTexturedModalRect(guiLeft + 92, guiTop + 76, 249, 0, 3, 6);
		} else if(recipe != null && torus.power >= torus.power) {
			drawTexturedModalRect(guiLeft + 92, guiTop + 76, 246, 0, 3, 6);
		}
		
		double gauge = BobMathUtil.sps((Minecraft.getMinecraft().theWorld.getTotalWorldTime() + interp) * 0.25) / 2 + 0.5D;
		
		// input energy
		GaugeUtil.drawSmoothGauge(guiLeft + 52, guiTop + 124, this.zLevel, gauge, 5, 2, 1, 0xA00000);
		// output genergy
		GaugeUtil.drawSmoothGauge(guiLeft + 88, guiTop + 124, this.zLevel, gauge, 5, 2, 1, 0xA00000);
		// fuel consumption
		GaugeUtil.drawSmoothGauge(guiLeft + 124, guiTop + 124, this.zLevel, gauge, 5, 2, 1, 0xA00000);

		// recipe selector
		this.renderItem(recipe != null ? recipe.getIcon() : TEMPLATE_FOLDER, 44, 81);
		
		// fluids
		torus.tanks[0].renderTank(guiLeft + 44, guiTop + 70, this.zLevel, 16, 52);
		torus.tanks[1].renderTank(guiLeft + 62, guiTop + 70, this.zLevel, 16, 52);
		torus.tanks[2].renderTank(guiLeft + 80, guiTop + 70, this.zLevel, 16, 52);
		torus.tanks[3].renderTank(guiLeft + 152, guiTop + 70, this.zLevel, 16, 52);
		
		// coolant
		torus.coolantTanks[0].renderTank(guiLeft + 188, guiTop + 98, this.zLevel, 16, 52);
		torus.coolantTanks[1].renderTank(guiLeft + 206, guiTop + 98, this.zLevel, 16, 52);
	}
}
