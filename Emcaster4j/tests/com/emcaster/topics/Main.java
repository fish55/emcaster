package com.emcaster.topics;

import java.io.IOException;
import java.util.Iterator;
import java.util.regex.Pattern;

import com.emcaster.topics.Message;
import com.emcaster.topics.UdpPublisher;
import com.emcaster.topics.UdpSubscriber;

public class Main {

	public static void main(String[] args) throws Exception {
		String address = "224.0.0.23";
		int port = 8001;
		// receive(address, port);
		send(address, port);
	}

	private static void send(String address, int port) throws 
			InterruptedException {
		UdpPublisher pub = new UdpPublisher(address, port);
		pub.connect();
		BatchWriter writer = new BatchWriter(1024*25, pub, pub.getAddress(), pub.getPort());
		Thread thread = new Thread(writer);
		thread.start();
		int count = 0;
		while (true) {
			String msg = "msg: " + count;
			byte[] bytes = msg.getBytes();
			writer.publish("test", bytes, 0, bytes.length);
			//pub.publish("test", bytes, 0, bytes.length);
		}
	}

	private static void receive(String address, int port) throws IOException {
		UdpSubscriber sub = new UdpSubscriber(address, port,
				64 * 1024);
		sub.start();
		
		MessageCounter counter = new MessageCounter();
		while (true) {
			Iterator<Message> iter = sub.readNext();
			while (iter.hasNext()) {
				counter.onMessage(iter.next());
			}
		}
	}

	private static void receivePattern(String address, int port)
			throws IOException {
		UdpSubscriber sub = new UdpSubscriber(address, port,
				64 * 1024);
		sub.start();
		Pattern pattern = Pattern.compile(".*");
		MessageListener receiver = new MessageCounter();
		PatternListener listener = new PatternListener(pattern, receiver);
		SubscriberRunnable runnable = new SubscriberRunnable(sub);
		runnable.add(listener);
		runnable.dispatchMessages();
	}

	private static class MessageCounter implements MessageListener {
		long count = 0;
		long startTime = System.currentTimeMillis();

		public void onMessage(Message msg) {
			count++;
			if ((count % 500000) == 0) {
				long totalTime = System.currentTimeMillis() - startTime;
				System.out.println("count: " + count);
				double avg = count / (totalTime / 1000.00);
				System.out.println("avg/sec: " + avg);
				count = 0;
				startTime = System.currentTimeMillis();
			}
		}
	}
}
