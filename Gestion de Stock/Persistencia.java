import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class Persistencia {
    private static final String ARCHIVO_PRODUCTOS = "productos.txt";
    private static final String ARCHIVO_VENTAS = "ventas.txt";
    private static final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    
    // ========== GUARDAR ==========
    
    public static void guardarTodo(Inventario inventario, SistemaGanancias sistema) {
        guardarProductos(inventario);
        guardarVentas(sistema);
        guardarConfiguracion();
        System.out.println("💾 Datos guardados correctamente");
    }
    
    private static void guardarProductos(Inventario inventario) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(ARCHIVO_PRODUCTOS))) {
            // Usar Locale.US para asegurar punto decimal
            for (Producto p : inventario.getTodosProductos()) {
                pw.println(String.format(Locale.US, "%d|%s|%s|%d|%.2f|%.2f",
                    p.getId(),
                    p.getNombre(),
                    p.getCategoria(),
                    p.getStock(),
                    p.getPrecioVenta(),
                    p.getPrecioCosto()
                ));
            }
        } catch (IOException e) {
            System.out.println("❌ Error guardando productos");
        }
    }
    
    private static void guardarVentas(SistemaGanancias sistema) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(ARCHIVO_VENTAS))) {
            // Usar Locale.US para punto decimal
            for (Venta v : sistema.getVentas()) {
                pw.println(String.format(Locale.US, "%s|%d|%.2f|%.2f|%s",
                    v.getProducto().getNombre(),
                    v.getCantidad(),
                    v.getTotal(),
                    v.getGanancia(),
                    sdf.format(v.getFecha())
                ));
            }
        } catch (IOException e) {
            System.out.println("❌ Error guardando ventas");
        }
    }
    
    private static void guardarConfiguracion() {
        try (PrintWriter pw = new PrintWriter(new FileWriter("config.txt"))) {
            pw.println("ULTIMO_ID=" + Producto.getUltimoId());
        } catch (IOException e) {
            System.out.println("⚠️  Error guardando configuración");
        }
    }
    
    // ========== CARGAR ==========
    
    public static void cargarTodo(Inventario inventario, SistemaGanancias sistema) {
        cargarConfiguracion();
        cargarProductos(inventario);
        cargarVentas(sistema, inventario);
    }
    
    private static void cargarProductos(Inventario inventario) {
        File archivo = new File(ARCHIVO_PRODUCTOS);
        if (!archivo.exists()) {
            System.out.println("📁 No hay datos previos de productos");
            return;
        }
        
        try (BufferedReader br = new BufferedReader(new FileReader(ARCHIVO_PRODUCTOS))) {
            String linea;
            int cargados = 0;
            int errores = 0;
            
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split("\\|");
                if (partes.length != 6) {
                    System.out.println("⚠️  Formato incorrecto: " + linea);
                    errores++;
                    continue;
                }
                
                try {
                    // IMPORTANTE: Reemplazar comas por puntos
                    String precioVentaStr = partes[4].replace(",", ".");
                    String precioCostoStr = partes[5].replace(",", ".");
                    
                    // Verificar que sean números válidos
                    double precioVenta = Double.parseDouble(precioVentaStr);
                    double precioCosto = Double.parseDouble(precioCostoStr);
                    int stock = Integer.parseInt(partes[3]);
                    int id = Integer.parseInt(partes[0]);
                    
                    Producto p = new Producto(
                        partes[1], // nombre
                        partes[2], // categoria
                        precioVenta,
                        precioCosto,
                        stock
                    );
                    
                    p.setId(id);
                    inventario.agregarProducto(p);
                    cargados++;
                    
                } catch (NumberFormatException e) {
                    System.out.println("⚠️  Error numérico en: " + linea);
                    System.out.println("   Detalle: " + e.getMessage());
                    errores++;
                } catch (Exception e) {
                    System.out.println("⚠️  Error cargando: " + linea);
                    errores++;
                }
            }
            
            System.out.println("📦 Productos cargados: " + cargados + " | Errores: " + errores);
            
        } catch (IOException e) {
            System.out.println("❌ Error leyendo archivo de productos");
        }
    }
    
    private static void cargarVentas(SistemaGanancias sistema, Inventario inventario) {
        File archivo = new File(ARCHIVO_VENTAS);
        if (!archivo.exists()) {
            System.out.println("📁 No hay ventas anteriores guardadas");
            return;
        }
        
        try (BufferedReader br = new BufferedReader(new FileReader(ARCHIVO_VENTAS))) {
            String linea;
            int cargadas = 0;
            
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split("\\|");
                if (partes.length != 5) continue;
                
                try {
                    Producto producto = inventario.buscarPorNombre(partes[0]);
                    if (producto == null) continue;
                    
                    int cantidad = Integer.parseInt(partes[1]);
                    
                    // Reemplazar comas en números
                    String totalStr = partes[2].replace(",", ".");
                    String gananciaStr = partes[3].replace(",", ".");
                    
                    double total = Double.parseDouble(totalStr);
                    double ganancia = Double.parseDouble(gananciaStr);
                    
                    // Recalcular la venta
                    Venta venta = new Venta(producto, cantidad);
                    sistema.registrarVenta(venta);
                    cargadas++;
                    
                } catch (Exception e) {
                    System.out.println("⚠️  Error cargando venta: " + linea);
                }
            }
            
            if (cargadas > 0) {
                System.out.println("💰 " + cargadas + " ventas cargadas");
            }
            
        } catch (IOException e) {
            System.out.println("❌ Error leyendo ventas");
        }
    }
    
    private static void cargarConfiguracion() {
        try (BufferedReader br = new BufferedReader(new FileReader("config.txt"))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (linea.startsWith("ULTIMO_ID=")) {
                    try {
                        int id = Integer.parseInt(linea.split("=")[1]);
                        Producto.setUltimoId(id);
                        System.out.println("🔢 Último ID cargado: " + id);
                    } catch (Exception e) {
                        System.out.println("⚠️  Error cargando último ID");
                    }
                }
            }
        } catch (Exception e) {
            // Si no existe, empezamos desde 0
        }
    }
}