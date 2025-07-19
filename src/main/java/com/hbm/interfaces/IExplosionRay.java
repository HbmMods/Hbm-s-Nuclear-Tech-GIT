package com.hbm.interfaces;

/**
 * Interface for procedural explosions.
 * @author mlbv
 */
public interface IExplosionRay {
	/**
	 * Called every tick. Caches the chunks affected by the explosion.
	 * All heavy calculations are recommended to be done off the main thread.
	 * @param processTimeMs maximum time to process in this tick
	 */
	void cacheChunksTick(int processTimeMs);

	/**
	 * Called every tick to apply block destruction to the affected chunks.
	 * @param processTimeMs maximum time to process in this tick
	 */
	void destructionTick(int processTimeMs);

	/**
	 * Immediately cancels the explosion.
	 */
	void cancel();


	/**
	 * @return true if the explosion is finished or cancelled.
	 */
	boolean isComplete();
}
