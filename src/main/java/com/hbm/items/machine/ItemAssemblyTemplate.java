package com.hbm.items.machine;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.inventory.RecipesCommon.OreDictStack;
import com.hbm.inventory.recipes.AssemblerRecipes;
import com.hbm.inventory.recipes.AssemblerRecipes.AssemblerRecipe;
import com.hbm.items.ModItems;
import com.hbm.util.i18n.I18nUtil;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraftforge.oredict.OreDictionary;

public class ItemAssemblyTemplate extends Item {

	@SideOnly(Side.CLIENT)
	protected IIcon hiddenIcon;

	public ItemAssemblyTemplate() {
		this.setHasSubtypes(true);
		this.setMaxDamage(0);
	}

	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamage(int meta) {

		ComparableStack stack = AssemblerRecipes.recipeList.get(meta);
		AssemblerRecipe recipe = AssemblerRecipes.recipes.get(stack);

		if(recipe != null && !recipe.folders.contains(ModItems.template_folder))
			return this.hiddenIcon;

		return this.itemIcon;
	}

	@SideOnly(Side.CLIENT)
	public IIcon getIconIndex(ItemStack stack) {

		//NEW
		ComparableStack out = readType(stack);
		//LEGACY
		if(out == null) out = AssemblerRecipes.recipeList.get(stack.getItemDamage());

		AssemblerRecipe recipe = AssemblerRecipes.recipes.get(out);

		if(recipe != null && !recipe.folders.contains(ModItems.template_folder))
			return this.hiddenIcon;

		return this.itemIcon;
	}

	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister reg) {
		super.registerIcons(reg);
		this.hiddenIcon = reg.registerIcon(this.iconString + "_secret");
	}
	
	public static ItemStack writeType(ItemStack stack, ComparableStack comp) {
		if(!stack.hasTagCompound())
			stack.stackTagCompound = new NBTTagCompound();

		stack.stackTagCompound.setInteger("id", Item.getIdFromItem(comp.item));
		stack.stackTagCompound.setByte("count", (byte)comp.stacksize);
		stack.stackTagCompound.setShort("meta", (short)comp.meta);
		
		return stack;
	}
	
	public static ComparableStack readType(ItemStack stack) {
		if(!stack.hasTagCompound())
			return null;
		
		if(!stack.stackTagCompound.hasKey("id"))
			return null;
		
		int id = stack.stackTagCompound.getInteger("id");
		int count = stack.stackTagCompound.getByte("count");
		int meta = stack.stackTagCompound.getShort("meta");
		
		return new ComparableStack(Item.getItemById(id), count, meta);
	}

	public String getItemStackDisplayName(ItemStack stack) {

		try {
			//NEW
			ComparableStack comp = readType(stack);
			//LEGACY
			if(comp == null) comp = AssemblerRecipes.recipeList.get(stack.getItemDamage());
			
			String s = ("" + StatCollector.translateToLocal(this.getUnlocalizedName() + ".name")).trim();
			ItemStack out = comp != null ? comp.toStack() : null;
			if(out.getItem() == null) {
				out = null;
			}
			if(out == null) {
				return EnumChatFormatting.RED + "Broken Template" + EnumChatFormatting.RESET;
			}
			
			String s1 = out.getDisplayName().trim();
	
			if(s1 != null) {
				s = s + " " + s1;
			}
	
			return s;
		} catch(Exception ex) {
			return EnumChatFormatting.RED + "Broken Template" + EnumChatFormatting.RESET;
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs tabs, List list) {

		int count = AssemblerRecipes.recipeList.size();

		for(int i = 0; i < count; i++) {
			ComparableStack comp = AssemblerRecipes.recipeList.get(i);
			list.add(writeType(new ItemStack(item), comp));
		}
	}

	public static int getProcessTime(ItemStack stack) {

		if(!(stack.getItem() instanceof ItemAssemblyTemplate))
			return 100;

		int i = stack.getItemDamage();

		if(i < 0 || i >= AssemblerRecipes.recipeList.size())
			return 100;

		//NEW
		ComparableStack out = readType(stack);
		//LEGACY
		if(out == null) out = AssemblerRecipes.recipeList.get(i);
		AssemblerRecipe recipe = AssemblerRecipes.recipes.get(out);
		
		if(recipe != null) return recipe.time;
		
		return 100;
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {

		if(!(stack.getItem() instanceof ItemAssemblyTemplate))
			return;

		int i = stack.getItemDamage();

		if(i < 0 || i >= AssemblerRecipes.recipeList.size()) {
			list.add("I AM ERROR");
			return;
		}
		
		//NEW
		ComparableStack out = readType(stack);
		//LEGACY
		if(out == null) {
			out = AssemblerRecipes.recipeList.get(i);
		}
		
		AssemblerRecipe recipe = AssemblerRecipes.recipes.get(out);
		if(recipe == null) {
			list.add("I AM ERROR");
			return;
		}
		
		HashSet<Item> folders = recipe.folders;

		if(folders == null)
			folders = new HashSet() {
				{
					add(ModItems.template_folder);
				}
			};

		String[] names = new String[folders.size()];

		int a = 0;
		for(Item folder : folders) {
			names[a] = I18nUtil.resolveKey(folder.getUnlocalizedName() + ".name");
			a++;
		}

		list.add(EnumChatFormatting.YELLOW + I18nUtil.resolveKey("info.templatefolder", String.join(" / ", names)));
		list.add("");

		if(out == null) {
			list.add("I AM ERROR");
			return;
		}

		Object[] in = recipe.ingredients;

		if(in == null) {
			list.add("I AM ERROR");
			return;
		}

		ItemStack output = out.toStack();

		list.add(EnumChatFormatting.BOLD + I18nUtil.resolveKey("info.template_out"));
		list.add(output.stackSize + "x " + output.getDisplayName());
		list.add(EnumChatFormatting.BOLD + I18nUtil.resolveKey("info.template_in_p"));

		for(Object o : in) {

			if(o instanceof ComparableStack) {
				ItemStack input = ((ComparableStack) o).toStack();
				list.add(input.stackSize + "x " + input.getDisplayName());

			} else if(o instanceof OreDictStack) {
				OreDictStack input = (OreDictStack) o;
				ArrayList<ItemStack> ores = OreDictionary.getOres(input.name);

				if(ores.size() > 0) {
					ItemStack inStack = ores.get((int) (Math.abs(System.currentTimeMillis() / 1000) % ores.size()));
					list.add(input.stacksize + "x " + inStack.getDisplayName());
				} else {
					list.add("I AM ERROR");
				}
			}
		}

		list.add(EnumChatFormatting.BOLD + I18nUtil.resolveKey("info.template_time"));
		list.add(Math.floor((float) (getProcessTime(stack)) / 20 * 100) / 100 + " " + I18nUtil.resolveKey("info.template_seconds"));
	}

	/*
	 * @Override
	 * 
	 * @SideOnly(Side.CLIENT) public boolean requiresMultipleRenderPasses() {
	 * return true; }
	 * 
	 * public int getRenderPasses(int metadata) { return 8; }
	 * 
	 * IIcon[] overlays;
	 * 
	 * @Override
	 * 
	 * @SideOnly(Side.CLIENT) public void registerIcons(IIconRegister
	 * p_94581_1_) { super.registerIcons(p_94581_1_);
	 * 
	 * this.overlays = new IIcon[7];
	 * 
	 * for(int i = 0; i < 7; i++) overlays[i] =
	 * p_94581_1_.registerIcon("hbm:assembly_template_" + i); }
	 * 
	 * @Override
	 * 
	 * @SideOnly(Side.CLIENT) public IIcon getIconFromDamageForRenderPass(int a,
	 * int b) { return b < 7 ? overlays[b] :
	 * super.getIconFromDamageForRenderPass(a, b); }
	 * 
	 * @Override
	 * 
	 * @SideOnly(Side.CLIENT) public int getColorFromItemStack(ItemStack stack,
	 * int layer) { if (layer == 7) { return 0xFFFFFF; } else if(layer < 7) {
	 * int j = colorFromSeed(getSeedFromMeta(stack.getItemDamage(), layer));
	 * 
	 * if (j < 0) { j = 0xFFFFFF; }
	 * 
	 * return j; }
	 * 
	 * return 0; }
	 * 
	 * private int getSeedFromMeta(int i, int count) { Random rand = new
	 * Random(i);
	 * 
	 * int cap = 11;
	 * 
	 * for(int j = 0; j < count - 1; j++) rand.nextInt(cap);
	 * 
	 * return rand.nextInt(cap); }
	 * 
	 * private int colorFromSeed(int i) { switch(i) { case 0: return 0x334077;
	 * case 1: return 0x6A298F; case 2: return 0xDF3795; case 3: return
	 * 0xFF0000; case 4: return 0x00FF00; case 5: return 0x0000FF; case 6:
	 * return 0xFFFF00; case 7: return 0x00FFFF; case 8: return 0x888888; case
	 * 9: return 0xFFFFFF; case 10: return 0x000000; default: return 0xFFFFFF; }
	 * }
	 */

	/*
	 * public Motif getColorMotifFromTemplate(EnumAssemblyTemplate temp) {
	 * 
	 * //using deprecated value operator, will remove soon if(temp.getValue() >
	 * 0) { Motif scheme = new Motif(temp.getValue, null);
	 * scheme.setTextureSize(16, 16); //scheme.applyUniversalScheme();
	 * scheme.colorCount = 4; //universal scheme configuration for testing
	 * //todo: get textures properly baked, display color for shield
	 * scheme.addColor(0x334077); scheme.addColor(0x6A298F);
	 * scheme.addColor(0xDF3795); scheme.addColor(0x334077);
	 * 
	 * //different test config; prpl, lprpl, cyn, prpl
	 * 
	 * scheme.unify(); return scheme;
	 * 
	 * } else { //return null; return Motif.defaultInstance; } }
	 */

}
