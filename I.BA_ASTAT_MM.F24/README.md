# Programming with R in a container

This setup allows you to work with R in R Studio in the browser.

## Setup

You will have to install Docker first if you haven't already!

1. Clone repository with

HTTPS:

```
git clone https://github.com/omeldar/r-in-a-container.git
```

SSH:

```
git@github.com:omeldar/r-in-a-container.git
```

2. Execute the command below in the git repositories folder.

```
docker compose up -d
```

_This step might take up to 2-15 minutes depending on your device's performance and your download speed._

3. When the container has started go to your browser and visit: [http://localhost:8787/](http://localhost:8787/)
4. Log in with user: **rstudio** and default password: **Init1234**

## Adding new scripts

If you want to add new scripts to your environment (f.e. after you get them from the teacher). Put the scripts into the `data` directory where you can also find the `example.R` script. You'll see in RStudio on the right bottom side, that another folder scripts will appear, from which you can then load the scripts (that are locally in the `data` folder).

## Default Working Directory

Default working directory is set to be `home/rstudio`. So to load csv files from the data folder you can just use `read.csv("source/data/filename.csv")`.

## Example R script

[View](example.R)

## Installing R packages

Install additional r packages using the below command directly in RStudio

```
install.packages("package-name")
```

_Replace 'package-name' with the package you want to install._

## Changing user and password

If you want to change the username and password to log in, you can do so in the `docker-compose.yml` under `environment`.

_I did not try this so far, you might need to change it in the Dockerfile too._
