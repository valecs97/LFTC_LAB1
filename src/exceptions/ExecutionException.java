package exceptions;

public class ExecutionException extends Exception{
    String message;

    public ExecutionException(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }
}
