package game

import common.MyJsonProtocol
import server.Server
import server.Server.RequestGame

import scala.concurrent.{Await, Future}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration

object ScalaGodTest extends MyJsonProtocol{
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

  def test1(): Unit ={
    MapGenerator.map1()
    val sids = 0 to 1
    val god = new God
    var i = 0
    god.initialPlayer(sids.toArray)
    val fs = for(sid<-sids)
      yield Future{
        for(i<-1 to 20) {
          println(sid, god.request(sid, "{}"))
          println(sid, god.request(sid,
            """{"hero":"hero1"}"""
          ))


          println(sid, god.request(sid, "{}"))
          println(sid, god.request(sid,
            """{"decision":-1,"moveDirection":-1,"fireTarget":-1}"""
          ))

          println(sid, god.request(sid,
            """{"seenCard":3}"""
          ))

          println(sid, god.request(sid,
            """{"gambleCard":[0]}"""
          ))
          // Account
          println(sid, god.request(sid, ""))
          // Account
          println(sid, god.request(sid, ""))
          // cardDesert
          println(sid, god.request(sid,
            """{"desertCardList":[0]}"""
          ))

          println(sid, god.request(sid, "{}"))
          println(sid, god.request(sid,
            """{"decision":-1,"moveDirection":-1,"fireTarget":-1}"""
          ))

          println(sid, god.request(sid,
            """{"seenCard":3}"""
          ))

          println(sid, god.request(sid,
            """{"gambleCard":[0]}"""
          ))
          // Account
          println(sid, god.request(sid, ""))
          // Account
          println(sid, god.request(sid, ""))
          // cardDesert
          println(sid, god.request(sid,
            """{"desertCardList":[0]}"""
          ))

          println(sid, god.request(sid, "{}"))
          println(sid, god.request(sid,
            """{"decision":-1,"moveDirection":-1,"fireTarget":-1}"""
          ))

          println(sid, god.request(sid,
            """{"seenCard":3}"""
          ))

          println(sid, god.request(sid,
            """{"gambleCard":[0]}"""
          ))
          // Account
          println(sid, god.request(sid, ""))
          // Account
          println(sid, god.request(sid, ""))
          // cardDesert
          println(sid, god.request(sid,
            """{"desertCardList":[0]}"""
          ))

          println(sid, god.request(sid, "{}"))
          println(sid, god.request(sid,
            """{"decision":-1,"moveDirection":-1,"fireTarget":-1}"""
          ))

          println(sid, god.request(sid,
            """{"seenCard":3}"""
          ))

          println(sid, god.request(sid,
            """{"gambleCard":[0]}"""
          ))
          // Account
          println(sid, god.request(sid, ""))
          // Account
          println(sid, god.request(sid, ""))
          // cardDesert
          println(sid, god.request(sid,
            """{"desertCardList":[0]}"""
          ))
        }
      }
    for(f<-fs) {
      f onComplete println
      Await.ready(f,Duration.Inf)
    }
  }

  def test2(): Unit ={
    SessionController.addGhost()
    val fs = for(sid<-0 to 1)
      yield
        for(data1<-SessionController.gameRequest(RequestGame(sid,"{}"));
            data2<-SessionController.gameRequest(RequestGame(sid,"{\"hero\":\"hero\"}"));
            data3<-SessionController.gameRequest(RequestGame(sid,"{}"))) yield (data1,data2,data3)
    for(f<-fs){
      f onComplete println
      Await.ready(f,Duration.Inf)
    }
  }

  def test3(): Unit = {
    SessionController.addGhost()
    SessionController.gameRequest(RequestGame(2,"{}"))
    SessionController.gameRequest(RequestGame(1,"{}"))
  }

  def main(args: Array[String]): Unit = {
    test1()
  }
}
