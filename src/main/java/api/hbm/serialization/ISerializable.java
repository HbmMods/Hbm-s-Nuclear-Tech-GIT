package api.hbm.serialization;

import java.io.Serializable;
import java.util.function.Function;
import java.util.function.Supplier;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * Universal interface to support serliazation and deserialization.
 * @author UFFR
 *
 * @param <T> The (implementing) type that supports serialization.
 */
public interface ISerializable<T> extends Deserializer<T>, Serializable
{
	/**
	 * Just {@code Unpooled.buffer()}, so you don't have to import another class.
	 * @return The {@link#ByteBuf} created by {@code Unpooled.buffer()}
	 */
	public static final Supplier<ByteBuf> alloc = Unpooled::buffer;
	/**
	 * Just {@code Unpooled.copiedBuffer()}, so you don't have to import another class.
	 * @param bytes The byte array you wish the buffer to copy and wrap.
	 * @return The {@link#ByteBuf} created by {@code Unpooled.copiedBuffer()}
	 */
	public static final Function<byte[], ByteBuf> allocCopy = Unpooled::copiedBuffer;
	
	
	/**
	 * Serialize the object into a byte array.
	 * @return The serialized byte array. Must be able to be deserialized.
	 */
	public byte[] serialize();
}
