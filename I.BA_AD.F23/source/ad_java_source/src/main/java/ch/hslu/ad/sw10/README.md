# SW10

## 2 Quicksort – klassisch programmiert (ca. 90')

For the quickSort implementation given by the teachers following execution times were recorded on my machine for
an array of 300'000 characters.

| Execution | Time in ms |
|-----------|------------|
| 1         | 535        |
| 2         | 556        |
| 3         | 571        |
| 4         | 510        |  
| 5         | 570        |
| 6         | 556        |
| 7         | 658        |
| 8         | 558        |
| 9         | 430        |
| 10        | 546        |
| 11        | 430        |
| 12        | 520        |
| 13        | 506        |
| 14        | 544        |
| 15        | 536        |

Outliers: 430, 658 - Median: 544

Without the first five:

Outliers: 540, 658 - Median: 540

For n = 500'000 on my system I get a StackOverflow Exception

In an array of 500'000 characters every character, for example 'a' will approximately be 19'230 times in this array.
So we store lots of doubled values. The quick sort algorithm implemented will be adapted to exchange same elements as well.

