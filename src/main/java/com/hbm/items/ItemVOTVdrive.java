package com.hbm.items;

import java.util.List;

import javax.sound.midi.VoiceStatus;

import com.hbm.items.machine.ItemZirnoxRod.EnumZirnoxType;
import com.hbm.lib.RefStrings;
import com.hbm.util.EnumUtil;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class ItemVOTVdrive extends ItemEnumMulti {

	private IIcon[] IIcons;
	private IIcon baseIcon;
	
	public ItemVOTVdrive() {
		super(DestinationType.class, true, true);
		this.setMaxStackSize(1);
		this.canRepair = false;
	}
	public enum DestinationType {
		BLANK("blank", 0, true),
	    DUNA("duna", 1, false),
	    MOHO("moho", 2, true),
	    DRES("dres", 2, false),
	    EVE("eve", 1, false), //eve is inherently hard to get to regardless
	    MINMUS("minmus", 0, false),
	    IKE("ike", 1, false),
	    LAYTHE("laythe", 3, false),
	    TEKTO("tekto", 3, false),
	    MUN("mun", 0, true); //lvl 0 drives are inherently processed, no need for them to be otherwise
	    private String destination;
	    private int processingLevel; // for the te 
	    private boolean isprocessed; // the level will stay the same, trick is is that the te will match the level and pretend to "process it" which is a fancy way of setting this to true.

	    DestinationType(String destination, int processingLevel, boolean b) {
	        this.destination = destination;
	        this.processingLevel = processingLevel;
	        this.isprocessed = b;
	    }

	    public String getDestination() {
	        return destination;
	    }

	    public int getProcessingLevel() {
	        return processingLevel;
	    }
	    public boolean isProcessed() {
	        return isprocessed;
	    }
	}
    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {
        super.addInformation(stack, player, list, bool);
        
        // Check if the item has an NBT tag compound
        if (stack.hasTagCompound()) {
            boolean pr = stack.getTagCompound().getBoolean("Processed");
            int metadata = stack.getItemDamage();
            DestinationType destinationType = DestinationType.values()[metadata];
            int processingLevel = destinationType.getProcessingLevel();
            
            if(destinationType.isprocessed == true) {
            	list.add("Destination: " + destinationType);
            	return;
            }
            if (!isProcessed(stack)) {
                // Display processing level info if not processed
                list.add("Process Requirement: Level " + processingLevel);
                list.add(EnumChatFormatting.GOLD + "Needs Processing!");
               
            } else {
                // Display destination info if processed
                list.add("Destination: " + destinationType); // Or use a mapping from processing level to destination
                list.add(EnumChatFormatting.GREEN + "Processed!");
                
            }
        } else {
            int metadata = stack.getItemDamage();
            DestinationType destinationType = DestinationType.values()[metadata];
            int processingLevel = getPlvl(stack);
            list.add("Process Requirement: Level " + processingLevel);
        }
        
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister) {
        IIcons = new IIcon[4]; // Four unique icons for each processing level

        for (int i = 0; i < IIcons.length; i++) { // Change the loop to start from 0
            IIcons[i] = iconRegister.registerIcon(RefStrings.MODID + ":votv_f" + i);
        }

        baseIcon = iconRegister.registerIcon(RefStrings.MODID + ":votv_f0"); // Base icon for unprocessed drives
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int metadata) {
        ItemStack stack = new ItemStack(this, 1, metadata);

        if (stack.hasTagCompound() && stack.stackTagCompound.getBoolean("Processed") != true) {
        	return baseIcon;
        }else {
            DestinationType[] values = DestinationType.values();
            if (metadata >= 0 && metadata < values.length) {
                int processingLevel = values[metadata].getProcessingLevel();
                if (processingLevel >= 0 && processingLevel <= IIcons.length) {
                    return IIcons[processingLevel]; // Subtract 1 to match array indexing
                }
            }
        }
        // Default to the base icon for unprocessed drives
        return baseIcon;
    }

	public static boolean isProcessed(ItemStack stack) {
		
		if(!stack.hasTagCompound()) {
			stack.stackTagCompound = new NBTTagCompound();
			return true;
		}
		
		return stack.stackTagCompound.getBoolean("Processed");
	}
	public static void setProcessed(ItemStack stack) {
		
		if(!stack.hasTagCompound())
			stack.stackTagCompound = new NBTTagCompound();
		
		stack.stackTagCompound.setBoolean("Processed", false);
	}
    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        int metadata = stack.getItemDamage();
        DestinationType destinationType = DestinationType.values()[metadata];

        // Now you can work with destinationType
            if(!isProcessed(stack)) {
        		stack.stackTagCompound.setBoolean("Processed", true);
            }
            System.out.println("DUNA processing: true");
    
        return stack;

    }
	public static int getPlvl(ItemStack stack) {
		
		if(!stack.hasTagCompound()) {
			stack.stackTagCompound = new NBTTagCompound();
			return 0;
		}
		
		return stack.stackTagCompound.getInteger("plvl");
	}
	public static void setPlvl(ItemStack stack, int lvl) {
	    DestinationType destinationType = DestinationType.values()[stack.getItemDamage()]; // Get the 
	    lvl = destinationType.getProcessingLevel();
		if(!stack.hasTagCompound())
			stack.stackTagCompound = new NBTTagCompound();
		
		stack.stackTagCompound.setInteger("plvl", lvl);
	}
	


}
