package com.hbm.inventory.recipes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.hbm.blocks.ModBlocks;
import com.hbm.interfaces.Spaghetti;
import com.hbm.inventory.FluidContainer;
import com.hbm.inventory.FluidContainerRegistry;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.items.ModItems;
import com.hbm.items.special.ItemHot;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.oredict.OreDictionary;

//TODO: clean this shit up
@Spaghetti("everything")
public class MachineRecipes
{
	private static final HashMap<ComparableStack, ItemStack> arcFurnaceRecipes = new HashMap<ComparableStack, ItemStack>();
	
	public static ItemStack getArcFurnaceResult(ItemStack stackIn)
	{
		if (stackIn != null)
		{
			ComparableStack comp = new ComparableStack(stackIn.copy()).makeSingular();
			if (arcFurnaceRecipes.containsKey(comp))
				return arcFurnaceRecipes.get(comp).copy();
		}
		return null;
	}

	
	public MachineRecipes() {

	}

	public static MachineRecipes instance() {
		return new MachineRecipes();
	}
	
	//return: Fluids, amount produced, amount required, heat required (°C * 100)
	public static Object[] getBoilerOutput(FluidType type) {
		
		if(type == Fluids.WATER) return new Object[] { Fluids.STEAM, 500, 5, 10000 };
		if(type == Fluids.STEAM) return new Object[] { Fluids.HOTSTEAM, 5, 50, 30000 };
		if(type == Fluids.HOTSTEAM) return new Object[] { Fluids.SUPERHOTSTEAM, 5, 50, 45000 };
		if(type == Fluids.OIL) return new Object[] { Fluids.HOTOIL, 5, 5, 35000 };
		if(type == Fluids.CRACKOIL) return new Object[] { Fluids.HOTCRACKOIL, 5, 5, 35000 };
		
		return null;
	}
	
	//return: Fluids, amount produced, amount required, HE produced
	public static Object[] getTurbineOutput(FluidType type) {
		
		if(type == Fluids.STEAM) return new Object[] { Fluids.SPENTSTEAM, 5, 500, 50 };
		if(type == Fluids.HOTSTEAM) return new Object[] { Fluids.STEAM, 50, 5, 100 };
		if(type == Fluids.SUPERHOTSTEAM) return new Object[] { Fluids.HOTSTEAM, 50, 5, 150 };
		if(type == Fluids.ULTRAHOTSTEAM) return new Object[] { Fluids.SUPERHOTSTEAM, 50, 5, 250 };
		
		return null;
	}

	public static ArrayList<ItemStack> getAlloyFuels() {
		ArrayList<ItemStack> fuels = new ArrayList<ItemStack>();
		fuels.add(new ItemStack(Items.coal));
		fuels.add(new ItemStack(Blocks.coal_block));
		fuels.add(new ItemStack(Items.lava_bucket));
		fuels.add(new ItemStack(Items.blaze_rod));
		fuels.add(new ItemStack(Items.blaze_powder));
		fuels.add(new ItemStack(ModItems.lignite));
		fuels.add(new ItemStack(ModItems.powder_lignite));
		fuels.add(new ItemStack(ModItems.briquette_lignite));
		fuels.add(new ItemStack(ModItems.coke));
		fuels.add(new ItemStack(ModItems.solid_fuel));
		fuels.add(new ItemStack(ModItems.powder_coal));
		return fuels;
	}

	public static ArrayList<ItemStack> getCentrifugeFuels() {
		ArrayList<ItemStack> fuels = new ArrayList<ItemStack>();
		fuels.add(new ItemStack(Items.coal));
		fuels.add(new ItemStack(Item.getItemFromBlock(Blocks.coal_block)));
		fuels.add(new ItemStack(Items.lava_bucket));
		fuels.add(new ItemStack(Items.redstone));
		fuels.add(new ItemStack(Item.getItemFromBlock(Blocks.redstone_block)));
		fuels.add(new ItemStack(Item.getItemFromBlock(Blocks.netherrack)));
		fuels.add(new ItemStack(Items.blaze_rod));
		fuels.add(new ItemStack(Items.blaze_powder));
		return fuels;
	}
	
	//keep this
	//like in a museum or something
	//this is a testament of my incompetence
	//look at it
	//look at how horrifying it is
	//children, never do this
	/*public class ShredderRecipe {

		public ItemStack input;
		public ItemStack output;

		public void registerEverythingImSrs() {
			
			String[] names = OreDictionary.getOreNames();
			List<ItemStack> stacks = new ArrayList<ItemStack>();
			
			for(int i = 0; i < names.length; i++) {
				stacks.addAll(OreDictionary.getOres(names[i]));
			}
			
			for(int i = 0; i < stacks.size(); i++) {
				
				int[] ids = OreDictionary.getOreIDs(stacks.get(i));

				List<String> oreNames = new ArrayList<String>();
				
				for(int j = 0; j < ids.length; j++) {
					oreNames.add(OreDictionary.getOreName(ids[j]));
				}
				
				theWholeThing.add(new DictCouple(stacks.get(i), oreNames));
			}
			
			MainRegistry.logger.info("Added " + theWholeThing.size() + " elements from the Ore Dict!");
		}
		
		public boolean doesExist(ItemStack stack) {
			
			for(DictCouple dic : theWholeThing) {
				if(dic.item.getItem() == stack.getItem() && dic.item.getItemDamage() == stack.getItemDamage())
					return true;
			}
			
			return false;
		}

		public void addRecipes() {

			// Not very efficient, I know, but at least it works AND it's
			// somewhat smart!
			
			for(int i = 0; i < theWholeThing.size(); i++)
			{
				for(int j = 0; j < theWholeThing.get(i).list.size(); j++)
				{
					String s = theWholeThing.get(i).list.get(j);
					
					if (s.length() > 5 && s.substring(0, 5).equals("ingot")) {
						ItemStack stack = canFindDustByName(s.substring(5));
						if (stack != null) {
							setRecipe(theWholeThing.get(i).item, stack);
						} else {
							setRecipe(theWholeThing.get(i).item, new ItemStack(ModItems.scrap));
						}
					} else if (s.length() > 3 && s.substring(0, 3).equals("ore")) {
						ItemStack stack = canFindDustByName(s.substring(3));
						if (stack != null) {
							setRecipe(theWholeThing.get(i).item, new ItemStack(stack.getItem(), 2, stack.getItemDamage()));
						} else {
							setRecipe(theWholeThing.get(i).item, new ItemStack(ModItems.scrap));
						}
					} else if (s.length() > 5 && s.substring(0, 5).equals("block")) {
						ItemStack stack = canFindDustByName(s.substring(5));
						if (stack != null) {
							setRecipe(theWholeThing.get(i).item, new ItemStack(stack.getItem(), 9, stack.getItemDamage()));
						} else {
							setRecipe(theWholeThing.get(i).item, new ItemStack(ModItems.scrap));
						}
					} else if (s.length() > 3 && s.substring(0, 3).equals("gem")) {
						ItemStack stack = canFindDustByName(s.substring(3));
						if (stack != null) {
							setRecipe(theWholeThing.get(i).item, new ItemStack(stack.getItem(), 1, stack.getItemDamage()));
						} else {
							setRecipe(theWholeThing.get(i).item, new ItemStack(ModItems.scrap));
						}
					} else if (s.length() > 4 && s.substring(0, 4).equals("dust")) {
						setRecipe(theWholeThing.get(i).item, new ItemStack(ModItems.dust));
					} else if (s.length() > 6 && s.substring(0, 6).equals("powder")) {
						setRecipe(theWholeThing.get(i).item, new ItemStack(ModItems.dust));
					} else {
						setRecipe(theWholeThing.get(i).item, new ItemStack(ModItems.scrap));
					}
				}

				if(theWholeThing.get(i).list.isEmpty())
					setRecipe(theWholeThing.get(i).item, new ItemStack(ModItems.scrap));
				if(!theWholeThing.get(i).list.isEmpty() && theWholeThing.get(i).list.get(0).equals("Unknown"))
					setRecipe(theWholeThing.get(i).item, new ItemStack(ModItems.scrap));
			}

			MainRegistry.logger.info("Added " + recipesShredder.size() + " in total.");
			MainRegistry.logger.info("Added " + dustCount + " ore dust recipes.");
		}
		
		public ItemStack canFindDustByName(String s) {
			
			for(DictCouple d : theWholeThing)
			{
				for(String s1 : d.list)
				{
					if(s1.length() > 4 && s1.substring(0, 4).equals("dust") && s1.substring(4).equals(s))
					{
						dustCount++;
						return d.item;
					}
				}
			}
			
			return null;
		}
		
		public void setRecipe(ItemStack inp, ItemStack outp) {
			ShredderRecipe recipe = new ShredderRecipe();
			
			recipe.input = inp;
			recipe.output = outp;
			
			recipesShredder.add(recipe);
		}
		
		public void overridePreSetRecipe(ItemStack inp, ItemStack outp) {
			
			boolean flag = false;
			
			for(int i = 0; i < recipesShredder.size(); i++)
			{
				if(recipesShredder.get(i) != null && 
						recipesShredder.get(i).input != null && 
						recipesShredder.get(i).output != null && 
						inp != null && 
						outp != null && 
						recipesShredder.get(i).input.getItem() == inp.getItem() && 
						recipesShredder.get(i).input.getItemDamage() == inp.getItemDamage()) {
					recipesShredder.get(i).output = outp;
					flag = true;
				}
			}
			
			if(!flag) {
				ShredderRecipe rec = new ShredderRecipe();
				rec.input = inp;
				rec.output = outp;
				recipesShredder.add(rec);
			}
		}
		
		public void removeDuplicates() {
			List<ShredderRecipe> newList = new ArrayList<ShredderRecipe>();
			
			for(ShredderRecipe piv : recipesShredder)
			{
				boolean flag = false;
				
				if(newList.size() == 0)
				{
					newList.add(piv);
				} else {
					for(ShredderRecipe rec : newList) {
						if(piv != null && rec != null && piv.input != null && rec.input != null && rec.input.getItem() != null && piv.input.getItem() != null && rec.input.getItemDamage() == piv.input.getItemDamage() && rec.input.getItem() == piv.input.getItem())
							flag = true;
						if(piv == null || rec == null || piv.input == null || rec.input == null)
							flag = true;
					}
				}
				
				if(!flag)
				{
					newList.add(piv);
				}
			}
		}
		
		public void PrintRecipes() {

			MainRegistry.logger.debug("TWT: " + theWholeThing.size() + ", REC: " + recipesShredder.size());
		}
	}

	public static class DictCouple {
		
		public ItemStack item;
		public List<String> list;

		public DictCouple(ItemStack item, List<String> list) {
			this.item = item;
			this.list = list;
		}
		
		public static List<String> findWithStack(ItemStack stack) {
			for(DictCouple couple : theWholeThing) {
				if(couple.item == stack);
					return couple.list;
			}
			
			return null;
		}
	}

	public static List<ShredderRecipe> recipesShredder = new ArrayList<ShredderRecipe>();
	public static List<DictCouple> theWholeThing = new ArrayList<DictCouple>();
	public static int dustCount = 0;
	
	public static ItemStack getShredderResult(ItemStack stack) {
		for(ShredderRecipe rec : recipesShredder)
		{
			if(stack != null && 
					rec.input.getItem() == stack.getItem() && 
					rec.input.getItemDamage() == stack.getItemDamage())
				return rec.output.copy();
		}
		
		return new ItemStack(ModItems.scrap);
	}
	
	public Map<Object, Object> getShredderRecipes() {
		Map<Object, Object> recipes = new HashMap<Object, Object>();
		
		for(int i = 0; i < MachineRecipes.recipesShredder.size(); i++) {
			if(MachineRecipes.recipesShredder.get(i) != null && MachineRecipes.recipesShredder.get(i).output.getItem() != ModItems.scrap)
				recipes.put(MachineRecipes.recipesShredder.get(i).input, getShredderResult(MachineRecipes.recipesShredder.get(i).input));
		}
		
		return recipes;
	}*/

	public static Map<Object[], Object> getCMBRecipes() {
		Map<Object[], Object> recipes = new HashMap<Object[], Object>();
		recipes.put(new ItemStack[] { new ItemStack(ModItems.ingot_advanced_alloy), new ItemStack(ModItems.ingot_magnetized_tungsten) },
				new ItemStack(ModItems.ingot_combine_steel, 4));
		recipes.put(new ItemStack[] { new ItemStack(ModItems.powder_advanced_alloy), new ItemStack(ModItems.powder_magnetized_tungsten) },
				new ItemStack(ModItems.ingot_combine_steel, 4));
		return recipes;
	}

	public static List<ItemStack> getBatteries() {
		ArrayList<ItemStack> fuels = new ArrayList<ItemStack>();
		fuels.add(new ItemStack(ModItems.battery_potato));
		fuels.add(new ItemStack(ModItems.battery_potatos));
		fuels.add(new ItemStack(ModItems.battery_su));
		fuels.add(new ItemStack(ModItems.battery_su_l));
		fuels.add(new ItemStack(ModItems.battery_generic));
		fuels.add(new ItemStack(ModItems.battery_red_cell));
		fuels.add(new ItemStack(ModItems.battery_red_cell_6));
		fuels.add(new ItemStack(ModItems.battery_red_cell_24));
		fuels.add(new ItemStack(ModItems.battery_advanced));
		fuels.add(new ItemStack(ModItems.battery_advanced_cell));
		fuels.add(new ItemStack(ModItems.battery_advanced_cell_4));
		fuels.add(new ItemStack(ModItems.battery_advanced_cell_12));
		fuels.add(new ItemStack(ModItems.battery_lithium));
		fuels.add(new ItemStack(ModItems.battery_lithium_cell));
		fuels.add(new ItemStack(ModItems.battery_lithium_cell_3));
		fuels.add(new ItemStack(ModItems.battery_lithium_cell_6));
		fuels.add(new ItemStack(ModItems.battery_schrabidium));
		fuels.add(new ItemStack(ModItems.battery_schrabidium_cell));
		fuels.add(new ItemStack(ModItems.battery_schrabidium_cell_2));
		fuels.add(new ItemStack(ModItems.battery_schrabidium_cell_4));
		fuels.add(new ItemStack(ModItems.battery_trixite));
		fuels.add(new ItemStack(ModItems.battery_spark));
		fuels.add(new ItemStack(ModItems.battery_spark_cell_6));
		fuels.add(new ItemStack(ModItems.battery_spark_cell_25));
		fuels.add(new ItemStack(ModItems.battery_spark_cell_100));
		fuels.add(new ItemStack(ModItems.battery_spark_cell_1000));
		fuels.add(new ItemStack(ModItems.battery_spark_cell_10000));
		fuels.add(new ItemStack(ModItems.battery_spark_cell_power));
		fuels.add(new ItemStack(ModItems.fusion_core));
		fuels.add(new ItemStack(ModItems.energy_core));
		return fuels;
	}

	public static List<ItemStack> getBlades() {
		ArrayList<ItemStack> fuels = new ArrayList<ItemStack>();
		fuels.add(new ItemStack(ModItems.blades_advanced_alloy));
		fuels.add(new ItemStack(ModItems.blades_aluminium));
		fuels.add(new ItemStack(ModItems.blades_combine_steel));
		fuels.add(new ItemStack(ModItems.blades_gold));
		fuels.add(new ItemStack(ModItems.blades_iron));
		fuels.add(new ItemStack(ModItems.blades_steel));
		fuels.add(new ItemStack(ModItems.blades_titanium));
		fuels.add(new ItemStack(ModItems.blades_schrabidium));
		fuels.add(new ItemStack(ModItems.blades_desh));
		return fuels;
	}
	
	public static boolean mODE(Item item, String[] names) {
		return mODE(new ItemStack(item), names);
	}
	
	public static boolean mODE(ItemStack item, String[] names) {
		boolean flag = false;
		if(names.length > 0) {
			for(int i = 0; i < names.length; i++) {
				if(mODE(item, names[i]))
					flag = true;
			}
		}
		
		return flag;
	}
	
	public static boolean mODE(Item item, String name) {
		return mODE(new ItemStack(item), name);
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
	
	
	public static Map<Object, Object> getBoilerRecipes() {

		Map<Object, Object> recipes = new HashMap<Object, Object>();
		
		for(int i = 0; i < Fluids.getAll().length; i++) {
			Object[] outs = getBoilerOutput(Fluids.fromID(i));
			
			if(outs != null) {

				ItemStack in = new ItemStack(ModItems.fluid_icon, 1, i);
				in.stackTagCompound = new NBTTagCompound();
				in.stackTagCompound.setInteger("fill", (Integer) outs[2]);
				
				ItemStack out = new ItemStack(ModItems.fluid_icon, 1, ((FluidType)outs[0]).getID());
				out.stackTagCompound = new NBTTagCompound();
				out.stackTagCompound.setInteger("fill", (Integer) outs[1]);
				
				recipes.put(in, out);
			}
		}
		
		return recipes;
	}
	
	public static Map<Object, Object> getFluidContainers() {
		Map<Object, Object> map = new HashMap<Object, Object>();
		
		for(FluidContainer con : FluidContainerRegistry.allContainers) {
			if(con != null) {
				ItemStack fluid = new ItemStack(ModItems.fluid_icon, 1, con.type.getID());
				fluid.stackTagCompound = new NBTTagCompound();
				fluid.stackTagCompound.setInteger("fill", con.content);
				map.put(fluid, con.fullContainer);
			}
		}
		
		return map;
	}


	public static void registerArcFurnaceRecipes()
	{
		arcFurnaceRecipes.put(new ComparableStack(ModBlocks.sand_quartz), new ItemStack(ModBlocks.glass_quartz));
		arcFurnaceRecipes.put(new ComparableStack(ModItems.powder_acrylic), new ItemStack(ModItems.acrylic));
//		arcFurnaceRecipes.put(new ComparableStack(ModItems.storage_optical_raw), new ItemStack(ModItems.acrylic));
		arcFurnaceRecipes.put(new ComparableStack(ModItems.powder_quartz), new ItemStack(Items.quartz));
		for (int i = 0; i < 6; i++)
			arcFurnaceRecipes.put(new ComparableStack(ModItems.ingot_dineutronium_forged, 1, i), ItemHot.heatUp(new ItemStack(ModItems.ingot_dineutronium_forged, 1, i)));
//		arcFurnaceRecipes.put(new ComparableStack(ModItems.powder_niobium_alloy), new ItemStack(ModItems.ingot_niobium_alloy));

	}

	public static Map<Object, Object> getArcFurnaceRecipes()
	{
		return ImmutableMap.copyOf(arcFurnaceRecipes);
	}
}
