package common

import game.GodController.MsgChooseHero
import game.SessionController.Session
import game.UserModel.{Stats, User}
import server.Server.{Config, RequestChangePassword, RequestChangeProfile, RequestForgetPassword, RequestGame, RequestLogin, RequestMatch, RequestRegister, RequestSetNewPassword}
import spray.json.DefaultJsonProtocol

/**
  * json formatter
  */
trait MyJsonProtocol extends DefaultJsonProtocol {
  implicit val ConfigFormat = jsonFormat9(Config)
  implicit val StatsFormat = jsonFormat1(Stats.apply)
  implicit val UserFormat = jsonFormat6(User.apply)
  implicit val RequestLoginFormat = jsonFormat2(RequestLogin)
  implicit val RequestRegisterFormat = jsonFormat5(RequestRegister)
  implicit val RequestForgetPasswordFormat = jsonFormat1(RequestForgetPassword)
  implicit val RequestSetNewFormat = jsonFormat2(RequestSetNewPassword)
  implicit val RequestChangePasswordFormat = jsonFormat3(RequestChangePassword)
  implicit val RequestChangeProfileFormat = jsonFormat4(RequestChangeProfile)
  implicit val RequestMatchFormat = jsonFormat1(RequestMatch)
  implicit val SessionFormat = jsonFormat3(Session)
  implicit val MsgChooseHeroFormat = jsonFormat2(MsgChooseHero)
  implicit val RequestGameFormat = jsonFormat2(RequestGame)
}