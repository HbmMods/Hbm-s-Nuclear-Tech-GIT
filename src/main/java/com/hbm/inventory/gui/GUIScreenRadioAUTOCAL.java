package com.hbm.inventory.gui;

import java.awt.Desktop;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

import org.lwjgl.opengl.GL11;

import com.hbm.lib.RefStrings;
import com.hbm.main.MainRegistry;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.toserver.NBTControlPacket;
import com.hbm.tileentity.network.TileEntityRadioAUTOCAL;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;

public class GUIScreenRadioAUTOCAL extends GuiScreen {
	
	protected static final ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/machine/gui_rtty_autocal.png");
	protected TileEntityRadioAUTOCAL autocal;
	
	protected int xSize = 150;
	protected int ySize = 90;
	protected int guiLeft;
	protected int guiTop;

	public GUIScreenRadioAUTOCAL(TileEntityRadioAUTOCAL autocal) {
		this.autocal = autocal;
	}

	@Override
	public void initGui() {
		super.initGui();
		this.guiLeft = (this.width - this.xSize) / 2;
		this.guiTop = (this.height - this.ySize) / 2;
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		this.drawDefaultBackground();
		this.drawGuiContainerBackgroundLayer(f, mouseX, mouseY);
		GL11.glDisable(GL11.GL_LIGHTING);
		this.drawGuiContainerForegroundLayer(mouseX, mouseY);
		GL11.glEnable(GL11.GL_LIGHTING);
	}

	@Override
	protected void mouseClicked(int x, int y, int i) {
		super.mouseClicked(x, y, i);
		
		NBTTagCompound data = null;

		if(checkClick(x, y, 8, 36, 18, 18)) { data = new NBTTagCompound(); data.setBoolean("on", true); }
		if(checkClick(x, y, 28, 36, 18, 18)) { data = new NBTTagCompound(); data.setBoolean("ignore", true); }
		if(checkClick(x, y, 48, 36, 18, 18)) { data = new NBTTagCompound(); data.setBoolean("auto", true); }
		
		// open folder and generate new script file
		if(checkClick(x, y, 104, 36, 18, 18)) {
			try {
				File uploadFolder = new File(MainRegistry.configDir.getParentFile(), "hbmComputerUpload");
				File script = new File(uploadFolder, "script.txt");
				if(!uploadFolder.exists()) uploadFolder.mkdir();
				if(!script.exists()) script.createNewFile();
				script.setExecutable(false);
				Desktop.getDesktop().browse(script.toURI());
			} catch(Throwable ex) { MainRegistry.logger.error("Couldn't open link", ex); }
		}
		
		if(checkClick(x, y, 84, 36, 18, 18)) {
			try {
				File uploadFolder = new File(MainRegistry.configDir.getParentFile(), "hbmComputerUpload");
				File script = new File(uploadFolder, "script.txt");
				if(!uploadFolder.exists()) uploadFolder.mkdir();
				if(!script.exists()) {
					script.createNewFile();
					script.setExecutable(false);
					return;
				}
				/*FileReader reader = new FileReader(script);
				BufferedReader buffer = new BufferedReader(reader);
				String[] lines = buffer.lines().toArray(String[]::new);
				buffer.close();
				// this is going to blow the fuck up once we hit the max packet size, but let's ignore that for now
				data = new NBTTagCompound();
				StringBuilder builder = new StringBuilder();
				for(int l = 0; l < lines.length; l++) {
					builder.append(lines[i]);
					if(l < lines.length - 1) builder.append("\n"); // yeah why the fuck not
				}
				data.setString("payload", builder.toString());*/
				byte[] bytes = Files.readAllBytes(Paths.get(script.toURI()));
				data = new NBTTagCompound();
				data.setString("payload", new String(bytes, StandardCharsets.UTF_8));
				
			} catch(Throwable ex) { }
		}
		
		// this thing can both upload and download files so let's be careful about this
		// the upload is simple, it's just text that is handled by the AUTOCAL, so doing anything malicious isn't more likely than with any other package
		// download is iffy, because we take text from the server, fully user-definable, and save it to disk. it's stored as a txt so accudentally running it
		// or getting it to run itself, should it be a malicious script, is unlikely. still, we want to minimized the chances as much as we can
		// option 1: set file attribute to disallow running (i.e. disable executable perm)
		// option 2: add fluff that would break scripts, however they might work. we can't change the actual lines because we want the script to be edited,
		//           but we can add some extra crap that would either halt common scripting langs entirely or at least disrupt them into not functioning
		// option 3: enforce validation so only MS-ES1 script can be received by the client. this means that info such as comments or incorrectly written commands
		//           are lost, however this is the safest way because it becomes impossible to send malicious code, but it also interferes with regular user operation more
		
		if(data != null) {
			mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
			PacketDispatcher.wrapper.sendToServer(new NBTControlPacket(data, autocal.xCoord, autocal.yCoord, autocal.zCoord));
		}
	}
	
	protected boolean checkClick(int x, int y, int left, int top, int sizeX, int sizeY) {
		return guiLeft + left <= x && guiLeft + left + sizeX > x && guiTop + top < y && guiTop + top + sizeY >= y;
	}

	private void drawGuiContainerForegroundLayer(int x, int y) {

		if(checkClick(x, y, 8, 36, 18, 18)) this.func_146283_a(Arrays.asList(new String[] {EnumChatFormatting.RED + "ON/OFF"}), x, y);
		if(checkClick(x, y, 28, 36, 18, 18)) this.func_146283_a(Arrays.asList(new String[] {EnumChatFormatting.RED + "Ignore Errors", "Skips instructions that error,", "leaving the computer turned on.", "May cause unintended behavior", "and inconsistencies."}), x, y);
		if(checkClick(x, y, 48, 36, 18, 18)) this.func_146283_a(Arrays.asList(new String[] {EnumChatFormatting.RED + "Automatic Reboot", "Restarts the computer automatically when", "the program stops due to an error", "or after finishing."}), x, y);

		if(checkClick(x, y, 84, 36, 18, 18)) this.func_146283_a(Arrays.asList(new String[] {EnumChatFormatting.BLUE + "Upload Program"}), x, y);
		if(checkClick(x, y, 104, 36, 18, 18)) this.func_146283_a(Arrays.asList(new String[] {EnumChatFormatting.BLUE + "Open Program File"}), x, y);
		if(checkClick(x, y, 124, 36, 18, 18)) this.func_146283_a(Arrays.asList(new String[] {EnumChatFormatting.BLUE + "Download Program", EnumChatFormatting.RED + "Currently unsupported!"}), x, y);
	}

	private void drawGuiContainerBackgroundLayer(float f, int mouseX, int mouseY) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

		if(autocal.isOn) drawTexturedModalRect(guiLeft + 8, guiTop + 36, 150, 0, 18, 18);
		if(!autocal.ignoreError) drawTexturedModalRect(guiLeft + 28, guiTop + 36, 150, 18, 18, 18);
		if(!autocal.autoReboot) drawTexturedModalRect(guiLeft + 48, guiTop + 36, 150, 36, 18, 18);
	}

	@Override
	protected void keyTyped(char c, int b) {
		if(b == 1 || b == Minecraft.getMinecraft().gameSettings.keyBindInventory.getKeyCode()) {
			Minecraft.getMinecraft().thePlayer.closeScreen();
		}
	}
	
	@Override public boolean doesGuiPauseGame() { return false; }
}
