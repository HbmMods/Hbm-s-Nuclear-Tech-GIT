package com.hbm.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerMachineAssembler;
import com.hbm.inventory.container.ContainerMachineMissileAssembly;
import com.hbm.items.ModItems;
import com.hbm.lib.RefStrings;
import com.hbm.main.ResourceManager;
import com.hbm.render.misc.MissileMultipart;
import com.hbm.render.misc.MissilePart;
import com.hbm.render.misc.MissilePronter;
import com.hbm.tileentity.machine.TileEntityMachineAssembler;
import com.hbm.tileentity.machine.TileEntityMachineMissileAssembly;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUIMachineMissileAssembly extends GuiInfoContainer {

	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/gui_missile_assembly.png");
	private TileEntityMachineMissileAssembly assembler;
	
	public GUIMachineMissileAssembly(InventoryPlayer invPlayer, TileEntityMachineMissileAssembly tedf) {
		super(new ContainerMachineMissileAssembly(invPlayer, tedf));
		assembler = tedf;
		
		this.xSize = 176;
		this.ySize = 222;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);
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

		MissileMultipart missile = new MissileMultipart();
		
		if(assembler.getStackInSlot(1) != null)
			missile.warhead = MissilePart.getPart(assembler.getStackInSlot(1).getItem());
		
		if(assembler.getStackInSlot(2) != null)
			missile.fuselage = MissilePart.getPart(assembler.getStackInSlot(2).getItem());
		
		if(assembler.getStackInSlot(3) != null)
			missile.fins = MissilePart.getPart(assembler.getStackInSlot(3).getItem());
		
		if(assembler.getStackInSlot(4) != null)
			missile.thruster = MissilePart.getPart(assembler.getStackInSlot(4).getItem());
		
		GL11.glTranslatef(guiLeft + 88, guiTop + 98, 100);
		GL11.glRotatef(System.currentTimeMillis() / 10 % 360, 0, -1, 0);
		
		double size = 8 * 18;
		double scale = size / Math.max(missile.getHeight(), 6);
		
		GL11.glTranslated(missile.getHeight() / 2 * scale, 0, 0);
		GL11.glScaled(scale, scale, scale);
		
		GL11.glRotatef(90, 1, 0, 0);
		GL11.glRotatef(-90, 0, 0, 1);
		GL11.glScalef(-1, -1, -1);
		
		MissilePronter.prontMissile(missile, Minecraft.getMinecraft().getTextureManager());
		
		GL11.glPopMatrix();
		/// DRAW MISSILE END
	}
}
