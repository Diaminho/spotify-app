# spotify-app

Rest service to get playlist from Spotify.

Also service can map spotify playlist to Yandex Music import page string playlist.

## Getting Started
____

Project needed to be imported as maven project

### Building
___

To build runnable jar:

```sh
$ ./mvnw clean package
```
### Run application
___
To run application:
```sh
$ ./mvnw clean spring-boot:run -f pom.xml
```


### Request examples
____

There are example requests

- **Receive all songs from playlist**

    ```
    $ curl -X GET http://localhost:8080/spotify/export/{playlistId}
    ```
    where `playlistId` - spotify identifier of playlist.
  
    Result is json array of simplified song representation:
  
    ```json
    [
      {
          "author": "Example Author, Another Example Author",
          "name": "Example Song Name"
      }
    ]
    ```

- **Receive all songs from playlist as yandex import string**

    ```
    $ curl -X GET http://localhost:8080/spotify/export-as-yandex-string/{playlistId}
    ```
    where `playlistId` - spotify identifier of playlist.

    Result

    ```
    Example Author - Example Song
    Example Another Author - Example Another Song
    ```

### Testing
___
To run tests:
```sh
$ ./mvnw clean test
```