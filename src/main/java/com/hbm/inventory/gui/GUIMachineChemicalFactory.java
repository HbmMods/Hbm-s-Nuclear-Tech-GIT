package com.hbm.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerMachineChemicalFactory;
import com.hbm.inventory.recipes.ChemicalPlantRecipes;
import com.hbm.inventory.recipes.loader.GenericRecipe;
import com.hbm.items.machine.ItemBlueprints;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.machine.TileEntityMachineChemicalFactory;
import com.hbm.util.i18n.I18nUtil;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;

public class GUIMachineChemicalFactory extends GuiInfoContainer {

	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/processing/gui_chemical_factory.png");
	private TileEntityMachineChemicalFactory chemplant;
	
	public GUIMachineChemicalFactory(InventoryPlayer invPlayer, TileEntityMachineChemicalFactory tedf) {
		super(new ContainerMachineChemicalFactory(invPlayer, tedf));
		chemplant = tedf;
		
		this.xSize = 248;
		this.ySize = 216;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);

		for(int i = 0; i < 3; i++) for(int j = 0; j < 4; j++) {
			chemplant.inputTanks[i + j * 3].renderTankInfo(this, mouseX, mouseY, guiLeft + 60 + i * 5, guiTop + 20 + j * 22, 3, 16);
			chemplant.outputTanks[i + j * 3].renderTankInfo(this, mouseX, mouseY, guiLeft + 189 + i * 5, guiTop + 20 + j * 22, 3, 16);
		}
		
		chemplant.water.renderTankInfo(this, mouseX, mouseY, guiLeft + 224, guiTop + 125, 7, 52);
		chemplant.lps.renderTankInfo(this, mouseX, mouseY, guiLeft + 233, guiTop + 125, 7, 52);
		
		this.drawElectricityInfo(this, mouseX, mouseY, guiLeft + 224, guiTop + 18, 16, 68, chemplant.power, chemplant.maxPower);

		for(int i = 0; i < 4; i++) if(guiLeft + 74 <= mouseX && guiLeft + 74 + 18 > mouseX && guiTop + 19 + i * 22 < mouseY && guiTop + 19 + i * 22 + 18 >= mouseY) {
			if(this.chemplant.chemplantModule[i].recipe != null && ChemicalPlantRecipes.INSTANCE.recipeNameMap.containsKey(this.chemplant.chemplantModule[i].recipe)) {
				GenericRecipe recipe = (GenericRecipe) ChemicalPlantRecipes.INSTANCE.recipeNameMap.get(this.chemplant.chemplantModule[i].recipe);
				this.func_146283_a(recipe.print(), mouseX, mouseY);
			} else {
				this.drawCreativeTabHoveringText(EnumChatFormatting.YELLOW + I18nUtil.resolveKey("gui.recipe.setRecipe"), mouseX, mouseY);
			}
		}
	}
	
	@Override
	protected void mouseClicked(int x, int y, int button) {
		super.mouseClicked(x, y, button);
		
		for(int i = 0; i < 4; i++) if(this.checkClick(x, y, 74, 19 + i * 22, 18, 18)) GUIScreenRecipeSelector.openSelector(ChemicalPlantRecipes.INSTANCE, chemplant, chemplant.chemplantModule[i].recipe, i, ItemBlueprints.grabPool(chemplant.slots[4 + i * 7]), this);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String name = this.chemplant.hasCustomInventoryName() ? this.chemplant.getInventoryName() : I18n.format(this.chemplant.getInventoryName());
		
		this.fontRendererObj.drawString(name, 106 - this.fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 26, this.ySize - 96 + 2, 4210752);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, 248, 116);
		drawTexturedModalRect(guiLeft + 18, guiTop + 116, 18, 116, 230, 100);
		
		int p = (int) (chemplant.power * 68 / chemplant.maxPower);
		drawTexturedModalRect(guiLeft + 224, guiTop + 86 - p, 0, 184 - p, 16, p);

		for(int i = 0; i < 4; i++) if(chemplant.chemplantModule[i].progress > 0) {
			int j = (int) Math.ceil(22 * chemplant.chemplantModule[i].progress);
			drawTexturedModalRect(guiLeft + 113, guiTop + 29 + i * 22, 0, 216, j, 6);
		}
		
		for(int g = 0; g < 4; g++) {
			GenericRecipe recipe = ChemicalPlantRecipes.INSTANCE.recipeNameMap.get(chemplant.chemplantModule[g].recipe);
			
			/// LEFT LED
			if(chemplant.didProcess[g]) {
				drawTexturedModalRect(guiLeft + 113, guiTop + 21 + g * 22, 4, 222, 4, 4);
			} else if(recipe != null) {
				drawTexturedModalRect(guiLeft + 113, guiTop + 21 + g * 22, 0, 222, 4, 4);
			}
			
			/// RIGHT LED
			if(chemplant.didProcess[g]) {
				drawTexturedModalRect(guiLeft + 121, guiTop + 21 + g * 22, 4, 222, 4, 4);
			} else if(recipe != null && chemplant.power >= recipe.power && chemplant.canCool()) {
				drawTexturedModalRect(guiLeft + 121, guiTop + 21 + g * 22, 0, 222, 4, 4);
			}
		}
		
		for(int g = 0; g < 4; g++) { // not a great way of doing it but at least we eliminate state leak bullshit
			GenericRecipe recipe = ChemicalPlantRecipes.INSTANCE.recipeNameMap.get(chemplant.chemplantModule[g].recipe);
			
			this.renderItem(recipe != null ? recipe.getIcon() : TEMPLATE_FOLDER, 75, 20 + g * 22);
			
			if(recipe != null && recipe.inputItem != null) {
				for(int i = 0; i < recipe.inputItem.length; i++) {
					Slot slot = (Slot) this.inventorySlots.inventorySlots.get(chemplant.chemplantModule[g].inputSlots[i]);
					if(!slot.getHasStack()) this.renderItem(recipe.inputItem[i].extractForCyclingDisplay(20), slot.xDisplayPosition, slot.yDisplayPosition, 10F);
				}
	
				Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
				OpenGlHelper.glBlendFunc(770, 771, 1, 0);
				GL11.glColor4f(1F, 1F, 1F, 0.5F);
				GL11.glEnable(GL11.GL_BLEND);
				this.zLevel = 300F;
				for(int i = 0; i < recipe.inputItem.length; i++) {
					Slot slot = (Slot) this.inventorySlots.inventorySlots.get(chemplant.chemplantModule[g].inputSlots[i]);
					if(!slot.getHasStack()) drawTexturedModalRect(guiLeft + slot.xDisplayPosition, guiTop + slot.yDisplayPosition, slot.xDisplayPosition, slot.yDisplayPosition, 16, 16);
				}
				this.zLevel = 0F;
				GL11.glColor4f(1F, 1F, 1F, 1F);
				GL11.glDisable(GL11.GL_BLEND);
			}
		}

		for(int i = 0; i < 3; i++) for(int j = 0; j < 4; j++) {
			chemplant.inputTanks[i + j * 3].renderTank(guiLeft + 60 + i * 5, guiTop + 36 + j * 22, this.zLevel, 3, 16);
			chemplant.outputTanks[i + j * 3].renderTank(guiLeft + 189 + i * 5, guiTop + 36 + j * 22, this.zLevel, 3, 16);
		}
		
		chemplant.water.renderTank(guiLeft + 224, guiTop + 177, this.zLevel, 7, 52);
		chemplant.lps.renderTank(guiLeft + 233, guiTop + 177, this.zLevel, 7, 52);
	}
}
