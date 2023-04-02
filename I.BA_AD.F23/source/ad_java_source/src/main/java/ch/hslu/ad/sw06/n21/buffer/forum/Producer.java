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
package ch.hslu.ad.sw06.n21.buffer.forum;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Produzent, der eine maximale Anzahl Werte produziert und diese in die
 * SimpleQueue speichert.
 */
public final class Producer implements Runnable {

    private static final Logger LOG = LogManager.getLogger(Producer.class);
    private final SimpleQueue queue;
    private final int maxRange;
    private volatile Thread runThread;

    /**
     * Erzeugt einen Produzent, der eine bestimmte Anzahl Integer-Werte
     * produziert.
     *
     * @param queue Queue zum Speichern der Integer-Werte.
     * @param max Anzahl Integer-Werte.
     */
    public Producer(final SimpleQueue queue, final int max) {
        this.queue = queue;
        this.maxRange = max;
    }

    @Override
    public void run() {
        runThread = Thread.currentThread();
        for (int i = 0; i < maxRange; i++) {
            try {
                queue.put(i);
                LOG.info(runThread.getName() + " Put: " + i);
            } catch (InterruptedException ex) {
                LOG.error(ex.getMessage());
            }
        }
        System.exit(0);
    }
}
