package com.hbm.inventory.gui;

import java.awt.Desktop;
import java.io.File;
import java.io.PrintWriter;
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
	
	protected int xSize = 170;
	protected int ySize = 138;
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
		
		// open folder and generate new doc file
		if(checkClick(x, y, 144, 36, 18, 18)) {
			try {
				File uploadFolder = new File(MainRegistry.configDir.getParentFile(), "hbmComputerUpload");
				File doc = new File(uploadFolder, "documentation.md");
				if(!uploadFolder.exists()) uploadFolder.mkdir();
				if(!doc.exists()) {
					doc.createNewFile();
					try {
						PrintWriter printer = new PrintWriter(doc, StandardCharsets.US_ASCII.name());
						for(String line : DOCS) printer.println(line);
						printer.close();
					} catch(Throwable e) { }
				}
				Desktop.getDesktop().browse(doc.toURI());
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
		
		for(int i = 0; i < autocal.history.length; i++) {
			String line = autocal.history[i];
			if(line == null || line.isEmpty()) continue;
			this.fontRendererObj.drawString(line, guiLeft + 7, guiTop + 73 + i * 10, 0x00ff00);
		}

		if(checkClick(x, y, 8, 36, 18, 18)) this.func_146283_a(Arrays.asList(new String[] {EnumChatFormatting.RED + "ON/OFF"}), x, y);
		if(checkClick(x, y, 28, 36, 18, 18)) this.func_146283_a(Arrays.asList(new String[] {EnumChatFormatting.RED + "Ignore Errors", "Skips instructions that error,", "leaving the computer turned on.", "May cause unintended behavior", "and inconsistencies."}), x, y);
		if(checkClick(x, y, 48, 36, 18, 18)) this.func_146283_a(Arrays.asList(new String[] {EnumChatFormatting.RED + "Automatic Reboot", "Restarts the computer automatically when", "the program stops due to an error", "or after finishing."}), x, y);

		if(checkClick(x, y, 84, 36, 18, 18)) this.func_146283_a(Arrays.asList(new String[] {EnumChatFormatting.BLUE + "Upload Program"}), x, y);
		if(checkClick(x, y, 104, 36, 18, 18)) this.func_146283_a(Arrays.asList(new String[] {EnumChatFormatting.BLUE + "Open Program File"}), x, y);
		if(checkClick(x, y, 124, 36, 18, 18)) this.func_146283_a(Arrays.asList(new String[] {EnumChatFormatting.BLUE + "Download Program", EnumChatFormatting.RED + "Currently unsupported!"}), x, y);
		if(checkClick(x, y, 144, 36, 18, 18)) this.func_146283_a(Arrays.asList(new String[] {EnumChatFormatting.BLUE + "Open Documentation"}), x, y);
	}

	private void drawGuiContainerBackgroundLayer(float f, int mouseX, int mouseY) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

		if(autocal.isOn) drawTexturedModalRect(guiLeft + 8, guiTop + 36, xSize, 0, 18, 18);
		if(!autocal.ignoreError) drawTexturedModalRect(guiLeft + 28, guiTop + 36, xSize, 18, 18, 18);
		if(!autocal.autoReboot) drawTexturedModalRect(guiLeft + 48, guiTop + 36, xSize, 36, 18, 18);
	}

	@Override
	protected void keyTyped(char c, int b) {
		if(b == 1 || b == Minecraft.getMinecraft().gameSettings.keyBindInventory.getKeyCode()) {
			Minecraft.getMinecraft().thePlayer.closeScreen();
		}
	}
	
	@Override public boolean doesGuiPauseGame() { return false; }
	
	
	public static final String[] DOCS = new String[] {
			"# AUTOCAL - The Automatic Calculator",
			"",
			"## About this document",
			"This documentation is designed to be understandable even by people with no programming background. This documentation extends to the AUTOCAL unit as well as the MS-ES1 script language it is programmed with.",	
			"",
			"Read this document carefully as all the described concepts are vital for using the AUTOCAL unit.",
			"",
			"## About AUTOCAL",
			"The AUTOCAL automatic calculator is a basic machine that reads a script, line by line, and performs actions based on those lines. This means it can be programmed with behavior, doing math, and handling signals. It communicated with the outside world exclusively using Redstone-over-Radio (RoR) signals, allowing RoR to supply it with values to be processed, and returning RoR signals to be displayed or for controlling RoR receiving devices.",
			"",
			"The first button is the on/off switch. Turning the AUTOCAL unit on will start the script at the first line. If the script concludes, an error is encountered (without the \"ignore errors\" setting enabled), the `shutdown` command is used or no program is loaded to begin with, the unit will automatically power down.",
			"",
			"The second button is the \"ignore errors\" setting. The red X means the setting is disabled, if an error is encountered in the program (an unrecognized command, missing or incorrect parameters, anything the script isn't meant to handle) then the unit will shut off. Otherwise, the erroring instruction is simply skipped. This may cause undefined or unexpected behavior, however in certain circumstances this doesn't matter. It's advised to keep this turned off, as it makes it easier to find obvious errors in the script.",
			"",
			"The third button is the \"automatic reboot\" setting. If the unit is powered down, no matter the reason, it will automatically try to start again. This allows for a simple \"loop\" where the same script is repeated every time it ends by simply rebooting the AUTOCAL unit at the end. Do note that doing so will delete all saved variables, more on how variables work later.",
			"",
			"The fourth button is for uploading the script. The script is in the minecraft's install folder, under the `hbmComputerUpload` folder, named `script.txt`. Clicking this button will send the contents of this script file to the AUTOCAL unit.",
			"",
			"The fifth button is for opening the script file. If no script file exists yet, the folder and empty script will be created.",
			"",
			"The sixth button is for downloading a script file. The existing `script.txt` is deleted and replaced with the script that is loaded onto the AUTOCAL unit.",
			"",
			"The simple workflow of programming an AUTOCAL unit is therefore using button #5 to open the file, writing the program, using #4 to flash the program to the AUTOCAL unit, and then using the first button to turn it on.",
			"",
			"## About MS-ES1",
			"The script read by AUTOCAL units is written in *Machine Script - Equestrian Standard, Version 1*, or MS-ES1. MS-ES1 features named variables, value comparison, (conditional) jumping, evaluation of mathematical expressions and reading/writing of RoR signals. The speed at which lines are processed is defined by the *clock speed*, which can be defined in the script. The amount describes the number of lines processed per tick, i.e. the default clock speed of 1 means 20 lines are processed per second (there are 20 ticks in one second). The maximum clock speed is determined by the server config value `AUTOCAL_MAX_CLOCK` (default is 20, i.e. 400 per second).",
			"",
			"Example: `/ntmserver set AUTOCAL_MAX_CLOCK 10` -> Sets the max clock speed to 10.",
			"",
			"## About the Buffer",
			"The buffer is a single \"slot\" of information that can be used for many commands. Some commands produce an output which is saved to the buffer, some commands modify the contents of the buffer, and some commands use the buffer's contents. The buffer's contents can also be saved permanently as a named variable, and named variables can be written back into the buffer again if needed. The buffer persists as long as the AUTOCAL unit is running, should it restart or shut down, the buffer's contents are lost.",
			"",
			"## About Variables",
			"MS-ES1 allows named variables to be saved for later use. There is no limit to how many variables can be saved. All variables are text (\"Strings\"), however depending on the command and context, that text may be interpreted as a number (both full and decimal). Variables, much like the buffer, are also stored as long as the program is running, if the AUTOCAL unit shuts down, all stored variables are lost.",
			"",
			"## About Variable Substitution",
			"Many commands allow for *variable substitution*, i.e. a specific format can be used to insert the contents of a variable (or multiple!) into a parameter. This allows for the quick use of variables, or multiple variables in the same statement. Substitution is defined by `$variable name$`, where this text is replaced with the value of a variable called \"variable name\".",
			"",
			"Example: `eval $val1$ + $val2$` assuming `val1` is 4 and `val2` is 8 would resolve to `eval 4 + 8`.",
			"",
			"Special case: The contents of the buffer can also be accessed using substitution using `$buffer$`. Consequently, a variable also named \"buffer\" can **not** be accessed using variable substitution at all.",
			"",
			"## About Redstone-over-Radio",
			"RoR has a specific limitation: A signal cannot be sent on the same channel within the same game tick. Subsequent signals on the same channel in the same tick will overwrite the previous one, with the exception of numeric (whole number) signals which are added together (e.g. sending \"5\" and \"7\" in the same tick creates a signal \"12\"). Since the AUTOCAL unit's clock speed allows it to theoretically send multiple signals on the same channel on the same tick, it might be necessary to *end* the operation for this tick even though there's still clock cycles left to do. This can be done with the `endtick` command.",
			"",
			"## The Script (Commands)",
			"",
			"### Comments",
			"Lines that start with `# ` (hashtag + space) are comments, and therefore ignored. If the AUTOCAL encounters such a line, it is skipped, not using up the clock cycle.",
			"",
			"Example: `# This is a comment` -> A line that does nothing, but can still be useful to explain and annotate other commands.",
			"",
			"### nop",
			"`nop` (no operation) is an operation that consumes one clock cycle, but does not have any other effect. This is only useful in special cases where clock cycle timing is somehow important.",
			"",
			"### clockspeed",
			"`clockspeed <speed>` sets the AUTOCAL's clock speed (i.e. amount of lines processed per tick). This can be changed at any point in the script, but usually it is most useful to start the script by defining the clock speed.",
			"",
			"Example: `clockspeed 5` -> Sets the AUTOCAL's clock speed to five lines per tick (100 lines per second).",
			"",
			"### dest",
			"`dest <name>` creates a jump destination. Using the various jumping conditions, we can cause the AUTOCAL to return back (or forward) to this point. If a destination is reached not by jumping but simply by reading the next line, it will not use up a clock cycle, just like a comment. Destinations need unique names, if multiple destinations exist with the same name, then the latter ones will overwrite the former ones.",
			"",
			"Example: `dest start` -> Creates a destination point named \"start\", any jump instruction using \"start\" will cause the script to return to this point.",
			"",
			"### jmp",
			"`jmp <destination>` will cause the program to skip to the destination with the supplied name. The jump will always be performed when the script reaches this instruction. Supports variable substitution!",
			"",
			"Example: `jmp start` -> Jumps to the destination point named \"start\".",
			"Example: `jmp $dest$` -> Jumps to the destination point with the same name as the contents of the variable \"dest\".",
			"",
			"### jmpif",
			"`jmpif <destination>` will cause the program to skip to the destination with the supplied name, if the buffer's content is `true`. Otherwise, the program will just proceed to the next line, like any other instruction. Supports variable substitution!",
			"",
			"Example:",
			"`jmpif skip`",
			"`nop`",
			"`dest skip`",
			"`nop`",
			"",
			"-> If the buffer is `true`, then the program will jump to the destination point named \"skip\" and only run the second `nop`. Otherwise, both the first and second `nop` will run.",
			"",
			"### jmpnot",
			"`jmpnot <destination>` will cause the program to skip to the destination with the supplied name, if the buffer's content is **not** `true`. This is basically the inverse of `jmpif`. Only exists for some convenience for people who are used to more traditional languages' `if` statements. Supports variable substitution!",
			"",
			"Example:",
			"`jmpnot skip` <- if",
			"`nop`         <- then",
			"`jmp end`",
			"`dest skip`",
			"`nop`         <- else",
			"`dest end`    <- end",
			"",
			"-> By using two jumps, one conditional and one fixed, we can emulate the structure of an `if/else` block. If the buffer's content is `true`, the first `nop` runs. Otherwise, the second `nop` is run. The second jump (`jmp end`) lets us skip over the \"else\" part of the script after running the \"then\" part.",
			"",
			"### endtick",
			"`endtick` stops the script until the next game tick. This is important when sending multiple RoR signals on the same channel in a row, since a channel in the same tick can only hold one signal.",
			"",
			"### shutdown",
			"`shutdown` will turn the AUTOCAL unit off. If the AUTOCAL is set up to automatically reboot, this effectively restarts the script from scratch, voiding all saved variables and the buffer. If not, then the AUTOCAL unit will stay off until manually restarted.",
			"",
			"### load",
			"`load <name>` will take the value of a variable with the supplied name and copy it to the buffer. Many commands work directly out of the buffer, so next to variable substitution, this is the only way of actually accessing the contents of a variable.",
			"",
			"Example: `load val` -> Assuming that the variable \"val\" contains the value `5`, then the buffer's value is now overwritten with that `5`.",
			"",
			"### save",
			"`save <name>` will take the value of the buffer and save it to a variable with the supplied name. This is the only way of actually changing variables, and saving values for later use besides the buffer.",
			"",
			"Example: `save val` -> Assuming that the buffer contains the value `12`, this will create a new variable named \"val\" with the value of `12`.",
			"",
			"### buffer",
			"`buffer <value>` will write the supplied value directly to the buffer. This means that commands that require buffer values can be supplied with values directly from the code.",
			"",
			"Example:",
			"`buffer Horseshoe`",
			"`save item`",
			"",
			"-> Will buffer the value `Horseshoe` and then save it to the variable called \"item\".",
			"",
			"### eval",
			"`eval [statement]` will evaluate the supplied statement as a mathematical expression. In short, anything NTM's calculator can make sense of (the one you open with N by default), this function can do the same. The statement is optional, if no statement is supplied, then it will try to use the buffer's contents as the statement. `eval` produces **decimal** values and not whole number **integers**, so for use in RoR signals, the output needs to be rounded! The result of the calculation is then saved to the buffer. Supports variable substitution!",
			"",
			"Example: `eval 4 + 5` -> Calculates \"4+5\" and saves `9` to the buffer.",
			"Example: `eval $val$ / 2` -> Takes the value of \"val\" and divides it by 2, saving the result to the buffer.",
			"",
			"Example:",
			"`load calc`",
			"`eval`",
			"",
			"-> Will write the value of \"calc\" to the buffer, and then treat it as a mathematical expression. If we assume \"calc\" to be `5+2` then `eval` will end up writing `7` to the buffer.",
			"",
			"### evalr",
			"`evalr [statement]` is identical to `eval`, however it will round the result to the nearest whole number. Whole numbers are important for RoR, since gauges, numeric displays and logic receivers can only handle whole numbers, and not decimals.",
			"",
			"### rounddown / floor",
			"`rounddown` or `floor` will try to interpret the buffer's content as a decimal, and round it **down** to the next lower integer, writing the result to the buffer again.",
			"",
			"Example: `rounddown` -> Assuming the buffer's value is `4.2`, the buffer's new value will be `4`.",
			"Example: `rounddown` -> Assuming the buffer's value is `4.6`, the buffer's new value will be `4`.",
			"",
			"### roundup / ceil",
			"`roundup` or `ceil` will try to interpret the buffer's content as a decimal, and round it **up** to the next higher integer, writing the result to the buffer again.",
			"",
			"Example: `roundup` -> Assuming the buffer's value is `4.2`, the buffer's new value will be `5`.",
			"Example: `roundup` -> Assuming the buffer's value is `4.6`, the buffer's new value will be `5`.",
			"",
			"### round / nearest",
			"`round` or `nearest` will try to interpret the buffer's content as a decimal, and round it to the **closest** integer, writing the result to the buffer again.",
			"",
			"Example: `round` -> Assuming the buffer's value is `4.2`, the buffer's new value will be `4`.",
			"Example: `round` -> Assuming the buffer's value is `4.6`, the buffer's new value will be `5`.",
			"",
			"### concat",
			"`concat <text>` works similarly to `buffer <text>`, however it accepts variable substitution. This means that the text of multiple variables can be combined.",
			"",
			"Example: `concat $first$ and $second$` -> Assuming the variable \"first\" to be `Cats` and \"second\" to be `dogs`, then the result saved to the buffer is `Cats and dogs`.",
			"",
			"### eq",
			"`eq <value>` - equals, will try to compare the buffer to the supplied value. The buffer will be set to `true` if the values are equal and `false` otherwise. Supports variable substitution!",
			"",
			"Example: `eq Brick` -> If the buffer is `Brick`, then it is set to `true`, otherwise it becomes `false`.",
			"Example: `eq $comp$` -> If the buffer's value is equal to the value of the variable \"comp\", then it is set to `true`, otherwise it becomes `false`.",
			"",
			"### gtb",
			"`gtb <value>` - greater than buffer, will try to compare the buffer to the supplied value. The buffer will be set to `true` if the supplied numerical value is greater and `false` otherwise. Supports variable substitution!",
			"",
			"Example: `gtb 4` -> If the buffer is `3` or lower, then it is set to `true`, otherwise it becomes `false`.",
			"Example: `gtb $comp$` -> If the buffer lower than the value of \"comp\", then it is set to `true`, otherwise it becomes `false`.",
			"",
			"### ltb",
			"`ltb <value>` - less than buffer, will try to compare the buffer to the supplied value. The buffer will be set to `true` if the supplied numerical value is lower and `false` otherwise. Supports variable substitution!",
			"",
			"Example: `ltb 4` -> If the buffer is `5` or higher, then it is set to `true`, otherwise it becomes `false`.",
			"Example: `ltb $comp$` -> If the buffer higher than the value of \"comp\", then it is set to `true`, otherwise it becomes `false`.",
			"",
			"### geb",
			"`geb <value>` - greater than or equal buffer.",
			"",
			"### leb",
			"`leb <value>` - less than or equal buffer.",
			"",
			"### send",
			"`send <channel>` will send a Redstone-over-Radio signal over the supplied channel, with the signal's value being the current buffer's value. Sending repeatedly over the same channel requires waiting for a full game tick, so using `endtick` after sending is advised. Supports variable substitution!",
			"",
			"Example:",
			"`buffer Hello!`",
			"`send transmission`",
			"",
			"-> Will send the RoR signal `Hello!` on the channel \"transmission\".",
			"",
			"Example:",
			"`buffer SOS`",
			"`send $target$`",
			"",
			"-> Will send the RoR signal \"SOS\" to the channel saved in the variable \"target\".",
			"",
			"### listen ",
			"`listen <channel>` will listen in on the supplied RoR channel and write the signal to the buffer. Will detect all signals, even expired ones, and one just ones sent in the previous tick, so picking up a signal doesn't mean it's new information. Supports variable substitution!",
			"",
			"Example:",
			"`listen input`",
			"`eval $buffer$ * 100`",
			"`send output`",
			"",
			"-> Will take the signal from the RoR channel \"input\", multiply it by 100, and send that value on the channel \"output\".",
			"",
			"## Advanced",
			"",
			"### Conditional Branches",
			"Basic if/else conditions are the bread and butter of most programming. If we want to change the behavior based on different values, we need to compare, and then conditional jump.",
			"",
			"This example creates a script that processed a number \"val\" based on how high it is. 4 and below are multiplied by 2, otherwise it is divided by 2:",
			"",
			"`buffer 4`       <- buffer `4` for comparison",
			"`gtb $val$`      <- is \"val\" greater than our buffer?",
			"`jmpnot else`    <- if not, jump to \"else\"",
			"`eval $val$ / 2` <- if it is, divide by 2",
			"`save val`       <- ...and save to \"val\"",
			"`jmp end`        <- now jump to \"end\" to skip our \"else\" block",
			"`dest else`      <- else...",
			"`eval $val$ * 2` <- multiply by 2",
			"`save val`       <- ...and save to val",
			"`dest end`       <- no matter which branch we took, we always end up here",
			"",
			"### Methods",
			"People who are used to high languages will already know this concept, reusable parts of code with a set of parameters and an optional return value. For this we will use a few variables and a set of jumps in order to be able to use this piece of code from anywhere:",
			"",
			"This example implements a basic lerp (linear interpolation) function.",
			"",
			"`dest lerp`",
			"`eval $a$ + ($b$ - $a$) * $i$`",
			"`save result`",
			"`jmp $return$`",
			"",
			"This function requires the variables \"a\", \"b\" and \"i\" as parameters and saves the result to the variable \"result\". We can now access this function like such:",
			"",
			"`buffer 4`          <- first we set up our parameters to be used in the function",
			"`save a`",
			"`buffer 7`",
			"`save b`",
			"`buffer 0.6`",
			"`save i`",
			"`buffer returnhere` <- then we define the name of the return point",
			"`save return`",
			"`jmp lerp`          <- call the function",
			"`dest returnhere`   <- once the function concludes, we are back here",
			"",
			"At the end of it all, the variable \"result\" now has the desired value.",
	};
}
