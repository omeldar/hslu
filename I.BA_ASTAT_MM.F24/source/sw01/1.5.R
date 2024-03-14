# a)
?seq

# b)
x <- c(4, 10, 3, NA, NA, 1, 8)

# b-i) 
mean(x) # In R, the function mean(x) returns NA if any of the values in the vector x are NA, 
        # due to the default behavior of the mean function and the way R handles NA values. 
        # NA in R represents missing or undefined data. When you perform calculations involving NA, the result is often NA 

mean(x[!is.na(x)]) # mean of all values != NA

sort(x)
order(x) # returns the indices of a vector that sort the array, without directly sorting it

# c)
z <- c(4,2, 8,9,7, 5,2, 1)
plot(z)

# Plotting with lines between nodes
plot(z, type= "l", col= "blue", lty= 2, main= "Haupttitel", xlab= "EinpaarZahlen", ylab= "AndereZahlen" )

# Add static lines to chart, v = x, h = y
abline(v=3, col = "green")
abline(h=4, col = "red")


# values without NA since they are not defined
u <- x[!is.na(x)]

# Calculate the corresponding y values
y <- 2 * u + 1

# Add a brown, dashed line representing y = 2x + 1
abline(a = 1, b = 2, col = "brown", lty = "dashed")
