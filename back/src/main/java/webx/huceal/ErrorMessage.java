package webx.huceal;

public class ErrorMessage {

	private String message;

	public ErrorMessage() {
		super();
	}

	public ErrorMessage(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof ErrorMessage)) return false;

		ErrorMessage that = (ErrorMessage) o;

		return getMessage() != null ? getMessage().equals(that.getMessage()) : that.getMessage() == null;
	}

}
