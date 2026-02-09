package com.hbm.hrist;

/** Generated out of multiple ConnectionDefinitions to form a straight segment, ends at a branch */
public class ConduitConnection {

	protected boolean valid = true;
	public ConduitConnection[] connectedTo = new ConduitConnection[2]; // a sausage always has two ends
	public int stateKeepAlive; // unclear whether that's a good idea, since it requires the traveling object to be chunk loaded
	public ConnectionStatus state = ConnectionStatus.OKAY;
	
	public boolean isValid() { return this.valid; }
	public void invalidate() { this.valid = false; }
	
	public static enum ConnectionStatus {
		OKAY,
		BLOCKED
	}
}
