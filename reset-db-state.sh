docker-compose down && docker container prune -f && docker volume rm db && docker volume create db && docker-compose up -d postgres
