package api.hbm.computer.filesystem;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Arrays;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import com.hbm.main.DeserializationException;
import com.hbm.main.MainRegistry;

public class RSAPrivateKey extends FileBase
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2968529720757558000L;
	private static final Decoder DECODER = Base64.getDecoder();
	private static final Encoder ENCODER = Base64.getEncoder();
	private final PrivateKey privateKey;
	public RSAPrivateKey(KeyPair pair)
	{
		privateKey = pair.getPrivate();
	}
	
	public RSAPrivateKey(PrivateKey key)
	{
		privateKey = key;
	}
	
	public RSAPrivateKey(byte[] bytes) throws DeserializationException
	{
		try
		{
			final PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(bytes);
			final KeyFactory factory = KeyFactory.getInstance("RSA");
			privateKey = factory.generatePrivate(spec);
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e)
		{
			e.printStackTrace();
			throw new DeserializationException(e);
		}
	}
	
	public byte[] decryptFromStringToBytes(String input)
	{
		return decryptToBytes(DECODER.decode(input));
	}
	
	public String decryptFromStringToString(String input)
	{
		return new String(decryptFromStringToBytes(input));
	}
	
	public byte[] decryptToBytes(byte[] input)
	{
		try
		{
			final Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.DECRYPT_MODE, privateKey);
			return cipher.doFinal(input);
		} catch (BadPaddingException | NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException e)
		{
			e.printStackTrace();
			MainRegistry.logger.error("Could not complete decryption task, this should not be possible!", e);
			return emptyBytes;
		}
	}
	
	public String decryptToString(byte[] input)
	{
		return new String(DECODER.decode(decryptToBytes(input)));
	}
	
	public byte[] signToBytes(byte[] input)
	{
		try
		{
			final Signature signature = Signature.getInstance("SHA256withRSA");
			signature.initSign(privateKey);
			signature.update(input);
			return signature.sign();
		} catch (InvalidKeyException | NoSuchAlgorithmException | SignatureException e)
		{
			e.printStackTrace();
			MainRegistry.logger.error("Could not complete signature task, this should not be possible!", e);
			return emptyBytes;
		}
	}
	
	public String signToString(byte[] input)
	{
		return ENCODER.encodeToString(signToBytes(input));
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
		return privateKey.getEncoded();
	}

	@Override
	public RSAPrivateKey deserialize(byte[] bytes) throws DeserializationException
	{
		return new RSAPrivateKey(bytes);
	}

	@Override
	public int hashCode()
	{
		return Arrays.hashCode(serialize());
	}

	@Override
	public boolean equals(Object obj)
	{
		if (obj == null || !(obj instanceof RSAPrivateKey))
			return false;
		final RSAPrivateKey test = (RSAPrivateKey) obj;
		return Arrays.equals(privateKey.getEncoded(), test.privateKey.getEncoded());
	}

	@Override
	public RSAPrivateKey clone()
	{
		return new RSAPrivateKey(privateKey);
	}

}
