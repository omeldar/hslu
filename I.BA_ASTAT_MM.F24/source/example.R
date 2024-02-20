# Install ggplot2 if not already installed

if (!requireNamespace("ggplot2", quietly = TRUE)) {
install.packages("ggplot2")
}

# Load the ggplot2 library

library(ggplot2)

# Generate a dataset of random numbers

set.seed(123) # Setting seed for reproducibility
data <- data.frame(
Index = 1:10,
RandomNumbers = rnorm(10) # 10 random numbers from a normal distribution
)

# Calculate the cumulative sum

data$CumulativeSum = cumsum(data$RandomNumbers)

# Plotting the data

ggplot(data, aes(x = Index)) +
geom_line(aes(y = RandomNumbers, color = "Random Numbers")) +
geom_line(aes(y = CumulativeSum, color = "Cumulative Sum")) +
labs(title = "Random Numbers and Their Cumulative Sum",
x = "Index",
y = "Value") +
scale_color_manual("",
breaks = c("Random Numbers", "Cumulative Sum"),
values = c("blue", "red"))