Feature: Proposal creation feature

  In order to get a proposal being published
  As an person who participate on the open space
  I want to send a new proposal

  Background:

  At this point, the users in the application does not need to be authenticated in the app
  in order to make a proposal.

  They just type the basic information and a new Task will be published.



  Scenario: Send a proposal
    When a "POST" request for the "/proposals" endpoint is created
      And the request has the json payload:
      """
      {
        "speakers": "Ana Smith and Tobias Walter",
        "title":    "A title"
      }
      """
      And the request is sent
    Then the response will have the "200" status code
      And  the response will have the "application/json" content type
      And  the response will return a proposal in its body with this information:
      """
      {
        "title":    "A title",
        "speakers": "Ana Smith and Tobias Walter",
        "visible":  false,
        "votes":    0,
        "id":       "generated id"
      }
      """