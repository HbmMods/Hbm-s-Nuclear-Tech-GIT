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
import net.minecraft.util.ResourceLocation;

// and you may ask yourself: vaer, why do this? this is basically just a copy of GUIScreenGuide.
// and I would answer, shut the fuck up nerd, the guide book system is too involved for my small
// brain to use for god knows how many tidbits of lore. i'll settle for a text box and cool textures, thanks
public class GUIBookLore extends GuiScreen {
	
	protected int xSize;
	protected int ySize;
	protected int guiLeft;
	protected int guiTop;
	
	private BookLoreType type;
	private GUIAppearance setup;
	
	int page = 0;
	int maxPage;
	
	public GUIBookLore(EntityPlayer player) {
		
		type = BookLoreType.getTypeFromStack(player.getHeldItem());
		setup = type.appearance;
		
		if(setup.mainPage.isTwoPages && type.pages <= 1) {
			xSize = setup.auxPage.sizeX;
			ySize = setup.auxPage.sizeY;
		} else {
			xSize = setup.mainPage.sizeX;
			ySize = setup.mainPage.sizeY;
		}
		
		maxPage = setup.mainPage.isTwoPages ? (int)Math.ceil(type.pages / 2D) - 1 : type.pages;
		System.out.print((int)Math.ceil(type.pages / 2D) - 1);
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
		
		if(page == maxPage && setup.mainPage.isTwoPages && this.page * 2 >= this.type.pages) //odd numbered pages
			Minecraft.getMinecraft().getTextureManager().bindTexture(setup.auxPage.texture);
		else
			Minecraft.getMinecraft().getTextureManager().bindTexture(setup.mainPage.texture);
		
		func_146110_a(guiLeft, guiTop, 0, 0, xSize, ySize, 512, 512);
		
		if(page > 0)
			setup.button.renderButton(this, xSize, guiLeft, guiTop, false, i, j);
		
		if(page < maxPage)
			setup.button.renderButton(this, xSize, guiLeft, guiTop, true, i, j);
	}
	
	protected void drawGuiContainerForegroundLayer(int x, int y) {
		String key = "book_lore." + type.keyI18n + ".page.";
		
		if(setup.mainPage.isTwoPages) {
			int defacto = this.page * 2 + 1;
			
			if((this.page + 1) * 2 <= this.type.pages) { //TODO: change this to make it accurate for odd-numbered max pages
				setup.mainPage.renderText(key + defacto, fontRendererObj, guiLeft, guiTop, false);
				setup.mainPage.renderText(key + (defacto + 1), fontRendererObj, guiLeft, guiTop, true);
			} else
				setup.auxPage.renderText(key + defacto, fontRendererObj, guiLeft, guiTop, false);
			
		} else {
			setup.mainPage.renderText(key + page, fontRendererObj, guiLeft, guiTop, false);
		}
		
	}
	
	@Override
	protected void mouseClicked(int i, int j, int k) {
		int q = 0; //if both buttons are somehow simultaneously clicked then obviously something's wrong already
		
		if(page > 0)
			q = setup.button.handleInput(xSize, guiLeft, guiTop, false, i, j);
		
		if(page < maxPage && q == 0)
			q = setup.button.handleInput(xSize, guiLeft, guiTop, true, i, j);
		
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
		GUIDEBOOK(new GUIPage(272, 182, 20, 20, new ResourceLocation(RefStrings.MODID + ":textures/gui/book/book.png")).setScale(2F), 
				new GUIPageButton(18, 10, 24, 155, 0, 0, 500, 200, new ResourceLocation(RefStrings.MODID + ":textures/gui/book/starter6.png")),
				0);
		
		public int itemTexture;
		
		protected GUIPage mainPage; //"Main" page, usually two pages. GUI accounts for one-paged main pages.
		protected GUIPage auxPage; //"Aux" page, AKA the final page if the max pages is oddly numbered.
		//If two-sided, text will be positioned on the left page. Can be null if main page is one-sided.
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
		
		protected int sizeX;
		protected int sizeY;
		
		//Text positioning
		protected int marginX; //Boundaries of the textbook, relative to the pages' edges.
		protected int marginY; //Mirrored on both sides if two-sided.
		protected boolean isTwoPages = true;
		protected float scale = 1.0F;
		
		protected GUIPage(int x, int y, int marX, int marY, ResourceLocation texture, boolean twoPages) {
			this.sizeX = x;
			this.sizeY = y;
			this.marginX = marX;
			this.marginY = marY;
			this.texture = texture;
			this.isTwoPages = twoPages;
		}
		
		protected GUIPage(int x, int y, int marX, int marY, ResourceLocation texture) {
			this.sizeX = x;
			this.sizeY = y;
			this.marginX = marX;
			this.marginY = marY;
			this.texture = texture;
		}
		
		protected GUIPage setScale(float scale) {
			this.scale = scale;
			return this;
		}
		
		protected void renderText(String key, FontRenderer renderer, int left, int top, boolean secondPage) {
			String text = I18nUtil.resolveKey(key);
			int width = isTwoPages ? (sizeX / 2) - (marginX * 2) : sizeX - (marginX * 2);
			int widthScaled = (int) (width * scale);
			
			List<String> lines = new ArrayList();
			String[] words = text.split(" ");
			
			lines.add(words[0]);
			int indent = renderer.getStringWidth(words[0]);
			
			for(int w = 1; w < words.length; w++) {
					
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
			
			int sideOffset = secondPage ? sizeX - marginX - width : marginX;
			
			for(int l = 0; l < lines.size(); l++) {
				renderer.drawString(lines.get(l), (int)((left + sideOffset) * scale), (int)((top + marginY) * scale + (12 * l)), 4210752);
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
		protected int u; //upper lefthand corner where the button textures lie.
		protected int v; //assumes uniform size for each.
		protected int sizeU;
		protected int sizeV;
		
		protected GUIPageButton(int sizeX, int sizeY, int x, int y, int u, int v, int sizeU, int sizeV, ResourceLocation tex) {
			this.sizeX = sizeX;
			this.sizeY = sizeY;
			this.x = x;
			this.y = y;
			this.u = u;
			this.v = v;
			this.texture = tex;
			this.sizeU = sizeU;
			this.sizeV = sizeV;
		}
		
		protected void renderButton(GuiScreen screen, int width, int left, int top, boolean rightPage, int i, int j) {
			Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
			boolean overY = j >= top + y && j < top + y + sizeY;
			
			if(!rightPage) {
				if(i >= left + x && i < left + x + sizeX && overY) {
					func_146110_a(left + x, top + y, u, v + sizeY, sizeX, sizeY, sizeX * 2, sizeY * 2);
				} else {
					func_146110_a(left + x, top + y, u, v, sizeX, sizeY, sizeX * 2, sizeY * 2);
				}
			} else {
				if(i >= left + width - x - sizeX && i < left + width - x && overY) {
					func_146110_a(left + width - x - sizeX, top + y, u + sizeX, v + sizeY, sizeX, sizeY, sizeX * 2, sizeY * 2);
				} else {
					func_146110_a(left + width - x - sizeX, top + y, u + sizeX, v, sizeX, sizeY, sizeX * 2, sizeY * 2);
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