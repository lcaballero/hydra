#!/bin/bash

# Start nginx with local .conf file
sudo nginx -c $(pwd)/app-nginx.conf

# Start fat jar
java -jar target/WebDrop-1.0-SNAPSHOT.jar server hello-world.yml
