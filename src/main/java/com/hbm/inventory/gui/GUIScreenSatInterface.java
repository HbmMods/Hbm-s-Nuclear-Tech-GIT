package com.hbm.inventory.gui;

import java.util.Arrays;
import java.util.List;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.recipes.MachineRecipes;
import com.hbm.items.ISatChip;
import com.hbm.items.tool.ItemSatInterface;
import com.hbm.lib.RefStrings;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.toserver.SatLaserPacket;
import com.hbm.saveddata.satellites.Satellite.InterfaceActions;
import com.hbm.saveddata.satellites.Satellite.Interfaces;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;

public class GUIScreenSatInterface extends GuiScreen {
	
    protected static final ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/satellites/gui_sat_interface.png");
    protected int xSize = 216;
    protected int ySize = 216;
    protected int guiLeft;
    protected int guiTop;
    private final EntityPlayer player;
    int x;
    int z;
    
    public GUIScreenSatInterface(EntityPlayer player) {
    	
    	this.player = player;
    }
    
    public void updateScreen() {
    }

    protected void mouseClicked(int i, int j, int k) {
    	
    	if(ItemSatInterface.currentSat != null && ItemSatInterface.currentSat.ifaceAcs.contains(InterfaceActions.CAN_CLICK)) {

    		if(i >= this.guiLeft + 8 && i < this.guiLeft + 208 && j >= this.guiTop + 8 && j < this.guiTop + 208 && player != null) {
    			
    			mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("hbm:item.techBleep"), 1.0F));
    			
    			int x = this.x - guiLeft + i - 8 - 100;
    			int z = this.z - guiTop + j - 8 - 100;
    			PacketDispatcher.wrapper.sendToServer(new SatLaserPacket(x, z, ISatChip.getFreqS(player.getHeldItem())));
    		}
    	}
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
        
        x = (int) player.posX;
        z = (int) player.posZ;
    }
	
	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}
	
	protected void drawGuiContainerForegroundLayer(int i, int j) {

    	if(ItemSatInterface.currentSat != null && ItemSatInterface.currentSat.ifaceAcs.contains(InterfaceActions.SHOW_COORDS)) {
    		
    		if(i >= this.guiLeft + 8 && i < this.guiLeft + 208 && j >= this.guiTop + 8 && j < this.guiTop + 208 && player != null) {

    			int x = this.x - guiLeft + i - 8 - 100;
    			int z = this.z - guiTop + j - 8 - 100;
    			func_146283_a(Arrays.asList(new String[] { x + " / " + z }), i, j);
    		}
    	}
	}

	protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		
		if(ItemSatInterface.currentSat == null) {
			drawNotConnected();
		} else {
			
			if(ItemSatInterface.currentSat.satIface != Interfaces.SAT_PANEL) {
				drawNoService();
				return;
			}

			if(ItemSatInterface.currentSat.ifaceAcs.contains(InterfaceActions.HAS_MAP)) {
				drawMap();
			}
			if(ItemSatInterface.currentSat.ifaceAcs.contains(InterfaceActions.HAS_ORES)) {
				drawScan();
			}
			if(ItemSatInterface.currentSat.ifaceAcs.contains(InterfaceActions.HAS_RADAR)) {
				drawRadar();
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
			int x = this.x + i;
			int z = this.z + scanPos - 100;
			int y = world.getHeightValue(x, z) - 1;
			map[i + 100][scanPos] = world.getBlock(x, y, z).getMaterial().getMaterialMapColor().colorValue;
		}
		prontMap();
		progresScan();
	}
	
	private void drawScan() {
		
		World world = player.worldObj;
		
		for(int i = -100; i < 100; i++) {
			int x = this.x + i;
			int z = this.z + scanPos - 100;
			
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
		if(MachineRecipes.mODE(stack, "oreTitanium"))
			return 0xDDDDDD;
		if(MachineRecipes.mODE(stack, "oreUranium"))
			return 0x3e4f3c;
		if(MachineRecipes.mODE(stack, "oreBeryllium"))
			return 0x8e8d7d;
		if(MachineRecipes.mODE(stack, "oreSulfur"))
			return 0x9b9309;
		if(MachineRecipes.mODE(stack, "oreSalpeter") || MachineRecipes.mODE(stack, "oreNiter"))
			return 0xa5a09d;
		if(MachineRecipes.mODE(stack, "oreFluorite"))
			return 0xffffff;
		if(MachineRecipes.mODE(stack, "oreSchrabidium"))
			return 0x1cffff;
		if(MachineRecipes.mODE(stack, "oreRareEarth"))
			return 0xffcc99;
		
		return isOre(stack) ? 0xBA00AF : 0x000000;
	}
	
	private static boolean isOre(ItemStack stack) {
		
		int[] ids = OreDictionary.getOreIDs(new ItemStack(stack.getItem(), 1, stack.getItemDamage()));
		
		for(int i = 0; i < ids.length; i++) {
			
			String s = OreDictionary.getOreName(ids[i]);
			
			if(s.length() > 3 && s.substring(0, 3).equals("ore"))
				return true;
		}
		
		return false;
	}
	
	private void drawRadar() {
		
		List<Entity> entities = player.worldObj.getEntitiesWithinAABBExcludingEntity(player, AxisAlignedBB.getBoundingBox(player.posX - 100, 0, player.posZ - 100, player.posX + 100, 5000, player.posZ + 100));
		
		if(!entities.isEmpty()) {
			for(Entity e : entities) {
				
				if(e.width * e.width * e.height >= 0.5D) {
					int x = (int)((e.posX - this.x) / ((double)100 * 2 + 1) * (200D - 8D)) - 4;
					int z = (int)((e.posZ - this.z) / ((double)100 * 2 + 1) * (200D - 8D)) - 4 - 9;
					
					int t = 5;
					
					//TODO: fix radar screen implementation
					/*if(e instanceof EntityMissileBaseAdvanced) {
						t = ((EntityMissileBaseAdvanced)e).getTargetType().ordinal();
					}*/
					
					if(e instanceof EntityMob) {
						t = 6;
					}
					
					if(e instanceof EntityPlayer) {
						t = 7;
					}
	
					drawTexturedModalRect(guiLeft + 108 + x, guiTop + 117 + z, 216, 8 * t, 8, 8);
				}
			}
		}
		
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
        
        if (p_73869_2_ == this.mc.gameSettings.keyBindForward.getKeyCode())
        {
            this.z -= 50;
            map = new int[200][200];
        }
        
        if (p_73869_2_ == this.mc.gameSettings.keyBindBack.getKeyCode())
        {
            this.z += 50;
            map = new int[200][200];
        }
        
        if (p_73869_2_ == this.mc.gameSettings.keyBindLeft.getKeyCode())
        {
            this.x -= 50;
            map = new int[200][200];
        }
        
        if (p_73869_2_ == this.mc.gameSettings.keyBindRight.getKeyCode())
        {
            this.x += 50;
            map = new int[200][200];
        }
        
    }

}
