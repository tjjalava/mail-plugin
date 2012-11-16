import io.Source
import org.specs2.mutable._
import play.api.libs.MimeTypes
import play.api.test._
import play.api.test.Helpers._
import play.modules.mail._
import org.junit.runner.RunWith
import org.specs2.runner.JUnitRunner
import play.modules.mail.MailBuilder._

@RunWith(classOf[JUnitRunner])
class MailSpec extends Specification {
  "Mail" should {
    "send dummy email using mock" in {
      running(FakeApplication(additionalConfiguration = Map("mail.mock" -> "true"))) {
        val attachment = Source.fromBytes("Ninja should wear black".toCharArray.map(_.toByte))

        Mail()
          .from("sender", "sender@example.com")
          .to("receiver", "receiver@example.com")
          .withSubject("A subject")
          .withText("body")
          .withAttachments(Attachment("ninja code", attachment, MimeTypes.forExtension("txt").get)).send

        success
      }
    }
  }
}
