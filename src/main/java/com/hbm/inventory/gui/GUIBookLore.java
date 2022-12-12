package com.hbm.inventory.gui;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import com.hbm.items.special.ItemBookLore.*;
import com.hbm.items.tool.ItemGuideBook.BookType;
import com.hbm.lib.RefStrings;
import com.hbm.util.I18nUtil;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

// and you may ask yourself: vaer, why do this? this is basically just a copy of GUIScreenGuide.
// and I would answer, shut the fuck up nerd, the guide book system is too involved for my small
// brain to use for god knows how many tidbits of lore. i'll settle for a text box and cool textures, thanks
public class GUIBookLore extends GuiScreen {
	protected int xSize;
	protected int ySize;
	protected int guiLeft;
	protected int guiTop;
	
	private NBTTagCompound tag; //Used for save-dependent information, like the MKU recipe
	private BookLoreType type;
	
	public int itemTexture;
	
	protected GUIPage mainPage;
	protected GUIPage auxPage;
	protected GUIPageButton button;
	
	int page = 0;
	int maxPage;
	
	public GUIBookLore(EntityPlayer player) {
		
		type = BookLoreType.getTypeFromStack(player.getHeldItem());
		tag = player.getHeldItem().getTagCompound(); //compound is created or gotten in method above
		GUIAppearance setup = type.appearance;
		
		mainPage = setup.mainPage;
		auxPage = setup.auxPage;
		button = setup.button;
		itemTexture = setup.itemTexture;
		
		if(type.pages <= 1) {
			xSize = auxPage.sizeX;
			ySize = auxPage.sizeY;
		} else {
			xSize = mainPage.sizeX;
			ySize = mainPage.sizeY;
		}
		
		maxPage = mainPage.isTwoPages ? (int)Math.ceil(type.pages / 2D) - 1 : type.pages - 1;
	}
	
	@Override
	public void initGui() {
		super.initGui();
		this.guiLeft = (this.width - this.xSize) / 2;
		this.guiTop = (this.height - this.ySize) / 2;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		this.drawDefaultBackground();
		this.drawGuiContainerBackgroundLayer(f, mouseX, mouseY);
		GL11.glDisable(GL11.GL_LIGHTING);
		this.drawGuiContainerForegroundLayer(mouseX, mouseY);
		GL11.glEnable(GL11.GL_LIGHTING);
	}
	
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		
		if(page == maxPage && (page + 1) * 2 > type.pages) { //odd numbered pages
			Minecraft.getMinecraft().getTextureManager().bindTexture(auxPage.texture);
			func_146110_a(guiLeft, guiTop, auxPage.u, auxPage.v, auxPage.sizeX, auxPage.sizeY, 512, 512);
		} else {
			Minecraft.getMinecraft().getTextureManager().bindTexture(mainPage.texture);
			func_146110_a(guiLeft, guiTop, mainPage.u, mainPage.v, mainPage.sizeX, mainPage.sizeY, 512, 512);
		}
		
		int width = page == maxPage && (page + 1) * 2 > type.pages ? auxPage.sizeX : mainPage.sizeX;
		
		if(page > 0)
			button.renderButton(this, width, guiLeft, guiTop, false, i, j);
		
		if(page < maxPage)
			button.renderButton(this, width, guiLeft, guiTop, true, i, j);
	}
	
	protected void drawGuiContainerForegroundLayer(int x, int y) {
		String key = "book_lore." + type.keyI18n + ".page.";
		
		if(mainPage.isTwoPages) {
			int defacto = page * 2 + 1;
			String text = type.resolveKey(key + defacto, tag);
			
			if((page + 1) * 2 <= type.pages) { //Checks if text should be rendered as an aux or a main page
				mainPage.renderText(text, fontRendererObj, guiLeft, guiTop, false);
				
				text = type.resolveKey(key + (defacto + 1), tag); //kinda awkward, but no way around it
				mainPage.renderText(text, fontRendererObj, guiLeft, guiTop, true);
			} else
				auxPage.renderText(text, fontRendererObj, guiLeft, guiTop, false);
			
		} else {
			String text = type.resolveKey(key + (page + 1), tag);
			
			if(page < maxPage)
				mainPage.renderText(text, fontRendererObj, guiLeft, guiTop, false);
			else
				auxPage.renderText(text, fontRendererObj, guiLeft, guiTop, false);
		}
		
	}
	
	@Override
	protected void mouseClicked(int i, int j, int k) {
		int q = 0; //if both buttons are somehow simultaneously clicked then obviously something's wrong already
		
		if(page > 0)
			q = button.handleInput(xSize, guiLeft, guiTop, false, i, j);
		
		if(page < maxPage && q == 0)
			q = button.handleInput(xSize, guiLeft, guiTop, true, i, j);
		
		if(q != 0) {
			mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
			this.page += q;
		}
	}
	
	@Override
	protected void keyTyped(char c, int key) {
		if(key == 1 || key == this.mc.gameSettings.keyBindInventory.getKeyCode()) {
			this.mc.thePlayer.closeScreen();
		}
	}
	
	// turn page buttons, one-page, both page textures, sizes, positions, etc.
	public enum GUIAppearance {
		GUIDEBOOK(new GUIPage(272, 182, new ResourceLocation(RefStrings.MODID + ":textures/gui/book/book.png")).setScale(2F).setMargins(20, 20, 20), 
				new GUIPageButton(18, 10, 17, 148, new ResourceLocation(RefStrings.MODID + ":textures/gui/book/notebook_and_papers.png")).setUV(263, 0, 512, 512),
				0), //Guide Book
		LOOSEPAPER(new GUIPage(130, 165, new ResourceLocation(RefStrings.MODID + ":textures/gui/book/notebook_and_papers.png"), false).setMargins(12, 10, 16).setUV(133, 0),
				new GUIPageButton(18, 10, 17, 148, new ResourceLocation(RefStrings.MODID + ":textures/gui/book/notebook_and_papers.png")).setUV(263, 0, 512, 512),
				1), //Singular loose page
		LOOSEPAPERS(new GUIPage(133, 165, new ResourceLocation(RefStrings.MODID + ":textures/gui/book/notebook_and_papers.png"), false).setMargins(12, 10, 16),
				new GUIPageButton(18, 10, 17, 148, new ResourceLocation(RefStrings.MODID + ":textures/gui/book/notebook_and_papers.png")).setUV(263, 0, 512, 512),
				2), //Collection of loose pages
		NOTEBOOK(new GUIPage(133, 165, new ResourceLocation(RefStrings.MODID + ":textures/gui/book/notebook_and_papers.png"), false).setMargins(10, 10, 16).setUV(0, 165),
				new GUIPage(133, 165, new ResourceLocation(RefStrings.MODID + ":textures/gui/book/notebook_and_papers.png"), false).setMargins(10, 10, 16).setUV(133, 165),
				new GUIPageButton(18, 10, 17, 148, new ResourceLocation(RefStrings.MODID + ":textures/gui/book/notebook_and_papers.png")).setUV(263, 0, 512, 512),
				3);
		
		public int itemTexture;
		
		protected GUIPage mainPage; //"Main" page, usually two pages. GUI accounts for one-paged main pages.
		protected GUIPage auxPage; //"Aux" page, AKA the final page if the max pages is oddly numbered.
		//If two-sided, text will be positioned on the left page.
		protected GUIPageButton button;
		
		private GUIAppearance(GUIPage main, GUIPage aux, GUIPageButton button, int texture) {
			this.mainPage = main;
			this.auxPage = aux;
			this.button = button;
			this.itemTexture = texture;
		}
		
		private GUIAppearance(GUIPage main, GUIPageButton button, int texture) {
			this.mainPage = main;
			this.auxPage = main;
			this.button = button;
			this.itemTexture = texture;
		}
		
	}
	
	private static class GUIPage {
		protected ResourceLocation texture;
		
		//UV positioning
		protected int u = 0; //X/U pos in texture
		protected int v = 0; //Y/V pos in texture
		
		protected int sizeX; //X size of the page
		protected int sizeY; //Y size of the page
		
		//Text positioning
		protected int marginInner = 10; //Margin from inner edge of page
		protected int marginOuter = 10; //Margin from outer edge of page
		protected int marginY = 20; //Margin from upper edge of page
		protected boolean isTwoPages = true; //Has two pages to display text
		protected float scale = 1.0F; //Scale of the text; larger values are smaller
		protected int spacing = 9; //12 is a more comfortable spacing
		
		protected GUIPage(int x, int y, ResourceLocation texture, boolean twoPages) {
			this.sizeX = x;
			this.sizeY = y;
			this.texture = texture;
			this.isTwoPages = twoPages;
		}
		
		protected GUIPage(int x, int y, ResourceLocation texture) {
			this.sizeX = x;
			this.sizeY = y;
			this.texture = texture;
		}
		
		protected GUIPage setUV(int u, int v) {
			this.u = u;
			this.v = v;
			return this;
		}
		
		protected GUIPage setScale(float scale) {
			this.scale = scale;
			return this;
		}
		
		protected GUIPage setMargins(int inner, int outer, int upper) {
			this.marginInner = inner;
			this.marginOuter = outer;
			this.marginY = upper;
			return this;
		}
		
		protected GUIPage setSpacing(int spacing) {
			this.spacing = spacing;
			return this;
		}
		
		protected void renderText(String text, FontRenderer renderer, int left, int top, boolean secondPage) {
			int width = (isTwoPages ? sizeX / 2 : sizeX) - marginInner - marginOuter;
			int widthScaled = (int) (width * scale);
			
			List<String> lines = new ArrayList();
			String[] words = text.split(" ");
			
			lines.add(words[0]);
			int indent = renderer.getStringWidth(words[0]);
			
			for(int w = 1; w < words.length; w++) {
				
				if(words[w].equals("$")) {
					if(w + 1 < words.length && !words[w + 1].equals("$")) {
						lines.add(words[++w]);
						indent = renderer.getStringWidth(words[w]);
					} else
						lines.add("");
					
					continue;
				}
				
				indent += renderer.getStringWidth(" " + words[w]);
				
				if(indent <= widthScaled) {
					String last = lines.get(lines.size() - 1);
					lines.set(lines.size() - 1, last += (" " + words[w]));
				} else {
					lines.add(words[w]);
					indent = renderer.getStringWidth(words[w]);
				}
			}
			
			GL11.glPushMatrix();
			GL11.glScalef(1F/scale, 1F/scale, 1F);
			
			int sideOffset = secondPage ? sizeX / 2 + marginInner : marginOuter;
			
			for(int l = 0; l < lines.size(); l++) {
				renderer.drawString(lines.get(l), (int)((left + sideOffset) * scale), (int)((top + marginY) * scale + (spacing * l)), 4210752);
			}
			
			GL11.glPopMatrix();
		}
	}
	
	private static class GUIPageButton {
		protected ResourceLocation texture;
		
		protected int sizeX; //size of a single button; full texture is 2*sizeX : 2*sizeZ
		protected int sizeY;
		protected int x; //x position on page, relative to edge of the page it is on.
		protected int y; //y position on page, relative to the top edge of the page.
		
		/* Left, Unsel | Right, Unsel 
		 * Left, Sel   | Right, Sel
		 */
		protected int u = 0; //upper lefthand corner where the button textures lie.
		protected int v = 0; //assumes uniform size for each.
		protected int sizeU = sizeX * 2; //Size of UV texture
		protected int sizeV = sizeY * 2;
		
		protected GUIPageButton(int sizeX, int sizeY, int x, int y, ResourceLocation tex) {
			this.sizeX = sizeX;
			this.sizeY = sizeY;
			this.x = x;
			this.y = y;
			this.texture = tex;
		}
		
		protected GUIPageButton setUV(int u, int v, int sizeU, int sizeV) {
			this.u = u;
			this.v = v;
			this.sizeU = sizeU;
			this.sizeV = sizeV;
			return this;
		}
		
		protected void renderButton(GuiScreen screen, int width, int left, int top, boolean rightPage, int i, int j) {
			Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
			boolean overY = j >= top + y && j < top + y + sizeY;
			
			if(!rightPage) {
				if(i >= left + x && i < left + x + sizeX && overY) {
					func_146110_a(left + x, top + y, u, v + sizeY, sizeX, sizeY, sizeU, sizeV);
				} else {
					func_146110_a(left + x, top + y, u, v, sizeX, sizeY, sizeU, sizeV);
				}
			} else {
				if(i >= left + width - x - sizeX && i < left + width - x && overY) {
					func_146110_a(left + width - x - sizeX, top + y, u + sizeX, v + sizeY, sizeX, sizeY, sizeU, sizeV);
				} else {
					func_146110_a(left + width - x - sizeX, top + y, u + sizeX, v, sizeX, sizeY, sizeU, sizeV);
				}
			}
		}
		
		protected int handleInput(int width, int left, int top, boolean rightPage, int i, int j) {
			boolean overY = j >= top + y && j < top + y + sizeY;
			if(!rightPage) {
				if(i >= left + x && i < left + x + sizeX && overY)
					return -1;
			} else {
				if(i >= left + width - x - sizeX && i < left + width - x && overY)
					return 1;
			}
			
			return 0;
		}
	}
}