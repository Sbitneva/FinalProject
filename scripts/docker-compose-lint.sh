pushd "$(dirname $0)/../compose"
ls -1 | grep docker-compose | xargs -I{} docker-compose -f {} config --quiet
popd