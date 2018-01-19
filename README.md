| **dev** | [![Travis CI Build Status](https://img.shields.io/travis/Sbitneva/FinalProject/dev.svg?style=flat-square)](https://travis-ci.org/Sbitneva/FinalProject) [![Codecov.io Coverage Status](https://img.shields.io/codecov/c/github/Sbitneva/FinalProject/dev.svg?style=flat-square)](https://codecov.io/gh/Sbitneva/FinalProject) [![VersionEye Dependency Status](https://www.versioneye.com/user/projects/5a57df910fb24f3b4514c58e/badge.svg?style=flat-square)](https://www.versioneye.com/user/projects/5a57df910fb24f3b4514c58e) |
|---|---|

# Final Project

## Ship cruise company management system

Company can have several ships.
Ship have a passenger capacity, a cruise route, a number of ports to visit, a cruise duration and staff. The client chooses cruise and pays for it. Also chooses excursions upon arrival at the port for additional payment. The cruise ship administrator is responsible for passengers bonuses, including ticket class (pool, gym, cinema room, beauty salons, etc.)

## Requirements

- Linux Ubuntu (Server) LTS 16.04
- [docker-ce](https://docs.docker.com/engine/installation/linux/docker-ce/ubuntu/#install-docker-ce)
- [docker-compose](https://docs.docker.com/compose/install/#install-compose)
- git
- maven
- postgresql

## Manual Build

1. Clone the project
    ```bash
    git clone https://www.github.com/Sbitneva/FinalProject
    ```

2. Change directory
    ```bash
    cd FinalProject
    ```

3. Run clean and default lifecycles (inclusive up to install phase)
    ```bash
    mvn clean install
    ```

## Run

```bash
DATABASE_URL=postgresql://localhost:5432/cruise_company \
java -jar cruise-company-1.0-SNAPSHOT.jar
```

- `DATABASE_URL` for full remote or local database path, in case of nothing provided the fallback value is `postgresql://localhost:54321/cruise_company`, which is used as immutable DB path at the test phase

_webapp_ service will be accessible at `http://localhost:8080/`
