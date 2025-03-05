#!/usr/bin/env bash
set -e

aws ecs update-service --cluster alpha-cluster --service sels-alpha --task-definition sels-alpha --force-new-deployment
