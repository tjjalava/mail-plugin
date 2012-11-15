package play.modules.mail

import play.api.templates.Html

import org.codemonkey.simplejavamail.Email
import io.Source
import javax.activation.DataSource

case class Attachment(name: String, data: Source, mimeType: String)

object MailBuilder {
  object Mail {
    def apply() = new Mail()
  }

  case class Mail(from: Option[(String, String)] = None, subject: Option[String] = None,
                  to: List[To] = List.empty, text: Option[String] = None, html: Option[Html] = None,
                  attachments: List[Attachment] = List.empty) {
    def withFrom(f: (String, String)): Mail = copy(from = Some(f))
    def withSubject(s: String): Mail = copy(subject = Some(s))
    def withTo(t: List[To]): Mail = copy(to = t)
    def withTo(to_x: To, to_xs: To*) = copy(to = to_x :: to_xs.toList)
    def withText(t: String): Mail = copy(text = Some(t))
    def withHtml(h: Html): Mail = copy(html = Some(h))
    def withAttachments(a: List[Attachment]) = copy(attachments = a)
    def withAttachments(a_x: Attachment, a_xs: Attachment*) = copy(attachments = a_x :: a_xs.toList)

    def toEmail: Email = {
      val email = new Email()
      from.map(f => email.setFromAddress(f._1, f._2))
      subject.map(email.setSubject(_))
      to.foreach(r => email.addRecipient(r.name, r.address, r.rType))
      text.map(email.setText(_))
      html.map(h => email.setTextHTML(h.toString()))
      attachments.foreach(a => email.addAttachment(a.name, a.data.map(_.toByte).toArray, a.mimeType))
      email
    }
  }

}

// vim: set ts=2 sw=2 ft=scala et:
