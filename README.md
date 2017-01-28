# google-map-api
RESTful API server to look up address by given coordinates
### Description
### API
```
GET /geocode
   URL Params:
      Required:
         latlng=string coordinates
         key=string    Google API Key
   Response:
      Code 200
      Code 400 InvalidInputException
      Code 500 INTERNAL_SERVER_ERROR

GET /geocode/history
    Response:
      Code 200
      Code 500 INTERNAL_SERVER_ERROR
```
### Demo
##### Checkout the repo
```
$ git clone https://github.com/simagix/google-map-api.git
```
##### Run the app
```
$ cd google-map-api
$ ./gradlew bootRun
```
##### Give it a spin
```
$ curl -XGET -v http://localhost:8080/geocode?latlng=33.969601,-84.100033\&key=<your API key>
$ curl -XGET -v http://localhost:8080/geocode?latlng=33.979601,-84.100033\&key=<your API key>
$ curl -XGET -v http://localhost:8080/geocode?latlng=33.989601,-84.100033\&key=<your API key>
```
##### Print cached history
```
$ curl -XGET -v http://localhost:8080/geocode/history
```
##### Exceptions
```
$ curl -XGET -v http://localhost:8080/geocode?latlng=133.969601,-84.100033\&key=<your API key>
```
##### Docker
```
$ docker run -d -p 8080:8080 simagix/google-map-api
```
