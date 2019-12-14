package com.atguigu.flume.source;

import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.flume.EventDeliveryException;
import org.apache.flume.FlumeException;
import org.apache.flume.channel.ChannelProcessor;
import org.apache.flume.event.EventBuilder;
import org.apache.flume.source.AbstractPollableSource;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wststart
 * @create 2019-08-11 21:30
 */

public class CustomSouce extends AbstractPollableSource {
    private String prefix = null;
    private Integer batchSize = null;
    private List <Event> events = new ArrayList <>();

    protected Status doProcess() throws EventDeliveryException {
        try {
//        1.读取数据，并封装成Event
            for (int i = 0; i < batchSize; i++) {
                String message = prefix + "-" + i;
                Event event = EventBuilder.withBody(message.getBytes());
                events.add(event);
            }
//        2.将event交给ChannelProcessor
            ChannelProcessor channelProcessor = getChannelProcessor();
            channelProcessor.processEventBatch(events);
        } catch (Exception e) {
            return Status.BACKOFF;
        } finally {
            events.clear();
        }
        return Status.READY;
    }

    protected void doConfigure(Context context) throws FlumeException {
        prefix = context.getString("prefix");
        batchSize = context.getInteger("batchSize", 20);
    }

    protected void doStart() throws FlumeException {
        System.out.println("custom source start");
    }

    protected void doStop() throws FlumeException {
        System.out.println("custom source stop");
    }
}
