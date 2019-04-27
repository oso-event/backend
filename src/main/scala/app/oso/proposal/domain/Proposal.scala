package app.oso.proposal.domain

case class Proposal (
  id: Int,
  title: String,
  speakers: String,
  votes: Int,
  visible: Boolean
)