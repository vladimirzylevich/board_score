package football.board.service;

import java.util.UUID;

public interface ScoreService {
    void updateScore(UUID matchUuid, Integer homeScore, Integer awayScore);
}
