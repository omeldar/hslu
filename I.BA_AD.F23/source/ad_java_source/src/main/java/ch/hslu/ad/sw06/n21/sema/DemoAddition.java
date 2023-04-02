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
package ch.hslu.ad.sw06.n21.sema;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Demo DemoAddition und Ausgabe mit Semaphore.
 */
public final class DemoAddition {

    private static final Logger LOG = LogManager.getLogger(DemoAddition.class);
    private static final Semaphore SEMA = new Semaphore();
    private static long sum = 0;

    /**
     * Privater Konstruktor.
     */
    private DemoAddition() {
    }

    /**
     * Main-Demo.
     *
     * @param args not used.
     */
    public static void main(final String args[]) {
        // Thread addiert...
        Thread t1 = new Thread(() -> {
            for (int i = 1; i <= 10000; i++) {
                sum += i;
            }
            LOG.info("calc finished, notifing...");
            SEMA.release();
        });
        // Thread wartet auf Summe...
        Thread t2 = new Thread(() -> {
            LOG.info("wait for result...");
            try {
                SEMA.acquire();
            } catch (InterruptedException ex) {
                return;
            }
            LOG.info("sum = " + sum);
        });
        // Ausgabe zuerst starten...
        t2.start();
        t1.start();
    }
}
