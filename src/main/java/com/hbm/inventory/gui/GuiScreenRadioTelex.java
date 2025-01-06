package com.hbm.inventory.gui;

import java.util.Arrays;
import java.util.Random;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import com.hbm.lib.RefStrings;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.toserver.NBTControlPacket;
import com.hbm.tileentity.network.TileEntityRadioTelex;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatAllowedCharacters;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

public class GuiScreenRadioTelex extends GuiScreen {
	
	protected static final ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/machine/gui_telex.png");
	protected TileEntityRadioTelex telex;
	protected int xSize = 256;
	protected int ySize = 244;
	protected int guiLeft;
	protected int guiTop;
	protected GuiTextField txFrequency;
	protected GuiTextField rxFrequency;
	protected boolean textFocus = false;
	
	protected String[] txBuffer;
	protected int cursorPos = 0;
	
	public GuiScreenRadioTelex(TileEntityRadioTelex tile) {
		this.telex = tile;
		this.txBuffer = new String[tile.txBuffer.length];
		
		for(int i = 0; i < txBuffer.length; i++) {
			this.txBuffer[i] = tile.txBuffer[i];
		}
		
		for(int i = 4; i > 0; i--) {
			if(!txBuffer[i].isEmpty()) {
				cursorPos = i;
				break;
			}
		}
	}

	@Override
	public void initGui() {
		super.initGui();
		this.guiLeft = (this.width - this.xSize) / 2;
		this.guiTop = (this.height - this.ySize) / 2;

		Keyboard.enableRepeatEvents(true);

		this.txFrequency = new GuiTextField(this.fontRendererObj, guiLeft + 29, guiTop + 110, 90, 14);
		this.txFrequency.setTextColor(0x00ff00);
		this.txFrequency.setDisabledTextColour(0x00ff00);
		this.txFrequency.setEnableBackgroundDrawing(false);
		this.txFrequency.setMaxStringLength(10);
		this.txFrequency.setText(telex.txChannel == null ? "" : telex.txChannel);

		this.rxFrequency = new GuiTextField(this.fontRendererObj, guiLeft + 29, guiTop + 224, 90, 14);
		this.rxFrequency.setTextColor(0x00ff00);
		this.rxFrequency.setDisabledTextColour(0x00ff00);
		this.rxFrequency.setEnableBackgroundDrawing(false);
		this.rxFrequency.setMaxStringLength(10);
		this.rxFrequency.setText(telex.rxChannel == null ? "" : telex.rxChannel);
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

		if(checkClick(x, y, 7, 85, 18, 18)) this.func_146283_a(Arrays.asList(new String[] {EnumChatFormatting.GOLD + "BELL", "Plays a bell when this character is received"}), x, y);
		if(checkClick(x, y, 27, 85, 18, 18)) this.func_146283_a(Arrays.asList(new String[] {EnumChatFormatting.GOLD + "PRINT", "Forces recipient to print message after transmission ends"}), x, y);
		if(checkClick(x, y, 47, 85, 18, 18)) this.func_146283_a(Arrays.asList(new String[] {EnumChatFormatting.GOLD + "CLEAR SCREEN", "Wipes message buffer when this character is received"}), x, y);
		if(checkClick(x, y, 67, 85, 18, 18)) this.func_146283_a(Arrays.asList(new String[] {EnumChatFormatting.GOLD + "FORMAT", "Inserts format character for message formatting"}), x, y);
		if(checkClick(x, y, 87, 85, 18, 18)) this.func_146283_a(Arrays.asList(new String[] {EnumChatFormatting.GOLD + "PAUSE", "Pauses message transmission for one second"}), x, y);

		if(checkClick(x, y, 127, 105, 18, 18)) this.func_146283_a(Arrays.asList(new String[] {EnumChatFormatting.GREEN + "SAVE ID"}), x, y);
		if(checkClick(x, y, 147, 105, 18, 18)) this.func_146283_a(Arrays.asList(new String[] {EnumChatFormatting.YELLOW + "SEND MESSAGE"}), x, y);
		if(checkClick(x, y, 167, 105, 18, 18)) this.func_146283_a(Arrays.asList(new String[] {EnumChatFormatting.RED + "DELETE MESSAGE BUFFER"}), x, y);
		
		if(checkClick(x, y, 127, 219, 18, 18)) this.func_146283_a(Arrays.asList(new String[] {EnumChatFormatting.GREEN + "SAVE ID"}), x, y);
		if(checkClick(x, y, 147, 219, 18, 18)) this.func_146283_a(Arrays.asList(new String[] {EnumChatFormatting.AQUA + "PRINT MESSAGE"}), x, y);
		if(checkClick(x, y, 167, 219, 18, 18)) this.func_146283_a(Arrays.asList(new String[] {EnumChatFormatting.RED + "CLEAR SCREEN"}), x, y);
	}

	private void drawGuiContainerBackgroundLayer(float f, int mouseX, int mouseY) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		
		this.txFrequency.drawTextBox();
		this.rxFrequency.drawTextBox();
		
		for(int line = 0; line < 5; line++) {
			String text = txBuffer[line];
			int y = 11 + 14 * line;
			
			String format = EnumChatFormatting.RESET + "";
			
			for(int index = 0; index < text.length(); index++) {
				int x = 11 + 7 * index;
				char c = text.charAt(index);
				x += (7 - this.fontRendererObj.getCharWidth(c)) / 2;
				if(c == '§' && text.length() > index + 1) {
					format = "\u00a7" + text.charAt(index + 1);
					x -= 3;
				}
				String glyph = format + c;
				if(c == '\u0007') glyph = EnumChatFormatting.RED + "B";
				if(c == '\u000c') glyph = EnumChatFormatting.RED + "P";
				if(c == '\u007f') glyph = EnumChatFormatting.RED + "<";
				if(c == '\u0016') glyph = EnumChatFormatting.RED + "W";
				this.fontRendererObj.drawString(glyph, guiLeft + x, guiTop + y, 0x00ff00);
			}

			if(System.currentTimeMillis() % 1000 < 500 && this.textFocus) {
				int x = Math.max(11 + 7 * (text.length() - 1) + 7, 11);
				if(this.cursorPos == line) {
					this.fontRendererObj.drawString("|", guiLeft + x, guiTop + y, 0x00ff00);
				}
			}
		}
		
		for(int line = 0; line < 5; line++) {
			String text = telex.rxBuffer[line];
			int y = 145 + 14 * line;
			
			String format = EnumChatFormatting.RESET + "";
			
			int x = 11;
			
			for(int index = 0; index < text.length(); index++) {
				
				char c = text.charAt(index);
				x += (7 - this.fontRendererObj.getCharWidth(c)) / 2;
				if(c == '§' && text.length() > index + 1) {
					format = "\u00a7" + text.charAt(index + 1);
					c = ' ';
				} else if(c == '§') {
					c = ' ';
				} else if(index > 0 && text.charAt(index - 1) == '§') {
					c = ' ';
					x -= 14;
				}
				String glyph = format + c;
				this.fontRendererObj.drawString(glyph, guiLeft + x, guiTop + y, 0x00ff00);
				x += 7;
			}
		}

		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glLineWidth(3F);
		Random rand = new Random(telex.sendingChar);
		Tessellator tess = Tessellator.instance;
		tess.startDrawing(GL11.GL_LINES);
		tess.setColorOpaque_I(0x00ff00);
		double offset = 0;
		for(int i = 0; i < 48; i++) {
			tess.addVertex(guiLeft + 199 + i, guiTop + 93.5 + offset, this.zLevel + 10);
			if(telex.sendingChar != ' ' && i > 4 && i < 43) offset = rand.nextGaussian() * 7; else offset = 0;
			offset = MathHelper.clamp_double(offset, -7D, 7D);
			tess.addVertex(guiLeft + 199 + i + 1, guiTop + 93.5 + offset, this.zLevel + 10);
		}
		tess.draw();
		GL11.glEnable(GL11.GL_TEXTURE_2D);
	}

	@Override
	protected void mouseClicked(int x, int y, int i) {
		super.mouseClicked(x, y, i);

		this.txFrequency.mouseClicked(x, y, i);
		this.rxFrequency.mouseClicked(x, y, i);
		
		if(guiLeft + 7 <= x && guiLeft + 7 + 242 > x && guiTop + 7 < y && guiTop + 7 + 74 >= y) {
			this.textFocus = true;
		} else {
			this.textFocus = false;
		}
		
		char character = '\0';
		String cmd = null;
		
		/* special characters */
		// BEL
		if(checkClick(x, y, 7, 85, 18, 18)) character = '\u0007'; // bell
		// PRT
		if(checkClick(x, y, 27, 85, 18, 18)) character = '\u000c'; // form feed
		// CLS
		if(checkClick(x, y, 47, 85, 18, 18)) character = '\u007f'; // delete
		// FMT
		if(checkClick(x, y, 67, 85, 18, 18)) character = '§'; // minecraft formatting character
		// PSE
		if(checkClick(x, y, 87, 85, 18, 18)) character = '\u0016'; // synchronous idle
		
		// SVE
		if(checkClick(x, y, 127, 105, 18, 18) || checkClick(x, y, 127, 219, 18, 18)) cmd = "sve"; // save channel
		// SND
		if(checkClick(x, y, 147, 105, 18, 18)) cmd = "snd"; // send message in TX buffer
		// DEL
		if(checkClick(x, y, 167, 105, 18, 18)) { // delete message in TX buffer
			cmd = "rxdel";
			for(int j = 0; j < 5; j++) this.txBuffer[j] = "";
			NBTTagCompound data = new NBTTagCompound();
			for(int j = 0; j < 5; j++) data.setString("tx" + j, this.txBuffer[j]);
			PacketDispatcher.wrapper.sendToServer(new NBTControlPacket(data, telex.xCoord, telex.yCoord, telex.zCoord));
		}
		// PRT
		if(checkClick(x, y, 147, 219, 18, 18)) cmd = "rxprt"; // print message in RX buffer
		// CLS
		if(checkClick(x, y, 167, 219, 18, 18)) cmd = "rxcls"; // delete message in RX buffer
		
		if(cmd != null) {
			mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
			NBTTagCompound data = new NBTTagCompound();
			data.setString("cmd", cmd);
			
			if("snd".equals(cmd)) {
				for(int j = 0; j < 5; j++) data.setString("tx" + j, this.txBuffer[j]);
			}
			
			if("sve".equals(cmd)) {
				data.setString("txChan", this.txFrequency.getText());
				data.setString("rxChan", this.rxFrequency.getText());
			}
			
			PacketDispatcher.wrapper.sendToServer(new NBTControlPacket(data, telex.xCoord, telex.yCoord, telex.zCoord));
		}
		
		if(character != '\0') {
			mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
			setTextFocus();
			submitChar(character);
		}
	}
	
	protected boolean checkClick(int x, int y, int left, int top, int sizeX, int sizeY) {
		return guiLeft + left <= x && guiLeft + left + sizeX > x && guiTop + top < y && guiTop + top + sizeY >= y;
	}
	
	protected void setTextFocus() {
		this.textFocus = true;
		this.txFrequency.setFocused(false);
		this.rxFrequency.setFocused(false);
	}

	@Override
	protected void keyTyped(char c, int i) {

		if(this.txFrequency.textboxKeyTyped(c, i)) return;
		if(this.rxFrequency.textboxKeyTyped(c, i)) return;
		
		if(this.textFocus) {
			
			if(i == 1) {
				this.textFocus = false;
				return;
			}

			if(i == Keyboard.KEY_UP) this.cursorPos--;
			if(i == Keyboard.KEY_DOWN) this.cursorPos++;
			
			this.cursorPos = MathHelper.clamp_int(cursorPos, 0, 4);
			
			if(ChatAllowedCharacters.isAllowedCharacter(c)) {
				submitChar(c);
				return;
			}
			
			if(i == Keyboard.KEY_BACK && this.txBuffer[cursorPos].length() > 0) {
				this.txBuffer[cursorPos] = this.txBuffer[cursorPos].substring(0, this.txBuffer[cursorPos].length() - 1);
			}
		}
		
		if(i == 1 || i == this.mc.gameSettings.keyBindInventory.getKeyCode()) {
			this.mc.thePlayer.closeScreen();
			this.mc.setIngameFocus();
		}
	}
	
	protected void submitChar(char c) {
		String line = this.txBuffer[cursorPos];
		
		if(line.length() < TileEntityRadioTelex.lineWidth) {
			this.txBuffer[cursorPos] = line + c;
		}
	}

	@Override
	public void onGuiClosed() {
		Keyboard.enableRepeatEvents(false);
		NBTTagCompound data = new NBTTagCompound();
		for(int j = 0; j < 5; j++) data.setString("tx" + j, this.txBuffer[j]);
		PacketDispatcher.wrapper.sendToServer(new NBTControlPacket(data, telex.xCoord, telex.yCoord, telex.zCoord));
	}
	
	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}
}
