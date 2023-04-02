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
package ch.hslu.ad.sw06.n21.buffer.sema;

/**
 * Produzent, der eine maximale Anzahl Werte produziert und diese in eine Queue
 * speichert.
 */
public final class Producer implements Runnable {

    private final BoundedBuffer<Integer> queue;
    private final int maxRange;
    private long sum;

    /**
     * Erzeugt einen Produzent, der eine bestimmte Anzahl Integer-Werte
     * produziert.
     *
     * @param queue Queue zum Speichern der Integer-Werte.
     * @param max Anzahl Integer-Werte.
     */
    public Producer(final BoundedBuffer<Integer> queue, final int max) {
        this.queue = queue;
        this.maxRange = max;
        this.sum = 0;
    }

    @Override
    public void run() {
        for (int i = 0; i < maxRange; i++) {
            try {
                sum += i;
                queue.add(i);
            } catch (InterruptedException ex) {
                return;
            }
        }
    }

    /**
     * Liefert die Summe aller gespeicherter Werte.
     *
     * @return Summe.
     */
    public long getSum() {
        return sum;
    }
}
