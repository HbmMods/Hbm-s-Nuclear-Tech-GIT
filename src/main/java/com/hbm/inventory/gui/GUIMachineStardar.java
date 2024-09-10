package com.hbm.inventory.gui;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import com.hbm.config.SpaceConfig;
import com.hbm.dim.CelestialBody;
import com.hbm.dim.SolarSystem;
import com.hbm.inventory.container.ContainerStardar;
import com.hbm.items.ItemVOTVdrive;
import com.hbm.items.ModItems;
import com.hbm.lib.RefStrings;
import com.hbm.packet.toserver.NBTControlPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.tileentity.machine.TileEntityMachineStardar;
import com.hbm.util.I18nUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.lwjgl.input.Keyboard;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
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
	private int sX, sY; // starting mouse position (for verifying clicks)
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
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);
        
		this.drawCustomInfoStat(mouseX, mouseY, guiLeft + 129, guiTop + 143, 18, 18, mouseX, mouseY, new String[] {"Program new orbital station into drive"} );
		this.drawCustomInfoStat(mouseX, mouseY, guiLeft + 149, guiTop + 143, 18, 18, mouseX, mouseY, new String[] {"Program current body into drive"} );
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
		
		if(star.heightmap == null) {
			Minecraft.getMinecraft().getTextureManager().bindTexture(nightTexture);
			drawTexturedModalRect(guiLeft, guiTop, (int) starX * -1, (int) starY * -1, 256, 256);

			Minecraft.getMinecraft().getTextureManager().bindTexture(texture);

			for(POI peepee : pList) {
				int px = (int) (guiLeft + starX + peepee.offsetX);
				int py = (int) (guiTop + starY + peepee.offsetY);

				drawTexturedModalRect(px, py, xSize + peepee.getBody().processingLevel * 8, 0, 8, 8);
			}
		} else {
			if(star.updateHeightmap) {
				for(int i = 0; i < star.heightmap.length; i++) {
					int h = star.heightmap[i] % 16 * 16;

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
			func_146110_a(guiLeft, guiTop, (int) starX * -1 - 256 - 9, (int) starY * -1 - 256 - 9, 256, 256, 512, 512);
		}

		popScissor();
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mx, int my) {
		ItemStack slotStack = inventorySlots.getSlot(0).getStack();

		if(checkClick(mx, my, 9, 9, 158, 108)) {
			if(star.heightmap == null) {
				for(POI poi : pList) {
					int px = (int) (starX + poi.offsetX);
					int py = (int) (starY + poi.offsetY);
		
					// Has a small buffer area around the POI to improve click targeting
					drawCustomInfoStat(mx - guiLeft, my - guiTop, px - 2, py - 2, 12, 12, px + 8, py + 10, I18nUtil.resolveKey("body." + poi.body.name), "Processing Tier: " + poi.body.processingLevel);
				}
			} else {
				pushScissor(9, 9, 158, 108);

				Minecraft.getMinecraft().getTextureManager().bindTexture(texture);

				// translate from mouse coordinate to heightmap coordinate
				int hx = (mx - (int)starX - guiLeft - 9 + 256) / 2;
				int hz = (my - (int)starY - guiTop - 9 + 256) / 2;

				String info = landingInfo(hx, hz);
				int altitude = altitude(hx, hz);
				boolean canLand = info == null;
				
				int sx = mx - ((mx + (int)starX) % 2);
				int sy = my - ((my + (int)starY) % 2);
				drawTexturedModalRect(sx - 6 - guiLeft, sy - 6 - guiTop, xSize + (canLand ? 14 : 0), 28, 14, 14);

				popScissor();

				fontRendererObj.drawString(canLand ? "Valid location" : info, 10, 128, canLand ? 0x00FF00 : 0xFF0000);
				if(altitude > 0) fontRendererObj.drawString("Target altitude: " + altitude, 10, 148, 0x00FF00);
			}
		} else if(star.heightmap != null) {
			fontRendererObj.drawString("Select landing zone", 10, 128, 0x00FF00);
		}

		if(star.heightmap == null) {
			if(slotStack == null) {
				fontRendererObj.drawString("Insert drive", 10, 128, 0x00FF00);
			} else {
				if(slotStack.getItem() == ModItems.full_drive) {
					if(ItemVOTVdrive.getDestination(slotStack).body == SolarSystem.Body.ORBIT) {
						fontRendererObj.drawString("Orbital station ready", 10, 128, 0x00FF00);
					} else {
						fontRendererObj.drawString("Loading heightmap...", 10, 128, 0x00FF00);
						fontRendererObj.drawString("Please wait", 10, 148, 0x00FF00);
					}
				} else if(slotStack.getItem() == ModItems.hard_drive) {
					fontRendererObj.drawString("Select body", 10, 128, 0x00FF00);
					fontRendererObj.drawString("Drag map to pan", 10, 148, 0x00FF00);
				}
			}
		}
	}
	
	private String landingInfo(int x, int z) {
		if(star.heightmap == null) return "No heightmap";
		if(x < 3 || x > 252 || z < 3 || z > 252) return "Outside bounds";

		for(int ox = x - 2; ox <= x + 2; ox++) {
			for(int oz = z - 2; oz <= z + 2; oz++) {
				if(star.heightmap[256 * oz + ox] != star.heightmap[256 * z + x]) {
					return "Area not flat";
				}
			}
		}

		return null;
	}

	private int altitude(int x, int z) {
		if(star.heightmap == null) return -1;
		if(x < 0 || x > 255 || z < 0 || z > 255) return -1;

		return star.heightmap[256 * z + x];
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

			sX = mX = lX = x;
			sY = mY = lY = y;
		} else {
			dragging = false;
		}

		// Clicking ORB will generate a new orbital station
		if(checkClick(x, y, 129, 143, 18, 18)) {
			mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));

			NBTTagCompound data = new NBTTagCompound();
			data.setInteger("pid", SpaceConfig.orbitDimension);

			PacketDispatcher.wrapper.sendToServer(new NBTControlPacket(data, star.xCoord, star.yCoord, star.zCoord));
		}

		// Clicking SLF will load in the current body
		if(checkClick(x, y, 149, 143, 18, 18)) {
			mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));

			NBTTagCompound data = new NBTTagCompound();
			data.setInteger("pid", star.getWorldObj().provider.dimensionId);
			data.setInteger("ix", star.xCoord);
			data.setInteger("iz", star.zCoord);

			PacketDispatcher.wrapper.sendToServer(new NBTControlPacket(data, star.xCoord, star.yCoord, star.zCoord));
		}
	}

	// which==-1 is mouseMove, which==0 or which==1 is mouseUp
	@Override
	protected void mouseMovedOrUp(int x, int y, int which) {
		super.mouseMovedOrUp(x, y, which);
		if(which == -1 || which == 1) return;

		if(Math.abs(sX - x) > 2 || Math.abs(sY - y) > 2) return;

		if(checkClick(x, y, 9, 9, 158, 108)) {
			if(star.heightmap == null) {
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
				// translate from mouse coordinate to heightmap coordinate
				int hx = (x - (int)starX - guiLeft - 9 + 256) / 2;
				int hz = (y - (int)starY - guiTop - 9 + 256) / 2;

				// check landing zone is valid
				boolean canLand = landingInfo(hx, hz) == null;
				if(canLand) {
					mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
					
					NBTTagCompound data = new NBTTagCompound();
					data.setInteger("px", hx);
					data.setInteger("pz", hz);

					PacketDispatcher.wrapper.sendToServer(new NBTControlPacket(data, star.xCoord, star.yCoord, star.zCoord));
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
