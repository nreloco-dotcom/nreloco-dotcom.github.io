import java.util.*;

public class Combo extends Producto {
    private Map<Integer, Integer> productosIncluidos; // ID Producto -> Cantidad
    private String descripcion;
    
    // CONSTRUCTOR SIMPLIFICADO (4 parámetros)
    public Combo(String nombre, String descripcion, double precioVenta, int stockInicial) {
        super(nombre, "COMBO", precioVenta, 0, stockInicial); // precioCosto = 0
        this.descripcion = descripcion;
        this.productosIncluidos = new HashMap<>();
    }
    
    // Agregar producto al combo
    public void agregarProducto(int productoId, int cantidad) {
        if (cantidad > 0 && productoId > 0) {
            productosIncluidos.put(productoId, cantidad);
        }
    }
    
    // Método para vender combo
    @Override
    public boolean vender(int cantidad) {
        // 1. Verificar stock de productos individuales
        if (!verificarStockDisponible(cantidad)) {
            return false;
        }
        
        // 2. Descontar stock de productos individuales
        descontarStockProductos(cantidad);
        
        // 3. Descontar stock del combo
        return super.vender(cantidad);
    }
    
    private boolean verificarStockDisponible(int cantidadCombo) {
        for (Map.Entry<Integer, Integer> entry : productosIncluidos.entrySet()) {
            int productoId = entry.getKey();
            int cantidadNecesaria = entry.getValue() * cantidadCombo;
            
            Producto p = buscarProductoPorId(productoId);
            if (p == null || p.getStock() < cantidadNecesaria) {
                return false;
            }
        }
        return true;
    }
    
    private void descontarStockProductos(int cantidadCombo) {
        for (Map.Entry<Integer, Integer> entry : productosIncluidos.entrySet()) {
            int productoId = entry.getKey();
            int cantidadNecesaria = entry.getValue() * cantidadCombo;
            
            Producto p = buscarProductoPorId(productoId);
            if (p != null) {
                p.vender(cantidadNecesaria);
            }
        }
    }
    
    // Método para calcular costo REAL del combo
    public double calcularCostoReal() {
        double costoTotal = 0;
        for (Map.Entry<Integer, Integer> entry : productosIncluidos.entrySet()) {
            Producto p = buscarProductoPorId(entry.getKey());
            if (p != null) {
                costoTotal += p.getPrecioCosto() * entry.getValue();
            }
        }
        return costoTotal;
    }
    
    // Sobrescribir getPrecioCosto para que devuelva el costo real
    @Override
    public double getPrecioCosto() {
        return calcularCostoReal();
    }
    
    // Sobrescribir getGananciaUnitaria
    @Override
    public double getGananciaUnitaria() {
        return getPrecioVenta() - getPrecioCosto();
    }
    
    // Mostrar detalles del combo
    public void mostrarDetalles() {
        System.out.println("\n🎁 COMBO: " + getNombre());
        System.out.println("📝 " + descripcion);
        System.out.println("💰 Precio venta: $" + getPrecioVenta());
        System.out.println("💲 Costo real: $" + String.format("%.2f", getPrecioCosto()));
        System.out.println("📈 Ganancia por combo: $" + String.format("%.2f", getGananciaUnitaria()));
        
        double precioSeparado = calcularPrecioSeparado();
        if (precioSeparado > 0) {
            double ahorro = precioSeparado - getPrecioVenta();
            System.out.println("🏷️  Ahorro vs comprar separado: $" + String.format("%.2f", ahorro));
        }
        
        System.out.println("\n📦 CONTIENE:");
        for (Map.Entry<Integer, Integer> entry : productosIncluidos.entrySet()) {
            Producto p = buscarProductoPorId(entry.getKey());
            if (p != null) {
                System.out.println("   • " + p.getNombre() + " x" + entry.getValue());
            }
        }
    }
    
    private double calcularPrecioSeparado() {
        double total = 0;
        for (Map.Entry<Integer, Integer> entry : productosIncluidos.entrySet()) {
            Producto p = buscarProductoPorId(entry.getKey());
            if (p != null) {
                total += p.getPrecioVenta() * entry.getValue();
            }
        }
        return total;
    }
    
    private Producto buscarProductoPorId(int id) {
        // Necesitamos una referencia al inventario
        // Usaremos InventarioManager como puente
        return InventarioManager.buscarProductoPorId(id);
    }
    
    // Getters
    public String getDescripcion() { return descripcion; }
    public Map<Integer, Integer> getProductosIncluidos() { 
        return new HashMap<>(productosIncluidos); 
    }
    
    @Override
    public String toString() {
        return String.format("[%d] %-35s | Stock: %-3d | PV: $%-7.2f | Gan: $%-6.2f",
            getId(), getNombre(), getStock(), getPrecioVenta(), getGananciaUnitaria());
    }
}