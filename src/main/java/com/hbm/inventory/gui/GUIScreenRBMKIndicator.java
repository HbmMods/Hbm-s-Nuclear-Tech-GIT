package com.hbm.inventory.gui;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import com.hbm.lib.RefStrings;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.toserver.NBTControlPacket;
import com.hbm.tileentity.machine.rbmk.TileEntityRBMKIndicator;
import com.hbm.util.i18n.I18nUtil;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

public class GUIScreenRBMKIndicator extends GuiScreen {

	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/machine/gui_rbmk_indicator.png");
	public TileEntityRBMKIndicator indicator;
	protected int xSize = 256;
	protected int ySize = 258; // idk if this is a good idea
	protected int guiLeft;
	protected int guiTop;

	protected GuiTextField[] color = new GuiTextField[6];
	protected GuiTextField[] label = new GuiTextField[6];
	protected GuiTextField[] rtty = new GuiTextField[6];
	protected GuiTextField[] min = new GuiTextField[6];
	protected GuiTextField[] max = new GuiTextField[6];
	protected boolean[] active = new boolean[6];
	protected boolean[] polling = new boolean[6];
	
	public GUIScreenRBMKIndicator(TileEntityRBMKIndicator indicator) {
		this.indicator = indicator;
	}

	@Override
	public void initGui() {
		super.initGui();
		this.guiLeft = (this.width - this.xSize) / 2;
		this.guiTop = (this.height - this.ySize) / 2;

		Keyboard.enableRepeatEvents(true);
		
		int oX = 4;
		int oY = 4;
		
		for(int i = 0; i < 6; i++) {
			String col = Integer.toHexString(indicator.indicators[i].color);
			while(col.length() < 6) col = "0" + col;
			color[i] = new GuiTextField(this.fontRendererObj, guiLeft + 27 + oX, guiTop + 37 + oY + i * 36, 72 - oX * 2, 14);
			GUIScreenRBMKKeyPad.setupTextFieldStandard(color[i], 6, col);
			label[i] = new GuiTextField(this.fontRendererObj, guiLeft + 175 + oX, guiTop + 37 + oY + i * 36, 72 - oX * 2, 14);
			GUIScreenRBMKKeyPad.setupTextFieldStandard(label[i], 15, indicator.indicators[i].label);
			rtty[i] = new GuiTextField(this.fontRendererObj, guiLeft + 27 + oX, guiTop + 55 + oY + i * 36, 72 - oX * 2, 14);
			GUIScreenRBMKKeyPad.setupTextFieldStandard(rtty[i], 10, indicator.indicators[i].rtty);
			min[i] = new GuiTextField(this.fontRendererObj, guiLeft + 121 + oX, guiTop + 55 + oY + i * 36, 52 - oX * 2, 14);
			GUIScreenRBMKKeyPad.setupTextFieldStandard(min[i], 32, indicator.indicators[i].min + "");
			max[i] = new GuiTextField(this.fontRendererObj, guiLeft + 195 + oX, guiTop + 55 + oY + i * 36, 52 - oX * 2, 14);
			GUIScreenRBMKKeyPad.setupTextFieldStandard(max[i], 32, indicator.indicators[i].max + "");

			active[i] = indicator.indicators[i].active;
			polling[i] = indicator.indicators[i].polling;
		}
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		this.drawDefaultBackground();
		this.drawGuiContainerBackgroundLayer(f, mouseX, mouseY);
		GL11.glDisable(GL11.GL_LIGHTING);
		this.drawGuiContainerForegroundLayer(mouseX, mouseY);
		GL11.glEnable(GL11.GL_LIGHTING);
	}

	private void drawGuiContainerForegroundLayer(int x, int y) {
		String name = I18nUtil.resolveKey("container.rbmkIndicator");
		this.fontRendererObj.drawString(name, this.guiLeft + this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, this.guiTop + 6, 4210752);
	}

	private void drawGuiContainerBackgroundLayer(float f, int mouseX, int mouseY) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, 256, 143);
		drawTexturedModalRect(guiLeft, guiTop + 143, 0, 35, 256, 115);
		
		for(int i = 0; i < 6; i++) {
			drawTexturedModalRect(guiLeft + 102, guiTop + 112 + 36 + i * 36, 34 + 8 * i, 150, 8, 8);
			if(this.active[i]) drawTexturedModalRect(guiLeft + 111, guiTop + i * 36 + 36, 18, 150, 16, 16);
			if(this.polling[i]) drawTexturedModalRect(guiLeft + 128, guiTop + i * 36 + 35, 0, 150, 18, 18);
		}
		
		for(int i = 0; i < 6; i++) {
			this.color[i].drawTextBox();
			this.label[i].drawTextBox();
			this.rtty[i].drawTextBox();
			this.min[i].drawTextBox();
			this.max[i].drawTextBox();
		}
	}

	@Override
	protected void mouseClicked(int x, int y, int b) {
		super.mouseClicked(x, y, b);
		
		for(int i = 0; i < 6; i++) {
			if(guiLeft + 111 <= x && guiLeft + 111 + 16 > x && guiTop + i * 36 + 36 < y && guiTop + i * 36 + 36 + 16 >= y) {
				this.active[i] = !this.active[i];
				mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 0.5F + (this.active[i] ? 0.25F : 0F)));
				return;
			}
			
			if(guiLeft + 128 <= x && guiLeft + 128 + 18 > x && guiTop + i * 36 + 35 < y && guiTop + i * 36 + 35 + 18 >= y) {
				this.polling[i] = !this.polling[i];
				mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 0.5F + (this.polling[i] ? 0.25F : 0F)));
				return;
			}
		}
		
		if(guiLeft + 209 <= x && guiLeft + 209 + 18 > x && guiTop + 8 < y && guiTop + 8 + 18 >= y) {
			mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
			NBTTagCompound data = new NBTTagCompound();
			byte active = 0;
			byte polling = 0;
			for(int i = 0; i < 6; i++) {
				if(this.active[i]) active |= 1 << i;
				if(this.polling[i]) polling |= 1 << i;
			}
			data.setByte("active", active);
			data.setByte("polling", polling);
			
			for(int i = 0; i < 6; i++) {
				try { data.setInteger("color" + i, Integer.parseInt(this.color[i].getText(), 16)); } catch(Exception ex) { }
				data.setString("label" + i, this.label[i].getText());
				data.setString("rtty" + i, this.rtty[i].getText());
				try { data.setInteger("min" + i, Integer.parseInt(this.min[i].getText())); } catch(Exception ex) { }
				try { data.setInteger("max" + i, Integer.parseInt(this.max[i].getText())); } catch(Exception ex) { }
			}
			PacketDispatcher.wrapper.sendToServer(new NBTControlPacket(data, indicator.xCoord, indicator.yCoord, indicator.zCoord));
			return;
		}
		
		for(int i = 0; i < 6; i++) {
			this.color[i].mouseClicked(x, y, b);
			this.label[i].mouseClicked(x, y, b);
			this.rtty[i].mouseClicked(x, y, b);
			this.min[i].mouseClicked(x, y, b);
			this.max[i].mouseClicked(x, y, b);
		}
	}

	@Override
	protected void keyTyped(char c, int b) {
		
		for(int i = 0; i < 6; i++) {
			if(this.color[i].textboxKeyTyped(c, b)) return;
			if(this.label[i].textboxKeyTyped(c, b)) return;
			if(this.rtty[i].textboxKeyTyped(c, b)) return;
			if(this.min[i].textboxKeyTyped(c, b)) return;
			if(this.max[i].textboxKeyTyped(c, b)) return;
		}
		
		if(b == 1 || b == this.mc.gameSettings.keyBindInventory.getKeyCode()) {
			this.mc.thePlayer.closeScreen();
			this.mc.setIngameFocus();
		}
	}

	@Override public void onGuiClosed() { Keyboard.enableRepeatEvents(false); }
	@Override public boolean doesGuiPauseGame() { return false; }
}
