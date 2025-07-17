package com.hbm.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerMachineAssemblyMachine;
import com.hbm.inventory.recipes.AssemblyMachineRecipes;
import com.hbm.inventory.recipes.loader.GenericRecipe;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.machine.TileEntityMachineAssemblyMachine;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;

public class GUIMachineAssemblyMachine extends GuiInfoContainer {

	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/processing/gui_assembler.png");
	private TileEntityMachineAssemblyMachine assembler;
	
	public GUIMachineAssemblyMachine(InventoryPlayer invPlayer, TileEntityMachineAssemblyMachine tedf) {
		super(new ContainerMachineAssemblyMachine(invPlayer, tedf));
		assembler = tedf;
		
		this.xSize = 176;
		this.ySize = 256;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);

		assembler.inputTank.renderTankInfo(this, mouseX, mouseY, guiLeft + 8, guiTop + 115, 34, 16);
		assembler.outputTank.renderTankInfo(this, mouseX, mouseY, guiLeft + 80, guiTop + 115, 34, 16);
		
		this.drawElectricityInfo(this, mouseX, mouseY, guiLeft + 152, guiTop + 18, 16, 61, assembler.power, assembler.maxPower);

		if(guiLeft + 7 <= mouseX && guiLeft + 7 + 18 > mouseX && guiTop + 125 < mouseY && guiTop + 125 + 18 >= mouseY) {
			if(this.assembler.assemblerModule.recipe != null && AssemblyMachineRecipes.INSTANCE.recipeNameMap.containsKey(this.assembler.assemblerModule.recipe)) {
				GenericRecipe recipe = (GenericRecipe) AssemblyMachineRecipes.INSTANCE.recipeNameMap.get(this.assembler.assemblerModule.recipe);
				this.func_146283_a(recipe.print(), mouseX, mouseY);
			} else {
				this.drawCreativeTabHoveringText(EnumChatFormatting.YELLOW + "Click to set recipe", mouseX, mouseY);
			}
		}
	}
	
	@Override
	protected void mouseClicked(int x, int y, int button) {
		super.mouseClicked(x, y, button);
		
		if(this.checkClick(x, y, 7, 125, 18, 18)) GUIScreenRecipeSelector.openSelector(AssemblyMachineRecipes.INSTANCE, assembler, assembler.assemblerModule.recipe, 0, this);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String name = this.assembler.hasCustomInventoryName() ? this.assembler.getInventoryName() : I18n.format(this.assembler.getInventoryName());
		
		this.fontRendererObj.drawString(name, 70 - this.fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		
		int p = (int) (assembler.power * 61 / assembler.maxPower);
		drawTexturedModalRect(guiLeft + 152, guiTop + 79 - p, 176, 61 - p, 16, p);

		if(assembler.assemblerModule.progress > 0) {
			int j = (int) Math.ceil(70 * assembler.assemblerModule.progress);
			drawTexturedModalRect(guiLeft + 62, guiTop + 126, 176, 61, j, 16);
		}
		
		GenericRecipe recipe = AssemblyMachineRecipes.INSTANCE.recipeNameMap.get(assembler.assemblerModule.recipe);
		
		/// LEFT LED
		if(assembler.didProcess) {
			drawTexturedModalRect(guiLeft + 51, guiTop + 121, 195, 0, 3, 6);
		} else if(recipe != null) {
			drawTexturedModalRect(guiLeft + 51, guiTop + 121, 192, 0, 3, 6);
		}
		
		/// RIGHT LED
		if(assembler.didProcess) {
			drawTexturedModalRect(guiLeft + 56, guiTop + 121, 195, 0, 3, 6);
		} else if(recipe != null && assembler.power >= recipe.power) {
			drawTexturedModalRect(guiLeft + 56, guiTop + 121, 192, 0, 3, 6);
		}
		
		this.renderItem(recipe != null ? recipe.getIcon() : TEMPLATE_FOLDER, 8, 126);
		
		if(recipe != null && recipe.inputItem != null) {
			for(int i = 0; i < recipe.inputItem.length; i++) {
				Slot slot = (Slot) this.inventorySlots.inventorySlots.get(assembler.assemblerModule.inputSlots[i]);
				if(!slot.getHasStack()) this.renderItem(recipe.inputItem[i].extractForCyclingDisplay(20), slot.xDisplayPosition, slot.yDisplayPosition, 10F);
			}

			Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
			OpenGlHelper.glBlendFunc(770, 771, 1, 0);
			GL11.glColor4f(1F, 1F, 1F, 0.5F);
			GL11.glEnable(GL11.GL_BLEND);
			this.zLevel = 300F;
			for(int i = 0; i < recipe.inputItem.length; i++) {
				Slot slot = (Slot) this.inventorySlots.inventorySlots.get(assembler.assemblerModule.inputSlots[i]);
				if(!slot.getHasStack()) drawTexturedModalRect(guiLeft + slot.xDisplayPosition, guiTop + slot.yDisplayPosition, slot.xDisplayPosition, slot.yDisplayPosition, 16, 16);
			}
			this.zLevel = 0F;
			GL11.glColor4f(1F, 1F, 1F, 1F);
			GL11.glDisable(GL11.GL_BLEND);
		}
		
		assembler.inputTank.renderTank(guiLeft + 8, guiTop + 115, this.zLevel, 34, 16, 1);
		assembler.outputTank.renderTank(guiLeft + 80, guiTop + 115, this.zLevel, 34, 16, 1);
	}
}
