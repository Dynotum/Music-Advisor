# Music-Advisor

# Stage 3/5: Useful connections 

Description

As you can see from the previous stage, we need to find a way to get a response code from the URL in the user's browser. By default, Java doesn't have browser capabilities but has opportunities to create your own simple HTTP server.

## What is HTTP

HTTP means Hypertext Transfer Protocol, which is a stateless data transfer protocol based on client-server technology, where messaging occurs according to the request-response scheme. The main manipulation object is the resource indexed by the URI. Every time you need to make an HTTP request, follow this structure:

A request line:

    method_name URI HTTP/version

Example:

    GET /some/uri HTTP/1.1

There are a lot of HTTP methods, but in this project, we will consider two of them:

GET requests a representation of the specified resource. They only retrieve data and have no other effect.

The POST method requests that the server accepts the entity enclosed in the request as a new subordinate of the web resource identified by the URI.

The response looks like this:
A response line:

    HTTP/version status_code message

Example:

    HTTP/1.1 200 OK

##Creating a server in java

com.sun.net.httpserver package included in Java SE contains classes. The main class is HttpServer. This class implements a simple HTTP server. To create an instance of this class, you need to use static factory method "create" and bind it to IP and port.

    HttpServer server = HttpServer.create();
    server.bind(new InetSocketAddress(8080), 0);

These lines will create an http server that will listen for incoming TCP connections from clients on 8080 port. Another main concept is context. When an HTTP request is received, the appropriate HttpContext (and handler) is located by finding the context whose path is the longest matching prefix of the request URI's path. To create the context, you should use the method createContext and pass a string of URI path and handler that implements the HttpHandler interface.

    server.createContext("/",
        new HttpHandler() {    
            public void handle(HttpExchange exchange) throws IOException {
                String hello = "hello, world";
                exchange.sendResponseHeaders(200, hello.length());
                exchange.getResponseBody().write(hello.getBytes());
                exchange.getResponseBody().close();
            }
        }
    );

The lines above will create a context to which all requests will be redirected by the server, and the context handler will always return "hello world".

To start the server, add the line server.start(); use the command server.stop(1) to shut down the server. 1 here is the maximum delay in seconds to wait until all handlers have finished.

If you try to run it, you can open your browser at localhost:8080, and you will see this message.

In this stage, you will receive a query parameter with the authorization code from the Spotify page. It looks like http:localhost:8080?code=123. To get the query inside the HttpExchange handler, you can use the following line:

    String query = exchange.getRequestURI().getQuery();

##Making HTTP requests in Java

JDK 11 provides a few classes in the java.net.http package to make HTTP requests: read more about them at openjdk.java.net. First, you should create an HttpClient instance:

    HttpClient client = HttpClient.newBuilder().build();

Then you should setup the http request by creating an HttpRequest instance. It supports the Builder pattern, so you should just call HttpRequest.newBuilder(), then add some methods to setup your request and then call a build() method to create it. Here is an example how to create a simple GET request:

    HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:8080"))
            .GET()
            .build();

To send the request, use the client instance:

    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
     
    System.out.println(response.body());

This code will print "hello, world" if it connects to the server from the section above.

In this stage, you will create a POST request to get the Spotify access token. The main difference between GET and POST requests is that POST may contain a body with some data. It may be a file, json, xml, or other format. You should set the type of data with "Content-type" header. This example shows how to send a POST request with data in the format application/x-www-form-urlencoded:

    HttpRequest request = HttpRequest.newBuilder()
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .uri(URI.create("http://localhost:8080"))
                    .POST(HttpRequest.BodyPublishers.ofString("login=admin&password=admin"))
                    .build();

You should put the body data inside the POST() method using the HttpRequest.BodyPublishers.ofString method. x-www-form-urlencoded data consists of key=value pairs, separated by the & symbol.

Similarly, you can send json data by setting "Content-type" as application/json and passing json inside the ofString() method.

Useful hint: you can use reqbin to test POST and GET requests to API without Java.
## Objectives

Using the Spotify authorization guide and the information given here (you need the section Authorization Code Flow), improve your program by adding real authorization on Spotify.

    1. Choose any free port on your machine (for example, 8080), and add the http://localhost:your_port to the whitelist of redirect_uri in your application settings on the Spotify site (Dashboard -> your app -> edit settings -> redirect URIs).
    Note that you should use the http protocol for localhost, not https, like in the Spotify example.)
    2. On the auth command, before printing the auth link (from the previous stage), you should start an HTTP server that will listen for the incoming requests. When the user confirms or rejects the authorization, the server should return the following text to the browser:
        "Got the code. Return back to your program." if the query contains the authorization code.
        "Authorization code not found. Try again." otherwise.
    This code is bound to each user who has a Spotify account and uses your app. Actually, you should ask this code once for each new user and save it somewhere.
    3. After the code is received, the server must shut down and you should get access_token by making a POST request on https://accounts.spotify.com/api/token with parameters described in the guide, and then print the response body.

Also, in this stage, you should read the Spotify access server point from the command line argument. Server path should be passed to the program using -access argument. If this argument is not set, you should use a default argument, https://accounts.spotify.com. Make sure you replace constants to this argument value everywhere!
## Output example

Below is an output example of the described program. Try to output all cases like in the example.

The symbol > represents the user input. Note that it's not part of the input.

    > new
    Please, provide access for application.
    > auth
    use this link to request the access code:
    https://accounts.spotify.com/authorize?client_id=a19ee7dbfda443b2a8150c9101bfd645&redirect_uri=http://localhost:8080&response_type=code
    waiting for code...
    code received
    making http request for access_token...
    response:
    {"access_token":"BQBSZ0CA3KR0cf0LxmiNK_E87ZqnkJKDD89VOWAZ9f0QXJcsCiHtl5Om-EVhkIfwt1AZs5WeXgfEF69e4JxL3YX6IIW9zl9WegTmgLkb4xLXWwhryty488CLoL2SM9VIY6HaHgxYxdmRFGWSzrgH3dEqcvPoLpd26D8Y","token_type":"Bearer","expires_in":3600,"refresh_token":"AQCSmdQsvsvpneadsdq1brfKlbEWleTE3nprDwPbZgNSge5dVe_svYBG-RG-_PxIGxVvA7gSnehFJjDRAczLDbbdWPjW1yUq2gtKbbNrCQVAH5ZBtY8wAYskmOIW7zn3IEiBzg","scope":""}
    ---SUCCESS---
    > new
    ---NEW RELEASES---
    Mountains [Sia, Diplo, Labrinth]
    Runaway [Lil Peep]
    The Greatest Show [Panic! At The Disco]
    All Out Life [Slipknot]
    > exit
    ---GOODBYE!---

# Stage 2/5: Rocking OAuth 

Description

You have written a simulation of the final application, and now we will make it fully functional, step by step. In this stage, we will introduce OAuth basics and register your application on the Spotify site.
Working with OAuth

OAuth means a protocol for authorization. When you are visiting a website that allows you to register, you often have the option of signing in with Google, Twitter, Facebook, or another service. All these services use OAuth to provide user information to third-party applications.

The main entity of OAuth is access_token, the secret code that should be sent with an HTTP request to API, so that the service is sure that you have enough rights to get information from API.

So, before an application can receive access_token, the user should confirm access to that application. Let’s see how it works:

    A developer of an application wants to use an API (Spotify in our case), so they must go to the site of the corresponding API and create the application there to get client_id and client_secret.
    After they have registered the application and received the client_id and client_secret, they must create an authorization link which will contain, in query parameters, the client_id, redirect_uri (where the user will be sent after confirm; redirect_uri itself must be allowed in the settings of the application), response_type (what should be returned in case of success), and scopes (what rights the user must provide to their account; in our case, we won’t use scopes).
    The user follows this link and authorizes access (clicks the “allow” button), and is redirected back to the developer’s specified redirect_uri with the response here.
    The developer uses this response to get access_token.

We encourage you to do some independent research to get more information about OAuth!

In our case, using the Spotify API, it will look like this:

    https://accounts.spotify.com/authorize?client_id=YOURCLIENT&redirect_uri=https://www.example.com&response_type=code

1. The user follows this link and confirms access.
2. Then the user is redirected to redirect_url with the response in the URL (http://www.example.com?code=7angkqw2DAsdfkQ). (Your own server waiting for code.)
3. You use this code to get access_token and make requests to an API.

You can find more information in the authorization guide (look at Authorization Code Flow section).
Objectives

So, in this stage, you must go to the Spotify Web site for developers and create your application.
To create an application, you should select Dashboard tab on the site, log in to Spotify, and click the button Create a client ID.

Add one more command to the app: an auth command that will print the auth link and allow us to use another command (no real authorization required, just print a workable link and make some Boolean field true). Don't forget to put your client id in this link.

Make commands unavailable if user access for the application is not confirmed (if they did not call the auth command).

Remember that you should add your redirect_uri in the settings of your application.
Output example

Below is an output example of the described program. Try to output all cases like in the example.

The symbol > represents the user input. Note that it's not part of the input.

> new
Please, provide access for application.
> featured
Please, provide access for application.
> auth
https://accounts.spotify.com/authorize?client_id=a19ee7dbfda443b2a8150c9101bfd645&redirect_uri=http://localhost:8080&response_type=code
---SUCCESS---
> new
---NEW RELEASES---
Mountains [Sia, Diplo, Labrinth]
Runaway [Lil Peep]
The Greatest Show [Panic! At The Disco]
All Out Life [Slipknot]
> exit
---GOODBYE!---

# Stage #1: Strings and fiddlesticks

Description

When developing your web applications, it can be useful to implement the ability to register users using third-party services (such as Twitter, Facebook, Google, etc.), as well as the ability to interact with these services (for example, use Twitter to publish a post, or getting a list of friends on Facebook). To implement this functionality, you need to learn how to work with the service's API. Most services use REST (Representational State Transfer) to provide the ability to interact with the service. To learn how to work with it from Java, we will develop a simple application that will offer us music using Spotify.

First, in this stage, you have to implement simple functionality to read user input and provide information at the user's request.

featured — a list of Spotify-featured playlists with their links fetched from API;
new — a list of new albums with artists and links on Spotify;
categories — a list of all available categories on Spotify (just their names);
playlists C_NAME, where C_NAME is the name of category. The list contains playlists of this category and their links on Spotify;
exit shuts down the application.
Notice that here, you don’t need to implement interaction with the API (you will do that later), just replace the API responses with simple strings.

Output example

Below is an output example of the described program. Try to output all cases like in the example.

The symbol > represents the user input. Note that it's not part of the input.

> new
---NEW RELEASES---
Mountains [Sia, Diplo, Labrinth]
Runaway [Lil Peep]
The Greatest Show [Panic! At The Disco]
All Out Life [Slipknot]
> featured
---FEATURED---
Mellow Morning
Wake Up and Smell the Coffee
Monday Motivation
Songs to Sing in the Shower
> categories
---CATEGORIES---
Top Lists
Pop
Mood
Latin
> playlists Mood
---MOOD PLAYLISTS---
Walk Like A Badass  
Rage Beats  
Arab Mood Booster  
Sunday Stroll
> exit
---GOODBYE!---
