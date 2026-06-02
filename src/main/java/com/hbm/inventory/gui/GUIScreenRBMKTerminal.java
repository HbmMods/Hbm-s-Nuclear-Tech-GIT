package com.hbm.inventory.gui;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.toserver.NBTControlPacket;
import com.hbm.tileentity.machine.rbmk.TileEntityRBMKTerminal;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.nbt.NBTTagCompound;

public class GUIScreenRBMKTerminal extends GuiScreen {
	
	protected GuiTextField line;
	protected TileEntityRBMKTerminal terminal;
	
	public GUIScreenRBMKTerminal(TileEntityRBMKTerminal terminal) {
		this.terminal = terminal;
	}

	@Override
	public void initGui() {
		super.initGui();

		Keyboard.enableRepeatEvents(true);
		
		line = new GuiTextField(this.fontRendererObj, 0, 0, 0, 0);
		line.setMaxStringLength(50);
		
		line.setFocused(true);
	}

	public void drawScreen(int x, int y, float f) {
		
		if(terminal.isInvalid()) { // for if the terminal blows up
			this.mc.displayGuiScreen((GuiScreen) null);
			this.mc.setIngameFocus();
			return;
		}
		
		GL11.glScaled(0.5, 0.5, 1);
		this.fontRendererObj.drawString("[Esc] - Quit", 2, 2, 0xffffff);
		this.fontRendererObj.drawString("chan <channel> - Set selected channel", 2, 12, 0xffffff);
		this.fontRendererObj.drawString("send <cmd> - Send single signal over selected channel", 2, 22, 0xffffff);
		this.fontRendererObj.drawString("start <cmd> - Continuously send signal over selected channel", 2, 32, 0xffffff);
		this.fontRendererObj.drawString("stop - Stop continuous sending", 2, 42, 0xffffff);
		this.fontRendererObj.drawString("clear - Delete command history", 2, 52, 0xffffff);
	}
	
	protected void keyTyped(char c, int b) {
		if(b == 1) {
			this.mc.displayGuiScreen((GuiScreen) null);
			this.mc.setIngameFocus();
			return;
		}
		
		if(b == Keyboard.KEY_RETURN) {
			NBTTagCompound data = new NBTTagCompound();
			data.setString("cmd", this.line.getText());
			PacketDispatcher.wrapper.sendToServer(new NBTControlPacket(data, terminal.xCoord, terminal.yCoord, terminal.zCoord));
			this.line.setText("");
			return;
		}
		
		if(b == 199) return;
		if(b == 203) return;
		if(b == 205) return;
		if(b == 207) return;
		
		this.line.setCursorPositionEnd();
		if(this.line.textboxKeyTyped(c, b)) return;
	}
	
	public static String getWorkingLine() {
		
		if(Minecraft.getMinecraft().currentScreen instanceof GUIScreenRBMKTerminal) {
			GUIScreenRBMKTerminal gui = (GUIScreenRBMKTerminal) Minecraft.getMinecraft().currentScreen;
			return gui.line.getText();
		}
		
		return "";
	}

	@Override public void onGuiClosed() { Keyboard.enableRepeatEvents(false); }
	@Override public boolean doesGuiPauseGame() { return false; }
}
