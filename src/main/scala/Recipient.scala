package play.modules.mail

import javax.mail.Message.RecipientType

/**
  * User: piotr
 * Date: 11/15/12
 * Time: 5:35 PM
 */
abstract class Recipient(val rType: RecipientType) {
  val name: String
  val address: String
}
case class To(name: String, address: String) extends Recipient(RecipientType.TO)
case class Bcc(name: String, address: String) extends Recipient(RecipientType.BCC)
case class CC(name: String, address: String) extends Recipient(RecipientType.CC)
