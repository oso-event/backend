package app.oso.votation.application

import app.oso.proposal.domain.Proposal
import app.oso.votation.domain.Votation

class VotationService {

  def vote(userIdentifier:String, proposal: Proposal):Votation = Votation(userIdentifier, proposal)

  def unVote(userIdentifier:String, proposal: Proposal):Unit = ()

}
