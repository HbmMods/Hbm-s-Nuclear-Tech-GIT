package com.hbm.items;

import java.util.List;

import com.hbm.dim.SolarSystem;
import com.hbm.lib.RefStrings;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class ItemVOTVdrive extends ItemEnumMulti {

	private IIcon[] IIcons;
	private IIcon baseIcon;
	
	public ItemVOTVdrive() {
		super(SolarSystem.Body.class, true, true);
		this.setMaxStackSize(1);
		this.canRepair = false;
	}

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {
        super.addInformation(stack, player, list, bool);

		int metadata = stack.getItemDamage();
		SolarSystem.Body destinationType = SolarSystem.Body.values()[metadata];

		if(destinationType == SolarSystem.Body.BLANK) {
			list.add("Destination: DRIVE IS BLANK");
			return;
		}

		int processingLevel = destinationType.getProcessingLevel();

		list.add("Destination: " + destinationType);

		if (!isProcessed(stack)) {
			// Display processing level info if not processed
			list.add("Process Requirement: Level " + processingLevel);
			list.add(EnumChatFormatting.GOLD + "Needs Processing!");
		} else {
			// Display destination info if processed
			list.add(EnumChatFormatting.GREEN + "Processed!");
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
		SolarSystem.Body destinationType = SolarSystem.Body.values()[metadata];

		if(destinationType == SolarSystem.Body.BLANK)
			return baseIcon;

		int processingLevel = destinationType.getProcessingLevel();
		if (processingLevel >= 0 && processingLevel <= IIcons.length) {
			return IIcons[processingLevel]; // Subtract 1 to match array indexing
		}

        // Default to the base icon for unprocessed drives
        return baseIcon;
    }

	public SolarSystem.Body getDestination(ItemStack stack) {
		int metadata = stack.getItemDamage();
		return SolarSystem.Body.values()[metadata];
	}

	public static boolean isProcessed(ItemStack stack) {
		if(!stack.hasTagCompound())
			stack.stackTagCompound = new NBTTagCompound();
		
		return stack.stackTagCompound.getBoolean("Processed");
	}

	public static void setProcessed(ItemStack stack, boolean processed) {
		if(!stack.hasTagCompound())
			stack.stackTagCompound = new NBTTagCompound();
		
		stack.stackTagCompound.setBoolean("Processed", processed);
	}

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        // Now you can work with destinationType
		if(!isProcessed(stack)) {
			setProcessed(stack, true);
		}
    
        return stack;
    }

}
