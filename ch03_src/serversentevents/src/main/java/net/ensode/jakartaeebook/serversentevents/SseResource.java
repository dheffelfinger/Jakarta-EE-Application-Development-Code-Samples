package net.ensode.jakartaeebook.serversentevents;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.sse.OutboundSseEvent;
import jakarta.ws.rs.sse.Sse;
import jakarta.ws.rs.sse.SseBroadcaster;
import jakarta.ws.rs.sse.SseEventSink;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.DoubleStream;

@ApplicationScoped
@Path("serversentevents")
public class SseResource {

  private static final Logger LOG = Logger.getLogger(SseResource.class.getName());

  private DoubleStream stockTickerValues = DoubleStream.iterate(10.0, val -> val + 0.9);

  private SseBroadcaster sseBroadcaster;
  private OutboundSseEvent.Builder eventBuilder;

  private Executor executor = Executors.newSingleThreadExecutor();

  private OutboundSseEvent.Builder outboundSseEventBuilder;

  @PostConstruct
  public void init() {
    sendEvents();
  }

  @Context
  public void setSse(Sse sse) {
    this.eventBuilder = sse.newEventBuilder();
    this.sseBroadcaster = sse.newBroadcaster();
  }

  @GET
  @Path("subscribe")
  @Produces(MediaType.SERVER_SENT_EVENTS)
  public void subscribe(@Context SseEventSink sseEventSink) {
    LOG.info(String.format("%s.subscribe() invoked", this.getClass().getName()));
    this.sseBroadcaster.register(sseEventSink);
  }

  public void sendEvents() {
    executor.execute(() -> {
      stockTickerValues.forEach(value -> {
        try {
          TimeUnit.SECONDS.sleep(5);
          LOG.info(String.format("Sending the following value: %.2f", value));
          final OutboundSseEvent outboundSseEvent = eventBuilder
                  .name("ENSD stock ticker value")
                  .data(String.class, String.format("%.2f", value))
                  .build();
          LOG.info(String.format("broadcasting event: %.2f", value));
          //broadcaster.broadcast(builder.data(Message.class, message).mediaType(MediaType.APPLICATION_JSON_TYPE).build());
          sseBroadcaster.broadcast(outboundSseEvent);

        } catch (InterruptedException ex) {
          LOG.log(Level.SEVERE, "Exception caught", ex);
        }

      });

    });
  }
}
