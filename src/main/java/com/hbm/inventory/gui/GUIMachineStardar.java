package com.hbm.inventory.gui;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import com.hbm.dim.SkyProviderCelestial;
import com.hbm.dim.SolarSystem;
import com.hbm.interfaces.Spaghetti;
import com.hbm.inventory.container.ContainerStardar;
import com.hbm.lib.RefStrings;
import com.hbm.main.ResourceManager;
import com.hbm.packet.NBTControlPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.tileentity.machine.TileEntityElectrolyser;
import com.hbm.tileentity.machine.TileEntityMachineStardar;
import com.hbm.util.AstronomyUtil;
import com.hbm.util.I18nUtil;

import java.util.Arrays;

import org.lwjgl.input.Keyboard;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;



//dear james:
/*
 among us is a GREAT GAME :DDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD
 */

public class GUIMachineStardar extends GuiInfoContainer {
    public static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/machine/gui_stardar.png");
    private static final ResourceLocation nightTexture = new ResourceLocation(RefStrings.MODID, "textures/misc/space/night.png");
    protected boolean clickorus;
    private TileEntityMachineStardar star;
    private int mX, mY;
    private int sX, sY;
    private int imgoingtojumpoffabuildingbutwithloveandroses, nmass2;
    private float additive = 0, additivey = 0;
    private float velocityX = 0, velocityY = 0;

    public GUIMachineStardar(InventoryPlayer iplayer, TileEntityMachineStardar restard) {
        super(new ContainerStardar(iplayer, restard));
        this.star = restard;

        this.xSize = 210;
        this.ySize = 256;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int x, int y) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

        pushScissor(9, 10, 137, 108);
        Minecraft.getMinecraft().getTextureManager().bindTexture(nightTexture);

        if (!Mouse.isButtonDown(0)) {
            velocityX *= 0.85;
            velocityY *= 0.85;
            additive += velocityX;
            additivey += velocityY;
        }
        drawTexturedModalRect(guiLeft, guiTop , (int)additive * -1, (int)additivey * -1, xSize, ySize);


        if (Keyboard.isKeyDown(Keyboard.KEY_UP)) {
        	additivey++;
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
        	additivey--;
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
        	additive++;
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
        	additive--;
        }

        popScissor();
        Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		
        int offsetx = 20;
        int offsety = 70;

		int fx = (int) (guiLeft + additive + offsetx);
		int fy = (int) (guiTop + additivey + offsety);
		if(fx < 275 && fx > 143 && fy > 6 && fy < 109) {
	        drawTexturedModalRect(guiLeft +(int)additive + offsetx, guiTop + (int)additivey + offsety ,  157, 0, 6, 7);
		}
	       System.out.println(fx);
		if(checkClick(x, y, (int) (additive + offsetx), guiTop + fy, 8,8)) {
			System.out.println("ci");
			this.func_146283_a(Arrays.asList(I18nUtil.resolveKeyArray("POI")), x, y);
		} 
		//TODO: so this is a test to see if the system well... systems. but anyway im thinking that you can see arrows that point to a planet to look at and then you can "ping"
// drawTexturedModalRect(guiLeft +(int)additive + 20, guiTop + (int)additivey + 20 ,  157, 0, 6, 7);

		

    }
	protected boolean checkClick(int x, int y, int left, int top, int sizeX, int sizeY) {
		return guiLeft + left <= x && guiLeft + left + sizeX > x && guiTop + top < y && guiTop + top + sizeY >= y;
	}
    @Override
    public void handleMouseInput() {
        super.handleMouseInput();

        int button = Mouse.getEventButton();

        if (button == 0 && !Mouse.getEventButtonState()) {
            velocityX = (mX - sX) * 0.08f;
            velocityY = (mY - sY) * 0.08f;
        }

        if (button == 0 && Mouse.getEventButtonState()) {
            sX = Mouse.getEventX() * this.width / this.mc.displayWidth;
            sY = this.height - Mouse.getEventY() * this.height / this.mc.displayHeight - 1;
        }
    }

    @Override
    protected void mouseClickMove(int x, int y, int p_146273_3_, long p_146273_4_) {
        super.mouseClickMove(x, y, p_146273_3_, p_146273_4_);
        int deltaX = x - mX;
        int deltaY = y - mY;
        additive += deltaX;
        additivey += deltaY;
        mX = x;
        mY = y;
        System.out.println("mx: " + x + " my: " + y);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float f) {
        super.drawScreen(mouseX, mouseY, f);
    }

    @Override
    protected void mouseClicked(int x, int y, int i) {
        super.mouseClicked(x, y, i);
        sX = x;
        sY = y;
        mX = x;
        mY = y;
    }
}