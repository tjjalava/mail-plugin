A Play 2.0 plugin providing a scala wrapper to simple-java-mail.

Installation
============

As a binary
-----------

Checkout the project, build it from the sources with `sbt package` command. Then either:
* put the jar available in `target/scala-2.9.1` to the lib folder of your play app
* publish it localy with `sbt publish-local` and add `"play.modules.mail" %% "play2-mail-plugin" % "0.1-SNAPSHOT"` to your build settings.

As a Git submodule
------------------
You can add it as a submodule of your play project.
Checkout the project in modules/mail-plugin, then do `git submodule add`

In your project Build.scala add the dependency to the plugin :

        val mailPlugin = Project("mailPlugin", file("modules/mail-plugin"))
        val main = PlayProject(appName, appVersion, appDependencies, mainLang = SCALA)
                    .dependsOn(mailPlugin)


Usage
=====

Add a play.plugins file in your conf directory with :
        400:mail.MailPlugin

Then in your controller, you can do :

        import mail._
        import Mail._

        def sendMail = Action { request =>
            val attachment = Source.fromBytes("Ninja should wear black".toCharArray.map(_.toByte))
            Mail()
                .from("sender", "sender@example.com")
                .to("receiver", "receiver@example.com")
                .withSubject("A subject")
                .withText("body")
                .withAttachments(Attachment("ninja code", attachment, "text/plain")
                .send()
            Ok("It works")
        }

Mail class utilizes statically typed builder pattern, so `send()` method is not available before sender, receiver,
subject and message body (either text or html) is set.

Configuration
=============
In application.conf :

        #put this setting in you want to mock the mail server in development
        mail.mock=true

        #smtp server settings
        smtp.host=smtp.server.com
        smtp.port=25
        smtp.username=
        smtp.password=


