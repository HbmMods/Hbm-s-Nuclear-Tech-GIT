package api.hbm.computer;

/**
 * Simple interface for computers. Don't usually have GUIs, just simple TEs.
 * @author UFFR
 *
 */
public interface IComputer extends IDevice
{
	public long upTime();
	public int getUpgradeCap();
	public void onShutdown();
	public boolean hasFault();
	public void setFault(boolean fault);
}
