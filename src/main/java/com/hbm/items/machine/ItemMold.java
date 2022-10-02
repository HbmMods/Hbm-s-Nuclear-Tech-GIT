package com.hbm.items.machine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.material.Mats;
import com.hbm.inventory.material.MaterialShapes;
import com.hbm.inventory.material.NTMMaterial;
import com.hbm.items.ModItems;
import com.hbm.lib.RefStrings;
import com.hbm.util.I18nUtil;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraftforge.oredict.OreDictionary;

public class ItemMold extends Item {
	
	public static List<Mold> molds = new ArrayList(); //molds in "pretty" order, variable between versions
	public static HashMap<Integer, Mold> moldById = new HashMap(); //molds by their static ID -> stack item damage
	
	public HashMap<NTMMaterial, ItemStack> blockOverrides = new HashMap();
	
	public ItemMold() {

		this.setHasSubtypes(true);
		this.setMaxDamage(0);

		blockOverrides.put(Mats.MAT_STONE,		new ItemStack(Blocks.stone));
		blockOverrides.put(Mats.MAT_OBSIDIAN,	new ItemStack(Blocks.obsidian));
		
		int S = 0;
		int L = 1;
		registerMold(new MoldShape(		0, S, "nugget", MaterialShapes.NUGGET));
		registerMold(new MoldShape(		1, S, "billet", MaterialShapes.BILLET));
		registerMold(new MoldShape(		2, S, "ingot", MaterialShapes.INGOT));
		registerMold(new MoldShape(		3, S, "plate", MaterialShapes.PLATE));
		registerMold(new MoldWire(		4, S, "wire"));
		
		registerMold(new MoldMulti(		5, S, "blade", MaterialShapes.INGOT.q(3),
				Mats.MAT_TITANIUM, new ItemStack(ModItems.blade_titanium),
				Mats.MAT_TUNGSTEN, new ItemStack(ModItems.blade_tungsten)));
		
		registerMold(new MoldMulti(		6, S, "blades", MaterialShapes.INGOT.q(4),
				Mats.MAT_GOLD,			new ItemStack(ModItems.blades_gold),
				Mats.MAT_ALUMINIUM,		new ItemStack(ModItems.blades_aluminium),
				Mats.MAT_IRON,			new ItemStack(ModItems.blades_iron),
				Mats.MAT_STEEL,			new ItemStack(ModItems.blades_steel),
				Mats.MAT_TITANIUM,		new ItemStack(ModItems.blades_titanium),
				Mats.MAT_ALLOY,			new ItemStack(ModItems.blades_advanced_alloy),
				Mats.MAT_CMB,			new ItemStack(ModItems.blades_combine_steel),
				Mats.MAT_SCHRABIDIUM,	new ItemStack(ModItems.blades_schrabidium)));
		
		registerMold(new MoldMulti(		7, S, "stamp", MaterialShapes.INGOT.q(4),
				Mats.MAT_STONE,			new ItemStack(ModItems.stamp_stone_flat),
				Mats.MAT_IRON,			new ItemStack(ModItems.stamp_iron_flat),
				Mats.MAT_STEEL,			new ItemStack(ModItems.stamp_steel_flat),
				Mats.MAT_TITANIUM,		new ItemStack(ModItems.stamp_titanium_flat),
				Mats.MAT_OBSIDIAN,		new ItemStack(ModItems.stamp_obsidian_flat),
				Mats.MAT_SCHRABIDIUM,	new ItemStack(ModItems.stamp_schrabidium_flat)));
		
		registerMold(new MoldMulti(		8, S, "hull_small", MaterialShapes.INGOT.q(2),
				Mats.MAT_STEEL,		new ItemStack(ModItems.hull_small_steel),
				Mats.MAT_ALUMINIUM,	new ItemStack(ModItems.hull_small_aluminium)));
		
		registerMold(new MoldMulti(		9, L, "hull_big", MaterialShapes.INGOT.q(6),
				Mats.MAT_STEEL,		new ItemStack(ModItems.hull_big_steel),
				Mats.MAT_ALUMINIUM,	new ItemStack(ModItems.hull_big_aluminium),
				Mats.MAT_TITANIUM,	new ItemStack(ModItems.hull_big_titanium)));
		
		registerMold(new MoldShape(		10, L, "ingots", MaterialShapes.INGOT, 9));
		registerMold(new MoldShape(		11, L, "plates", MaterialShapes.PLATE, 9));
		registerMold(new MoldBlock(		12, L, "block", MaterialShapes.BLOCK));
		registerMold(new MoldSingle(	13, L, "pipes", new ItemStack(ModItems.pipes_steel), Mats.MAT_STEEL, MaterialShapes.BLOCK.q(3)));

		registerMold(new MoldSingle(	14, S, "c357", new ItemStack(ModItems.casing_357), Mats.MAT_COPPER, MaterialShapes.PLATE.q(1)));
		registerMold(new MoldSingle(	15, S, "c44", new ItemStack(ModItems.casing_44), Mats.MAT_COPPER, MaterialShapes.PLATE.q(1)));
		registerMold(new MoldSingle(	16, S, "c9", new ItemStack(ModItems.casing_9), Mats.MAT_COPPER, MaterialShapes.PLATE.q(1)));
		registerMold(new MoldSingle(	17, S, "c50", new ItemStack(ModItems.casing_50), Mats.MAT_COPPER, MaterialShapes.PLATE.q(1)));
		registerMold(new MoldSingle(	18, S, "cbuckshot", new ItemStack(ModItems.casing_buckshot), Mats.MAT_COPPER, MaterialShapes.PLATE.q(1)));
	}
	
	public void registerMold(Mold mold) {
		this.molds.add(mold);
		this.moldById.put(mold.id, mold);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs tab, List list) {
		for(int i = 0; i < molds.size(); i++) {
			Mold mold = molds.get(i);
			list.add(new ItemStack(item, 1, mold.id));
		}
	}
	
	protected IIcon[] icons;

	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister reg) {
		
		icons = new IIcon[molds.size()];
		
		for(int i = 0; i < molds.size(); i++) {
			Mold mold = molds.get(i);
			this.icons[i] = reg.registerIcon(RefStrings.MODID + ":mold_" + mold.name);
		}
	}

	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamage(int meta) {
		Mold mold = this.moldById.get(meta);
		if(mold != null)
			return this.icons[mold.order];
		
		return this.icons[0];
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {
		Mold mold = getMold(stack);
		list.add(EnumChatFormatting.YELLOW + mold.getTitle());
		
		if(mold.size == 0) list.add(EnumChatFormatting.GOLD + I18nUtil.resolveKey(ModBlocks.foundry_mold.getUnlocalizedName() + ".name"));
		if(mold.size == 1) list.add(EnumChatFormatting.RED + I18nUtil.resolveKey(ModBlocks.foundry_basin.getUnlocalizedName() + ".name"));
	}
	
	public Mold getMold(ItemStack stack) {
		Mold mold = moldById.get(stack.getItemDamage());
		return mold != null ? mold : molds.get(0);
	}
	
	public static int nextOrder = 0;

	public abstract class Mold {
		public int order;
		public int id;
		public int size;
		public String name;
		
		public Mold(int id, int size, String name) {
			this.order = nextOrder++;
			this.id = id;
			this.size = size;
			this.name = name;
		}
		
		public abstract ItemStack getOutput(NTMMaterial mat);
		public abstract int getCost();
		public abstract String getTitle();
	}

	public class MoldShape extends Mold {
		
		public MaterialShapes shape;
		public int amount;

		public MoldShape(int id, int size, String name, MaterialShapes shape) {
			this(id, size, name, shape, 1);
		}

		public MoldShape(int id, int size, String name, MaterialShapes shape, int amount) {
			super(id, size, name);
			this.shape = shape;
			this.amount = amount;
		}

		@Override
		public ItemStack getOutput(NTMMaterial mat) {
			
			for(String name : mat.names) {
				String od = shape.name().toLowerCase() + name;
				List<ItemStack> ores = OreDictionary.getOres(od);
				if(!ores.isEmpty()) {
					ItemStack copy = ores.get(0);
					copy.stackSize = this.amount;
					return copy;
				}
			}
			
			return null;
		}

		@Override
		public int getCost() {
			return shape.q(amount);
		}

		@Override
		public String getTitle() {
			return I18nUtil.resolveKey("shape." + shape.name().toLowerCase()) + " x" + amount;
		}
	}

	public class MoldBlock extends MoldShape {

		public MoldBlock(int id, int size, String name, MaterialShapes shape) {
			super(id, size, name, shape);
		}

		@Override
		public ItemStack getOutput(NTMMaterial mat) {
			
			ItemStack override = blockOverrides.get(mat);
			
			if(override != null)
				return override.copy();
			
			return super.getOutput(mat);
		}
	}

	public class MoldWire extends Mold {

		public MoldWire(int id, int size, String name) {
			super(id, size, name);
		}

		@Override
		public ItemStack getOutput(NTMMaterial mat) {

			if(mat == Mats.MAT_ALUMINIUM) return new ItemStack(ModItems.wire_aluminium, 8);
			if(mat == Mats.MAT_ALLOY) return new ItemStack(ModItems.wire_advanced_alloy, 8);
			if(mat == Mats.MAT_COPPER) return new ItemStack(ModItems.wire_copper, 8);
			if(mat == Mats.MAT_GOLD) return new ItemStack(ModItems.wire_gold, 8);
			if(mat == Mats.MAT_MAGTUNG) return new ItemStack(ModItems.wire_magnetized_tungsten, 8);
			if(mat == Mats.MAT_MINGRADE) return new ItemStack(ModItems.wire_red_copper, 8);
			if(mat == Mats.MAT_SCHRABIDIUM) return new ItemStack(ModItems.wire_schrabidium, 8);
			if(mat == Mats.MAT_TUNGSTEN) return new ItemStack(ModItems.wire_tungsten, 8);
			return null;
		}

		@Override
		public int getCost() {
			return MaterialShapes.WIRE.q(8);
		}

		@Override
		public String getTitle() {
			return I18nUtil.resolveKey("shape." + MaterialShapes.WIRE.name().toLowerCase()) + " x8";
		}
	}

	/* because why not */
	public class MoldSingle extends Mold {
		
		public ItemStack out;
		public NTMMaterial mat;
		public int amount;

		public MoldSingle(int id, int size, String name, ItemStack out, NTMMaterial mat, int amount) {
			super(id, size, name);
			this.out = out;
			this.mat = mat;
			this.amount = amount;
		}

		@Override
		public ItemStack getOutput(NTMMaterial mat) {
			return this.mat == mat ? out.copy() : null;
		}

		@Override
		public int getCost() {
			return amount;
		}

		@Override
		public String getTitle() {
			return out.getDisplayName() + " x" + this.out.stackSize;
		}
	}

	/* not so graceful but it does the job and it does it well */
	public class MoldMulti extends Mold {
		
		public HashMap<NTMMaterial, ItemStack> map = new HashMap();
		public int amount;
		public int stacksize;

		public MoldMulti(int id, int size, String name, int amount, Object... inputs) {
			super(id, size, name);
			this.amount = amount;
			
			for(int i = 0; i < inputs.length; i += 2) {
				map.put((NTMMaterial) inputs[i], (ItemStack) inputs[i + 1]);
				
				if(i == 0) stacksize = (((ItemStack) inputs[i + 1])).stackSize;
			}
		}

		@Override
		public ItemStack getOutput(NTMMaterial mat) {
			ItemStack out = this.map.get(mat);
			
			if(out != null)
				return out.copy();
			
			return out;
		}

		@Override
		public int getCost() {
			return amount;
		}

		@Override
		public String getTitle() {
			return I18nUtil.resolveKey("shape." + name) + " x" + this.stacksize;
		}
	}
}
