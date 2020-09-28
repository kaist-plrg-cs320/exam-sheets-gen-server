package cs320

import scala.io.Source
import scala.collection.immutable.ArraySeq

import unfiltered.jetty.{Server => JServer}
import unfiltered.filter.Plan
import unfiltered.request._
import unfiltered.response._
import unfiltered.directives._, Directives._

object Main {

  val year = 2020
  val semester = "Fall"
  val qnum = 9

  def main(args: Array[String]): Unit = run(
    args match {
      case Array() => 8080
      case Array(port) => port.toInt
      case _ => sys.error("too many arguments")
    }
  )

  def run(port: Int): Unit = {
    val secret = Source.fromFile("secret").mkString.filter(_.isLetterOrDigit)
    JServer.http(port).plan(new ServerPlan(secret)).run(
      _ => println(s"Running on http://localhost:$port/")
    )
  }

  def getSheet(id: String, aes: AES256): Array[Byte] = {
    val sheet = new ExamSheet(id, aes)
    for (i <- 1 to qnum)
      sheet.addPage(i, year, semester, "Midterm")
    for (i <- 1 to qnum)
      sheet.addPage(i, year, semester, "Final")
    val arr = sheet.toByteArray
    sheet.close()
    arr
  }

  class ServerPlan(secret: String) extends Plan {
    val aes = new AES256(secret)

    def intent = Directive.Intent {
      case r @ GET(Path("/") & Params(IdParam(id))) =>
        success(
          ResponseHeader("Content-Disposition", List(s"""inline; filename="${id}.pdf"""")) ~>
          ResponseHeader("Content-Type", List("application/pdf")) ~>
          ResponseBytes(getSheet(id, aes))
        )
    }
  }

  object IdParam {
    def unapply(map: Map[String, ArraySeq[String]]): Option[String] =
      map
        .get("id")
        .flatMap(_.headOption)
        .filter(s => s.length == 8 && s.forall(_.isDigit))
  }
}
