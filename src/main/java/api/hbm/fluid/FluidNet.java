package api.hbm.fluid;

import com.hbm.uninos.NodeNet;

public class FluidNet extends NodeNet { // yeah i don't feel like it, gonna do that shit tomorrow or sth
	
	public long tracker = 0L;
	
	protected static int timeout = 3_000;
	
	@Override public void resetTrackers() { this.tracker = 0; }
	
	@Override
	public void update() {
		
	}
}
