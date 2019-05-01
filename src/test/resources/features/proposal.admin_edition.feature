@proposal
Feature: Proposal admin edition feature

  In order to review and publish a proposal
  As an organizer of the open space
  I want to edit a proposal

  Background:

  At this point, the users in the application does not need to be authenticated in the app
  in order to manage proposals.

  They just type the information and a new Task will be updated.
  The authentication is made on the client side.

  Given the following proposals were sent to the system:

    | identifier | title    | speakers                    | visible | votes |
    | 1          | A title  | Ana Smith and Tobias Walter | false   | 0     |


  Scenario: Edit a proposal data
    When a "PATCH" request for the "/proposals/1" endpoint is created
      And the request has the json payload:
      """
      {
        "speakers": "Ana Smith",
        "title":    "A second title"
      }
      """
      And the request is sent
    Then the response will have the "200" status code
      And  the response will have the "application/json" content type
      And  the response will return a proposal in its body with this information:
      """
      {
        "title":    "A second title",
        "speakers": "Ana Smith",
        "visible":  false,
        "votes":    0,
        "id":       "1"
      }
      """

  Scenario: Make a proposal visible
    When a "PATCH" request for the "/proposals/1" endpoint is created
    And the request has the json payload:
      """
      {
        "visible": true
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
        "visible":  true,
        "votes":    0,
        "id":       "1"
      }
      """