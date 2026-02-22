package br.com.alurafood.avaliacao.amqp;

import br.com.alurafood.avaliacao.dto.PagamentoDto;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class AvaliacaoListener {

    @RabbitListener(queues = "pagamentos.detalhes-avaliacao")
    public void recebeMensagem(@Payload PagamentoDto pagamento) {
        System.out.println(pagamento.getId() + " " + pagamento.getNumero());

        if (pagamento.getNumero().equals("0000")) {
            throw new RuntimeException("Não é possível processar!");
        }

        String mensagem = """
                Necessário criar registro de avaliação para o Pedido %s;
                ID do Pagamento: %s;
                Nome do Cliente: %s;
                Valor R$%s;
                Status: %s.
                """.formatted(pagamento.getPedidoId(),
                pagamento.getId(),
                pagamento.getNome(),
                pagamento.getValor(),
                pagamento.getStatus());

        System.out.println(mensagem);
    }

}
