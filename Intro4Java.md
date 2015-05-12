The java API can publish and subscribe to messages over UDP. The messages can be exchanged between .NET and java.

## Dependencies ##

  * Java 5

## Publishing ##

```
UdpPublisher pub = new UdpPublisher(address, port);
pub.connect();
BatchWriter writer = new BatchWriter(1024*25, pub, pub.getAddress(), pub.getPort());
Thread thread = new Thread(writer);
thread.start();
byte[] bytes = "Hello World".getBytes();
writer.publish("test", bytes, 0, bytes.length);
```

## Subscribing ##

```
UdpSubscriber sub = new UdpSubscriber(address, port,
				64 * 1024);
sub.connect();
Pattern pattern = Pattern.compile(".*");
MessageListener receiver = ... create a listener that implements MessageListener;
PatternListener listener = new PatternListener(pattern, receiver);
SubscriberRunnable runnable = new SubscriberRunnable(sub);
runnable.add(listener);
runnable.dispatchMessages();
```

## PGM Support ##
Using JNI on windows, PGM can be used as the underlying transport.

  * [Example with JSocket Wrench](http://jroller.com/page/mrettig?entry=pgm_socket_in_java_with)