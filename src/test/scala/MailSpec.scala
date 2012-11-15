import org.specs2.mutable._
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
         running(FakeApplication(
            additionalConfiguration= Map(
               "mail.smtp" -> "dev"/*,
               "smtp.host" -> "localhost")*/
            ))) {
            import play.api.Play.current

            val m = Mail()
                  .withFrom("Bibi","no-reply@bibi.com")
                  .withTo(To("Toto","toto@bibi.com"))
                  .withSubject("A subject")
                  .withText("body")

            MailPlugin.send(m).map(r => println("Mail sent ? "+r))

            success
         }
      }
   }
}
