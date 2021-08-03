package com.hbm.items.machine;

import java.util.List;

import com.hbm.inventory.FluidStack;
import com.hbm.inventory.MachineRecipes;
import com.hbm.items.ModItems;
import com.hbm.util.I18nUtil;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;

public class ItemChemistryTemplate extends Item {
	
	public enum EnumChemistryTemplate {
		
		//FP - Fuel Processing
		//FR - Fuel Reprocessing
		//FC - Fuel Cracking
		//CC - Coal Cracking
		//SF - Solid Fuel Production
		//BP - Biofuel Production
		
		FP_HEAVYOIL,
		FP_SMEAR,
		FP_NAPHTHA,
		FP_LIGHTOIL,
		FR_REOIL,
		FR_PETROIL,
		FC_BITUMEN,
		FC_I_NAPHTHA,
		FC_GAS_PETROLEUM,
		FC_DIESEL_KEROSENE,
		FC_KEROSENE_PETROLEUM,
		CC_OIL,
		CC_I,
		CC_HEATING,
		CC_HEAVY,
		CC_NAPHTHA,
		SF_OIL,
		SF_HEAVYOIL,
		SF_SMEAR,
		SF_HEATINGOIL,
		SF_RECLAIMED,
		SF_PETROIL,
		SF_LUBRICANT,
		SF_NAPHTHA,
		SF_DIESEL,
		SF_LIGHTOIL,
		SF_KEROSENE,
		SF_GAS,
		SF_PETROLEUM,
		SF_BIOGAS,
		SF_BIOFUEL,
		BP_BIOGAS,
		BP_BIOFUEL,
		OIL_SAND,
		ASPHALT,
		COOLANT,
		CRYOGEL,
		DESH,
		NITAN,
		PEROXIDE,
		CIRCUIT_4,
		CIRCUIT_5,
		POLYMER,
		DEUTERIUM,
		STEAM,
		YELLOWCAKE,
		UF6,
		PUF6,
		SAS3,
		DYN_SCHRAB,
		DYN_EUPH,
		DYN_DNT,
		CORDITE,
		KEVLAR,
		CONCRETE,
		CONCRETE_ASBESTOS,
		SOLID_FUEL,
		ELECTROLYSIS,
		XENON,
		XENON_OXY,
		SATURN,
		BALEFIRE,
		SCHRABIDIC,
		SCHRABIDATE,
		COLTAN_CLEANING,
		COLTAN_PAIN,
		COLTAN_CRYSTAL;
		
		public static EnumChemistryTemplate getEnum(int i) {
			if(i < EnumChemistryTemplate.values().length)
				return EnumChemistryTemplate.values()[i];
			else
				return FP_HEAVYOIL;
		}
		
		public String getName() {
			return this.toString();
		}
	}

    public ItemChemistryTemplate()
    {
        this.setHasSubtypes(true);
        this.setMaxDamage(0);
    }

    public String getItemStackDisplayName(ItemStack stack)
    {
        String s = ("" + StatCollector.translateToLocal(this.getUnlocalizedName() + ".name")).trim();
        String s1 = ("" + StatCollector.translateToLocal("chem." + EnumChemistryTemplate.getEnum(stack.getItemDamage()).name())).trim();

        if (s1 != null)
        {
            s = s + " " + s1;
        }

        return s;
    }

    @Override
	@SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs tabs, List list)
    {
        for (int i = 0; i < EnumChemistryTemplate.values().length; ++i)
        {
            list.add(new ItemStack(item, 1, i));
        }
    }
    
    public static int getProcessTime(ItemStack stack) {
    	
    	if(!(stack.getItem() instanceof ItemChemistryTemplate))
    		return 100;
    	
        int i = stack.getItemDamage();
        EnumChemistryTemplate enum1 = EnumChemistryTemplate.getEnum(i);
        
        switch (enum1) {
        case FP_HEAVYOIL:
        	return 50;
        case FP_SMEAR:
        	return 50;
        case FP_NAPHTHA:
        	return 50;
        case FP_LIGHTOIL:
        	return 50;
        case FR_REOIL:
        	return 30;
        case FR_PETROIL:
        	return 30;
        case OIL_SAND:
        	return 200;
        case FC_BITUMEN:
        	return 100;
        case FC_I_NAPHTHA:
        	return 150;
        case FC_GAS_PETROLEUM:
        	return 100;
        case FC_DIESEL_KEROSENE:
        	return 150;
        case FC_KEROSENE_PETROLEUM:
        	return 150;
        case CC_OIL:
        	return 150;
        case CC_I:
        	return 200;
        case CC_HEATING:
        	return 250;
        case CC_HEAVY:
        	return 200;
        case CC_NAPHTHA:
        	return 300;
        case ASPHALT:
        	return 100;
        case COOLANT:
        	return 50;
        case CRYOGEL:
        	return 50;
        case DESH:
        	return 300;
        case NITAN:
        	return 50;
        case PEROXIDE:
        	return 50;
        case CIRCUIT_4:
        	return 200;
        case CIRCUIT_5:
        	return 250;
        case SF_OIL:
        	return 20;
        case SF_HEAVYOIL:
        	return 20;
        case SF_SMEAR:
        	return 20;
        case SF_HEATINGOIL:
        	return 20;
        case SF_RECLAIMED:
        	return 20;
        case SF_PETROIL:
        	return 20;
    	case SF_LUBRICANT:
        	return 20;
    	case SF_NAPHTHA:
        	return 20;
    	case SF_DIESEL:
        	return 20;
    	case SF_LIGHTOIL:
        	return 20;
    	case SF_KEROSENE:
        	return 20;
    	case SF_GAS:
        	return 20;
    	case SF_PETROLEUM:
        	return 20;
    	case SF_BIOGAS:
        	return 20;
    	case SF_BIOFUEL:
        	return 20;
        case POLYMER:
        	return 100;
        case DEUTERIUM:
        	return 200;
        case STEAM:
        	return 20;
        case BP_BIOGAS:
        	return 200;
        case BP_BIOFUEL:
        	return 100;
        case YELLOWCAKE:
        	return 250;
        case UF6:
        	return 100;
        case PUF6:
        	return 150;
        case SAS3:
        	return 200;
        case DYN_SCHRAB:
        	return 1*60*20;
        case DYN_EUPH:
        	return 3*60*20;
        case DYN_DNT:
        	return 5*60*20;
        case CORDITE:
        	return 40;
        case KEVLAR:
        	return 40;
        case CONCRETE:
        	return 100;
        case CONCRETE_ASBESTOS:
        	return 100;
        case SOLID_FUEL:
        	return 200;
        case ELECTROLYSIS:
        	return 150;
        case XENON:
        	return 300;
        case XENON_OXY:
        	return 20;
        case SATURN:
        	return 60;
        case BALEFIRE:
        	return 100;
        case SCHRABIDIC:
        	return 100;
        case SCHRABIDATE:
        	return 150;
        case COLTAN_CLEANING:
        	return 60;
        case COLTAN_PAIN:
        	return 120;
        case COLTAN_CRYSTAL:
        	return 80;
        default:
        	return 100;
        }
    }
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool)
	{
    	
    	if(!(stack.getItem() instanceof ItemChemistryTemplate))
    		return;

    	List<ItemStack> stacks = MachineRecipes.getChemInputFromTempate(stack);
    	FluidStack[] inF = MachineRecipes.getFluidInputFromTempate(stack);
    	ItemStack[] out = MachineRecipes.getChemOutputFromTempate(stack);
    	FluidStack[] outF = MachineRecipes.getFluidOutputFromTempate(stack);
    	
		list.add(EnumChatFormatting.YELLOW + I18nUtil.resolveKey("info.templatefolder", I18nUtil.resolveKey(ModItems.template_folder.getUnlocalizedName() + ".name")));
		list.add("");

    	try {
    		list.add(EnumChatFormatting.BOLD + I18nUtil.resolveKey("info.template_out_p"));
    		for(int i = 0; i < 4; i++)
    			if(out[i] != null)
    				list.add(out[i].stackSize + "x " + out[i].getDisplayName());
    		
    		for(int i = 0; i < 2; i++)
    			if(outF[i] != null)
    				list.add(outF[i].fill + "mB " + I18n.format(outF[i].type.getUnlocalizedName()));
    		
    		list.add(EnumChatFormatting.BOLD + I18nUtil.resolveKey("info.template_in_p"));
    		
    		if(stacks != null)
    			for(int i = 0; i < stacks.size(); i++)
    				list.add(stacks.get(i).stackSize + "x " + stacks.get(i).getDisplayName());
    		
    		for(int i = 0; i < 2; i++)
    			if(inF[i] != null)
    				list.add(inF[i].fill + "mB " + I18n.format(inF[i].type.getUnlocalizedName()));
    		
    		list.add(EnumChatFormatting.BOLD + I18nUtil.resolveKey("info.template_time"));
        	list.add(Math.floor((float)(getProcessTime(stack)) / 20 * 100) / 100 + " " + I18nUtil.resolveKey("info.template_seconds"));
    	} catch(Exception e) {
    		list.add("###INVALID###");
    		list.add("0x334077-0x6A298F-0xDF3795-0x334077");
    	}
	}

}
