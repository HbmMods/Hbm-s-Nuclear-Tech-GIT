package com.hbm.inventory.gui;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import com.hbm.lib.RefStrings;
import com.hbm.util.i18n.I18nUtil;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

public class GUIBookLore extends GuiScreen {
	
	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/book/book_lore.png");
	
	protected int guiLeft;
	protected int guiTop;
	protected static int sizeX = 272;
	protected static int sizeY = 182;
	
	protected String key;
	protected NBTTagCompound tag;
	
	//judgement
	protected int color;
	
	protected int page;
	protected int maxPage;
	
	public GUIBookLore(EntityPlayer player) {
		ItemStack stack = player.getHeldItem();
		if(!stack.hasTagCompound()) return;
		this.tag = stack.getTagCompound();
		this.key = tag.getString("k");
		if(key.isEmpty()) return;
		
		this.color = tag.getInteger("cov_col");
		if(color <= 0)
			color = 0x303030;
		this.maxPage = (int)Math.ceil(tag.getInteger("p") / 2D) - 1;
	}
	
	@Override
	public void initGui() {
		if(key == null || key.isEmpty()) this.mc.thePlayer.closeScreen();
		this.guiLeft = (this.width - this.sizeX) / 2;
		this.guiTop = (this.height - this.sizeY) / 2;
	}
	
	@Override
	public void drawScreen(int i, int j, float f) {
		this.drawDefaultBackground();
		this.drawGuiContainerBackgroundLayer(f, i, j);
		GL11.glDisable(GL11.GL_LIGHTING);
		this.drawGuiContainerForegroundLayer(i, j);
		GL11.glEnable(GL11.GL_LIGHTING);
	}
	
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		
		float r = (float)(color >> 16 & 255) / 255F;
		float g = (float)(color >> 8 & 255) / 255F;
		float b = (float)(color & 255) / 255F;
		GL11.glColor4f(r, g, b, 1.0F);
		func_146110_a(guiLeft, guiTop, 0, 0, sizeX, sizeY, 512, 512);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		func_146110_a(guiLeft + 7, guiTop + 7, 0, 182, 258, 165, 512, 512);
		
		final boolean overY = j >= guiTop + 155 && j < guiTop + 165;
		if(page > 0) {
			if(overY && i >= guiLeft + 24 && i <= guiLeft + 42)
				func_146110_a(guiLeft + 24, guiTop + 155, 295, 13, 18, 10, 512, 512);
			else
				func_146110_a(guiLeft + 24, guiTop + 155, 272, 13, 18, 10, 512, 512);
		}
		
		if(page < maxPage) {
			if(overY && i >= guiLeft + 230 && i <= guiLeft + 248)
				func_146110_a(guiLeft + 230, guiTop + 155, 295, 0, 18, 10, 512, 512);
			else
				func_146110_a(guiLeft + 230, guiTop + 155, 272, 0, 18, 10, 512, 512);
		}
	}
	
	protected void drawGuiContainerForegroundLayer(int x, int y) {
		String k = "book_lore." + key + ".page.";
		
		for(int i = 0; i < 2; i++) {
			int defacto = this.page * 2 + i;
			
			if(defacto < tag.getInteger("p")) {
				String text;
				NBTTagCompound argTag = tag.getCompoundTag("p" + defacto);
				
				if(argTag.hasNoTags())
					text = I18nUtil.resolveKey(k + defacto);
				else {
					List<String> args = new ArrayList();
					int index = 1;
					String arg = argTag.getString("a1");
					
					while(!arg.isEmpty()) {
						args.add(arg);
						index++;
						arg = argTag.getString("a" + index);
					}
					
					text = I18nUtil.resolveKey(k + defacto, args.toArray());
				}
				
				float scale = 1;
				int width = 100;
				int widthScaled = (int) (width * scale);
				
				List<String> lines = new ArrayList();
				String[] words = text.split(" ");
				
				lines.add(words[0]);
				int indent = this.fontRendererObj.getStringWidth(words[0]);
				
				for(int w = 1; w < words.length; w++) {
					if(words[w].equals("$")) {
						if(w + 1 < words.length && !words[w + 1].equals("$")) {
							lines.add(words[++w]);
							indent = this.fontRendererObj.getStringWidth(words[w]);
						} else
							lines.add("");
						
						continue;
					}
					
					indent += this.fontRendererObj.getStringWidth(" " + words[w]);
					
					if(indent <= widthScaled) {
						String last = lines.get(lines.size() - 1);
						lines.set(lines.size() - 1, last += (" " + words[w]));
					} else {
						lines.add(words[w]);
						indent = this.fontRendererObj.getStringWidth(words[w]);
					}
				}
				
				GL11.glPushMatrix();
				GL11.glScalef(1F/scale, 1F/scale, 1F);
				
				for(int l = 0; l < lines.size(); l++) {
					this.fontRendererObj.drawString(lines.get(l),
							(int)((guiLeft + 20 + i * 130) * scale),
							(int)((guiTop + 20) * scale + (9 * l)),
							0x0F0F0F);
				}
				
				GL11.glPopMatrix();
			}
		}
		
	}
	
	@Override
	protected void mouseClicked(int i, int j, int k) {
		if(j < guiTop + 155 || j >= guiTop + 165) return;
		
		if(page > 0 && i >= guiLeft + 24 && i <= guiLeft + 42) {
			page--;
			mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
		}
		
		if(page < maxPage && i >= guiLeft + 230 && i <= guiLeft + 248) {
			page++;
			mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
		}
	}
	
	@Override
	protected void keyTyped(char c, int key) {
		if(key == 1 || key == this.mc.gameSettings.keyBindInventory.getKeyCode()) {
			this.mc.thePlayer.closeScreen();
		}
	}
}