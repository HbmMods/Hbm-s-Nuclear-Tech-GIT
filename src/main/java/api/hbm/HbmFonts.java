package api.hbm;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.LoaderState;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.FontRenderer;

public class HbmFonts {
	
	@SideOnly(Side.CLIENT)
	private static FontRenderer helveticaFont;
	
	@SideOnly(Side.CLIENT)
	public static void setHelveticaFont(FontRenderer font) {
		if(helveticaFont == null && Loader.instance().getLoaderState() == LoaderState.INITIALIZATION 
				&& Loader.instance().activeModContainer().getModId().equals("hbm"))
			helveticaFont = font;
	}
	
	@SideOnly(Side.CLIENT)
	public static FontRenderer getHelveticaFont() {
		return helveticaFont;
	}
	
	
}