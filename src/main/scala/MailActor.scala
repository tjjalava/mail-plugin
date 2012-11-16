package play.modules.mail

import play.api.Logger
import play.api.libs.concurrent._
import akka.actor.{Actor, Props}
import org.codemonkey.simplejavamail.{MailException, Email, Mailer}
import play.api.Play.current

object MailActor {
  val actorName: String = "mailer"
  def startWith(mailer: Mailer) = Akka.system.actorOf(Props(new MailActor(mailer)), name = actorName)
  def startWithMock = Akka.system.actorOf(Props(new MailActorMock()), name = actorName)
  def get = Akka.system.actorFor("/user/%s".format(actorName))
}

class MailActor(mailer: Mailer) extends Actor {
   def receive = {
      case email: Email => {
         try {
            mailer.sendMail(email)
            Logger.debug("MailPlugin: email sent")
            sender ! true
         } catch {
            case e:MailException => {
               Logger.error("MailPlugin:"+e.getMessage)
               sender ! false
            }
         }
      }
   }
}

class MailActorMock extends Actor {
  def receive = {
    case email: Email => {
      Logger.debug("MailPlugin: email sent to mock")
      sender ! true
    }
  }
}


// vim: set ts=2 sw=2 ft=scala et:
