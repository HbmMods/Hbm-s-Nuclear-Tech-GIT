package api.hbm.computer;

import java.util.UUID;

import api.hbm.IHasAge;

/**
 * (Hopefully) simple base interface for various devices. For stuff even simpler than {@link#IComputer}.
 * @author UFFR
 *
 */
public interface IDevice extends IHasAge
{
	public long getCPU();
	public long getUsedCPU();
	public long getRAM();
	public long getUsedRAM();
	public UUID getUUID();
	public String getFriendlyName();
}
