package com.hbm.inventory.gui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.lwjgl.opengl.GL11;

import com.hbm.items.ModItems;
import com.hbm.lib.RefStrings;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.toserver.ItemDesignatorPacket;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class GUIScreenDesignator extends GuiScreen {
	
    protected static final ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/gui_designator.png");
    protected int xSize = 176;
    protected int ySize = 178;
    protected int guiLeft;
    protected int guiTop;
    int shownX;
    int shownZ;
    int currentPage = 0;
    List<ItemStack> stacks = new ArrayList<ItemStack>();
    List<FolderButton> buttons = new ArrayList<FolderButton>();
    private final EntityPlayer player;
    
    public GUIScreenDesignator(EntityPlayer player) {
    	
    	this.player = player;
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

		shownX = 0;
		shownZ = 0;
		ItemStack stack = player.getHeldItem();
		
		if(stack != null && stack.getItem() == ModItems.designator_manual && stack.hasTagCompound()) {
			shownX = stack.stackTagCompound.getInteger("xCoord");
			shownZ = stack.stackTagCompound.getInteger("zCoord");
		}
		
		updateButtons();
    }
    
    private void updateButtons() {
    	buttons.clear();

    	buttons.add(new FolderButton(guiLeft + 25,	guiTop + 26, 0, 0, 0, 1, null));
    	buttons.add(new FolderButton(guiLeft + 52,	guiTop + 26, 1, 0, 0, 5, null));
    	buttons.add(new FolderButton(guiLeft + 79,	guiTop + 26, 2, 0, 0, 10, null));
    	buttons.add(new FolderButton(guiLeft + 106,	guiTop + 26, 3, 0, 0, 50, null));
    	buttons.add(new FolderButton(guiLeft + 133,	guiTop + 26, 4, 0, 0, 100, null));

    	buttons.add(new FolderButton(guiLeft + 25,	guiTop + 62, 5, 1, 0, 1, null));
    	buttons.add(new FolderButton(guiLeft + 52,	guiTop + 62, 6, 1, 0, 5, null));
    	buttons.add(new FolderButton(guiLeft + 79,	guiTop + 62, 7, 1, 0, 10, null));
    	buttons.add(new FolderButton(guiLeft + 106,	guiTop + 62, 8, 1, 0, 50, null));
    	buttons.add(new FolderButton(guiLeft + 133,	guiTop + 62, 9, 1, 0, 100, null));

    	buttons.add(new FolderButton(guiLeft + 133,	guiTop + 44, 10, 2, 0, 0, "Set coord to current X position..."));

    	buttons.add(new FolderButton(guiLeft + 25,	guiTop + 26 + 72, 0, 0, 1, 1, null));
    	buttons.add(new FolderButton(guiLeft + 52,	guiTop + 26 + 72, 1, 0, 1, 5, null));
    	buttons.add(new FolderButton(guiLeft + 79,	guiTop + 26 + 72, 2, 0, 1, 10, null));
    	buttons.add(new FolderButton(guiLeft + 106,	guiTop + 26 + 72, 3, 0, 1, 50, null));
    	buttons.add(new FolderButton(guiLeft + 133,	guiTop + 26 + 72, 4, 0, 1, 100, null));

    	buttons.add(new FolderButton(guiLeft + 25,	guiTop + 62 + 72, 5, 1, 1, 1, null));
    	buttons.add(new FolderButton(guiLeft + 52,	guiTop + 62 + 72, 6, 1, 1, 5, null));
    	buttons.add(new FolderButton(guiLeft + 79,	guiTop + 62 + 72, 7, 1, 1, 10, null));
    	buttons.add(new FolderButton(guiLeft + 106,	guiTop + 62 + 72, 8, 1, 1, 50, null));
    	buttons.add(new FolderButton(guiLeft + 133,	guiTop + 62 + 72, 9, 1, 1, 100, null));

    	buttons.add(new FolderButton(guiLeft + 133,	guiTop + 44 + 72, 10, 2, 1, 0, "Set coord to current Z position..."));
    }

    protected void mouseClicked(int i, int j, int k) {
    	try {
    		for(FolderButton b : buttons)
    			if(b.isMouseOnButton(i, j))
    				b.executeAction();
    	} catch (Exception ex) { }
    }
	
	protected void drawGuiContainerForegroundLayer(int i, int j) {

		//this.fontRendererObj.drawString(I18n.format((currentPage + 1) + "/" + (getPageCount() + 1)), 
		//		guiLeft + this.xSize / 2 - this.fontRendererObj.getStringWidth(I18n.format((currentPage + 1) + "/" + (getPageCount() + 1))) / 2, guiTop + 10, 4210752);
		
		for(FolderButton b : buttons)
			if(b.isMouseOnButton(i, j))
				b.drawString(i, j);

		String x = String.valueOf(shownX);
		String z = String.valueOf(shownZ);
		this.fontRendererObj.drawString("X: " + x, 
				guiLeft + this.xSize / 2 - this.fontRendererObj.getStringWidth("X: " + x) / 2, guiTop + 50, 4210752);
		this.fontRendererObj.drawString("Z: " + z, 
				guiLeft + this.xSize / 2 - this.fontRendererObj.getStringWidth("Z: " + z) / 2, guiTop + 50 + 18 * 4, 4210752);
	}

	protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

		for(FolderButton b : buttons)
			b.drawButton(b.isMouseOnButton(i, j));
	}
	
    protected void keyTyped(char p_73869_1_, int p_73869_2_)
    {
        if (p_73869_2_ == 1 || p_73869_2_ == this.mc.gameSettings.keyBindInventory.getKeyCode())
        {
            this.mc.thePlayer.closeScreen();
        }
        
    }
    
    public void updateScreen() {
    	if(player.getHeldItem() == null || player.getHeldItem().getItem() != ModItems.designator_manual)
    		player.closeScreen();
    }
	
	class FolderButton {
		
		int xPos;
		int yPos;
		int type;
		int operator;
		int value;
		int reference;
		String info;
		
		public FolderButton(int x, int y, int t, int o, int r, int v, String i) {
			xPos = x;
			yPos = y;
			type = t;
			operator = o;
			value = v;
			reference = r;
			info = i;
		}
		
		public void updateButton(int mouseX, int mouseY) {
		}
		
		public boolean isMouseOnButton(int mouseX, int mouseY) {
			return xPos <= mouseX && xPos + 18 > mouseX && yPos < mouseY && yPos + 18 >= mouseY;
		}
		
		public void drawButton(boolean b) {
			Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
			drawTexturedModalRect(xPos, yPos, b ? 176 + 18 : 176, type * 18, 18, 18);
		}
		
		public void drawString(int x, int y) {
			if(info == null || info.isEmpty())
				return;
			
			String s = info;

			func_146283_a(Arrays.asList(new String[] { s }), x, y);
		}
		
		public void executeAction() {
			mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
			PacketDispatcher.wrapper.sendToServer(new ItemDesignatorPacket(this.operator, this.value, this.reference));
			
			int result = 0;

			if(operator == 0)
				result += value;
			if(operator == 1)
				result -= value;
			if(operator == 2) {
				if(reference == 0)
					shownX = (int)Math.round(player.posX);
				else
					shownZ = (int)Math.round(player.posZ);
				return;
			}
			
			if(reference == 0)
				shownX += result;
			else
				shownZ += result;
		}
		
	}

}
