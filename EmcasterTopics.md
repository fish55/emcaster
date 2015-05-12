### Topics ###

Topics with Emcaster are just strings. They can be any length and are encoded in UTF-8 for transport. The topic could be the entire content of the message (e.g. stock-quote-aapl-85.05). Clients can selectively receive messages based upon regular expressions.

Unlike some topic based libraries, Emcaster topics can be created dynamically at runtime. If needed, every message can be sent under a different topic.

```
publisher = ... create a publisher
int count = 0;
....
publisher.publish("topic-"+(count++), "msg".getBytes());
```

### Topic Filtering ###

Topics are filtered using basic regular expressions. Specifically, The [Regex](http://msdn2.microsoft.com/en-us/library/system.text.regularexpressions.regex.aspx)  class is used in .NET and the Pattern class is used in Java.

### Defining Namespaces ###
Since topics are regular expressions, take care to avoid characters commonly used in regular expressions such as asterisks and periods.