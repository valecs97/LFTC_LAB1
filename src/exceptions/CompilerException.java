package exceptions;

public class CompilerException extends Exception {
    String message;

    public CompilerException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }
}
