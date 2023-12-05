#!/bin/bash

IMAGE=registry.ldblao.la/tikgithub/merchant

if [ -n "$1" ]; then VERSION=$1; else VERSION="latest";fi

echo "Building image $IMAGE:$VERSION"

mvn clean dependency:tree compile package

docker build -t $IMAGE:$VERSION .
docker push $IMAGE:$VERSION
