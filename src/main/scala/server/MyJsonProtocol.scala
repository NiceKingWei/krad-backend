package server

import game.SessionManager.Session
import game.UserDB.{Stats, User}
import server.Server.{Config, RequestChangePassword, RequestChangeProfile, RequestForgetPassword, RequestLogin, RequestRegister, RequestSetNewPassword}
import spray.json.DefaultJsonProtocol

/**
  * json formatter
  */
trait MyJsonProtocol extends DefaultJsonProtocol {
  implicit val ConfigFormat = jsonFormat8(Config)
  implicit val StatsFormat = jsonFormat1(Stats.apply)
  implicit val UserFormat = jsonFormat6(User.apply)
  implicit val RequestLoginFormat = jsonFormat2(RequestLogin)
  implicit val RequestRegisterFormat = jsonFormat5(RequestRegister)
  implicit val RequestForgetPasswordFormat = jsonFormat1(RequestForgetPassword)
  implicit val RequestSetNewFormat = jsonFormat2(RequestSetNewPassword)
  implicit val RequestChangePasswordFormat = jsonFormat3(RequestChangePassword)
  implicit val RequestChangeProfileFormat = jsonFormat4(RequestChangeProfile)
  implicit val SessionFormat = jsonFormat3(Session)
}