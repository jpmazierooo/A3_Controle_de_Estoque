package dao;

import java.util.ArrayList;
import java.util.List;
import model.Movimentacao;

public class MovimentacaoDAO {

    private List<Movimentacao> movimentacoes;

    public MovimentacaoDAO() {
        movimentacoes = new ArrayList<>();
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
