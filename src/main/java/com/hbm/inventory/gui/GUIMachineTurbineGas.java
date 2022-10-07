package com.hbm.inventory.gui;

import java.awt.event.MouseListener;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerMachineTurbineGas;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.lib.RefStrings;
import com.hbm.packet.NBTControlPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.tileentity.machine.TileEntityMachineTurbineGas;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

public class GUIMachineTurbineGas extends GuiInfoContainer {
	
	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/generators/gui_turbinegas.png");
	private static ResourceLocation gauge_tex = new ResourceLocation(RefStrings.MODID + ":textures/gui/gauges/button_big.png");
	private TileEntityMachineTurbineGas turbinegas;
	
	int yStart;
	int slidStart;
	
	public GUIMachineTurbineGas(InventoryPlayer invPlayer, TileEntityMachineTurbineGas te) {
		super(new ContainerMachineTurbineGas(invPlayer, te));
		turbinegas = te;
		
		this.xSize = 176;
		this.ySize = 204;
	}
	
	//@Override
	protected void mouseClicked(int x, int y, int i) {
		
		super.mouseClicked(x, y, i);
		
		slidStart = turbinegas.powerSliderPos;
		yStart = y;
		
		if(turbinegas.state != -1 && Math.sqrt(Math.pow((x - guiLeft - 88), 2) + Math.pow((y - guiTop - 31), 2)) <= 8) { //start/stop circular button
			
			int state = Math.abs(turbinegas.state) - 1; //offline(0) to startup(-1), online(1) to offline(0)
			mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
			
			NBTTagCompound data = new NBTTagCompound();
			data.setInteger("state", state);
			PacketDispatcher.wrapper.sendToServer(new NBTControlPacket(data, turbinegas.xCoord, turbinegas.yCoord, turbinegas.zCoord));
		}	
		
		if(turbinegas.state == 1 && x > guiLeft + 74 && x <= guiLeft + 74 + 29 && y >= guiTop + 77 && y < guiTop + 77 + 13) { //auto mode button
			
			boolean automode = !turbinegas.autoMode;
			mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
			
			NBTTagCompound data = new NBTTagCompound();
			data.setBoolean("autoMode", automode);
			PacketDispatcher.wrapper.sendToServer(new NBTControlPacket(data, turbinegas.xCoord, turbinegas.yCoord, turbinegas.zCoord));
		}
			
		if(!turbinegas.autoMode && turbinegas.state == 1 && (guiTop + 88 - slidStart) <= yStart && (guiTop + 94 - slidStart) > yStart && guiLeft + 36 < x && guiLeft + 52 >= x) { //power slider
				
			mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
		}
	}
	
	@Override
	protected void mouseClickMove(int x, int y, int p_146273_3_, long p_146273_4_) {
		
		super.mouseClickMove(x, y, p_146273_3_, p_146273_4_);
	
		int slidPos = turbinegas.powerSliderPos;
	
		if(!turbinegas.autoMode && turbinegas.state == 1 && guiLeft + 36 < x && guiLeft + 52 >= x && guiTop + 28 < y && guiTop + 94 >= y) { //area in which the slider can move
		
			if((guiTop + 88 - slidStart) <= yStart && (guiTop + 94 - slidStart) > yStart) {
				slidPos = guiTop + 91 - y;
			
				if(slidPos > 60) 
					slidPos = 60;
				else if(slidPos < 0)
					slidPos = 0;
			
				NBTTagCompound data = new NBTTagCompound();
				data.setDouble("slidPos", slidPos);
				PacketDispatcher.wrapper.sendToServer(new NBTControlPacket(data, turbinegas.xCoord, turbinegas.yCoord, turbinegas.zCoord));
			}
		}
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) { //TODO all the displayed things are moved down by some pixels
		
		super.drawScreen(mouseX, mouseY, f);

		this.drawElectricityInfo(this, mouseX, mouseY, guiLeft + 26, guiTop + 100, 142, 16, turbinegas.power, turbinegas.getMaxPower());
		
		if(turbinegas.powerSliderPos == 0)
			this.drawCustomInfoStat(mouseX, mouseY, guiLeft + 36, guiTop + 28, 16, 66, mouseX, mouseY, new String[] {"Turbine idle"});
		else
			this.drawCustomInfoStat(mouseX, mouseY, guiLeft + 36, guiTop + 28, 16, 66, mouseX, mouseY, new String[] {(turbinegas.powerSliderPos) * 100 / 60 + "% power"});	
		
		if(turbinegas.temp >= 20)
			this.drawCustomInfoStat(mouseX, mouseY, guiLeft + 133, guiTop + 15, 8, 72, mouseX, mouseY, new String[] {"Temperature: " + (turbinegas.temp) + " °C"});
		else
			this.drawCustomInfoStat(mouseX, mouseY, guiLeft + 133, guiTop + 15, 8, 72, mouseX, mouseY, new String[] {"Temperature: 20 °C"});
		
		turbinegas.tanks[0].renderTankInfo(this, mouseX, mouseY, guiLeft + 8, guiTop + 8, 16, 48); //TODO these suck, are imprecise
		turbinegas.tanks[1].renderTankInfo(this, mouseX, mouseY, guiLeft + 8, guiTop + 62, 16, 32);
		turbinegas.tanks[2].renderTankInfo(this, mouseX, mouseY, guiLeft + 147, guiTop + 53, 16, 40);
		turbinegas.tanks[3].renderTankInfo(this, mouseX, mouseY, guiLeft + 147, guiTop + 13, 16, 40);
		
		String[] text1 = new String[] { "wip", //TODO 
				"wipp",
				"work in progress",
				"sork in wrogress" };
		this.drawCustomInfoStat(mouseX, mouseY, guiLeft - 16, guiTop + 36 + 16, 16, 16, guiLeft - 8, guiTop + 36 + 16, text1);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float iinterpolation, int x, int y) {
		
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize); //the main thing
		
		if(turbinegas.autoMode)
			drawTexturedModalRect(guiLeft + 74, guiTop + 77, 194, 11, 29, 13); //auto mode button
		else
			drawTexturedModalRect(guiLeft + 74, guiTop + 77, 194, 24, 29, 13);
		
		switch(turbinegas.state) {
		case 0:
			drawTexturedModalRect(guiLeft + 80, guiTop + 23, 178, 38, 16, 16); //red button
			break;
		case -1:
			drawTexturedModalRect(guiLeft + 80, guiTop + 23, 194, 38, 16, 16); //orange button
			displayStartup();
			break;
		case 1:
			drawTexturedModalRect(guiLeft + 80, guiTop + 23, 210, 38, 16, 16); //green button
			drawPowerMeterDisplay((int) (20 * turbinegas.instantPowerOutput));
			break;
		default:
			break;
		}
		
		drawTexturedModalRect(guiLeft + 36, guiTop + 88 - turbinegas.powerSliderPos, 178, 0, 16, 6); //power slider
		
		int power = (int) (turbinegas.power * 142 / turbinegas.maxPower); //power storage
		drawTexturedModalRect(guiLeft + 26, guiTop + 100, 0, 204, power, 16);
		
		drawRPMGauge(turbinegas.rpm);
		drawThermometer(turbinegas.temp);
		
		turbinegas.tanks[0].renderTank(guiLeft + 8, guiTop + 56, this.zLevel, 16, 48);
		turbinegas.tanks[1].renderTank(guiLeft + 8, guiTop + 94, this.zLevel, 16, 32);
		turbinegas.tanks[2].renderTank(guiLeft + 147, guiTop + 89, this.zLevel, 16, 36);
		turbinegas.tanks[3].renderTank(guiLeft + 147, guiTop + 49, this.zLevel, 16, 36);
	}
	
	int numberToDisplay = 0; //for startup
	int digitNumber = 0; 
	int exponent = 0;
	
	public void displayStartup() {
		
		boolean displayOn = true;
		
		if(numberToDisplay < 888888 && turbinegas.counter < 60) { //48 frames needed to complete
			
			digitNumber++;
			if(digitNumber == 9) {
				digitNumber = 1;
				exponent++;
			}
			numberToDisplay += Math.pow(10, exponent);
		}
		
		if(turbinegas.counter > 50)
			numberToDisplay = 0;
		
		drawPowerMeterDisplay(numberToDisplay);
	}
	
	protected void drawPowerMeterDisplay(int number) { //display code TODO the number that was displayed previously isn't updated or shit like that
		
		int firstDigitX = 66;
		int firstDigitY = 62;
		
		int width = 5;
		int height = 11;
		int spaceBetweenBumbers = 3;
		
		int[] digit = new int[6];
		
		for(int i = 5; i >= 0; i--) { //creates an array of digits that represent the numbers
			
			digit[i] = (int) (number % 10);
			
			number = number / 10;
			
			drawTexturedModalRect(guiLeft + firstDigitX + i * 8, guiTop + firstDigitY, 194 + digit[i] * 5, 0, 5, 11);
		}
		
		int uselessZeros = 0;
		
		for(int i = 0; i < 5; i++) { //counts how much zeros there are before the number, to display 57 instead of 000057
			
			if(digit[i] == 0)
				uselessZeros++;
			else
				break;
		}
		
		for(int i = 0; i < uselessZeros; i++) { //turns off the useless zeros
			
			drawTexturedModalRect(guiLeft + firstDigitX + i * 8, guiTop + firstDigitY, 244, 0, 5, 11);
		}
	}
	
	protected void drawThermometer(int temp) {
		
		int xPos = guiLeft + 136;
		int yPos = guiTop + 19;
		
		int width = 2;
		int height = 64;
		
		int maxTemp = 800;
		
		double uMin = (176D / 256D);
		double uMax = (178D / 256D);
		double vMin = ((64D - 64 * temp / maxTemp) / 256D);
		double vMax = (64D / 256D);
		
		GL11.glEnable(GL11.GL_BLEND);
		
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		
		Tessellator tessellator = Tessellator.instance;
		
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV(xPos, yPos + height, this.zLevel, uMin, vMax);
		tessellator.addVertexWithUV(xPos + width, yPos + height, this.zLevel, uMax, vMax);
		tessellator.addVertexWithUV(xPos + width, yPos + 64 - (64 * temp / maxTemp), this.zLevel,uMax, vMin);
		tessellator.addVertexWithUV(xPos, yPos + 64 - (64 * temp / maxTemp), this.zLevel, uMin, vMin);
		tessellator.draw();

		GL11.glDisable(GL11.GL_BLEND);
	}
	
	protected void drawRPMGauge(int position) {
		
		int xPos = guiLeft + 64;
		int yPos = guiTop + 7;
		
		int squareSideLenght = 48;
		
		double uMin = (48D / 4848D) * position;
		double uMax = (48D / 4848D) * (position + 1);
		double vMin = 0D;
		double vMax = 1D;
		
		GL11.glEnable(GL11.GL_BLEND);
		
		Minecraft.getMinecraft().getTextureManager().bindTexture(gauge_tex); //long boi
		
		Tessellator tessellator = Tessellator.instance;
		
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV(xPos, yPos + squareSideLenght, this.zLevel, uMin, vMax);
		tessellator.addVertexWithUV(xPos + squareSideLenght, yPos + squareSideLenght, this.zLevel, uMax, vMax);
		tessellator.addVertexWithUV(xPos + squareSideLenght, yPos, this.zLevel,uMax, vMin);
		tessellator.addVertexWithUV(xPos, yPos, this.zLevel, uMin, vMin);
		tessellator.draw();

		GL11.glDisable(GL11.GL_BLEND);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		//useless piece of shit, at least for now
	}
}
