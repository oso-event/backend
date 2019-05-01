package app.oso.proposal.infrastructure.dto

case class ProposalAdminDTO(
  speakers:Option[String] = None,
  title:Option[String]    = None,
  visible:Option[Boolean] = None
)
