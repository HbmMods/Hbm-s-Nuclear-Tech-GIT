package com.hbm.inventory.gui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.lwjgl.opengl.GL11;

import com.hbm.handler.FluidTypeHandler.FluidType;
import com.hbm.inventory.MachineRecipes;
import com.hbm.items.ModItems;
import com.hbm.items.tool.ItemAssemblyTemplate.EnumAssemblyTemplate;
import com.hbm.items.tool.ItemChemistryTemplate;
import com.hbm.items.tool.ItemFluidIdentifier;
import com.hbm.lib.RefStrings;
import com.hbm.packet.ItemFolderPacket;
import com.hbm.packet.PacketDispatcher;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class GUIScreenTemplateFolder extends GuiScreen {
	
    protected static final ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/gui_planner.png");
    protected int xSize = 176;
    protected int ySize = 229;
    protected int guiLeft;
    protected int guiTop;
    int currentPage = 0;
    List<ItemStack> stacks = new ArrayList<ItemStack>();
    List<FolderButton> buttons = new ArrayList<FolderButton>();
    private final EntityPlayer player;
    
    public GUIScreenTemplateFolder(EntityPlayer player) {
    	
    	this.player = player;

    	//Stamps
		for(Item i : MachineRecipes.stamps_plate)
			stacks.add(new ItemStack(i));
		for(Item i : MachineRecipes.stamps_wire)
			stacks.add(new ItemStack(i));
		for(Item i : MachineRecipes.stamps_circuit)
			stacks.add(new ItemStack(i));
    	//Fluid IDs
    	for(int i = 1; i < FluidType.values().length; i++)
    		stacks.add(new ItemStack(ModItems.fluid_identifier, 1, i));
    	//Assembly Templates
    	for(int i = 0; i < EnumAssemblyTemplate.values().length; i++)
    		stacks.add(new ItemStack(ModItems.assembly_template, 1, i));
    	//Chemistry Templates
    	for(int i = 0; i < ItemChemistryTemplate.EnumChemistryTemplate.values().length; i++)
    		stacks.add(new ItemStack(ModItems.chemistry_template, 1, i));
    }
    
    int getPageCount() {
    	return (int)Math.ceil(stacks.size() / (5 * 7));
    }
    
    public void updateScreen() {
    	if(currentPage < 0)
    		currentPage = 0;
    	if(currentPage > getPageCount())
    		currentPage = getPageCount();
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
        
        for(int i = currentPage * 35; i < Math.min(currentPage * 35 + 35, stacks.size()); i++) {
    		buttons.add(new FolderButton(guiLeft + 25 + (27 * (i % 5)), guiTop + 26 + (27 * (int)Math.floor((i / 5D))) - currentPage * 27 * 7, stacks.get(i)));
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
				guiLeft + this.xSize / 2 - this.fontRendererObj.getStringWidth(I18n.format((currentPage + 1) + "/" + (getPageCount() + 1))) / 2, guiTop + 10, 4210752);
		
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
		ItemStack stack;
		
		public FolderButton(int x, int y, int t, String i) {
			xPos = x;
			yPos = y;
			type = t;
			info = i;
		}
		
		public FolderButton(int x, int y, ItemStack stack) {
			xPos = x;
			yPos = y;
			type = 0;
			info = stack.getDisplayName();
			this.stack = stack.copy();
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
				if(stack != null) {
					if(stack.getItem() == ModItems.assembly_template)
						itemRender.renderItemAndEffectIntoGUI(fontRendererObj, mc.getTextureManager(), MachineRecipes.getOutputFromTempate(stack), xPos + 1, yPos + 1);
					else if(stack.getItem() == ModItems.chemistry_template)
						itemRender.renderItemAndEffectIntoGUI(fontRendererObj, mc.getTextureManager(), new ItemStack(ModItems.chemistry_icon, 1, stack.getItemDamage()), xPos + 1, yPos + 1);
					else
						itemRender.renderItemAndEffectIntoGUI(fontRendererObj, mc.getTextureManager(), stack, xPos + 1, yPos + 1);
				}
		        GL11.glEnable(GL11.GL_LIGHTING);
			} catch(Exception x) { }
		}
		
		public void drawString(int x, int y) {
			if(info == null || info.isEmpty())
				return;
			
			String s = info;
			if(stack != null)
				if(stack.getItem() instanceof ItemFluidIdentifier)
					s += (": " + I18n.format(FluidType.getEnum(stack.getItemDamage()).getUnlocalizedName()));

			func_146283_a(Arrays.asList(new String[] { s }), x, y);
		}
		
		public void executeAction() {
			mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
			if(type == 0) {
				PacketDispatcher.wrapper.sendToServer(new ItemFolderPacket(stack.copy()));
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

}
