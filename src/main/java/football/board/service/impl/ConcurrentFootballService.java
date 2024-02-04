package football.board.service.impl;


import football.board.entity.Match;
import football.board.entity.Team;

import java.util.Collection;
import java.util.UUID;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * A concurrent version of the FootballWorldScoreBoardService with synchronized methods using ReentrantLock.
 */
public class ConcurrentFootballService extends FootballWorldScoreBoardService {
    private final Lock lock = new ReentrantLock();

    /**
     * Default constructor initializing the service.
     */
    public ConcurrentFootballService() {
        super();
    }

    /**
     * Starts a new match and adds it to the scoreboard. Uses a lock to ensure thread safety.
     *
     * @param homeTeam The home team for the new match.
     * @param awayTeam The away team for the new match.
     * @return The UUID of the newly created match.
     */
    @Override
    public UUID newMatch(Team homeTeam, Team awayTeam) {
        try {
            lock.lock();
            return super.newMatch(homeTeam, awayTeam);
        } finally {
            lock.unlock();
        }
    }

    /**
     * Updates the score of an existing match. Uses a lock to ensure thread safety.
     *
     * @param matchUuid The UUID of the match to update.
     * @param homeScore The updated home team score.
     * @param awayScore The updated away team score.
     */
    @Override
    public void updateScore(UUID matchUuid, Integer homeScore, Integer awayScore) {
        try {
            lock.lock();
            super.updateScore(matchUuid, homeScore, awayScore);
        } finally {
            lock.unlock();
        }
    }

    /**
     * Finishes a match currently in progress. Removes the match from the scoreboard. Uses a lock to ensure thread safety.
     *
     * @param matchUuid The UUID of the match to finish.
     */
    @Override
    public void finishMatchByUUID(UUID matchUuid) {
        try {
            lock.lock();
            super.finishMatchByUUID(matchUuid);
        } finally {
            lock.unlock();
        }
    }

    /**
     * Gets a summary of matches in progress ordered by their total score. Uses a lock to ensure thread safety.
     *
     * @return A collection of matches sorted by total score.
     */
    @Override
    public Collection<Match> getSortedSummary() {
        try {
            lock.lock();
            return super.getSortedSummary();
        } finally {
            lock.unlock();
        }
    }
}
