package com.hbm.render.loader;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModelCustom;
import net.minecraftforge.client.model.IModelCustomLoader;
import net.minecraftforge.client.model.ModelFormatException;

@Deprecated
public class HmfModelLoader implements IModelCustomLoader {

    @Override
    public String getType()
    {
        return "HMF model";
    }

    private static final String[] types = { "hmf" };
    
    @Override
    public String[] getSuffixes()
    {
        return types;
    }

    @Override
    public IModelCustom loadInstance(ResourceLocation resource) throws ModelFormatException
    {
        return new HbmModelObject(resource);
    }
}
