import java.util.concurrent.BlockingQueue;

// Clase que consume pedidos desde la cola y los procesa
public class ConsumidorPedidos implements Runnable {

    // Cola compartida desde donde se tomarán los pedidos
    private BlockingQueue<Pedido> colaPedidos;

    // Servicio encargado de validar y descontar stock
    private ServicioStock servicioStock;

    // Constructor que recibe la cola y el servicio de stock
    public ConsumidorPedidos(BlockingQueue<Pedido> colaPedidos, ServicioStock servicioStock) {
        this.colaPedidos = colaPedidos;
        this.servicioStock = servicioStock;
    }

    @Override
    public void run() {
        try {
            boolean continuar = true;

            // El consumidor trabaja mientras continuar sea verdadero
            while (continuar) {

                // take() espera hasta que exista un pedido en la cola
                Pedido pedido = colaPedidos.take();

                // Pedido especial para finalizar el consumidor
                if (pedido.getId() == 0) {
                    continuar = false;
                    System.out.println("No hay más pedidos por procesar");
                } else {

                    // Procesa solamente los pedidos normales
                    procesarPedido(pedido);
                }
            }

        } catch (InterruptedException e) {

            // Detiene correctamente el hilo si ocurre una interrupción
            Thread.currentThread().interrupt();
        }
    }

    // Método que representa la lógica de procesamiento de un pedido
    private void procesarPedido(Pedido pedido) {

        System.out.println("Procesando pedido "
                + pedido.getId()
                + " de "
                + pedido.getCliente()
                + ": "
                + pedido.getProducto());

        // Se intenta descontar stock del producto solicitado
        boolean stockDisponible = servicioStock.descontarStock(pedido.getProducto());

        // Si hay stock, el pedido se confirma
        if (stockDisponible) {
            System.out.println("Pedido confirmado para "
                    + pedido.getCliente()
                    + ": "
                    + pedido.getProducto());
        } else {

            // Si no hay stock, el pedido se rechaza
            System.out.println("Pedido rechazado para "
                    + pedido.getCliente()
                    + ": "
                    + pedido.getProducto());
        }
    }
}
