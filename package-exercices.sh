#!/bin/bash

export PROJECTS_DIR="Exercices"

git archive --format=tar.gz HEAD:${PROJECTS_DIR} > ./exercices.tar.gz
