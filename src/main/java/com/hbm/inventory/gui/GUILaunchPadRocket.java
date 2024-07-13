package com.hbm.inventory.gui;

import java.util.List;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerLaunchPadRocket;
import com.hbm.lib.RefStrings;
import com.hbm.packet.NBTControlPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.render.util.MissilePronter;
import com.hbm.tileentity.bomb.TileEntityLaunchPadRocket;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

public class GUILaunchPadRocket extends GuiInfoContainer {

    private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/machine/gui_launchpad_rocket.png");
    private TileEntityLaunchPadRocket machine;

    public GUILaunchPadRocket(InventoryPlayer invPlayer, TileEntityLaunchPadRocket machine) {
        super(new ContainerLaunchPadRocket(invPlayer, machine));
        this.machine = machine;

        xSize = 213;
        ySize = 224;
    }
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);
		
		this.drawElectricityInfo(this, mouseX, mouseY, guiLeft + 189, guiTop + 27, 16, 52, machine.power, machine.maxPower);
	}

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

        if(machine.rocket != null) {
            GL11.glPushMatrix();
            {
    
                pushScissor(50, 7, 43, 101);
    
                GL11.glTranslatef(guiLeft + 71, guiTop + 101, 100);
                GL11.glRotatef(System.currentTimeMillis() / 10 % 360, 0, -1, 0);
                
                double size = 86;
                double height = machine.rocket.getHeight();
                double targetScale = size / Math.max(height, 6);
                
                GL11.glScaled(-targetScale, -targetScale, -targetScale);
    
                MissilePronter.prontRocket(machine.rocket, Minecraft.getMinecraft().getTextureManager());
    
                popScissor();
    
            }
            GL11.glPopMatrix();

            GL11.glPushMatrix();
            {

                GL11.glScalef(0.5F, 0.5F, 0.5F);

                List<String> issues = machine.findIssues();
                for(int i = 0; i < issues.size(); i++) {
                    String issue = issues.get(i);
                    fontRendererObj.drawString(issue, (guiLeft + 97) * 2, (guiTop + 10) * 2 + i * 8, 0xFFFFFF);
                }

            }
            GL11.glPopMatrix();
        }
    }

	@Override
	protected void mouseClicked(int x, int y, int i) {
    	super.mouseClicked(x, y, i);
		
		// COMMIT TO LAUNCH
    	if(machine.rocket != null && machine.rocket.validate() && checkClick(x, y, 29, 5, 16, 14)) {
			mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
			NBTTagCompound data = new NBTTagCompound();
			data.setBoolean("launch", true);
			PacketDispatcher.wrapper.sendToServer(new NBTControlPacket(data, machine.xCoord, machine.yCoord, machine.zCoord));
    	}
    }
    
}
