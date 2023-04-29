package com.hbm.inventory.recipes;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.trait.*;

import com.hbm.inventory.recipes.loader.SerializableRecipe;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemFluidIcon;
import com.hbm.render.util.EnumSymbol;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;


public class FluidsEditor extends SerializableRecipe {
    public static HashMap<String, FluidType> fluids = new HashMap();
    public void registerDefaults(){
        for(FluidType fluidname:Fluids.getAll()){
            fluids.put(fluidname.getName(),fluidname);
        }
    }
    public static HashMap generateRecipes() {
        HashMap<Object, Object> cache = new HashMap<Object, Object>();
        //if(cache != null) return cache;

        for(Map.Entry<String, FluidType> entry : FluidsEditor.fluids.entrySet())  {
               FluidType type = entry.getValue();
            if(type.hasTrait(FT_Heatable.class)) {
                FT_Heatable trait = type.getTrait(FT_Heatable.class);

                if(trait.getEfficiency(FT_Heatable.HeatingType.BOILER) > 0) {
                    FT_Heatable.HeatingStep step = trait.getFirstStep();
                    cache.put(ItemFluidIcon.make(type, step.amountReq), ItemFluidIcon.make(step.typeProduced, step.amountProduced));
                }
            }
        }
        return cache;
    }
    public static Map<Object, Object> getBoilerRecipes() {

        Map<Object, Object> recipes = new HashMap<Object, Object>();

        for(int i = 0; i < Fluids.getAll().length; i++) {
            Object[] outs = MachineRecipes.getBoilerOutput(Fluids.fromID(i));

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
    @Override
    public String getFileName() {
        return "hbmFluids.json";
    }

    @Override
    public Object getRecipeObject() {
        return fluids;
    }

    @Override
    public void deleteRecipes() {
        fluids.clear();
    }

    @Override
    public void readRecipe(JsonElement recipe) {
        JsonObject obj = (JsonObject) recipe;
        String fluidname = obj.get("fluidname").getAsString();
        int poison = obj.get("poison").getAsInt();
        int flammability = obj.get("flammability").getAsInt();
        int reactivity = obj.get("reactivity").getAsInt();
        EnumSymbol symbol = EnumSymbol.valueOf(obj.get("symbol").getAsString());
        if(Fluids.fromName(fluidname)!=null){
            Fluids.fromName(fluidname).poison=poison;
            Fluids.fromName(fluidname).flammability=flammability;
            Fluids.fromName(fluidname).reactivity=reactivity;
            Fluids.fromName(fluidname).symbol=symbol;
            if(obj.has("temperature")) Fluids.fromName(fluidname).temperature=obj.get("temperature").getAsInt();
            else {Fluids.fromName(fluidname).temperature = 20;}
            if(obj.has("Corrosive")) Fluids.fromName(fluidname).addTraits(new FT_Corrosive(obj.get("Corrosive").getAsInt()));
            else {Fluids.fromName(fluidname).removeTrait(FT_Corrosive.class);}
            if(obj.has("Radiation")) Fluids.fromName(fluidname).addTraits(new FT_VentRadiation(obj.get("Radiation").getAsInt()));
            else {Fluids.fromName(fluidname).removeTrait(FT_VentRadiation.class);}
            if(obj.has("FuelGrade")){Fluids.fromName(fluidname).addTraits(
               new FT_Combustible(FT_Combustible.FuelGrade.valueOf(obj.get("FuelGrade").getAsString())
                                  ,obj.get("CombustibleEnergy").getAsInt()));
            }
            else {Fluids.fromName(fluidname).removeTrait(FT_Combustible.class);}
            if(obj.has("HeatEnergy")){Fluids.fromName(fluidname).addTraits(new FT_Flammable(obj.get("HeatEnergy").getAsInt()));}
            else {Fluids.fromName(fluidname).removeTrait(FT_Flammable.class);}
            if(obj.has("CoolableToFluid")){
                Fluids.fromName(fluidname).removeTrait(FT_Coolable.class);
                Fluids.fromName(fluidname).addTraits(
                        new FT_Coolable(Fluids.fromName(obj.get("CoolableToFluid").getAsString()),
                                        obj.get("CoolableFluidAmountReq").getAsInt(),
                                        obj.get("CoolableFluidAmountProduced").getAsInt(),
                                        obj.get("CoolableFluidheatEnergy").getAsInt())
                                        .setEff(FT_Coolable.CoolingType.TURBINE, 1.0D).setEff(FT_Coolable.CoolingType.HEATEXCHANGER, 0.25D)
                );
            }
            else {Fluids.fromName(fluidname).removeTrait(FT_Coolable.class);}
            if(obj.has("HeatableToFluid")){
                Fluids.fromName(fluidname).removeTrait(FT_Heatable.class);
                Fluids.fromName(fluidname).addTraits(
                        new FT_Heatable().setEff(FT_Heatable.HeatingType.BOILER, 1.0D).setEff(FT_Heatable.HeatingType.HEATEXCHANGER, 0.25D)
                                .addStep(obj.get("HeatableFluidHeatReq").getAsInt(),
                                                  obj.get("HeatableFluidAmountReq").getAsInt(),
                                                  Fluids.fromName(obj.get("HeatableToFluid").getAsString()),
                                                  obj.get("HeatableFluidAmountProduced").getAsInt())
                );
            }
            else {Fluids.fromName(fluidname).removeTrait(FT_Heatable.class);}
            fluids.put(Fluids.fromName(fluidname).getName(),Fluids.fromName(fluidname));
        }
    }

    @Override
    public void writeRecipe(Object recipe, JsonWriter writer) throws IOException {
        Map.Entry<Integer, FluidType> entry = (Map.Entry<Integer, FluidType>) recipe;
        writer.name("fluidname").value(entry.getValue().getName());
        writer.name("poison").value(entry.getValue().poison);
        writer.name("flammability").value(entry.getValue().flammability);
        writer.name("reactivity").value(entry.getValue().reactivity);
        writer.name("symbol").value(entry.getValue().symbol.toString());
        if(entry.getValue().hasTrait(FT_Combustible.class)){
            writer.name("CombustibleEnergy").value(entry.getValue().getTrait(FT_Combustible.class).getCombustionEnergy());
            writer.name("FuelGrade").value(entry.getValue().getTrait(FT_Combustible.class).getGrade().name());
        }
        if(entry.getValue().temperature!=20){
            writer.name("temperature").value(entry.getValue().temperature);
        }
        if (entry.getValue().hasTrait(FT_Corrosive.class)){
            writer.name("Corrosive").value(entry.getValue().getTrait(FT_Corrosive.class).getRating());
        }
        if (entry.getValue().hasTrait(FT_VentRadiation.class)){
            writer.name("Radiation").value(entry.getValue().getTrait(FT_VentRadiation.class).getRadPerMB());
        }
        if (entry.getValue().hasTrait(FT_Flammable.class)) {
           writer.name("HeatEnergy").value(entry.getValue().getTrait(FT_Flammable.class).getHeatEnergy());
        }
        if (entry.getValue().hasTrait(FT_Coolable.class)) {
            writer.name("CoolableToFluid").value(entry.getValue().getTrait(FT_Coolable.class).coolsTo.getName());
            writer.name("CoolableFluidAmountReq").value(entry.getValue().getTrait(FT_Coolable.class).amountReq);
            writer.name("CoolableFluidAmountProduced").value(entry.getValue().getTrait(FT_Coolable.class).amountProduced);
            writer.name("CoolableFluidheatEnergy").value(entry.getValue().getTrait(FT_Coolable.class).heatEnergy);
        }
        if (entry.getValue().hasTrait(FT_Heatable.class)){
            writer.name("HeatableToFluid").value(entry.getValue().getTrait(FT_Heatable.class).getFirstStep().typeProduced.getName());
            writer.name("HeatableFluidAmountReq").value(entry.getValue().getTrait(FT_Heatable.class).getFirstStep().amountReq);
            writer.name("HeatableFluidAmountProduced").value(entry.getValue().getTrait(FT_Heatable.class).getFirstStep().amountProduced);
            writer.name("HeatableFluidHeatReq").value(entry.getValue().getTrait(FT_Heatable.class).getFirstStep().heatReq);
        }
    }
}
