package ma.bay.com.labase.common.http.exception;

import com.google.renamedgson.JsonElement;
import com.google.renamedgson.JsonNull;

public class LARespException extends RespException {

	private int statusCode; // LA code
	private JsonElement errorData;

	public LARespException(String msg, int statusCode) {
		this(msg, statusCode, JsonNull.INSTANCE);
	}

	public LARespException(String msg, int statusCode, JsonElement errorData) {
		super(msg);
		this.statusCode = statusCode;
		this.errorData = errorData;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public JsonElement getErrorData() {
		return errorData;
	}
}
