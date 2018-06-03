package game

import java.security.cert.PKIXRevocationChecker

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

object ScalaGodTest {
  def testSample(): Unit = {
    val god = new God
    val sid = Array(1, 2, 3, 4)
    god.initialPlayer(sid)
    for (s <- sid) {
      println(s)
      Future {
        god.request(s, s"Hero${4 - s}")
      } onComplete { x =>
        println(x)
      }
      Thread.sleep(100)
    }
  }
  

  def exper(): Unit ={
    val f1 = Future {
      Thread.sleep(1000)
      1
    }
    println("Hello world")
    val f2 = f1.map { x =>
      println("f2:", x)
      x + 1
    }
    val f3 = f2.flatMap(x => Future {
      Thread.sleep(1000)
      println("complete")
      x * 10
    }
    )
    f3.onComplete(x =>
      println("done")
    )
    Thread.sleep(8000)
  }

  def main(args: Array[String]): Unit = {
  }
}
