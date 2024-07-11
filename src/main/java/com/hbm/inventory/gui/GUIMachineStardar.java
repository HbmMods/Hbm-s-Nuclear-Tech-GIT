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
 * jesus fucking christ, this is exactly the kind of shit i was fearing when i initially thought of ways to have a planet selector screen.
 * its not that i cant finish this, or the fact that this shit took me an ENTIRE FUCKING NIGHT. its the fact how fucking *close* i was
 * and the fact that i cannot under my utter retardedness cannot figure it out holy shit and i might actually never will.
 * its over for me as a programmer.
 */

@Spaghetti("dont wanna put it in the annotation")
public class GUIMachineStardar extends GuiInfoContainer {
	public static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/machine/gui_stardar.png");
	private static final ResourceLocation nightTexture = new ResourceLocation(RefStrings.MODID, "textures/misc/space/night.png");
	protected boolean clickorus;
	private TileEntityMachineStardar star;
	private int mX, mY;
	private int sX, sY;
	private int imgoingtojumpoffabuildingbutwithloveandroses, nmass2;
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
		int nmass = (mX - sX);
		int ymass = (mY - sY);
		imgoingtojumpoffabuildingbutwithloveandroses = nmass;
		nmass2 = ymass;

		
		drawTexturedModalRect(guiLeft , guiTop, mX, mY, xSize, ySize);
        if (Keyboard.isKeyDown(Keyboard.KEY_W))
        {
        	mY--;
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_S))        
        {
        	mY++;
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_A))        
        { 
        	mX--;
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_D))        
        {
        	mX++;
        }
		popScissor();
	}
	
	
    @Override
    public void handleMouseInput() {
        super.handleMouseInput();
        /*
        int button = Mouse.getEventButton();

        if (button == 0 && !Mouse.getEventButtonState()) { 
        	//sX = mX;
        	//sY = mY;

        	imgoingtojumpoffabuildingbutwithloveandroses = (mX - sX);
        	nmass2 = (mY - sY);
    		System.out.println("i let go of the mouse here (still the difference) x: " + imgoingtojumpoffabuildingbutwithloveandroses);
    		System.out.println("i let go of the mouse here (still the difference) y: " + nmass2);
        }       
        if (button == 0 && Mouse.getEventButtonState()) {
            sX = Mouse.getEventX() * this.width / this.mc.displayWidth;
            sY = this.height - Mouse.getEventY() * this.height / this.mc.displayHeight - 1;

        }
        */
    }
	@Override
	protected void mouseClickMove(int x, int y, int p_146273_3_, long p_146273_4_) {
		
		super.mouseClickMove(x, y, p_146273_3_, p_146273_4_);
		
		/*
		mX = x;
		mY = y;
		*/


	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);
		
	}
	
	@Override 
	protected void mouseClicked(int x, int y, int i) {
		super.mouseClicked(x, y, i);

		/*
		sX = x;
		sY = y;
		mX = x;
		mY = y;
		System.out.println("start x: " + sX);
		System.out.println("start y: " + sY);
		System.out.println("mouse x: " + mX);
		System.out.println("mouse y: " + mY);
		System.out.println("the difference in x: " + imgoingtojumpoffabuildingbutwithloveandroses);
		System.out.println("the difference in y: " + nmass2);
	}
	*/
	}
}
