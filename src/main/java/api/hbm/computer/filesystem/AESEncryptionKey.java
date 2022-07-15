package api.hbm.computer.filesystem;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import com.hbm.lib.Library;
import com.hbm.main.DeserializationException;
import com.hbm.main.MainRegistry;

import io.netty.buffer.ByteBuf;

public class AESEncryptionKey extends FileBase
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5971864114448314627L;
	private static final SecureRandom RANDOM = new SecureRandom();
	private final IvParameterSpec spec;
	private final SecretKey secretKey;
	private boolean invalid = false;
	public AESEncryptionKey()
	{
		spec = genIvSpec();
		secretKey = genKey();
	}
	
	public AESEncryptionKey(String password)
	{
		spec = genIvSpec();
		secretKey = genKey(password);
	}
	
	public AESEncryptionKey(SecretKey key, IvParameterSpec spec)
	{
		secretKey = key;
		this.spec = spec;
	}
	
	public boolean isKeyValid()
	{
		return !invalid;
	}
	
	public byte[] encryptToBytes(byte[] input)
	{
		try
		{
			final Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, secretKey, spec);
			return cipher.doFinal(input);
		} catch (BadPaddingException | NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | InvalidAlgorithmParameterException | IllegalBlockSizeException e)
		{
			e.printStackTrace();
			MainRegistry.logger.error("Could not complete encryption task, this should not be possible!", e);
			return emptyBytes;
		}
	}
	
	public String encryptToString(byte[] input)
	{
		return Base64.getEncoder().encodeToString(encryptToBytes(input));
	}
	
	public byte[] decryptFromStringToBytes(String input)
	{
		return decryptToBytes(Base64.getDecoder().decode(input));
	}
	
	public String decryptFromStringToString(String input)
	{
		return new String(decryptFromStringToBytes(input));
	}
	
	public byte[] decryptToBytes(byte[] input)
	{
		try
		{
			final Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, secretKey, spec);
			return cipher.doFinal(input);
		} catch (BadPaddingException | IllegalBlockSizeException | InvalidKeyException | InvalidAlgorithmParameterException | NoSuchAlgorithmException | NoSuchPaddingException e)
		{
			e.printStackTrace();
			MainRegistry.logger.error("Could not complete decryption task, this should not be possible!", e);
			return emptyBytes;
		}
	}
	
	public String decryptToString(byte[] input)
	{
		return new String(Base64.getDecoder().decode(decryptToBytes(input)));
	}
	
	private static SecretKey genKey(String password)
	{
		try
		{
			final SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
			final byte[] salt = new byte[256];
			RANDOM.nextBytes(salt);
			KeySpec spec = new PBEKeySpec(Library.toHexString(Library.getHash(password)).toCharArray(), salt, 65536, 256);
			return new SecretKeySpec(factory.generateSecret(spec).getEncoded(), "AES");
		} catch (InvalidKeySpecException | NoSuchAlgorithmException e)
		{
			e.printStackTrace();
			MainRegistry.logger.error("Could not complete key generation task, this should not be possible!", e);
			return null;
		}
	}
	
	private static SecretKey genKey()
	{
		try
		{
			final KeyGenerator generator = KeyGenerator.getInstance("AES");
			generator.init(256);
			return generator.generateKey();
		} catch (NoSuchAlgorithmException e)
		{
			e.printStackTrace();
			MainRegistry.logger.error("Could not complete key generation task, this should not be possible!", e);
			return null;
		}
	}
	
	private static IvParameterSpec genIvSpec()
	{
		final byte[] bytes = new byte[16];
		RANDOM.nextBytes(bytes);
		return new IvParameterSpec(bytes);
	}

	@Override
	protected void writeObject(ObjectOutputStream stream) throws IOException
	{
		stream.defaultWriteObject();
	}
	
	@Override
	protected void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException
	{
		stream.defaultReadObject();
	}
	
	@Override
	public long getSize()
	{
		return serialize().length;
	}
	
	@Override
	public byte[] serialize()
	{
		final ByteBuf buf = alloc.get();
		buf.writeBoolean(invalid);
		if (!invalid)
		{
			final byte[] specBytes = spec.getIV();
			final byte[] keyBytes = secretKey.getEncoded();
			buf.writeBytes(specBytes);
			buf.writeBytes(keyBytes);
		}
		return buf.array();
	}

	@Override
	public AESEncryptionKey deserialize(byte[] bytes) throws DeserializationException
	{
		final ByteBuf buf = allocCopy.apply(bytes);
		if (buf.readBoolean())
			throw new DeserializationException("The stored file is corrupt.");
		
		final byte[] specBytes = new byte[16];
		final byte[] keyBytes = new byte[32];
		buf.readBytes(specBytes);
		buf.readBytes(keyBytes);
		return new AESEncryptionKey(new SecretKeySpec(keyBytes, "AES"), new IvParameterSpec(specBytes));
	}

	@Override
	public int hashCode()
	{
		return 31 * Arrays.hashCode(spec.getIV()) * 7 * Arrays.hashCode(secretKey.getEncoded());
	}

	@Override
	public boolean equals(Object obj)
	{
		if (obj == null || !(obj instanceof AESEncryptionKey))
			return false;
		
		final AESEncryptionKey test = (AESEncryptionKey) obj;
		return Arrays.equals(spec.getIV(), test.spec.getIV()) && Arrays.equals(secretKey.getEncoded(), test.secretKey.getEncoded()) && invalid == test.invalid;
	}

	@Override
	public AESEncryptionKey clone()
	{
		return new AESEncryptionKey(secretKey, spec);
	}

}
