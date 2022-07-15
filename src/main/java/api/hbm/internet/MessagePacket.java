package api.hbm.internet;

import java.io.IOException;
import java.util.UUID;

import com.hbm.main.DeserializationException;

import api.hbm.serialization.ISerializable;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTSizeTracker;
import net.minecraft.nbt.NBTTagCompound;

public class MessagePacket implements ISerializable<MessagePacket>
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3903749834059266402L;

	public enum PacketType
	{
		UNKNOWN,
		START,
		MIDDLE,
		END,
		COMMAND,
		HASH,
		CORRUPT;
	}
	private final UUID sender, target;
	private final long size;
	private final NBTTagCompound nbt;
	private final PacketType type;
	
	public MessagePacket(UUID sender, UUID target, long size, NBTTagCompound nbt, PacketType type)
	{
		this.sender = sender;
		this.target = target;
		this.size = size;
		this.nbt = nbt;
		this.type = type;
	}
	
	public MessagePacket(UUID sender, UUID target, long size, PacketType type)
	{
		this(sender, target, size, new NBTTagCompound(), type);
	}
	
	

	public MessagePacket(UUID sender, UUID target, byte[] hash)
	{
		this.sender = sender;
		this.target = target;
		size = hash.length;
		nbt = new NBTTagCompound();
		nbt.setByteArray("hash", hash);
		type = PacketType.HASH;
	}

	/**
	 * @return the sender
	 */
	public UUID getSender()
	{
		return sender;
	}

	/**
	 * @return the target
	 */
	public UUID getTarget()
	{
		return target;
	}

	/**
	 * @return the size
	 */
	public long getSize()
	{
		return size;
	}

	/**
	 * @return the nbt
	 */
	public NBTTagCompound getNbt()
	{
		return (NBTTagCompound) nbt.copy();
	}

	/**
	 * @return the type
	 */
	public PacketType getType()
	{
		return type;
	}

	@Override
	public MessagePacket deserialize(byte[] bytes) throws DeserializationException
	{
		try
		{
			final ByteBuf buf = allocCopy.apply(bytes);
			final byte[] senderBytes = new byte[36],
						 targetBytes = new byte[36];
			buf.readBytes(senderBytes);
			buf.readBytes(targetBytes);
			final UUID sender = UUID.nameUUIDFromBytes(senderBytes),
					   target = UUID.nameUUIDFromBytes(targetBytes);
			final long size = buf.readLong();
			final byte typeOrdinal = buf.readByte();
			final PacketType type;
			if (typeOrdinal < 0 || typeOrdinal > 5)
				return new MessagePacket(sender, target, size, PacketType.CORRUPT);
			type = PacketType.values()[typeOrdinal];
			if (type == PacketType.HASH)
			{
				final byte[] hashBytes = new byte[16];
				buf.readBytes(hashBytes);
				return new MessagePacket(sender, target, hashBytes);
			}
			final short nbtSize = buf.readShort();
			if (nbtSize == 0)
				return new MessagePacket(sender, target, size, type);
			else if (nbtSize > 0)
				return new MessagePacket(sender, target, size, PacketType.CORRUPT);
			else
			{
				final byte[] nbtBytes = new byte[nbtSize];
				buf.readBytes(nbtBytes);
				final NBTTagCompound nbt = CompressedStreamTools.func_152457_a(nbtBytes, new NBTSizeTracker(2097152L));
				return new MessagePacket(sender, target, size, nbt, type);
			}
		} catch (IOException e)
		{
			e.printStackTrace();
			throw new DeserializationException(e);
		}
	}

	@Override
	public byte[] serialize()
	{
		final ByteBuf buf = alloc.get();
		buf.writeBytes(sender.toString().getBytes());
		buf.writeBytes(target.toString().getBytes());
		buf.writeLong(getSize());
		buf.writeByte(type.ordinal());
		if (type == PacketType.HASH)
		{
			buf.writeBytes(nbt.getByteArray("hash"));
			return buf.array();
		}
		else if (nbt.hasNoTags())
			buf.writeShort(0);
		else
		{
			try
			{
				final byte[] nbtBytes = CompressedStreamTools.compress(getNbt());
				buf.writeShort(nbtBytes.length);
				buf.writeBytes(nbtBytes);

			} catch (IOException e)
			{
				e.printStackTrace();
				buf.writeShort(-1);
			}
		}
		return buf.array();
	}

}
