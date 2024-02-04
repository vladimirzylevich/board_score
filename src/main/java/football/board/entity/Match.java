package football.board.entity;

import java.util.Collections;
import java.util.Comparator;
import java.util.UUID;

public class Match {
    private final UUID matchUuid;

    private final Team homeTeam;

    private final Team awayTeam;

    private Integer homeScore;

    private Integer awayScore;

    public Match(Team homeTeam, Team awayTeam) {
        this.matchUuid = UUID.randomUUID();
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.homeScore = 0;
        this.awayScore = 0;
    }
    public Match(Match original) {
        this.matchUuid = original.matchUuid;
        this.homeTeam = original.homeTeam;
        this.awayTeam = original.awayTeam;
        this.homeScore = original.homeScore;
        this.awayScore = original.awayScore;
    }

    public UUID getMatchUuid() {
        return matchUuid;
    }

    public Team getHomeTeam() {
        return homeTeam;
    }

    public Team getAwayTeam() {
        return awayTeam;
    }

    public Integer getHomeScore() {
        return homeScore;
    }

    public void setHomeScore(Integer homeScore) {
        this.homeScore = homeScore;
    }

    public Integer getAwayScore() {
        return awayScore;
    }

    public void setAwayScore(Integer awayScore) {
        this.awayScore = awayScore;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Match{");
        sb.append("matchUuid=").append(matchUuid);
        sb.append(", homeTeam=").append(homeTeam);
        sb.append(", awayTeam=").append(awayTeam);
        sb.append(", homeScore=").append(homeScore);
        sb.append(", awayScore=").append(awayScore);
        sb.append('}');
        return sb.toString();
    }

    public static Comparator<Match> getSortComparator() {
        return Collections.reverseOrder(Comparator.comparingInt(match -> match.getHomeScore() + match.getAwayScore()));
    }
}
