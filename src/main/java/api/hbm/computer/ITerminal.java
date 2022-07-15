package api.hbm.computer;

import java.awt.Shape;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.EmptyStackException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import com.google.common.annotations.Beta;
import com.google.common.collect.ImmutableList;
import com.hbm.inventory.gui.GUICalculator;
import com.hbm.lib.Library;
import com.hbm.lib.RefStrings;
import com.hbm.util.BobMathUtil;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.minecraft.nbt.NBTTagCompound;
/**
 * Collection for anything related to terminal style computers, contains a lot of methods for handling related stuff.
 * @author UFFR
 *
 */
@Beta
public interface ITerminal extends IComputer
{
	@Immutable
	public static class TerminalCommand
	{
		private final String[] parsed, args;
		private final String command, argsUnsplit;
		public TerminalCommand(String cmd)
		{
			parsed = cmd.split(" ");
			args = new String[parsed.length - 1];
			for (int i = 0; i < args.length; i++)
				args[i] = parsed[i + 1];
			command = parsed[0].toLowerCase();
			argsUnsplit = cmd.length() > command.length() + 1 ? cmd.substring(command.length() + 1) : "";
		}
		public String[] getParsed()
		{
			return parsed.clone();
		}
		public String[] getArgs()
		{
			return args.clone();
		}
		public String getCommand()
		{
			return command;
		}
		public String getArgsUnsplit()
		{
			return argsUnsplit;
		}
	}

	public static class FloatingText
	{
		public String text;
		public short x, y;
		public FloatingText(String text, int x, int y)
		{
			this.text = text;
			this.x = (short) x;
			this.y = (short) y;
		}
		public void writeBytes(ByteBuf buf)
		{
			buf.writeShort(x);
			buf.writeShort(y);
			buf.writeByte(text.length());
			buf.writeBytes(text.getBytes());
		}
		public static FloatingText readBytes(ByteBuf buf)
		{
			final short x, y;
			x = buf.readShort();
			y = buf.readShort();
			byte[] stringBytes = new byte[buf.readByte()];
			buf.readBytes(stringBytes);
			return new FloatingText(new String(stringBytes), x, y);
		}
	}

	public static final ImmutableList<String> startupLogo = ImmutableList.copyOf(new String[]
			{
				"█████          █████         █████",
				"██ ██         ██   ██        ██ ██",
				"██  ██       ██     ██       ██  ██",
				"█████        ██     ██       █████",
				"██   ██      ██     ██       ██   ██",
				"██  ██        ██   ██        ██  ██",
				"██████         █████         ██████",
				"Bobsoft Computational Services Inc.",
				"BobOS®" + RefStrings.VERSION.substring(0, 6) + "© 2077"
			});
	public static final ImmutableList<String> startupLogoFixed = ImmutableList.copyOf(new String[]
			{
					"█████           █████         █████",
					"██   ██         ██     ██        ██   ██",
					"██    ██       ██       ██       ██    ██",
					"█████        ██        ██       █████",
					"██     ██      ██       ██       ██     ██",
					"██     ██       ██     ██        ██     ██",
					"██████         █████         ██████",
					"Bobsoft Computational Services Inc.",
					"BobOS(R) " + RefStrings.VERSION.substring(0, 6) + " (C) 2077"

			});
	public static final String NBT_KEY = "NTM_COMPUTER_TERMINAL_NBT";
	
	public static void stockCommandHandler(ITerminal terminal, String cmd)
	{
		TerminalCommand parsed = new TerminalCommand(cmd);
		switch (parsed.getCommand())
		{
		case "arith":
			try
			{
				terminal.print(String.valueOf(GUICalculator.evaluateExpression(parsed.getArgsUnsplit())));
			}
			catch (NumberFormatException | ArithmeticException | EmptyStackException e)
			{
				terminal.handleReturn(ReturnCode.HANDLED, String.format("[ARITH] encountered the [%s] internal error with %s.", e.getClass().getSimpleName(), e.getMessage() == null ? "no additional message" : "the following message: " + e.getMessage()));
			}
			catch (Exception e)
			{
				terminal.handleReturn(ReturnCode.MINOR, String.format("[ARITH] encountered a syntax error: [%s]", e.getClass().getSimpleName()));
			}
			break;
		case "echo": terminal.print(parsed.getArgsUnsplit()); break;
		case "help":
			if (parsed.getArgs().length == 0)
				terminal.print(" - Prints out the help command of the specified program", " - Accepts only one argument", " - Should function the same as running the program with the -h parameter");
			else if (parsed.getArgs().length == 1)
			{
				String arg = parsed.getArgs()[0].toLowerCase();
				
				switch (arg)
				{
				case "echo": terminal.print(" - Prints the specified string verbatim."); break;
				case "uuid": terminal.print(" - Prints the system's UUID."); break;
				case "cls": terminal.print(" - Clears the entire screen of visuals."); break;
				case "arith": terminal.print(" - Evaluates the arithmatic expression with 256 digit precision.", " - May return a fault code if an error is caught."); break;
				case "neofetch": terminal.print(" - Displays full system information and specifications."); break;
				default:
				if (terminal.getPrograms().containsKey(arg))
					terminal.print(Program.getHelpText(cmd));
				else
					terminal.print("Unknown program: " + arg);
					break;
				}
			}
			else
				terminal.handleUnknown(parsed.getArgs(), true);
			break;
		case "cls": terminal.clearScreen(-1); break;
		case "uuid": 
			if (parsed.getArgs().length == 0)
				terminal.print("Current system UUID:", terminal.getUUID().toString());
			else
				terminal.handleUnknown(parsed.getArgs(), true);
			break;
		case "neofetch":
			for (String s : startupLogoFixed)
				terminal.print(s);
			char[] bar = new char[24];
			Arrays.fill(bar, '═');
			terminal.print(terminal.getUUID().toString(), String.valueOf(bar));
			terminal.print("Host: ", "Kernel: " + RefStrings.VERSION, "Uptime: " + terminal.upTime(), "Resolution: ", String.format("CPU: %s / %sHz", BobMathUtil.getShortNumber(terminal.getUsedCPU()), BobMathUtil.getShortNumber(terminal.getCPU())), String.format("Memory: %s / %sb", BobMathUtil.getShortNumber(terminal.getUsedRAM()), BobMathUtil.getShortNumber(terminal.getRAM())), "Motherboard Capacity: " + terminal.getUpgradeCap());
			break;
		default:
			if (terminal.getPrograms().containsKey(parsed.getCommand()))
			{
				try
				{
					Program p = terminal.getPrograms().get(parsed.getCommand()).getConstructor(ITerminal.class).newInstance(terminal);
					terminal.setCurrentProgram(p);
					terminal.getCurrentProgram().get().execute(parsed.getArgs());
				} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
						| InvocationTargetException | NoSuchMethodException | SecurityException e)
				{
					e.printStackTrace();
					terminal.handleReturn(ReturnCode.FATAL, e);
				}
			}
			else
				terminal.handleUnknown(parsed.getParsed(), false);
			break;
		}
	}
	
	public List<String> getPreviousCommands();
	public void addToPreviousCommands(String cmd);
	public void addAllToPrevious(Collection<String> cmds);
	public byte getRows();
	public byte getColumns();
	public char[][] getConsoleText();
	public void print(String s);
	public List<FloatingText> getFloatingText();
	public void print(FloatingText txt);
	public List<Shape> getShapes();
	public void draw(Shape shape);
	public void clearScreen(int i);
	public Map<String, Class<? extends Program>> getPrograms();
	public Optional<Program> getCurrentProgram();
	public void setCurrentProgram(@Nullable Program program);
	
	public default void onBoot()
	{
		print("Starting BobOS...");
		for (String s : startupLogoFixed)
			print(s);
	}
	
	public default void onTick() throws Exception
	{
		if (getCurrentProgram().isPresent())
		{
			if (getCurrentProgram().get().isExecutionComplete())
			{
				handleReturn(getCurrentProgram().get().onProcessKill());
				setCurrentProgram(null);
			}
			else
				getCurrentProgram().get().execute();
		}
	}
	
	public default void print(String s, int x, int y)
	{
		print(new FloatingText(s, x, y));
	}
	
	public default void runCommand(String cmd)
	{
		print('>' + cmd);
		addToPreviousCommands(cmd);
//		stockCommandHandler(this, cmd);
	}
	
//	public default List<String> getConsoleText(int rows, int columns)
//	{
//		ArrayList<String> consoleCopy = new ArrayList<>(getConsoleText());
//		ArrayList<String> toOut = new ArrayList<>();
//		Iterator<String> textIterator = consoleCopy.iterator();
//		while (textIterator.hasNext())
//		{
//			String[] split = WordUtils.wrap(textIterator.next(), columns, null, true).split("\n");
//			for (String s : split)
//				toOut.add(s);
//			textIterator.remove();
//		}
//		return ImmutableList.copyOf(rows < toOut.size() ? toOut.subList(toOut.size() - rows, toOut.size()) : toOut);
//	}
	/**
	 * Handle an unknown array of strings
	 * @param strings The errant strings to display
	 * @param b If true, the strings are said to be parameters; if false, the strings are said to be commands
	 */
	public default void handleUnknown(String[] strings, boolean b)
	{
		print("Unknown " + (b ? "parameters" : "command") + ": " + Arrays.toString(strings));
	}
	
	public default void print(String...strings)
	{
		for (String s : strings)
			print(s);
	}
	
	public default void print(Iterable<String> strings)
	{
		for (String s : strings)
			print(s);
	}
	
	public default void draw(Shape...shapes)
	{
		for (Shape s : shapes)
			draw(s);
	}
	
	public default void draw(Iterable<Shape> shapes)
	{
		for (Shape s : shapes)
			draw(s);
	}
	
	public default void handleKey(char c, int i)
	{
		if (getCurrentProgram().isPresent())
			getCurrentProgram().get().keyTypeHook(c, i);
	}
	
	public default void handleReturn(ReturnCode code)
	{
		handleReturn(code, code.defaultMessage);
	}
	public default void handleReturn(ReturnCode code, Throwable throwable)
	{
		handleReturn(code, String.format("Recieved Throwable: [%s] with the cause [%s] and with %s", throwable.getClass().getSimpleName(), throwable.getCause() == null ? null : throwable.getCause().getClass().getSimpleName(), throwable.getMessage() == null ? "no additional message" : "the message: " + throwable.getMessage()));
	}
	public default void handleReturn(ReturnCode code, String message)
	{
//		if (message == null)
//			message = "";
		if (code == ReturnCode.NO_ERROR)
			print(String.format("Program return code: (0x%x)%s; %s; No action required, program executed successfully.", code.ordinal(), code.toString(), message.isEmpty() ? "[No message recieved]" : message));
		else
			print(String.format("Program return code: (0x%x)%s; %s; Consult documentation on this error.", code.ordinal(), code.toString(), message.isEmpty() ? "[No message recieved]" : message));
	}
	
	public default void saveTerminalToNBT(NBTTagCompound nbt)
	{
		final NBTTagCompound subTag = new NBTTagCompound();
		boolean programRunning = getCurrentProgram().isPresent();
		subTag.setBoolean("isProgramRunning", programRunning);
		subTag.setString("programName", programRunning ? getCurrentProgram().get().getClass().getSimpleName() : "");
		
//		subTag.setByteArray("consoleText", Library.compressStringCollection(getConsoleText()));
		final ByteBuf textBuf = Unpooled.buffer();
		final char[][] screenText = getConsoleText();
		for (byte row = 0; row < getRows(); row++)
		{
			for (byte column = 0; column < getColumns(); column++)
				textBuf.writeChar(screenText[row][column]);
		}
		subTag.setByteArray("consoleText", textBuf.array());
		subTag.setByteArray("prevCommands", Library.compressStringCollection(getPreviousCommands()));
		
		final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		try
		{
			final ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
			objectOutputStream.writeObject(getShapes());
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		subTag.setByteArray("shapes", byteArrayOutputStream.toByteArray());
		
		final List<FloatingText> floatingText = getFloatingText();
		ByteBuf floatingTxtBuf = Unpooled.buffer();
		floatingTxtBuf.writeInt(floatingText.size());
		for (FloatingText text : floatingText)
			text.writeBytes(floatingTxtBuf);
		subTag.setByteArray("floatingText", floatingTxtBuf.array());
		
		if (getCurrentProgram().isPresent())
			getCurrentProgram().get().writeToNBT(subTag);
		
		nbt.setTag(NBT_KEY, subTag);
	}
	
	public default void loadTerminalFromNBT(NBTTagCompound nbt)
	{
		final NBTTagCompound subTag = nbt.getCompoundTag(NBT_KEY);
		clearScreen(-1);
		final ByteBuf consoleBuf = Unpooled.copiedBuffer(subTag.getByteArray("consoleText"));
		final ArrayList<String> consoleText = new ArrayList<String>(getRows());
		for (byte i = 0; i < getRows(); i++)
		{
			final byte[] stringBytes = new byte[getColumns()];
			consoleBuf.readBytes(stringBytes);
			consoleText.add(new String(stringBytes));
		}
		print(consoleText);
		
		final ByteBuf prevCmdBuf = Unpooled.copiedBuffer(subTag.getByteArray("prevCommands"));
		final int prevCmdSize = prevCmdBuf.readInt();
		final ArrayList<String> savedCommands = new ArrayList<String>(prevCmdSize);
		for (int i = 0; i < prevCmdSize; i++)
		{
			final byte[] stringBytes = new byte[prevCmdBuf.readByte()];
			prevCmdBuf.readBytes(stringBytes);
			savedCommands.add(new String(stringBytes));
		}
		addAllToPrevious(savedCommands);
		
		final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(subTag.getByteArray("shapes"));
		try
		{
			final ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
			final List<Shape> shapes = (List<Shape>) objectInputStream.readObject();
			draw(shapes);
		} catch (IOException | ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		
		final ByteBuf floatingTextBuf = Unpooled.copiedBuffer(subTag.getByteArray("floatingText"));
		final int floatingTextLength = floatingTextBuf.readInt();
		for (int i = 0; i < floatingTextLength; i++)
			print(FloatingText.readBytes(floatingTextBuf));
		if (subTag.getBoolean("isProgramRunning"))
		{
			final boolean programRunning = getCurrentProgram().isPresent(), programMatches = programRunning ? getCurrentProgram().get().getClass().getSimpleName().equals(subTag.getString("programName")) : false;
			
			if (programRunning && programMatches)
				getCurrentProgram().get().readFromNBT(subTag);
			else if (!programRunning)
			{
				try
				{
					setCurrentProgram(getPrograms().get(subTag.getString("programName")).getConstructor(ITerminal.class).newInstance(this));
				} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
						| InvocationTargetException | NoSuchMethodException | SecurityException e)
				{
					e.printStackTrace();
					handleReturn(ReturnCode.FATAL, e);
				}
			}
			else
				setCurrentProgram(null);
		}
	}
}
