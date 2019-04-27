package app.oso


import cats.effect.IO
import org.http4s.util.StreamApp

object Server extends StreamApp[IO] {
  import scala.concurrent.ExecutionContext.Implicits.global

  def stream(args: List[String], requestShutdown: IO[Unit]) = ServerStream.stream[IO]
}

