#!/bin/bash
pushd "$(dirname $0)/../compose"
until docker-compose -f $1 exec postgres pg_isready; do sleep 2; done
popd
