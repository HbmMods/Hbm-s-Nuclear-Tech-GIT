package com.hbm.qmaw;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import com.hbm.lib.RefStrings;
import com.hbm.qmaw.components.*;

import cpw.mods.fml.common.FMLCommonHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.LanguageManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

public class GuiQMAW extends GuiScreen {

	protected static final ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/gui_wiki.png");
	
	public String title;
	public String qmawID;
	public ItemStack icon;
	public List<List<ManualElement>> lines = new ArrayList();
	/** History for returning via button */
	public List<String> back = new ArrayList();
	public List<String> forward = new ArrayList();
	
	protected int xSize = 340;
	protected int ySize = 224;
	protected int guiLeft;
	protected int guiTop;
	
	protected boolean isDragging = false;
	protected int scrollProgress = 0;
	protected int lastClickX = 0;
	protected int lastClickY = 0;
	
	public static final String EN_US = "en_US";
	
	public GuiQMAW(QuickManualAndWiki qmaw) {
		qmawID = qmaw.name;
		parseQMAW(qmaw);
	}
	
	protected void parseQMAW(QuickManualAndWiki qmaw) {
		LanguageManager lang = Minecraft.getMinecraft().getLanguageManager();
		
		this.title = qmaw.title.get(lang.getCurrentLanguage());
		if(title == null) this.title = qmaw.title.get(EN_US);
		if(title == null) this.title = "Missing Localization!";
		
		this.icon = qmaw.icon;
		
		String toParse = qmaw.contents.get(lang.getCurrentLanguage());
		if(toParse == null) toParse = qmaw.contents.get(EN_US);
		if(toParse == null) toParse = "Missing Localization!";
		toParse = "" + toParse; // strings are reference types, no?
		
		int maxLineLength = xSize - 29;
		String prevToParse = "" + toParse;
		int maxIterations = 1000;
		int currentLineWidth = 0;
		
		while(!toParse.isEmpty() && maxIterations > 0) {
			if(this.lines.isEmpty()) this.lines.add(new ArrayList());
			List<ManualElement> currentLine = this.lines.get(this.lines.size() - 1);
			
			toParse = toParse.trim();
			
			maxIterations--;
			
			if(toParse.startsWith("<br>")) {
				toParse = toParse.substring(4);
				currentLine = new ArrayList();
				this.lines.add(currentLine);
				currentLineWidth = 0;
				continue;
			}
			
			// handle links
			if(toParse.startsWith("[[")) {
				int end = toParse.indexOf("]]");
				if(end != -1) {
					String link = toParse.substring(2, end);
					toParse = toParse.substring(end + 2);
					
					int pipe = link.indexOf("|");
					QComponentLink linkComponent;
					
					String suffix = toParse.startsWith(" ") ? " " : "";
					
					if(pipe == -1) {
						linkComponent = new QComponentLink(link, link + suffix);
					} else {
						linkComponent = new QComponentLink(link.substring(pipe + 1, link.length()), link.substring(0, pipe) + suffix);
					}
					
					// append to current line
					int width = linkComponent.getWidth();
					if(width + currentLineWidth <= maxLineLength) {
						currentLine.add(linkComponent);
						currentLineWidth += width;
					// new line
					} else {
						currentLine = new ArrayList();
						this.lines.add(currentLine);
						currentLine.add(linkComponent);
						currentLineWidth = width;
					}

					prevToParse = "" + toParse;
					continue;
				}
			}
			
			// handle standard text
			int delimit = toParse.length();
			
			int spaceIndex = toParse.indexOf(" ");
			if(spaceIndex != -1) delimit = Math.min(delimit, spaceIndex);
			int linkIndex = toParse.indexOf("[[");
			if(linkIndex != -1) delimit = Math.min(delimit, linkIndex);
			int brIndex = toParse.indexOf("<br>");
			if(brIndex != -1) delimit = Math.min(delimit, brIndex);
			
			if(delimit > 0) {
				QComponentText textComponent = new QComponentText(toParse.substring(0, delimit) + (spaceIndex == delimit ? " " : ""));
				toParse = toParse.substring(delimit);
				
				// append to current line
				int width = textComponent.getWidth();
				if(width + currentLineWidth <= maxLineLength) {
					currentLine.add(textComponent);
					currentLineWidth += width;
				// new line
				} else {
					currentLine = new ArrayList();
					this.lines.add(currentLine);
					currentLine.add(textComponent);
					currentLineWidth = width;
				}

				prevToParse = "" + toParse;
				continue;
			}
			
			if(toParse.equals(prevToParse)) break;
			prevToParse = "" + toParse;
		}
	}

	@Override
	public void initGui() {
		super.initGui();
		this.guiLeft = (this.width - this.xSize) / 2;
		this.guiTop = (this.height - this.ySize) / 2;
	}

	@Override
	protected void mouseClicked(int x, int y, int key) {
		super.mouseClicked(x, y, key);
		
		if(key == 0) {
			this.lastClickX = x;
			this.lastClickY = y;
		}

		if(guiLeft + 3 <= x && guiLeft + 3 + 18 > x && guiTop + 3 < y && guiTop + 3 + 18 >= y) back();
		if(guiLeft + 21 <= x && guiLeft + 21 + 18 > x && guiTop + 3 < y && guiTop + 3 + 18 >= y) forward();
	}
	
	public void back() {
		if(this.back.isEmpty()) return;
		
		String prev = back.get(back.size() - 1);

		QuickManualAndWiki qmaw = QMAWLoader.qmaw.get(prev);
		if(qmaw != null) {
			Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
			GuiQMAW screen = new GuiQMAW(qmaw);
			screen.back.addAll(back);
			screen.back.remove(screen.back.size() - 1);
			screen.forward.addAll(forward);
			screen.forward.add(qmawID);
			FMLCommonHandler.instance().showGuiScreen(screen);
		}
	}
	
	public void forward() {
		if(this.forward.isEmpty()) return;
		
		String next = forward.get(forward.size() - 1);

		QuickManualAndWiki qmaw = QMAWLoader.qmaw.get(next);
		if(qmaw != null) {
			Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
			GuiQMAW screen = new GuiQMAW(qmaw);
			screen.back.addAll(back);
			screen.back.add(qmawID);
			screen.forward.addAll(forward);
			screen.forward.remove(screen.forward.size() - 1);
			FMLCommonHandler.instance().showGuiScreen(screen);
		}
	}
	
	public int getSliderPosition() {
		double progress = (double) scrollProgress / (double) (lines.size() - 1);
		return 25 + (int) (progress * 180);
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		
		if(Mouse.isButtonDown(0) && guiLeft + xSize - 15 <= mouseX && guiLeft + xSize - 15 + 12 > mouseX && guiTop + 25 < mouseY && guiTop + 25 + 191 >= mouseY) {
			isDragging = true;
		}
		
		if(!Mouse.isButtonDown(0)) isDragging = false;
		
		if(isDragging) {
			int min = guiTop + 25 + 8;
			int max = guiTop + 25 + 191 - 8;
			int span = max - min;
			
			double progress = MathHelper.clamp_double((double) (mouseY - min) / span, 0D, 1D);
			this.scrollProgress = MathHelper.clamp_int((int) Math.round((lines.size() - 1) * progress), 0, lines.size() - 1);
		}
		
		handleScroll();

		//this.drawRect(0, 0, this.width, this.height, 0x80919191);
		this.drawRect(0, 0, this.width, this.height, 0xe0000000);
		
		this.drawGuiContainerBackgroundLayer(f, mouseX, mouseY);
		GL11.glDisable(GL11.GL_LIGHTING);
		this.drawGuiContainerForegroundLayer(mouseX, mouseY);
		GL11.glEnable(GL11.GL_LIGHTING);
		
		this.lastClickX = 0;
		this.lastClickY = 0;
	}
	
	protected void handleScroll() {
		
		if(!Mouse.isButtonDown(0) && !Mouse.isButtonDown(1) && Mouse.next()) {
			int scroll = Mouse.getEventDWheel();
			if(scroll > 0 && this.scrollProgress > 0) this.scrollProgress--;
			if(scroll < 0 && this.scrollProgress < this.lines.size() - 1) this.scrollProgress++;
		}
	}

	private void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		
		int x = 43;
		int y = 4;
		
		if(this.icon != null) {
			GL11.glPushMatrix();
			GL11.glEnable(GL11.GL_DEPTH_TEST);
			Minecraft mc = Minecraft.getMinecraft();
			GL11.glRotated(180, 1, 0, 0);
			RenderHelper.enableStandardItemLighting();
			GL11.glRotated(-180, 1, 0, 0);
			itemRender.renderItemAndEffectIntoGUI(this.fontRendererObj, mc.renderEngine, this.icon, guiLeft + x, guiTop + y);
			itemRender.renderItemOverlayIntoGUI(this.fontRendererObj, mc.renderEngine, this.icon, guiLeft + x, guiTop + y, null);
			RenderHelper.disableStandardItemLighting();
			GL11.glDisable(GL11.GL_DEPTH_TEST);
			GL11.glPopMatrix();
			
			x += 18;
			y += (16 - this.fontRendererObj.FONT_HEIGHT) / 2;
		}
		
		y += 1;
		
		this.fontRendererObj.drawString(title, guiLeft + x, guiTop + y, 0xFFFFFF);
	}

	private void drawGuiContainerBackgroundLayer(float f, int mouseX, int mouseY) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, 170, ySize);
		drawTexturedModalRect(guiLeft + 170, guiTop, 52, 0, 30, ySize);
		drawTexturedModalRect(guiLeft + 200, guiTop, 52, 0, 140, ySize);

		if(!back.isEmpty()) drawTexturedModalRect(guiLeft + 3, guiTop + 3, 204, 0, 18, 18);
		if(!forward.isEmpty()) drawTexturedModalRect(guiLeft + 21, guiTop + 3, 222, 0, 18, 18);
		
		// scroll bar
		drawTexturedModalRect(guiLeft +  xSize - 15, guiTop + getSliderPosition(), 192, 0, 12, 16);
		
		int x = guiLeft + 7;
		int y = guiTop + 30;
		int lineNum = 0;
		
		for(List<ManualElement> line : lines) {
			lineNum++;
			
			if(lineNum <= this.scrollProgress) continue;
			
			int maxHeight = 0;
			int inset = 0;
			
			for(ManualElement element : line) {
				maxHeight = Math.max(maxHeight, element.getHeight());
			}
			
			if(y + maxHeight > guiTop + 219) break;
			
			if(line.isEmpty()) y += this.fontRendererObj.FONT_HEIGHT;
			
			for(ManualElement element : line) {
				int elementX = x + inset;
				int elementY = y + (maxHeight - element.getHeight()) / 2;
				boolean mouseOver = (elementX <= mouseX && elementX + element.getWidth() > mouseX && elementY < mouseY && elementY + element.getHeight() >= mouseY);
				element.render(mouseOver, elementX, elementY, mouseX, mouseY);
				if(elementX <= lastClickX && elementX + element.getWidth() > lastClickX && elementY < lastClickY && elementY + element.getHeight() >= lastClickY)
					element.onClick(this);
				inset += element.getWidth();
			}
			
			y += maxHeight + 2;
		}
	}

	@Override
	protected void keyTyped(char typedChar, int keyCode) {

		if(keyCode == Keyboard.KEY_LEFT) back();
		if(keyCode == Keyboard.KEY_RIGHT) forward();
		
		if(keyCode == 1 || keyCode == this.mc.gameSettings.keyBindInventory.getKeyCode()) {
			this.mc.displayGuiScreen((GuiScreen) null);
			this.mc.setIngameFocus();
		}
	}
}
