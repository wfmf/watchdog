package nl.tudelft.watchdog.logic.exceptions;

@SuppressWarnings("serial")
public class EditorClosedPrematurelyException extends Exception {

	public EditorClosedPrematurelyException() {
		super();
	}

	public EditorClosedPrematurelyException(String message) {
		super(message);
	}
}