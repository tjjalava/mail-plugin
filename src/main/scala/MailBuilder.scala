package play.modules.mail

import play.api.templates.Html

import org.codemonkey.simplejavamail.Email

object MailBuilder {

  import javax.mail.Message.RecipientType

  abstract class Recipient(val rType: RecipientType) {
    val name: String
    val address: String
  }
  case class To(name: String, address: String) extends Recipient(RecipientType.TO)
  case class Bcc(name: String, address: String) extends Recipient(RecipientType.BCC)
  case class CC(name: String, address: String) extends Recipient(RecipientType.CC)

  object Mail {
    def apply() = new Mail()
  }

  case class Mail(from: Option[(String, String)] = None, subject: Option[String] = None,
                  to: List[To] = List.empty, text: Option[String] = None, html: Option[Html] = None) {
    def withFrom(f: (String, String)): Mail = this.copy(from = Some(f))
    def withSubject(s: String): Mail = this.copy(subject = Some(s))
    def withTo(t: List[To]): Mail = this.copy(to = t)
    def withTo(to_x: To, to_xs: To*) = this.copy(to = to_x :: to_xs.toList)
    def withText(t: String): Mail = this.copy(text = Some(t))
    def html(h: Html): Mail = this.copy(html = Some(h))

    def toEmail: Email = {
      val email = new Email()
      from.map(f => email.setFromAddress(f._1, f._2))
      subject.map(s => email.setSubject(s))
      to.foreach(r => email.addRecipient(r.name, r.address, r.rType))
      text.map(s => email.setText(s))
      html.map(h => email.setTextHTML(h.toString()))
      email
    }
  }

}

// vim: set ts=2 sw=2 ft=scala et:
