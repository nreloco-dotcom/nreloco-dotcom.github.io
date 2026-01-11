import java.util.*;

public class InventarioManager {
    private static Inventario inventario;
    
    public static void setInventario(Inventario inv) {
        inventario = inv;
    }
    
    public static Inventario getInventario() {
        return inventario;
    }
    
    public static Producto buscarProductoPorId(int id) {
        if (inventario != null) {
            return inventario.buscarPorId(id);
        }
        return null;
    }
    
    // ==============================================
    // 🎁 COMBOS CON CONSTRUCTOR DE 4 PARÁMETROS
    // ==============================================
    public static void crearCombosReales() {
        System.out.println("\n🎁 Creando combos predeterminados...");
        
        // Buscar productos
        Producto funda11Transp = buscarPorNombre("Funda Transparente iPhone 11");
        Producto funda11Silicone = buscarPorNombre("Funda Silicone iPhone 11");
        Producto templado11 = buscarPorNombre("Templado iPhone 11");
        
        Producto funda13Transp = buscarPorNombre("Funda Transparente iPhone 13");
        Producto funda13Silicone = buscarPorNombre("Funda Silicone iPhone 13");
        
        Producto funda16Transp = buscarPorNombre("Funda Transparente iPhone 16");
        Producto funda16Silicone = buscarPorNombre("Funda Silicone iPhone 16");
        Producto templado16 = buscarPorNombre("Templado iPhone 16");
        
        Producto cargador = buscarPorNombre("Cargador iPhone");
        
        int combosCreados = 0;
        
        // ===== COMBOS FUNDA + TEMPLADO =====
        
        if (funda11Transp != null && templado11 != null) {
            Combo comboT11 = new Combo(
                "Combo Funda Transparente + Templado iPhone 11",
                "Funda transparente + templado de vidrio iPhone 11",
                5500,  // precioVenta
                5      // stock
            );
            comboT11.agregarProducto(funda11Transp.getId(), 1);
            comboT11.agregarProducto(templado11.getId(), 1);
            inventario.agregarProducto(comboT11);
            combosCreados++;
        }
        
        if (funda11Silicone != null && templado11 != null) {
            Combo comboS11 = new Combo(
                "Combo Funda Silicone Case + Templado iPhone 11",
                "Funda silicone + templado iPhone 11",
                6500,  // precioVenta
                5      // stock
            );
            comboS11.agregarProducto(funda11Silicone.getId(), 1);
            comboS11.agregarProducto(templado11.getId(), 1);
            inventario.agregarProducto(comboS11);
            combosCreados++;
        }
        
        if (funda16Silicone != null && templado16 != null) {
            Combo comboS16 = new Combo(
                "Combo Funda Silicone Case + Templado iPhone 16",
                "Funda silicone + templado iPhone 16",
                6500, 5
            );
            comboS16.agregarProducto(funda16Silicone.getId(), 1);
            comboS16.agregarProducto(templado16.getId(), 1);
            inventario.agregarProducto(comboS16);
            combosCreados++;
        }
        
        if (funda16Transp != null && templado16 != null) {
            Combo comboT16 = new Combo(
                "Combo Funda Transparente + Templado iPhone 16",
                "Funda transparente + templado iPhone 16",
                5500, 5
            );
            comboT16.agregarProducto(funda16Transp.getId(), 1);
            comboT16.agregarProducto(templado16.getId(), 1);
            inventario.agregarProducto(comboT16);
            combosCreados++;
        }
        
        // ===== COMBOS CON CARGADOR =====
        
        if (cargador != null && funda11Transp != null && templado11 != null) {
            Combo comboTC11 = new Combo(
                "Combo Funda Transparente + Templado iPhone 11 + Cargador",
                "Kit completo iPhone 11: funda + templado + cargador",
                17000, 5
            );
            comboTC11.agregarProducto(cargador.getId(), 1);
            comboTC11.agregarProducto(funda11Transp.getId(), 1);
            comboTC11.agregarProducto(templado11.getId(), 1);
            inventario.agregarProducto(comboTC11);
            combosCreados++;
        }
        
        if (cargador != null && funda11Silicone != null && templado11 != null) {
            Combo comboSC11 = new Combo(
                "Combo Funda Silicone Case + Templado iPhone 11 + Cargador",
                "Kit premium iPhone 11: funda silicone + templado + cargador",
                18000, 5
            );
            comboSC11.agregarProducto(cargador.getId(), 1);
            comboSC11.agregarProducto(funda11Silicone.getId(), 1);
            comboSC11.agregarProducto(templado11.getId(), 1);
            inventario.agregarProducto(comboSC11);
            combosCreados++;
        }
        
        // ===== COMBOS DOBLES (2 FUNDAS) =====
        
        if (funda11Silicone != null && funda11Transp != null) {
            Combo comboST11 = new Combo(
                "Combo Fundas S+T iPhone 11",
                "Pack 2 fundas: Silicone + Transparente iPhone 11",
                7500, 5
            );
            comboST11.agregarProducto(funda11Silicone.getId(), 1);
            comboST11.agregarProducto(funda11Transp.getId(), 1);
            inventario.agregarProducto(comboST11);
            combosCreados++;
        }
        
        if (funda13Silicone != null && funda13Transp != null) {
            Combo comboST13 = new Combo(
                "Combo Fundas S+T iPhone 13",
                "Pack 2 fundas: Silicone + Transparente iPhone 13",
                7500, 5
            );
            comboST13.agregarProducto(funda13Silicone.getId(), 1);
            comboST13.agregarProducto(funda13Transp.getId(), 1);
            inventario.agregarProducto(comboST13);
            combosCreados++;
        }
        
        if (funda16Silicone != null && funda16Transp != null) {
            Combo comboST16 = new Combo(
                "Combo Fundas S+T iPhone 16",
                "Pack 2 fundas: Silicone + Transparente iPhone 16",
                7500, 5
            );
            comboST16.agregarProducto(funda16Silicone.getId(), 1);
            comboST16.agregarProducto(funda16Transp.getId(), 1);
            inventario.agregarProducto(comboST16);
            combosCreados++;
        }
        
        // ===== COMBOS DOBLES SILICONE x2 =====
        
        if (funda11Silicone != null) {
            Combo comboSS11 = new Combo(
                "Combo Fundas S+S iPhone 11",
                "Pack 2 fundas Silicone iPhone 11",
                8500, 5
            );
            comboSS11.agregarProducto(funda11Silicone.getId(), 2);
            inventario.agregarProducto(comboSS11);
            combosCreados++;
        }
        
        if (funda13Silicone != null) {
            Combo comboSS13 = new Combo(
                "Combo Fundas S+S iPhone 13",
                "Pack 2 fundas Silicone iPhone 13",
                8500, 5
            );
            comboSS13.agregarProducto(funda13Silicone.getId(), 2);
            inventario.agregarProducto(comboSS13);
            combosCreados++;
        }
        
        if (funda16Silicone != null) {
            Combo comboSS16 = new Combo(
                "Combo Fundas S+S iPhone 16",
                "Pack 2 fundas Silicone iPhone 16",
                8500, 5
            );
            comboSS16.agregarProducto(funda16Silicone.getId(), 2);
            inventario.agregarProducto(comboSS16);
            combosCreados++;
        }
        
        System.out.println("✅ " + combosCreados + " combos creados exitosamente");
    }
    
    private static Producto buscarPorNombre(String nombre) {
        if (inventario == null) return null;
        
        for (Producto p : inventario.getTodosProductos()) {
            if (p.getNombre().equalsIgnoreCase(nombre)) {
                return p;
            }
        }
        return null;
    }
}