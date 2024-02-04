package football.board.service.impl;

import football.board.entity.Match;
import football.board.entity.Team;
import football.board.exception.NotFoundException;
import football.board.service.ValidatedBoardService;
import football.board.storage.FootballWorldScoreBoardStorage;

import java.util.Collection;
import java.util.UUID;

/**
 * Implementation of the {@link ValidatedBoardService} for managing football match scores and summaries.
 */
public class FootballWorldScoreBoardService extends ValidatedBoardService {

    /**
     * Constructs a new instance of FootballWorldScoreBoardService with the default storage.
     */
    public FootballWorldScoreBoardService() {
        super(new FootballWorldScoreBoardStorage());
    }

    /**
     * Creates a new football match between two teams and adds it to the storage.
     *
     * @param homeTeam The home team.
     * @param awayTeam The away team.
     * @return The UUID of the newly created match.
     */
    @Override
    public UUID newMatch(Team homeTeam, Team awayTeam) {
        validateNewMatchInput(homeTeam, awayTeam);
        final Match newMatch = new Match(homeTeam, awayTeam);
        storage.addMatch(newMatch);
        return newMatch.getMatchUuid();
    }

    /**
     * Updates the scores of a football match and sorts the storage.
     *
     * @param matchUuid The UUID of the match to update.
     * @param homeScore The updated home team score.
     * @param awayScore The updated away team score.
     * @throws NotFoundException If the match with the specified UUID is not found.
     */
    @Override
    public void updateScore(UUID matchUuid, Integer homeScore, Integer awayScore) {
        validateUpdateScoreInput(matchUuid, homeScore, awayScore);
        Match matchToUpdate = storage.getAllMatches().stream()
                .filter(match -> match.getMatchUuid().equals(matchUuid))
                .findFirst().orElseThrow(() -> new NotFoundException(matchUuid));
        matchToUpdate.setHomeScore(homeScore);
        matchToUpdate.setAwayScore(awayScore);
        storage.updateAndSort(matchToUpdate);
    }

    /**
     * Finishes a football match by removing it from the storage.
     *
     * @param matchUuid The UUID of the match to finish.
     * @throws NotFoundException If the match with the specified UUID is not found.
     */
    @Override
    public void finishMatchByUUID(UUID matchUuid) {
        if (!storage.removeMatch(matchUuid)) {
            throw new NotFoundException(matchUuid);
        }
    }

    /**
     * Retrieves a sorted summary of all football matches from the storage.
     *
     * @return A collection of football matches sorted by score.
     */
    @Override
    public Collection<Match> getSortedSummary() {
        return storage.getAllMatches();
    }
}
