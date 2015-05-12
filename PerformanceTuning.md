# Introduction #

Achieving maximum throughput often takes a fair amount of research and tweaking. With modest production hardware and a gigabit network you should expect to be able to send at rates over 500 megabit per second. Generally, smaller messages require more CPU and larger messages require more bandwidth. Smaller messages result in higher message rates (100's of k per second), while larger messages use more bandwidth. With larger messages, make sure the all buffers (write and read) are sized large enough to accommodate the message. Emcaster does not break apart and reassemble messages.

### Serialization ###

.NET serialization is not particularly fast, so if you are attempting to send at high rates avoid serialization.  As an alternative, raw [Struct Serialization](http://groups.google.com/group/microsoft.public.dotnet.languages.csharp/msg/7e43c0f0613adce1) is incredibly fast.

The Emcaster TopicPublisher object provides methods for publishing objects or raw bytes. Obviously, the raw bytes method is the fastest sending method. The object publishing method just uses default .NET serialization.


### Batching ###
Emcaster automatically batches outgoing messages to optimize network writes. A separate thread is used to flush the bytes to the network. The BatchWriter class contains several  methods that may need to be tweaked for hardware and network differences.
  * [BatchWriter](http://emcaster.googlecode.com/svn/trunk/Emcaster/Sockets/BatchWriter.cs)

Also stats can be logged to help determine bottlenecks.
```
asyncWriter = new BatchWriter(sendSocket, 1024*128);
asyncWriter.PrintStats = true;

...
[INFO]  Emcaster.Sockets.BatchWriter - Flushes: 728 Avg/Bytes: 108636 Sleep(ms): 1280
```

Using these stats, the sender can be optimized to balance between CPU utilization, latency, and throughput.

### Buffering ###
The pgm senders and receivers have buffers that may need to be increased to accommodate larger loads.
  * [PgmReceiver](http://emcaster.googlecode.com/svn/trunk/Emcaster/Sockets/PgmReceiver.cs)
  * [PgmSource](http://emcaster.googlecode.com/svn/trunk/Emcaster/Sockets/PgmSource.cs)
  * [PgmReader](http://emcaster.googlecode.com/svn/trunk/Emcaster/Sockets/PgmReader.cs)

### PGM Options ###
Emcaster automatically enables pgm for high speed networks. Other [pgm options](http://msdn2.microsoft.com/en-gb/library/ms738591.aspx) may need to be set to achieve optimal performance. The [PgmConstants](http://emcaster.googlecode.com/svn/trunk/Emcaster/Sockets/PgmConstants.cs) class is provided to help with PGM settings.

The PgmSource class provides some convenience methods for setting the send rate and window size.
```
PgmSource sendSocket = new PgmSource(address, port)
sendSocket.RateKbitsPerSec = 75000
sendSocket.WindowSizeInMSecs = 2000
sendSocket.WindowSizeinBytes = 0
```
Only two options need to be set. The socket will calculate the third. An improperly set send option will result in a socket error at startup.