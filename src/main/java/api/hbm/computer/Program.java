package api.hbm.computer;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

import com.google.common.annotations.Beta;

import net.minecraft.nbt.NBTTagCompound;

/**
 * Use the {@code print()} function to print lines of text</br>
 * Use {@code execute(...)} for initialization</br>
 * {@code execute()} for code that runs every tick</br>
 * And {@code complete()} to end program
 * @author UFFR
 *
 */
@Beta
public abstract class Program implements Cloneable
{
	@FunctionalInterface
	public interface ProgramTask
	{
		public void run();
	}
	private static final HashMap<String, Class<? extends Program>> registeredPrograms = new HashMap<String, Class<? extends Program>>();
	private static final HashMap<String, String[]> programHelp = new HashMap<String, String[]>();
	/**
	 * Register classes here
	 * '/n' for new lines
	 */
	static
	{
		//TODO
	}
	public static void register(Class<? extends Program> p, String help)
	{
		registeredPrograms.put(p.getSimpleName().toLowerCase(), p);
		programHelp.put(p.getSimpleName().toLowerCase(), help.split("\n"));
	}
	public static Class<? extends Program> getProgram(String cmd)
	{
		return registeredPrograms.get(cmd);
	}
	public static String[] getHelpText(String cmd)
	{
		return programHelp.getOrDefault(cmd, new String[] {"Program has no associated help documentation.", "Consult the program's manual instead."});
	}
	protected final ITerminal terminal;
	private boolean executionComplete = false;
	protected long ram, cpu;
	protected final Queue<ProgramTask> taskQueue = new LinkedList<>();
	protected int line = 0;
	public Program(ITerminal terminal)
	{
		this.terminal = terminal;
	}
	public long getRAM()
	{
		return ram;
	}
	public long getCPU()
	{
		return cpu;
	}
	public final void complete()
	{
		executionComplete = true;
	}
	public final boolean isExecutionComplete()
	{
		return executionComplete;
	}
	@Override
	public final Program clone()
	{
		try
		{
			return (Program) super.clone();
		} catch (CloneNotSupportedException e)
		{
			e.printStackTrace();
			terminal.handleReturn(ReturnCode.FATAL, "Unable to register program: [" + getClass().getSimpleName() + "]!");
			return null;
		}
	}
	/**Per tick program execution**/
	public abstract void execute();
	/**Initialization program execution**/
	public abstract void execute(String...args);
	/**Receive keypresses and act on them, superseded by {@link #requestingConsoleInput()} & {@link #acceptConsoleInputHook(String)}**/
	public abstract void keyTypeHook(char c, int i);
	/**To request text input from the console instead of {@link #keyTypeHook(char, int)}**/
	public abstract boolean requestingConsoleInput();
	/**Hook to receive strings from the console input, requires {@link #requestingConsoleInput()} to be {@code true}**/
	public abstract void acceptConsoleInputHook(String s);
	/**What to do when the program is terminated, generally if {@code executionComplete} is {@code true},
	 * the either nothing or standard close behavior happens and if {@code false} then some error
	 * or corruption may happen instead**/
	public abstract ReturnCode onProcessKill();
	/**Save program data to NBT for syncing or saving**/
	public abstract void writeToNBT(NBTTagCompound nbt);
	/**Load program data from NBT for syncing or loading a world**/
	public abstract void readFromNBT(NBTTagCompound nbt);
}
