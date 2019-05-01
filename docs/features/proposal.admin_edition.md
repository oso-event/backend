# Proposal admin edition feature

In order to review and publish a proposal
  As an organizer of the open space
  I want to edit a proposal

## Background ()

At this point, the users in the application does not need to be authenticated in the app
  in order to manage proposals.

  They just type the information and a new Task will be updated.
  The authentication is made on the client side.
  
_Given_ the following proposals were sent to the system:
  
| identifier | title    | speakers                    | visible | votes |
|------------|----------|-----------------------------|---------|-------|
| 1          | A title  | Ana Smith and Tobias Walter | false   | 0     |

## Edit a proposal data

_When_ a "PATCH" request for the "/proposals/1" endpoint is created

_And_ the request has the json payload:

```
{
  "speakers": "Ana Smith",
  "title":    "A second title"
}
```

_And_ the request is sent

_Then_ the response will have the "200" status code

_And_ the response will have the "application/json" content type

_And_ the response will return a proposal in its body with this information:

```
{
  "title":    "A second title",
  "speakers": "Ana Smith",
  "visible":  false,
  "votes":    0,
  "id":       "1"
}
```

## Make a proposal visible

_When_ a "PATCH" request for the "/proposals/1" endpoint is created

_And_ the request has the json payload:

```
{
  "visible": true
}
```

_And_ the request is sent

_Then_ the response will have the "200" status code

_And_ the response will have the "application/json" content type

_And_ the response will return a proposal in its body with this information:

```
{
  "title":    "A title",
  "speakers": "Ana Smith and Tobias Walter",
  "visible":  true,
  "votes":    0,
  "id":       "1"
}
```
