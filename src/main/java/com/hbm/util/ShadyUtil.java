package com.hbm.util;

import com.google.common.collect.Sets;
import com.hbm.config.GeneralConfig;
import com.hbm.main.MainRegistry;
import com.hbm.main.ModEventHandler;
import cpw.mods.fml.relauncher.ReflectionHelper;

import java.lang.reflect.Field;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * Handles anything regarding hashes, base64 encoding, etc. Shady looking stuff, that is
 * @author hbm
 *
 */
public class ShadyUtil {

	//this is a list of UUIDs used for various things, primarily for accessories.
	//for a comprehensive list, check RenderAccessoryUtility.java
	public static String HbMinecraft = "192af5d7-ed0f-48d8-bd89-9d41af8524f8";
	public static String LPkukin = "937c9804-e11f-4ad2-a5b1-42e62ac73077";
	public static String Dafnik = "3af1c262-61c0-4b12-a4cb-424cc3a9c8c0";
	public static String a20 = "4729b498-a81c-42fd-8acd-20d6d9f759e0";
	public static String LordVertice = "a41df45e-13d8-4677-9398-090d3882b74f";
	public static String CodeRed_ = "912ec334-e920-4dd7-8338-4d9b2d42e0a1";
	public static String dxmaster769 = "62c168b2-d11d-4dbf-9168-c6cea3dcb20e";
	public static String Dr_Nostalgia = "e82684a7-30f1-44d2-ab37-41b342be1bbd";
	public static String Samino2 = "87c3960a-4332-46a0-a929-ef2a488d1cda";
	public static String Hoboy03new = "d7f29d9c-5103-4f6f-88e1-2632ff95973f";
	public static String Dragon59MC = "dc23a304-0f84-4e2d-b47d-84c8d3bfbcdb";
	public static String Steelcourage = "ac49720b-4a9a-4459-a26f-bee92160287a";
	public static String ZippySqrl = "03c20435-a229-489a-a1a1-671b803f7017";
	public static String Schrabby = "3a4a1944-5154-4e67-b80a-b6561e8630b7";
	public static String SweatySwiggs = "5544aa30-b305-4362-b2c1-67349bb499d5";
	public static String Drillgon = "41ebd03f-7a12-42f3-b037-0caa4d6f235b";
	public static String Doctor17 = "e4ab1199-1c22-4f82-a516-c3238bc2d0d1";
	public static String Doctor17PH = "4d0477d7-58da-41a9-a945-e93df8601c5a";
	public static String ShimmeringBlaze = "061bc566-ec74-4307-9614-ac3a70d2ef38";
	public static String FifeMiner = "37e5eb63-b9a2-4735-9007-1c77d703daa3";
	public static String lag_add = "259785a0-20e9-4c63-9286-ac2f93ff528f";
	public static String Pu_238 = "c95fdfd3-bea7-4255-a44b-d21bc3df95e3";
	public static String Tankish = "609268ad-5b34-49c2-abba-a9d83229af03";
	public static String FrizzleFrazzle = "fc4cc2ee-12e8-4097-b26a-1c6cb1b96531";
	public static String the_NCR = "28ae585f-4431-4491-9ce8-3def6126e3c6";
	public static String Barnaby99_x = "b04cf173-cff0-4acd-aa19-3d835224b43d";
	public static String Ma118 = "1121cb7a-8773-491f-8e2b-221290c93d81";
	public static String Adam29Adam29 = "bbae7bfa-0eba-40ac-a0dd-f3b715e73e61";
	public static String Alcater = "0b399a4a-8545-45a1-be3d-ece70d7d48e9";
	public static String ege444 = "42ee978c-442a-4cd8-95b6-29e469b6df10";
	public static String LePeeperSauvage = "433c2bb7-018c-4d51-acfe-27f907432b5e";

	public static final Set<String> hashes = new HashSet();
	static {
		hashes.add("41de5c372b0589bbdb80571e87efa95ea9e34b0d74c6005b8eab495b7afd9994");
		hashes.add("31da6223a100ed348ceb3254ceab67c9cc102cb2a04ac24de0df3ef3479b1036");
	}

	public static final int c = 0x3d;
	public static String initializer =	"Ur bp7mN-@UFZKXBx9N[/>M'k\\7\\9m3b";
	public static String signature =	"dYPq\\YzrNpfn[ZDxdk7PS2jhTY72cZT7SoH|\\WL3dIznfC";
	public static String mask =			"E#?V,%l!nb4 ik_wJ@(&k4o>Wq";
	public static String checksum =		"dpXt\\Xnr\\Yzm";
	public static String testCase =		"dYPq\\YzrNm3FUH;P[ZTq";
	public static String testValue =	"WGm?";
	public static String smTest1 =		"hgwS";
	public static String smTest2 =		"8Sfw";
	public static String smTest3 =		"j11D";
	public static String smTest4 =		"s783";

	public static Set<String> contributors = Sets.newHashSet(new String[] {
			"06ab7c03-55ce-43f8-9d3c-2850e3c652de", //mustang_rudolf
			"5bf069bc-5b46-4179-aafe-35c0a07dee8b", //JMF781
			"ccd9aa1c-26b9-4dde-8f37-b96f8d99de22", //kakseao
			});

	// simple cryptographic utils
	public static String encode(String msg) { return Base64.getEncoder().encodeToString(msg.getBytes()); }
	public static String decode(String msg) { return new String(Base64.getDecoder().decode(msg)); }

	public static String offset(String msg, int o) {
		byte[] bytes = msg.getBytes();
		for(int i = 0; i < bytes.length; i++) {
			bytes[i] += o;
		}
		return new String(bytes);
	}

	/** Encryptor for the h-cat answer strings */
	public static String smoosh(String s1, String s2, String s3, String s4) {

		Random rand = new Random();
		String s = "";

		byte[] b1 = s1.getBytes();
		byte[] b2 = s2.getBytes();
		byte[] b3 = s3.getBytes();
		byte[] b4 = s4.getBytes();

		if(b1.length == 0 || b2.length == 0 || b3.length == 0 || b4.length == 0) return "";

		s += s1;
		rand.setSeed(b1[0]);
		s += rand.nextInt(0xffffff);
		s += s2;
		rand.setSeed(rand.nextInt(0xffffff) + b2[0]);
		rand.setSeed(b2[0]);
		s += rand.nextInt(0xffffff);
		s += s3;
		rand.setSeed(rand.nextInt(0xffffff) + b3[0]);
		rand.setSeed(b3[0]);
		s += rand.nextInt(0xffffff);
		s += s4;
		rand.setSeed(rand.nextInt(0xffffff) + b4[0]);
		rand.setSeed(b4[0]);
		s += rand.nextInt(0xffffff);
		return getHash(s);
	}

	/** Simple SHA256 call */
	public static String getHash(String inp) {
		try {
			MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
			byte[] bytes = sha256.digest(inp.getBytes());
			String str = "";
			for(int b : bytes) str = str + Integer.toString((b & 0xFF) + 256, 16).substring(1);
			return str;
		} catch(NoSuchAlgorithmException e) { }
		return "";
	}

	public static void test() {
		if(!GeneralConfig.enableDebugMode) return; //only run in debug mode
		
		//unit test for smooshing
		MainRegistry.logger.debug(smoosh(smTest1, smTest2, smTest3, smTest4));

		try {
			Class test = Class.forName(decode(offset(signature, -2)));
			Field field = ReflectionHelper.findField(test, decode(offset(checksum, -2)));
			if(field != null) {
				System.out.println("TEST SECTION START");
				Class toLoad = Class.forName(decode(offset(testCase, -2)));
				Field toRead = ReflectionHelper.findField(toLoad, decode(offset(testValue, -2)));
				ModEventHandler.reference = toRead;
				System.out.println("TEST SECTION END");
			}
		} catch(Throwable e) { }
	}
}
