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

/**
 * Sehr einfache Blocking Queue (gefunden im Forum java-samples.com).
 */
public final class SimpleQueue {

    private int value;
    private boolean valueSet = false;

    /**
     * Falls ein Wert gespeichert wurde, diesen auslesen, ansonsten warten.
     *
     * @return Integer Wert.
     * @throws InterruptedException wenn beim Warten unterbrochen.
     */
    public synchronized int get() throws InterruptedException {
        if (!valueSet) {
            this.wait();
        }
        valueSet = false;
        this.notify();
        return value;
    }

    /**
     * Ein Wert speichern, falls noch keiner gespeicher ist, ansonsten warten.
     *
     * @param value zu speichernder Integer Wert.
     * @throws InterruptedException wenn beim Warten unterbrochen.
     */
    public synchronized void put(final int value) throws InterruptedException {
        if (valueSet) {
            this.wait();
        }
        this.value = value;
        valueSet = true;
        this.notify();
    }
}
