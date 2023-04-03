# Semaphore

## 3.a

```Java
public final class Semaphore {
    private int sema; // Semaphor Zähler
    public Semaphore(final int init) {
        sema = init;
    }

    public synchronized void acquire() throws InterruptedException {
        while (sema == 0) {
            this.wait();
        }
        sema--;
    }

    public synchronized void release() {
        sema++;
        this.notifyAll();
    }
}
```

### Wie fair ist das oben vorgestellte Semaphor?

Nein, diese Implementation ist nicht fair, da sie nicht auf die Reihenfolge schaut.

### Was ist die Ursache für die entsprechende Fairness?

Fairness bei Semaphoren generell bedeutet, dass die Threads in der Reihenfolge Zugriff zu einer Ressource kriegen, in der sie diese angefordert haben. Also im FIFO Prinzip.

### Wie könnten Sie die bestehende Fairness verbessern?

Wir müssten das `acquire()` so anpassen, dass sie einen fairen synchronisatzions Mechanismus nutzt, so wie `ReentrantLock` mit einer fairen Policy, anstelle `wait()` und `notifyAll()`.

## 3.b

### Wo hat die Methode `release` Verbesserungspotenzial?

Man müsste ein private `lock` erstellen in der Klasse und nutzen anstelle auf `this` zu locken.

### Was benötigen wir um das Verbesserungspotential umzusetzen?

Um eine vollständige Fairness zu erreichen, benötigen wir einen ausgefeilteren Mechanismus, wie eine Warteschlange, die die Reihenfolge der wartenden Threads aufrechterhält.

## 3.c

Um eine Methode zu schreiben, welche eine Semaphore mit einer maximalen Anzahl `permits` initialisieren kann, müssen wir sicherstellen, dass die maximale Anzahl nicht kleiner ist als die initial gegebenen `permits`:

```Java
/**
 * Erzeugt ein nach oben begrenztes Semaphore.
 *
 * @param permits Anzahl Passiersignale zur Initialisierung.
 * @param limit maximale Anzahl der Passiersignale.
 * @throws IllegalArgumentException wenn Argumente ungültige Werte besitzen.
 */
public Semaphore(final int permits, final int limit) throws IllegalArgumentException {
    if (permits < 0) {
        throw new IllegalArgumentException("Initial permits must be >= 0");
    }

    if (limit < 1) {
        throw new IllegalArgumentException("Limit must be >= 1");
    }

    if (permits > 0 && permits >= limit) {
        throw new IllegalArgumentException("Initial permits must be less than limit");
    }

    this.sema = permits;
    this.limit = limit;
}
```

### Was sind ungültige Argument-Werte beim Konstruktor, die eine `IllegalArgumentException` werfen?

Hier muss einiges Stimmen. Folgende Regeln gelten:

1. Die initial `permits` müssen grösser oder gleich 0 sein.
2. Das `limit` muss grösser als 1 sein.
3. Initial `limit` muss kleiner sein als initial `permits`

### Wie initialisiert ein Default-Konstruktor die Attribute des nach oben begrenzten Semaphors?

```Java
public Semaphore() {
    this(0, UNLIMITED_BOUND);
}
```

Default mässig wird mit 0 Initial-`permits` und mit keinem `limit` gearbeitet.

### Welche Methoden sind vom Limit des Semaphors betroffen?

`release()`, `release(final int permits)`, `acquire()`, `acquire(final int permits)`

### Wie reagieren diese Methoden, wenn das Limit überschritten wird?

Es wird in allen Fällen eine `IllegalStateException()` mit der Nachricht "Permits to acquire exceed the limit" oder "Permits to release exceeded the limit" geworfen, wenn das Limit überschritten wird.

## 3.4 Reflektion

### Wie würden Sie Ihr Semaphor einordnen - Windhund Prinzip Ja oder Nein? Begründen Sie Ihre Antwort.

Es wird in der Implementation nicht garantiert, dass die Threads der Reihe nach, wie sie die Anfrage gestellt haben, dran kommen. Jedoch hat jeder Thread die selbe Chance, jedesmal wenn die Semaphore frei wird.

Das Windhund-Prinzip ist somit nicht erfüllt. Jedoch ist die Auswahl des Threads zufällig.

### Die Fragen aus a betreffen nur das Semaphor. Wie würde im Allgemeinen eine faire Umsetzung beim "Warten auf Bedingungen" aussehen?

Jeder Thread müsste in der Reihenfolge in der er ankommt auch auf die Ressource dürfen. Dass nun ein "Platz frei wurde" dürfte nur der nächste Thread jeweils mitbekommen, damit es fair ist.
