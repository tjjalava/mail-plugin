package play.modules.mail


import play.api._
import org.codemonkey.simplejavamail.Mailer
import play.modules.mail.MailWorker.Start
import play.api.Play.current

/**
 * User: alabbe
 * Date: 01/03/12
 * Time: 17:19
 */

class MailPlugin(app:Application) extends Plugin {
  lazy val helper = MailHelper(
    host = app.configuration.getString("smtp.host") getOrElse MailPlugin.DEFAULT_HOST,
    port = app.configuration.getInt("smtp.port") getOrElse MailPlugin.DEFAULT_PORT,
    username = app.configuration.getString("smtp.username") getOrElse "",
    password = app.configuration.getString("smtp.password") getOrElse ""
  )
}

object MailPlugin {
   private[mail] val DEFAULT_HOST = "localhost"
   private[mail] val DEFAULT_PORT = 25

  lazy val worker = {
    Logger.debug("Mail plugin starting...")
    MailWorker.ref ! Start(helper.mailer)
    Logger.info("Mail plugin successfully started with smtp server on %s:%s".format(helper.host, helper.port))
    MailWorker.ref
  }

   private def helper(implicit app:Application):MailHelper = app.plugin[MailPlugin] match {
      case Some(plugin) => plugin.helper
      case _ => throw PlayException("MailPugin Error",
        "The MailPlugin is not initialized, Please edit your conf/play.plugins file and add the following line: " +
          "'400:play.modules.mailb.MailPlugin' (400 is an arbitrary priority and may be changed to match your needs).")
   }
}

private[mail] case class MailHelper(host: String = MailPlugin.DEFAULT_HOST, port: Int = MailPlugin.DEFAULT_PORT,
                                    username: String = "", password: String = "") {
   def mailer = new Mailer(host, port, username, password)
}


