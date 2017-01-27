# google-map-api
RESTful API server to look up address by given coordinates
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
$ curl -XGET -v http://localhost:8080/geocode?latlng=33.969601,-84.100033&key=<your API key>
$ curl -XGET -v http://localhost:8080/geocode?latlng=33.979601,-84.100033&key=<your API key>
$ curl -XGET -v http://localhost:8080/geocode?latlng=33.989601,-84.100033&key=<your API key>
```
##### Print cached history
```
$ curl -XGET -v http://localhost:8080/geocode/history?key=<your API key>
```
##### Exceptions
```
$ curl -XGET -v http://localhost:8080/geocode?latlng=133.969601,-84.100033&key=<your API key>
```
