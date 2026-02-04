package com.hbm.hrist;

import java.util.HashMap;
import java.util.Map;

import com.hbm.util.fauxpointtwelve.DimPos;

/// ROBUR PER UNITATEM ///
public class ConduitSpace {
	
	/** Maps conduit core pos to the actual conduit piece logical unit */
	public static Map<DimPos, ConduitPiece> pieces = new HashMap();
}
