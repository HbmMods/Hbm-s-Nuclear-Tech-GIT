package com.hbm.items.machine;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.hbm.inventory.AssemblerRecipes;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.inventory.RecipesCommon.OreDictStack;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraftforge.oredict.OreDictionary;

public class ItemAssemblyTemplate extends Item {

    public ItemAssemblyTemplate()
    {
        this.setHasSubtypes(true);
        this.setMaxDamage(0);
    }

    public String getItemStackDisplayName(ItemStack stack)
    {
        String s = ("" + StatCollector.translateToLocal(this.getUnlocalizedName() + ".name")).trim();
        ItemStack out = stack.getItemDamage() < AssemblerRecipes.recipeList.size() ? AssemblerRecipes.recipeList.get(stack.getItemDamage()).toStack() : null;
        String s1 = ("" + StatCollector.translateToLocal((out != null ? out.getUnlocalizedName() : "") + ".name")).trim();

        if (s1 != null)
        {
            s = s + " " + s1;
        }

        return s;
    }

    @Override
	@SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs tabs, List list) {
    	
    	int count = AssemblerRecipes.recipeList.size();
    	
    	for(int i = 0; i < count; i++) {
            list.add(new ItemStack(item, 1, i));
    	}
    }
    
    public static int getProcessTime(ItemStack stack) {
    	
    	if(!(stack.getItem() instanceof ItemAssemblyTemplate))
    		return 100;
    	
    	int i = stack.getItemDamage();
    	
    	if(i < 0 || i >= AssemblerRecipes.recipeList.size())
    		return 100;
    	
    	ComparableStack out = AssemblerRecipes.recipeList.get(i);
    	Integer time = AssemblerRecipes.time.get(out);
    	
    	if(time != null)
    		return time;
    	else
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
    	
    	ComparableStack out = AssemblerRecipes.recipeList.get(i);
    	
    	if(out == null) {
    		list.add("I AM ERROR");
    		return;
    	}
    	
    	Object[] in = AssemblerRecipes.recipes.get(out);
    	
    	if(in == null) {
    		list.add("I AM ERROR");
    		return;
    	}
    	
    	ItemStack output = out.toStack();
    	
		list.add("Output:");
		list.add(output.stackSize + "x " + output.getDisplayName());
		list.add("Inputs:");
		
		Random rand = new Random(System.currentTimeMillis() / 1000);
		
		for(Object o : in) {
			
			if(o instanceof ComparableStack)  {
				ItemStack input = ((ComparableStack)o).toStack();
	    		list.add(input.stackSize + "x " + input.getDisplayName());
	    		
			} else if(o instanceof OreDictStack)  {
				OreDictStack input = (OreDictStack) o;
				ArrayList<ItemStack> ores = OreDictionary.getOres(input.name);
				
				if(ores.size() > 0) {
					ItemStack inStack = ores.get(rand.nextInt(ores.size()));
		    		list.add(input.stacksize + "x " + inStack.getDisplayName());
				} else {
		    		list.add("I AM ERROR");
				}
			}
		}
		
		list.add("Production time:");
    	list.add(Math.floor((float)(getProcessTime(stack)) / 20 * 100) / 100 + " seconds");
	}

    /*@Override
	@SideOnly(Side.CLIENT)
    public boolean requiresMultipleRenderPasses()
    {
        return true;
    }
    
    public int getRenderPasses(int metadata)
    {
        return 8;
    }
    
	IIcon[] overlays;

    @Override
	@SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister p_94581_1_)
    {
        super.registerIcons(p_94581_1_);

        this.overlays = new IIcon[7];
        
        for(int i = 0; i < 7; i++)
        	overlays[i] = p_94581_1_.registerIcon("hbm:assembly_template_" + i);
    }
    
    @Override
	@SideOnly(Side.CLIENT)
    public IIcon getIconFromDamageForRenderPass(int a, int b)
    {
        return b < 7 ? overlays[b] : super.getIconFromDamageForRenderPass(a, b);
    }

    @Override
	@SideOnly(Side.CLIENT)
    public int getColorFromItemStack(ItemStack stack, int layer)
    {
        if (layer == 7)
        {
            return 0xFFFFFF;
        }
        else if(layer < 7)
        {
            int j = colorFromSeed(getSeedFromMeta(stack.getItemDamage(), layer));

            if (j < 0)
            {
                j = 0xFFFFFF;
            }

            return j;
        }
        
        return 0;
    }
    
    private int getSeedFromMeta(int i, int count) {
    	Random rand = new Random(i);
    	
    	int cap = 11;
    	
    	for(int j = 0; j < count - 1; j++)
    		rand.nextInt(cap);
    	
    	return rand.nextInt(cap);
    }
    
    private int colorFromSeed(int i) {
    	switch(i) {
    	case 0: return 0x334077;
    	case 1: return 0x6A298F;
    	case 2: return 0xDF3795;
    	case 3: return 0xFF0000;
    	case 4: return 0x00FF00;
    	case 5: return 0x0000FF;
    	case 6: return 0xFFFF00;
    	case 7: return 0x00FFFF;
    	case 8: return 0x888888;
    	case 9: return 0xFFFFFF;
    	case 10: return 0x000000;
    	default: return 0xFFFFFF;
    	}
    }*/
	
	/*public Motif getColorMotifFromTemplate(EnumAssemblyTemplate temp) {
		
		//using deprecated value operator, will remove soon
		if(temp.getValue() > 0) {
			Motif scheme = new Motif(temp.getValue, null);
			scheme.setTextureSize(16, 16);
			//scheme.applyUniversalScheme();
			scheme.colorCount = 4;
			//universal scheme configuration for testing
			//todo: get textures properly baked, display color for shield
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
