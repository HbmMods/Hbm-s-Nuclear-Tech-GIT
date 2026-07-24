package com.hbm.inventory.gui;

import com.hbm.util.Calculator;
import com.hbm.util.Tuple;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;

public class GUICalculator extends GuiScreen {
	private int xSize = 220;
	private int ySize = 50;
	private int borderWidth = 2;
	private GuiTextField inputField;

	private int selectedHist = -1;
	private static final int maxHistory = 6;
	private static Deque<Tuple.Pair<String, Double>> history = new ArrayDeque<>();
	private String latestResult = "?";

	@Override
	public void initGui() {
		Keyboard.enableRepeatEvents(true);

		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;
		inputField = new GuiTextField(fontRendererObj, x + 5, y + 8, 210, 13);
		inputField.setTextColor(-1);
		inputField.setCanLoseFocus(false);
		inputField.setFocused(true);
		inputField.setMaxStringLength(1000);
	}

	@Override
	public void onGuiClosed() {
		Keyboard.enableRepeatEvents(false);
	}

	@Override
	protected void keyTyped(char p_73869_1_, int p_73869_2_) {
		if (!inputField.textboxKeyTyped(p_73869_1_, p_73869_2_))
			super.keyTyped(p_73869_1_, p_73869_2_);

		String input = inputField.getText().replaceAll("[^\\d+\\-*/%^!.()\\sA-Za-z]+", "");

		if (p_73869_1_ == 13 || p_73869_1_ == 10) { // when pressing enter (CR or LF)
			if (selectedHist != -1) {
				input = new ArrayList<>(history).get(selectedHist).key;
				inputField.setText(input);
				selectedHist = -1;
			} else {
				try {
					double result = Calculator.evaluateExpression(input);
					history.addFirst(new Tuple.Pair<String,Double>(input, result));
					if (history.size() > maxHistory) history.removeLast();
					String plainStringRepresentation = (new BigDecimal(result, MathContext.DECIMAL64)).toPlainString();
					GuiScreen.setClipboardString(plainStringRepresentation);
					inputField.setText(plainStringRepresentation);
					inputField.setCursorPositionEnd();
					inputField.setSelectionPos(0);
				} catch (Exception ignored) {}
				return;
			}
		}

		if (p_73869_2_ == 200) { // up arrow
			selectedHist = Math.max(selectedHist - 1, -1);
		} else if (p_73869_2_ == 208) { // down arrow
			selectedHist = Math.min(selectedHist + 1, history.size() - 1);
		} else {
			selectedHist = -1;
		}

		if (input.isEmpty()) {
			latestResult = "?";
			return;
		}

		try {
			latestResult = Double.toString(Calculator.evaluateExpression(input));
		} catch (Exception e) { latestResult = e.toString(); }
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		super.drawScreen(mouseX, mouseY, partialTicks);

		GL11.glColor4f(1F, 1F, 1F, 1F);
		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;
		int histHeight = (fontRendererObj.FONT_HEIGHT + 2) * maxHistory;
		int histStart = y + 30 + fontRendererObj.FONT_HEIGHT + 8;

		drawRect(x, y, x+xSize, y+ySize+histHeight, 0xFF2d2d2d);
		drawRect(x+borderWidth, y+borderWidth, x+xSize-borderWidth, y+ySize-borderWidth+histHeight, 0xFF3d3d3d);
		drawRect(x, histStart - 5, x+xSize, histStart - 3, 0xFF2d2d2d);

		inputField.drawTextBox();
		fontRendererObj.drawString("=" + latestResult, x + 5, y + 30, -1);

		int i = 0;
		for (Tuple.Pair<String, Double> prevInput : history) {
			int hy = y + 50 + (fontRendererObj.FONT_HEIGHT+1)*i;
			if (i == selectedHist) {
				drawRect(x + 4, hy - 1, x + 4 + xSize - 9, hy + fontRendererObj.FONT_HEIGHT, 0xFF111111);
			}
			fontRendererObj.drawString(prevInput.key + " = " + prevInput.value, x + 5, hy, -1);
			i++;
		}
	}
}
