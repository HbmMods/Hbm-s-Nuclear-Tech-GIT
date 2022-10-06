package com.hbm.render.block.ct;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.util.IIcon;

public class CTStitchReceiver {
	
	public static final List<CTStitchReceiver> receivers = new ArrayList();

	IIcon parentFull;
	IIcon parentCT;
	public IIcon[] fragCache = new IIcon[20];
	
	public CTStitchReceiver(IIcon parentFull, IIcon parentCT) {
		this.parentFull = parentFull;
		this.parentCT = parentCT;
		receivers.add(this);
	}

	public void postStitch() {
		
		for(int i = 0; i < 20; i++) {
			fragCache[i] = new IconCT(i < 4 ? parentFull : parentCT, i);
		}
	}
}
