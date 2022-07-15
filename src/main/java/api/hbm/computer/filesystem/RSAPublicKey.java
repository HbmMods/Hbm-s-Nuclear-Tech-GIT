package api.hbm.computer.filesystem;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
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

public class RSAPublicKey extends FileBase
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6452344097711580712L;
	private static final Encoder ENCODER = Base64.getEncoder();
	private static final Decoder DECODER = Base64.getDecoder();
	private final PublicKey publicKey;
	public RSAPublicKey(KeyPair pair)
	{
		publicKey = pair.getPublic();
	}
	
	public RSAPublicKey(PublicKey key)
	{
		publicKey = key;
	}
	
	public RSAPublicKey(byte[] bytes) throws DeserializationException
	{
		try
		{
			final X509EncodedKeySpec spec = new X509EncodedKeySpec(bytes);
			final KeyFactory factory = KeyFactory.getInstance("RSA");
			publicKey = factory.generatePublic(spec);
		} catch (InvalidKeySpecException | NoSuchAlgorithmException | NullPointerException e)
		{
			e.printStackTrace();
			throw new DeserializationException(e);
		}
		
	}
	
	public byte[] encryptToBytes(byte[] input)
	{
		try
		{
			final Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.ENCRYPT_MODE, publicKey);
			return cipher.doFinal(input);
		} catch (BadPaddingException | NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException e)
		{
			e.printStackTrace();
			MainRegistry.logger.error("Could not complete encryption task, this should not be possible!", e);
			return emptyBytes;
		}
	}
	
	public String encryptToString(byte[] input)
	{
		return ENCODER.encodeToString(encryptToBytes(input));
	}
	
	public boolean verifySignature(byte[] input)
	{
		try
		{
			final Signature signature = Signature.getInstance("SHA256withRSA");
			signature.initVerify(publicKey);
			return signature.verify(input);
		} catch (InvalidKeyException | NoSuchAlgorithmException | SignatureException e)
		{
			e.printStackTrace();
			MainRegistry.logger.error("Could not complete signature verification task, this should not be possible!", e);
			return false;
		}
	}
	
	public boolean verifySignature(String input)
	{
		return verifySignature(DECODER.decode(input));
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
		return publicKey.getEncoded();
	}

	@Override
	public RSAPublicKey deserialize(byte[] bytes) throws DeserializationException
	{
		return new RSAPublicKey(bytes);
	}

	@Override
	public int hashCode()
	{
		return Arrays.hashCode(publicKey.getEncoded());
	}

	@Override
	public boolean equals(Object obj)
	{
		if (obj == null || !(obj instanceof RSAPublicKey))
			return false;
		final RSAPublicKey test = (RSAPublicKey) obj;
		return Arrays.equals(publicKey.getEncoded(), test.publicKey.getEncoded());
	}

	@Override
	public RSAPublicKey clone()
	{
		return new RSAPublicKey(publicKey);
	}

}
