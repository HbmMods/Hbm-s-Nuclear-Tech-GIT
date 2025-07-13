package com.hbm.util;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import javax.imageio.ImageIO;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class ColorUtil {

	@SideOnly(Side.CLIENT)
	public static BufferedImage getImageFromStack(ItemStack stack) throws IOException {
		String iconName = stack.getItem().getIconFromDamage(stack.getItemDamage()).getIconName();
		String domain = "minecraft";

		if(iconName.contains(":")) {
			String[] parts = iconName.split(":");
			domain = parts[0];
			iconName = parts[1];
		}

		ResourceLocation loc = new ResourceLocation(domain, "textures/items/" + iconName + ".png");

		return ImageIO.read(Minecraft.getMinecraft().getResourceManager().getResource(loc).getInputStream());
	}

	@SideOnly(Side.CLIENT)
	public static int getAverageColorFromStack(ItemStack stack) {

		try {
			BufferedImage tex = getImageFromStack(stack);

			int r = 0;
			int g = 0;
			int b = 0;
			int pixels = 0;

			for(int i = 0; i < tex.getWidth(); i++) {
				for(int j = 0; j < tex.getHeight(); j++) {

					Color pixel = new Color(tex.getRGB(i, j));

					if(pixel.getAlpha() == 255) {
						r += pixel.getRed();
						g += pixel.getGreen();
						b += pixel.getBlue();
						pixels++;
					}
				}
			}

			int avgR = r / pixels;
			int avgG = g / pixels;
			int avgB = b / pixels;

			return (avgR << 16) | (avgG << 8) | avgB;

		} catch(Exception ex) {
			return 0xFFFFFF;
		}
	}

	@SideOnly(Side.CLIENT)
	public static int getMedianBrightnessColorFromStack(ItemStack stack) {

		try {
			BufferedImage tex = getImageFromStack(stack);

			HashMap<Integer, Color> brightMap = new HashMap();
			List<Integer> brightnesses = new ArrayList();

			for(int i = 0; i < tex.getWidth(); i++) {
				for(int j = 0; j < tex.getHeight(); j++) {

					Color pixel = new Color(tex.getRGB(i, j));
					int brightness = pixel.getRed() * pixel.getRed() + pixel.getGreen() * pixel.getGreen() + pixel.getBlue() * pixel.getBlue();
					brightnesses.add(brightness);
					brightMap.put(brightness, pixel); //overlap possible, but we don't differentiate between colors anyway.
				}
			}

			Collections.sort(brightnesses);
			int median = brightnesses.get(brightnesses.size() / 2);
			Color medianColor = brightMap.get(median);

			return medianColor.getRGB();

		} catch(Exception ex) {
			return 0xFFFFFF;
		}
	}

	/**
	 * Decides whether a color is considered "colorful", i.e. weeds out colors that are too dark or too close to gray.
	 * @param hex
	 * @return
	 */
	public static boolean isColorColorful(int hex) {
		Color color = new Color(hex);

		/*double r = color.getRed();
		double g = color.getBlue();
		double b = color.getGreen();

		if(r < 50 && g < 50 && b < 50)
			return false;

		if(r / g > 1.5) return true;
		if(r / b > 1.5) return true;
		if(g / r > 1.5) return true;
		if(g / b > 1.5) return true;
		if(b / r > 1.5) return true;
		if(b / g > 1.5) return true;*/

		float[] hsb = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), new float[3]);

		//     saturation       brightness
		return hsb[1] > 0.25 && hsb[2] > 0.25;
	}

	/**
	 * Raises the highest RGB component to the specified limit, scaling the other components with it.
	 * @param hex
	 * @param limit
	 * @return
	 */
	public static int amplifyColor(int hex, int limit) {
		Color color = new Color(hex);
		int r = color.getRed();
		int g = color.getGreen();
		int b = color.getBlue();
		int max = Math.max(Math.max(1, r), Math.max(g, b));

		r = r * limit / max;
		g = g * limit / max;
		b = b * limit / max;

		return new Color(r, g, b).getRGB();
	}

	/**
	 * Same as the regular amplifyColor but it uses 255 as the limit.
	 * @param hex
	 * @return
	 */
	public static int amplifyColor(int hex) {
		return amplifyColor(hex, 255);
	}

	/**
	 * Amplifies a given color by approaching all components to maximum by a given percentage. A percentage of 1 (100%) should always yield white.
	 * @param hex
	 * @param percent
	 * @return
	 */
	public static int lightenColor(int hex, double percent) {
		Color color = new Color(hex);
		int r = color.getRed();
		int g = color.getGreen();
		int b = color.getBlue();

		r = (int) (r + (255 - r) * percent);
		g = (int) (g + (255 - g) * percent);
		b = (int) (b + (255 - b) * percent);

		return new Color(r, g, b).getRGB();
	}

	/** Converts a color into HSB and then returns the brightness component [] */
	public static double getColorBrightness(int hex) {
		Color color = new Color(hex);
		float[] hsb = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), new float[3]);
		return hsb[2];
	}

	public static HashMap<String, Integer> nameToColor = new HashMap() {{
		put("black", 1973019);
		put("red", 11743532);
		put("green", 3887386);
		put("brown", 5320730);
		put("blue", 2437522);
		put("purple", 8073150);
		put("cyan", 2651799);
		put("silver", 11250603);
		put("gray", 4408131);
		put("pink", 14188952);
		put("lime", 4312372);
		put("yellow", 14602026);
		put("lightBlue", 6719955);
		put("magenta", 12801229);
		put("orange", 15435844);
		put("white", 15790320);
	}};

	public static int getColorFromDye(ItemStack stack) {
		List<String> oreNames = ItemStackUtil.getOreDictNames(stack);

		for(String dict : oreNames) {
			if(dict.length() > 3 && dict.startsWith("dye")) {
				String color = dict.substring(3).toLowerCase(Locale.US);
				if(nameToColor.containsKey(color)) return nameToColor.get(color);
			}
		}

		return 0;
	}
}
