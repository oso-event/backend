@votation
Feature: Votation feature

  In order to get a talk
  As a person who participate on the open space
  I want to vote a proposal

  Background:

  At this point, the users in the application does not need to be authenticated in the app
  in order to manage proposals.

  They just type the information and the votation will be taking in account.
  The authentication is made on the client side.

  Given the following proposals were sent to the system:

    | identifier | title    | speakers                    | visible | votes |
    | 1          | A title  | Ana Smith and Tobias Walter | false   | 2     |

  And the following votations are registered:

    | userIdentifier | proposalIdentifier |
    | anna           | 1                  |
    | john           | 1                  |


  Scenario: Vote a proposal
    When a "POST" request for the "/votation" endpoint is created
      And the request has the json payload:
      """
      {
        "userIdentifier":        "kevin",
        "proposalIdentifier":    "1"
      }
      """
      And the request is sent
    Then the response will have the "200" status code
      And  the response will have the "application/json" content type
      And  the response will return a votation in its body with this information:
      """
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
      """

  Scenario: Unvote a proposal
    When a "DELETE" request for the "/votation/kevin-1" endpoint is created
    And  the request is sent
    Then the response will have the "204" status code