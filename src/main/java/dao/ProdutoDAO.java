package dao;

import model.Produto;
import java.util.ArrayList;

/**
 * Classe Data Access Object (DAO) para o Produto.
 * 
 * @author João Pedro Maziero
 */
public class ProdutoDAO {
    
    /**
     * Lista estática que armazena todos os produtos cadastrados no sistema
     * durante o tempo de execução.
     */
    private static ArrayList<Produto> minhaLista = new ArrayList<>();
    
    /**
     * Recupera a lista de produtos cadastrados.
     *
     * @return O {@link ArrayList} contendo todas as instâncias de {@link Produto}.
     */
    public static ArrayList<Produto> getMinhaLista() {
        return minhaLista;
    }

    public static void setMinhaLista(ArrayList minhaLista) {
        ProdutoDAO.minhaLista = minhaLista;
    }

    /**
     * Procura em toda a lista de produtos para identificar e retornar o maior 
     * valor de ID preenchido. 
     * 
     * @return O maior ID encontrado na lista.
     */
    public static int maiorID() {
        int maiorID = 0;
        for (int i = 0; i < minhaLista.size(); i++) {
            if (minhaLista.get(i).getId() > maiorID) {
                maiorID = minhaLista.get(i).getId();
            }
        }
        return maiorID;
    }
}


