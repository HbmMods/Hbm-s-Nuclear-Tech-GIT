package com.hbm.inventory.gui;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import com.hbm.dim.CelestialBody;
import com.hbm.inventory.container.ContainerStardar;
import com.hbm.lib.RefStrings;
import com.hbm.packet.NBTControlPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.tileentity.machine.TileEntityMachineStardar;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.lwjgl.input.Keyboard;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

// Dearest Jam:
/**
 * Ooh ee ooh ah ah ting tang walla walla bing bang
 * Ooh ee ooh ah ah ting tang walla walla bang bang
 * Ooh ee ooh ah ah ting tang walla walla bing bang
 * Ooh ee ooh ah ah ting tang walla walla bang bang
 * Doh, doh, doh, doh, doh, doh, doh
 * Ooh ee ooh ah ah ting tang walla walla bing bang
 * Ooh ee ooh ah ah ting tang walla walla bang bang
 * Ooh ee ooh ah ah ting tang walla walla bing bang
 * Ooh ee ooh ah ah ting ting walla walla bang bang
 */

public class GUIMachineStardar extends GuiInfoContainer {

	public static ResourceLocation texture = new ResourceLocation( RefStrings.MODID + ":textures/gui/machine/gui_stardar.png");
	private static final ResourceLocation nightTexture = new ResourceLocation(RefStrings.MODID, "textures/misc/space/night.png");
	protected boolean clickorus;
	private TileEntityMachineStardar star;

	private int mX, mY; // current mouse position
	private int lX, lY; // mouse position last frame (for flinging)

	private float additive = 0, additivey = 0;
	private float velocityX = 0, velocityY = 0;
	private List<POI> pList = new ArrayList<>();
	Random rnd = new Random();

	private final DynamicTexture groundTexture;
	private final ResourceLocation groundMap;
	private final int[] groundColors;

	public void init() {
		for(CelestialBody rody : CelestialBody.getLandableBodies()) {
			CelestialBody body = CelestialBody.getBody(star.getWorldObj());
			if(rody != body) {
				int posX = rnd.nextInt(256);
				int posY = rnd.nextInt(256);
				pList.add(new POI(posX, posY, rody));
			}
		}
	}

	public GUIMachineStardar(InventoryPlayer iplayer, TileEntityMachineStardar restard) {
		super(new ContainerStardar(iplayer, restard));
		this.star = restard;

		this.xSize = 210;
		this.ySize = 256;
		init();

		groundTexture = new DynamicTexture(256, 256);
		groundMap = Minecraft.getMinecraft().getTextureManager().getDynamicTextureLocation("groundMap", groundTexture);
		groundColors = groundTexture.getTextureData();
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float interp, int x, int y) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

		pushScissor(9, 10, 137, 108);
		Minecraft.getMinecraft().getTextureManager().bindTexture(nightTexture);
		if(!Mouse.isButtonDown(0)) {
			velocityX *= 0.85;
			velocityY *= 0.85;
			additive += velocityX;
			additivey += velocityY;

		}
		drawTexturedModalRect(guiLeft, guiTop, (int) additive * -1, (int) additivey * -1, xSize, ySize);

		if(Keyboard.isKeyDown(Keyboard.KEY_UP)) {
			additivey++;
		}

		if(Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
			additivey--;
		}

		if(Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
			additive++;
		}

		if(Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
			additive--;
		}

		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);

		for(POI peepee : pList) {
			int px = (int) (guiLeft + additive + peepee.offsetX);
			int py = (int) (guiTop + additivey + peepee.offsetY);

			switch(peepee.getBody().processingLevel) {
			case 1:
				drawTexturedModalRect(px, py, 191, 0, 6, 7);
				break;
			case 2:
				drawTexturedModalRect(px, py, 164, 0, 8, 8);
				break;
			case 3:
				drawTexturedModalRect(px, py, 173, 0, 8, 8);
				break;
			case 4:
				drawTexturedModalRect(px, py, 182, 0, 8, 8);
				break;
			default:
				drawTexturedModalRect(px, py, 157, 0, 6, 7);
				break;
			}
		}

		if(star.heightmap != null) {
			for(int i = 0; i < star.heightmap.length; i++) {
				int h = Math.min(star.heightmap[i], 127) * 2;
				int r = 0;
				int g = h;
				int b = 0;
				int a = 255;

				groundColors[i] = a << 24 | r << 16 | g << 8 | b;
			}

			groundTexture.updateDynamicTexture();

			mc.getTextureManager().bindTexture(groundMap);
			drawTexturedModalRect(guiLeft, guiTop, (int) additive * -1, (int) additivey * -1, 256, 256);
		}

		popScissor();
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mx, int my) {
		if(checkClick(mx, my, 9, 10, 137, 108)) {
			for(POI poi : pList) {
				int px = (int) (additive + poi.offsetX);
				int py = (int) (additivey + poi.offsetY);
	
				// Has a small buffer area around the POI to improve click targeting
				drawCustomInfoStat(mx - guiLeft, my - guiTop, px - 2, py - 2, 12, 12, px + 8, py + 10, poi.body.name, "Processing Tier: " + poi.body.processingLevel);
			}
		}
	}

	@Override
	public void handleMouseInput() {
		super.handleMouseInput();

		int button = Mouse.getEventButton();
		if(additive > 400) {
			velocityX = 0;
			velocityY = 0;
			additive = 400;
		}

		if(additive < -400) {
			velocityX = 0;
			velocityY = 0;
			additive = -400;
		}

		if(additivey < -400) {
			velocityX = 0;
			velocityY = 0;
			additivey = -400;
		}

		if(additivey > 400) {
			velocityX = 0;
			velocityY = 0;
			additivey = 400;
		}

		if(button == 0 && !Mouse.getEventButtonState()) {
			velocityX = (mX - lX) * 0.8f;
			velocityY = (mY - lY) * 0.8f;
		}
	}

	@Override
	protected void mouseClickMove(int x, int y, int p_146273_3_, long p_146273_4_) {
		super.mouseClickMove(x, y, p_146273_3_, p_146273_4_);
		int deltaX = x - mX;
		int deltaY = y - mY;
		additive += deltaX;
		additivey += deltaY;
		lX = mX;
		lY = mY;
		mX = x;
		mY = y;
	}

	@Override
	protected void mouseClicked(int x, int y, int i) {
		super.mouseClicked(x, y, i);
		mX = lX = x;
		mY = lY = y;

		if(checkClick(x, y, 9, 10, 137, 108)) {
			for(POI poi : pList) {
				int poiX = (int) (additive + poi.offsetX);
				int poiY = (int) (additivey + poi.offsetY);

				// Again, a small buffer area around
				if(checkClick(x, y, poiX - 2, poiY - 2, 12, 12)) {
					mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));

					NBTTagCompound data = new NBTTagCompound();
					data.setInteger("pid", poi.getBody().dimensionId);

					PacketDispatcher.wrapper.sendToServer(new NBTControlPacket(data, star.xCoord, star.yCoord, star.zCoord));
					break;
				}
			}
		}
	}

	public static class POI {

		int offsetX;
		int offsetY;

		CelestialBody body;

		public POI(int offsetx, int offsety, CelestialBody dbody) {
			offsetX = offsetx;
			offsetY = offsety;

			body = dbody;
		}

		public CelestialBody getBody() {
			return body;
		}

	}

}
