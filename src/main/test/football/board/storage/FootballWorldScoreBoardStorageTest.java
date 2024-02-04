package football.board.storage;

import football.board.entity.Match;
import football.board.entity.Team;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.Iterator;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class FootballWorldScoreBoardStorageTest {

    private FootballWorldScoreBoardStorage storage;

    @BeforeEach
    void setUp() {
        storage = new FootballWorldScoreBoardStorage();
    }

    @Test
    void addShouldAddMatchToStorage() {
        Match match = new Match(new Team("HomeTeam"), new Team("AwayTeam"));
        storage.addMatch(match);

        Collection<Match> matches = storage.getAllMatches();

        assertTrue(matches.contains(match));
    }


    @Test
    void updateAndSortShouldUpdateMatchAndSortStorage() {
        Match match1 = new Match(new Team("Team1"), new Team("Team2"));
        Match match2 = new Match(new Team("Team3"), new Team("Team4"));
        storage.addMatch(match1);
        storage.addMatch(match2);

        Match updatedMatch = new Match(match1);
        updatedMatch.setHomeScore(2);
        updatedMatch.setAwayScore(1);

        storage.updateAndSort(updatedMatch);


        Collection<Match> matches = storage.getAllMatches();

        assertEquals(2, matches.iterator().next().getHomeScore());
        assertEquals(1, matches.iterator().next().getAwayScore());

        Iterator<Match> iterator = matches.iterator();
        if (iterator.hasNext()) {
            Match prevMatch = iterator.next();
            while (iterator.hasNext()) {
                Match currentMatch = iterator.next();
                assertTrue(Match.getSortComparator().compare(currentMatch, prevMatch) >= 0);
                prevMatch = currentMatch;
            }
        }
    }


    @Test
    void removeShouldRemoveMatchFromStorage() {
        Match match = new Match(new Team("HomeTeam"), new Team("AwayTeam"));
        storage.addMatch(match);

        UUID matchUuid = match.getMatchUuid();
        boolean removed = storage.removeMatch(matchUuid);

        assertTrue(removed);
        assertFalse(storage.getAllMatches().contains(match));
    }

    @Test
    void getCollectionShouldReturnImmutableCopy() {
        Match match = new Match(new Team("HomeTeam"), new Team("AwayTeam"));
        storage.addMatch(match);

        Collection<Match> matches = storage.getAllMatches();

        assertThrows(UnsupportedOperationException.class, () -> matches.add(new Match(new Team("Team1"), new Team("Team2"))));
    }
}
