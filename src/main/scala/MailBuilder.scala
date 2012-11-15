package play.modules.mail

import play.api.templates.Html

import io.Source
import javax.mail.Message.RecipientType
import play.api.Application
import play.api.libs.concurrent._
import org.codemonkey.simplejavamail.Email
import akka.util.{Timeout, Duration}
import akka.pattern.ask

object MailBuilder {
  object Mail {
    def apply() = new Mail[UNSET, UNSET, UNSET, UNSET]()
  }

  class Mail[FROM, TO, SUBJECT, BODY](val from: Option[(String, String)] = None, val subject: Option[String] = None,
                  val recipients: List[Recipient] = List.empty, val text: Option[String] = None,
                  val html: Option[Html] = None, val attachments: List[Attachment] = List.empty) {
    def from(f: (String, String)) = new Mail[SET, TO, SUBJECT, BODY](Some(f), subject, recipients, text, html, attachments)
    def from(name: String, address: String) =
      new Mail[SET, TO, SUBJECT, BODY](Some((name, address)), subject, recipients, text, html, attachments)
    def withRecipients(r: List[Recipient]) =
      new Mail[FROM, SET, SUBJECT, BODY](from, subject, r, text, html, attachments)
    def withRecipients(to_x: Recipient, to_xs: Recipient*) =
      new Mail[FROM, SET, SUBJECT, BODY](from, subject, to_x :: to_xs.toList, text, html, attachments)
    def to(name: String, address: String) = withRecipients(To(name, address) :: recipients)
    def cc(name: String, address: String) = withRecipients(Cc(name, address) :: recipients)
    def bcc(name: String, address: String) = withRecipients(Bcc(name, address) :: recipients)
    def withSubject(s: String) = new Mail[FROM, TO, SET, BODY](from, Some(s), recipients, text, html, attachments)
    def withText(t: String) = new Mail[FROM, TO, SUBJECT, SET](from, subject, recipients, Some(t), html, attachments)
    def withHtml(h: Html) = new Mail[FROM, TO, SUBJECT, SET](from, subject, recipients, text, Some(h), attachments)
    def withAttachments(a: List[Attachment]) = new Mail[FROM, TO, SUBJECT, BODY](from, subject, recipients, text, html, a)
    def withAttachments(a_x: Attachment, a_xs: Attachment*) =
      new Mail[FROM, TO, SUBJECT, BODY](from, subject, recipients, text, html, a_x :: a_xs.toList)
    def withAttachment(name: String, data: Source, mimeType: String) =
      new Mail[FROM, TO, SUBJECT, BODY](from, subject, recipients, text, html, Attachment(name, data, mimeType) :: attachments)
  }

  implicit def enableSending(mail: Mail[SET, SET, SET, SET]) = new {
    def send(implicit app: Application) = {
      app.configuration.getString("mail.smtp") match {
        case Some(s) if (s.equalsIgnoreCase("dev")) => Akka.future(true)
        case _ => {
          implicit val timeout: Timeout = Duration(5, "seconds")
          (MailPlugin.worker ? (toEmail)).mapTo[Boolean].asPromise   //FIX-ME, switch to fire and forget
        }
      }
    }
    private def toEmail = {
      val e = new Email()
      mail.from.map { case (name, address) => e.setFromAddress(name, address) }
      e.setSubject(mail.subject.get)
      mail.recipients.foreach(r => e.addRecipient(r.name, r.address, r.rType))
      mail.text.map(e.setText(_))
      mail.html.map(h => e.setTextHTML(h.toString()))
      mail.attachments.foreach(a => e.addAttachment(a.name, a.data.map(_.toByte).toArray, a.mimeType))
      e
    }
  }

  abstract class UNSET
  abstract class SET
}

case class Attachment(name: String, data: Source, mimeType: String)

abstract class Recipient(val rType: RecipientType) {
  val name: String
  val address: String
}
case class To(name: String, address: String) extends Recipient(RecipientType.TO)
case class Bcc(name: String, address: String) extends Recipient(RecipientType.BCC)
case class Cc(name: String, address: String) extends Recipient(RecipientType.CC)

// vim: set ts=2 sw=2 ft=scala et:
