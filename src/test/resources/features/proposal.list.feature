@proposal
Feature: Proposal list feature

  In order to vote the proposals
  As a person who participate on the open space
  I want to view the proposals

  Background:

  At this point, the users in the application does not need to be authenticated in the app
  in order to view the proposals.


  Scenario: Send a proposal
    When a "GET" request for the "/proposals" endpoint is created
      And the request is sent
    Then the response will have the "200" status code
      And  the response will have the "application/json" content type
      And  the response will return a list of proposals:
      """
      [
        {
          "title":    "A title",
          "speakers": "Ana Smith and Tobias Walter",
          "visible":  false,
          "votes":    0,
          "id":       "generated id"
        }
      ]
      """