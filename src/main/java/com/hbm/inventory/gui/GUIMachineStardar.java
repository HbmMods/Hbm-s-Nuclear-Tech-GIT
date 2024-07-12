package com.hbm.inventory.gui;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import com.hbm.dim.SkyProviderCelestial;
import com.hbm.dim.SolarSystem;
import com.hbm.interfaces.Spaghetti;
import com.hbm.inventory.container.ContainerStardar;
import com.hbm.lib.RefStrings;
import com.hbm.packet.NBTControlPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.tileentity.machine.TileEntityElectrolyser;
import com.hbm.tileentity.machine.TileEntityMachineStardar;
import com.hbm.util.AstronomyUtil;

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
    protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
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
        	additivey--;
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
        	additivey++;
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
        	additive--;
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
        	additive++;
        }

        popScissor();
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