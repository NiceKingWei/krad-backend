package server

import java.io.{File, FileWriter}
import java.util.Date

import scala.io.Source
import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.{Directives, Route}
import akka.stream.ActorMaterializer
import common.MyJsonProtocol
import spray.json._
import game.{SessionController, UserController, UserModel}

import scala.concurrent.ExecutionContextExecutor

/**
  * http server
  */
object Server extends Directives with SprayJsonSupport with MyJsonProtocol {

  // config and log
  final case class Config(db_user:String,
                          db_password:String,
                          db_host:String,
                          web_host:String,
                          web_port:Int,
                          web_url:String,
                          email_host:String,
                          email_username:String,
                          email_password:String)

  val log_file = new FileWriter(new File("log.txt"),true)
  val config:Config = Source.fromFile("config.txt")(io.Codec("UTF-8")).mkString.parseJson.convertTo[Config]

  /**
    * log
    * @param action action
    * @param info information
    */
  def log[A](action:String,info:A): Unit = {
    val date = new Date()
    val s=s"[$action] $info #\t$date\n"
    print(s)
    log_file.write(s)
    log_file.flush()
  }

  // requests
  /*
  var req = {
    email:"affd@asf.com",
    password:"asdfassaf"
  };
  Json.Stringfy(req)
   */
  final case class RequestLogin(email:String,password:String)
  final case class RequestRegister(email:String,
                                   nickname: String,
                                   avatar: String,
                                   gender: String,
                                   password: String)
  final case class RequestForgetPassword(email:String)
  final case class RequestSetNewPassword(sid:Int,new_password:String)
  final case class RequestChangePassword(sid:Int,old_password:String,new_password:String)
  final case class RequestChangeProfile(sid:Int,
                                        nickname: String,
                                        avatar: String,
                                        gender: String)

  // http server
  implicit val system: ActorSystem = ActorSystem("my-system")
  implicit val materializer: ActorMaterializer = ActorMaterializer()

  // needed for the future flatMap/onComplete in the end
  implicit val executionContext: ExecutionContextExecutor = system.dispatcher

  // api
  val route: Route =
    path("hello") {
      get {
        log("get","hello")
        complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "<h1>Say hello to krad api!</h1>"))
      }
    }~
    path("session" / "login") {
      post {
        entity(as[RequestLogin]){req=>
          log("post","session/login")
          SessionController.login(req) match {
            case Some(res) => complete(res)
            case None => complete(HttpResponse(StatusCodes.BadRequest))
          }
        }
      }
    }~
    path("session" / "register"){
      post{
        entity(as[RequestRegister]){req=>
          log("post","session/register")
          SessionController.register(req) match {
            case Some(res) => complete(res)
            case None => complete(HttpResponse(StatusCodes.BadRequest))
          }
        }
      }
    }~
    path("user" / "forget"){
      post{
        entity(as[RequestForgetPassword]){req=>
          log("post","user/forget")
          UserController.forget(req) match {
            case Some(_) => complete(HttpResponse(StatusCodes.Accepted))
            case None => complete(HttpResponse(StatusCodes.BadRequest))
          }
        }
      }
    }~
    path("user" / "setpw"){
      post{
        entity(as[RequestSetNewPassword]){req=>
          log("post","user/setpw")
          UserController.setNewPassword(req) match {
            case Some(_) => complete(HttpResponse(StatusCodes.Accepted))
            case None => complete(HttpResponse(StatusCodes.BadRequest))
          }
        }
      }
    }~
    path("user" / "changepw"){
      post{
        entity(as[RequestChangePassword]){req=>
          log("post","user/changepw")
          UserController.changePassword(req) match {
            case Some(_) => complete(HttpResponse(StatusCodes.Accepted))
            case None => complete(HttpResponse(StatusCodes.BadRequest))
          }
        }
      }
    }~
    path("user"/"changeprofile"){
      post{
        entity(as[RequestChangeProfile]){req=>
          log("post","user/changeprofile")
          UserController.changeProfile(req) match {
            case Some(_) => complete(HttpResponse(StatusCodes.Accepted))
            case None => complete(HttpResponse(StatusCodes.BadRequest))
          }
        }
      }
    }


  // main
  def main(args: Array[String]): Unit = {
    val cmd = if(args.length > 0) args(0) else ""
    cmd match {
      case "migrant" => UserModel.migrant()
      case "connect" => UserModel.connect()
      case _ => {}
    }
    Http().bindAndHandle(route, config.web_host, config.web_port)
    log("bind",s"${config.web_host}:${config.web_port}")
  }
}
