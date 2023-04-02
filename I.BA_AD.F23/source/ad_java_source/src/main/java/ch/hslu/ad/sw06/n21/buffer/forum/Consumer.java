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
 * Konsument, der soviele Werte aus der SimpleQueue liest, wie er nur kann.
 */
public final class Consumer implements Runnable {

    private static final Logger LOG = LogManager.getLogger(Consumer.class);
    private final SimpleQueue queue;
    private final boolean[] check;
    private volatile Thread runThread;

    /**
     * Erzeugt einen Konsumenten, der soviel Integer-Werte ausliest, wie er nur
     * kann.
     *
     * @param queue Queue zum Lesen der Integer-Werte.
     * @param check Array zum Prüfen auf doppelt ausgelesene Werte.
     */
    public Consumer(final SimpleQueue queue, final boolean[] check) {
        this.queue = queue;
        this.check = check;
    }

    @Override
    public void run() {
        runThread = Thread.currentThread();
        int value;
        while (true) {
            try {
                value = queue.get();
                LOG.info(runThread.getName() + " Got: " + value);
                synchronized (queue) {
                    if (!check[value]) {
                        check[value] = true;
                    } else {
                        LOG.error("double value: " + value);
                    }
                }
            } catch (InterruptedException ex) {
                LOG.error(ex.getMessage());
            }
        }
    }
}
