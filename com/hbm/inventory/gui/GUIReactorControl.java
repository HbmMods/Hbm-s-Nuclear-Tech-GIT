package com.hbm.inventory.gui;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerReactorControl;
import com.hbm.lib.RefStrings;
import com.hbm.packet.AuxButtonPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.tileentity.machine.TileEntityReactorControl;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUIReactorControl extends GuiInfoContainer {

	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/gui_reactor_control.png");
	private TileEntityReactorControl control;
	
	public GUIReactorControl(InventoryPlayer invPlayer, TileEntityReactorControl tedf) {
		super(new ContainerReactorControl(invPlayer, tedf));
		control = tedf;
		
		this.xSize = 176;
		this.ySize = 166;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);

		this.drawCustomInfo(this, mouseX, mouseY, guiLeft + 80, guiTop + 34, 88, 4, new String[] { "Fuel: " + control.fuel + "%" });
		this.drawCustomInfo(this, mouseX, mouseY, guiLeft + 80, guiTop + 40, 88, 4, new String[] { "Water: " + control.water + "/" + control.maxWater + "mB" });
		this.drawCustomInfo(this, mouseX, mouseY, guiLeft + 80, guiTop + 46, 88, 4, new String[] { "Coolant: " + control.cool + "/" + control.maxCool + "mB" });
		

		this.drawCustomInfo(this, mouseX, mouseY, guiLeft + 7, guiTop + 16, 18, 18, new String[] { "Reactor Status: " + (control.isOn ? "ON" : "OFF") });
		this.drawCustomInfo(this, mouseX, mouseY, guiLeft + 43, guiTop + 16, 18, 18, new String[] { "Automatic Shutdown: " + (control.auto ? "ENABLED" : "DISABLED") });

		
		if(!control.isLinked) {
			this.drawCustomInfo(this, mouseX, mouseY, guiLeft + 79, guiTop + 16, 18, 18, new String[] { "Reactor link not found!" });
		}

		if(control.water < control.maxWater * 0.1) {
			this.drawCustomInfo(this, mouseX, mouseY, guiLeft + 79 + 18, guiTop + 16, 18, 18, new String[] { "Water level low!" });
		}
		
		if(control.cool < control.maxCool * 0.1) {
			this.drawCustomInfo(this, mouseX, mouseY, guiLeft + 79 + 18 * 2, guiTop + 16, 18, 18, new String[] { "Coolant level low!" });
		}
		
		if(control.steam > control.maxSteam * 0.95) {
			this.drawCustomInfo(this, mouseX, mouseY, guiLeft + 79 + 18 * 3, guiTop + 16, 18, 18, new String[] { "Steam buffer full!" });
		}
		
		if(control.coreHeat > (50000 * 0.85)) {
			this.drawCustomInfo(this, mouseX, mouseY, guiLeft + 79 + 18 * 4, guiTop + 16, 18, 18, new String[] { "CORE TEMPERATURE CRITICAL!!" });
		}
		
		String s = "";
		switch(control.compression) {
		case 0: s = "Steam"; break;
		case 1: s = "Dense Steam"; break;
		case 2: s = "Super Dense Steam"; break;
		}
		this.drawCustomInfo(this, mouseX, mouseY, guiLeft + 80, guiTop + 52, 88, 4, new String[] { s + ": " + control.steam + "/" + control.maxSteam + "mB" });
		
		this.drawCustomInfo(this, mouseX, mouseY, guiLeft + 80, guiTop + 58, 88, 4, new String[] { "Hull Heat: " + Math.round((control.hullHeat) * 0.00001 * 980 + 20) + "°C" });
		this.drawCustomInfo(this, mouseX, mouseY, guiLeft + 80, guiTop + 64, 88, 4, new String[] { "Core Heat: " + Math.round((control.coreHeat) * 0.00002 * 980 + 20) + "°C" });
	}

	protected void mouseClicked(int x, int y, int i) {
    	super.mouseClicked(x, y, i);
		
    	if(guiLeft + 7 <= x && guiLeft + 7 + 18 > x && guiTop + 16 < y && guiTop + 16 + 18 >= y) {
    		
			mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
    		PacketDispatcher.wrapper.sendToServer(new AuxButtonPacket(control.xCoord, control.yCoord, control.zCoord, control.isOn ? 0 : 1, 0));
    	}
		
    	if(guiLeft + 43 <= x && guiLeft + 43 + 18 > x && guiTop + 16 < y && guiTop + 16 + 18 >= y) {
    		
			mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
    		PacketDispatcher.wrapper.sendToServer(new AuxButtonPacket(control.xCoord, control.yCoord, control.zCoord, control.auto ? 0 : 1, 1));
    	}
		
    	if(guiLeft + 63 <= x && guiLeft + 63 + 14 > x && guiTop + 52 < y && guiTop + 52 + 18 >= y) {
    		
			mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
    		PacketDispatcher.wrapper.sendToServer(new AuxButtonPacket(control.xCoord, control.yCoord, control.zCoord, (control.compression + 1) % 3, 2));
    	}
    }
	
	@Override
	protected void drawGuiContainerForegroundLayer( int i, int j) {
		String name = this.control.hasCustomInventoryName() ? this.control.getInventoryName() : I18n.format(this.control.getInventoryName());
		
		this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
		
		this.fontRendererObj.drawString("Rods: " + control.rods + "%", 8, 40, 4210752);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		
		if(control.hullHeat > 0) {
			int i = (control.hullHeat * 88 / 100000);
			
			i = (int) Math.min(i, 160);
			
			drawTexturedModalRect(guiLeft + 80, guiTop + 59, 0, 166, i, 4);
		}
		
		if(control.coreHeat > 0) {
			int i = (control.coreHeat * 88 / 50000);
			
			i = (int) Math.min(i, 160);
			
			drawTexturedModalRect(guiLeft + 80, guiTop + 65, 0, 170, i, 4);
		}
		
		if(control.steam > 0) {
			int i = (control.steam * 88 / control.maxSteam);
			
			i = (int) Math.min(i, 160);
			
			drawTexturedModalRect(guiLeft + 80, guiTop + 53, 0, 174 + 4 * control.compression, i, 4);
		}
		
		if(control.cool > 0) {
			int i = (control.cool * 88 / control.maxCool);
			
			i = (int) Math.min(i, 160);
			
			drawTexturedModalRect(guiLeft + 80, guiTop + 47, 0, 194, i, 4);
		}
		
		if(control.water > 0) {
			int i = (control.water * 88 / control.maxWater);
			
			i = (int) Math.min(i, 160);
			
			drawTexturedModalRect(guiLeft + 80, guiTop + 41, 0, 190, i, 4);
		}
		
		if(control.fuel > 0) {
			int i = (control.fuel * 88 / 100);
			
			i = (int) Math.min(i, 160);
			
			drawTexturedModalRect(guiLeft + 80, guiTop + 35, 0, 186, i, 4);
		}
		
		if(control.isOn) {
			drawTexturedModalRect(guiLeft + 7, guiTop + 16, 218, 0, 18, 18);
		}
		
		if(control.auto) {
			drawTexturedModalRect(guiLeft + 43, guiTop + 16, 236, 0, 18, 18);
		}
		
		drawTexturedModalRect(guiLeft + 63, guiTop + 52, 176 + 14 * control.compression, 0, 14, 18);
		
		if(!control.isLinked) {
			drawTexturedModalRect(guiLeft + 79, guiTop + 16, 88, 166, 18, 18);
		}

		if(control.water < control.maxWater * 0.1) {
			drawTexturedModalRect(guiLeft + 79 + 18, guiTop + 16, 88 + 18, 166, 18, 18);
		}
		
		if(control.cool < control.maxCool * 0.1) {
			drawTexturedModalRect(guiLeft + 79 + 18 * 2, guiTop + 16, 88 + 18 * 2, 166, 18, 18);
		}
		
		if(control.steam > control.maxSteam * 0.95) {
			drawTexturedModalRect(guiLeft + 79 + 18 * 3, guiTop + 16, 88 + 18 * 3, 166, 18, 18);
		}
		
		if(control.coreHeat > (50000 * 0.85)) {
			drawTexturedModalRect(guiLeft + 79 + 18 * 4, guiTop + 16, 88 + 18 * 4, 166, 18, 18);
		}
		
		if(control.rods == control.maxRods && control.rods != 0) {
			drawTexturedModalRect(guiLeft + 25, guiTop + 16, 176, 18, 18, 18);
		} else if(control.rods > 0) {
			drawTexturedModalRect(guiLeft + 25, guiTop + 16, 194, 18, 18, 18);
		}
	}
	
    protected void keyTyped(char ch, int i)
    {
        super.keyTyped(ch, i);
        
        switch(ch) {
        case '0':
			mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
    		PacketDispatcher.wrapper.sendToServer(new AuxButtonPacket(control.xCoord, control.yCoord, control.zCoord, 0, 0)); break;
        case '1':
			mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
    		PacketDispatcher.wrapper.sendToServer(new AuxButtonPacket(control.xCoord, control.yCoord, control.zCoord, 10, 0)); break;
        case '2':
			mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
    		PacketDispatcher.wrapper.sendToServer(new AuxButtonPacket(control.xCoord, control.yCoord, control.zCoord, 20, 0)); break;
        case '3':
			mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
    		PacketDispatcher.wrapper.sendToServer(new AuxButtonPacket(control.xCoord, control.yCoord, control.zCoord, 30, 0)); break;
        case '4':
			mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
    		PacketDispatcher.wrapper.sendToServer(new AuxButtonPacket(control.xCoord, control.yCoord, control.zCoord, 40, 0)); break;
        case '5':
			mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
    		PacketDispatcher.wrapper.sendToServer(new AuxButtonPacket(control.xCoord, control.yCoord, control.zCoord, 50, 0)); break;
        case '6':
			mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
    		PacketDispatcher.wrapper.sendToServer(new AuxButtonPacket(control.xCoord, control.yCoord, control.zCoord, 60, 0)); break;
        case '7':
			mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
    		PacketDispatcher.wrapper.sendToServer(new AuxButtonPacket(control.xCoord, control.yCoord, control.zCoord, 70, 0)); break;
        case '8':
			mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
    		PacketDispatcher.wrapper.sendToServer(new AuxButtonPacket(control.xCoord, control.yCoord, control.zCoord, 80, 0)); break;
        case '9':
			mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
    		PacketDispatcher.wrapper.sendToServer(new AuxButtonPacket(control.xCoord, control.yCoord, control.zCoord, 90, 0)); break;
        }
        
    }
    
    public void handleMouseInput()
    {
        super.handleMouseInput();
        int i = Mouse.getEventDWheel();

        if (i > 0)
        {
            i = 1;
        }

        if (i < 0)
        {
            i = -1;
        }
        
        int rods = control.rods + i;
        
        if(rods < 0)
        	rods = 0;
        
        if(rods > 100)
        	rods = 100;
        
		PacketDispatcher.wrapper.sendToServer(new AuxButtonPacket(control.xCoord, control.yCoord, control.zCoord, rods, 0));
    }
}
