package com.hbm.inventory.gui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.recipes.AssemblerRecipes;
import com.hbm.inventory.recipes.AssemblerRecipes.AssemblerRecipe;
import com.hbm.inventory.recipes.ChemplantRecipes;
import com.hbm.inventory.recipes.ChemplantRecipes.ChemRecipe;
import com.hbm.inventory.recipes.CrucibleRecipes;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemAssemblyTemplate;
import com.hbm.items.machine.ItemCassette;
import com.hbm.items.machine.ItemStamp;
import com.hbm.items.machine.ItemStamp.StampType;
import com.hbm.lib.RefStrings;
import com.hbm.main.MainRegistry;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.toserver.ItemFolderPacket;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class GUIScreenTemplateFolder extends GuiScreen {

	protected static final ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/gui_planner.png");
	protected static final ResourceLocation texture_journal = new ResourceLocation(RefStrings.MODID + ":textures/gui/gui_planner_journal.png");
	private boolean isJournal = false;
	protected int xSize = 176;
	protected int ySize = 229;
	protected int guiLeft;
	protected int guiTop;
	int currentPage = 0;
	List<ItemStack> allStacks = new ArrayList<ItemStack>();
	List<ItemStack> stacks = new ArrayList<ItemStack>();
	List<FolderButton> buttons = new ArrayList<FolderButton>();
	private GuiTextField search;

	public GUIScreenTemplateFolder(EntityPlayer player) {

		if(player.getHeldItem() == null)
			return;
		
		Item item = player.getHeldItem().getItem();

		this.isJournal = item != ModItems.template_folder;

		if(!this.isJournal) {
			// Stamps
			for(ItemStack i : ItemStamp.stamps.get(StampType.PLATE)) allStacks.add(i.copy());
			for(ItemStack i : ItemStamp.stamps.get(StampType.WIRE)) allStacks.add(i.copy());
			for(ItemStack i : ItemStamp.stamps.get(StampType.CIRCUIT)) allStacks.add(i.copy());
			
			// Tracks
			for(int i = 1; i < ItemCassette.TrackType.values().length; i++) {
				allStacks.add(new ItemStack(ModItems.siren_track, 1, i));
			}
			
			// Fluid IDs
			FluidType[] fluids = Fluids.getInNiceOrder();
			for(int i = 1; i < fluids.length; i++) {
				if(!fluids[i].hasNoID()) {
					allStacks.add(new ItemStack(ModItems.fluid_identifier, 1, fluids[i].getID()));
				}
			}
		}
		
		// Assembly Templates
		for(int i = 0; i < AssemblerRecipes.recipeList.size(); i++) {
			ComparableStack comp = AssemblerRecipes.recipeList.get(i);
			AssemblerRecipe recipe = AssemblerRecipes.recipes.get(comp);
			if(recipe != null && recipe.folders.contains(item)) {
				allStacks.add(ItemAssemblyTemplate.writeType(new ItemStack(ModItems.assembly_template, 1, i), comp));
			}
		}

		if(!this.isJournal) {
			// Chemistry Templates
			for(int i = 0; i < ChemplantRecipes.recipes.size(); i++) {
				ChemRecipe chem = ChemplantRecipes.recipes.get(i);
				allStacks.add(new ItemStack(ModItems.chemistry_template, 1, chem.getId()));
			}
			
			// Crucible Templates
			for(int i = 0; i < CrucibleRecipes.recipes.size(); i++) {
				allStacks.add(new ItemStack(ModItems.crucible_template, 1, CrucibleRecipes.recipes.get(i).getId()));
			}
		}
		
		search(null);
	}
	
	private void search(String sub) {
		
		stacks.clear();
		
		this.currentPage = 0;
		
		if(sub == null || sub.isEmpty()) {
			stacks.addAll(allStacks);
			updateButtons();
			return;
		}
		
		sub = sub.toLowerCase(Locale.US);
		
		outer:
		for(ItemStack stack : allStacks) {
			
			for(Object o : stack.getTooltip(MainRegistry.proxy.me(), true)) {
				
				if(o instanceof String) {
					String text = (String) o;
					
					if(text.toLowerCase(Locale.US).contains(sub)) {
						stacks.add(stack);
						continue outer;
					}
				}
			}
			
			if(stack.getItem() == ModItems.fluid_identifier) {
				FluidType fluid = Fluids.fromID(stack.getItemDamage());
				
				if(fluid.getLocalizedName().contains(sub)) {
					stacks.add(stack);
				}
			}
		}
		
		updateButtons();
	}

	int getPageCount() {
		return (int) Math.ceil((stacks.size() - 1) / (5 * 7));
	}

	@Override
	public void updateScreen() {
		if(currentPage < 0)
			currentPage = 0;
		if(currentPage > getPageCount())
			currentPage = getPageCount();
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		this.drawDefaultBackground();
		this.drawGuiContainerBackgroundLayer(f, mouseX, mouseY);
		GL11.glDisable(GL11.GL_LIGHTING);
		this.drawGuiContainerForegroundLayer(mouseX, mouseY);
		GL11.glEnable(GL11.GL_LIGHTING);
	}

	@Override
	public void initGui() {
		super.initGui();
		this.guiLeft = (this.width - this.xSize) / 2;
		this.guiTop = (this.height - this.ySize) / 2;

		updateButtons();

		Keyboard.enableRepeatEvents(true);
		this.search = new GuiTextField(this.fontRendererObj, guiLeft + 61, guiTop + 213, 48, 12);
		this.search.setTextColor(0xffffff);
		this.search.setDisabledTextColour(0xffffff);
		this.search.setEnableBackgroundDrawing(false);
		this.search.setMaxStringLength(100);
		this.search.setFocused(true);
	}

	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}

	protected void updateButtons() {

		if(!buttons.isEmpty())
			buttons.clear();

		for(int i = currentPage * 35; i < Math.min(currentPage * 35 + 35, stacks.size()); i++) {
			buttons.add(new FolderButton(guiLeft + 25 + (27 * (i % 5)), guiTop + 26 + (27 * (int) Math.floor((i / 5D))) - currentPage * 27 * 7, stacks.get(i)));
		}

		if(currentPage != 0)
			buttons.add(new FolderButton(guiLeft + 25 - 18, guiTop + 26 + (27 * 3), 1, "Previous"));
		if(currentPage != getPageCount())
			buttons.add(new FolderButton(guiLeft + 25 + (27 * 4) + 18, guiTop + 26 + (27 * 3), 2, "Next"));
	}

	@Override
	public void handleMouseInput() {
		super.handleMouseInput();

		if(Mouse.getEventButton() == -1) {
			int scroll = Mouse.getEventDWheel();

			if(scroll > 0) {
				if(currentPage > 0)
					currentPage--;
				updateButtons();
			} else if(scroll < 0) {
				if(currentPage < getPageCount())
					currentPage++;
				updateButtons();
			}
		}
	}

	@Override
	protected void mouseClicked(int i, int j, int k) {
		
		if(i >= guiLeft + 45 && i < guiLeft + 117 && j >= guiTop + 211 && j < guiTop + 223) {
			this.search.setFocused(true);
		} else  {
			this.search.setFocused(false);
		}

		try {
			for(FolderButton b : buttons)
				if(b.isMouseOnButton(i, j))
					b.executeAction();
		} catch(Exception ex) {
			updateButtons();
		}
	}

	protected void drawGuiContainerForegroundLayer(int i, int j) {

		this.fontRendererObj.drawString(I18n.format((currentPage + 1) + "/" + (getPageCount() + 1)), guiLeft + this.xSize / 2 - this.fontRendererObj.getStringWidth(I18n.format((currentPage + 1) + "/" + (getPageCount() + 1))) / 2, guiTop + 10, isJournal ? 4210752 : 0xffffff);

		for(FolderButton b : buttons)
			if(b.isMouseOnButton(i, j))
				b.drawString(i, j);
	}

	protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		
		if(!isJournal)
			Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		else
			Minecraft.getMinecraft().getTextureManager().bindTexture(texture_journal);
		
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

		if(search.isFocused())
			drawTexturedModalRect(guiLeft + 45, guiTop + 211, 176, 54, 72, 12);

		for(FolderButton b : buttons)
			b.drawButton(b.isMouseOnButton(i, j));
		for(FolderButton b : buttons)
			b.drawIcon(b.isMouseOnButton(i, j));
		
		search.drawTextBox();
	}

	@Override
	protected void keyTyped(char p_73869_1_, int p_73869_2_) {
		
		if (this.search.textboxKeyTyped(p_73869_1_, p_73869_2_)) {
			this.search(this.search.getText());
			return;
		}
		
		if(p_73869_2_ == 1 || p_73869_2_ == this.mc.gameSettings.keyBindInventory.getKeyCode()) {
			this.mc.thePlayer.closeScreen();
		}

	}

	class FolderButton {

		int xPos;
		int yPos;
		// 0: regular, 1: prev, 2: next
		int type;
		String info;
		ItemStack stack;

		public FolderButton(int x, int y, int t, String i) {
			xPos = x;
			yPos = y;
			type = t;
			info = i;
		}

		public FolderButton(int x, int y, ItemStack stack) {
			xPos = x;
			yPos = y;
			type = 0;
			info = stack.getDisplayName();
			this.stack = stack.copy();
		}

		public void updateButton(int mouseX, int mouseY) {
		}

		public boolean isMouseOnButton(int mouseX, int mouseY) {
			return xPos <= mouseX && xPos + 18 > mouseX && yPos < mouseY && yPos + 18 >= mouseY;
		}

		public void drawButton(boolean b) {
			
			if(!isJournal)
				Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
			else
				Minecraft.getMinecraft().getTextureManager().bindTexture(texture_journal);
			
			drawTexturedModalRect(xPos, yPos, b ? 176 + 18 : 176, type == 1 ? 18 : (type == 2 ? 36 : 0), 18, 18);
		}

		public void drawIcon(boolean b) {
			try {
				RenderHelper.enableGUIStandardItemLighting();
				GL11.glEnable(GL12.GL_RESCALE_NORMAL);
				OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float) 240 / 1.0F, (float) 240 / 1.0F);
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
				if(stack != null) {
					if(stack.getItem() == ModItems.assembly_template)
						itemRender.renderItemAndEffectIntoGUI(fontRendererObj, mc.getTextureManager(), AssemblerRecipes.getOutputFromTempate(stack), xPos + 1, yPos + 1);
					else if(stack.getItem() == ModItems.chemistry_template)
						itemRender.renderItemAndEffectIntoGUI(fontRendererObj, mc.getTextureManager(), new ItemStack(ModItems.chemistry_icon, 1, stack.getItemDamage()), xPos + 1, yPos + 1);
					else if(stack.getItem() == ModItems.crucible_template)
						itemRender.renderItemAndEffectIntoGUI(fontRendererObj, mc.getTextureManager(), CrucibleRecipes.indexMapping.get(stack.getItemDamage()).icon, xPos + 1, yPos + 1);
					else
						itemRender.renderItemAndEffectIntoGUI(fontRendererObj, mc.getTextureManager(), stack, xPos + 1, yPos + 1);
				}
				GL11.glEnable(GL11.GL_LIGHTING);
			} catch(Exception x) {
			}
		}

		public void drawString(int x, int y) {
			if(info == null || info.isEmpty())
				return;
			
			if(stack != null) {
				GUIScreenTemplateFolder.this.renderToolTip(stack, x, y);
			} else {
				func_146283_a(Arrays.asList(new String[] { info }), x, y);
			}
		}

		public void executeAction() {
			mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
			if(type == 0) {
				PacketDispatcher.wrapper.sendToServer(new ItemFolderPacket(stack.copy()));
			} else if(type == 1) {
				if(currentPage > 0)
					currentPage--;
				updateButtons();
			} else if(type == 2) {
				if(currentPage < getPageCount())
					currentPage++;
				updateButtons();
			}
		}
	}

	@Override
	public void onGuiClosed() {
		Keyboard.enableRepeatEvents(false);
	}
}
