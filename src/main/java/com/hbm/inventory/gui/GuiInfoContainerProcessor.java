package com.hbm.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.interfaces.IControlReceiver;
import com.hbm.inventory.gui.element.GUIElements;
import com.hbm.inventory.recipes.loader.GenericRecipe;
import com.hbm.items.machine.ItemBlueprints;
import com.hbm.module.machine.ModuleMachineBase;
import com.hbm.util.i18n.I18nUtil;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;

/**
 * For things that use the GUIScreenRecipeSelector, mainly for the preview rendering
 * and certain other standardized GUI components.
 * @author hbm
 */
public abstract class GuiInfoContainerProcessor extends GuiInfoContainer {
	
	protected ModuleMachineBase[] processorModule;

	public GuiInfoContainerProcessor(Container container) {
		super(container);
	}
	
	/**
	 * Array of all recipe fields.
	 * Each recipe field is defined by an int array
	 * [ selector x / selector y / template slot index ]
	 */
	public abstract int[][] getSelectorPositions();
	
	/**
	 * Returns the IControlReceiver instance (i.e. our tile entity) to
	 * which the recipe selectro should send the recipe change request to
	 */
	public abstract IControlReceiver getControlReceiver();

	public abstract ResourceLocation getTexture();

	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);

		int[][] selectors = this.getSelectorPositions();
		
		// draw the tooltips for the recipe selectors
		for(int i = 0; i < selectors.length; i++) {
			int x = selectors[i][0];
			int y = selectors[i][1];
			
			if(guiLeft + x <= mouseX && guiLeft + x + 18 > mouseX && guiTop + y < mouseY && guiTop + y + 18 >= mouseY) {
				
				ModuleMachineBase module = this.processorModule[i];
				GenericRecipe recipe = module.getRecipe();
				
				if(recipe != null) {
					GUIElements.drawHoveringTextRecipe(recipe.print(), mouseX, mouseY, this.fontRendererObj, itemRender, this.width, this.height);
				} else {
					this.drawCreativeTabHoveringText(EnumChatFormatting.YELLOW + I18nUtil.resolveKey("gui.recipe.setRecipe"), mouseX, mouseY);
				}
			}
		}
	}

	@Override
	protected void mouseClicked(int x, int y, int button) {
		super.mouseClicked(x, y, button);

		int[][] selectors = this.getSelectorPositions();
		
		// standard recipe selector open
		for(int i = 0; i < selectors.length; i++) {
			int ix = selectors[i][0];
			int iy = selectors[i][1];
			int slot = selectors[i][2];
			
			if(this.checkClick(x, y, ix, iy, 18, 18)) {
				this.click();
				
				ModuleMachineBase module = this.processorModule[i];
				GUIScreenRecipeSelector.openSelector(module.getRecipeSet(), this.getControlReceiver(),
						module.getRecipeName(), 0, ItemBlueprints.grabPool(this.inventorySlots.getSlot(slot).getStack()), this);
			}
		}
	}
	
	/** Renders the standard double LEDs which are 3x6 pixels large and two pixels apart */
	protected void renderStandardLEDs(boolean didProcess, GenericRecipe recipe, long power, int lX, int lY, int sX, int sY) {

		/// LEFT LED
		if(didProcess) {
			drawTexturedModalRect(guiLeft + lX, guiTop + lY, sX, sY, 3, 6);
		} else if(recipe != null) {
			drawTexturedModalRect(guiLeft + lX, guiTop + lY, sX - 3, sY, 3, 6);
		}

		/// RIGHT LED
		if(didProcess) {
			drawTexturedModalRect(guiLeft + lX + 5, guiTop + lY, sX, sY, 3, 6);
		} else if(recipe != null && power >= recipe.power) {
			drawTexturedModalRect(guiLeft + lX + 5, guiTop + lY, sX - 3, sY, 3, 6);
		}
	}
	
	/**
	 * Draws the half-opacity item icons over the slots as well as the recipe selector button
	 */
	protected void renderRecipeIcons() {

		int[][] selectors = this.getSelectorPositions();
		
		// draws the icons for the recipe selectors as well as the ghost items in empty slots
		for(int i = 0; i < selectors.length; i++) {
			int ix = selectors[i][0] + 1;
			int iy = selectors[i][1] + 1;
			ModuleMachineBase module = this.processorModule[i];
			GenericRecipe recipe = module.getRecipe();

			this.renderItem(recipe != null ? recipe.getIcon() : TEMPLATE_FOLDER, ix, iy);
			
			if(recipe != null && recipe.inputItem != null) {
				
				for(int j = 0; j < recipe.inputItem.length; j++) {
					Slot slot = (Slot) this.inventorySlots.inventorySlots.get(module.inputSlots[j]);
					if(!slot.getHasStack()) this.renderItem(recipe.inputItem[j].extractForCyclingDisplay(20), slot.xDisplayPosition, slot.yDisplayPosition, 10F);
				}
	
				Minecraft.getMinecraft().getTextureManager().bindTexture(getTexture());
				OpenGlHelper.glBlendFunc(770, 771, 1, 0);
				GL11.glColor4f(1F, 1F, 1F, 0.5F);
				GL11.glEnable(GL11.GL_BLEND);
				this.zLevel = 300F;
				
				for(int j = 0; j < recipe.inputItem.length; j++) {
					Slot slot = (Slot) this.inventorySlots.inventorySlots.get(module.inputSlots[j]);
					if(!slot.getHasStack()) drawTexturedModalRect(guiLeft + slot.xDisplayPosition, guiTop + slot.yDisplayPosition, slot.xDisplayPosition, slot.yDisplayPosition, 16, 16);
				}
				
				this.zLevel = 0F;
				GL11.glColor4f(1F, 1F, 1F, 1F);
				GL11.glDisable(GL11.GL_BLEND);
			}
		}
	}
}
