package com.hbm.render.tileentity.door;

import java.nio.DoubleBuffer;

import com.hbm.render.anim.BusAnimation;
import com.hbm.render.anim.BusAnimationSequence;
import com.hbm.render.anim.HbmAnimations.Animation;
import com.hbm.tileentity.TileEntityDoorGeneric;
import com.hbm.util.Clock;

public interface IRenderDoors {

	public void render(TileEntityDoorGeneric door, DoubleBuffer buf);
	
	public static double[] getRelevantTransformation(String bus, Animation anim) {

		if(anim != null) {

			BusAnimation buses = anim.animation;
			int millis = (int)(Clock.get_ms() - anim.startMillis);

			BusAnimationSequence seq = buses.getBus(bus);

			if(seq != null) {
				double[] trans = seq.getTransformation(millis);

				if(trans != null)
					return trans;
			}
		}

		return new double[] {
			0, 0, 0, // position
			0, 0, 0, // rotation
			1, 1, 1, // scale
			0, 0, 0, // offset
			0, 1, 2, // XYZ order
		};
	}
}
