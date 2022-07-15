package api.hbm.internet;
/**
 * Refer to <a href="https://en.wikipedia.org/wiki/List_of_HTTP_status_codes">Wikipedia</a> or <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Status">Firefox Documentation</a> for more info
 * @author UFFR
 *
 */
public enum MessageResponse
{
	CONTINUE(100, false),
	SWITCHING_PROTOCOLS(101, false),
	PROCESSING(102, false),
	EARLY_HINTS(103, false),
	OK(200, false),
	CREATED(201, false),
	ACCEPTED(202, false),
	NON_AUTHORITATIVE_INFORMATION(203, false),
	NO_CONTENT(204, false),
	RESET_CONTENT(205, false),
	PARTIAL_CONTENT(206, false),
	MULTI_STATUS(207, false),
	ALREADY_REPORTED(208, false),
	IM_USED(226, false);
	private final int code;
	private final boolean isError;
	private MessageResponse(int code, boolean isError)
	{
		this.code = code;
		this.isError = isError;
	}
	public int getCode()
	{
		return code;
	}
	public boolean isError()
	{
		return isError;
	}
	
	@Override
	public String toString()
	{
		return code + ' ' + super.toString();
	}
}
