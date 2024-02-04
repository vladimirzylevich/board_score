package football.board.exception;

/**
 * Exception thrown when an invalid argument is provided in the Score Board.
 */
public class InvalidArgumentException extends ScoreBoardException {

    /**
     * Constructs a new InvalidArgumentException with the specified detail message.
     *
     * @param message The detail message.
     */
    public InvalidArgumentException(String message) {
        super(message);
    }
}
