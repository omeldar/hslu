package ch.hslu.ad.sw06.n2.signal;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class SemaphoreTest {
    @Test
    public void ctorUnlimitedSemaphoreTest() {
        assertDoesNotThrow(() -> new Semaphore());
    }

    @Test
    public void ctorLimitSemaphoreTest() {
        assertDoesNotThrow(() -> new Semaphore(3, 3));
    }

    @Test
    public void ctorInitialPermitExceedBoundTest() {
        assertThrows(IllegalArgumentException.class, () -> new Semaphore(4, 3));
    }

    @Test
    public void ctorNegativeInitialPermitsTest() {
        assertThrows(IllegalArgumentException.class, () -> new Semaphore(-1));
    }

    @Test
    public void releaseTooManyPermitsTest() {
        var semaphore = new Semaphore(0, 3);

        assertThrows(IllegalStateException.class, () -> semaphore.release(4));
    }

    @Test
    public void acquireTooManyPermitsTest() {
        var semaphore = new Semaphore(3, 3);

        assertThrows(IllegalStateException.class, () -> semaphore.acquire(4));
    }

    @Test
    public void acquireNegativePermitsTest() {
        var semaphore = new Semaphore();

        assertThrows(IllegalArgumentException.class, () -> semaphore.acquire(-1));
    }
}
