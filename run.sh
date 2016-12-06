#! /bin/sh

if ! [ -e "environment.sh" ]; then
  echo "environment.sh doesn't exist. Consult the readme.md and"
  echo "build your own environment.sh based on environment.sample.sh"
  exit 1
fi

source environment.sh
lein ring server-headless
