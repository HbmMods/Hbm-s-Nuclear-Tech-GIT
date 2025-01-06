package com.hbm.inventory.gui;

import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.IntBuffer;
import java.util.function.Function;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import com.hbm.lib.RefStrings;
import com.hbm.main.MainRegistry;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class GUIScreenWikiRender extends GuiScreen {

    // Basically the same thing as GUIScreenPreview, but will iterate through all provided preview stacks
    // taking a screenshot of each, as fast as the game can render them

	protected static final ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/nei/gui_nei.png");
	protected ItemStack[] preview;
    protected int index = 0;
	protected int scale = 1;
	protected String saveLocation = "wiki-screenshots";
	protected String prefix = "";

	protected Function<ItemStack, String> getStackName = (stack) -> {
		return stack.getDisplayName();
	};

	public GUIScreenWikiRender(ItemStack[] stacks, String prefix, String directory, int scale) {
		this.preview = stacks;
		this.prefix = prefix;
		this.saveLocation = directory;
		this.scale = scale;
	}

	public GUIScreenWikiRender(ItemStack[] stacks, String prefix, String directory, int scale, Function<ItemStack, String> getStackName) {
		this(stacks, prefix, directory, scale);
		this.getStackName = getStackName;
	}

    @Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		if(this.mc.theWorld != null) {
			GuiScreen.drawRect(0, 0, this.width, this.height, 0xFFC6C6C6);
		} else {
			this.drawBackground(0);
		}

        // Once we've reached the end of the array, immedaitely close this GUI
        if(index >= preview.length) {
            this.mc.thePlayer.closeScreen();
            return;
        }
		
		this.drawGuiContainerBackgroundLayer();
		GL11.glDisable(GL11.GL_LIGHTING);
		this.drawGuiContainerForegroundLayer(preview[index]);
		GL11.glEnable(GL11.GL_LIGHTING);
		
		ScaledResolution res = new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);
		int zoom = scale * res.getScaleFactor();

        try {
            String slotName = getStackName.apply(preview[index]).replaceAll("§.", "").replaceAll("[^\\w ().-]+", "");
            if(!slotName.endsWith(".name")) {
                saveScreenshot(Minecraft.getMinecraft().mcDataDir, saveLocation, prefix + slotName + ".png", zoom, zoom, zoom * 16, zoom * 16, 0xFF8B8B8B);
            }
        } catch (Exception ex) {
            // Just skip any failures caused by display name or rendering
        }

        index++;
    }

	protected void drawGuiContainerBackgroundLayer() {
		GL11.glPushMatrix();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(texture);
		ScaledResolution res = new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);
		GL11.glScaled(scale, scale, scale);
        this.drawTexturedModalRect(0, res.getScaledHeight_double() / scale - 18D, 5, 87, 18, 18);
		GL11.glPopMatrix();
	}

	public void drawTexturedModalRect(double x, double y, int sourceX, int sourceY, int sizeX, int sizeY) {
		double f = 0.00390625D;
		double f1 = 0.00390625D;
		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV((double) (x + 0), (double) (y + sizeY), (double) this.zLevel, (double) ((float) (sourceX + 0) * f), (double) ((float) (sourceY + sizeY) * f1));
		tessellator.addVertexWithUV((double) (x + sizeX), (double) (y + sizeY), (double) this.zLevel, (double) ((float) (sourceX + sizeX) * f), (double) ((float) (sourceY + sizeY) * f1));
		tessellator.addVertexWithUV((double) (x + sizeX), (double) (y + 0), (double) this.zLevel, (double) ((float) (sourceX + sizeX) * f), (double) ((float) (sourceY + 0) * f1));
		tessellator.addVertexWithUV((double) (x + 0), (double) (y + 0), (double) this.zLevel, (double) ((float) (sourceX + 0) * f), (double) ((float) (sourceY + 0) * f1));
		tessellator.draw();
	}

	protected void drawGuiContainerForegroundLayer(ItemStack preview) {
		if(preview == null) return;
		
		GL11.glPushMatrix();
		RenderHelper.enableGUIStandardItemLighting();
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240F, 240F);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		
		GL11.glScaled(scale, scale, scale);
		
		ScaledResolution res = new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);
        GL11.glTranslated(9D, res.getScaledHeight_double() / scale - 9D, -200);

		this.zLevel = 200.0F;
		itemRender.zLevel = 200.0F;

		GL11.glEnable(GL11.GL_DEPTH_TEST);
		itemRender.renderItemAndEffectIntoGUI(this.fontRendererObj, this.mc.getTextureManager(), preview, -8, -8);
		itemRender.renderItemOverlayIntoGUI(this.fontRendererObj, this.mc.getTextureManager(), preview, -8, -8, null);

		itemRender.zLevel = 0.0F;
		this.zLevel = 0.0F;
		
		GL11.glPopMatrix();
	}


	private static IntBuffer pixelBuffer;
	private static int[] pixelValues;

	// This implementation is based directly on ScreenShotHelper.saveScreenshot()
	// But allows for defining a rect where you want to sample pixels from
	private static void saveScreenshot(File dataDir, String ssDir, String fileName, int x, int y, int width, int height, int transparentColor) {
		try {
			File screenshotDirectory = new File(dataDir, ssDir);
			screenshotDirectory.mkdir();

			int bufferSize = width * height;
			if(pixelBuffer == null || pixelBuffer.capacity() < bufferSize) {
				pixelBuffer = BufferUtils.createIntBuffer(bufferSize);
				pixelValues = new int[bufferSize];
			}

			GL11.glPixelStorei(GL11.GL_PACK_ALIGNMENT, 1);
			GL11.glPixelStorei(GL11.GL_UNPACK_ALIGNMENT, 1);
			pixelBuffer.clear();
			GL11.glReadPixels(x, y, width, height, GL12.GL_BGRA, GL12.GL_UNSIGNED_INT_8_8_8_8_REV, pixelBuffer);

			pixelBuffer.get(pixelValues);
			TextureUtil.func_147953_a(pixelValues, width, height);
			BufferedImage imageBuffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
			imageBuffer.setRGB(0, 0, width, height, pixelValues, 0, width);

			// This is the only proper custom part, setting the background of an inventory slot to be transparent
			if(transparentColor != 0) {
				for(int iy = 0; iy < imageBuffer.getHeight(); ++iy) {
					for(int ix = 0; ix < imageBuffer.getWidth(); ++ix) {
						if(imageBuffer.getRGB(ix, iy) == transparentColor) {
							imageBuffer.setRGB(ix, iy, 0);
						}
					}
				}
			}

			File imageFile;
			if(fileName == null) {
				throw new IllegalArgumentException("fileName must not be null");
			} else {
				imageFile = new File(screenshotDirectory, fileName);
			}

			ImageIO.write(imageBuffer, "png", imageFile);
		} catch (Exception ex) {
			MainRegistry.logger.warn("Failed to save NTM screenshot", ex);
		}
	}

}