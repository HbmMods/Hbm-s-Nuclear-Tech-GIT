package com.hbm.tileentity.network;

import java.util.HashMap;
import java.util.Map.Entry;

import com.hbm.util.Tuple.Pair;

import net.minecraft.world.World;

public class RTTYSystem {

	/** Public frequency band for reading purposes, delayed by one tick */
	public static HashMap<Pair<World, String>, RTTYChannel> broadcast = new HashMap();
	/** New message queue for writing, gets written into readable Map later on */
	public static HashMap<Pair<World, String>, Object> newMessages = new HashMap();
	
	/** Pushes a new signal to be used next tick. Only the last signal pushed will be used. */
	public static void broadcast(World world, String channelName, Object signal) {
		Pair identifier = new Pair(world, channelName);
		newMessages.put(identifier, signal);
	}
	
	public static RTTYChannel listen(World world, String channelName) {
		RTTYChannel channel = broadcast.get(new Pair(world, channelName));
		return channel;
	}
	
	public static void updateBroadcastQueue() {
		
		for(Entry<Pair<World, String>, Object> worldEntry : newMessages.entrySet()) {
			Pair<World, String> identifier = worldEntry.getKey();
			Object lastSignal = worldEntry.getValue();
			
			RTTYChannel channel = new RTTYChannel();
			channel.timeStamp = identifier.getKey().getTotalWorldTime();
			channel.signal = lastSignal;
		}
	}
	
	public static class RTTYChannel {
		long timeStamp = -1; //the totalWorldTime at the time of publishing, happens in the server tick event's PRE-phase. the publishing timestamp is that same number minus one
		Object signal; // a signal can be anything, a number, an encoded string, an entire blue whale, Steve from accounting, the concept of death, 7492 hot dogs, etc.
	}

	/* Special objects for signifying specific signals to be used with RTTY machines (or telex) */
	public static enum RTTYSpecialSignal {
		BEGIN_TTY,		//start a new message block
		STOP_TTY,		//end the message block
		PRINT_BUFFER	//print message, literally, it makes a paper printout
	}
}
