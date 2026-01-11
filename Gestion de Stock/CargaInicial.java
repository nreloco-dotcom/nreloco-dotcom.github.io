public class CargaInicial {
    
    public static void cargarStockReal(Inventario inventario) {
        
        // ===== FUNDAS =====
        inventario.agregarProducto(new Producto("Funda Silicone iPhone 11", "Funda", 4500, 1770, 5));
        inventario.agregarProducto(new Producto("Funda Transparente iPhone 11", "Funda", 3500, 1210, 5));
        inventario.agregarProducto(new Producto("Funda Silicone iPhone 13", "Funda", 4500, 1770, 5));
        inventario.agregarProducto(new Producto("Funda Transparente iPhone 13", "Funda", 3500, 1210, 5));
        inventario.agregarProducto(new Producto("Funda Silicone iPhone 16", "Funda", 4500, 1770, 5));
        inventario.agregarProducto(new Producto("Funda Transparente iPhone 16", "Funda", 4000, 1210, 5));
        
        // ===== TEMPLADOS =====
        inventario.agregarProducto(new Producto("Templado iPhone 11", "Templado", 2500, 700, 10));
        inventario.agregarProducto(new Producto("Templado iPhone 16", "Templado", 2500, 800, 10));
        
        // ===== CARGADORES =====
        inventario.agregarProducto(new Producto("Cargador iPhone", "Cargador", 11000, 5900, 30));
        
        System.out.println("✅ " + inventario.getTodosProductos().size() + " productos cargados");
    }
}