package com.hbm.main;

import javax.swing.JOptionPane;

import com.hbm.lib.Library;

import net.minecraft.client.Minecraft;

public class KoishiBreachException extends RuntimeException
{
	private static final String[] messages =
		{
				"Koishi on my computer? More likely than you think!",
				"Error: Insufficient shrimp fry (<1)",
				"Hey " + System.getProperty("user.name") + ", I'm right behind you!",
				"Oops, sorry about that one!",
				"Don't leave yet! I still have more skills to show off!",
				"I love not thinking!",
				"Reading people's minds is depressing, there's nothing good about it.",
				"I'm Going to Call You Now, So Answer the Phone!",
				"I could get addicted to this.",
				"I'm telling you to get scared!",
				"COMPUTER OVER! KOISHI = VERY YES!"
		};
	private static final String[] messages11 =
		{
				"Is this what lies beyond Heaven or Hell?",
				"Have you noticed me yet?",
				"Right now I'm nearby. So I'm coming to meet you.",
				"Even if they could see your smashed up face, nobody will come to save you.",
				"From now, I won't let you get away.",
				"God told me my road home is closed off, but that's ok.",
				"Cut you up, cut you two, mix them together. Red... Red... What a waste of time...",
				"All alone in this world.",
				"You, me, and these dreams, finally come to an end.",
				"it hurts it hurts it hurts",
				"How long will you keep dreaming?"
		};
	/**
	 * 
	 */
	private static final long serialVersionUID = 4125023980567616164L;
	
	public KoishiBreachException(boolean b)
	{
		// TODO Auto-generated constructor stub
		this(Library.randomFromArray(MainRegistry.isPolaroid11() ? messages11 : messages), b);
	}

	public KoishiBreachException(String arg0, boolean b)
	{
		super(arg0);
		pauseAndDisplay(b);
		// TODO Auto-generated constructor stub
	}

	public KoishiBreachException(Throwable arg0, boolean b)
	{
		super(arg0);
		pauseAndDisplay(b);
		// TODO Auto-generated constructor stub
	}

	public KoishiBreachException(String arg0, Throwable arg1, boolean b)
	{
		super(arg0, arg1);
		pauseAndDisplay(b);
		// TODO Auto-generated constructor stub
	}

	public KoishiBreachException(String arg0, Throwable arg1, boolean arg2, boolean arg3, boolean b)
	{
		super(arg0, arg1, arg2, arg3);
		pauseAndDisplay(b);
		// TODO Auto-generated constructor stub
	}
	
	private void pauseAndDisplay(boolean b)
	{
		Minecraft.getMinecraft().displayInGameMenu();
		if (b)
			JOptionPane.showMessageDialog(null, getMessage(), "Minecraft has crashed!", JOptionPane.ERROR_MESSAGE);
	}
}
