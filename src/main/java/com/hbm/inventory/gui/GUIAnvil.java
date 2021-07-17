package com.hbm.inventory.gui;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import com.hbm.inventory.AnvilRecipes;
import com.hbm.inventory.AnvilRecipes.AnvilConstructionRecipe;
import com.hbm.inventory.AnvilRecipes.AnvilOutput;
import com.hbm.inventory.RecipesCommon.AStack;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.inventory.RecipesCommon.OreDictStack;
import com.hbm.inventory.container.ContainerAnvil;
import com.hbm.lib.RefStrings;
import com.hbm.packet.AnvilCraftPacket;
import com.hbm.packet.PacketDispatcher;

import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.oredict.OreDictionary;

public class GUIAnvil extends GuiContainer {

	public static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/processing/gui_anvil.png");
	
	private int tier;
	private List<AnvilConstructionRecipe> originList = new ArrayList();
	private List<AnvilConstructionRecipe> recipes = new ArrayList();
	int index;
	int size;
	int selection;
	private GuiTextField search;

	public GUIAnvil(InventoryPlayer player, int tier) {
		super(new ContainerAnvil(player, tier));

		this.tier = tier;
		this.xSize = 176;
		this.ySize = 222;
		
		for(AnvilConstructionRecipe recipe : AnvilRecipes.getConstruction()) {
			if(recipe.isTierValid(this.tier))
				this.originList.add(recipe);
		}
		
		regenerateRecipes();
		
		guiLeft = (this.width - this.xSize) / 2;
		guiTop = (this.height - this.ySize) / 2;
	}
	
	@Override
	public void initGui() {

		super.initGui();

		Keyboard.enableRepeatEvents(true);
		this.search = new GuiTextField(this.fontRendererObj, guiLeft + 10, guiTop + 111, 84, 12);
		this.search.setTextColor(-1);
		this.search.setDisabledTextColour(-1);
		this.search.setEnableBackgroundDrawing(false);
		this.search.setMaxStringLength(25);
	}
	
	private void regenerateRecipes() {
		
		this.recipes.clear();
		this.recipes.addAll(this.originList);
		
		resetPaging();
	}
	
	private void search(String search) {
		
		search = search.toLowerCase();

		this.recipes.clear();
		
		if(search.isEmpty()) {
			this.recipes.addAll(this.originList);
			
		} else {
			for(AnvilConstructionRecipe recipe : this.originList) {
				List<String> list = recipeToSearchList(recipe);
				
				for(String s : list) {
					if(s.contains(search)) {
						this.recipes.add(recipe);
						break;
					}
				}
			}
		}
		
		resetPaging();
	}
	
	private void resetPaging() {
		
		this.index = 0;
		this.selection = -1;
		this.size = Math.max(0, (int)Math.ceil((this.recipes.size() - 10) / 2D));
	}
	
	@Override
	protected void mouseClicked(int x, int y, int k) {
		super.mouseClicked(x, y, k);
		
		this.search.mouseClicked(x, y, k);
		
		if(guiLeft + 7 <= x && guiLeft + 7 + 9 > x && guiTop + 71 < y && guiTop + 71 + 36 >= y) {
			mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
			if(this.index > 0)
				this.index--;
			
			return;
		}
		
		if(guiLeft + 106 <= x && guiLeft + 106 + 9 > x && guiTop + 71 < y && guiTop + 71 + 36 >= y) {
			mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
			if(this.index < this.size)
				this.index++;
			
			return;
		}
		
		if(guiLeft + 52 <= x && guiLeft + 52 + 18 > x && guiTop + 53 < y && guiTop + 53 + 18 >= y) {
			
			if(this.selection == -1)
				return;
			
			mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
			PacketDispatcher.wrapper.sendToServer(new AnvilCraftPacket(this.recipes.get(this.selection), Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) ? 1 : 0));
			
			return;
		}
		
		if(guiLeft + 97 <= x && guiLeft + 97 + 18 > x && guiTop + 107 < y && guiTop + 107 + 18 >= y) {
			mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
			search(this.search.getText());
			return;
		}
		
		for(int i = index * 2; i < index * 2 + 10; i++) {
			
			if(i >= this.recipes.size())
				break;
			
			int ind = i - index * 2;
			
			int ix = 16 + 18 * (ind / 2);
			int iy = 71 + 18 * (ind % 2);
			if(guiLeft + ix <= x && guiLeft + ix + 18 > x && guiTop + iy < y && guiTop + iy + 18 >= y) {
				
				if(this.selection != i)
					this.selection = i;
				else
					this.selection = -1;
				
				mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
				return;
			}
		}
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mX, int mY) {
		String name = I18n.format("container.anvil", tier);
		this.fontRendererObj.drawString(name, 61 - this.fontRendererObj.getStringWidth(name) / 2, 8, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
		
		if(this.selection >= 0) {
			
			AnvilConstructionRecipe recipe = recipes.get(this.selection);
			List<String> list = recipeToList(recipe);
			int longest = 0;
			
			for(String s : list) {
				int length = this.fontRendererObj.getStringWidth(s);
				
				if(length > longest)
					longest = length;
			}
			
			double scale = 0.5D;
			GL11.glScaled(scale, scale, scale);
			int offset = 0;
			for(String s : list) {
				this.fontRendererObj.drawString(s, 260, 50 + offset, 0xffffff);
				offset += 9;
			}
			
			this.lastSize = (int)(longest * scale);
			GL11.glScaled(1D/scale, 1D/scale, 1D/scale);
			
		} else {
			this.lastSize = 0;
		}
	}
	
	/**
	 * Generates the neat structured list for showing ingredients
	 * @param recipe
	 * @return
	 */
	public List<String> recipeToList(AnvilConstructionRecipe recipe) {

		List<String> list = new ArrayList();
		
		list.add(EnumChatFormatting.YELLOW + "Inputs:");
		
		for(AStack stack : recipe.input) {
			if(stack instanceof ComparableStack)  {
				ItemStack input = ((ComparableStack) stack).toStack();
				list.add(">" + input.stackSize + "x " + input.getDisplayName());
				
			} else if(stack instanceof OreDictStack) {
				OreDictStack input = (OreDictStack) stack;
				ArrayList<ItemStack> ores = OreDictionary.getOres(input.name);
				
				if(ores.size() > 0) {
					ItemStack inStack = ores.get((int) (Math.abs(System.currentTimeMillis() / 1000) % ores.size()));
					list.add(">" + input.stacksize + "x " + inStack.getDisplayName());
					
				} else {
					list.add("I AM ERROR");
				}
			}
		}

		list.add("");
		list.add(EnumChatFormatting.YELLOW + "Outputs:");
		
		for(AnvilOutput stack : recipe.output) {
			list.add(">" + stack.stack.stackSize + "x " + stack.stack.getDisplayName() + (stack.chance != 1F ? (" (" + (stack.chance * 100) + "%)" ) : ""));
		}
		
		return list;
	}
	
	/**
	 * Generates a simple, unstructured list of inputs (and all ore dict variants) and outputs for searching
	 * @param recipe
	 * @return
	 */
	public List<String> recipeToSearchList(AnvilConstructionRecipe recipe) {

		List<String> list = new ArrayList();
		
		for(AStack stack : recipe.input) {
			if(stack instanceof ComparableStack)  {
				ItemStack input = ((ComparableStack) stack).toStack();
				list.add(input.getDisplayName().toLowerCase());
				
			} else if(stack instanceof OreDictStack) {
				OreDictStack input = (OreDictStack) stack;
				ArrayList<ItemStack> ores = OreDictionary.getOres(input.name);
				
				if(ores.size() > 0) {
					for(ItemStack ore : ores) {
						list.add(ore.getDisplayName().toLowerCase());
					}
					
				}
			}
		}
		
		for(AnvilOutput stack : recipe.output) {
			list.add(stack.stack.getDisplayName().toLowerCase());
		}
		
		return list;
	}
	
	int lastSize = 1;
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float inter, int mX, int mY) {
		
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(texture);
		
		this.drawTexturedModalRect(guiLeft, guiTop, 0, 0, this.xSize, this.ySize);
		
		int slide = MathHelper.clamp_int(this.lastSize - 42, 0, 1000);
		
		int mul = 1;
		while(true) {
			
			if(slide >= 51 * mul) {
				this.drawTexturedModalRect(guiLeft + 125 + 51 * mul, guiTop + 17, 125, 17, 54, 108);
				mul++;
				
			} else {
				break;
			}
		}
		
		this.drawTexturedModalRect(guiLeft + 125 + slide, guiTop + 17, 125, 17, 54, 108);
		
		if(this.search.isFocused()) {
			drawTexturedModalRect(guiLeft + 8, guiTop + 108, 168, 222, 88, 16);
		}
		
		if(guiLeft + 7 <= mX && guiLeft + 7 + 9 > mX && guiTop + 71 < mY && guiTop + 71 + 36 >= mY) {
			drawTexturedModalRect(guiLeft + 7, guiTop + 71, 176, 186, 9, 36);
		}
		if(guiLeft + 106 <= mX && guiLeft + 106 + 9 > mX && guiTop + 71 < mY && guiTop + 71 + 36 >= mY) {
			drawTexturedModalRect(guiLeft + 106, guiTop + 71, 185, 186, 9, 36);
		}
		if(guiLeft + 52 <= mX && guiLeft + 52 + 18 > mX && guiTop + 53 < mY && guiTop + 53 + 18 >= mY) {
			drawTexturedModalRect(guiLeft + 52, guiTop + 53, 176, 150, 18, 18);
		}
		if(guiLeft + 97 <= mX && guiLeft + 97 + 18 > mX && guiTop + 107 < mY && guiTop + 107 + 18 >= mY) {
			drawTexturedModalRect(guiLeft + 97, guiTop + 107, 176, 168, 18, 18);
		}
		
		for(int i = index * 2; i < index * 2 + 10; i++) {
			if(i >= recipes.size())
				break;
			
			int ind = i - index * 2;
			
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			RenderHelper.enableGUIStandardItemLighting();
			GL11.glDisable(GL11.GL_LIGHTING);
			
			AnvilConstructionRecipe recipe = recipes.get(i);
			ItemStack display = recipe.getDisplay();
			
			FontRenderer font = null;
			if (display != null) font = display.getItem().getFontRenderer(display);
			if (font == null) font = fontRendererObj;
			
			itemRender.zLevel = 100.0F;
			itemRender.renderItemAndEffectIntoGUI(font, this.mc.getTextureManager(), recipe.getDisplay(), guiLeft + 17 + 18 * (ind / 2), guiTop + 72 + 18 * (ind % 2));
			itemRender.zLevel = 0.0F;
			
			GL11.glEnable(GL11.GL_ALPHA_TEST);
			GL11.glDisable(GL11.GL_LIGHTING);
			this.mc.getTextureManager().bindTexture(texture);
			this.zLevel = 300.0F;
			this.drawTexturedModalRect(guiLeft + 16 + 18 * (ind / 2), guiTop + 71 + 18 * (ind % 2), 18 + 18 * recipe.getOverlay().ordinal(), 222, 18, 18);
			
			if(selection == i)
				this.drawTexturedModalRect(guiLeft + 16 + 18 * (ind / 2), guiTop + 71 + 18 * (ind % 2), 0, 222, 18, 18);
		}
		
		this.search.drawTextBox();
	}
	
	@Override
	protected void keyTyped(char c, int key) {
		
		if(!this.search.textboxKeyTyped(c, key)) {
			
			if(key == 28) {
				this.search.setFocused(false);
				search(this.search.getText());
			}
			
			super.keyTyped(c, key);
		}
	}
}
