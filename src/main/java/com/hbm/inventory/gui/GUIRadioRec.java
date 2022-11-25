package com.hbm.inventory.gui;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.math.NumberUtils;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerRadioRec;
import com.hbm.lib.RefStrings;
import com.hbm.packet.AuxButtonPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.tileentity.machine.TileEntityRadioRec;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUIRadioRec extends GuiInfoContainer {

	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/gui_radiorec.png");
	private TileEntityRadioRec diFurnace;
	
    private GuiTextField freqField;
    
    List<RadioButton> buttons = new ArrayList<RadioButton>();
	

	public GUIRadioRec(InventoryPlayer invPlayer, TileEntityRadioRec tedf) {
		super(new ContainerRadioRec(invPlayer, tedf));
		diFurnace = tedf;

		this.xSize = 176;
		this.ySize = 54;
	}
	
	public void initGui() {

		super.initGui();

		buttons.clear();
		buttons.add(new RadioButton(guiLeft + 25, guiTop + 16, 0, "Save"));
		buttons.add(new RadioButton(guiLeft + 61, guiTop + 16, 1, "On/Off"));

        Keyboard.enableRepeatEvents(true);
        this.freqField = new GuiTextField(this.fontRendererObj, guiLeft + 100, guiTop + 21, 48, 12);
        this.freqField.setTextColor(-1);
        this.freqField.setDisabledTextColour(-1);
        this.freqField.setEnableBackgroundDrawing(false);
        this.freqField.setMaxStringLength(5);
        this.freqField.setText(String.valueOf(diFurnace.freq));
        
        if(diFurnace.freq == 0) {
        	double d = 100 + diFurnace.getWorldObj().rand.nextInt(900);
        	d += (diFurnace.getWorldObj().rand.nextInt(10) * 0.1D);
            this.freqField.setText(String.valueOf(d));
        }
        
        save();
	}

	@Override
	public void drawScreen(int i, int j, float f) {
		super.drawScreen(i, j, f);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_BLEND);
        this.freqField.drawTextBox();
		
		for(RadioButton b : buttons)
			if(b.isMouseOnButton(i, j))
				b.drawString(i, j);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String name = I18n.format("container.radiorec");
		this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);

		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

		if(freqField.isFocused())
			drawTexturedModalRect(guiLeft + 97, guiTop + 16, 0, 184 + 18, 54, 18);
		else
			drawTexturedModalRect(guiLeft + 97, guiTop + 16, 0, 184, 54, 18);
		
		for(RadioButton b : buttons)
			b.drawButton();
	}
	
    public void updateScreen() { }
	
    protected void keyTyped(char p_73869_1_, int p_73869_2_)
    {
        if (this.freqField.textboxKeyTyped(p_73869_1_, p_73869_2_)) { }
        else {
            super.keyTyped(p_73869_1_, p_73869_2_);
        }
    }
    
    protected void rectify() {
    	String s = freqField.getText();
    	
    	if(NumberUtils.isNumber(s)) {
    		double d = Double.parseDouble(s);
    		d = Math.max(100, Math.min(999.9, d));
    		d = truncateDecimal(d, 1).doubleValue();
    		s = String.valueOf(d);
    	} else {
    		s = "100.0";
    	}
    	
    	freqField.setText(s);
    }
    
    private BigDecimal truncateDecimal(double x, int numberofDecimals)
    {
        if (x > 0) {
            return new BigDecimal(String.valueOf(x)).setScale(numberofDecimals, BigDecimal.ROUND_FLOOR);
        } else {
            return new BigDecimal(String.valueOf(x)).setScale(numberofDecimals, BigDecimal.ROUND_CEILING);
        }
    }
    
    protected void mouseClicked(int i, int j, int k)
    {
        super.mouseClicked(i, j, k);
        this.freqField.mouseClicked(i, j, k);
        
		for(RadioButton b : buttons)
			if(b.isMouseOnButton(i, j))
				b.executeAction();
    }
    
    protected void toggle() {
    	
    	rectify();
    	
    	PacketDispatcher.wrapper.sendToServer(new AuxButtonPacket(diFurnace.xCoord, diFurnace.yCoord, diFurnace.zCoord, diFurnace.isOn ? 0 : 1, 0));
    }
    
    protected void save() {
    	
    	rectify();
    	
    	String s = freqField.getText();
    	double d = Double.parseDouble(s);
    	PacketDispatcher.wrapper.sendToServer(new AuxButtonPacket(diFurnace.xCoord, diFurnace.yCoord, diFurnace.zCoord, (int)(d * 10D), 1));
    }
    
    
    class RadioButton {
		
		int xPos;
		int yPos;
		int buttonType;
		String info;
		
		//0: save
		//1: type
		//2: music 1
		//3: music 2
		//4: music 3
		//5: music 4
		
		public RadioButton(int x, int y, int t, String i) {
			xPos = x;
			yPos = y;
			buttonType = t;
			info = i;
		}
		
		public boolean isMouseOnButton(int mouseX, int mouseY) {
			return xPos <= mouseX && xPos + 18 > mouseX && yPos < mouseY && yPos + 18 >= mouseY;
		}
		
		public void drawButton() {
			
			switch(buttonType) {
			case 0: 
				drawTexturedModalRect(xPos, yPos, 176 + 18 * 0, 18 * 0, 18, 18); break;
			case 1:
				drawTexturedModalRect(xPos, yPos, 176 + 18 * 1, 18 * 0, 18, 18); break;
			}
		}
		
		public void drawString(int x, int y) {
			if(info == null || info.isEmpty())
				return;
			
			String s = info;
			
			if(buttonType == 1) {
				if(diFurnace.isOn)
					s = "Turn Off";
				else
					s = "Turn On";
			}

			func_146283_a(Arrays.asList(new String[] { s }), x, y);
		}
		
		public void executeAction() {
			
			mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
			
			switch(buttonType) {
			case 0: save(); break;
			case 1: toggle(); break;
			}
		}
    }

	@Override
	public void onGuiClosed() {
		Keyboard.enableRepeatEvents(false);
	}
}
