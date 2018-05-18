package game

import common.Mail
import common.MyUtils._
import game.SessionController.{createSession, map}
import server.Server.{RequestChangePassword, RequestChangeProfile, RequestForgetPassword, RequestSetNewPassword, config}

object UserController {
  /**
    * forget password
    */
  def forget(req:RequestForgetPassword) : Option[Unit] = {
    val uid = req.email
    UserModel.checkUser(uid).flatMap { _ =>
      val sid = createSession(uid)
      val title = "[Krad] 重置密码"
      val text = s"请点击以下链接以重置密码： ${config.web_url}/session/reset.html?sid=$sid"
      Some(Mail.send(req.email, title, text))
    }.succ()
  }

  /**
    * set new password
    */
  def setNewPassword(req:RequestSetNewPassword) : Option[Unit] = {
    map.getB(req.sid).flatMap { uid =>
      UserModel.setNewPassword(uid, req.new_password)
    }
  }

  /**
    * change password
    */
  def changePassword(req:RequestChangePassword) : Option[Unit] = {
    map.getB(req.sid).flatMap { uid =>
      UserModel.changePassword(uid,req.old_password,req.new_password)
    }
  }

  /**
    * change profile
    */
  def changeProfile(req:RequestChangeProfile) : Option[Unit] = {
    map.getB(req.sid).flatMap { uid =>
      UserModel.changeProfile(uid,req.nickname,req.avatar,req.gender)
    }
  }
}