using System;
using System.Collections.Generic;
using System.Text;
using NUnit.Framework;
using System.IO;
using Emcaster.Topics;
using Rhino.Mocks;
using Emcaster.Sockets;

namespace EmcasterTest.Topics
{
    [TestFixture]
    public class TopicPublisherTests
    {
        public delegate bool WriteDelegate(byte[] data, int offset, int length);

        [Test]
        public void PublishBytes()
        {
            IList<object> received = new List<object>();
            MessageParser parser = new MessageParser();
            TopicSubscriber subscriber = new TopicSubscriber("AAPL", parser);
            subscriber.Start();
            subscriber.TopicMessageEvent += delegate(IMessageParser msgParser)
            {
                received.Add(msgParser.ParseObject());
            };
         
            WriteDelegate doMessage = delegate(byte[] data, int offset, int length)
            {
                parser.ParseBytes(data, offset, length);
                return true;
            };
            MockRepository mocks = new MockRepository();
            IByteWriter writer = (IByteWriter)mocks.CreateMock(typeof(IByteWriter));
            writer.Start();
            writer.Write(null, 0, 55);
            LastCall.IgnoreArguments().Do(doMessage);
            writer.Write(null, 0, 55);
            LastCall.IgnoreArguments().Do(doMessage);
      
            mocks.ReplayAll();
            
            TopicPublisher pubber = new TopicPublisher(writer);
            pubber.Start();
            pubber.PublishObject("AAPL", "80.54");

            Assert.AreEqual(1, received.Count);
            Assert.AreEqual("80.54", received[0]);

            pubber.PublishObject("MSFT", "34.43");
            Assert.AreEqual(1, received.Count);
        }
    }
}
