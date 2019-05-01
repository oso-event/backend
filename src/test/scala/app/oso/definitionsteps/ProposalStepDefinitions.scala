package app.oso.definitionsteps

import java.util
import app.oso.proposal.application.ProposalService
import app.oso.proposal.domain.Proposal
import app.oso.proposal.infrastructure.{InMemoryProposalRepository, ProposalController}
import cats.effect.IO
import com.sun.tools.javac.util.Name.Table
import cucumber.api.PendingException
import org.http4s.dsl.io._
import io.circe.syntax._
import io.circe.generic.auto._
import org.http4s.circe._
import io.circe.parser.decode
import org.http4s.circe.CirceEntityDecoder._
import cucumber.api.scala.{EN, ScalaDsl}
import io.cucumber.datatable.DataTable
import org.http4s.{HttpService, MediaType, Method, Request, Response, Status, Uri, headers}
import org.scalatest.Matchers


class ProposalStepDefinitions extends ScalaDsl with EN with Matchers {

  var request:Request[IO] = _
  var response:Response[IO] = _
  var method:Method  = _
  var endpoint:Uri   = _
  var payload:String = _
  var proposals:util.List[Proposal] = _

  def getController: HttpService[IO] = {
    val service    = new ProposalService(new InMemoryProposalRepository())
    new ProposalController[IO](service).controller
  }


  Given("""the following proposals were sent to the system:"""){ data:util.List[Proposal] =>
    proposals = data
    proposals
  }

  When("""a {string} request for the {string} endpoint is created"""){ (_method:String, _endpoint:String) =>{
    endpoint = Uri.fromString(s"${_endpoint}").right.get
    method   = _method match {
      case "POST" => Method.POST
      case "PATCH" => Method.PATCH
    }
  }}

  When("""the request has the json payload:"""){ _payload:String => payload = _payload }

  When("""the request is sent"""){ () => {
    request  = Request[IO](method, endpoint).withBody(payload).unsafeRunSync()
    response = getController.orNotFound(request).unsafeRunSync()
  }}

  Then("""the response will have the {string} status code"""){ statusCode:String => {
    statusCode match {
      case "200" => response.status should be(Status.Ok)
    }
  }}
  Then("""the response will have the {string} content type"""){ contentType:String => {
    contentType match {
      case "application/json" => response.contentType.get should be( headers.`Content-Type`(MediaType.`application/json`, None) )
    }
  }}
  Then("""the response will return a proposal in its body with this information:"""){ body:String => {
    val desiredProposal:Proposal  = decode[Proposal](body).right.get
    val returnedProposal:Proposal = response.as[Proposal].unsafeRunSync()

    returnedProposal.title should be(desiredProposal.title)
    returnedProposal.speakers should be(desiredProposal.speakers)
    returnedProposal.visible should be (desiredProposal.visible)
    returnedProposal.votes should be (desiredProposal.votes)
  }}

}
