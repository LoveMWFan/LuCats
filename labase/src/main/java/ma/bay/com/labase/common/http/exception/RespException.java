package ma.bay.com.labase.common.http.exception;

public class RespException extends Exception {

	private String msg;

	public RespException(String msg) {
		this.msg = msg;
	}

	public String getMessage() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}
