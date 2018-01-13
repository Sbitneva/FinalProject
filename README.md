| **dev** | [![Travis CI Build Status](https://img.shields.io/travis/Sbitneva/FinalProject/dev.svg?style=flat-square)](https://travis-ci.org/Sbitneva/FinalProject) [![Codecov.io Coverage Status](https://img.shields.io/codecov/c/github/Sbitneva/FinalProject/dev.svg?style=flat-square)](https://codecov.io/gh/Sbitneva/FinalProject) [![VersionEye Dependency Status](https://www.versioneye.com/user/projects/5a57df910fb24f3b4514c58e/badge.svg?style=flat-square)](https://www.versioneye.com/user/projects/5a57df910fb24f3b4514c58e) |
|---|---|

# Final Project

## Ship cruise company management system

Company can have several ships.
Ship have a passenger capacity, a cruise route, a number of ports to visit, a cruise duration and staff. The client chooses cruise and pays for it. Also chooses excursions upon arrival at the port for additional payment. The cruise ship administrator is responsible for passengers bonuses, including ticket class (pool, gym, cinema room, beauty salons, etc.)

## Requirements installation

- Update package index
```bash
sudo apt update
```

- git
```bash
sudo apt-get install -y git
```

- [docker-ce](https://docs.docker.com/engine/installation/linux/docker-ce/ubuntu/#install-docker-ce)
```bash
sudo apt-get install apt-transport-https ca-certificates curl software-properties-common
curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo apt-key add -
sudo add-apt-repository "deb [arch=amd64] https://download.docker.com/linux/ubuntu $(lsb_release -cs) stable"
sudo apt-get update
sudo apt-get install docker-ce
sudo groupadd docker
sudo usermod -aG docker $USER
```

- [docker-compose](https://docs.docker.com/compose/install/#install-compose)
```bash
sudo apt-get update && sudo apt-get install -y python-pip
sudo pip install docker-compose
```

## Project build and installation

1. Clone the project
    ```bash
    git clone https://www.github.com/Sbitneva/FinalProject
    cd FinalProject
    ```
2. Build project's _java_ source
    ```bash
    docker run -it --rm             \
        -v "$PWD":/usr/src/app      \
        -w /usr/src/app             \
        maven:3.5.2-jdk-8-alpine    \
        mvn clean install
    ```
3. Change built _target_ files owner from `root` to current user `$UID`
    ```bash
    sudo chown -R $UID:$UID "$PWD/target"
    ```
4. Build _webapp_ docker image
    ```bash
    docker-compose build
    ```
5. Create external docker volume for _webapp_ persistent database storage
    ```bash
    docker volume create db
    ```
6. Create external user-defined docker network for _postgres_ and _webapp_ services
    ```bash
    docker network create back-end
    ```

## Run project services

```bash
docker-compose up -d
```

_webapp_ service is now accessible at `http://localhost/`

## Stop project services

```bash
docker-compose down
```

## Presentation
_TODO_
