package com.hbm.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.FluidTank;
import com.hbm.inventory.container.ContainerReactorAmat;
import com.hbm.lib.Library;
import com.hbm.lib.RefStrings;
import com.hbm.packet.AuxButtonPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.tileentity.machine.TileEntityReactorAmat;
import com.hbm.util.I18nUtil;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUIReactorAmat extends GuiInfoContainer
{
	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID, "textures/gui/reactors/gui_reactor_amat.png");
	private TileEntityReactorAmat reactor;
	
	public GUIReactorAmat(InventoryPlayer invPlayer, TileEntityReactorAmat te)
	{
		super(new ContainerReactorAmat(invPlayer, te));
		reactor = te;
		xSize = 175;
		ySize = 226;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f)
	{
		super.drawScreen(mouseX, mouseY, f);
		String[] info = I18nUtil.resolveKeyArray("desc.gui.reactorAmat.box", reactor.getPlasmaBaseProduction(), reactor.getPlasmaProduction(), reactor.getFuelBaseConsumption(), reactor.getFuelConsumption(), reactor.getBoosterBuffer(), reactor.getCatalystBuffer(), reactor.isCoreValid() ? (reactor.getCoreLife() == -1 ? "∞" : reactor.getCoreLife()) : "N/A");
		drawCustomInfoStat(mouseX, mouseY, guiLeft + 27, guiTop + 15, 49, 35, mouseX, mouseY, info);
		String[] reactant = new String[] {"Reactant base plasma production: " + reactor.getPlasmaBaseProduction(), "Reactant remaining: " + reactor.getCatalystBuffer()};
		String[] booster = new String[] {"Post-Booster plasma production: " + reactor.getPlasmaProduction(), "Post-Booster fuel consumption: " + reactor.getFuelConsumption(), "Booster remaining: " + reactor.getBoosterBuffer()};
		drawCustomInfoStat(mouseX, mouseY, guiLeft + 62, guiTop + 87, 4, 18, mouseX, mouseY, booster);
		drawCustomInfoStat(mouseX, mouseY, guiLeft + 135, guiTop + 87, 4, 18, mouseX, mouseY, reactant);
		drawElectricityInfo(this, mouseX, mouseY, guiLeft + 62, guiTop + 103, 52, 16, reactor.power, reactor.getMaxPower());
		reactor.getTank(0).renderTankInfo(this, mouseX, mouseY, guiLeft + 8, guiTop + 16, 16, 70);
		reactor.getTank(1).renderTankInfo(this, mouseX, mouseY, guiLeft + 151, guiTop + 16, 16, 70);
		reactor.getTank(2).renderTankInfo(this, mouseX, mouseY, guiLeft + 80, guiTop + 16, 16, 70);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int p_146979_1_, int p_146979_2_)
	{
		String name = reactor.hasCustomInventoryName() ? reactor.getInventoryName() : I18n.format(reactor.getInventoryName());
		
		fontRendererObj.drawString(name, xSize / 2 - fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
		fontRendererObj.drawString(I18nUtil.resolveKey("container.inventory"), 8, ySize - 96 + 2, 4210752);
	}
	
	@Override
	protected void mouseClicked(int x, int y, int i)
	{
		super.mouseClicked(x, y, i);
//		boolean powerButton = guiLeft + 25 <= x && guiLeft + 25 + 18 > x && guiTop + 105 < y && guiTop + 105 + 18 >= y;
		boolean powerButton = Library.getButtonBool(x, y, 25, 108, 18, 18, guiLeft, guiTop);
		if (powerButton)
		{
			Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
			PacketDispatcher.wrapper.sendToServer(new AuxButtonPacket(reactor.xCoord, reactor.yCoord, reactor.zCoord, 0, 0));
		}
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_)
	{
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		
		if (reactor.isOn())
			drawTexturedModalRect(guiLeft + 25, guiTop + 105, 180, 0, 18, 18);
		if (reactor.hasPower(true))
			drawTexturedModalRect(guiLeft + 115, guiTop + 105, 176, 18, 18, 18);
		if (reactor.isCoreValid())
			drawTexturedModalRect(guiLeft + 79, guiTop + 87, 194, 18, 18, 18);
		int b = Library.getScaledBar(reactor.getBoosterBuffer(), 16, reactor.boosterMax);
		drawTexturedModalRect(guiLeft + 62, guiTop + 104 - b, 176, 17 - b, 3, b);
		int c = Library.getScaledBar(reactor.getCatalystBuffer(), 16, reactor.catalystMax);
		drawTexturedModalRect(guiLeft + 134, guiTop + 104 - c, 176, 17 - c, 3, c);
		long e = Library.getScaledBar(reactor.getPower(), 52, reactor.getMaxPower());
		drawTexturedModalRect(guiLeft + 62, guiTop + 106, 198, 0, (int) e, 16);
		
		FluidTank deutTank = reactor.tanks[0];
		Minecraft.getMinecraft().getTextureManager().bindTexture(deutTank.getSheet());
		deutTank.renderTank(this, guiLeft + 8, guiTop + 86, deutTank.getTankType().textureX() * FluidTank.x, deutTank.getTankType().textureY() * FluidTank.y, 16, 70);
		FluidTank amatTank = reactor.tanks[1];
		Minecraft.getMinecraft().getTextureManager().bindTexture(amatTank.getSheet());
		amatTank.renderTank(this, guiLeft + 151, guiTop + 86, amatTank.getTankType().textureX() * FluidTank.x, amatTank.getTankType().textureY() * FluidTank.y, 16, 70);
		FluidTank plasmaTank = reactor.tanks[2];
		Minecraft.getMinecraft().getTextureManager().bindTexture(plasmaTank.getSheet());
		plasmaTank.renderTank(this, guiLeft + 80, guiTop + 86, plasmaTank.getTankType().textureX() * FluidTank.x, plasmaTank.getTankType().textureY() * FluidTank.y, 16, 70);
	}
}
