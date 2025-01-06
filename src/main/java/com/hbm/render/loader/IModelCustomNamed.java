package com.hbm.render.loader;

import java.util.List;

import net.minecraftforge.client.model.IModelCustom;

public interface IModelCustomNamed extends IModelCustom {

	// A little messy, but this is the cleanest refactor, and can be useful in the future
	
	public List<String> getPartNames();

}
