
package visao;

/**
 * Exceção personalizada utilizada para encapsular e lançar mensagens de erro 
 * relacionadas às validações de regras de negócio na interface gráfica.
 * <p>
 * Estende a classe {@link Exception}, permitindo o tratamento de cenários 
 * de fluxo incorreto (como campos vazios ou dados inválidos) por meio de blocos try-catch.
 * </p>
 * * @author João Pedro Maziero
 * @version 1.0
 */
public class Mensagem extends Exception {
    /**
     * Construtor da exceção que recebe uma mensagem detalhada do erro.
     * Esta mensagem será capturada posteriormente e exibida para o usuário,
     * geralmente através de caixas de diálogo (como o {@code JOptionPane}).
     *
     * @param mensagem O texto descritivo contendo o erro ou validação violada.
     */
    public Mensagem(String mensagem) {
        super(mensagem);
    }
}
