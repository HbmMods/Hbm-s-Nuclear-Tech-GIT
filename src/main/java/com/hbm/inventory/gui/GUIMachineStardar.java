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
import net.minecraft.util.MathHelper;
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
	private TileEntityMachineStardar star;

	private int mX, mY; // current mouse position
	private int lX, lY; // mouse position last frame (for flinging)
	private boolean dragging = false;

	private float starX = 0, starY = 0;
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
				int posX = rnd.nextInt(400) - 200;
				int posY = rnd.nextInt(400) - 200;
				pList.add(new POI(posX, posY, rody));
			}
		}
	}

	public GUIMachineStardar(InventoryPlayer iplayer, TileEntityMachineStardar restard) {
		super(new ContainerStardar(iplayer, restard));
		this.star = restard;

		this.xSize = 176;
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

		pushScissor(9, 9, 158, 108);
		if(!Mouse.isButtonDown(0)) {
			velocityX *= 0.85;
			velocityY *= 0.85;
			starX += velocityX;
			starY += velocityY;
			starX = MathHelper.clamp_float(starX, -256 + 158, 256);
			starY = MathHelper.clamp_float(starY, -256 + 108, 256);
		}
		
		Minecraft.getMinecraft().getTextureManager().bindTexture(nightTexture);
		drawTexturedModalRect(guiLeft, guiTop, (int) -starX, (int) -starY, 256, 256);

		if(Keyboard.isKeyDown(Keyboard.KEY_UP)) {
			starY++;
		}

		if(Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
			starY--;
		}

		if(Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
			starX++;
		}

		if(Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
			starX--;
		}

		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);

		for(POI peepee : pList) {
			int px = (int) (guiLeft + starX + peepee.offsetX);
			int py = (int) (guiTop + starY + peepee.offsetY);

			drawTexturedModalRect(px, py, xSize + peepee.getBody().processingLevel * 8, 0, 8, 8);
		}

		if(star.heightmap != null) {
			if(star.updateHeightmap) {
				for(int i = 0; i < star.heightmap.length; i++) {
					int h = Math.min(star.heightmap[i], 127) * 2;
					int r = 0;
					int g = h;
					int b = 0;
					int a = 255;
	
					groundColors[i] = a << 24 | r << 16 | g << 8 | b;
				}
	
				groundTexture.updateDynamicTexture();

				star.updateHeightmap = false;
			}

			mc.getTextureManager().bindTexture(groundMap);
			func_146110_a(guiLeft, guiTop, (int) -starX - 256 - 9, (int) -starY - 256 - 9, 256, 256, 512, 512);
		}

		popScissor();
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mx, int my) {
		if(checkClick(mx, my, 9, 9, 158, 108)) {
			for(POI poi : pList) {
				int px = (int) (starX + poi.offsetX);
				int py = (int) (starY + poi.offsetY);
	
				// Has a small buffer area around the POI to improve click targeting
				drawCustomInfoStat(mx - guiLeft, my - guiTop, px - 2, py - 2, 12, 12, px + 8, py + 10, poi.body.name, "Processing Tier: " + poi.body.processingLevel);
			}
		}
	}

	@Override
	public void handleMouseInput() {
		super.handleMouseInput();

		int button = Mouse.getEventButton();
		if(starX > 256) {
			velocityX = 0;
			starX = 256;
		}

		if(starX < -256 + 158) {
			velocityX = 0;
			starX = -256 + 158;
		}

		if(starY < -256 + 108) {
			velocityY = 0;
			starY = -256 + 108;
		}

		if(starY > 256) {
			velocityY = 0;
			starY = 256;
		}

		if(dragging && button == 0 && !Mouse.getEventButtonState()) {
			velocityX = (mX - lX) * 0.8f;
			velocityY = (mY - lY) * 0.8f;
			dragging = false;
		}
	}

	@Override
	protected void mouseClickMove(int x, int y, int p_146273_3_, long p_146273_4_) {
		super.mouseClickMove(x, y, p_146273_3_, p_146273_4_);
		if(!dragging) return;

		int deltaX = x - mX;
		int deltaY = y - mY;
		starX += deltaX;
		starY += deltaY;
		lX = mX;
		lY = mY;
		mX = x;
		mY = y;
	}

	@Override
	protected void mouseClicked(int x, int y, int i) {
		super.mouseClicked(x, y, i);
		if(checkClick(x, y, 9, 9, 158, 108)) {
			dragging = true;

			mX = lX = x;
			mY = lY = y;

			for(POI poi : pList) {
				int poiX = (int) (starX + poi.offsetX);
				int poiY = (int) (starY + poi.offsetY);

				// Again, a small buffer area around
				if(checkClick(x, y, poiX - 2, poiY - 2, 12, 12)) {
					mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));

					NBTTagCompound data = new NBTTagCompound();
					data.setInteger("pid", poi.getBody().dimensionId);

					PacketDispatcher.wrapper.sendToServer(new NBTControlPacket(data, star.xCoord, star.yCoord, star.zCoord));
					break;
				}
			}
		} else {
			dragging = false;
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
