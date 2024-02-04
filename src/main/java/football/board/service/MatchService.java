package football.board.service;

import football.board.entity.Team;

import java.util.UUID;

public interface MatchService {
    UUID newMatch(Team homeTeam, Team awayTeam);

    void finishMatchByUUID(UUID matchUuid);

}
