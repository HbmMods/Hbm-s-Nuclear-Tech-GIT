package api.hbm.computer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import javax.annotation.CheckForNull;

import com.google.common.annotations.Beta;

/**
 * Things that can be researched by the supercomputers. Can be recipes, programs, or simple intermediary research steps.
 * @author UFFR
 *
 */
@Beta
public class Researchable
{
	private static final ArrayList<Researchable> allResearch = new ArrayList<Researchable>();
	@CheckForNull
	public static Researchable retrieve(String name)
	{
		for (Researchable r : allResearch)
		{
			if (r.getName().equals(name))
				return r;
		}
		return null;
	}
	
	public static void addAll(Collection<? extends Researchable> c)
	{
		allResearch.addAll(c);
	}
	
	private final String name;
	private long cpu, ram, filesize;
	private Optional<Researchable> previous = Optional.empty();
	public Researchable(String name)
	{
		this.name = name;
	}
	/**Unique name for the recipe, usually will be the output name for simplicity**/
	public String getKey()
	{
		return name;
	}
	/**Amount of RAM needed to research this item**/
	public long getRAM()
	{
		return ram;
	}
	/**Amount of CPU power needed to research this item**/
	public long getCPU()
	{
		return cpu;
	}
	public long getFilesize()
	{
		return filesize;
	}
	public String getName()
	{
		return name;
	}
	public Researchable setWork(long ram, long cpu)
	{
		this.cpu = cpu;
		this.ram = ram;
		return this;
	}
	public Researchable setFileSize(long filesize)
	{
		this.filesize = filesize;
		return this;
	}
	public Researchable setPrevious(Researchable prev)
	{
		previous = Optional.of(prev);
		return this;
	}
	public Researchable getPrevious()
	{
		return previous.get();
	}
	public boolean isDependent()
	{
		return previous.isPresent();
	}
	@Override
	public int hashCode()
	{
		return getKey().hashCode() * 13 * (isDependent() ? getPrevious().hashCode() : 1);
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if (obj == null || !(obj instanceof Researchable))
			return false;
		if (obj == this)
			return true;
		Researchable comp = (Researchable) obj;
		return comp.getCPU() == getCPU() && comp.getKey().equals(getKey()) && comp.getRAM() == getRAM() && comp.getPrevious().equals(getPrevious()) && comp.hashCode() == hashCode();
	}

}
