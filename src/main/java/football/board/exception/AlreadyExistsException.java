package football.board.exception;

/**
 * Exception thrown when an entity already exists in the Score Board.
 */
public class AlreadyExistsException extends ScoreBoardException {

    /**
     * Constructs a new AlreadyExistsException with the specified detail message.
     *
     * @param message The detail message.
     */
    public AlreadyExistsException(String message) {
        super(message);
    }
}
