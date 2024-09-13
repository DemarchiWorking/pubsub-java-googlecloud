package br.edu.infnet.service;

import br.edu.infnet.model.Evento;
import br.edu.infnet.model.Pedido;
import br.edu.infnet.model.StatusPedido;
import br.edu.infnet.repository.PedidoRepository;
import com.google.api.client.util.DateTime;
import com.google.cloud.spring.pubsub.core.PubSubTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PedidoService {
    @Autowired
    private PubSubTemplate pubSubTemplate;

    @Autowired
    private PedidoRepository pedidoRepository;
    public ResponseEntity<Pedido> cadastroCliente(Pedido pedido){
        pedido.setStatus(StatusPedido.valueOf("PREPARACAO"));
        Pedido pedidoNovo = pedidoRepository.save(pedido);
        Evento evento = new Evento(pedido.getId(),pedido.getDescricao(), LocalDateTime.now());
        pubSubTemplate.publish("dr4_topic",pedidoNovo.getId()+"|"+StatusPedido.PREPARACAO+"|"+evento.getData());

        return ResponseEntity.ok(pedidoNovo);

    }
    public ResponseEntity<Pedido> alterarStatus(StatusPedido status, Long id){

        Pedido pedido = pedidoRepository.findById(id).orElse(null);
        if(pedido == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }else{
            pedido.setStatus(status);
            pedidoRepository.save(pedido); // Este método realiza a atualização
            pubSubTemplate.publish("dr4_topic",pedido.getId()+"|"+status+"|"+LocalDateTime.now());

            return ResponseEntity.ok(pedido);
        }
    }
    public Optional<Pedido> buscarPorId(Long id) {
        return pedidoRepository.findById(id);
    }
}
