## Wire Format ##

Emcaster uses a standard wire format that can be parsed in any language, on any platform. Every message has four fields.

  1. 4 Byte Integer - Little Endian - Topic Size
  1. 4 Byte Integer - Little Endian - Message Size
  1. UTF 8 - Topic Bytes
  1. Message Bytes - Application Specific Message

Messages are transmitted over the network in batch format. Each network message will contain at least one message. The message rate and buffer sizes of the applications will dictate the batch size.

## API's ##

[Intro to the .NET API](IntroDotNet.md)

[Intro to the Java API](Intro4Java.md)

## Message Format ##

Emcaster works with any message format. Messages are published as simple byte arrays which could represent an XML document or a raw struct. The core Emcaster library can handle several hundred thousand messages per second at the raw transport layer. The marshaling overhead of the messages can often be the limiting factor for throughput, so choose the appropriate marshaling strategy for the expected volume.

If publishing messages across languages or platforms, XML is usually a safe choice. A common approach is to define an explicit message contract with [XSD](http://en.wikipedia.org/wiki/Xsd).

### .NET XML ###
In .NET, the XSD tool can be used to generate classes to handle XML serialization.
  * [XSD](http://msdn2.microsoft.com/en-us/library/x6c1kb0s(VS.80).aspx)

### Java XML ###
  * [XmlBeans](http://xmlbeans.apache.org/)
  * [JAXB](http://java.sun.com/webservices/jaxb/)
  * [JIBX](http://jibx.sourceforge.net/)