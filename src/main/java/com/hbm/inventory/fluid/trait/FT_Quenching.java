package com.hbm.inventory.fluid.trait;

import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;
import net.minecraft.util.EnumChatFormatting;


import java.io.IOException;
import java.util.List;

public class FT_Quenching extends  FluidTrait{


    public FT_Quenching() { }


    @Override
    public void addInfo(List<String> info) {
        info.add(EnumChatFormatting.AQUA + "[Quenching]");
    }

}
