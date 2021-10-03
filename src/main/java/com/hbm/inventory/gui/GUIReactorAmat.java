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
/**
 * Superseded by {@link#GUIReactorWarp}
 * @deprecated
 * @author UFFR
 *
 */
public class GUIReactorAmat extends GuiInfoContainer
{
	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID, "textures/gui/reactors/gui_reactor_amat.png");
	private TileEntityReactorAmat reactor;
	private static final int color1 = 0x00ff00;
	private NumberDisplay disp;
	private boolean advDisplayOpen = false;
	public GUIReactorAmat(InventoryPlayer invPlayer, TileEntityReactorAmat te)
	{
		super(new ContainerReactorAmat(invPlayer, te));
		reactor = te;
		xSize = 175;
		ySize = 226;
		disp = new NumberDisplay(29, 20, 218, 18).setMaxMin(100, 0);
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f)
	{
		super.drawScreen(mouseX, mouseY, f);
		String[] reactant = new String[] {"Reactant remaining: " + reactor.getCatalystBuffer() + "/" + reactor.catalystMax};
		String[] booster = new String[] {"Post-Booster plasma production: " + reactor.getPlasmaProduction(), "Post-Booster fuel consumption: " + reactor.getFuelConsumption(), "Booster remaining: " + reactor.getBoosterBuffer() + "/" + reactor.boosterMax};
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
		
		GL11.glScaled(0.5D, 0.5D, 1D);
		String[] info = I18nUtil.resolveKeyArray("desc.gui.reactorAmat.box", disp.getNumber(), reactor.getPlasmaProduction(), reactor.getFuelConsumption(), reactor.getBoosterBuffer(), reactor.getCatalystBuffer(), reactor.isCoreValid() ? (reactor.getCoreLife() == -1 ? "∞" : reactor.getCoreLife()) : "N/A");
		if (advDisplayOpen)
			for (int i = 0; i < info.length; i++)
				fontRendererObj.drawString(info[i], 80 * 2, (40 + (i * 5)) * 2, color1);
		GL11.glScaled(2D, 2D, 1D);
	}
	
	@Override
	protected void mouseClicked(int x, int y, int i)
	{
		super.mouseClicked(x, y, i);
		if (getButtonBool(x, y, 25, 108, 18, 18, guiLeft, guiTop))
			PacketDispatcher.wrapper.sendToServer(new AuxButtonPacket(reactor, 0, 0));
		if (getButtonBool(x, y, 100, 108, 18, 18, guiLeft, guiTop))
			PacketDispatcher.wrapper.sendToServer(new AuxButtonPacket(reactor, 1, 0));
		if (getButtonBool(x, y, 142, 86, 8, 19, guiLeft, guiTop))
			advDisplayOpen = !advDisplayOpen;
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_)
	{
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		
		if (reactor.isOn())
			drawTexturedModalRect(guiLeft + 25, guiTop + 105, 180, 0, 18, 18);
		if (reactor.hasPower(true))
			drawTexturedModalRect(guiLeft + 115, guiTop + 105, 176, 18, 18, 18);
		if (reactor.isCoreValid())
			drawTexturedModalRect(guiLeft + 79, guiTop + 87, 194, 18, 18, 18);
		int b = getScaledBar(reactor.getBoosterBuffer(), 16, reactor.boosterMax);
		drawTexturedModalRect(guiLeft + 62, guiTop + 104 - b, 176, 17 - b, 3, b);
		int c = getScaledBar(reactor.getCatalystBuffer(), 16, reactor.catalystMax);
		drawTexturedModalRect(guiLeft + 134, guiTop + 104 - c, 176, 17 - c, 3, c);
		long e = getScaledBar(reactor.getPower(), 52, reactor.getMaxPower());
		drawTexturedModalRect(guiLeft + 62, guiTop + 106, 198, 0, (int) e, 16);
		
		disp.setNumber(0);
		disp.drawNumber();
		
		FluidTank deutTank = reactor.tanks[0];
		mc.getTextureManager().bindTexture(deutTank.getSheet());
		deutTank.renderTank(this, guiLeft + 8, guiTop + 86, deutTank.getTankType().textureX() * FluidTank.x, deutTank.getTankType().textureY() * FluidTank.y, 16, 70);
		FluidTank amatTank = reactor.tanks[1];
		mc.getTextureManager().bindTexture(amatTank.getSheet());
		amatTank.renderTank(this, guiLeft + 151, guiTop + 86, amatTank.getTankType().textureX() * FluidTank.x, amatTank.getTankType().textureY() * FluidTank.y, 16, 70);
		FluidTank plasmaTank = reactor.tanks[2];
		mc.getTextureManager().bindTexture(plasmaTank.getSheet());
		plasmaTank.renderTank(this, guiLeft + 80, guiTop + 86, plasmaTank.getTankType().textureX() * FluidTank.x, plasmaTank.getTankType().textureY() * FluidTank.y, 16, 70);
		mc.getTextureManager().bindTexture(texture);
		if (advDisplayOpen)
		{
			drawTexturedModalRect(guiLeft + 142, guiLeft + 86, 217, 18, 8, 19);
			drawTexturedModalRect(guiLeft + 83, guiTop + 40, 175, 36, 67, 46);
		}
	}
}
