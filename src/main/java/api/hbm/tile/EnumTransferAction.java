package api.hbm.tile;

public enum EnumTransferAction {
	
	NOTHING(20),					// port not in use, wait a full second
	CONNECT_NET(10),				// port connecting to a nodespace, half second refreshes are enough
	PROVIDE_DIRECT(1),				// direct transfer, needs to happen every tick
	REQUEST_IMMEDIATE_UPDATE(1);	// port not ticked yet, do an update right away
	
	public final int delay;
	
	private EnumTransferAction(int delay) {
		this.delay = delay;
	}
}
