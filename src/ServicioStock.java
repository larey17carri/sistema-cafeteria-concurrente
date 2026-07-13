import java.util.HashMap;
import java.util.Map;

// Clase encargada de controlar el stock de productos
public class ServicioStock {

    private Map<String, Producto> productos = new HashMap<>();
    private MetricasSistema metricas;

    public ServicioStock(MetricasSistema metricas) {
        this.metricas = metricas;

        productos.put("Empanada", new Producto("Empanada", 10));
        productos.put("Jugo", new Producto("Jugo", 8));
        productos.put("Pan con pollo", new Producto("Pan con pollo", 6));

        metricas.registrarStockInicial("Empanada", 10);
        metricas.registrarStockInicial("Jugo", 8);
        metricas.registrarStockInicial("Pan con pollo", 6);
    }

    public synchronized boolean descontarStock(String nombreProducto) {

        Producto producto = productos.get(nombreProducto);

        if (producto == null) {
            System.out.println("Producto no encontrado: " + nombreProducto);
            return false;
        }

        if (producto.getStock() > 0) {
            producto.descontarUno();

            metricas.actualizarStock(nombreProducto, producto.getStock());

            System.out.println("Stock descontado: "
                    + nombreProducto
                    + " | Stock restante: "
                    + producto.getStock());

            return true;
        }

        System.out.println("Sin stock disponible para: " + nombreProducto);
        return false;
    }

    public synchronized void reponerStock(String nombreProducto, int cantidad) {

        Producto producto = productos.get(nombreProducto);

        if (producto != null) {
            producto.aumentarStock(cantidad);
            metricas.actualizarStock(nombreProducto, producto.getStock());

            System.out.println("Reposición de stock: "
                    + nombreProducto
                    + " +"
                    + cantidad
                    + " | Stock actual: "
                    + producto.getStock());
        }
    }
}
