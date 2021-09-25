package api.hbm.internet;

public interface IDataConductor extends IDataConnector
{
	public IInternet getDataNetwork();
	
	public void setDataNetwork(IInternet network);
}
