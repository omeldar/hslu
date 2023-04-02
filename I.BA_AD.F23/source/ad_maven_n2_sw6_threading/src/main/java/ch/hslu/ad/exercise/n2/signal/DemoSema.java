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
package ch.hslu.ad.exercise.n2.signal;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Demonstration eines Semaphors.
 */
public final class DemoSema {

    private static final Logger LOG = LogManager.getLogger(DemoSema.class);

    /**
     * Privater Konstruktor.
     */
    private DemoSema() {
    }

    /**
     * Main-Demo.
     *
     * @param args not used.
     * @throws InterruptedException wenn das warten unterbrochen wird.
     */
    public static void main(final String[] args) throws InterruptedException {
        try {
            new Semaphore(4, 3);
        } catch (Exception e) {
            LOG.debug(e.getMessage());
        }
        try {
            Semaphore sema = new Semaphore(3, 3);
            sema.release();
        } catch (Exception e) {
            LOG.debug(e.getMessage());
        }
        try {
            Semaphore sema = new Semaphore(0, 3);
            sema.release(4);
        } catch (Exception e) {
            LOG.debug(e.getMessage());
        }
        try {
            Semaphore sema = new Semaphore(3, 3);
            sema.acquire(4);
        } catch (Exception e) {
            LOG.debug(e.getMessage());
        }
        try {
            Semaphore sema = new Semaphore(3, 3);
            sema.acquire(-1);
        } catch (Exception e) {
            LOG.debug(e.getMessage());
        }
        try {
            Semaphore sema = new Semaphore(1, 3);
            sema.release(-1);
        } catch (Exception e) {
            LOG.debug(e.getMessage());
        }
    }
}
