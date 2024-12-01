package ru.chessplatform;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}
/*
docker run --name chess_db_container \
    -e POSTGRES_DB=chess_db \
    -e POSTGRES_USER=superboy \
    -e POSTGRES_PASSWORD=superboy \
    -p 5432:5432 \
    -d postgres

psql -h localhost -U superboy -d chess_db
 */