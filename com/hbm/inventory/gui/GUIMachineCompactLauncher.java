package com.hbm.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerCompactLauncher;
import com.hbm.inventory.container.ContainerMachineMissileAssembly;
import com.hbm.items.weapon.ItemCustomMissile;
import com.hbm.items.weapon.ItemMissile;
import com.hbm.lib.RefStrings;
import com.hbm.packet.AuxButtonPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.render.misc.MissileMultipart;
import com.hbm.render.misc.MissilePart;
import com.hbm.render.misc.MissilePronter;
import com.hbm.tileentity.bomb.TileEntityCompactLauncher;
import com.hbm.tileentity.machine.TileEntityMachineMissileAssembly;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class GUIMachineCompactLauncher extends GuiInfoContainer {

	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/gui_launch_table_small.png");
	private TileEntityCompactLauncher assembler;
	
	public GUIMachineCompactLauncher(InventoryPlayer invPlayer, TileEntityCompactLauncher tedf) {
		super(new ContainerCompactLauncher(invPlayer, tedf));
		assembler = tedf;
		
		this.xSize = 176;
		this.ySize = 222;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);
	}

	protected void mouseClicked(int x, int y, int i) {
    	super.mouseClicked(x, y, i);
		
    	if(guiLeft + 115 <= x && guiLeft + 115 + 18 > x && guiTop + 35 < y && guiTop + 35 + 18 >= y) {
    		
			mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
    		PacketDispatcher.wrapper.sendToServer(new AuxButtonPacket(assembler.xCoord, assembler.yCoord, assembler.zCoord, 0, 0));
    	}
    }

	@Override
	protected void drawGuiContainerForegroundLayer( int i, int j) {
		String name = this.assembler.hasCustomInventoryName() ? this.assembler.getInventoryName() : I18n.format(this.assembler.getInventoryName());
		
		this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		
		/// DRAW MISSILE START
		GL11.glPushMatrix();

		MissileMultipart missile;
		
		if(assembler.getStackInSlot(0) != null && assembler.getStackInSlot(0).getItem() instanceof ItemCustomMissile) {
			ItemStack custom = assembler.getStackInSlot(0);
			
			missile = new MissileMultipart();
			
			missile = ItemCustomMissile.getMultipart(custom);
		
			GL11.glTranslatef(guiLeft + 42, guiTop + 115, 100);
			
			double size = 5 * 18;
			double scale = size / Math.max(missile.getHeight(), 6);
			
			GL11.glTranslated(missile.getHeight() / 2 * scale, 0, 0);
			GL11.glScaled(scale, scale, scale);

			GL11.glRotatef(90, 0, 1, 0);
			GL11.glScalef(-1, -1, -1);
			
			MissilePronter.prontMissile(missile, Minecraft.getMinecraft().getTextureManager());
		}
		
		GL11.glPopMatrix();
		/// DRAW MISSILE END
	}
}
