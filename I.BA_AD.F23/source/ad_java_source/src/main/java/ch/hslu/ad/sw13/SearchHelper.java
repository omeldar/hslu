package ch.hslu.ad.sw13;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class SearchHelper {
    public static void main(String[] args) throws IOException {
        Path filename = Path.of("src/main/java/ch/hslu/ad/sw13/dracula.txt").toAbsolutePath();
        String book = Files.readString(filename);
        System.out.println(filename);

        for (int i = 0; i < 10; i++){
            // start searching
            long start = System.currentTimeMillis();

            PatternSearch.stateSearch(book);

            long stop = System.currentTimeMillis();
            System.out.println(i + ":" + (stop - start));
        }
    }
}
