package com.hbm.inventory.gui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Nonnegative;

import com.hbm.interfaces.Untested;
import com.hbm.lib.Library;
import com.hbm.lib.RefStrings;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

public abstract class GuiInfoContainer extends GuiContainer {
	
	ResourceLocation guiUtil =  new ResourceLocation(RefStrings.MODID + ":textures/gui/gui_utility.png");

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
	public static boolean getTextBool(int mouseX, int mouseY, int fX, int fY, int fW, int fH)
	{
		return mouseX >= fX && mouseX < fX + fW && mouseY >= fY && mouseY < fY + fH;
	}
	public static boolean getTextBool(int mouseX, int mouseY, GuiTextField field, int guiLeft, int guiTop)
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
		private int padding = 3;
		/** Does it blink or not, default false **/
		private boolean blink = false;
		/** Max number the display can handle **/
		private int maxNum;
		/** Min number the display can handle **/
		private int minNum;
		private boolean customBounds = false;
		/** Does it pad out with zeros **/
		private boolean pads = false;
		/** Max number of digits the display has, default 3 **/
		@Nonnegative
		private byte digitLength = 3;
		private int numIn = 0;
		private char[] toDisp = new char[] {0, 0, 0};
		@Nonnegative
		private byte dispOffset = 0;
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
			byte gap = (byte) (digitLength - num.length);
			for (int i = 0; i < num.length; i++)
			{
				dispOffset = (byte) ((padding + 6) * (i + gap));
				drawNumber(num[i]);
			}
			if (pads && gap > 0)
			{
				for (int i = 0; i < gap; i++)
				{
					dispOffset = (byte) ((padding + 6) * i);
					drawNumber('0');
				}
			}
		}
		/**
		 * Draw the previously provided number
		 */
		public void drawNumber()
		{
			byte gap = (byte) (digitLength - toDisp.length);
			for (int i = 0; i < toDisp.length; i++)
			{
				dispOffset = (byte) ((padding + 6) * (i + gap));
				drawNumber(toDisp[i]);
			}
			
			if (pads && gap > 0)
			{
				for (int i = 0; i < gap; i++)
				{
					dispOffset = (byte) ((padding + 6) * i);
					drawNumber('0');
				}
			}
		}
		/** Draw a single number (requires dispOffset to be set) **/
		public void drawNumber(char num)
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
		
		private void drawVertical(int posX, int posY)
		{
			byte offsetX = (byte) (posX * 5);
			byte offsetY = (byte) (posY * 6);
			drawTexturedModalRect(guiLeft + displayX + offsetX + dispOffset, guiTop + displayY + offsetY + 1, referenceX, referenceY + 1, 1, 5);
		}
		
		public void setNumber(int in)
		{
			numIn = in;
			if (customBounds)
				numIn = MathHelper.clamp_int(in, minNum, maxNum);
			
			toDisp = new Integer(numIn).toString().toCharArray();
		}
		/** Get the set number **/
		public Integer getNumber()
		{
			return numIn;
		}
		/** Make the display blink **/
		public NumberDisplay setBlinks()
		{
			blink = true;
			return this;
		}
		/** Padding between digits, default 3 **/
		public NumberDisplay setPadding(int p)
		{
			padding = p;
			return this;
		}
		/** Max number of digits **/
		public NumberDisplay setDigitLength(byte l)
		{
			digitLength = l;
			return this;
		}
		/** Set custom number bounds **/
		public NumberDisplay setMaxMin(int max, int min)
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
	}
}
