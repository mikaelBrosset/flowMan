package com.mik.flowman

import akka.actor.ActorSystem
import cats.effect._
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{HttpRequest, HttpResponse}
import akka.stream.ActorMaterializer
import com.typesafe.config.{Config, ConfigFactory}

import scala.concurrent._

object Main extends App {
  def run(implicit
            ec: ExecutionContext,
            cs: ContextShift[IO])
  : Unit = {

    for {
      config <- IO.delay(ConfigFactory.load())
      rowFlow <- download(config, ec, cs)
    } yield()

  }

  def download(config: Config, ec: ExecutionContext, cs: ContextShift[IO]): IO[String] = {

    implicit val cs2 = cs
    implicit val system = ActorSystem()
    implicit val materializer = ActorMaterializer()

    val ioResp = IO.fromFuture(IO(Http().singleRequest(HttpRequest(uri = config.getString("flow.url")))))
    ioResp.map(resp => resp.entity.toString)
  }
}
