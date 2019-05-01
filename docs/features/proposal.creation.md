# Proposal creation feature

In order to get a proposal being published
  As a person who participate on the open space
  I want to send a new proposal

## Background ()

At this point, the users in the application does not need to be authenticated in the app
  in order to make a proposal.

  They just type the basic information and a new Task will be published.

## Send a proposal

_When_ a "POST" request for the "/proposals" endpoint is created

_And_ the request has the json payload:

```
{
  "speakers": "Ana Smith and Tobias Walter",
  "title":    "A title"
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
  "visible":  false,
  "votes":    0,
  "id":       "generated id"
}
```

