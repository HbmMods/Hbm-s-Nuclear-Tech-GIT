package com.hbm.inventory.gui;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Nonnegative;

import com.google.common.annotations.Beta;
import com.hbm.interfaces.Untested;
import com.hbm.lib.Library;
import com.hbm.lib.RefStrings;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import scala.runtime.DoubleRef;

public abstract class GuiInfoContainer extends GuiContainer {
	
	ResourceLocation guiUtil =  new ResourceLocation(RefStrings.MODID + ":textures/gui/gui_utility.png");
	public static final int color0 = 4210752;
	public GuiInfoContainer(Container p_i1072_1_) {
		super(p_i1072_1_);
	}
	
	public void drawFluidInfo(String[] text, int x, int y) {
		this.func_146283_a(Arrays.asList(text), x, y);
	}
	
	public void drawElectricityInfo(GuiInfoContainer gui, int mouseX, int mouseY, int x, int y, int width, int height, long power, long maxPower) {
		if(x <= mouseX && x + width > mouseX && y < mouseY && y + height >= mouseY)
			gui.drawFluidInfo(new String[] { Library.getShortNumber(power) + "/" + Library.getShortNumber(maxPower) + "HE" }, mouseX, mouseY);
	}
	
	public void drawCustomInfo(GuiInfoContainer gui, int mouseX, int mouseY, int x, int y, int width, int height, String[] text) {
		if(x <= mouseX && x + width > mouseX && y < mouseY && y + height >= mouseY)
			this.func_146283_a(Arrays.asList(text), mouseX, mouseY);
	}
	
	public void drawCustomInfoStat(int mouseX, int mouseY, int x, int y, int width, int height, int tPosX, int tPosY, String[] text) {
		
		if(x <= mouseX && x + width > mouseX && y < mouseY && y + height >= mouseY)
			this.func_146283_a(Arrays.asList(text), tPosX, tPosY);
	}
	/**
	 * Get a scaled number for a display bar
	 * @param toScale - The number to scale
	 * @param scaleBy - The size of the display bar
	 * @param total - Max size of toScale
	 * @return The scaled number to be used
	 */
	public static int getScaledBar(int toScale, int scaleBy, int total)
	{
		return (toScale * scaleBy) / total;
	}
	/**
	 * Get a scaled number for a display bar
	 * @param toScale - The number to scale
	 * @param scaleBy - The size of the display bar
	 * @param total - Max size of toScale
	 * @return The scaled number to be used
	 */
	public static long getScaledBar(long toScale, int scaleBy, long total)
	{
		return (toScale * scaleBy) / total;
	}
	/**
	 * Check if a button was pressed
	 * @param mouseX - The mouse's X coord
	 * @param mouseY - The mouse's Y coord
	 * @param buttonX - X coord of the button
	 * @param buttonY - Y coord of the button
	 * @param xSize - X size of the button
	 * @param ySize - Y size of the button
	 * @param guiLeft - Left bound of the GUI
	 * @param guiTop - Top bound of the GUI
	 * @return If it was clicked
	 */
	public static boolean getButtonBool(int mouseX, int mouseY, int buttonX, int buttonY, int xSize, int ySize, int guiLeft, int guiTop)
	{
		return guiLeft + buttonX <= mouseX && guiLeft + buttonX + xSize > mouseX && guiTop + buttonY < mouseY && guiTop + buttonY + ySize >= mouseY;
	}
	public boolean getButtonBool(int mouseX, int mouseY, int buttonX, int buttonY, int xSize, int ySize)
	{
		return guiLeft + buttonX <= mouseX && guiLeft + buttonX + xSize > mouseX && guiTop + buttonY < mouseY && guiTop + buttonY + ySize >= mouseY;
	}
	public static boolean getTextBool(int mouseX, int mouseY, int fX, int fY, int fW, int fH)
	{
		return mouseX >= fX && mouseX < fX + fW && mouseY >= fY && mouseY < fY + fH;
	}
	public static boolean getTextBool(int mouseX, int mouseY, GuiTextField field)
	{
		return mouseX >= field.xPosition && mouseX < field.xPosition + field.width && mouseY >= field.yPosition && mouseY < field.yPosition + field.height;
	}
	public void drawInfoPanel(int x, int y, int width, int height, int type) {

		Minecraft.getMinecraft().getTextureManager().bindTexture(guiUtil);
		
		switch(type) {
		case 0:
			//Small blue I
			drawTexturedModalRect(x, y, 0, 0, 8, 8); break;
		case 1:
			//Small green I
			drawTexturedModalRect(x, y, 0, 8, 8, 8); break;
		case 2:
			//Large blue I
			drawTexturedModalRect(x, y, 8, 0, 16, 16); break;
		case 3:
			//Large green I
			drawTexturedModalRect(x, y, 24, 0, 16, 16); break;
		case 4:
			//Small red !
			drawTexturedModalRect(x, y, 0, 16, 8, 8); break;
		case 5:
			//Small yellow !
			drawTexturedModalRect(x, y, 0, 24, 8, 8); break;
		case 6:
			//Large red !
			drawTexturedModalRect(x, y, 8, 16, 16, 16); break;
		case 7:
			//Large yellow !
			drawTexturedModalRect(x, y, 24, 16, 16, 16); break;
		case 8:
			//Small blue *
			drawTexturedModalRect(x, y, 0, 32, 8, 8); break;
		case 9:
			//Small grey *
			drawTexturedModalRect(x, y, 0, 40, 8, 8); break;
		case 10:
			//Large blue *
			drawTexturedModalRect(x, y, 8, 32, 16, 16); break;
		case 11:
			//Large grey *
			drawTexturedModalRect(x, y, 24, 32, 16, 16); break;
		}
	}
	/**
	 * Turn a string into an integer and remove all non-digit characters
	 * @param in - The text input
	 * @return The formatted integer or -1 if invalid
	 */
	public static Integer validateTextInput(String in)
	{
		return validateTextInput(in, Integer.MIN_VALUE, Integer.MAX_VALUE);
	}
	
	public static Integer validateTextInput(String in, int min, int max)
	{
		String formatted = in.replaceAll("\\D", "");
		return formatted.isEmpty() || formatted.length() == 0 ? -1 : MathHelper.clamp_int(Integer.parseInt(formatted), min, max);
	}
	
	/**
	 * Turn a string into a integer and allow non-digit characters (most likely the negative sign)
	 * @param in - The text input
	 * @return The formatted integer or -1 if invalid
	 */
	public static Integer validateTextInputAllowChars(String in)
	{
		return validateTextInputAllowChars(in, Integer.MIN_VALUE, Integer.MAX_VALUE);
	}
	
	public static Integer validateTextInputAllowChars(String in, int min, int max)
	{
		try
		{
			return MathHelper.clamp_int(Byte.parseByte(in), min, max);
		}
		catch (NumberFormatException e)
		{
			e.printStackTrace();
			return -1;
		}
	}
	@Beta
	public class NumberDisplay
	{
		/** The display's X coordinate **/
		private int displayX;
		/** The display's Y coordinate **/
		private int displayY;
		/** The X coordinate of the reference **/
		private int referenceX;
		/** The Y coordinate of the reference **/
		private int referenceY;
		/** The amount of padding between digits, default 3 **/
		@Nonnegative
		private byte padding = 3;
		/** Does it blink or not, default false **/
		private boolean blink = false;
		/** Max number the display can handle **/
		private float maxNum;
		/** Min number the display can handle **/
		private float minNum;
		private boolean customBounds = false;
		// Should it be a decimal number?
		private boolean isFloat = false;
		// How many trailing zeros?
		private byte floatPad = 1;
		/** Does it pad out with zeros **/
		private boolean pads = false;
		/** Max number of digits the display has, default 3 **/
		@Nonnegative
		private byte digitLength = 3;
		private double numIn = 0;
		private char[] toDisp = new char[] {0, 0, 0};
		@Nonnegative
		private short dispOffset = 0;
		/**
		 * Construct a new number display
		 * @param dX - X coordinate of the display
		 * @param dY - Y coordinate of the display
		 * @param rX - X coordinate of the reference
		 * @param rY - Y coordinate of the reference
		 */
		public NumberDisplay(int dX, int dY, int rX, int rY) 
		{
			displayX = dX;
			displayY = dY;
			referenceX = rX;
			referenceY = rY;
		}
		/**
		 * Draw custom number
		 * @param num - The char array that has the number
		 */
		public void drawNumber(char[] num)
		{
			short gap = (short) (digitLength - num.length);
			for (int i = 0; i < num.length; i++)
			{
				if (num[i] == '.')
					gap--;
				dispOffset = (short) ((padding + 6) * (i + gap));
				drawChar(num[i]);
			}
			if (pads)
				padOut(gap);
		}
		/** Draw the previously provided number **/
		public void drawNumber()
		{
			drawNumber(toDisp);
		}
		private void padOut(short gap)
		{
			if (gap == 0)
				return;
			for (int i = 0; i < gap; i++)
			{
				dispOffset = (short) ((padding + 6) * i);
				drawChar('0');
			}
		}
		
		/** Draw a single character (requires dispOffset to be set) **/
		public void drawChar(char num)
		{
//			System.out.println(num);
			switch (num)
			{
			case '1':
				drawVertical(1, 0);
				drawVertical(1, 1);
				break;
			case '2':
				drawHorizontal(0);
				drawVertical(1, 0);
				drawHorizontal(1);
				drawVertical(0, 1);
				drawHorizontal(2);
				break;
			case '3':
				drawHorizontal(0);
				drawHorizontal(1);
				drawHorizontal(2);
				drawVertical(1, 0);
				drawVertical(1, 1);
				break;
			case '4':
				drawVertical(0, 0);
				drawVertical(1, 0);
				drawVertical(1, 1);
				drawHorizontal(1);
				break;
			case '5':
				drawHorizontal(0);
				drawHorizontal(1);
				drawHorizontal(2);
				drawVertical(0, 0);
				drawVertical(1, 1);
				break;
			case '6':
				drawHorizontal(0);
				drawHorizontal(1);
				drawHorizontal(2);
				drawVertical(0, 0);
				drawVertical(0, 1);
				drawVertical(1, 1);
				break;
			case '7':
				drawHorizontal(0);
				drawVertical(1, 0);
				drawVertical(1, 1);
				break;
			case '8':
				drawHorizontal(0);
				drawHorizontal(1);
				drawHorizontal(2);
				drawVertical(0, 0);
				drawVertical(1, 0);
				drawVertical(0, 1);
				drawVertical(1, 1);
				break;
			case '9':
				drawHorizontal(0);
				drawHorizontal(1);
				drawHorizontal(2);
				drawVertical(0, 0);
				drawVertical(1, 0);
				drawVertical(1, 1);
				break;
			case '0':
				drawHorizontal(0);
				drawHorizontal(2);
				drawVertical(0, 0);
				drawVertical(0, 1);
				drawVertical(1, 0);
				drawVertical(1, 1);
				break;
			case '-':
				drawHorizontal(1);
				break;
			case '.':
				drawPeriod();
				break;
			case 'E':
			default:
				drawHorizontal(0);
				drawHorizontal(1);
				drawHorizontal(2);
				drawVertical(0, 0);
				drawVertical(0, 1);
				break;
			}
		}
		
		private void drawHorizontal(int pos)
		{
			byte offset = (byte) (pos * 6);
			drawTexturedModalRect(guiLeft + displayX + dispOffset + 1, guiTop + displayY + offset, referenceX + 1, referenceY, 5, 1);
		}
		
		private void drawPeriod()
		{
			drawTexturedModalRect(guiLeft + displayX + dispOffset + padding - (int) Math.ceil(padding / 2) + 5, guiTop + displayY + 12, referenceX + 1, referenceY, 1, 1);
		}
		
		private void drawVertical(int posX, int posY)
		{
			byte offsetX = (byte) (posX * 5);
			byte offsetY = (byte) (posY * 6);
			drawTexturedModalRect(guiLeft + displayX + offsetX + dispOffset, guiTop + displayY + offsetY + 1, referenceX, referenceY + 1, 1, 5);
		}
		
		public void setNumber(double in)
		{
			numIn = in;
			if (customBounds)
				numIn = MathHelper.clamp_double(in, minNum, maxNum);
			
			toDisp = new Long((int) Math.round(new Double(numIn))).toString().toCharArray();
			toDisp = truncOrExpand();
		}
		/** Get the set number **/
		public Double getNumber()
		{
			return numIn;
		}
		/** Get the char array for display **/
		public char[] getDispNumber()
		{
			return toDisp.clone();
		}
		/** Make the display blink **/
		public NumberDisplay setBlinks()
		{
			blink = true;
			return this;
		}
		/** Padding between digits, default 3 **/
		public NumberDisplay setPadding(@Nonnegative int p)
		{
			padding = (byte) p;
			return this;
		}
		/** Max number of digits **/
		public NumberDisplay setDigitLength(@Nonnegative int l)
		{
			digitLength = (byte) l;
			toDisp = truncOrExpand();
			return this;
		}
		/** Set custom number bounds **/
		public NumberDisplay setMaxMin(float max, float min)
		{
			if (min > max)
				throw new IllegalArgumentException("Minimum value is larger than maximum value!");
			maxNum = max;
			minNum = min;
			customBounds = true;
			return this;
		}
		/** Pad out the left side of the number with zeros **/
		public NumberDisplay setPadNumber()
		{
			pads = true;
			return this;
		}
		/** Set the number to be a decimal, default zero trailing is 1 **/
		public NumberDisplay setFloat()
		{
			return setFloat(1);
		}
		/** Set the number to be a decimal with specified zero trailing **/
		public NumberDisplay setFloat(@Nonnegative int pad)
		{
			floatPad = (byte) pad;
			isFloat = true;
			BigDecimal bd = new BigDecimal(new Double(numIn).toString());
			bd = bd.setScale(pad, RoundingMode.HALF_UP);
			
//			char[] proc = new Double(bd.doubleValue()).toString().toCharArray();
			char[] proc = bd.toString().toCharArray();
			
			if (proc.length == digitLength)
				toDisp = proc;
			else
				toDisp = truncOrExpand();
			
			return this;
		}
		@Beta
		private char[] truncOrExpand()
		{
			if (isFloat)
			{
				char[] out = Arrays.copyOf(toDisp, digitLength);
				for (int i = 0; i < digitLength; i++)
					if (out[i] == '\u0000')
						out[i] = '0';
				return out.clone();
			}
			return toDisp;
		}
	}
}
