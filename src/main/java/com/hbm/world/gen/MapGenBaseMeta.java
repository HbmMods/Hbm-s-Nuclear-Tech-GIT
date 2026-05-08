package com.hbm.world.gen;

import java.util.Random;

public abstract class MapGenBaseMeta {

	protected Random rand = new Random();
	protected int range;
	protected int[] metas;
	
	public void setMetas(int[] metas) {
		this.metas = metas;
	}

}
