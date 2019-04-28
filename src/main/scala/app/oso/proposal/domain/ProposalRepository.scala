package app.oso.proposal.domain

trait ProposalRepository {
  def findAll:List[Proposal]
  def create(proposal: Proposal):Proposal
}
