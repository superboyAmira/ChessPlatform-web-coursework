#!/bin/bash

docker run --name redis -d -p 6379:6379 redis
docker ps

# docker exec -it redis redis-cli
