# Introduction #

Emcaster is a reliable multicast library written in c#. It can be configured to use [Pragmatic General Multicast (PGM)](http://en.wikipedia.org/wiki/Pragmatic_General_Multicast) for reliable multicast or regular Udp Multicast.

# Dependencies #

  * [.NET 2.0 or later](http://www.microsoft.com/net/default.mspx)
  * [MSMQ 3.0](http://www.microsoft.com/windowsserver2003/technologies/msmq/default.mspx) - InstallMsmq - Installs Pgm as a dependency. Not required for plain Udp Multicast.
  * Windows 2000, Windows 2003, Windows XP
  * [Common Logging from Spring](http://springframework.net/doc-latest/reference/html/ch13.html)

# Features #
  * EmcasterTopics with Regex based Matching
  * Unmatched Performance - Over 700 thousand messages per second on commodity hardware with a gigabit network. See PerformanceTuning.
  * Reliable, In-Order Multicast with PGM - WhyPgm
  * UDP Support - Cross platform support through the Java api.

# Examples #

  * TopicPublisherExample
  * TopicSubscriberExample
  * [UdpTest](http://emcaster.googlecode.com/svn/trunk/Emcaster.NET/EmcasterTest/Topics/UdpSmokeTests.cs)
  * [PgmTest](http://emcaster.googlecode.com/svn/trunk/Emcaster.NET/EmcasterTest/Topics/PgmSmokeTests.cs)