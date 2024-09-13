package br.edu.infnet.controller;

import br.edu.infnet.model.Pedido;
import br.edu.infnet.model.StatusPedido;
import br.edu.infnet.service.PedidoService;
import br.edu.infnet.service.StringMessageService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pedido")
public class PedidoController {

        @Autowired
        private PedidoService pedidoService;
        //private StringMessageService stringMessageService;
        @PostMapping
        public ResponseEntity<Pedido> criarPedido(@RequestBody Pedido pedido) {
            return pedidoService.cadastroCliente(pedido);
        }

        @PostMapping("/caminho/{id}")
        public ResponseEntity<Pedido> enviarACaminho(@PathVariable Long id) {
            return pedidoService.alterarStatus(StatusPedido.CAMINHO,id);
        }

        @PostMapping("/entregue/{id}")
        public ResponseEntity<Pedido> enviarConcluido(@PathVariable Long id) {
            return pedidoService.alterarStatus(StatusPedido.ENTREGUE,id);
        }

}
