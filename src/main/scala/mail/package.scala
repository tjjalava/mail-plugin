/** A Play 2 plugin providing a scala wrapper to simple-java-mail
  *
  * Put '`400:mail.MailPlugin`' into `conf/play.plugins` to make it work.
  *
  * Recognized configuration properties (to be set in `application.conf`):
  *  - '''mail.mock''':     creates mocked mailer actor on startup and omits any other settings if set to `true`
  *  - '''smtp.host''':     hostname of smtp server to be used, defaults to `localhost`
  *  - '''smtp.port''':     port of smtp server to be used, defaults to `25`
  *  - '''smtp.username''': username to be used when accessing smtp server, defaults to empty string
  *  - '''smtp.password''': password to be used when accessing smtp server, defaults to empty string
  *
  *  Code snippet:
  *  {{{
  *   import mail._
  *   import Mail._
  *
  *    val attachment = Source.fromBytes("Ninja should wear black".toCharArray.map(_.toByte))
  *     Mail()
  *       .from("sender", "sender@example.com")
  *       .to("receiver", "receiver@example.com")
  *       .withSubject("A subject")
  *       .withText("body")
  *       .withAttachments(Attachment("ninja code", attachment, "text/plain")
  *       .send()
  *  }}}
  */
package object mail {}
