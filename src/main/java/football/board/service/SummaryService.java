package football.board.service;

import football.board.entity.Match;

import java.util.Collection;

public interface SummaryService {
    Collection<Match> getSortedSummary();
}
