package com.hbm.inventory.gui;

import java.util.Arrays;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import com.hbm.lib.RefStrings;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.toserver.NBTControlPacket;
import com.hbm.tileentity.network.TileEntityRadioTorchLogic;
import com.hbm.util.I18nUtil;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

public class GUIScreenRadioTorchLogic extends GuiScreen {
	
	protected static final ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/machine/gui_rtty_logic_receiver.png");
	
	protected TileEntityRadioTorchLogic logic;
	protected GuiTextField frequency;
	protected GuiTextField[] map;
	protected int[] conditions; //so the 'save settings' paradigm applies to the conditions, too
	
	protected static final int xSize = 256;
	protected static final int ySize = 204;
	protected int guiLeft;
	protected int guiTop;
	
	public GUIScreenRadioTorchLogic(TileEntityRadioTorchLogic logic) {
		this.logic = logic;
	}
	
	@Override
	public void initGui() {
		super.initGui();
		this.guiLeft = (this.width - this.xSize) / 2;
		this.guiTop = (this.height - this.ySize) / 2;

		Keyboard.enableRepeatEvents(true);
		
		int oX = 4;
		int oY = 4;
		
		this.frequency = new GuiTextField(this.fontRendererObj, guiLeft + 25 + oX, guiTop + 17 + oY, 90 - oX * 2, 14);
		this.frequency.setTextColor(0x00ff00);
		this.frequency.setDisabledTextColour(0x00ff00);
		this.frequency.setEnableBackgroundDrawing(false);
		this.frequency.setMaxStringLength(10);
		this.frequency.setText(logic.channel == null ? "" : logic.channel);
		
		this.map = new GuiTextField[16];
		this.conditions = new int[16];
		
		for(int i = 0; i < 16; i++) {
			this.map[i] = new GuiTextField(this.fontRendererObj, guiLeft + 7 + (130 * (i / 8)) + oX + 18, guiTop + 53 + (18 * (i % 8)) + oY, 54 - oX * 2, 14);
			this.map[i].setTextColor(0x00ff00);
			this.map[i].setDisabledTextColour(0x00ff00);
			this.map[i].setEnableBackgroundDrawing(false);
			this.map[i].setMaxStringLength(15);
			this.map[i].setText(logic.mapping[i] == null ? "" : logic.mapping[i]);
			
			this.conditions[i] = logic.conditions[i];
		}
	}
	
	@Override
	public void drawScreen(int x, int y, float f) {
		this.drawDefaultBackground();
		this.drawGuiContainerBackgroundLayer(f, x, y);
		GL11.glDisable(GL11.GL_LIGHTING);
		this.drawGuiContainerForegroundLayer(x, y);
		GL11.glEnable(GL11.GL_LIGHTING);
		//easy selection
		if(guiLeft > x && guiLeft + xSize <= x && guiTop > y && guiTop + ySize <= y) return;
		
		if(!Mouse.isButtonDown(0) && !Mouse.isButtonDown(1) && Mouse.next()) {	
			for(int j = 0; j < 16; j++) {
				if(guiLeft + 7 + (130 * (j / 8)) <= x && guiLeft + 7 + 18 + (130 * (j / 8)) > x && guiTop + 53 + (18 * (j % 8)) <= y && guiTop + 53 + 18 + (18 * (j % 8)) > y) {
					int scroll = Mouse.getEventDWheel();
					
					if(scroll > 0) this.conditions[j] = (this.conditions[j] + 1) % 10;
					if(scroll < 0) this.conditions[j] = (this.conditions[j] + 9) % 10;
					return;
				}
			}
		}
	}
	
	private void drawGuiContainerForegroundLayer(int x, int y) {
		String name = I18nUtil.resolveKey("container.rttyLogic");
		this.fontRendererObj.drawString(name, this.guiLeft + this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, this.guiTop + 6, 4210752);
		//TODO add localization for *every* RTTY
		if(guiLeft + 137 <= x && guiLeft + 137 + 18 > x && guiTop + 17 < y && guiTop + 17 + 18 >= y) {
			func_146283_a(Arrays.asList(new String[] { logic.descending ? "Descending Order" : "Ascending Order" }), x, y);
		}
		if(guiLeft + 173 <= x && guiLeft + 173 + 18 > x && guiTop + 17 < y && guiTop + 17 + 18 >= y) {
			func_146283_a(Arrays.asList(new String[] { logic.polling ? "Polling" : "State Change" }), x, y);
		}
		if(guiLeft + 209 <= x && guiLeft + 209 + 18 > x && guiTop + 17 < y && guiTop + 17 + 18 >= y) {
			func_146283_a(Arrays.asList(new String[] { "Save Settings" }), x, y);
		}
		for(int j = 0; j < 16; j++) {
			if(guiLeft + 7 + (130 * (j / 8)) <= x && guiLeft + 7 + 18 + (130 * (j / 8)) > x && guiTop + 53 + (18 * (j % 8)) <= y && guiTop + 53 + 18 + (18 * (j % 8)) > y) {
				func_146283_a(Arrays.asList(new String[] { I18nUtil.resolveKey("desc.gui.rttyLogic.cond" + this.conditions[j]) }), x, y);
				break;
			}
		}
	}

	private void drawGuiContainerBackgroundLayer(float f, int mouseX, int mouseY) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		if(logic.descending) drawTexturedModalRect(guiLeft + 137, guiTop + 17, 0, 204, 18, 18);
		if(logic.polling) drawTexturedModalRect(guiLeft + 173, guiTop + 17, 0, 222, 18, 18);
		
		for(int i = 0; i < 16; i++) {
			if(logic.mapping[i].isEmpty()) {
				if(this.conditions[i] != 0)
					drawTexturedModalRect(guiLeft + 7 + (130 * (i / 8)), guiTop + 53 + (18 * (i % 8)), 18 + this.conditions[i] * 18, 222, 18, 18);
			} else {
				drawTexturedModalRect(guiLeft + 7 + (130 * (i / 8)), guiTop + 53 + (18 * (i % 8)), 18 + this.conditions[i] * 18, 204, 18, 18);
				drawTexturedModalRect(guiLeft + 85 + (130 * (i / 8)), guiTop + 57 + (18 * (i % 8)), 198, 204, 14, 10);
			}
		}
		
		for(int i = 0; i < 16; i++) this.map[i].drawTextBox();
		this.frequency.drawTextBox();
	}

	@Override
	protected void mouseClicked(int x, int y, int i) {
		super.mouseClicked(x, y, i);
		
		this.frequency.mouseClicked(x, y, i);
		for(int j = 0; j < 16; j++) this.map[j].mouseClicked(x, y, i);
		
		if(guiLeft + 137 <= x && guiLeft + 137 + 18 > x && guiTop + 17 < y && guiTop + 17 + 18 >= y) {
			mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
			NBTTagCompound data = new NBTTagCompound();
			data.setBoolean("d", !logic.descending);
			PacketDispatcher.wrapper.sendToServer(new NBTControlPacket(data, logic.xCoord, logic.yCoord, logic.zCoord));
		}
		
		if(guiLeft + 173 <= x && guiLeft + 173 + 18 > x && guiTop + 17 < y && guiTop + 17 + 18 >= y) {
			mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
			NBTTagCompound data = new NBTTagCompound();
			data.setBoolean("p", !logic.polling);
			PacketDispatcher.wrapper.sendToServer(new NBTControlPacket(data, logic.xCoord, logic.yCoord, logic.zCoord));
		}
		
		if(guiLeft + 209 <= x && guiLeft + 209 + 18 > x && guiTop + 17 < y && guiTop + 17 + 18 >= y) {
			mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
			NBTTagCompound data = new NBTTagCompound();
			data.setString("c", this.frequency.getText());
			for(int j = 0; j < 16; j++) data.setString("m" + j, this.map[j].getText().isEmpty() ? "" : this.map[j].getText());
			for(int j = 0; j < 16; j++) data.setInteger("c" + j, this.conditions[j]);
			PacketDispatcher.wrapper.sendToServer(new NBTControlPacket(data, logic.xCoord, logic.yCoord, logic.zCoord));
		}
		
		for(int j = 0; j < 16; j++) {
			if(guiLeft + 7 + (130 * (j / 8)) <= x && guiLeft + 7 + 18 + (130 * (j / 8)) > x && guiTop + 53 + (18 * (j % 8)) <= y && guiTop + 53 + 18 + (18 * (j % 8)) > y) {
				mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
				this.conditions[j] = (this.conditions[j] + 1) % 10;
			}
		}
	}

	@Override
	protected void keyTyped(char c, int i) {
		
		if(this.frequency.textboxKeyTyped(c, i))
			return;

		for(int j = 0; j < 16; j++) if(this.map[j].textboxKeyTyped(c, i)) return;
		
		if(i == 1 || i == this.mc.gameSettings.keyBindInventory.getKeyCode()) {
			this.mc.thePlayer.closeScreen();
			this.mc.setIngameFocus();
		}
	}

	@Override
	public void onGuiClosed() {
		Keyboard.enableRepeatEvents(false);
	}
	
	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}
	
}
