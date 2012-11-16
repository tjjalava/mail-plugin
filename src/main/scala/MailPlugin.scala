package play.modules.mail


import play.api._
import org.codemonkey.simplejavamail.Mailer

/**
 * User: alabbe
 * Date: 01/03/12
 * Time: 17:19
 */

class MailPlugin(app:Application) extends Plugin {
  private[mail] val DEFAULT_HOST = "localhost"
  private[mail] val DEFAULT_PORT = 25

  lazy val mock = app.configuration.getBoolean("mail.mock") getOrElse false
  lazy val host = app.configuration.getString("smtp.host") getOrElse DEFAULT_HOST
  lazy val port = app.configuration.getInt("smtp.port") getOrElse DEFAULT_PORT
  lazy val username = app.configuration.getString("smtp.username") getOrElse ""
  lazy val password = app.configuration.getString("smtp.password") getOrElse ""

  def mailer = {
    if (app.plugin[MailPlugin].isEmpty)
      throw PlayException("MailPugin Error",
        "The MailPlugin is not initialized, Please edit your conf/play.plugins file and add the following line: " +
          "'400:play.modules.mailb.MailPlugin' (400 is an arbitrary priority and may be changed to match your needs).")
    new Mailer(host, port, username, password)
  }

  override def onStart() {
    Logger.debug("Mail plugin starting... ")
    if (mock) {
      MailActor.startWithMock
      Logger.info("Mail plugin successfully started using mocked mailer")
    }
    else {
      MailActor.startWith(mailer)
      Logger.info("Mail plugin successfully started with smtp server on %s:%s".format(host, port))
    }
  }
}
