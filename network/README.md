# General Network tools

## TCP Server/Client

### Socket

The `java.net` package provides two classes--`Socket` and `ServerSocket`--that
implement the client side of the connection and the server side of the
connection, respectively.

One socket is related to a hostname and a port number. Both server and client
need to a assign a port number to a connection (socket).

### Read/Write with a Socket

#### Write a single-link Client/Server

For the client, you need to create a `Socket` that connects the server with a
hostname and port number.

For the Server you need to create a `SocketServer` first, which makes the server
listen on a specific port number. Then you can wait for a connection from some
client. Here, you can use `accept` method, which returns a `Socket` connecting
with some client, to get a connection and then start communication.

Here, on the server side, you need to write thread program in order to process
different connections from clients.
