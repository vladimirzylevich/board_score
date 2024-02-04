package football.board.exception;

import java.util.UUID;

/**
 * Exception thrown when an entity is not found in the Score Board.
 */
public class NotFoundException extends ScoreBoardException {
    /**
     * Constructs a new NotFoundException with the specified detail message.
     *
     * @param message The detail message.
     */
    public NotFoundException(String message) {
        super(message);
    }

    /**
     * Constructs a new NotFoundException for a specific entity with its UUID.
     *
     * @param matchUuid The UUID of the entity not found.
     */
    public NotFoundException(UUID matchUuid) {
        this("Match with UUID: " + matchUuid + " not found");
    }

}
