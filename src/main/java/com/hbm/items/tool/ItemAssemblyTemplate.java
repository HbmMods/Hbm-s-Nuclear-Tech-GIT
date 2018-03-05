package com.hbm.items.tool;

import java.util.List;

import com.hbm.inventory.MachineRecipes;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public class ItemAssemblyTemplate extends Item {
	
	public enum EnumAssemblyTemplate {
		
		MIXED_PLATE,
		HAZMAT_CLOTH,
		ASBESTOS_CLOTH,
		COAL_FILTER,
		CENTRIFUGE_ELEMENT,
		CENTRIFUGE_TOWER,
		DEE_MAGNET,
		FLAT_MAGNET,
		CYCLOTRON_TOWER,
		REACTOR_CORE,
		RTG_UNIT,
		HEAT_UNIT,
		GRAVITY_UNIT,
		TITANIUM_DRILL,
		TELEPAD, TELEKIT,
		GEASS_REACTOR,
		GENERATOR_FRONT,
		WT1_GENERIC,
		WT2_GENERIC,
		WT3_GENERIC,
		WT1_FIRE,
		WT2_FIRE,
		WT3_FIRE,
		WT1_CLUSTER,
		WT2_CLUSTER,
		WT3_CLUSTER,
		WT1_BUSTER,
		WT2_BUSTER,
		WT3_BUSTER,
		W_NUCLEAR,
		W_MIRVLET,
		W_MIRV,
		W_ENDOTHERMIC,
		W_EXOTHERMIC,
		T1_TANK,
		T2_TANK,
		T3_TANK,
		T1_THRUSTER,
		T2_THRUSTER,
		T3_THRUSTER,
		CHOPPER_HEAD,
		CHOPPER_GUN,
		CHOPPER_BODY,
		CHOPPER_TAIL,
		CHOPPER_WING,
		CHOPPER_BLADES,
		CIRCUIT_2,
		CIRCUIT_3,
		RTG_PELLET,
		WEAK_PELLET,
		FUSION_PELLET,
		CLUSTER_PELLETS,
		GUN_PELLETS,
		AUSTRALIUM_MACHINE,
		MAGNETRON,
		W_SP,
		W_SHE,
		W_SME,
		W_SLE,
		W_B,
		W_N,
		W_L,
		W_A,
		UPGRADE_TEMPLATE,
		UPGRADE_RED_I,
		UPGRADE_RED_II,
		UPGRADE_RED_III,
		UPGRADE_GREEN_I,
		UPGRADE_GREEN_II,
		UPGRADE_GREEN_III,
		UPGRADE_BLUE_I,
		UPGRADE_BLUE_II,
		UPGRADE_BLUE_III,
		UPGRADE_PURPLE_I,
		UPGRADE_PURPLE_II,
		UPGRADE_PURPLE_III,
		UPGRADE_PINK_I,
		UPGRADE_PINK_II,
		UPGRADE_PINK_III,
		FUSE,
		REDCOIL_CAPACITOR,
		TITANIUM_FILTER,
		LITHIUM_BOX,
		BERYLLIUM_BOX,
		COAL_BOX,
		COPPER_BOX,
		PLUTONIUM_BOX,
		THERMO_ELEMENT,
		LIMITER,
		ANGRY_METAL,
		METEOR_BLOCK,
		CMB_TILE,
		CMB_BRICKS,
		HATCH_FRAME,
		HATCH_CONTROLLER,
		CENTRIFUGE,
		BREEDING_REACTOR,
		RTG_FURNACE,
		DIESEL_GENERATOR,
		NUCLEAR_GENERATOR,
		INDUSTRIAL_GENERATOR,
		CYCLOTRON,
		RT_GENERATOR,
		BATTERY,
		HE_TO_RF,
		RF_TO_HE,
		SHREDDER,
		DERRICK,
		PUMPJACK,
		FLARE_STACK,
		REFINERY,
		CHEMPLANT,
		TANK,
		MINER,
		TURBOFAN,
		TELEPORTER,
		SCHRABTRANS,
		CMB_FURNACE,
		FA_HULL,
		FA_HATCH,
		FA_CORE,
		FA_PORT,
		LR_ELEMENT,
		LR_CONTROL,
		LR_HATCH,
		LR_PORT,
		LR_CORE,
		LF_MAGNET,
		LF_CENTER,
		LF_MOTOR,
		LF_HEATER,
		LF_HATCH,
		LF_CORE,
		LW_ELEMENT,
		LW_CONTROL,
		LW_COOLER,
		LW_STRUTURE,
		LW_HATCH,
		LW_PORT,
		LW_CORE,
		FW_PORT,
		FW_MAGNET,
		FW_COMPUTER,
		FW_CORE,
		GADGET,
		LITTLE_BOY,
		FAT_MAN,
		IVY_MIKE,
		TSAR_BOMB,
		PROTOTYPE,
		FLEIJA,
		CUSTOM_NUKE,
		BOMB_LEV,
		BOMB_ENDO,
		BOMB_EXO,
		LAUNCH_PAD,
		HUNTER_CHOPPER,
		TURRET_LIGHT,
		TURRET_HEAVY,
		TURRET_ROCKET,
		TURRET_FLAMER,
		TURRET_TAU,
		MISSILE_HE_1,
		MISSILE_FIRE_1,
		MISSILE_CLUSTER_1,
		MISSILE_BUSTER_1,
		MISSILE_HE_2,
		MISSILE_FIRE_2,
		MISSILE_CLUSTER_2,
		MISSILE_BUSTER_2,
		MISSILE_HE_3,
		MISSILE_FIRE_3,
		MISSILE_CLUSTER_3,
		MISSILE_BUSTER_3,
		MISSILE_NUCLEAR,
		MISSILE_MIRV,
		MISSILE_ENDO,
		MISSILE_EXO,
		DEFAB,
		MINI_NUKE,
		MINI_MIRV,
		DARK_PLUG,
		COMBINE_BALL,
		GRENADE_FLAME,
		GRENADE_SHRAPNEL,
		GRENAGE_CLUSTER,
		GREANADE_FLARE,
		GRENADE_LIGHTNING,
		GRENADE_IMPULSE,
		GRENADE_PLASMA,
		GRENADE_TAU,
		GRENADE_SCHRABIDIUM,
		GRENADE_NUKE,
		GRENADE_ZOMG,
		GRENADE_BLACK_HOLE,
		POWER_FIST,
		GADGET_PROPELLANT,
		GADGET_WIRING,
		GADGET_CORE,
		BOY_SHIELDING,
		BOY_TARGET,
		BOY_BULLET,
		BOY_PRPELLANT,
		BOY_IGNITER,
		MAN_PROPELLANT,
		MAN_IGNITER,
		MAN_CORE,
		MIKE_TANK,
		MIKE_DEUT,
		MIKE_COOLER,
		FLEIIJA_IGNITER,
		FLEIJA_CORE,
		FLEIJA_PROPELLANT,
		SCHRABIDIUM_HAMMER,
		COMPONENT_LIMITER,
		COMPONENT_EMITTER,
		AMS_LIMITER,
		AMS_EMITTER,
		RADAR;
		
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

    public String getItemStackDisplayName(ItemStack stack)
    {
        String s = ("" + StatCollector.translateToLocal(this.getUnlocalizedName() + ".name")).trim();
        ItemStack out = MachineRecipes.getOutputFromTempate(stack);
        String s1 = ("" + StatCollector.translateToLocal((out != null ? out.getUnlocalizedName() : "") + ".name")).trim();

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
		case MIXED_PLATE:
			return 50;
		case HAZMAT_CLOTH:
			return 50;
		case ASBESTOS_CLOTH:
			return 50;
		case COAL_FILTER:
			return 50;
		case CENTRIFUGE_ELEMENT:
			return 200;
		case CENTRIFUGE_TOWER:
			return 150;
		case DEE_MAGNET:
			return 100;
		case FLAT_MAGNET:
			return 150;
		case CYCLOTRON_TOWER:
			return 300;
		case REACTOR_CORE:
			return 100;
		case RTG_UNIT:
			return 100;
		case HEAT_UNIT:
			return 100;
		case GRAVITY_UNIT:
			return 100;
		case TITANIUM_DRILL:
			return 100;
		case TELEPAD:
			return 300;
		case TELEKIT:
			return 200;
		case GEASS_REACTOR:
			return 200;
		case GENERATOR_FRONT:
			return 200;
		case WT1_GENERIC:
			return 100;
		case WT2_GENERIC:
			return 150;
		case WT3_GENERIC:
			return 200;
		case WT1_FIRE:
			return 100;
		case WT2_FIRE:
			return 150;
		case WT3_FIRE:
			return 200;
		case WT1_CLUSTER:
			return 100;
		case WT2_CLUSTER:
			return 150;
		case WT3_CLUSTER:
			return 200;
		case WT1_BUSTER:
			return 100;
		case WT2_BUSTER:
			return 150;
		case WT3_BUSTER:
			return 200;
		case W_NUCLEAR:
			return 300;
		case W_MIRVLET:
			return 250;
		case W_MIRV:
			return 500;
		case W_ENDOTHERMIC:
			return 300;
		case W_EXOTHERMIC:
			return 300;
		case T1_TANK:
			return 100;
		case T2_TANK:
			return 150;
		case T3_TANK:
			return 200;
		case T1_THRUSTER:
			return 100;
		case T2_THRUSTER:
			return 150;
		case T3_THRUSTER:
			return 200;
		case CHOPPER_HEAD:
			return 300;
		case CHOPPER_GUN:
			return 150;
		case CHOPPER_BODY:
			return 350;
		case CHOPPER_TAIL:
			return 200;
		case CHOPPER_WING:
			return 150;
		case CHOPPER_BLADES:
			return 200;
		case CIRCUIT_2:
			return 100;
		case CIRCUIT_3:
			return 150;
		case RTG_PELLET:
			return 50;
		case WEAK_PELLET:
			return 50;
		case FUSION_PELLET:
			return 150;
		case CLUSTER_PELLETS:
			return 50;
		case GUN_PELLETS:
			return 50;
		case AUSTRALIUM_MACHINE:
			return 150;
		case MAGNETRON:
			return 100;
		case W_SP:
			return 200;
		case W_SHE:
			return 200;
		case W_SME:
			return 200;
		case W_SLE:
			return 200;
		case W_B:
			return 200;
		case W_N:
			return 200;
		case W_L:
			return 200;
		case W_A:
			return 200;
		case UPGRADE_TEMPLATE:
			return 100;
		case UPGRADE_RED_I:
			return 200;
		case UPGRADE_RED_II:
			return 300;
		case UPGRADE_RED_III:
			return 500;
		case UPGRADE_GREEN_I:
			return 200;
		case UPGRADE_GREEN_II:
			return 300;
		case UPGRADE_GREEN_III:
			return 500;
		case UPGRADE_BLUE_I:
			return 200;
		case UPGRADE_BLUE_II:
			return 300;
		case UPGRADE_BLUE_III:
			return 500;
		case UPGRADE_PURPLE_I:
			return 200;
		case UPGRADE_PURPLE_II:
			return 300;
		case UPGRADE_PURPLE_III:
			return 500;
		case UPGRADE_PINK_I:
			return 200;
		case UPGRADE_PINK_II:
			return 300;
		case UPGRADE_PINK_III:
			return 500;
		case FUSE:
			return 100;
		case REDCOIL_CAPACITOR:
			return 200;
		case TITANIUM_FILTER:
			return 200;
		case LITHIUM_BOX:
			return 50;
		case BERYLLIUM_BOX:
			return 50;
		case COAL_BOX:
			return 50;
		case COPPER_BOX:
			return 50;
		case PLUTONIUM_BOX:
			return 50;
		case THERMO_ELEMENT:
			return 150;
		case LIMITER:
			return 150;
		case ANGRY_METAL:
			return 50;
		case METEOR_BLOCK:
			return 500;
		case CMB_TILE:
			return 100;
		case CMB_BRICKS:
			return 200;
		case HATCH_FRAME:
			return 50;
		case HATCH_CONTROLLER:
			return 100;
		case CENTRIFUGE:
			return 250;
		case BREEDING_REACTOR:
			return 150;
		case RTG_FURNACE:
			return 150;
		case DIESEL_GENERATOR:
			return 200;
		case NUCLEAR_GENERATOR:
			return 300;
		case INDUSTRIAL_GENERATOR:
			return 500;
		case CYCLOTRON:
			return 600;
		case RT_GENERATOR:
			return 200;
		case BATTERY:
			return 200;
		case HE_TO_RF:
			return 150;
		case RF_TO_HE:
			return 150;
		case SHREDDER:
			return 200;
		case DERRICK:
			return 250;
		case PUMPJACK:
			return 400;
		case FLARE_STACK:
			return 200;
		case REFINERY:
			return 350;
		case CHEMPLANT:
			return 200;
		case TANK:
			return 150;
		case MINER:
			return 200;
		case TURBOFAN:
			return 500;
		case TELEPORTER:
			return 300;
		case SCHRABTRANS:
			return 500;
		case CMB_FURNACE:
			return 150;
		case FA_HULL:
			return 50;
		case FA_HATCH:
			return 100;
		case FA_CORE:
			return 100;
		case FA_PORT:
			return 50;
		case LR_ELEMENT:
			return 150;
		case LR_CONTROL:
			return 100;
		case LR_HATCH:
			return 150;
		case LR_PORT:
			return 150;
		case LR_CORE:
			return 250;
		case LF_MAGNET:
			return 150;
		case LF_CENTER:
			return 200;
		case LF_MOTOR:
			return 250;
		case LF_HEATER:
			return 150;
		case LF_HATCH:
			return 250;
		case LF_CORE:
			return 350;
		case LW_ELEMENT:
			return 200;
		case LW_CONTROL:
			return 250;
		case LW_COOLER:
			return 300;
		case LW_STRUTURE:
			return 150;
		case LW_HATCH:
			return 200;
		case LW_PORT:
			return 250;
		case LW_CORE:
			return 350;
		case FW_PORT:
			return 250;
		case FW_MAGNET:
			return 250;
		case FW_COMPUTER:
			return 300;
		case FW_CORE:
			return 450;
		case GADGET:
			return 300;
		case LITTLE_BOY:
			return 300;
		case FAT_MAN:
			return 300;
		case IVY_MIKE:
			return 300;
		case TSAR_BOMB:
			return 300;
		case PROTOTYPE:
			return 500;
		case FLEIJA:
			return 400;
		case CUSTOM_NUKE:
			return 300;
		case BOMB_LEV:
			return 250;
		case BOMB_ENDO:
			return 250;
		case BOMB_EXO:
			return 250;
		case LAUNCH_PAD:
			return 250;
		case TURRET_LIGHT:
			return 200;
		case TURRET_HEAVY:
			return 250;
		case TURRET_ROCKET:
			return 300;
		case TURRET_FLAMER:
			return 250;
		case TURRET_TAU:
			return 350;
		case HUNTER_CHOPPER:
			return 300;
		case MISSILE_HE_1:
			return 200;
		case MISSILE_FIRE_1:
			return 200;
		case MISSILE_CLUSTER_1:
			return 200;
		case MISSILE_BUSTER_1:
			return 200;
		case MISSILE_HE_2:
			return 250;
		case MISSILE_FIRE_2:
			return 250;
		case MISSILE_CLUSTER_2:
			return 250;
		case MISSILE_BUSTER_2:
			return 250;
		case MISSILE_HE_3:
			return 350;
		case MISSILE_FIRE_3:
			return 350;
		case MISSILE_CLUSTER_3:
			return 350;
		case MISSILE_BUSTER_3:
			return 350;
		case MISSILE_NUCLEAR:
			return 500;
		case MISSILE_MIRV:
			return 600;
		case MISSILE_ENDO:
			return 350;
		case MISSILE_EXO:
			return 350;
		case DEFAB:
			return 200;
		case MINI_NUKE:
			return 40;
		case MINI_MIRV:
			return 100;
		case DARK_PLUG:
			return 50;
		case COMBINE_BALL:
			return 200;
		case GRENADE_FLAME:
			return 150;
		case GRENADE_SHRAPNEL:
			return 150;
		case GRENAGE_CLUSTER:
			return 200;
		case GREANADE_FLARE:
			return 100;
		case GRENADE_LIGHTNING:
			return 200;
		case GRENADE_IMPULSE:
			return 300;
		case GRENADE_PLASMA:
			return 300;
		case GRENADE_TAU:
			return 300;
		case GRENADE_SCHRABIDIUM:
			return 300;
		case GRENADE_NUKE:
			return 200;
		case GRENADE_ZOMG:
			return 300;
		case GRENADE_BLACK_HOLE:
			return 500;
		case POWER_FIST:
			return 600;
		case GADGET_PROPELLANT:
			return 200;
		case GADGET_WIRING:
			return 100;
		case GADGET_CORE:
			return 200;
		case BOY_SHIELDING:
			return 150;
		case BOY_TARGET:
			return 200;
		case BOY_BULLET:
			return 100;
		case BOY_PRPELLANT:
			return 100;
		case BOY_IGNITER:
			return 150;
		case MAN_PROPELLANT:
			return 200;
		case MAN_IGNITER:
			return 150;
		case MAN_CORE:
			return 250;
		case MIKE_TANK:
			return 300;
		case MIKE_DEUT:
			return 200;
		case MIKE_COOLER:
			return 200;
		case FLEIIJA_IGNITER:
			return 300;
		case FLEIJA_CORE:
			return 500;
		case FLEIJA_PROPELLANT:
			return 400;
		case SCHRABIDIUM_HAMMER:
			return 1000;
		case COMPONENT_LIMITER:
			return 2500;
		case COMPONENT_EMITTER:
			return 2500;
		case AMS_LIMITER:
			return 6000;
		case AMS_EMITTER:
			return 6000;
		case RADAR:
			return 300;
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
    		list.add("Production time:");
        	list.add(Math.floor((float)(getProcessTime(stack)) / 20 * 100) / 100 + " seconds");
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
