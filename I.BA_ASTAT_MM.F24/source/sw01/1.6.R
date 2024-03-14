# clear environment
rm(list = ls())

setwd("/home/rstudio/source/data")
data <- read.csv("weather.csv")

# value from 2nd row and 3rd column
data[2,3]

# save 4th column to data1
data1 <- data["Zurich"]

# write data1 to .csv
write.table(data1, file = paste("weather2.csv"),sep=",", row.names = TRUE)

# Name einer Spalte
names(data)[3]
