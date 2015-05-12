# Topic Publisher #
```
using Emcaster.Sockets;
using Emcaster.Topics;

PgmSource sendSocket = new PgmSource("224.0.0.23", 7272);
sendSocket.Start();

BatchWriter asyncWriter = new BatchWriter(sendSocket, 1024*128);

TopicPublisher publisher = new TopicPublisher(asyncWriter);
publisher.Start();

int sendTimeout = 1000;
publish.PublishObject("Stock-Quotes-AAPL", 123.3, sendTimeout);
```