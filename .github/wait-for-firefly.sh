#!/bin/bash

set -e

times=15
while ! curl -sSL 'http://localhost/register' 2>&1 \
             | grep '<html' >/dev/null; do
    echo 'Waiting for the Firefly'
    sleep 10
    times=$(($times - 1))

    if [ $times -le 0 ]; then
        echo 'Time out'
        exit 1
    fi
done

echo 'The Firefly is up'