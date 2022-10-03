package py.una.pol.distritarea3.Server;

import java.util.ArrayList;
import py.una.pol.distritarea3.Models.NIS;

/**
 *
 * @author Alejandra Lezcano https://github.com/lezcanoale
 */
public class DB {

    public ArrayList<NIS> datos = new ArrayList<>();

    public void datosPrecargados() {
        this.datos.add(new NIS(1));
        this.datos.add(new NIS(2));
        this.datos.add(new NIS(3));
    }

    public NIS obtener(int id) {
        for (NIS elemento : datos) {
            if (elemento.getId() == id) {
                return elemento;
            }
        }
        return null;
    }

    public NIS crear(int id) {
        NIS nuevo = new NIS(id);
        this.datos.add(nuevo);
        return nuevo;
    }

    public ArrayList<NIS> getActivos() {
        ArrayList<NIS> retorno = new ArrayList<>();
        for (NIS elemento : datos) {
            if (elemento.isActivo()) {
                retorno.add(elemento);
            }
        }
        return retorno;
    }

    public ArrayList<NIS> getInActivos() {
        ArrayList<NIS> retorno = new ArrayList<>();
        for (NIS elemento : datos) {
            if (!elemento.isActivo()) {
                retorno.add(elemento);
            }
        }
        return retorno;
    }
}
