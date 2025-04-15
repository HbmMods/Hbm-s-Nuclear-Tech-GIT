package api.ntm1of90.compat;

import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fluids.BlockFluidBase;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.IFluidBlock;
import org.lwjgl.opengl.GL11;

import java.util.Locale;

/**
 * Handles rendering of Forge fluid blocks with HBM colors and textures.
 * This class provides hooks for fluid rendering.
 */
@SideOnly(Side.CLIENT)
public class HBMForgeFluidRenderer {

    /**
     * Initialize the renderer.
     * This should be called during mod initialization.
     */
    public static void initialize() {
        // Register for render events
        net.minecraftforge.common.MinecraftForge.EVENT_BUS.register(new HBMForgeFluidRenderer());
        System.out.println("[HBM] Forge fluid renderer initialized");
    }

    /**
     * This method is called when a fluid block is rendered.
     * It applies the appropriate color to the fluid.
     *
     * @param fluid The fluid being rendered
     * @return true if color was applied, false otherwise
     */
    public static boolean applyFluidColor(Fluid fluid) {
        if (fluid instanceof ColoredForgeFluid) {
            return HBMFluidColorApplier.applyFluidColor(fluid);
        }
        return false;
    }

    /**
     * Reset the color after rendering a fluid.
     */
    public static void resetFluidColor() {
        GL11.glColor3f(1.0f, 1.0f, 1.0f);
    }
}
