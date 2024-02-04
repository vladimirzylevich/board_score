package football.board.storage;

import football.board.entity.Match;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

/**
 * A storage class for maintaining the state of football matches.
 */
public class FootballWorldScoreBoardStorage implements Storage {

    /**
     * The list of matches stored in the scoreboard.
     */
    private final List<Match> matchList;

    /**
     * Constructs a new FootballWorldScoreBoardStorage with an initial empty list of matches.
     */
    public FootballWorldScoreBoardStorage() {
        this.matchList = new LinkedList<>();
    }

    /**
     * Adds a new match to the storage.
     *
     * @param matchToAdd The match to be added.
     */
    @Override
    public void addMatch(Match matchToAdd) {
        matchList.add(matchToAdd);
    }

    /**
     * Updates the provided match and sorts the storage based on the total score.
     *
     * @param updatedMatch The match to be updated.
     */
    @Override
    public void updateAndSort(Match updatedMatch) {
        matchList.stream()
                .filter(match -> match.getMatchUuid().equals(updatedMatch.getMatchUuid()))
                .findFirst()
                .ifPresent(match -> {
                    match.setHomeScore(updatedMatch.getHomeScore());
                    match.setAwayScore(updatedMatch.getAwayScore());
                });
        matchList.sort(Match.getSortComparator());
    }

    /**
     * Removes a match from the storage based on its UUID.
     *
     * @param uuidToRemove The UUID of the match to be removed.
     * @return {@code true} if the match was found and removed, {@code false} otherwise.
     */
    @Override
    public boolean removeMatch(UUID uuidToRemove) {
        return matchList.removeIf(match -> match.getMatchUuid().equals(uuidToRemove));
    }

    /**
     * Retrieves an immutable copy of the collection of matches in the storage.
     *
     * @return An immutable copy of the collection of matches.
     */
    @Override
    public Collection<Match> getAllMatches() {
        return List.copyOf(matchList);
    }
}
