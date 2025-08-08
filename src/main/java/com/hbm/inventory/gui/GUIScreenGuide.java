package com.hbm.inventory.gui;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.math.NumberUtils;
import org.lwjgl.opengl.GL11;

import com.hbm.items.tool.ItemGuideBook.BookType;
import com.hbm.items.tool.ItemGuideBook.GuideImage;
import com.hbm.items.tool.ItemGuideBook.GuidePage;
import com.hbm.items.tool.ItemGuideBook.GuideText;
import com.hbm.lib.RefStrings;
import com.hbm.util.i18n.I18nUtil;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public class GUIScreenGuide extends GuiScreen {

	private static final ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/book/book.png");
	private static final ResourceLocation texture_cover = new ResourceLocation(RefStrings.MODID + ":textures/gui/book/book_cover.png");

	protected int xSize;
	protected int ySize;
	protected int guiLeft;
	protected int guiTop;
	
	private BookType type;
	
	int page;
	int maxPage;

	public GUIScreenGuide(EntityPlayer player) {
		
		type = BookType.getType(player.getHeldItem().getItemDamage());
		
		page = -1;
		maxPage = (int)Math.ceil(type.pages.size() / 2D) - 1;

		this.xSize = 272;
		this.ySize = 182;
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
		
		if(page < 0) {
			Minecraft.getMinecraft().getTextureManager().bindTexture(texture_cover);
			func_146110_a(guiLeft, guiTop, 0, 0, xSize, ySize, 512, 512);
			return;
		}
		
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		func_146110_a(guiLeft, guiTop, 0, 0, xSize, ySize, 512, 512);

		boolean overLeft = i >= guiLeft + 24 && i < guiLeft + 42 && j >= guiTop + 155 && j < guiTop + 165;
		boolean overRight = i >= guiLeft + 230 && i < guiLeft + 248 && j >= guiTop + 155 && j < guiTop + 165;

		if(this.page > 0) {
			
			if(!overLeft)
				func_146110_a(guiLeft + 24, guiTop + 155, 3, 207, 18, 10, 512, 512);
			else
				func_146110_a(guiLeft + 24, guiTop + 155, 26, 207, 18, 10, 512, 512);
		}
		
		if(this.page < this.maxPage) {
			
			if(!overRight)
				func_146110_a(guiLeft + 230, guiTop + 155, 3, 194, 18, 10, 512, 512);
			else
				func_146110_a(guiLeft + 230, guiTop + 155, 26, 194, 18, 10, 512, 512);
		}
	}

	public static void drawImage(int x, int y, int dimX, int dimY) {
		
		Tessellator tessellator = Tessellator.instance;
		tessellator.setBrightness(240);
		tessellator.setColorRGBA_F(1.0F, 1.0F, 1.0F, 1.0F);
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV(x,			y + dimY,	0.0D,	0,	1);
		tessellator.addVertexWithUV(x + dimX,	y + dimY,	0.0D,	1,	1);
		tessellator.addVertexWithUV(x + dimX,	y,			0.0D,	1,	0);
		tessellator.addVertexWithUV(x,			y,			0.0D,	0,	0);
		tessellator.draw();
	}

	protected void drawGuiContainerForegroundLayer(int x, int y) {
		
		if(this.page < 0) {
			
			float scale = this.type.titleScale;
			String[] coverLines = I18nUtil.resolveKeyArray(this.type.title);
			
			for(int i = 0; i < coverLines.length; i++) {
				
				String cover = coverLines[i];
				
				GL11.glPushMatrix();
				GL11.glScalef(scale, scale, 1F);
				this.fontRendererObj.drawString(cover, (int)((guiLeft + ((this.xSize / 2) - (this.fontRendererObj.getStringWidth(cover) / 2 * scale))) / scale), (int)((guiTop + 50 + i * 10 * scale) / scale), 0xfece00);
				GL11.glPopMatrix();
			}
			
			return;
		}
		
		int sideOffset = 130;
		
		for(int i = 0; i < 2; i++) {
			
			int defacto = this.page * 2 + i;
			
			if(defacto < this.type.pages.size()) {
				
 				GuidePage page = this.type.pages.get(defacto);
				
 				for(GuideText textBox : page.texts) {
 					float scale = textBox.scale;
 					String text = I18nUtil.resolveKey(textBox.text);
 					int width = textBox.width;
 					
 					int widthScaled = (int) (width * scale);
 					List<String> lines = new ArrayList();
 					String[] words = text.split(" ");
 					
 					lines.add(words[0]);
 					int indent = this.fontRendererObj.getStringWidth(words[0]);
 					
 					for(int w = 1; w < words.length; w++) {
 						
 						indent += this.fontRendererObj.getStringWidth(" " + words[w]);
 						
 						if(indent <= widthScaled) {
 							String last = lines.get(lines.size() - 1);
 							lines.set(lines.size() - 1, last += (" " + words[w]));
 						} else {
 							lines.add(words[w]);
 							indent = this.fontRendererObj.getStringWidth(words[w]);
 						}
 					}
 					
 					float titleScale = getOverrideScale(page.titleScale, page.title + ".scale");
 					
 					GL11.glPushMatrix();
 					GL11.glScalef(1F/scale, 1F/scale, 1F);
 					
 					float topOffset;
 					
 					if(textBox.yOffset == -1) {
 	 					topOffset = page.title == null ? -10 : 6 / titleScale;
 					} else {
 						topOffset = textBox.yOffset;
 					}
 					
 					for(int l = 0; l < lines.size(); l++) {
 						this.fontRendererObj.drawString(lines.get(l), (int)((guiLeft + 20 + i * sideOffset + textBox.xOffset) * scale), (int)((guiTop + 30 + topOffset) * scale + (12 * l)), 4210752);
 					}
 					
 					GL11.glPopMatrix();
 				}
				
				if(page.title != null) {
					
					float tScale = page.titleScale;
					String titleLoc = I18nUtil.resolveKey(page.title);
					
					GL11.glPushMatrix();
					GL11.glScalef(1F/tScale, 1F/tScale, 1F);
					this.fontRendererObj.drawString(titleLoc, (int)((guiLeft + 20 + i * sideOffset + ((100 / 2) - (this.fontRendererObj.getStringWidth(titleLoc) / 2 / tScale))) * tScale), (int)((guiTop + 20) * tScale), page.titleColor);
					
					GL11.glPopMatrix();
				}
				
				if(!page.images.isEmpty()) {
					for(GuideImage image : page.images) {
						GL11.glColor4f(1F, 1F, 1F, 1F);
						Minecraft.getMinecraft().getTextureManager().bindTexture(image.image);
						
						int ix = image.x;
						
						if(ix == -1)
							ix = 100 / 2 - image.sizeX / 2;
						
						drawImage(guiLeft + 20 + ix + sideOffset * i, guiTop + image.y, image.sizeX, image.sizeY);
					}
				}
				
				String pageLabel = (defacto + 1) + "/" + (this.type.pages.size());
				this.fontRendererObj.drawString(pageLabel, guiLeft + 44 + i * 185 - i * this.fontRendererObj.getStringWidth(pageLabel), guiTop + 156, 4210752);
			}
		}
	}
	
	private float getOverrideScale(float def, String tag) {
		
		String scale = I18nUtil.resolveKey(tag);
		
		if(NumberUtils.isNumber(scale)) {
			return 1F / NumberUtils.toFloat(scale);
		}
		
		return def;
	}

	@Override
	protected void mouseClicked(int i, int j, int k) {
		
		if(page < 0) {
			page = 0;
			mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
			return;
		}
		
		boolean overLeft = i >= guiLeft + 24 && i < guiLeft + 42 && j >= guiTop + 155 && j < guiTop + 165;
		boolean overRight = i >= guiLeft + 230 && i < guiLeft + 248 && j >= guiTop + 155 && j < guiTop + 165;
		
		if(overLeft && page > 0) {
			page--;
			mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
		}
		
		if(overRight && page < maxPage) {
			page++;
			mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
		}
	}

	@Override
	protected void keyTyped(char p_73869_1_, int p_73869_2_) {
		if(p_73869_2_ == 1 || p_73869_2_ == Minecraft.getMinecraft().gameSettings.keyBindInventory.getKeyCode()) {
			Minecraft.getMinecraft().thePlayer.closeScreen();
		}
	}
}
