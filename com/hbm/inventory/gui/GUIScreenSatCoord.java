package com.hbm.inventory.gui;

import java.util.Arrays;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import com.hbm.inventory.gui.GUIRadioRec.RadioButton;
import com.hbm.items.tool.ItemSatInterface;
import com.hbm.lib.RefStrings;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.SatLaserPacket;
import com.hbm.saveddata.satellites.Satellite.CoordActions;
import com.hbm.saveddata.satellites.Satellite.InterfaceActions;
import com.hbm.saveddata.satellites.Satellite.Interfaces;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public class GUIScreenSatCoord extends GuiScreen {
	
    protected static final ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/satellites/gui_sat_coord.png");
    protected int xSize = 176;
    protected int ySize = 126;
    protected int guiLeft;
    protected int guiTop;
    private final EntityPlayer player;
    int x;
    int z;

    private GuiTextField xField;
    private GuiTextField yField;
    private GuiTextField zField;
    
    public GUIScreenSatCoord(EntityPlayer player) {
    	
    	this.player = player;
    }
    
    public void initGui()
    {
        super.initGui();
        this.guiLeft = (this.width - this.xSize) / 2;
        this.guiTop = (this.height - this.ySize) / 2;

        Keyboard.enableRepeatEvents(true);
        this.xField = new GuiTextField(this.fontRendererObj, guiLeft + 66, guiTop + 21, 48, 12);
        this.xField.setTextColor(-1);
        this.xField.setDisabledTextColour(-1);
        this.xField.setEnableBackgroundDrawing(false);
        this.xField.setMaxStringLength(5);
        this.yField = new GuiTextField(this.fontRendererObj, guiLeft + 66, guiTop + 56, 48, 12);
        this.yField.setTextColor(-1);
        this.yField.setDisabledTextColour(-1);
        this.yField.setEnableBackgroundDrawing(false);
        this.yField.setMaxStringLength(5);
        this.zField = new GuiTextField(this.fontRendererObj, guiLeft + 66, guiTop + 92, 48, 12);
        this.zField.setTextColor(-1);
        this.zField.setDisabledTextColour(-1);
        this.zField.setEnableBackgroundDrawing(false);
        this.zField.setMaxStringLength(5);
    }
    
    protected void mouseClicked(int i, int j, int k)
    {
        super.mouseClicked(i, j, k);
        this.xField.mouseClicked(i, j, k);
        if(ItemSatInterface.currentSat.coordAcs.contains(CoordActions.HAS_Y)) this.yField.mouseClicked(i, j, k);
        this.zField.mouseClicked(i, j, k);

    	if(i >= this.guiLeft + 133 && i < this.guiLeft + 133 + 18 && j >= this.guiTop + 52 && j < this.guiTop + 52 + 18 && player != null) {
    		
    		mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("hbm:item.techBleep"), 1.0F));
    		
    		PacketDispatcher.wrapper.sendToServer(new SatLaserPacket(x, z, ItemSatInterface.getFreq(player.getHeldItem())));
    	}
    }
    
    public void drawScreen(int mouseX, int mouseY, float f)
    {
        this.drawDefaultBackground();
        this.drawGuiContainerBackgroundLayer(f, mouseX, mouseY);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_LIGHTING);
        this.drawGuiContainerForegroundLayer(mouseX, mouseY);
        GL11.glEnable(GL11.GL_LIGHTING);
    }
	
	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}
	
	protected void drawGuiContainerForegroundLayer(int i, int j) {

        this.xField.drawTextBox();
        if(ItemSatInterface.currentSat.coordAcs.contains(CoordActions.HAS_Y)) this.yField.drawTextBox();
        this.zField.drawTextBox();
	}

	protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		
		if(ItemSatInterface.currentSat != null) {
			
			drawTexturedModalRect(guiLeft + 120, guiTop + 17, 194, 0, 7, 7);
			
			if(ItemSatInterface.currentSat.satIface == Interfaces.SAT_COORD) {
				
				drawTexturedModalRect(guiLeft + 120, guiTop + 25, 194, 0, 7, 7);
			}
		}
	}
	
    protected void keyTyped(char p_73869_1_, int p_73869_2_) {
    	

        if (this.xField.textboxKeyTyped(p_73869_1_, p_73869_2_)) {
        } else if (ItemSatInterface.currentSat.coordAcs.contains(CoordActions.HAS_Y) && this.yField.textboxKeyTyped(p_73869_1_, p_73869_2_)) {
        } else if (this.zField.textboxKeyTyped(p_73869_1_, p_73869_2_)) {
        } else {
        	
            super.keyTyped(p_73869_1_, p_73869_2_);
        }
    	
        if (p_73869_2_ == 1 || p_73869_2_ == this.mc.gameSettings.keyBindInventory.getKeyCode())
        {
            this.mc.thePlayer.closeScreen();
        }
        
    }

}
