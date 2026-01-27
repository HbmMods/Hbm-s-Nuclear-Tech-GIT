package com.hbm.inventory.recipes;

import java.util.ArrayList;
import java.util.List;
import com.hbm.interfaces.Spaghetti;
import com.hbm.inventory.FluidContainer;
import com.hbm.inventory.FluidContainerRegistry;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemBatteryPack.EnumBatteryPack;
import com.hbm.items.machine.ItemBatterySC.EnumBatterySC;
import com.hbm.util.Tuple.Triplet;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.oredict.OreDictionary;

//TODO: clean this shit up
@Spaghetti("i cannot sleep well at night knowing that this class still exists")
public class MachineRecipes {

	public static MachineRecipes instance() {
		return new MachineRecipes();
	}


	public ArrayList<ItemStack> getAlloyFuels() {
		ArrayList<ItemStack> fuels = new ArrayList<ItemStack>();
		fuels.add(new ItemStack(Items.coal));
		fuels.add(new ItemStack(Blocks.coal_block));
		fuels.add(new ItemStack(Items.lava_bucket));
		fuels.add(new ItemStack(Items.blaze_rod));
		fuels.add(new ItemStack(Items.blaze_powder));
		fuels.add(new ItemStack(ModItems.lignite));
		fuels.add(new ItemStack(ModItems.powder_lignite));
		fuels.add(new ItemStack(ModItems.briquette));
		fuels.add(new ItemStack(ModItems.coke));
		fuels.add(new ItemStack(ModItems.solid_fuel));
		fuels.add(new ItemStack(ModItems.powder_coal));
		return fuels;
	}
	
	/*
	 * this is the smoldering crater where once the 2016 shredder recipe code was
	 */

	public ArrayList<ItemStack> getBatteries() {
		ArrayList<ItemStack> fuels = new ArrayList<ItemStack>();
		fuels.add(new ItemStack(ModItems.battery_potato));
		fuels.add(new ItemStack(ModItems.battery_potatos));
		fuels.add(new ItemStack(ModItems.energy_core));
		for(EnumBatteryPack num : EnumBatteryPack.values()) fuels.add(new ItemStack(ModItems.battery_pack, 1, num.ordinal()));
		for(EnumBatterySC num : EnumBatterySC.values()) fuels.add(new ItemStack(ModItems.battery_sc, 1, num.ordinal()));
		fuels.add(new ItemStack(ModItems.battery_creative));
		return fuels;
	}
	
	//Matches Ore Dict Entry
	public static boolean mODE(ItemStack stack, String name) {
		
		int[] ids = OreDictionary.getOreIDs(new ItemStack(stack.getItem(), 1, stack.getItemDamage()));
		
		for(int i = 0; i < ids.length; i++) {
			
			String s = OreDictionary.getOreName(ids[i]);
			
			if(s.length() > 0 && s.equals(name))
				return true;
		}
		
		return false;
	}
	
	public List<Triplet<ItemStack, ItemStack, ItemStack>> getFluidContainers() {
		List<Triplet<ItemStack, ItemStack, ItemStack>> list = new ArrayList();
		
		for(FluidContainer con : FluidContainerRegistry.allContainers) {
			
			if(con != null) {
				
				ItemStack fluid = new ItemStack(ModItems.fluid_icon, 1, con.type.getID());
				fluid.stackTagCompound = new NBTTagCompound();
				fluid.stackTagCompound.setInteger("fill", con.content);
				list.add(new Triplet(fluid, con.emptyContainer, con.fullContainer));
			}
		}
		
		return list;
	}
}
