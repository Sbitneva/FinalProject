pushd $(dirname $0)
ls -1 | grep docker-compose | xargs -I{} docker-compose -f {} config --quiet
popd