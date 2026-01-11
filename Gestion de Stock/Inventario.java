import java.util.*;
import java.util.stream.Collectors;

public class Inventario {
    private List<Producto> productos;
    
    public Inventario() {
        productos = new ArrayList<>();
    }
    
    public void agregarProducto(Producto p) {
        productos.add(p);
    }
    
    public Producto buscarPorId(int id) {
        for (Producto p : productos) {
            if (p.getId() == id) return p;
        }
        return null;
    }
    
    public Producto buscarPorNombre(String nombre) {
        for (Producto p : productos) {
            if (p.getNombre().equalsIgnoreCase(nombre)) return p;
        }
        return null;
    }
    
    public List<Producto> getTodosProductos() {
        return new ArrayList<>(productos);
    }
    
    public List<Producto> getProductosBajoStock(int limite) {
        return productos.stream()
                       .filter(p -> p.getStock() <= limite)
                       .collect(Collectors.toList());
    }
    
    public int getTotalProductos() {
        return productos.size();
    }
}