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
		
		TEST, MIXED_PLATE, HAZMAT_CLOTH, ASBESTOS_CLOTH, COAL_FILTER, CENTRIFUGE_ELEMENT, CENTRIFUGE_TOWER,
		DEE_MAGNET, FLAT_MAGNET, CYCLOTRON_TOWER, REACTOR_CORE, RTG_UNIT, HEAT_UNIT, GRAVITY_UNIT,
		TITANIUM_DRILL, TELEPAD, TELEKIT, GEASS_REACTOR, GENERATOR_FRONT, WT1_GENERIC, WT2_GENERIC,
		WT3_GENERIC, WT1_FIRE, WT2_FIRE, WT3_FIRE, WT1_CLUSTER, WT2_CLUSTER, WT3_CLUSTER, WT1_BUSTER,
		WT2_BUSTER, WT3_BUSTER, W_NUCLEAR, W_MIRVLET, W_MIRV, W_ENDOTHERMIC, W_EXOTHERMIC, T1_TANK, T2_TANK,
		T3_TANK, T1_THRUSTER, T2_THRUSTER, T3_THRUSTER, CHOPPER_HEAD, CHOPPER_GUN, CHOPPER_BODY,
		CHOPPER_TAIL, CHOPPER_WING, CHOPPER_BLADES, CIRCUIT_2, CIRCUIT_3, RTG_PELLET, WEAK_PELLET,
		FUSION_PELLET, CLUSTER_PELLETS, GUN_PELLETS, AUSTRALIUM_MACHINE, MAGNETRON, W_SP, W_SHE, W_SME,
		W_SLE, W_B, W_N, W_L, W_A, UPGRADE_TEMPLATE, UPGRADE_RED_I, UPGRADE_RED_II, UPGRADE_RED_III,
		UPGRADE_GREEN_I, UPGRADE_GREEN_II, UPGRADE_GREEN_III, UPGRADE_BLUE_I, UPGRADE_BLUE_II,
		UPGRADE_BLUE_III, UPGRADE_PURPLE_I, UPGRADE_PURPLE_II, UPGRADE_PURPLE_III, FUSE, REDCOIL_CAPACITOR,
		TITANIUM_FILTER, LITHIUM_BOX, BERYLLIUM_BOX, COAL_BOX, COPPER_BOX, PLUTONIUM_BOX, THERMO_ELEMENT,
		LIMITER, ANGRY_METAL, CMB_TILE, CMB_BRICKS, HATCH_FRAME, HATCH_CONTROLLER, CENTRIFUGE,
		BREEDING_REACTOR, RTG_FURNACE, DIESEL_GENERATOR, NUCLEAR_GENERATOR, INDUSTRIAL_GENERATOR, CYCLOTRON,
		RT_GENERATOR, BATTERY, HE_TO_RF, RF_TO_HE, SHREDDER, DEUTERIUM_EXTRACTOR, DERRICK, FLARE_STACK,
		REFINERY;
		
		//private final int value;
		//private EnumAssemblyTemplate(int value) {
		//	this.value = value;
		//}
		
		//public int getValue() {
		//	return value;
		//}
		
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
    		list.add("0x334077-0x6A298F-0xDF3795-0x334077");
    	}
	}
	
	/*public Motif getColorMotifFromTemplate(EnumAssemblyTemplate temp) {
		
		//using deprecated value operator, will remove soon
		if(temp.getValue() > 0) {
			Motif scheme = new Motif(temp.getValue, null);
			scheme.setTextureSize(16, 16);
			//scheme.applyUniversalScheme();
			scheme.colorCount = 4;
			//universal scheme configuration for testing
			//TODO: get textures properly baked, display color for shield
			scheme.addColor(0x334077);
			scheme.addColor(0x6A298F);
			scheme.addColor(0xDF3795);
			scheme.addColor(0x334077);
			
			//different test config; prpl, lprpl, cyn, prpl
			
			scheme.unify();
			return scheme;
			
		} else {
			//return null;
			return Motif.defaultInstance;
		}
	}*/

}
