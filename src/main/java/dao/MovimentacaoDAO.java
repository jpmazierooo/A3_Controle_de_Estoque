package dao;

import java.util.ArrayList;
import java.util.List;
import model.Movimentacao;

public class MovimentacaoDAO {

    private List<Movimentacao> movimentacoes;

    public MovimentacaoDAO() {
        movimentacoes = new ArrayList<>();

        // Dados para testes da tabela
        movimentacoes.add(new Movimentacao(
                1,
                "Arroz",
                "Alimento",
                10,
                "25/05/2026",
                "Entrada",
                "Estoque normal"
        ));

        movimentacoes.add(new Movimentacao(
                2,
                "Feijão",
                "Alimento",
                5,
                "25/05/2026",
                "Saida",
                "Estoque baixo"
        ));

        movimentacoes.add(new Movimentacao(
                3,
                "Macarrão",
                "Alimento",
                8,
                "25/05/2026",
                "Entrada",
                "Estoque normal"
        ));

     }

    // Retorna todas as movimentações do sistema
    public List<Movimentacao> listarMovimentacoes() {
        return movimentacoes;
    }

    // Verifica se existem movimentações cadastradas
    public boolean possuiMovimentacoes() {
        return !movimentacoes.isEmpty();
    }
}
