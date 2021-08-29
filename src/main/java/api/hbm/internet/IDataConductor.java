package api.hbm.internet;

public interface IDataConductor extends IDataConnector
{
	public static final String RESEARCH_ASSEMBLER = "DATA_RECIPE_ASSEMBLER";
	public static final String RESEARCH_CHEMPLANT = "DATA_RECIPE_CHEMPLANT";
	public static final String RECIPE = "DATA_RECIPE";
	public static final String MESSAGE = "DATA_MESSAGE";
	public static final String PROGRAM = "DATA_PROGRAM";
	public static final String COMMAND_TASK = "DATA_COMMAND_TASK";
	
	public IInternet getDataNetwork();
	
	public void setDataNetwork(IInternet network);
}
