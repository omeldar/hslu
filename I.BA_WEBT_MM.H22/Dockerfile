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