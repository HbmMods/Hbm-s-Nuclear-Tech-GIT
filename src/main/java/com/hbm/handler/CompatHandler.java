package com.hbm.handler;

import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.RecipesCommon;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.lib.RefStrings;
import com.hbm.main.MainRegistry;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Optional;
import li.cil.oc.api.Items;
import li.cil.oc.api.fs.FileSystem;
import li.cil.oc.api.machine.Arguments;
import li.cil.oc.api.machine.Context;
import li.cil.oc.api.network.*;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.ForgeDirection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Callable;

import static com.hbm.main.CraftingManager.addShapelessAuto;
import static li.cil.oc.api.FileSystem.asReadOnly;
import static li.cil.oc.api.FileSystem.fromClass;

/**
 * General handler for OpenComputers compatibility.
 * @author BallOfEnergy (Microwave)
 */
public class CompatHandler {

    /**
     * Used for converting a steam type to an integer (compression levels).
     * @param type Steam type.
     * @return Object[] array containing an int with the "compression level"
     */
    public static Object[] steamTypeToInt(FluidType type) {
		final int typeId = type.getID();
		if(typeId == Fluids.HOTSTEAM.getID()) {
			return new Object[]{1};
		} else if(typeId == Fluids.SUPERHOTSTEAM.getID()) {
			return new Object[]{2};
		} else if(typeId == Fluids.ULTRAHOTSTEAM.getID()) {
			return new Object[]{3};
		}
		return new Object[] {0};
    }

    /**
     * Used for converting a compression level to a steam type.
     * @param arg Steam compression level.
     * @return FluidType of the steam type based on the compression level.
     */
    public static FluidType intToSteamType(int arg) {
        switch(arg) {
            case(1):
                return Fluids.HOTSTEAM;
            case(2):
                return Fluids.SUPERHOTSTEAM;
            case(3):
                return Fluids.ULTRAHOTSTEAM;
			default:
				return Fluids.STEAM;
        }
    }

    /**
     * Allows for easy creation of read-only filesystems. Primarily for floppy disks.
     * (Though maybe reading directly from VOTV drives as filesystems could be implemented. :3)
     **/
    protected static class ReadOnlyFileSystem implements Callable<FileSystem> {

        private final String name;

        ReadOnlyFileSystem(String name) {
            this.name = name;
        }

        @Override
        @Optional.Method(modid = "OpenComputers")
        public li.cil.oc.api.fs.FileSystem call() throws Exception {
            return asReadOnly(fromClass(MainRegistry.class, RefStrings.MODID, "disks/" + FloppyDisk.sanitizeName(name)));
        }
    }

    // Floppy disk class.
    public static class FloppyDisk {
        // Specifies the callable ReadOnlyFileSystem to allow OC to access the floppy.
		protected final ReadOnlyFileSystem fs;
        // Specifies the color of the floppy disk (0-16 colors defined by OC).
        public final Byte color;
        // Set after loading the disk; allows for adding a recipe to the item.
        public ItemStack item;

        FloppyDisk(String name, int color) {
            this.fs = new ReadOnlyFileSystem(FloppyDisk.sanitizeName(name));
            this.color = (byte) color;
        }

        // Disk names will be sanitized before the FileSystem is created.
        // This only affects the location/directory, not the display name.
        // (Prevents filesystems from breaking/crashing due to having file separators, wildcards, etc.)
        public static String sanitizeName(String input) {
            return input.toLowerCase().replaceAll("\\W", "");
        }
    }

    /**
     * Simple enum for mapping OC color ordinals to a nicer format for adding new disks.
     */
    public enum OCColors {
        BLACK, //0x444444
        RED, //0xB3312C
        GREEN, //0x339911
        BROWN, //0x51301A
        BLUE, //0x6666FF
        PURPLE, //0x7B2FBE
        CYAN, //0x66FFFF
        LIGHTGRAY, //0xABABAB
        GRAY, //0x666666
        PINK, //0xD88198
        LIME, //0x66FF66
        YELLOW, //0xFFFF66
        LIGHTBLUE, //0xAAAAFF
        MAGENTA, //0xC354CD
        ORANGE, //0xEB8844
        WHITE; //0xF0F0F0

        /**
         * Returns the RGB color value for this OC color.
         */
        public int getColor() {
            switch (this) {
                case BLACK: return 0x444444;
                case RED: return 0xB3312C;
                case GREEN: return 0x339911;
                case BROWN: return 0x51301A;
                case BLUE: return 0x6666FF;
                case PURPLE: return 0x7B2FBE;
                case CYAN: return 0x66FFFF;
                case LIGHTGRAY: return 0xABABAB;
                case GRAY: return 0x666666;
                case PINK: return 0xD88198;
                case LIME: return 0x66FF66;
                case YELLOW: return 0xFFFF66;
                case LIGHTBLUE: return 0xAAAAFF;
                case MAGENTA: return 0xC354CD;
                case ORANGE: return 0xEB8844;
                case WHITE: return 0xF0F0F0;
                default: return 0xABABAB;
            }
        }

        /**
         * Maps a stored RGB integer back to an OCColors enum value.
         */
        public static OCColors fromInt(int color) {
            for (OCColors c : values()) {
                if (c.getColor() == color) return c;
            }
            return LIGHTGRAY;
        }

        /**
         * Converts a dye ItemStack to an OC color.
         * Accepts vanilla ItemDye damage values (0-15).
         */
        public static OCColors fromDye(net.minecraft.item.ItemStack stack) {
            if (stack == null) return LIGHTGRAY;
            if (stack.getItem() instanceof net.minecraft.item.ItemDye) {
                switch (stack.getItemDamage() & 15) {
                    case 0:  return BLACK;
                    case 1:  return RED;
                    case 2:  return GREEN;
                    case 3:  return BROWN;
                    case 4:  return BLUE;
                    case 5:  return PURPLE;
                    case 6:  return CYAN;
                    case 7:  return LIGHTGRAY;
                    case 8:  return GRAY;
                    case 9:  return PINK;
                    case 10: return LIME;
                    case 11: return YELLOW;
                    case 12: return LIGHTBLUE;
                    case 13: return MAGENTA;
                    case 14: return ORANGE;
                    case 15: return WHITE;
                }
            }
            return LIGHTGRAY;
        }
    }

    // Where all disks are stored with their name and `FloppyDisk` class.
    public static HashMap<String, FloppyDisk> disks = new HashMap<>();

    /**
     * Called in the FML PostLoad stage, after the OC API loads.
     * <br>
     * Loads various parts of OC compatibility.
     */
    public static void init() {
        if(Loader.isModLoaded("OpenComputers")) {
            /*
            For anyone wanting to add their own floppy disks,
            read the README found in assets.hbm.disks.
            */

            // Idea/Code by instantnootles
            disks.put("PWRangler", new FloppyDisk("PWRangler", OCColors.CYAN.ordinal()));

            // begin registering disks
            Logger logger = LogManager.getLogger("HBM");
            logger.info("Loading OpenComputers disks...");
            if(disks.isEmpty()) {
                logger.info("No disks registered; see com.hbm.handler.CompatHandler.disks");
                return;
            }
            disks.forEach((s, disk) -> {

                // Test if the disk path even exists.
                FileSystem fs = fromClass(MainRegistry.class, RefStrings.MODID, "disks/" + disk.fs.name);

                if (fs == null) { // Disk path does NOT exist, and it should not be loaded.

					logger.error("Error loading disk: {} at /assets/" + RefStrings.MODID + "/disks/{}", s, disk.fs.name);
                    logger.error("This is likely due to the path to the disk being non-existent.");

                } else { // Disk path DOES exist, and it should be loaded.

                    disk.item = Items.registerFloppy(s, disk.color, disk.fs); // The big part, actually registering the floppies!
					logger.info("Registered disk: {} at /assets/" + RefStrings.MODID + "/disks/{}", s, disk.fs.name);

                }
            });
            logger.info("OpenComputers disks registered.");

            // OC disk recipes!
            List<ItemStack> floppyDisks = new RecipesCommon.OreDictStack("oc:floppy").toStacks();

            if(!floppyDisks.isEmpty()) { //check that floppy disks even exist in oredict.

                // Recipes must be initialized here, since if they were initialized in `CraftingManager` then the disk item would not be created yet.
                addShapelessAuto(disks.get("PWRangler").item, "oc:floppy", new ItemStack(ModBlocks.pwr_casing));

                logger.info("OpenComputers disk recipe added for PWRangler.");
            } else {
                logger.info("OpenComputers floppy disk oredict not found, recipes cannot be loaded!");
            }

            // boom, OC disks loaded
            logger.info("OpenComputers disks loaded.");
        }
    }

    // Null component name, default to this if broken to avoid NullPointerExceptions.
    public static final String nullComponent = "ntm_null";

    /**
     * This is an interface made specifically for adding OC compatibility to NTM machines. The {@link li.cil.oc.api.network.SimpleComponent} interface must also be implemented in the TE.
     * <br>
     * This interface is not required to be defined as an optional interface, though the {@link li.cil.oc.api.network.SimpleComponent} interface must be declared as an optional interface.
     * <br>
     * Pseudo multiblocks will automatically receive compatibility with their ports by proxying their `methods()` and `invoke()` functions. This is the only time they need to be defined.
     *
     **/
    @Optional.InterfaceList({
            @Optional.Interface(iface = "li.cil.oc.api.network.SimpleComponent", modid = "OpenComputers"),
            @Optional.Interface(iface = "li.cil.oc.api.network.SidedComponent", modid = "OpenComputers"),
            @Optional.Interface(iface = "li.cil.oc.api.network.ManagedPeripheral", modid = "OpenComputers"),
    })
    @SimpleComponent.SkipInjection // make sure OC doesn't inject this shit into the interface and crash
    public interface OCComponent extends SimpleComponent, SidedComponent, ManagedPeripheral {

        /**
         * Must be overridden in the implemented TE, or it will default to "ntm_null".
         * <br>
         * Dictates the component name exposed to the computer.
         * @return String
         */
        @Override
        @Optional.Method(modid = "OpenComputers")
        default String getComponentName() {
            return nullComponent;
        }

        /**
         * Tells OC which sides of the block cables should connect to.
         * @param side Side to check
         * @return If the side should be able to connect.
         */
        @Override
        @Optional.Method(modid = "OpenComputers")
        default boolean canConnectNode(ForgeDirection side) {
            return !this.getComponentName().equals(nullComponent);
        }

        /**
         * Standard methods array from {@link li.cil.oc.api.network.ManagedPeripheral} extending {@link li.cil.oc.api.network.SimpleComponent}.
         * @return Array of methods to expose to the computer.
         */
        @Override
        @Optional.Method(modid = "OpenComputers")
        default String[] methods() {return new String[0];}

        /**
         * Standard invoke function from {@link li.cil.oc.api.network.ManagedPeripheral} extending {@link li.cil.oc.api.network.SimpleComponent}.
         * @return Data to the computer as a return from the function.
         */
        @Override
        @Optional.Method(modid = "OpenComputers")
        default Object[] invoke(String method, Context context, Arguments args) throws Exception {return null;}
    }
}
