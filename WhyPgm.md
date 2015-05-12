Emcaster is a _reliable_ messaging framework. It provides at most once delivery to your application. If you need guaranteed delivery, take a look at MSMQ or ActiveMQ.

PGM provides an ideal transport for reliable messaging.

  * In Order Delivery - The protocol will order messages if the messages arrive out of order
  * NAK based messaging - If a message is missed, the receiver will request a repair packet.
  * Multicast - Supports multiple consumers

## Platform Support ##
PGM is only supported on Windows with .NET. The java library only supports UDP.

## How Reliable? ##

As long as the network and your application do not become overloaded or crash, all messages should be delivered. Under heavy load on the network or application layer, messages could be lost, which is most often the desired behavior. Also, Emcaster provides no facilities for recovering from an application crash. Messages are published as multicast packets. If the app is not running, then it will never receive the packets.

## How are messages resent? ##

The PGM protocol controls the resending of data. The [Socket Options](http://msdn2.microsoft.com/en-gb/library/ms738591.aspx) configure how the source retains messages for resends.

NB: MSDN often states that PGM is not supported on Windows XP. That is not true. See InstallMsmq.

## What about slow subscribers? ##
Slow subscribers will request packets to be resent. Under normal loads, this is expected and handled gracefully by PGM. However, if a receiver requests a retransmit of a message and the source no longer has the packet, then the receiver socket will be disconnected temporarily. With some experimentation, you should be able to configure the source to send at rate that the receivers can handle _and_ configure a retransmit window that allows for normal NAK'ing but kicks off abnormally slow receivers. In general, habitually slow receivers need to be found and fixed. If slow receivers are the norm and data loss is acceptable, then it might be better to use [Udp Multicast](http://emcaster.googlecode.com/svn/trunk/EmcasterTest/Topics/UdpSmokeTests.cs).