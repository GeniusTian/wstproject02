package com.atguigu.flume.sink;

import org.apache.flume.*;
import org.apache.flume.conf.Configurable;
import org.apache.flume.sink.AbstractSink;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wststart
 * @create 2019-08-12 18:46
 */
public class CustomSink extends AbstractSink implements Configurable {
    private Integer batchSize = null;
    private String prefix = null;
    private List <Event> events = new ArrayList <>();
    private static Logger LOG = LoggerFactory.getLogger(CustomSink.class);

    @Override
    public Status process() throws EventDeliveryException {
        Channel channel = getChannel();
        Transaction transaction = channel.getTransaction();
        transaction.begin();

        try {
            for (int i = 0; i < batchSize; i++) {
                Event take = channel.take();
                if (take == null) break;
                events.add(take);
            }
            for (Event event : events) {
                LOG.info(prefix + "-" + new String(event.getBody()));
            }
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
            return Status.BACKOFF;
        } finally {
            transaction.close();
        }
        return Status.READY;
    }

    @Override
    public void configure(Context context) {
        batchSize = context.getInteger("batchSize");
        prefix = context.getString("prefix");
    }
}
