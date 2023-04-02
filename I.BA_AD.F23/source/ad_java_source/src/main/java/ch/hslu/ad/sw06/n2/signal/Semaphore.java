/*
 * Copyright 2023 Hochschule Luzern - Informatik.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ch.hslu.ad.sw06.n2.signal;

/**
 * Ein nach oben nicht begrenztes Semaphor, d.h. der Semaphoren-zähler kann
 * unendlich wachsen.
 */
public final class Semaphore {
    public static int UNLIMITED_BOUND = -1;

    private Object lock = new Object();
    private int limit;
    private int availablePermits; // Semaphoren-zähler
    private int waitingCount; // Anzahl der wartenden Threads.

    /**
     * Erzeugt ein Semaphore mit 0 Passiersignalen.
     */
    public Semaphore() {
        this(0, UNLIMITED_BOUND);
    }

    /**
     * Erzeugt ein Semaphore mit einem Initialwert für den Semaphoren-zähler.
     *
     * @param permits Anzahl Passiersignale zur Initialisierung.
     * @throws IllegalArgumentException wenn der Initialwert negativ ist.
     */
    public Semaphore(final int permits) {
        this(permits, UNLIMITED_BOUND);
    }

    /**
     * Erzeugt ein nach oben begrenztes Semaphore.
     *
     * @param permits Anzahl Passiersignale zur Initialisierung.
     * @param limit maximale Anzahl der Passiersignale.
     * @throws IllegalArgumentException wenn Argumente ungültige Werte besitzen.
     */
    public Semaphore(final int permits, final int limit) {
        if (permits < 0) {
            throw new IllegalArgumentException("Initial permits must be >= 0");
        }

        if (limit > -1 && limit < 1) {  // UNLIMITTED_BOUND is -1
            throw new IllegalArgumentException("Limit must be >= 1");
        }

        if (permits > 0 && permits > limit) {
            throw new IllegalArgumentException("Initial permits must be less than limit");
        }

        this.availablePermits = permits;
        this.limit = limit;
    }

    /**
     * Entspricht dem P() Eintritt (Passieren) in einen synchronisierten
     * Bereich, wobei mitgezählt wird, der wievielte Eintritt es ist. Falls der
     * Zähler null ist, wird der Aufrufer blockiert.
     *
     * @throws java.lang.InterruptedException falls das Warten unterbrochen
     * wird.
     */
    public void acquire() throws InterruptedException {
        acquire(1);
    }

    /**
     * Entspricht dem P() Eintritt (Passieren) in einen synchronisierten
     * Bereich, wobei mitgezählt wird, der wievielte Eintritt es ist. Falls der
     * Zähler null ist, wird der Aufrufer blockiert.
     *
     * @param permits Anzahl Passiersignale zum Eintritt.
     * @throws java.lang.InterruptedException falls das Warten unterbrochen
     * wird.
     */
    public void acquire(final int permits) throws InterruptedException {
        if (permits <= 0){
            throw new IllegalArgumentException("Permits to acquire need to be > 0");
        }

        if (!canAcquire(permits)) {
            throw new IllegalStateException("Permits to acquire exceed the limit");
        }

        synchronized (lock) {
            while (availablePermits < permits) {
                waitingCount++;
                this.wait();
                waitingCount--;
            }

            availablePermits -= permits;
        }
    }

    /**
     * Entspricht dem V() Verlassen (Freigeben) eines synchronisierten
     * Bereiches, wobei ebenfalls mitgezählt wird, wie oft der Bereich verlassen
     * wird.
     */
    public void release() {
        release(1);
    }

    /**
     * Entspricht dem V() Verlassen (Freigeben) eines synchronisierten
     * Bereiches, wobei mitgezählt wird, wie oft der Bereich verlassen wird.
     *
     * @param permits Anzahl Passiersignale zur Freigabe.
     */
    public void release(final int permits) {
        if (permits <= 0) {
            throw new IllegalArgumentException("Number of permits must be at least 1");
        }

        synchronized (lock) {
            if (!canRelease(permits)) {
                throw new IllegalStateException("Permits to release exceeded the limit");
            }

            availablePermits += permits;
            this.notifyAll();
        }
    }

    /**
     * Lesen der Anzahl wartenden Threads.
     *
     * @return Anzahl wartende Threads.
     */
    public int pending() {
        synchronized (lock) {
            return waitingCount;
        }
    }

    /**
     * Überprüfen, ob genug permits angefordert werden können
     *
     * @return Ob genug angefordert werden können.
     */
    private boolean canAcquire(int permitCount) {
        if (limit < 0) {
            return true;
        }

        return permitCount < limit;
    }

    /**
     * Überprüfen, ob genug permits freigegeben werden können
     *
     * @return Ob genug freigegeben werden können.
     */
    private boolean canRelease(int permitCount) {
        synchronized (lock) {
            if (limit < 0) {
                return true;
            }

            return availablePermits + permitCount <= limit;
        }
    }
}
