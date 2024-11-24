# ChessPlatform-web-coursework

# DDD

```cmd
com.chessplatform
├── application
│   └── service
│       ├── PlayerService.java
│       ├── GameService.java
│       └── TournamentService.java
├── domain
│   ├── model
│   │   ├── aggregate
│   │   │   └── Tournament.java
│   │   |   └── LeaderboardEntry.java
│   │   ├── entity
|   |   |   └── Player.java
|   |   |   └── Game.java
│   │   │   └── Move.java
│   │   └── valueobject
|   |       └── Figure.java
│   ├── repository
│   │   ├── PlayerRepository.java
│   │   ├── GameRepository.java
│   │   ├── TournamentRepository.java
│   │   └── MoveRepository.java
│   └── service
│       └── RatingService.java
├── infrastructure
│   ├── persistence
│   │   ├── hibernate
│   │   │   ├── PlayerRepositoryImpl.java
│   │   │   ├── GameRepositoryImpl.java
│   │   │   ├── TournamentRepositoryImpl.java
│   │   │   └── MoveRepositoryImpl.java
│   │   └── config
│   │       └── PersistenceConfig.java
├── ui
│   └── controller
│       ├── PlayerController.java
│       ├── GameController.java
│       └── TournamentController.java
└── util
    └── exception
        └── PlayerNotFoundException.java

```

Я вынес таблицу Figure в value-object, так как она не является самостоятельной сущностью