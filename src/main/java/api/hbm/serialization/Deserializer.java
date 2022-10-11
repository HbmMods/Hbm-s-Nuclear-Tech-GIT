package api.hbm.serialization;

import com.hbm.main.DeserializationException;

/**
 * Generic interface to deserialize a byte array into an object
 * @author UFFR
 *
 * @param <T> The type that the byte array was created from and will be returned after deserialization
 */
@FunctionalInterface
public interface Deserializer<T>
{
	/**
	 * Deserialize a byte array into the given type
	 * @param bytes The byte array created by the given type (ideally)
	 * @return The type properly deserialized given the byte array
	 * @throws DeserializationException Thrown when anything goes wrong with deserialization. All causing exceptions should be wrapped into {@code DeserializationException} for logging and debugging purposes.
	 */
	public T deserialize(byte[] bytes) throws DeserializationException;
}