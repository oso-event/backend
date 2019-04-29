package app.oso.proposal.infrastructure

import app.oso.proposal.domain.{Proposal, ProposalRepository}

class InMemoryProposalRepository extends ProposalRepository{
  override def findAll: List[Proposal] = List(
    Proposal(
      id = 1,
      title = "Cómo hemos hecho OSO",
      speakers = "Fran, Asier, Juan Miguel",
      visible = true,
      votes = 0)
  )

  override def find(id: Int): Proposal = findAll.head

  override def create(proposal: Proposal): Proposal = proposal
}
