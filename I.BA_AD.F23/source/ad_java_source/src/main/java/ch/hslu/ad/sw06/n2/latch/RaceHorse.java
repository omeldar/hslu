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
package ch.hslu.ad.sw06.n2.latch;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Random;

/**
 * Ein Rennpferd, das durch ein Startsignal losläuft. Nach einer zufälligen Zeit
 * kommt es im Ziel an.
 */
public final class RaceHorse implements Runnable {

    private static final Logger LOG = LogManager.getLogger(ch.hslu.ad.sw06.n2.latch.RaceHorse.class);
    private final Synch startSignal;
    private volatile Thread runThread;
    private final Random random;

    /**
     * Erzeugt ein Rennpferd, das in die Starterbox eintritt.
     *
     * @param startSignal Starterbox.
     */
    public RaceHorse(Synch startSignal) {
        this.startSignal = startSignal;
        this.random = new Random();
    }

    @Override
    public void run() {
        runThread = Thread.currentThread();
        LOG.info("Rennpferd {} geht in die Box.", runThread.getName());
        try {
            startSignal.acquire();
            LOG.info("Rennpferd {} laeuft los...", runThread.getName());
            Thread.sleep(random.nextInt(3000));
        } catch (InterruptedException ex) {
            LOG.debug(ex);
            runThread.interrupt();
        }
        LOG.info("Rennpferd {} ist im Ziel.", runThread.getName());
    }
}
