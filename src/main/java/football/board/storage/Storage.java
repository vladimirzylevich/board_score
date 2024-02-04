package football.board.storage;

import football.board.entity.Match;

import java.util.Collection;
import java.util.UUID;


/**
 * Interface for storing football match data.
 */
public interface Storage {

    /**
     * Adds a new matchToAdd to the storage.
     *
     * @param matchToAdd The matchToAdd to be added.
     */
    void addMatch(Match matchToAdd);

    /**
     * Updates the scores of a match and sorts the storage based on the updated scores.
     *
     * @param updatedMatch The match with updated scores.
     */
    void updateAndSort(Match updatedMatch);

    /**
     * Removes a match from the storage based on its UUID.
     *
     * @param matchUUID The UUID of the match to be removed.
     * @return True if the match was removed successfully, false otherwise.
     */
    boolean removeMatch(UUID matchUUID);

    /**
     * Retrieves the collection of matches stored in the storage.
     *
     * @return The collection of matches.
     */
    Collection<Match> getAllMatches();
}
