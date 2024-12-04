#!/usr/bin/env bash
set -e
PHASE=${1-alpha}
./gradlew :bootstrap:clean
./gradlew :bootstrap:bootBuildImage --imageName=sels-$PHASE

aws ecr get-login-password --region ap-northeast-2 | docker login --username AWS --password-stdin 739275469392.dkr.ecr.ap-northeast-2.amazonaws.com

docker tag sels-$PHASE:latest 739275469392.dkr.ecr.ap-northeast-2.amazonaws.com/sels-$PHASE:latest

docker push 739275469392.dkr.ecr.ap-northeast-2.amazonaws.com/sels-$PHASE:latest
