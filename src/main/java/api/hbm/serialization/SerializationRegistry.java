package api.hbm.serialization;

import java.util.HashMap;

import com.hbm.main.DeserializationException;

/**
 * The main class to register serializable classes implementing {@link#ISerializable}.
 * @author UFFR
 *
 */
public final class SerializationRegistry
{
	private static final HashMap<Class<? extends ISerializable<?>>, Deserializer<?>> deserializerRegistry = new HashMap<>();
	private static final HashMap<String, Class<? extends ISerializable<?>>> classNameRegistry = new HashMap<>();
	private SerializationRegistry() throws InstantiationException
	{
		throw new InstantiationException("Class cannot be instantiated!");
	}
	/**
	 * Register a class and its deserializer.
	 * @param clazz The class to register.
	 * @param deserializer The deserializer object that can be used to create objects of the class.
	 */
	public static void register(Class<? extends ISerializable<?>> clazz, Deserializer<?> deserializer)
	{
		deserializerRegistry.putIfAbsent(clazz, deserializer);
		classNameRegistry.putIfAbsent(clazz.getSimpleName(), clazz);
	}
	/**
	 * Check if the class is registered.
	 * @param clazz
	 * @return If it is registered or not.
	 */
	public static boolean registered(Class<? extends ISerializable<?>> clazz)
	{
		return deserializerRegistry.containsKey(clazz);
	}
	/**
	 * Uses the class' simple name to see if it's registered.
	 * @param className
	 * @return If it is registered or not.
	 */
	public static boolean registered(String className)
	{
		return classNameRegistry.containsKey(className);
	}
	/**
	 * Uses the registry to get a deserializer for the class.
	 * @param <T> The type of the class and object.
	 * @param clazz The class to call the deserializer.
	 * @param bytes The byte array created from an object of the class to be deserialized.
	 * @return The deserialized object cast to the type for convenience.
	 * @throws DeserializationException Thrown if anything fails during deserialization. Should be wrapped around another exception to provide more debugging information.
	 */
	public static <T extends ISerializable<?>> T deserialize(Class<T> clazz, byte[] bytes) throws DeserializationException
	{
		return (T) deserializerRegistry.get(clazz).deserialize(bytes);
	}
	/**
	 * Uses the registry to get a deserializer for the class.
	 * @param className The simple name of the class.
	 * @param bytes The byte array created from an object of the class to be deserialized.
	 * @return The deserialized object.
	 * @throws DeserializationException Thrown if anything fails during deserialization. Should be wrapped around another exception to provide more debugging information.
	 */
	public static Object deserialize(String className, byte[] bytes) throws DeserializationException
	{
		return deserializerRegistry.get(classNameRegistry.get(className)).deserialize(bytes);
	}
}