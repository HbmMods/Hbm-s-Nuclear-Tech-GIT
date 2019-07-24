package com.hbm.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.FluidTank;
import com.hbm.inventory.container.ContainerSoyuzLauncher;
import com.hbm.items.weapon.ItemCustomMissile;
import com.hbm.items.weapon.ItemMissile.PartSize;
import com.hbm.lib.RefStrings;
import com.hbm.packet.AuxButtonPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.render.misc.MissileMultipart;
import com.hbm.render.misc.MissilePronter;
import com.hbm.tileentity.machine.TileEntitySoyuzLauncher;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class GUISoyuzLauncher extends GuiInfoContainer {

	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/gui_soyuz.png");
	private TileEntitySoyuzLauncher launcher;
	
	public GUISoyuzLauncher(InventoryPlayer invPlayer, TileEntitySoyuzLauncher tedf) {
		super(new ContainerSoyuzLauncher(invPlayer, tedf));
		launcher = tedf;
		
		this.xSize = 176;
		this.ySize = 222;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);

		/*launcher.tanks[0].renderTankInfo(this, mouseX, mouseY, guiLeft + 116, guiTop + 88 - 52, 16, 52);
		launcher.tanks[1].renderTankInfo(this, mouseX, mouseY, guiLeft + 134, guiTop + 88 - 52, 16, 52);
		this.drawCustomInfo(this, mouseX, mouseY, guiLeft + 152, guiTop + 88 - 52, 16, 52, new String[] { "Solid Fuel: " + launcher.solid + "l" });
		this.drawElectricityInfo(this, mouseX, mouseY, guiLeft + 134, guiTop + 113, 34, 6, launcher.power, launcher.maxPower);
		this.drawCustomInfo(this, mouseX, mouseY, guiLeft + 7, guiTop + 98, 18, 18, new String[] { "Size 10 & 10/15" });
		this.drawCustomInfo(this, mouseX, mouseY, guiLeft + 25, guiTop + 98, 18, 18, new String[] { "Size 15 & 15/20" });
		this.drawCustomInfo(this, mouseX, mouseY, guiLeft + 43, guiTop + 98, 18, 18, new String[] { "Size 20" });

		String[] text = new String[] { "Acepts custom missiles", "of all sizes, as long as the", "correct size setting is selected." };
		this.drawCustomInfoStat(mouseX, mouseY, guiLeft - 16, guiTop + 36, 16, 16, guiLeft - 8, guiTop + 36 + 16, text);
		
		String[] text1 = new String[] { "Detonator can only trigger center block." };
		this.drawCustomInfoStat(mouseX, mouseY, guiLeft - 16, guiTop + 36 + 16, 16, 16, guiLeft - 8, guiTop + 36 + 16, text1);*/
	}

	protected void mouseClicked(int x, int y, int i) {
    	super.mouseClicked(x, y, i);
		
    	/*if(guiLeft + 7 <= x && guiLeft + 7 + 18 > x && guiTop + 98 < y && guiTop + 98 + 18 >= y) {
    		
			mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
    		PacketDispatcher.wrapper.sendToServer(new AuxButtonPacket(launcher.xCoord, launcher.yCoord, launcher.zCoord, PartSize.SIZE_10.ordinal(), 0));
    	}
		
    	if(guiLeft + 25 <= x && guiLeft + 25 + 18 > x && guiTop + 98 < y && guiTop + 98 + 18 >= y) {
    		
			mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
    		PacketDispatcher.wrapper.sendToServer(new AuxButtonPacket(launcher.xCoord, launcher.yCoord, launcher.zCoord, PartSize.SIZE_15.ordinal(), 0));
    	}
		
    	if(guiLeft + 43 <= x && guiLeft + 43 + 18 > x && guiTop + 98 < y && guiTop + 98 + 18 >= y) {
    		
			mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
    		PacketDispatcher.wrapper.sendToServer(new AuxButtonPacket(launcher.xCoord, launcher.yCoord, launcher.zCoord, PartSize.SIZE_20.ordinal(), 0));
    	}*/
    }

	@Override
	protected void drawGuiContainerForegroundLayer( int i, int j) {
		String name = this.launcher.hasCustomInventoryName() ? this.launcher.getInventoryName() : I18n.format(this.launcher.getInventoryName());
		
		this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
	}
}
