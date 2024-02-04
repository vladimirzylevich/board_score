package football.board.service;


import football.board.entity.Match;
import football.board.entity.Team;
import football.board.exception.AlreadyExistsException;
import football.board.exception.InvalidArgumentException;
import football.board.storage.Storage;

import java.util.Collection;
import java.util.Objects;
import java.util.UUID;

/**
 * Base class for services in the Score Board project with validation methods.
 */
public abstract class ValidatedBoardService implements SummaryService, ScoreService, MatchService {

    protected final Storage storage;

    protected ValidatedBoardService(Storage storage) {
        this.storage = storage;
    }

    /**
     * Validate input for a new match.
     *
     * @param homeTeam The home team.
     * @param awayTeam The away team.
     * @throws AlreadyExistsException if one of the teams is busy in another match.
     */
    protected void validateNewMatchInput(Team homeTeam, Team awayTeam) {
        validateNotNullOrEmpty(homeTeam, "Home team");
        validateNotNullOrEmpty(awayTeam, "Away team");

        Collection<Match> collection = storage.getAllMatches();
        if (collection.stream().anyMatch(match -> teamsInMatch(match, homeTeam, awayTeam))) {
            throw new AlreadyExistsException("One of the teams is already participating in another match.");
        }
    }

    /**
     * Validate input for updating the score of a match.
     *
     * @param matchUuid The UUID of the match.
     * @param homeScore The home team score.
     * @param awayScore The away team score.
     * @throws InvalidArgumentException if any input is invalid.
     */
    protected void validateUpdateScoreInput(UUID matchUuid, Integer homeScore, Integer awayScore) {
        validateNotNullOrEmpty(matchUuid, "Match UUID");
        validateNotNullOrEmpty(homeScore, "Home score");
        validateNotNullOrEmpty(awayScore, "Away score");

        if (homeScore < 0 || awayScore < 0) {
            throw new InvalidArgumentException("Negative score is not allowed.");
        }
    }

    /**
     * Validate that a value is not null or empty.
     *
     * @param value     The value to validate.
     * @param fieldName The name of the field.
     * @throws InvalidArgumentException if the value is null or empty.
     */
    private void validateNotNullOrEmpty(Object value, String fieldName) {
        if (Objects.isNull(value)) {
            throw new InvalidArgumentException(fieldName + " cannot be null.");
        }
    }

    /**
     * Check if the provided teams are involved in the match.
     *
     * @param match     The match.
     * @param homeTeam  The home team.
     * @param awayTeam  The away team.
     * @return True if teams are in the match, false otherwise.
     */
    private boolean teamsInMatch(Match match, Team homeTeam, Team awayTeam) {
        UUID homeTeamUuid = homeTeam.getUuid();
        UUID awayTeamUuid = awayTeam.getUuid();
        return getTeamUuid(match.getHomeTeam()).equals(homeTeamUuid)
                || getTeamUuid(match.getAwayTeam()).equals(homeTeamUuid)
                || getTeamUuid(match.getHomeTeam()).equals(awayTeamUuid)
                || getTeamUuid(match.getAwayTeam()).equals(awayTeamUuid);
    }

    /**
     * Get the UUID of a team.
     *
     * @param team The team.
     * @return The UUID of the team.
     */
    private UUID getTeamUuid(Team team) {
        return team.getUuid();
    }
}

