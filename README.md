# Music-Advisor

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
