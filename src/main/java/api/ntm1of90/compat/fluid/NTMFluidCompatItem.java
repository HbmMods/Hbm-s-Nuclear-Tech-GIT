package api.ntm1of90.compat.fluid;

import com.hbm.lib.RefStrings;

import api.ntm1of90.compat.fluid.render.NTMFluidCompatRenderer;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A special item that represents NTM fluids for other mods to use in their inventories.
 * This item ensures that NTM fluids are displayed correctly in other mods' GUIs.
 */
public class NTMFluidCompatItem extends Item {
    
    // The singleton instance of this item
    private static NTMFluidCompatItem instance;
    
    // Maps fluid names to their display items
    private static final Map<String, ItemStack> fluidItems = new HashMap<>();
    
    /**
     * Get the singleton instance of this item
     */
    public static NTMFluidCompatItem getInstance() {
        if (instance == null) {
            instance = new NTMFluidCompatItem();
        }
        return instance;
    }
    
    /**
     * Private constructor to enforce singleton pattern
     */
    private NTMFluidCompatItem() {
        super();
        this.setUnlocalizedName("ntm_fluid_compat");
        this.setTextureName(RefStrings.MODID + ":forgefluid/water");
        this.setCreativeTab(null); // Not shown in creative tab
    }
    
    /**
     * Get an ItemStack that represents a fluid
     * @param fluid The fluid to represent
     * @return An ItemStack that represents the fluid
     */
    public static ItemStack getItemStackForFluid(Fluid fluid) {
        if (fluid == null) return null;
        
        String fluidName = fluid.getName();
        
        // Check if we already have an item for this fluid
        ItemStack item = fluidItems.get(fluidName);
        if (item != null) {
            return item.copy();
        }
        
        // Create a new item for this fluid
        item = new ItemStack(getInstance());
        NBTTagCompound tag = new NBTTagCompound();
        tag.setString("fluid", fluidName);
        item.setTagCompound(tag);
        
        // Store the item for future use
        fluidItems.put(fluidName, item);
        
        return item.copy();
    }
    
    /**
     * Get the fluid represented by an ItemStack
     * @param stack The ItemStack to get the fluid from
     * @return The fluid represented by the ItemStack, or null if none
     */
    public static Fluid getFluidFromItemStack(ItemStack stack) {
        if (stack == null || stack.getItem() != getInstance() || !stack.hasTagCompound()) {
            return null;
        }
        
        NBTTagCompound tag = stack.getTagCompound();
        if (!tag.hasKey("fluid")) {
            return null;
        }
        
        String fluidName = tag.getString("fluid");
        return FluidRegistry.getFluid(fluidName);
    }
    
    /**
     * Get a FluidStack from an ItemStack
     * @param stack The ItemStack to get the FluidStack from
     * @param amount The amount of fluid
     * @return A FluidStack with the fluid and amount, or null if none
     */
    public static FluidStack getFluidStackFromItemStack(ItemStack stack, int amount) {
        Fluid fluid = getFluidFromItemStack(stack);
        if (fluid == null) {
            return null;
        }
        
        return new FluidStack(fluid, amount);
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister register) {
        // Don't register any icons, we'll use the fluid's icon
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int damage) {
        // Use the default icon
        return itemIcon;
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(ItemStack stack, int pass) {
        // Use the fluid's icon if available
        Fluid fluid = getFluidFromItemStack(stack);
        if (fluid != null) {
            IIcon icon = NTMFluidCompatRenderer.getFluidIcon(fluid);
            if (icon != null) {
                return icon;
            }
        }
        
        // Fall back to the default icon
        return itemIcon;
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public int getColorFromItemStack(ItemStack stack, int pass) {
        // Use the fluid's color if available
        Fluid fluid = getFluidFromItemStack(stack);
        if (fluid != null) {
            return NTMFluidCompatRenderer.getFluidColor(fluid);
        }
        
        // Fall back to white
        return 0xFFFFFF;
    }
    
    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        // Use the fluid's name if available
        Fluid fluid = getFluidFromItemStack(stack);
        if (fluid != null) {
            return fluid.getLocalizedName(new FluidStack(fluid, 1000));
        }
        
        // Fall back to the default name
        return super.getItemStackDisplayName(stack);
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs tab, List list) {
        // Don't show any subitems in creative tab
    }
}
