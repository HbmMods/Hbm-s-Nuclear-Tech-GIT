package com.hbm.inventory.gui;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import com.hbm.inventory.container.ContainerMachineAmmoPress;
import com.hbm.inventory.recipes.AmmoPressRecipes;
import com.hbm.inventory.recipes.AmmoPressRecipes.AmmoPressRecipe;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.machine.TileEntityMachineAmmoPress;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.util.ResourceLocation;

public class GUIMachineAmmoPress extends GuiInfoContainer {

	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/processing/gui_ammo_press.png");
	private TileEntityMachineAmmoPress press;

	private List<AmmoPressRecipe> recipes = new ArrayList();
	int index;
	int size;
	int selection;
	private GuiTextField search;

	public GUIMachineAmmoPress(InventoryPlayer invPlayer, TileEntityMachineAmmoPress press) {
		super(new ContainerMachineAmmoPress(invPlayer, press));
		this.press = press;
		
		this.xSize = 176;
		this.ySize = 200;
		
		regenerateRecipes();
	}
	
	@Override
	public void initGui() {

		super.initGui();

		Keyboard.enableRepeatEvents(true);
		this.search = new GuiTextField(this.fontRendererObj, guiLeft + 10, guiTop + 75, 66, 12);
		this.search.setTextColor(-1);
		this.search.setDisabledTextColour(-1);
		this.search.setEnableBackgroundDrawing(false);
		this.search.setMaxStringLength(25);
	}
	
	private void regenerateRecipes() {
		
		this.recipes.clear();
		this.recipes.addAll(AmmoPressRecipes.recipes);
		
		resetPaging();
	}
	
	private void search(String search) {
		
		search = search.toLowerCase(Locale.US);

		this.recipes.clear();
		
		if(search.isEmpty()) {
			this.recipes.addAll(AmmoPressRecipes.recipes);
			
		} else {
			for(AmmoPressRecipe recipe : AmmoPressRecipes.recipes) {
				if(recipe.output.getDisplayName().toLowerCase(Locale.US).contains(search)) {
					this.recipes.add(recipe);
				}
			}
		}
		
		resetPaging();
	}
	
	private void resetPaging() {
		
		this.index = 0;
		this.selection = -1;
		this.size = Math.max(0, (int)Math.ceil((this.recipes.size() - 12) / 2D));
	}
	
	@Override
	public void drawScreen(int x, int y, float interp) {
		super.drawScreen(x, y, interp);
		
		for(Object obj : this.inventorySlots.inventorySlots) {
			Slot slot = (Slot) obj;
			
			// if the mouse is over a slot, cancel
			if(this.func_146978_c(slot.xDisplayPosition, slot.yDisplayPosition, 16, 16, x, y) && slot.getHasStack()) {
				return;
			}
		}

		if(guiLeft <= x && guiLeft + xSize > x && guiTop < y && guiTop + ySize >= y && getSlotAtPosition(x, y) == null) {
			if(!Mouse.isButtonDown(0) && !Mouse.isButtonDown(1) && Mouse.next()) {
				int scroll = Mouse.getEventDWheel();
				
				if(scroll > 0 && this.index > 0) this.index--;
				if(scroll < 0 && this.index < this.size) this.index++;
			}
		}
	}
	
	@Override
	protected void mouseClicked(int x, int y, int k) {
		super.mouseClicked(x, y, k);
		
		this.search.mouseClicked(x, y, k);
		
		if(guiLeft + 7 <= x && guiLeft + 7 + 9 > x && guiTop + 17 < y && guiTop + 17 + 54 >= y) {
			mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
			if(this.index > 0)
				this.index--;
			
			return;
		}
		
		if(guiLeft + 88 <= x && guiLeft + 88 + 9 > x && guiTop + 17 < y && guiTop + 17 + 54 >= y) {
			mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
			if(this.index < this.size)
				this.index++;
			
			return;
		}
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String name = this.press.hasCustomInventoryName() ? this.press.getInventoryName() : I18n.format(this.press.getInventoryName());
		this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, 6, 0xffffff);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float interp, int x, int y) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

		if(guiLeft + 7 <= x && guiLeft + 7 + 9 > x && guiTop + 17 < y && guiTop + 17 + 54 >= y) {
			drawTexturedModalRect(guiLeft + 7, guiTop + 17, 176, 0, 9, 54);
		}
		if(guiLeft + 88 <= x && guiLeft + 88 + 9 > x && guiTop + 17 < y && guiTop + 17 + 54 >= y) {
			drawTexturedModalRect(guiLeft + 88, guiTop + 17, 185, 0, 9, 54);
		}
		
		if(this.search.isFocused()) {
			drawTexturedModalRect(guiLeft + 8, guiTop + 72, 176, 54, 70, 16);
		}
		
		for(int i = index * 3; i < index * 3 + 12; i++) {
			if(i >= recipes.size())
				break;
			
			int ind = i - index * 3;
			
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			RenderHelper.enableGUIStandardItemLighting();
			GL11.glDisable(GL11.GL_LIGHTING);
			GL11.glEnable(GL12.GL_RESCALE_NORMAL);
			
			AmmoPressRecipe recipe = recipes.get(i);
			
			FontRenderer font = null;
			if (recipe.output != null) font = recipe.output.getItem().getFontRenderer(recipe.output);
			if (font == null) font = fontRendererObj;
			
			itemRender.zLevel = 100.0F;
			itemRender.renderItemAndEffectIntoGUI(font, this.mc.getTextureManager(), recipe.output, guiLeft + 17 + 18 * (ind / 3), guiTop + 18 + 18 * (ind % 3));
			itemRender.zLevel = 0.0F;

			GL11.glEnable(GL11.GL_ALPHA_TEST);
			GL11.glDisable(GL11.GL_LIGHTING);
			this.mc.getTextureManager().bindTexture(texture);
			this.zLevel = 300.0F;
			
			if(selection == i)
				this.drawTexturedModalRect(guiLeft + 16 + 18 * (ind / 3), guiTop + 17 + 18 * (ind % 3), 194, 0, 18, 18);
			else
				this.drawTexturedModalRect(guiLeft + 16 + 18 * (ind / 3), guiTop + 17 + 18 * (ind % 3), 212, 0, 18, 18);
		}
		
		this.search.drawTextBox();
	}
	
	@Override
	protected void keyTyped(char c, int key) {
		
		if(this.search.textboxKeyTyped(c, key)) {
			search(this.search.getText());
		} else {
			super.keyTyped(c, key);
		}
	}

	@Override
	public void onGuiClosed() {
		Keyboard.enableRepeatEvents(false);
	}
}
