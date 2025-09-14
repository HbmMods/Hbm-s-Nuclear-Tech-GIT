package com.hbm.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerMachineAssemblyFactory;
import com.hbm.inventory.recipes.AssemblyMachineRecipes;
import com.hbm.inventory.recipes.loader.GenericRecipe;
import com.hbm.items.machine.ItemBlueprints;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.machine.TileEntityMachineAssemblyFactory;
import com.hbm.util.i18n.I18nUtil;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;

public class GUIMachineAssemblyFactory extends GuiInfoContainer {

	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/processing/gui_assembly_factory.png");
	private TileEntityMachineAssemblyFactory assembler;
	
	public GUIMachineAssemblyFactory(InventoryPlayer invPlayer, TileEntityMachineAssemblyFactory tedf) {
		super(new ContainerMachineAssemblyFactory(invPlayer, tedf));
		assembler = tedf;
		
		this.xSize = 256;
		this.ySize = 240;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);

		for(int j = 0; j < 4; j++) {
			assembler.inputTanks[j].renderTankInfo(this, mouseX, mouseY, guiLeft + 105 + (j % 2) * 109, guiTop + 20 + (j / 2) * 56, 5, 32);
			assembler.outputTanks[j].renderTankInfo(this, mouseX, mouseY, guiLeft + 105 + (j % 2) * 109, guiTop + 54 + (j / 2) * 56, 5, 16);
		}
		
		assembler.water.renderTankInfo(this, mouseX, mouseY, guiLeft + 232, guiTop + 149, 7, 52);
		assembler.lps.renderTankInfo(this, mouseX, mouseY, guiLeft + 241, guiTop + 149, 7, 52);
		
		this.drawElectricityInfo(this, mouseX, mouseY, guiLeft + 234, guiTop + 18, 16, 92, assembler.power, assembler.maxPower);

		for(int i = 0; i < 4; i++) if(guiLeft + 6 + (i % 2) * 109 <= mouseX && guiLeft + 6 + (i % 2) * 109 + 18 > mouseX && guiTop + 53 + (i / 2) * 56 < mouseY && guiTop + 53 + (i / 2) * 56 + 18 >= mouseY) {
			if(this.assembler.assemblerModule[i].recipe != null && AssemblyMachineRecipes.INSTANCE.recipeNameMap.containsKey(this.assembler.assemblerModule[i].recipe)) {
				GenericRecipe recipe = (GenericRecipe) AssemblyMachineRecipes.INSTANCE.recipeNameMap.get(this.assembler.assemblerModule[i].recipe);
				this.func_146283_a(recipe.print(), mouseX, mouseY);
			} else {
				this.drawCreativeTabHoveringText(EnumChatFormatting.YELLOW + I18nUtil.resolveKey("gui.recipe.setRecipe"), mouseX, mouseY);
			}
		}
	}
	
	@Override
	protected void mouseClicked(int x, int y, int button) {
		super.mouseClicked(x, y, button);
		
		for(int i = 0; i < 4; i++) if(this.checkClick(x, y, 6 + (i % 2) * 109, 53 + (i / 2) * 56, 18, 18)) GUIScreenRecipeSelector.openSelector(AssemblyMachineRecipes.INSTANCE, assembler, assembler.assemblerModule[i].recipe, i, ItemBlueprints.grabPool(assembler.slots[4 + i * 14]), this);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String name = this.assembler.hasCustomInventoryName() ? this.assembler.getInventoryName() : I18n.format(this.assembler.getInventoryName());
		
		this.fontRendererObj.drawString(name, 113 - this.fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 33, this.ySize - 96 + 2, 4210752);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, 256, 140);
		drawTexturedModalRect(guiLeft + 25, guiTop + 140, 25, 140, 231, 100);
		
		int p = (int) (assembler.power * 92 / assembler.maxPower);
		drawTexturedModalRect(guiLeft + 234, guiTop + 110 - p, 0, 232 - p, 16, p);

		for(int i = 0; i < 4; i++) if(assembler.assemblerModule[i].progress > 0) {
			int j = (int) Math.ceil(37 * assembler.assemblerModule[i].progress);
			drawTexturedModalRect(guiLeft + 45 + (i % 2) * 109, guiTop + 63 + (i / 2) * 56, 0, 240, j, 6);
		}
		
		for(int g = 0; g < 4; g++) {
			GenericRecipe recipe = AssemblyMachineRecipes.INSTANCE.recipeNameMap.get(assembler.assemblerModule[g].recipe);
			
			/// LEFT LED
			if(assembler.didProcess[g]) {
				drawTexturedModalRect(guiLeft + 45 + (g % 2) * 109, guiTop + 55 + (g / 2) * 56, 4, 236, 4, 4);
			} else if(recipe != null) {
				drawTexturedModalRect(guiLeft + 45 + (g % 2) * 109, guiTop + 55 + (g / 2) * 56, 0, 236, 4, 4);
			}
			
			/// RIGHT LED
			if(assembler.didProcess[g]) {
				drawTexturedModalRect(guiLeft + 53 + (g % 2) * 109, guiTop + 55 + (g / 2) * 56, 4, 236, 4, 4);
			} else if(recipe != null && assembler.power >= recipe.power && assembler.canCool()) {
				drawTexturedModalRect(guiLeft + 53 + (g % 2) * 109, guiTop + 55 + (g / 2) * 56, 0, 236, 4, 4);
			}
		}
		
		for(int g = 0; g < 4; g++) {
			GenericRecipe recipe = AssemblyMachineRecipes.INSTANCE.recipeNameMap.get(assembler.assemblerModule[g].recipe);
			
			this.renderItem(recipe != null ? recipe.getIcon() : TEMPLATE_FOLDER, 7 + (g % 2) * 109, 54 + (g / 2) * 56);
			
			if(recipe != null && recipe.inputItem != null) {
				for(int i = 0; i < recipe.inputItem.length; i++) {
					Slot slot = (Slot) this.inventorySlots.inventorySlots.get(assembler.assemblerModule[g].inputSlots[i]);
					if(!slot.getHasStack()) this.renderItem(recipe.inputItem[i].extractForCyclingDisplay(20), slot.xDisplayPosition, slot.yDisplayPosition, 10F);
				}
	
				Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
				OpenGlHelper.glBlendFunc(770, 771, 1, 0);
				GL11.glColor4f(1F, 1F, 1F, 0.5F);
				GL11.glEnable(GL11.GL_BLEND);
				this.zLevel = 300F;
				for(int i = 0; i < recipe.inputItem.length; i++) {
					Slot slot = (Slot) this.inventorySlots.inventorySlots.get(assembler.assemblerModule[g].inputSlots[i]);
					if(!slot.getHasStack()) drawTexturedModalRect(guiLeft + slot.xDisplayPosition, guiTop + slot.yDisplayPosition, slot.xDisplayPosition, slot.yDisplayPosition, 16, 16);
				}
				this.zLevel = 0F;
				GL11.glColor4f(1F, 1F, 1F, 1F);
				GL11.glDisable(GL11.GL_BLEND);
			}
		}

		for(int j = 0; j < 4; j++) {
			assembler.inputTanks[j].renderTank(guiLeft + 105 + (j % 2) * 109, guiTop + 52 + (j / 2) * 56, this.zLevel, 5, 32);
			assembler.outputTanks[j].renderTank(guiLeft + 105 + (j % 2) * 109, guiTop + 70 + (j / 2) * 56, this.zLevel, 5, 16);
		}
		
		assembler.water.renderTank(guiLeft + 232, guiTop + 201, this.zLevel, 7, 52);
		assembler.lps.renderTank(guiLeft + 241, guiTop + 201, this.zLevel, 7, 52);
	}
}
