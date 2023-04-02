/*
 * Copyright 2023 Hochschule Luzern Informatik.
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
package ch.hslu.ad.sw06.executor.fixedpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Demo eines FixedThreadPool Executors.
 */
public final class DemoFixedThreadPool {

    private static final Logger LOG = LogManager.getLogger(DemoFixedThreadPool.class);

    /**
     * Privater Konstruktor.
     */
    private DemoFixedThreadPool() {
    }

    /**
     * Main-Demo.
     * @param args not used.
     * @throws InterruptedException wenn das Warten unterbrochen wird.
     */
    @SuppressWarnings("deprecation")
    public static void main(String[] args) throws InterruptedException {
        final int nWorker = 1;
        final ExecutorService executor = Executors.newFixedThreadPool(nWorker);
        for (int nTask = 1; nTask <= 4; nTask++) {
            final char ch = (char) (64 + nTask);
            final char chStop = 'X';
            executor.execute(() -> {
                LOG.info("{} starts {}", Thread.currentThread().getName(), ch);
                for (int i = 0; i < 200; i++) {
                    System.out.print(ch);
                    if (ch == chStop && i == 100) {
                        Thread.currentThread().stop(); // nur zu Demonstration!
                    }
                }
                System.out.println("");
                LOG.info("{} finished {}", Thread.currentThread().getName(), ch);
            });
        }
        TimeUnit.MILLISECONDS.sleep(100);
        LOG.info("shutdown");
        executor.shutdownNow();
    }
}
