package com.hbm.inventory.gui;

import com.hbm.qmaw.GuiQMAW;
import com.hbm.qmaw.QMAWLoader;
import com.hbm.qmaw.QuickManualAndWiki;
import com.hbm.util.Calculator;
import com.hbm.util.Tuple;

import com.hbm.util.i18n.I18nUtil;

import cpw.mods.fml.common.FMLCommonHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.RenderHelper;

import net.minecraft.item.ItemStack;

import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.List;

public class GUICalculator extends GuiScreen {
	private static final int xSize = 220;
	private static final int ySize = 50;
	private static final int borderWidth = 2;
	private GuiTextField inputField;

	private int selectedHist = -1;
	private static final int maxHistory = 6;
	private static final List<Tuple.Pair<String, Double>> calcHistory = new ArrayList<>(maxHistory);
	private static final List<String> qmawHistory = new ArrayList<>(maxHistory);
	private final List<String> qmawResults = new ArrayList<>(QMAWLoader.QMAW_KEYS.size());// Update initial capacity???
	// Can in theory add more modes if we make it an enum and use a switch, but I imagine that'd be impractical
	private static boolean qmawMode = false;
	private String latestResult = "?", lastInput = "";

	@Override
	public void initGui() {
		Keyboard.enableRepeatEvents(true);
		
		int x = startX();
		int y = startY();
		inputField = new GuiTextField(fontRendererObj, x + 5, y + 14, 210, 13);
		inputField.setTextColor(-1);
		inputField.setCanLoseFocus(false);
		inputField.setFocused(true);
		inputField.setMaxStringLength(1000);
	}
	
	/// @return The GUI x-coord starting position
	private int startX()
	{
		return (width - xSize) / 2;
	}
	
	/// @return The GUI y-coord starting position
	private int startY()
	{
		return (height - (ySize + (fontRendererObj.FONT_HEIGHT + 4) * maxHistory)) / 2;
	}
	
	@Override
	public void onGuiClosed() {
		Keyboard.enableRepeatEvents(false);
	}
	
	private boolean isInputEmpty()
	{
		return inputField.getText().isEmpty();
	}
		
	@Override
	protected void keyTyped(char typedChar, int keyCode) {
		if (!inputField.textboxKeyTyped(typedChar, keyCode))
			super.keyTyped(typedChar, keyCode);

		if (typedChar == 9)// Tab
			qmawMode = !qmawMode;
		
		if (qmawMode)
			trySearchForQMAW(typedChar, keyCode);
		else 
			handleCalculator(typedChar, keyCode);
	}
	
	private void handleCalculator(char typedChar, int keyCode)
	{
		String input = inputField.getText().replaceAll("[^\\d+\\-*/%^!.()\\sA-Za-z]+", "");

		selectedHistSanityCheck();
		
		if (typedChar == 13 || typedChar == 10) { // when pressing enter (CR or LF)
			if (selectedHist != -1) {
				input = calcHistory.get(selectedHist).key;
				inputField.setText(input);
				selectedHist = -1;
			} else {
				try {
					double result = Calculator.evaluateExpression(input);
					calcHistory.add(0, new Tuple.Pair<>(input, result));
					if (calcHistory.size() > maxHistory) calcHistory.remove(calcHistory.size() - 1);
					String plainStringRepresentation = (new BigDecimal(result, MathContext.DECIMAL64)).toPlainString();
					GuiScreen.setClipboardString(plainStringRepresentation);
					inputField.setText(plainStringRepresentation);
					inputField.setCursorPositionEnd();
					inputField.setSelectionPos(0);
				} catch (Exception ignored) {}
				return;
			}
		}
		
		if (typedChar == 127 && input.isEmpty())// Delete key
			deleteCalcHistoryEntry();

		// up arrow, wraps
		if (keyCode == 200) selectedHist = (selectedHist < 0) ? (Math.min(calcHistory.size(), maxHistory) - 1) : Math.max(selectedHist - 1, -1);
		// down arrow, wraps
		else if (keyCode == 208) selectedHist = (selectedHist >= (calcHistory.size() - 1)) ? -1 : Math.min(selectedHist + 1, calcHistory.size() - 1);
		else selectedHist = -1;

		if (input.isEmpty()) {
			latestResult = "?";
			return;
		}

		try {
			latestResult = Double.toString(Calculator.evaluateExpression(input));
		} catch (Exception e) { latestResult = e.toString(); }
	}
	
	private void deleteCalcHistoryEntry()
	{
		selectedHistSanityCheck();
		if (selectedHist >= 0)
			calcHistory.remove(selectedHist);
	}

	private static void sortQMAWResults(String search, List<String> results)
	{
		// Prioritize entries that start with the query
		results.sort((s1, s2) ->
			{
				final boolean starts1 = s1.toLowerCase().startsWith(search), starts2 = s2.toLowerCase().startsWith(search);

				return starts1 && !starts2 ? -1 :
				       !starts1 && starts2 ? 1 : s1.compareTo(s2);
			});
	}
	
	// Split it out to keep things contained
	protected void trySearchForQMAW(char typedChar, int keyCode)
	{
		final boolean history = isInputEmpty();
		final String query = inputField.getText().toLowerCase();
		// Reset with updated search parameters if new string to save on some compute
		if (!inputField.getText().equalsIgnoreCase(lastInput))
		{
			qmawResults.clear();
			lastInput = inputField.getText();
			
			for (String entry : QMAWLoader.QMAW_KEYS)
				if (entry.toLowerCase().contains(query) && !qmawResults.contains(entry))// Make sure we don't have duplicate entries
					qmawResults.add(entry);
		}

		sortQMAWResults(query, qmawResults);

		if (typedChar == 13 || typedChar == 10) // Enter key
			executeQMAW(history);
		
		if (typedChar == 127 && history)// Delete key
			deleteQMAWHistoryEntry();
				
		// up arrow, wraps to the end for efficiency
		if (keyCode == 200) selectedHist = (selectedHist < 0) ? ((history ? qmawHistory.size() : Math.min(qmawResults.size(), maxHistory)) - 1) : Math.max(selectedHist - 1, -1);
		// down arrow, also wraps
		else if (keyCode == 208) selectedHist = (selectedHist >= ((history ? qmawHistory.size() : qmawResults.size()) - 1)) ? -1 : ((selectedHist >= (maxHistory - 1)) ? -1 : (selectedHist + 1));
		else selectedHist = -1;
	}
	
	private void executeQMAW(boolean historyMode)
	{
		selectedHistSanityCheck();
		if (selectedHist >= 0)
		{
			final String key = new ArrayList<>(historyMode ? qmawHistory : qmawResults).get(selectedHist);
			qmawHistory.remove(key);// Remove duplicate history
			qmawHistory.add(0, key);
			if (qmawHistory.size() > maxHistory) qmawHistory.remove(qmawHistory.size() - 1);
			inputField.setText(key);
			selectedHist = -1;

			final QuickManualAndWiki qmaw = QMAWLoader.qmaw.get(key);
			if (qmaw != null)
			{
				Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
				final GuiQMAW screen = new GuiQMAW(qmaw);
				FMLCommonHandler.instance().showGuiScreen(screen);
			}
		}
	}

	private void deleteQMAWHistoryEntry()
	{
		selectedHistSanityCheck();
		if (selectedHist >= 0)
			qmawHistory.remove(selectedHist);
	}

	private static final int BORDER_COLOR = 0xFF2d2d2d, FILL_COLOR = 0xFF3d3d3d, SELECT_COLOR = 0xFF111111;
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		super.drawScreen(mouseX, mouseY, partialTicks);

		GL11.glColor4f(1F, 1F, 1F, 1F);
		int x = startX();
		int y = startY();
		int histHeight = (fontRendererObj.FONT_HEIGHT + 4) * maxHistory;
		int histStart = y + 30 + fontRendererObj.FONT_HEIGHT + 8;

		drawRect(x, y, x + xSize, y + ySize + histHeight, BORDER_COLOR);
		drawRect(x + borderWidth, y + borderWidth, x + xSize - borderWidth, y + ySize - borderWidth + histHeight, FILL_COLOR);
		if (!qmawMode)
			drawRect(x, histStart - 5, x + xSize, histStart - 3, BORDER_COLOR);

		inputField.drawTextBox();

		final String headerText = qmawMode ? I18nUtil.resolveKey("gui.calc.qmawResult") : I18nUtil.resolveKey("gui.calc.calculatorResult");
		
		fontRendererObj.drawString(headerText, x + 4, y + 4, -1);

		handleScrolling();
				
		if (qmawMode)
			drawQMAW(mouseX, mouseY, x, y);
		else
			drawCalc(mouseX, mouseY, x, y);

	}
	
	private void handleScrolling()
	{
		if (!Mouse.isButtonDown(MOUSE_LEFT) && !Mouse.isButtonDown(MOUSE_MIDDLE) && Mouse.next())
		{
			final int scroll = Mouse.getEventDWheel();
//			System.out.printf("Scrolling = %d\n", scroll);
			// Scrolling up behaves the same as up arrow
			if (scroll > 0)
			{
				if (qmawMode)
					selectedHist = (selectedHist < 0) ? ((isInputEmpty() ? qmawHistory.size() : Math.min(qmawResults.size(), maxHistory)) - 1) : Math.max(selectedHist - 1, -1);
				else
					selectedHist = (selectedHist < 0) ? (Math.min(calcHistory.size(), maxHistory) - 1) : Math.max(selectedHist - 1, -1);
			} else if (scroll < 0) // And vice versa
			{
				if (qmawMode)
					selectedHist = (selectedHist >= ((isInputEmpty() ? qmawHistory.size() : qmawResults.size()) - 1)) ? -1 : ((selectedHist >= (maxHistory - 1)) ? -1 : (selectedHist + 1));
				else
					selectedHist = (selectedHist >= (calcHistory.size() - 1)) ? -1 : Math.min(selectedHist + 1, calcHistory.size() - 1);
			}
		}
	}

	private void drawCalc(int mouseX, int mouseY, int x, int y)
	{
		fontRendererObj.drawString("=" + latestResult, x + 5, y + 32, -1);

		int i = 0;
		for (Tuple.Pair<String, Double> prevInput : calcHistory) {
			int hy = y + 50 + (fontRendererObj.FONT_HEIGHT+1)*i;
			if (i == selectedHist) {
				drawRect(x + 4, hy - 1, x + 4 + xSize - 9, hy + fontRendererObj.FONT_HEIGHT, SELECT_COLOR);
			}
			fontRendererObj.drawString(prevInput.key + " = " + prevInput.value, x + 5, hy, -1);
			i++;
		}
	}
	
	private static final int QMAW_ENTRY_Y_SPACING = 7;
	
	private void drawQMAW(int mouseX, int mouseY, int x, int y)
	{
		int i = 0;
		for (String r : isInputEmpty() ? qmawHistory : qmawResults)
		{			
			if (i > maxHistory - 1)
				break;
			final int hy = y + 35 + (fontRendererObj.FONT_HEIGHT + QMAW_ENTRY_Y_SPACING) * i;
			if (i == selectedHist) drawRect(x + 4, hy - QMAW_ENTRY_Y_SPACING / 2, x + 4 + xSize - 9, hy + fontRendererObj.FONT_HEIGHT + QMAW_ENTRY_Y_SPACING / 2, SELECT_COLOR);
			fontRendererObj.drawString(r, x + 25, hy, -1);
			
			// Copy-pasted from GuiQMAW
			final ItemStack qmawIcon = QMAWLoader.qmaw.get(r).icon;
			if (qmawIcon!= null)
			{
				GL11.glPushMatrix();
				GL11.glEnable(GL11.GL_DEPTH_TEST);
				GL11.glEnable(GL12.GL_RESCALE_NORMAL);
				final Minecraft mc = Minecraft.getMinecraft();
				GL11.glRotated(180, 1, 0, 0);
				RenderHelper.enableStandardItemLighting();
				GL11.glRotated(-180, 1, 0, 0);
				itemRender.renderItemAndEffectIntoGUI(this.fontRendererObj, mc.renderEngine, qmawIcon, x + 5, hy - QMAW_ENTRY_Y_SPACING + 2);
				itemRender.renderItemOverlayIntoGUI(this.fontRendererObj, mc.renderEngine, qmawIcon, x + 5, hy - QMAW_ENTRY_Y_SPACING + 2, null);
				RenderHelper.disableStandardItemLighting();
				GL11.glDisable(GL12.GL_RESCALE_NORMAL);
				GL11.glDisable(GL11.GL_DEPTH_TEST);
				GL11.glPopMatrix();
			}
			
			i++;
		}
	}

	private static final byte MOUSE_LEFT = 0, MOUSE_MIDDLE = 2;
	
	/// Is the mouse in a relevant spot to count its position and clicks? Ignore events outside of this range
	private boolean isMouseInSegment(int mouseX, int mouseY)
	{
		final int x = startX(), y = startY();
		return (mouseX > x && mouseX < (x + xSize)) && (mouseY > y && mouseY < (y + ySize + (fontRendererObj.FONT_HEIGHT + 4) * maxHistory));
	}
	
	/// Returns what entry the mouse probably clicked on
	/// @return 0 - 5 if such entries do exist, -1 if the click was out of bounds or otherwise invalid
	private int mouseEntrySelected(int mouseX, int mouseY)
	{
		int entry = -1;
		if (isMouseInSegment(mouseX, mouseY))
		{
			final int x = startX(), y = startY();
			for (int i = 0; i < maxHistory; i++)
			{
				final int hy = qmawMode ? y + 35 + (fontRendererObj.FONT_HEIGHT + QMAW_ENTRY_Y_SPACING) * i : y + 50 + (fontRendererObj.FONT_HEIGHT + 1) * i;
				if (qmawMode)
				{
					if ((mouseX > (x + 4)) && (mouseY > (hy - (QMAW_ENTRY_Y_SPACING / 2))) && (mouseX < ((x + 4 + xSize) - 9)) && (mouseY < (hy + fontRendererObj.FONT_HEIGHT + (QMAW_ENTRY_Y_SPACING / 2))))
					{
						entry = i;
						break;
					}
				} else
				{
					if ((mouseX > (x + 4)) && (mouseY > (hy - 1)) && (mouseX < ((x + 4 + xSize) - 9)) && (mouseY < (hy + fontRendererObj.FONT_HEIGHT)))
					{
						entry = i;
						break;
					}
				}
			}
		}
		final boolean history = isInputEmpty();
		if (entry >= (qmawMode ? (history ? qmawHistory.size() : qmawResults.size()) : calcHistory.size()))
			entry = -1;
		return entry;
	}
	
	@Override
	protected void mouseClicked(int mouseX, int mouseY, int key)
	{
		super.mouseClicked(mouseX, mouseY, key);
		
//		if (isMouseInSegment(mouseX, mouseY))
//			System.out.printf("MouseX = %d, MouseY = %d, button = %d\n", mouseX, mouseY, key);
		
		if (!isMouseInSegment(mouseX, mouseY))
			return;
		
		if ((selectedHist = mouseEntrySelected(mouseX, mouseY)) >= 0)
		{
			if (qmawMode)
			{
				if (key == MOUSE_LEFT)
					executeQMAW(isInputEmpty());
				else if (key == MOUSE_MIDDLE)
					deleteQMAWHistoryEntry();
			} else
			{
				if (key == MOUSE_LEFT)
					handleCalculator((char) 10, 0);// Sometimes you gotta live by the jank
				else if (key == MOUSE_MIDDLE)
					deleteCalcHistoryEntry();
			}
		}
	}
	
	private void selectedHistSanityCheck()
	{
		selectedHist = MathHelper.clamp_int(selectedHist, -1, qmawMode ? (isInputEmpty() ? qmawHistory.size() : qmawResults.size()) : calcHistory.size());
	}
}
