import java.util.HashMap;
import java.util.Map;

// Clase encargada de controlar el stock de productos
public class ServicioStock {

    // Mapa que almacena los productos por nombre
    private Map<String, Producto> productos = new HashMap<>();

    // Constructor: carga productos iniciales al sistema
    public ServicioStock() {
        productos.put("Empanada", new Producto("Empanada", 2));
        productos.put("Jugo", new Producto("Jugo", 1));
        productos.put("Pan con pollo", new Producto("Pan con pollo", 1));
    }

    // Método sincronizado para evitar que dos hilos descuenten stock al mismo tiempo
    public synchronized boolean descontarStock(String nombreProducto) {

        // Busca el producto solicitado en el mapa
        Producto producto = productos.get(nombreProducto);

        // Si el producto no existe, no se puede procesar
        if (producto == null) {
            System.out.println("Producto no encontrado: " + nombreProducto);
            return false;
        }

        // Verifica si todavía existe stock disponible
        if (producto.getStock() > 0) {

            // Descuenta una unidad del producto
            producto.descontarUno();

            // Muestra el stock restante después del descuento
            System.out.println("Stock descontado: "
                    + nombreProducto
                    + " | Stock restante: "
                    + producto.getStock());

            return true;
        }

        // Si no hay stock, el pedido no debe procesarse
        System.out.println("Sin stock disponible para: " + nombreProducto);
        return false;
    }
}
