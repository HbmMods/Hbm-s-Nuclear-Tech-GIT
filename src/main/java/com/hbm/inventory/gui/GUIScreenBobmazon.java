package com.hbm.inventory.gui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.lwjgl.opengl.GL11;

import com.hbm.items.ModItems;
import com.hbm.lib.RefStrings;
import com.hbm.main.MainRegistry;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.toserver.ItemBobmazonPacket;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Achievement;
import net.minecraft.util.ResourceLocation;

public class GUIScreenBobmazon extends GuiScreen {
	
    protected static final ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/gui_bobmazon.png");
    protected int xSize = 176;
    protected int ySize = 229;
    protected int guiLeft;
    protected int guiTop;
    int currentPage = 0;
    List<Offer> offers = new ArrayList<Offer>();
    List<FolderButton> buttons = new ArrayList<FolderButton>();
    private final EntityPlayer player;
    
    public GUIScreenBobmazon(EntityPlayer player, List<Offer> offers) {
    	
    	this.player = player;

    	this.offers = offers;
    }
    
    int getPageCount() {
    	return (int)Math.ceil((offers.size() - 1) / 3);
    }
    
    public void updateScreen() {
    	if(currentPage < 0)
    		currentPage = 0;
    	if(currentPage > getPageCount())
    		currentPage = getPageCount();
    	
    	if(this.player.getHeldItem() != null && this.player.getHeldItem().getItem() == ModItems.bobmazon_hidden && player.getDisplayName().equals("SolsticeUnlimitd"))
    		this.mc.thePlayer.closeScreen();
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

        updateButtons();
    }
    
    protected void updateButtons() {
        
        if(!buttons.isEmpty())
        	buttons.clear();
        
        for(int i = currentPage * 3; i < Math.min(currentPage * 3 + 3, offers.size()); i++) {
    		buttons.add(new FolderButton(guiLeft + 34, guiTop + 35 + (54 * (int)Math.floor(i)) - currentPage * 3 * 54, offers.get(i)));
        }

        if(currentPage != 0)
        	buttons.add(new FolderButton(guiLeft + 25 - 18, guiTop + 26 + (27 * 3), 1, "Previous"));
        if(currentPage != getPageCount())
        	buttons.add(new FolderButton(guiLeft + 25 + (27 * 4) + 18, guiTop + 26 + (27 * 3), 2, "Next"));
    }

    protected void mouseClicked(int i, int j, int k) {
    	try {
    		for(FolderButton b : buttons)
    			if(b.isMouseOnButton(i, j))
    				b.executeAction();
    	} catch (Exception ex) {
    		updateButtons();
    	}
    }
	
	protected void drawGuiContainerForegroundLayer(int i, int j) {

		this.fontRendererObj.drawString(I18n.format((currentPage + 1) + "/" + (getPageCount() + 1)), 
				guiLeft + this.xSize / 2 - this.fontRendererObj.getStringWidth(I18n.format((currentPage + 1) + "/" + (getPageCount() + 1))) / 2, guiTop + 205, 4210752);
		
		for(FolderButton b : buttons)
			if(b.isMouseOnButton(i, j))
				b.drawString(i, j);
	}

	protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

		for(FolderButton b : buttons)
			b.drawButton(b.isMouseOnButton(i, j));
		for(FolderButton b : buttons)
			b.drawIcon(b.isMouseOnButton(i, j));
        
        for(int d = currentPage * 3; d < Math.min(currentPage * 3 + 3, offers.size()); d++) {
    		offers.get(d).drawRequirement(this, guiLeft + 34, guiTop + 53 + (54 * (int)Math.floor(d)) - currentPage * 3 * 54);
        }
	}
	
    protected void keyTyped(char p_73869_1_, int p_73869_2_)
    {
        if (p_73869_2_ == 1 || p_73869_2_ == this.mc.gameSettings.keyBindInventory.getKeyCode())
        {
            this.mc.thePlayer.closeScreen();
        }
        
    }
	
	class FolderButton {
		
		int xPos;
		int yPos;
		//0: regular, 1: prev, 2: next
		int type;
		String info;
		Offer offer;
		
		public FolderButton(int x, int y, int t, String i) {
			xPos = x;
			yPos = y;
			type = t;
			info = i;
		}
		
		public FolderButton(int x, int y, Offer offer) {
			xPos = x;
			yPos = y;
			type = 0;
			this.offer = offer;
		}
		
		public void updateButton(int mouseX, int mouseY) {
		}
		
		public boolean isMouseOnButton(int mouseX, int mouseY) {
			return xPos <= mouseX && xPos + 18 > mouseX && yPos < mouseY && yPos + 18 >= mouseY;
		}
		
		public void drawButton(boolean b) {
			Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
			drawTexturedModalRect(xPos, yPos, b ? 176 + 18 : 176, type == 1 ? 18 : (type == 2 ? 36 : 0), 18, 18);
		}
		
		public void drawIcon(boolean b) {
			try {
		        GL11.glDisable(GL11.GL_LIGHTING);
				if(offer != null) {
					itemRender.renderItemAndEffectIntoGUI(fontRendererObj, mc.getTextureManager(), offer.offer, xPos + 1, yPos + 1);
				}
		        GL11.glEnable(GL11.GL_LIGHTING);
			} catch(Exception x) { }
		}
		
		public void drawString(int x, int y) {
			if(info == null || info.isEmpty())
				return;
			
			func_146283_a(Arrays.asList(new String[] { info }), x, y);
		}
		
		public void executeAction() {
			mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
			if(type == 0) {
				PacketDispatcher.wrapper.sendToServer(new ItemBobmazonPacket(player, offer));
			} else if(type == 1) {
				if(currentPage > 0)
					currentPage--;
				updateButtons();
			} else if(type == 2) {
				if(currentPage < getPageCount())
					currentPage++;
				updateButtons();
			}
		}
	}
	
	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}
	
	public static class Offer {
		
		public ItemStack offer;
		public Requirement requirement;
		public int cost;
		public int rating;
		public String comment;
		public String author;
		
		public Offer(ItemStack offer, Requirement requirement, int cost, int rating, String comment, String author) {
			this.offer = offer;
			this.requirement = requirement;
			this.cost = cost;
			this.rating = rating * 4 - 1;
			this.comment = comment;
			this.author = author;
		}
		
		public Offer(ItemStack offer, Requirement requirement, int cost) {
			this.offer = offer;
			this.requirement = requirement;
			this.cost = cost;
			this.rating = 0;
			this.comment = "No Ratings";
			this.author = "";
		}
		
		public void drawRequirement(GUIScreenBobmazon gui, int x, int y) {
			try {

				RenderHelper.enableGUIStandardItemLighting();
				GL11.glColor3f(1F, 1F, 1F);
				Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
				gui.drawTexturedModalRect(x + 19, y - 4, 176, 62, 39, 8);
				gui.drawTexturedModalRect(x + 19, y - 4, 176, 54, rating, 8);
				
				String count = "";
				if(offer.stackSize > 1)
					count = " x" + offer.stackSize;

				GL11.glPushMatrix();
				
				float scale = 0.65F;
				GL11.glScalef(scale, scale, scale);
				gui.fontRendererObj.drawString(I18n.format(offer.getDisplayName()) + count, (int)((x + 20) / scale), (int)((y - 12) / scale), 4210752);
				
				GL11.glPopMatrix();
				
				String price = cost + " Cap";
				if(cost != 1)
					price += "s";

				gui.fontRendererObj.drawString(price, x + 62, y - 3, 4210752);
				
				GL11.glPushMatrix();
					
					GL11.glScalef(0.5F, 0.5F, 0.5F);
					
					if(!author.isEmpty())
						gui.fontRendererObj.drawString("- " + author, (x + 20) * 2, (y + 18) * 2, 0x222222);
					gui.fontRendererObj.drawString(comment, (x + 20) * 2, (y + 8) * 2, 0x222222);
					
				GL11.glPopMatrix();
				
		        GL11.glDisable(GL11.GL_LIGHTING);
				if(offer != null) {
					gui.itemRender.renderItemAndEffectIntoGUI(gui.fontRendererObj, gui.mc.getTextureManager(), requirement.achievement.theItemStack, x + 1, y + 1);
				}
		        GL11.glEnable(GL11.GL_LIGHTING);
		        
			} catch(Exception ex) { }
		}
		
	}
	
	public enum Requirement {

		STEEL(MainRegistry.achBlastFurnace),
		ASSEMBLY(MainRegistry.achAssembly),
		CHEMICS(MainRegistry.achChemplant),
		OIL(MainRegistry.achDesh),
		NUCLEAR(MainRegistry.achTechnetium),
		HIDDEN(MainRegistry.bobHidden);
		
		private Requirement(Achievement achievement) {
			this.achievement = achievement;
		}
		
		public boolean fullfills(EntityPlayerMP player) {
			
			return player.func_147099_x().hasAchievementUnlocked(achievement);
		}
		
		public Achievement achievement;
	}

}
