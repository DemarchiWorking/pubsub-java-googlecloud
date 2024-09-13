package br.edu.infnet.service;

import br.edu.infnet.model.Pedido;
import br.edu.infnet.repository.PedidoRepository;
import com.google.cloud.spring.pubsub.core.PubSubTemplate;
import com.google.cloud.spring.pubsub.integration.AckMode;
import com.google.cloud.spring.pubsub.integration.inbound.PubSubInboundChannelAdapter;

import com.google.cloud.spring.pubsub.support.BasicAcknowledgeablePubsubMessage;
import com.google.cloud.spring.pubsub.support.GcpPubSubHeaders;
import com.google.cloud.spring.pubsub.support.PublisherFactory;
import com.google.cloud.spring.pubsub.support.SubscriberFactory;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.logging.Logger;

@Service
public class StringMessageService {
    //private static final Logger LOG = (Logger) LoggerFactory.getLogger(StringMessageService.class);

    @Bean
    public MessageChannel inputMessageChannel(){
        return  new PublishSubscribeChannel();
    }
    @Bean
    public PubSubInboundChannelAdapter inboundChandelAdapter(
            @Qualifier("inputMessageChannel") MessageChannel messageChannel, PubSubTemplate pubSubTemplate)
    {
        PubSubInboundChannelAdapter adapter = new PubSubInboundChannelAdapter(pubSubTemplate, "dr4_sub");
        adapter.setOutputChannel(messageChannel);
        adapter.setAckMode(AckMode.MANUAL);
        adapter.setPayloadType(String.class);
        return adapter;
    }

    @ServiceActivator(inputChannel = "inputMessageChannel")
    public void messageReceiver(String payload,
                                @Header(GcpPubSubHeaders.ORIGINAL_MESSAGE)BasicAcknowledgeablePubsubMessage message){
        //LOG.info("--- Mensagem Recebida ---"+ payload);
        message.ack();
    }

    public void sendMessage(String topic, String message) {
        //PubSubTemplate pubSubTemplate = new PubSubTemplate();
        //pubSubTemplate.publish(topic, message);
        //log.info("Mensagem enviada para o tópico {}: {}", topic, message);
    }
}
