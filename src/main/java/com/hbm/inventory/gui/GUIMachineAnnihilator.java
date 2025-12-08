package com.hbm.inventory.gui;

import java.util.Arrays;
import java.util.Locale;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerMachineAnnihilator;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.items.machine.IItemFluidIdentifier;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.machine.TileEntityMachineAnnihilator;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUIMachineAnnihilator extends GuiInfoContainer {

	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/processing/gui_annihilator.png");
	private TileEntityMachineAnnihilator annihilator;

	public GUIMachineAnnihilator(InventoryPlayer invPlayer, TileEntityMachineAnnihilator tedf) {
		super(new ContainerMachineAnnihilator(invPlayer, tedf));
		annihilator = tedf;

		this.xSize = 176;
		this.ySize = 208;
	}
	
	@Override
	public void drawScreen(int x, int y, float interp) {
		super.drawScreen(x, y, interp);
		
		if(annihilator.slots[8] != null && this.checkClick(x, y, 151, 35, 18, 18)) {
			String name = annihilator.slots[8].getDisplayName();
			if(annihilator.slots[8].getItem() instanceof IItemFluidIdentifier) {
				IItemFluidIdentifier id = (IItemFluidIdentifier) annihilator.slots[8].getItem();
				FluidType type = id.getType(null, 0, 0, 0, annihilator.slots[8]);
				name = type.getLocalizedName();
			}
			this.func_146283_a(Arrays.asList(new String[] { name + ":", String.format(Locale.US, "%,d", annihilator.monitorBigInt) }), x, y);
		}
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String name = this.annihilator.hasCustomInventoryName() ? this.annihilator.getInventoryName() : I18n.format(this.annihilator.getInventoryName());

		this.fontRendererObj.drawString(name, 70 - this.fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float interp, int x, int y) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
	}
}
