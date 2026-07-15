package com.hbm.packet;

/**
 * Implemented by messages which own resources that must be released when the
 * bounded main-thread queue rejects the message.
 */
public interface IDiscardablePacket {

	void discard();
}
