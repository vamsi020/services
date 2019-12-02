#!/bin/sh
# Build, Create docker image and Run docker image for Newsletter API !
echo =============================================
echo 			Maven Packege Build
echo =============================================
./mvnw clean install
echo =============================================
echo 			Create Docker Image
echo =============================================
docker build -t vamsi020/newsletter-api .
echo =============================================
echo 			Run Docker Image
echo =============================================
docker run -p 8085:8085 -d -t vamsi020/newsletter-api