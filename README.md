# netty-restful-server
[![Build Status](https://travis-ci.org/zhoumengkang/netty-restful-server.svg?branch=master)](https://travis-ci.org/zhoumengkang/netty-restful-server)

A light and high performance restful server built on netty. If you are preparing for leaning java or netty, it's a nice gift for you.

### Preview

project demo can be visited here:

1 . get user info 

> GET   [http://netty.restful.api.mengkang.net/user/1](http://netty.restful.api.mengkang.net/user/1)

2 . get user album info 

> GET   [http://netty.restful.api.mengkang.net/user/1/album/10?build=103](http://netty.restful.api.mengkang.net/user/1/album/10?build=103)

### Version 1.1.0 (2016.01.07)

In this version,there is no database, so you can run it directly.

At first, java 8 should be supported in your server.

There's two way to run it.

1 . Run `net.mengkang.demo.ServerLauncher` in Intellij IDEA, Then you can visit 

> [http://localhost:8080/user/1](http://localhost:8080/user/1) 

> [http://localhost:8080/user/1/album/10?build=103](http://localhost:8080/user/1/album/10?build=103)

in your browser for testing.

2 . Anther way is using java command line. 

> Download the 
> [netty-restful-server-1.1.0.zip](https://github.com/zhoumengkang/netty-light-api-server/releases/download/1.1.0/netty-light-api-server-1.1.0.zip) 
> and unzip it , then run it like this:

```sh
java -Dfile.encoding=UTF-8 -jar netty-restful-server-1.0-SNAPSHOT.jar
```