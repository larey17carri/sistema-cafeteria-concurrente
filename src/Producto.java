// Clase que representa un producto de la cafetería
public class Producto {

    private final String nombre;
    private int stock;

    public Producto(String nombre, int stock) {
        this.nombre = nombre;
        this.stock = stock;
    }

    public String getNombre() {
        return nombre;
    }

    public int getStock() {
        return stock;
    }

    public void descontarUno() {
        stock--;
    }

    public void aumentarStock(int cantidad) {
        stock += cantidad;
    }
}
