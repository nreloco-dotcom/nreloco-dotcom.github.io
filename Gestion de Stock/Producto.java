import java.io.Serializable;

public class Producto implements Serializable {
    private int id;
    private String nombre;
    private String categoria;
    private int stock;
    private double precioVenta;
    private double precioCosto;
    
    private static int ultimoId = 0;
    
    public Producto(String nombre, String categoria, double precioVenta, 
               double precioCosto, int stock) {
    this.id = ++ultimoId;
    this.nombre = nombre;
    this.categoria = categoria.toUpperCase(); // ← Convertir a mayúsculas
    this.precioVenta = precioVenta;
    this.precioCosto = precioCosto;
    this.stock = stock;
}
    
    // Getters
    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public String getCategoria() { return categoria; }
    public int getStock() { return stock; }
    public double getPrecioVenta() { return precioVenta; }
    public double getPrecioCosto() { return precioCosto; }
    
    // Setters para persistencia
    public void setId(int id) { this.id = id; }
    public static void setUltimoId(int id) { ultimoId = id; }
    public static int getUltimoId() { return ultimoId; }
    
    // Métodos de negocio
    public boolean vender(int cantidad) {
        if (cantidad > 0 && cantidad <= stock) {
            stock -= cantidad;
            return true;
        }
        return false;
    }
    
    public void reponer(int cantidad) {
        if (cantidad > 0) {
            stock += cantidad;
        }
    }
    
    public double getGananciaUnitaria() {
        return precioVenta - precioCosto;
    }
    
    @Override
    public String toString() {
        return String.format("[%d] %-25s | Stock: %-3d | PV: $%-7.2f | Gan: $%-6.2f",
                           id, nombre, stock, precioVenta, getGananciaUnitaria());
    }
}