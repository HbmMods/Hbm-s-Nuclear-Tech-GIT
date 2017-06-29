package com.hbm.items.tool;

import java.util.List;

import com.hbm.inventory.MachineRecipes;
import com.hbm.items.ModItems;

import cpw.mods.fml.common.eventhandler.Event.Result;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockColored;
import net.minecraft.block.BlockLog;
import net.minecraft.block.IGrowable;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.FakePlayerFactory;
import net.minecraftforge.event.entity.player.BonemealEvent;

public class ItemAssemblyTemplate extends Item {
	
	public enum EnumAssemblyTemplate {
		
		TEST(0);
		
		private final int value;
		private EnumAssemblyTemplate(int value) {
			this.value = value;
		}
		
		public int getValue() {
			return value;
		}
		
		public static EnumAssemblyTemplate getEnum(int i) {
			return EnumAssemblyTemplate.values()[i];
		}
		
		public String getName() {
			return this.toString();
		}
	}

    public ItemAssemblyTemplate()
    {
        this.setHasSubtypes(true);
        this.setMaxDamage(0);
    }

    public String getUnlocalizedName(ItemStack stack)
    {
        int i = stack.getItemDamage();
        return super.getUnlocalizedName() + "." + EnumAssemblyTemplate.getEnum(i).getName();
    }

    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs tabs, List list)
    {
        for (int i = 0; i < EnumAssemblyTemplate.values().length; ++i)
        {
            list.add(new ItemStack(item, 1, i));
        }
    }
    
    public static int getProcessTime(ItemStack stack) {
    	
    	if(!(stack.getItem() instanceof ItemAssemblyTemplate))
    		return 100;
    	
        int i = stack.getItemDamage();
        EnumAssemblyTemplate enum1 = EnumAssemblyTemplate.getEnum(i);
        
        switch (enum1) {
        case TEST:
        	return 200;
        default:
        	return 100;
        }
    }
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool)
	{
    	
    	if(!(stack.getItem() instanceof ItemAssemblyTemplate))
    		return;

    	List<ItemStack> stacks = MachineRecipes.getRecipeFromTempate(stack);
    	ItemStack out = MachineRecipes.getOutputFromTempate(stack);

    	try {
    		list.add("Output:");
    		list.add(out.stackSize + "x " + out.getDisplayName());
    		list.add("Inputs:");
    	
    		for(int i = 0; i < stacks.size(); i++) {
    			if(stacks.get(i) != null)
    	    		list.add(stacks.get(i).stackSize + "x " + stacks.get(i).getDisplayName());
    		}
    	} catch(Exception e) {
    		list.add("###INVALID###");
    	}
	}

}
