package ma.bay.com.labase.common.http.exception;

public class HttpRespException extends RespException {

	private int httpCode;

	public HttpRespException(int httpCode, String msg) {
		super(msg);
		this.httpCode = httpCode;
	}

	public int getHttpCode() {
		return httpCode;
	}
}
