package app.oso.votation.infrastructure.framework
import app.oso.votation.infrastructure.VotationDTO

object VotationDTOVal {
  def unapply(str: String): Option[VotationDTO] = str.split("-").toList match {
    case List(userIdentifier:String, proposalIdentifier:String) => Some(VotationDTO(userIdentifier, proposalIdentifier))
    case _ => None
  }
}
