package com.hbm.interfaces;

public interface IExplosionRay {
	boolean isComplete();

	void cacheChunksTick(int processTime);

	void destructionTick(int processTime);

	void cancel();
}
