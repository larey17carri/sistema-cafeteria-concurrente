import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

// Clase principal que inicia el sistema
public class SistemaCafeteria {

    public static void main(String[] args) {

        // Cola compartida entre productor y consumidor
        BlockingQueue<Pedido> colaPedidos = new LinkedBlockingQueue<>();

        // Servicio compartido para controlar el stock
        ServicioStock servicioStock = new ServicioStock();

        // Hilo que registra pedidos en la cola
        Thread productor = new Thread(new ProductorPedidos(colaPedidos));

        // Hilo que procesa pedidos desde la cola y valida stock
        Thread consumidor = new Thread(new ConsumidorPedidos(colaPedidos, servicioStock));

        // Se inicia el hilo productor
        productor.start();

        // Se inicia el hilo consumidor
        consumidor.start();
    }
}
