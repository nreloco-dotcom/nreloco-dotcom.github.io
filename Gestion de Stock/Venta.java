import java.util.Date;

public class Venta {
    private Producto producto;
    private int cantidad;
    private Date fecha;
    private double total;
    private double ganancia;
    
    public Venta(Producto producto, int cantidad) {
        this.producto = producto;
        this.cantidad = cantidad;
        this.fecha = new Date();
        this.total = producto.getPrecioVenta() * cantidad;
        this.ganancia = (producto.getPrecioVenta() - producto.getPrecioCosto()) * cantidad;
    }
    
    public Producto getProducto() { return producto; }
    public int getCantidad() { return cantidad; }
    public Date getFecha() { return fecha; }
    public double getTotal() { return total; }
    public double getGanancia() { return ganancia; }
    
    @Override
    public String toString() {
        return String.format("%s x%d | Total: $%.2f | Ganancia: $%.2f",
                           producto.getNombre(), cantidad, total, ganancia);
    }
}