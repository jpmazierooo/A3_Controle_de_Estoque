package dao;

import model.Produto;
import java.util.ArrayList;

/**
 *
 * @author João Pedro Maziero
 */
public class ProdutoDAO {

    private static ArrayList<Produto> minhaLista = new ArrayList<>();

    public static ArrayList<Produto> getMinhaLista() {
        return minhaLista;
    }

    public static void setMinhaLista(ArrayList minhaLista) {
        ProdutoDAO.minhaLista = minhaLista;
    }

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


