package api.hbm.computer.filesystem;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;

import com.hbm.main.DeserializationException;
import com.hbm.util.Tuple.Pair;

import io.netty.buffer.ByteBuf;

public class RSAKeyPair extends FileBase
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1371245428681698673L;
	private static final Decoder DECODER = Base64.getDecoder();
	private static final Encoder ENCODER = Base64.getEncoder();
//	private KeyPair keyPair;
	private final Pair<RSAPublicKey, RSAPrivateKey> pair;
	private boolean invalid = false;
	public RSAKeyPair() throws NoSuchAlgorithmException
	{
		final KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
		generator.initialize(2048);
		final KeyPair keyPair = generator.generateKeyPair();
		final RSAPublicKey pubKey = new RSAPublicKey(keyPair);
		final RSAPrivateKey privKey = new RSAPrivateKey(keyPair);
		pair = new Pair<RSAPublicKey, RSAPrivateKey>(pubKey, privKey);
	}
	
	public RSAKeyPair(KeyPair pair)
	{
		this.pair = new Pair<RSAPublicKey, RSAPrivateKey>(new RSAPublicKey(pair), new RSAPrivateKey(pair));
	}
	
	public RSAKeyPair(Pair<RSAPublicKey, RSAPrivateKey> pair)
	{
		this.pair = pair;
	}
	
	public RSAKeyPair(PublicKey publicKey, PrivateKey privateKey)
	{
		pair = new Pair<RSAPublicKey, RSAPrivateKey>(new RSAPublicKey(publicKey), new RSAPrivateKey(privateKey));
	}
	
	public RSAKeyPair(RSAPublicKey publicKey, RSAPrivateKey privateKey)
	{
		pair = new Pair<RSAPublicKey, RSAPrivateKey>(publicKey, privateKey);
	}
	
	public boolean isKeyValid()
	{
		return !invalid;
	}

	public byte[] encryptToBytes(byte[] input)
	{
		return pair.getKey().encryptToBytes(input);
	}
	
	public String encryptToString(byte[] input)
	{
		return ENCODER.encodeToString(encryptToBytes(input));
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
		return pair.getValue().decryptToBytes(input);
	}
	
	public String decryptToString(byte[] input)
	{
		return new String(DECODER.decode(decryptToBytes(input)));
	}
	
	public byte[] signToBytes(byte[] input)
	{
		return pair.getValue().signToBytes(input);
	}
	
	public String signToString(byte[] input)
	{
		return pair.getValue().signToString(input);
	}
	
	public Pair<byte[], byte[]> encryptAndSignToBytes(byte[] input)
	{
		return new Pair<byte[], byte[]>(encryptToBytes(input), signToBytes(input));
	}
	
	public Pair<String, String> encryptAndSignToString(byte[] input)
	{
		return new Pair<String, String>(encryptToString(input), signToString(input));
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
		return pair.getKey().getSize() + pair.getValue().getSize();
	}
	
	@Override
	public byte[] serialize()
	{
		return alloc.get().writeBytes(pair.getKey().serialize()).writeBytes(pair.getValue().serialize()).array();
	}
	
	@Override
	public RSAKeyPair deserialize(byte[] bytes) throws DeserializationException
	{
		try
		{
			final byte[] privateKeyBytes = new byte[1216];
			final byte[] publicKeyBytes = new byte[294];
			final ByteBuf buf = allocCopy.apply(bytes);
			buf.readBytes(publicKeyBytes);
			buf.readBytes(privateKeyBytes);
			
			final RSAPublicKey publicKey = new RSAPublicKey(publicKeyBytes);
			final RSAPrivateKey privateKey = new RSAPrivateKey(privateKeyBytes);
			
			return new RSAKeyPair(publicKey, privateKey);
		} catch (Exception e)
		{
			e.printStackTrace();
			throw new DeserializationException(e);
		}
	}

	@Override
	public int hashCode()
	{
		return pair.hashCode();
	}

	@Override
	public boolean equals(Object obj)
	{
		if (obj == null || !(obj instanceof RSAKeyPair))
			return false;
		
		final RSAKeyPair test = (RSAKeyPair) obj;
		return pair.equals(test.pair);
	}

	@Override
	public RSAKeyPair clone()
	{
		return new RSAKeyPair(pair);
	}

}
