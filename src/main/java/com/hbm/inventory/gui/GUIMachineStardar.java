package com.hbm.inventory.gui;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import com.hbm.dim.CelestialBody;
import com.hbm.dim.SkyProviderCelestial;
import com.hbm.dim.SolarSystem;
import com.hbm.dim.SolarSystem.AstroMetric;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import org.lwjgl.input.Keyboard;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
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
    private List<POI> pList = new ArrayList<>();
	Random rnd = new Random();

    public void init() {
    	for(CelestialBody rody : CelestialBody.getLandableBodies()) {
    		CelestialBody body = CelestialBody.getBody(star.getWorldObj());
    		if(rody != body) {
        		int posX = rnd.nextInt(256);
        		int posY = rnd.nextInt(256);
        		pList.add(new POI(posX, posY, rody.getBody(rody.dimensionId)));	
    		}
    	}
    }
    
    public GUIMachineStardar(InventoryPlayer iplayer, TileEntityMachineStardar restard) {
        super(new ContainerStardar(iplayer, restard));
        this.star = restard;

        this.xSize = 210;
        this.ySize = 256;
        init();
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
		
		//RenderPOI(80, 80, x, y, 1);
		//RenderPOI(60, 100, x, y, 2);
		//RenderPOI(20, 100, x, y, 3);

		//TODO: so this is a test to see if the system well... systems. but anyway im thinking that you can see arrows that point to a planet to look at and then you can "ping"
		//RenderPOI(80, 80, x, y, 1);
		//RenderPOI(60, 100, x, y, 2);
		//RenderPOI(20, 85, x, y, 3); 
		//RenderPOI(10, 70, x, y, 2); 
		//RenderPOI(20, 40, x, y, 4); 
		//RenderPOI(65, 50, x, y, 1); 
		

			
		for(POI peepee : pList) {
			RenderPOI(peepee.offsetX, peepee.offsetY, x, y, peepee.getBody().processingLevel, peepee.getBody().name);
		}
    }
    
    protected boolean WithinBounds(int x, int y) {
		return x < 275 && x > 143 && y > 6 && y < 109;
	}
    
    
    protected void RenderPOI(int offsetx, int offsety, int mx, int my, int tier, String name) {

		int x = (int) (guiLeft + additive + offsetx);
		int y = (int) (guiTop + additivey + offsety);
		
		if(WithinBounds(x, y)) {

	        Minecraft.getMinecraft().getTextureManager().bindTexture(texture);

			switch (tier) {
			case 1:
		        drawTexturedModalRect(guiLeft +(int)additive + offsetx, guiTop + (int)additivey + offsety ,  191, 0, 6, 7);
				break;
			case 2:
		        drawTexturedModalRect(guiLeft +(int)additive + offsetx, guiTop + (int)additivey + offsety ,  164, 0, 8, 8);
				break;
			case 3:
		        drawTexturedModalRect(guiLeft +(int)additive + offsetx, guiTop + (int)additivey + offsety ,  173, 0, 8, 8);
				break;
			case 4:
		        drawTexturedModalRect(guiLeft +(int)additive + offsetx, guiTop + (int)additivey + offsety ,  182, 0, 8, 8);
				break;
			default:
		        drawTexturedModalRect(guiLeft +(int)additive + offsetx, guiTop + (int)additivey + offsety ,  157, 0, 6, 7);
				break;
			}
			
		}
        int button = Mouse.getEventButton();

		if(checkClick(mx, my, (int) (additive + offsetx), guiTop + y, 8,8)) {
			//this.func_146283_a(Arrays.asList(I18nUtil.resolveKeyArray("POI Tier: " + tier)), x, y);
			//drawCustomInfoStat(mx, my, x, y, 35, 14, mx, my, name);
			drawCustomInfoStat(mx, my, x, y, 35, 14, mx, my, name, "Processing Tier: " + String.format(Locale.US, "%,d", (int)(tier)));
        }
		


    
	}
	protected boolean checkClick(int x, int y, int left, int top, int sizeX, int sizeY) {
		return guiLeft + left <= x && guiLeft + left + sizeX > x && guiTop + top < y && guiTop + top + sizeY >= y;
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
		this.drawGuiContainerForegroundLayer(mouseX, mouseY);

    }

    @Override
    protected void mouseClicked(int x, int y, int i) {
        super.mouseClicked(x, y, i);
        sX = x;
        sY = y;
        mX = x;
        mY = y;
		//if(checkClick(x, y, (int) (additive + po), guiTop + y, 8,8)) {
		//	System.out.println("fuck");
		//}
        for (POI poi : pList) {
            int poiX = (int) (additive + poi.offsetX);
            int poiY = (int) (additivey + poi.offsetY);
            if (checkClick(sX, sY, poiX, poiY, 8, 8)) {
    			NBTTagCompound data = new NBTTagCompound();
    			data.setString("Pname", poi.getBody().name);
    			data.setInteger("tier", poi.getBody().processingLevel);
    			data.setInteger("id", poi.getBody().dimensionId);

    			PacketDispatcher.wrapper.sendToServer(new NBTControlPacket(data, star.xCoord, star.yCoord, star.zCoord));
                break;
            }
        }
    
    }
public static class POI{
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

	