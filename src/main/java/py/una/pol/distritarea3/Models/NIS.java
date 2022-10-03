package py.una.pol.distritarea3.Models;


public class NIS {

    private int id = 0;
    private boolean activo = false;
    private int consumo = 0;

    public NIS(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public int getConsumo() {
        return consumo;
    }

    public void setConsumo(int consumo) {
        this.consumo = consumo;
    }

    @Override
    public String toString() {
        return "NIS{" + "id=" + id + ", activo=" + activo + ", consumo=" + consumo + '}';
    }
}
