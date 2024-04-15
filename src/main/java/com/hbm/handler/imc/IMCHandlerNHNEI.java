package com.hbm.handler.imc;

import codechicken.nei.recipe.TemplateRecipeHandler;
import com.hbm.config.VersatileConfig;
import com.hbm.handler.nei.*;
import com.hbm.lib.RefStrings;
import com.hbm.main.NEIConfig;
import cpw.mods.fml.common.event.FMLInterModComms;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.ArrayList;

public class IMCHandlerNHNEI {
    
    public static ArrayList<TemplateRecipeHandler> handlerList() {
        ArrayList<TemplateRecipeHandler> handlers = new ArrayList<>();
        
        handlers.add(new AlloyFurnaceRecipeHandler());
        handlers.add(new ShredderRecipeHandler());
        handlers.add(new PressRecipeHandler());
        handlers.add(new CentrifugeRecipeHandler());
        handlers.add(new GasCentrifugeRecipeHandler());
        handlers.add(new BreederRecipeHandler());
        handlers.add(new CyclotronRecipeHandler());
        handlers.add(new AssemblerRecipeHandler());
        handlers.add(new RefineryRecipeHandler());
        handlers.add(new VacuumRecipeHandler());
        handlers.add(new CrackingHandler());
        handlers.add(new ReformingHandler());
        handlers.add(new HydrotreatingHandler());
        handlers.add(new BoilerRecipeHandler());
        handlers.add(new ChemplantRecipeHandler());
        handlers.add(new CrystallizerRecipeHandler());
        handlers.add(new BookRecipeHandler());
        handlers.add(new FusionRecipeHandler());
        handlers.add(new HadronRecipeHandler());
        handlers.add(new SILEXRecipeHandler());
        handlers.add(new SmithingRecipeHandler());
        handlers.add(new AnvilRecipeHandler());
        handlers.add(new FuelPoolHandler());
        handlers.add(new FluidRecipeHandler());
        handlers.add(new RadiolysisRecipeHandler());
        handlers.add(new CrucibleSmeltingHandler());
        handlers.add(new CrucibleAlloyingHandler());
        handlers.add(new CrucibleCastingHandler());
        handlers.add(new ToolingHandler());
        handlers.add(new ConstructionHandler());

        //universal boyes
        handlers.add(new ZirnoxRecipeHandler());
        if(VersatileConfig.rtgDecay()) {
            handlers.add(new RTGRecipeHandler());
        }
        handlers.add(new LiquefactionHandler());
        handlers.add(new SolidificationHandler());
        handlers.add(new CokingHandler());
        handlers.add(new FractioningHandler());
        handlers.add(new BoilingHandler());
        handlers.add(new CombinationHandler());
        handlers.add(new SawmillHandler());
        handlers.add(new MixerHandler());
        handlers.add(new OutgasserHandler());
        handlers.add(new ElectrolyserFluidHandler());
        handlers.add(new ElectrolyserMetalHandler());
        handlers.add(new AshpitHandler());
        handlers.add(new ArcWelderHandler());
        handlers.add(new ExposureChamberHandler());

        return handlers;
    }
    public static void IMCSender() {

        for (TemplateRecipeHandler handler: handlerList()) {

            Class<? extends TemplateRecipeHandler> handlerClass = handler.getClass();

            if(handler instanceof ICompatNHNEI && ((ICompatNHNEI) handler).getMachinesForRecipe() != null) {
                String blockName = "hbm:" + ((ICompatNHNEI) handler).getMachinesForRecipe()[0].getUnlocalizedName();
                String hClass = handlerClass.getName();
                sendHandler(hClass, ((ICompatNHNEI) handler).getRecipeID(), blockName);
                for (ItemStack stack: ((ICompatNHNEI) handler).getMachinesForRecipe()) {
                    sendCatalyst(hClass, "hbm:" + stack.getUnlocalizedName());
                }
            }
        }

    }
    private static void sendHandler(String aName, String handlerID, String aBlock) {
        sendHandler(aName, handlerID, aBlock, 3);
    }

    private static void sendHandler(String aName,  String handlerID, String aBlock, int maxRecipesPerPage) {
        NBTTagCompound aNBT = new NBTTagCompound();
        aNBT.setString("handler", aName);
        aNBT.setString("handlerID", handlerID);
        aNBT.setString("modName", RefStrings.NAME);
        aNBT.setString("modId", RefStrings.MODID);
        aNBT.setBoolean("modRequired", true);
        aNBT.setString("itemName", aBlock);
        aNBT.setInteger("handlerHeight", 65);
        aNBT.setInteger("handlerWidth", 166);
        aNBT.setInteger("maxRecipesPerPage", maxRecipesPerPage);
        aNBT.setInteger("yShift", 6);
        FMLInterModComms.sendMessage("NotEnoughItems", "registerHandlerInfo", aNBT);
    }

    private static void sendCatalyst(String aName, String aStack, int aPriority) {
        NBTTagCompound aNBT = new NBTTagCompound();
        aNBT.setString("handlerID", aName);
        aNBT.setString("catalystHandlerID", aName);
        aNBT.setString("itemName", aStack);
        aNBT.setInteger("priority", aPriority);
        FMLInterModComms.sendMessage("NotEnoughItems", "registerCatalystInfo", aNBT);
    }

    private static void sendCatalyst(String aName, String aStack) {
        sendCatalyst(aName, aStack, 0);
    }
}
