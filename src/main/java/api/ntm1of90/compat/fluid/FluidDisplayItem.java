package api.ntm1of90.compat.fluid;

import api.ntm1of90.compat.fluid.render.ColoredForgeFluid;
import api.ntm1of90.compat.fluid.render.NTMFluidColorApplier;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * An item that displays fluids in the inventory.
 * This is used for displaying fluids in AE2's ME system and other inventory GUIs.
 */
public class FluidDisplayItem extends Item {

    private static FluidDisplayItem instance;
    private static Map<String, Integer> fluidToMetaMap = new HashMap<>();
    private static Map<Integer, String> metaToFluidMap = new HashMap<>();
    private static int nextMeta = 1; // Start at 1 to avoid conflicts

    private IIcon icon;

    /**
     * Create a new FluidDisplayItem.
     */
    public FluidDisplayItem() {
        super();
        this.setHasSubtypes(true);
        this.setMaxStackSize(1);
        this.setUnlocalizedName("fluidDisplayItem");
        this.setTextureName("minecraft:bucket_water");
    }

    /**
     * Get the instance of the FluidDisplayItem.
     * @return The instance of the FluidDisplayItem
     */
    public static FluidDisplayItem getInstance() {
        if (instance == null) {
            instance = new FluidDisplayItem();
        }
        return instance;
    }

    /**
     * Register a fluid with the FluidDisplayItem.
     * @param fluid The fluid to register
     * @return The metadata value for the fluid
     */
    public static int registerFluid(Fluid fluid) {
        if (fluid == null) {
            return 0;
        }

        String fluidName = fluid.getName();
        if (fluidToMetaMap.containsKey(fluidName)) {
            return fluidToMetaMap.get(fluidName);
        }

        int meta = nextMeta++;
        fluidToMetaMap.put(fluidName, meta);
        metaToFluidMap.put(meta, fluidName);
        return meta;
    }

    /**
     * Get the fluid for a metadata value.
     * @param meta The metadata value
     * @return The fluid for the metadata value
     */
    public static Fluid getFluidForMeta(int meta) {
        String fluidName = metaToFluidMap.get(meta);
        if (fluidName == null) {
            return null;
        }
        return FluidRegistry.getFluid(fluidName);
    }

    /**
     * Get the metadata value for a fluid.
     * @param fluid The fluid
     * @return The metadata value for the fluid
     */
    public static int getMetaForFluid(Fluid fluid) {
        if (fluid == null) {
            return 0;
        }
        String fluidName = fluid.getName();
        if (fluidToMetaMap.containsKey(fluidName)) {
            return fluidToMetaMap.get(fluidName);
        }
        return registerFluid(fluid);
    }

    /**
     * Get an ItemStack for a fluid.
     * @param fluid The fluid
     * @return An ItemStack for the fluid
     */
    public static ItemStack getItemStackForFluid(Fluid fluid) {
        if (fluid == null) {
            return null;
        }
        int meta = getMetaForFluid(fluid);
        return new ItemStack(getInstance(), 1, meta);
    }

    /**
     * Get an ItemStack for a FluidStack.
     * @param fluidStack The FluidStack
     * @return An ItemStack for the FluidStack
     */
    public static ItemStack getItemStackForFluidStack(FluidStack fluidStack) {
        if (fluidStack == null || fluidStack.getFluid() == null) {
            return null;
        }
        return getItemStackForFluid(fluidStack.getFluid());
    }

    /**
     * Get a FluidStack for an ItemStack.
     * @param itemStack The ItemStack
     * @param amount The amount of fluid
     * @return A FluidStack for the ItemStack
     */
    public static FluidStack getFluidStackForItemStack(ItemStack itemStack, int amount) {
        if (itemStack == null || itemStack.getItem() != getInstance()) {
            return null;
        }
        Fluid fluid = getFluidForMeta(itemStack.getItemDamage());
        if (fluid == null) {
            return null;
        }
        return new FluidStack(fluid, amount);
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        Fluid fluid = getFluidForMeta(stack.getItemDamage());
        if (fluid == null) {
            return "Unknown Fluid";
        }
        return fluid.getLocalizedName(new FluidStack(fluid, 1000));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getColorFromItemStack(ItemStack stack, int pass) {
        Fluid fluid = getFluidForMeta(stack.getItemDamage());
        if (fluid == null) {
            return 0xFFFFFF;
        }
        return NTMFluidColorApplier.getFluidColorForInventory(fluid);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister register) {
        this.icon = register.registerIcon("minecraft:bucket_water");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int damage) {
        return this.icon;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs tab, List list) {
        // Don't show in creative tabs
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean requiresMultipleRenderPasses() {
        return false;
    }
}
