#!/bin/sh
docker build . -t twitter-demo-kotlin-v2
echo
echo
echo "To run the docker container execute:"
echo "    $ docker run -p 8080:8080 twitter-demo-kotlin-v2"
