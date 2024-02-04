package football.board.service.impl;

import football.board.entity.Match;
import football.board.entity.Team;
import football.board.exception.AlreadyExistsException;
import football.board.exception.InvalidArgumentException;
import football.board.exception.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class FootballWorldScoreBoardServiceTest {

    private FootballWorldScoreBoardService scoreBoardService;

    @BeforeEach
    void setUp() {
        scoreBoardService = new FootballWorldScoreBoardService();
        scoreBoardService.newMatch(new Team("Home1"), new Team("Away1"));
        scoreBoardService.newMatch(new Team("Home2"), new Team("Away2"));
    }

    @Test
    void newMatch_AwayTeamBusy_ThrowsAlreadyExists() {
        List<Match> matches = new ArrayList<>(scoreBoardService.getSortedSummary());

        if (matches.size() < 1) {
            fail("Not enough matches in the scoreboard to run this test.");
        }

        Team existingAwayTeam = matches.get(0).getAwayTeam();

        Team newHomeTeam = new Team("NewHomeTeam");
        Team newAwayTeam = new Team("NewAwayTeam");

        assertAll(
                () -> assertThrows(AlreadyExistsException.class, () -> scoreBoardService.newMatch(newHomeTeam, existingAwayTeam)),
                () -> assertThrows(AlreadyExistsException.class, () -> scoreBoardService.newMatch(existingAwayTeam, newAwayTeam))
        );
    }

    @Test
    void newMatch_HomeTeamBusy_ThrowsAlreadyExists() {
        List<Match> matches = new ArrayList<>(scoreBoardService.getSortedSummary());

        if (matches.size() < 1) {
            fail("Not enough matches in the scoreboard to run this test.");
        }

        Team existingHomeTeam = matches.get(0).getHomeTeam();

        Team newHomeTeam = new Team("NewHomeTeam");
        Team newAwayTeam = new Team("NewAwayTeam");

        assertAll(
                () -> assertThrows(AlreadyExistsException.class, () -> scoreBoardService.newMatch(newHomeTeam, existingHomeTeam)),
                () -> assertThrows(AlreadyExistsException.class, () -> scoreBoardService.newMatch(existingHomeTeam, newAwayTeam))
        );
    }

    @Test
    void newMatch_InvalidInput_NullInput() {
        assertAll(
                () -> assertThrows(InvalidArgumentException.class, () -> scoreBoardService.newMatch(new Team("Home2"), null)),
                () -> assertThrows(InvalidArgumentException.class, () -> scoreBoardService.newMatch(null, new Team("Away2")))
        );
    }

    @Test
    void updateScore_ValidInput() {
        int homeScore = 2;
        int awayScore = 1;
        Team homeTeam = new Team("home");
        Team awayTeam = new Team("away");
        UUID uuid = scoreBoardService.newMatch(homeTeam, awayTeam);
        scoreBoardService.updateScore(uuid, homeScore, awayScore);
        Match actual = getMatchById(uuid);
        assertEquals(homeScore, actual.getHomeScore());
        assertEquals(awayScore, actual.getAwayScore());
    }

    private Match getMatchById(UUID uuid) {
        return scoreBoardService.getSortedSummary().stream()
                .filter(match -> match.getMatchUuid().equals(uuid))
                .findFirst()
                .orElseThrow(() -> new NotFoundException(uuid));
    }

    @Test
    void updateScore_InvalidInput_ThrowsException() {
        int homeScore = 2;
        int awayScore = 1;
        Team homeTeam = new Team("home");
        Team awayTeam = new Team("away");
        UUID uuid = scoreBoardService.newMatch(homeTeam, awayTeam);

        assertAll(
                () -> assertThrows(InvalidArgumentException.class, () -> scoreBoardService.updateScore(null, homeScore, awayScore)),
                () -> assertThrows(InvalidArgumentException.class, () -> scoreBoardService.updateScore(uuid, null, awayScore)),
                () -> assertThrows(InvalidArgumentException.class, () -> scoreBoardService.updateScore(uuid, homeScore, null)),
                () -> assertThrows(InvalidArgumentException.class, () -> scoreBoardService.updateScore(uuid, -1, awayScore)),
                () -> assertThrows(InvalidArgumentException.class, () -> scoreBoardService.updateScore(uuid, homeScore, -1)),
                () -> assertThrows(NotFoundException.class, () -> scoreBoardService.updateScore(UUID.randomUUID(), homeScore, awayScore))
        );
    }

    @Test
    void finishMatchByUUID_ExistingMatch_RemovesMatch() {
        List<Match> sortedSummary = new ArrayList<>(scoreBoardService.getSortedSummary());
        int removedCounter = 0;

        for (Match match : sortedSummary) {
            assertDoesNotThrow(() -> scoreBoardService.finishMatchByUUID(match.getMatchUuid()));
            removedCounter++;
        }

        assertEquals(sortedSummary.size() - removedCounter, scoreBoardService.getSortedSummary().size());
    }

    @Test
    void finishMatchByUUID_NonExistingMatch_ThrowsNotFoundException() {
        assertThrows(NotFoundException.class, () -> scoreBoardService.finishMatchByUUID(UUID.randomUUID()));
        assertThrows(NotFoundException.class, () -> scoreBoardService.finishMatchByUUID(null));
    }

    @Test
    void getSortedSummary_ReturnsSortedMap() {
        UUID match2Uuid = scoreBoardService.newMatch(new Team("home2"), new Team("away2"));
        UUID match3Uuid = scoreBoardService.newMatch(new Team("home3"), new Team("away3"));
        scoreBoardService.updateScore(match2Uuid, 1, 1);
        scoreBoardService.updateScore(match3Uuid, 2, 2);

        assertTrue(checkOrder(new ArrayList<>(scoreBoardService.getSortedSummary())));
    }

    private boolean checkOrder(List<Match> list) {
        List<Match> copyOfList = new ArrayList<>(list);
        copyOfList.sort(Collections.reverseOrder(Comparator.comparingInt(match -> match.getHomeScore() + match.getAwayScore())));
        return list.equals(copyOfList);
    }
}
