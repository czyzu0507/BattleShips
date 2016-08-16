# BattleShips game

Battleships game with GUI. It has a server and client. 
Server is capable of running multiple games.

##Contributors

Damian,
Kamil,
Maciek,
Tomek

####Master branch build status:
[![Build Status](https://travis-ci.org/clinton56/BattleShips.svg?branch=master)](https://travis-ci.org/clinton56/BattleShips)

### Usage (from command line)

Run in root directory: `mvn clean compile assembly:single install`

Next: `java -jar ${module}-0.9-jar-with-dependencies.jar`
- where `${module}` is either client or server

By default game starts on localhost, but you can play over the network.

Client has additionally one parameter - server ip address.

ex. `java -jar client-0.9-jar-with-dependencies.jar 127.0.0.1`

