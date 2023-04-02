# WEBT Projekt
## Setup Info
Follow the steps below to setup the project with docker.

1. Clone the project
2. Navigate into the project folder
3. Execute the command `docker build -t webt:latest .`
4. Execute the command `docker-compose up -d`

The application should be running now on localhost:80

### Dockerfile

```Dockerfile
FROM php:8.2-rc-apache-bullseye

# Configure Apache
COPY httpd.conf /etc/apache2/conf-available
RUN a2enconf httpd

# Copy content
WORKDIR /var/www
COPY *.html ./html
COPY *.php ./html
COPY js/ ./js
COPY css/ ./css
COPY img/ ./img

# Run
EXPOSE 80
CMD ["apache2-foreground"]
```
### docker-compose.yml
```yml
version: '3.7'

services:
  web:
    image: webt:latest
    deploy:
      resources:
        limits:
          memory: 512M
        reservations:
          memory: 128M
    volumes:
      - "../webt-container-volume:/var/log/apache2"
    ports:
      - "80:80"
```