package exceptions;

public class NoMatchException extends Exception {
    String message;

    public NoMatchException() {
    }

    public NoMatchException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }
}
