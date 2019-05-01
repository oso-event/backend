# Votation feature

In order to get a talk
  As a person who participate on the open space
  I want to vote a proposal

## Background ()

At this point, the users in the application does not need to be authenticated in the app
  in order to manage proposals.

  They just type the information and the votation will be taking in account.
  The authentication is made on the client side.

_Given_ the following proposals were sent to the system:

| identifier | title   | speakers                    | visible | votes |
|------------|---------|-----------------------------|---------|-------|
| 1          | A title | Ana Smith and Tobias Walter | false   | 2     |

_And_ the following votations are registered:

| userIdentifier | proposalIdentifier |
|----------------|--------------------|
| anna           | 1                  |
| john           | 1                  |

## Vote a proposal

_When_ a "POST" request for the "/votation" endpoint is created

_And_ the request has the json payload:

```
{
  "userIdentifier":        "kevin",
  "proposalIdentifier":    "1"
}
```

_And_ the request is sent

_Then_ the response will have the "200" status code

_And_ the response will have the "application/json" content type

_And_ the response will return a votation in its body with this information:

```
{
  "userIdentifier": "kevin",
  "proposal": {
    "title":    "A title",
    "speakers": "Ana Smith and Tobias Walter",
    "visible":  false,
    "votes":    0,
    "id":       "1"
  }
}
```

## Unvote a proposal

_When_ a "DELETE" request for the "/votation/kevin-1" endpoint is created

_And_ the request is sent

_Then_ the response will have the "204" status code.
on/kevin-1" endpoint is created

_And_ the request is sent

_Then_ the response will have the "204" status code.


_And_ the request is sent

_Then_ the response will have the "204" status code.
