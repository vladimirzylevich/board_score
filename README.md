# Football Board Project

This project implements a football scoreboard system with features to manage matches, scores, and teams.

## Overview

The project is organized into several packages:

- `football.board.entity`: Contains the entities such as `Match` and `Team`.
- `football.board.exception`: Custom exception classes for error handling.
- `football.board.service`: Service interfaces and implementations for managing matches, scores, and summaries.
- `football.board.storage`: Storage interfaces and implementations for storing matches and teams.

## Key Classes

### `Team`

Represents a football team with a unique identifier (`UUID`) and a name.

### `Match`

Represents a football match between two teams with details such as scores and a unique identifier (`UUID`).

### `FootballWorldScoreBoardService`

Implements the main scoreboard service, managing new matches, updating scores, and providing match summaries.

### `ConcurrentFootballService`

Extends the scoreboard service to handle concurrent usage by synchronizing critical sections.

## Usage

To use the football scoreboard services, you can create instances of `FootballWorldScoreBoardService` or `ConcurrentFootballService` and perform operations like creating matches, updating scores, and retrieving summaries.

```java
// Example Usage
FootballWorldScoreBoardService scoreBoardService = new FootballWorldScoreBoardService();
Team homeTeam = new Team("Home");
Team awayTeam = new Team("Away");
UUID matchUuid = scoreBoardService.newMatch(homeTeam, awayTeam);
scoreBoardService.updateScore(matchUuid, 2, 1);
Collection<Match> summary = scoreBoardService.getSortedSummary();
