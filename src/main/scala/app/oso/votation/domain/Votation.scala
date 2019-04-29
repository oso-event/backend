package app.oso.votation.domain

import app.oso.proposal.domain.Proposal

case class Votation(userIdentifier:String, proposal: Proposal)
