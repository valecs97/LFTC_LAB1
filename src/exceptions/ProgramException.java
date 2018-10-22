package exceptions;

public class ProgramException extends Exception {
    String message;

    public ProgramException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }
}
