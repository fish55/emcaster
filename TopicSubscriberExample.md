# Topic Subscriber #

To receive messages, the subscriber must be listening on the same multicast address and port as the source. The TopicSubscriber class subscribes to the [EmcasterTopic](EmcasterTopics.md) with a regular expression.

```
using Emcaster.Sockets;
using Emcaster.Topics;

MessageParserFactory msgParser = new MessageParserFactory();
PgmReader reader = new PgmReader(msgParser);
PgmReceiver receiveSocket = new PgmReceiver("224.0.0.23", 7272, reader);

TopicSubscriber topicSubscriber = new TopicSubscriber("Stock-Quotes-AAPL", msgParser);
topicSubscriber.TopicMessageEvent += YourDelegateMethod;
topicSubscriber.Start();
receiveSocket.Start();
```

## Your Delegate Method ##
Your method should be thread safe. With multiple sources, the method will be called from multiple threads. Do not retain a reference to the parser object since it is reused between objects.

# Topic Queue Subscriber #

A Topic Queue Subscription enqueues messages in a single capped queue.  This allows the message processing to be batched and executed on a separate thread. Batching can increase latency, but usually results in better throughput and better average latency. The read thread is dedicated to receiving the messages as they arrive to the OS and placing them directly into memory. The application probably can allocate more memory to queueing the messages than the OS. With a multi-core/cpu machine, the TopicQueueSubscriber also allows for concurrent processing. It's also thread safe for many dispatch threads, but then message ordering can't be guaranteed as the threads will race each other to process the separate batches.

```
TopicSubscriber topic= ... create a subscriber
topic.Start();
int maxQueueSize = 100000;
TopicQueueSubscriber queue = new TopicQueueSubscriber(topic, maxQueueSize);
while(true)
{
   int maxTimeToWaitForMessage = 1000;
   List<IMessageParser> msgBatch = queue.Dequeue(maxTimeToWaitForMessage);
   ... do something with messages
}
```