# netty-light-api-server
A light java api server build on netty, if you are preparing for leaning java or netty, it's a nice gift for you.

Version 1.0.0
---
In this version,there is no database, so you can run it directly.

At first, java 8 should be supported in your server.

There's two way to run it.

1. Run `net.mengkang.netty.Server` in Intellij IDEA, Then you can visit http://localhost:8080/?api=user.get&uid=1 in your browser for test.

2. Anther way is use java command line to run it. Download the `netty-light-api-server-1.0.0.zip` and unzip it , then run it like this
```sh
java -Dfile.encoding=UTF-8 -jar netty-light-api-server-1.0-SNAPSHOT.jar
```
