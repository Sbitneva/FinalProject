#!/bin/bash
pushd "$(dirname $0)/../compose"
docker-compose -f docker-compose-immutable-psql.yml down
docker-compose -f docker-compose-immutable-psql.yml up -d postgres
../scripts/pg-ready.sh docker-compose-immutable-psql.yml
popd
