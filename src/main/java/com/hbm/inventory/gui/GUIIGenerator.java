package com.hbm.inventory.gui;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerIGenerator;
import com.hbm.lib.Library;
import com.hbm.lib.RefStrings;
import com.hbm.packet.AuxButtonPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.render.util.GaugeUtil;
import com.hbm.render.util.GaugeUtil.Gauge;
import com.hbm.tileentity.machine.TileEntityMachineIGenerator;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;

public class GUIIGenerator extends GuiInfoContainer {
	
	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/generators/gui_igen.png");
	private TileEntityMachineIGenerator igen;
	boolean caughtMouse = false;

	public GUIIGenerator(InventoryPlayer invPlayer, TileEntityMachineIGenerator tedf) {
		super(new ContainerIGenerator(invPlayer, tedf));
		igen = tedf;
		
		this.xSize = 188;
		this.ySize = 222;
	}
	
	@Override
	public void drawScreen(int x, int y, float f) {
		super.drawScreen(x, y, f);
		
    	if(!caughtMouse && Mouse.isButtonDown(0) && guiLeft + 85 <= x && guiLeft + 85 + 18 > x && guiTop + 71 < y && guiTop + 71 + 18 >= y) {
    		caughtMouse = true;
    	}
    	
    	if(caughtMouse && !Mouse.isButtonDown(0)) {
    		int dial = (int) Math.round(Math.toDegrees(getAngle(x, y)));
    		igen.setDialByAngle(dial);
    		PacketDispatcher.wrapper.sendToServer(new AuxButtonPacket(igen.xCoord, igen.yCoord, igen.zCoord, dial, 2));
    		caughtMouse = false;
    	}

		this.drawCustomInfoStat(x, y, guiLeft + 76, guiTop + 20, 36, 12, x, y, new String[] { (igen.temperature + 300) + "K" });
		this.drawCustomInfoStat(x, y, guiLeft + 76, guiTop + 56, 36, 12, x, y, new String[] { (Math.round((igen.torque * igen.animSpeed / (360D * 20D)) * 10D) / 10D) + "RPM" });
		this.drawCustomInfoStat(x, y, guiLeft + 76, guiTop + 92, 36, 12, x, y, new String[] { Library.getShortNumber(igen.power) + "HE" });
		this.drawCustomInfoStat(x, y, guiLeft + 40, guiTop + 26, 18, 18, x, y, new String[] { (igen.burnTime / 20) + "s" });
		this.drawCustomInfoStat(x, y, guiLeft + 24, guiTop + 64, 14, 14, x, y, new String[] { "Add pellet to stack" });
		this.drawCustomInfoStat(x, y, guiLeft + 24, guiTop + 100, 14, 14, x, y, new String[] { "Take pellet from stack" });
    	
    	igen.tanks[0].renderTankInfo(this, x, y, guiLeft + 148, guiTop + 26, 18, 18);
    	igen.tanks[1].renderTankInfo(this, x, y, guiLeft + 148, guiTop + 62, 18, 18);
    	igen.tanks[2].renderTankInfo(this, x, y, guiLeft + 148, guiTop + 98, 18, 18);
	}

	protected void mouseClicked(int x, int y, int i) {
    	super.mouseClicked(x, y, i);
		
    	if(guiLeft + 24 <= x && guiLeft + 24 + 14 > x && guiTop + 64 < y && guiTop + 64 + 14 >= y) {
    		
    		//push pellet
			mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
    		PacketDispatcher.wrapper.sendToServer(new AuxButtonPacket(igen.xCoord, igen.yCoord, igen.zCoord, 0, 0));
    	}
		
    	if(guiLeft + 24 <= x && guiLeft + 24 + 14 > x && guiTop + 100 < y && guiTop + 100 + 14 >= y) {
    		
    		//pop pellet
			mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
    		PacketDispatcher.wrapper.sendToServer(new AuxButtonPacket(igen.xCoord, igen.yCoord, igen.zCoord, 0, 1));
    	}
    }
	
	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String name = this.igen.hasCustomInventoryName() ? this.igen.getInventoryName() : I18n.format(this.igen.getInventoryName());
		
		this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 14, this.ySize - 96 + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float iinterpolation, int x, int y) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		
		for(int i = 0; i < igen.pellets.length; i++) {
			
			if(igen.pellets[i] != null)
				drawTexturedModalRect(guiLeft + 6, guiTop + 106 - 4 * i, 188, igen.pellets[i].offset, 14, 9);
		}
		
		for(int i = 0; i < 3; i++) {
			if(igen.tanks[i].getFill() > 0) {
				
				int hex = igen.tanks[i].getTankType().getColor();
				
			    int r = (hex & 0xFF0000) >> 16;
			    int g = (hex & 0xFF00) >> 8;
			    int b = (hex & 0xFF);
			    
			    GL11.glColor3f(r / 256F, g / 256F, b / 256F);
			    
				drawTexturedModalRect(guiLeft + 149, guiTop + 39 + 36 * i, 218, 0, 16, 4);
			}
		}
		
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		
		drawDial(x, y);
		
		GaugeUtil.renderGauge(Gauge.BOW_SMALL, guiLeft + 40, guiTop + 26, this.zLevel, igen.getSolidGauge());

		GaugeUtil.renderGauge(Gauge.BAR_SMALL, guiLeft + 76, guiTop + 20, this.zLevel, igen.getTempGauge());
		GaugeUtil.renderGauge(Gauge.BAR_SMALL, guiLeft + 76, guiTop + 56, this.zLevel, igen.getTorqueGauge());
		GaugeUtil.renderGauge(Gauge.BAR_SMALL, guiLeft + 76, guiTop + 92, this.zLevel, igen.getPowerGauge());

		GaugeUtil.renderGauge(Gauge.WIDE_SMALL, guiLeft + 148, guiTop + 26, this.zLevel, (double)igen.tanks[0].getFill() / (double)igen.tanks[0].getMaxFill());
		GaugeUtil.renderGauge(Gauge.WIDE_SMALL, guiLeft + 148, guiTop + 62, this.zLevel, (double)igen.tanks[1].getFill() / (double)igen.tanks[1].getMaxFill());
		GaugeUtil.renderGauge(Gauge.WIDE_SMALL, guiLeft + 148, guiTop + 98, this.zLevel, (double)igen.tanks[2].getFill() / (double)igen.tanks[2].getMaxFill());
	}
	
	private void drawDial(float x, float y) {
		
		float angle = (float) getAngle(x, y);
		double pixel = 1D/256D;
		
		Vec3 vec = Vec3.createVectorHelper(8, 8, 0);
		vec.rotateAroundZ(-angle);

        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        
        tessellator.addVertexWithUV(guiLeft + 94 + vec.xCoord, guiTop + 80 + vec.yCoord, this.zLevel, pixel * 218, 0);
        vec.rotateAroundZ((float)Math.toRadians(90));
        tessellator.addVertexWithUV(guiLeft + 94 + vec.xCoord, guiTop + 80 + vec.yCoord, this.zLevel, pixel * 218, pixel * 16);
        vec.rotateAroundZ((float)Math.toRadians(90));
        tessellator.addVertexWithUV(guiLeft + 94 + vec.xCoord, guiTop + 80 + vec.yCoord, this.zLevel, pixel * 202, pixel * 16);
        vec.rotateAroundZ((float)Math.toRadians(90));
        tessellator.addVertexWithUV(guiLeft + 94 + vec.xCoord, guiTop + 80 + vec.yCoord, this.zLevel, pixel * 202, 0);
		
        tessellator.draw();
	}
	
	private double getAngle(float x, float y) {
		
		if(!caughtMouse)
			return Math.toRadians(igen.getAngleFromDial());
		
		double angle = -Math.atan2(guiLeft + 94 - x, guiTop + 80 - y) + (float) Math.PI * 1;

		angle = Math.max(angle, Math.PI * 0.25);
		angle = Math.min(angle, Math.PI * 1.75);
		
		return angle;
	}
}
