package accew.common.exceptions;


public class UpdateException extends RuntimeException {
	String errorCode;
	Throwable cause;
	
	private static final long serialVersionUID = 1L;

	public UpdateException(String s) {
		super(s);
	}
	
	public UpdateException(String errorCode, String s) {
		this(errorCode, s, true);
	}

	/**
	 * Constructor.
	 * 
	 * @param s
	 *            Exception message.
	 */
	public UpdateException(String errorCode, String s, boolean flag) {
		super(s);
		this.errorCode = errorCode;

	}

	public UpdateException(String errorCode, Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public UpdateException(String errorCode, String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	@Override
	public Throwable getCause() {
		return cause;
	}

	public void setCause(Throwable cause) {
		this.cause = cause;
	}

}
