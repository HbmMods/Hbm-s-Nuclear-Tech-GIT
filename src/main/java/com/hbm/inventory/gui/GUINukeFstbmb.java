package com.hbm.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerNukeFstbmb;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.bomb.TileEntityNukeBalefire;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUINukeFstbmb extends GuiInfoContainer {

	public static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/weapon/fstbmbSchematic.png");
	private TileEntityNukeBalefire bomb;
	
	public GUINukeFstbmb(InventoryPlayer invPlayer, TileEntityNukeBalefire bomb) {
		super(new ContainerNukeFstbmb(invPlayer, bomb));
		this.bomb = bomb;

		this.xSize = 176;
		this.ySize = 222;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);
	}

	protected void mouseClicked(int x, int y, int i) {
    	super.mouseClicked(x, y, i);
		
    	if(guiLeft + 61 <= x && guiLeft + 61 + 18 > x && guiTop + 17 < y && guiTop + 17 + 18 >= y) {
    		
			mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
    		//PacketDispatcher.wrapper.sendToServer(new AuxButtonPacket(laser.xCoord, laser.yCoord, laser.zCoord, 0, 0));
    	}
    }
	
	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		
		String name = this.bomb.hasCustomInventoryName() ? this.bomb.getInventoryName() : I18n.format(this.bomb.getInventoryName());
		
		this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
		
		if(bomb.hasBattery()) {
			String timer = bomb.getMinutes() + ":" + bomb.getSeconds();
			double scale = 0.75;
			GL11.glScaled(scale, scale, scale);
			this.fontRendererObj.drawString(timer, (int) ((69 - this.fontRendererObj.getStringWidth(timer) / 2) * (1/scale)), (int) (95 * (1/scale)), 0xff0000);

			GL11.glScaled(1/scale, 1/scale, 1/scale);
		}
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		
		if(bomb.hasEgg())
			drawTexturedModalRect(guiLeft + 19, guiTop + 90, 176, 0, 30, 16);
		
		if(bomb.hasBattery())
			drawTexturedModalRect(guiLeft + 88, guiTop + 93, 176, 16, 18, 10);
	}
}
