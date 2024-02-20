# Start with CentOS 7 base image
FROM centos:7

# Install R pre-requisites
RUN yum -y update && yum -y install epel-release
RUN yum -y groupinstall "Development Tools"
RUN yum -y install wget

# R is available in this repository
RUN yum install epel-release -y

# Install R following instructions from CRAN
# This might need to be updated depending on the latest available R version
RUN yum -y install R

# Install additional dependencies for RStudio Server
RUN yum -y install openssl-devel libcurl-devel

# Download and install RStudio Server
# Check for the latest version of RStudio Server for CentOS and replace the URL below
RUN wget https://download2.rstudio.org/server/centos7/x86_64/rstudio-server-rhel-2023.12.1-402-x86_64.rpm
RUN yum -y install rstudio-server-rhel-2023.12.1-402-x86_64.rpm

# Clean up the cache to reduce the layer size
RUN rm rstudio-server-rhel-2023.12.1-402-x86_64.rpm && \
    yum clean all && \
    rm -rf /var/cache/yum

# Expose the RStudio Server port
EXPOSE 8787

# Add a default user for RStudio
# Replace 'rstudio' and 'your_password_here' with your desired username and password
RUN useradd rstudio
RUN echo "rstudio:Init1234" | chpasswd

# Set default command to run RStudio Server
CMD ["/usr/lib/rstudio-server/bin/rserver", "--server-daemonize", "0"]
