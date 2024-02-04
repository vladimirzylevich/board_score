package football.board.service.impl;


import football.board.entity.Team;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class ConcurrentFootballServiceTest {

    @Test
    void concurrentUsage_ShouldNotThrowExceptions() throws InterruptedException {
        ConcurrentFootballService concurrentFootballService = new ConcurrentFootballService();

        int numThreads = 10;
        ExecutorService executorService = Executors.newFixedThreadPool(numThreads);
        CountDownLatch startLatch = new CountDownLatch(1);
        CountDownLatch endLatch = new CountDownLatch(numThreads);

        for (int i = 0; i < numThreads; i++) {
            executorService.submit(() -> {
                try {
                    startLatch.await(); // Wait until all threads are ready
                    performConcurrentOperations(concurrentFootballService);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    executorService.shutdown();
                } finally {
                    endLatch.countDown(); // Signal that this thread has finished
                }
            });
        }

        startLatch.countDown(); // Allow all threads to start simultaneously
        endLatch.await(); // Wait until all threads have finished

        executorService.shutdown(); // Shutdown the executor service
        executorService.awaitTermination(10, TimeUnit.SECONDS);
        // If no exceptions are thrown during concurrent usage, the test passes
    }

    private void performConcurrentOperations(ConcurrentFootballService concurrentFootballService) {
        Team homeTeam = new Team("Home");
        Team awayTeam = new Team("Away");

        assertDoesNotThrow(() -> concurrentFootballService.newMatch(homeTeam, awayTeam));
        assertDoesNotThrow(() -> concurrentFootballService.updateScore(concurrentFootballService.getSortedSummary().iterator().next().getMatchUuid(), 1, 1));
        assertDoesNotThrow(() -> concurrentFootballService.finishMatchByUUID(concurrentFootballService.getSortedSummary().iterator().next().getMatchUuid()));
    }
}
