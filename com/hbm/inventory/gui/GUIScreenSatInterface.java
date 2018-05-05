package com.hbm.inventory.gui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.lwjgl.opengl.GL11;

import com.hbm.handler.FluidTypeHandler.FluidType;
import com.hbm.inventory.MachineRecipes;
import com.hbm.items.ModItems;
import com.hbm.items.tool.ItemAssemblyTemplate.EnumAssemblyTemplate;
import com.hbm.items.tool.ItemCassette;
import com.hbm.items.tool.ItemCassette.TrackType;
import com.hbm.items.tool.ItemChemistryTemplate;
import com.hbm.items.tool.ItemFluidIdentifier;
import com.hbm.items.tool.ItemSatChip;
import com.hbm.lib.RefStrings;
import com.hbm.packet.ItemFolderPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.saveddata.SatelliteSaveStructure;
import com.hbm.saveddata.SatelliteSavedData;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;

public class GUIScreenSatInterface extends GuiScreen {
	
    protected static final ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/gui_sat_interface.png");
    protected int xSize = 216;
    protected int ySize = 216;
    protected int guiLeft;
    protected int guiTop;
    private final EntityPlayer player;
    protected SatelliteSaveStructure connectedSat;
    public static SatelliteSavedData satData;
    
    public GUIScreenSatInterface(EntityPlayer player) {
    	
    	this.player = player;
    }
    
    public void updateScreen() {
    }
    
    public void drawScreen(int mouseX, int mouseY, float f)
    {
        this.drawDefaultBackground();
        this.drawGuiContainerBackgroundLayer(f, mouseX, mouseY);
        GL11.glDisable(GL11.GL_LIGHTING);
        this.drawGuiContainerForegroundLayer(mouseX, mouseY);
        GL11.glEnable(GL11.GL_LIGHTING);
    }
    
    public void initGui()
    {
        super.initGui();
        this.guiLeft = (this.width - this.xSize) / 2;
        this.guiTop = (this.height - this.ySize) / 2;
	    
	    if(satData != null && player.getHeldItem() != null && player.getHeldItem().getItem() == ModItems.sat_interface) {

			int freq = ItemSatChip.getFreq(player.getHeldItem());
			
			connectedSat = satData.getSatFromFreq(freq);
	    }
    }
	
	protected void drawGuiContainerForegroundLayer(int i, int j) {

	}

	protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		
		if(connectedSat == null) {
			drawNotConnected();
		} else {
			switch(connectedSat.satelliteType) {
			
			case LASER:
				break;
				
			case MAPPER:
				drawMap(); break;
				
			case RADAR:
				drawRadar(); break;
				
			case SCANNER:
				drawScan(); break;
				
			case RELAY:
			case RESONATOR:
				drawNoService(); break;
			}
		}
	}
	
	private int scanPos = 0;
	private long lastMilli = 0;
	
	private void progresScan() {
		
		if(lastMilli + 25 < System.currentTimeMillis()) {
			lastMilli = System.currentTimeMillis();
			scanPos++;
		}
		
		if(scanPos >= 200)
			scanPos -= 200;
	}
	
	private int[][] map = new int[200][200];
	
	private void drawMap() {
		
		World world = player.worldObj;
		
		for(int i = -100; i < 100; i++) {
			int x = (int)player.posX + i;
			int z = (int)player.posZ + scanPos - 100;
			int y = world.getHeightValue(x, z) - 1;
			map[i + 100][scanPos] = world.getBlock(x, y, z).getMaterial().getMaterialMapColor().func_151643_b(2);
		}
		prontMap();
		progresScan();
	}
	
	private void drawScan() {
		
		World world = player.worldObj;
		
		for(int i = -100; i < 100; i++) {
			int x = (int)player.posX + i;
			int z = (int)player.posZ + scanPos - 100;
			//int y = world.getHeightValue(x, z) - 1;
			//map[i + 100][scanPos] = 0x081F0D + world.getBlock(x, y, z).getMaterial().getMaterialMapColor().func_151643_b(3);
			
			for(int j = 255; j >= 0; j--) {
				int c = getColorFromBlock(new ItemStack(world.getBlock(x, j, z), 1, world.getBlockMetadata(x, j, z)));
				
				if(c != 0) {
					map[i + 100][scanPos] = c;
					break;
				}
			}
		}
		prontMap();
		progresScan();
	}
	
	private int getColorFromBlock(ItemStack stack) {
		
		if(stack == null || stack.getItem() == null/* || stack.getItemDamage() < 0*/)
			return 0;

		if(MachineRecipes.mODE(stack, "oreCoal"))
			return 0x333333;
		if(MachineRecipes.mODE(stack, "oreIron"))
			return 0xb2aa92;
		if(MachineRecipes.mODE(stack, "oreGold"))
			return 0xffe460;
		if(MachineRecipes.mODE(stack, "oreSilver"))
			return 0xe5e5e5;
		if(MachineRecipes.mODE(stack, "oreDiamond"))
			return 0x6ed5ef;
		if(MachineRecipes.mODE(stack, "oreEmerald"))
			return 0x6cf756;
		if(MachineRecipes.mODE(stack, "oreLapis"))
			return 0x092f7a;
		if(MachineRecipes.mODE(stack, "oreRedstone"))
			return 0xe50000;
		if(MachineRecipes.mODE(stack, "oreTin"))
			return 0xa09797;
		if(MachineRecipes.mODE(stack, "oreCopper"))
			return 0xd16208;
		if(MachineRecipes.mODE(stack, "oreLead"))
			return 0x384b68;
		if(MachineRecipes.mODE(stack, "oreAluminum"))
			return 0xdbdbdb;
		if(MachineRecipes.mODE(stack, "oreTungsten"))
			return 0x333333;
		if(MachineRecipes.mODE(stack, "oreUranium"))
			return 0x3e4f3c;
		if(MachineRecipes.mODE(stack, "oreBeryllium"))
			return 0x8e8d7d;
		if(MachineRecipes.mODE(stack, "oreSulfur"))
			return 0x9b9309;
		if(MachineRecipes.mODE(stack, "oreSalpeter"))
			return 0xa5a09d;
		if(MachineRecipes.mODE(stack, "oreFluorite"))
			return 0xffffff;
		if(MachineRecipes.mODE(stack, "oreSchrabidium"))
			return 0x1cffff;
		
		return isOre(stack) ? 0xBA00AF : 0x000000;
	}
	
	private static boolean isOre(ItemStack stack) {
		
		int[] ids = OreDictionary.getOreIDs(new ItemStack(stack.getItem(), 1, stack.getItemDamage()));
		
		for(int i = 0; i < ids.length; i++) {
			
			String s = OreDictionary.getOreName(ids[i]);
			
			if(s.length() > 3 && s.substring(3).equals("ore"))
				return true;
		}
		
		return false;
	}
	
	private void drawRadar() {
		
	}
	
	private void prontMap() {
		for(int x = 0; x < 200; x++) {
			for(int z = 0; z < 200; z++) {
				if(map[x][z] != 0) {
					GL11.glColor3ub((byte)((map[x][z] & 0xFF0000) >> 16), (byte)((map[x][z] & 0x00FF00) >> 8), (byte)(map[x][z] & 0x0000FF));
					drawTexturedModalRect(guiLeft + 8 + x, guiTop + 8 + z, 216, 216, 1, 1);
				}
			}
		}
		
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	}
	
	private void drawNoService() {
		drawTexturedModalRect((this.width - 77) / 2, (this.height - 12) / 2, 0, 228, 77, 12);
	}
	
	private void drawNotConnected() {
		drawTexturedModalRect((this.width - 121) / 2, (this.height - 12) / 2, 0, 216, 121, 12);
	}
	
    protected void keyTyped(char p_73869_1_, int p_73869_2_)
    {
        if (p_73869_2_ == 1 || p_73869_2_ == this.mc.gameSettings.keyBindInventory.getKeyCode())
        {
            this.mc.thePlayer.closeScreen();
        }
        
    }

}
