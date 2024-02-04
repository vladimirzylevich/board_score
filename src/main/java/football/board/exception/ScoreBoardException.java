package football.board.exception;

/**
 * Base exception for the Score Board project.
 */
public class ScoreBoardException extends RuntimeException{
    /**
     * Constructs a new Score Board exception with the specified detail message.
     *
     * @param message The detail message.
     */
    public ScoreBoardException(String message) {
        super(message);
    }
}
