package api.hbm.computer;
/**
 * Codes programs or the system can return to show more detailed errors</br>
 * All codes are as follows:
 * <li>{@link #NO_ERROR}
 * <li>{@link #HANDLED}
 * <li>{@link #MINOR}
 * <li>{@link #UNKNOWN}
 * <li>{@link #FATAL}
 * <li>{@link #NO_CPU}
 * <li>{@link #NO_RAM}
 * <li>{@link #NO_DISC}
 * <li>{@link #NO_SPACE}
 * <li>{@link #FILE_ERROR}
 * <li>{@link #NO_FILE}
 * <li>{@link #NO_WRITE}
 */
public enum ReturnCode
{
	/**No error, execution normal**/
	NO_ERROR("Program execution complete"),
	/**Error, but it was handled internally and results in no issues**/
	HANDLED("Program encountered an error, but it was handled"),
	/**Program experienced an error, but it was minor**/
	MINOR("Program encountered an error, but it was minor"),
	/**Unknown error, fatal**/
	UNKNOWN("Unknown error; fatal, unrecoverable"),
	/**Generic fatal error**/
	FATAL("Unrecoverable error!"),
	/**Command not recognized or other error**/
	INVALID_COMMAND("Unrecognized or invalid command"),
	/**Invalid/insufficient CPU**/
	NO_CPU("Invalid/insufficient CPU"),
	/**Invalid/insufficient RAM**/
	NO_RAM("Invalid/insufficient RAM"),
	/**Invalid disk drive, usually used by file accessing programs**/
	NO_DISC("Invalid disk drive"),
	/**Not enough disk space**/
	NO_SPACE("Not enough disk space"),
	/**Generic file related error**/
	FILE_ERROR("File operation failed"),
	/**File doesn't exist**/
	NO_FILE("File does not exist"),
	/**Drive has a write speed of 0**/
	NO_WRITE("Disk is write protected");
	public final String defaultMessage;
	private ReturnCode(String defaultMessage)
	{
		this.defaultMessage = defaultMessage;
	}
	private ReturnCode()
	{
		defaultMessage = new String();
	}
}
