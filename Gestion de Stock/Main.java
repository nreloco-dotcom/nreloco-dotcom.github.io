import java.text.SimpleDateFormat;  // ← AÑADE ESTO
import java.util.Scanner;
import java.util.*;
import java.io.*;

public class Main {
    // Variables globales del sistema
    private static Inventario inventario = new Inventario();
    private static SistemaGanancias ganancias = new SistemaGanancias(236700); // Inversión inicial
    private static Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args) {
        mostrarBienvenida();
        inicializarSistema();
        mostrarMenuPrincipal();
    }
    
    // ============================================
    // INICIALIZACIÓN DEL SISTEMA
    // ============================================
    private static void mostrarBienvenida() {
        System.out.println("╔══════════════════════════════════════════╗");
        System.out.println("║     SISTEMA DE GESTIÓN - ACCESORIOS      ║");
        System.out.println("║               BullTecno                  ║");
        System.out.println("╚══════════════════════════════════════════╝");
    }
    
    private static void inicializarSistema() {
    System.out.println("\n🔧 INICIALIZANDO SISTEMA CON DATOS REALES...");
    
    // Cargar datos guardados (si existen)
    Persistencia.cargarTodo(inventario, ganancias);
    
    // Configurar el manager
    InventarioManager.setInventario(inventario);
    
    // Si no hay datos guardados, cargar TUS datos reales
    if (inventario.getTodosProductos().isEmpty()) {
        System.out.println("\n📦 Cargando TU stock real...");
        CargaInicial.cargarStockReal(inventario);
        
        System.out.println("\n🎁 Creando TUS combos reales...");
        InventarioManager.crearCombosReales();
        
        System.out.println("\n✅ Sistema listo con TUS datos reales");
        System.out.println("   Productos: " + contarProductosIndividuales());
        System.out.println("   Combos: " + contarCombos());
        System.out.println("   Inversión: $" + ganancias.getInversionInicial());
    } else {
        System.out.println("✅ Datos cargados de sesiones anteriores");
        System.out.println("   Productos: " + contarProductosIndividuales());
        System.out.println("   Combos: " + contarCombos());
    }
    
    esperarEnter();
}
    
    // ============================================
    // MENÚ PRINCIPAL
    // ============================================
    private static void mostrarMenuPrincipal() {
        int opcion;
        
        do {
            mostrarEncabezadoMenu();
            mostrarOpciones();
            
            try {
                System.out.print("\n▶️  Selecciona una opción: ");
                opcion = scanner.nextInt();
                scanner.nextLine(); // Limpiar buffer
                
                procesarOpcion(opcion);
                
            } catch (Exception e) {
                System.out.println("❌ Error: ingresa un número válido");
                scanner.nextLine();
                opcion = -1;
            }
        } while (true);
    }
    
    private static void mostrarEncabezadoMenu() {
        System.out.println("\n" + "═".repeat(55));
        System.out.println("🏪 MENÚ PRINCIPAL - GESTIÓN DE STOCK");
        System.out.println("═".repeat(55));
    }
    
    private static void mostrarOpciones() {
        System.out.println("1.  📱  Ver todos los productos individuales");
        System.out.println("2.  🎁  Ver combos disponibles");
        System.out.println("3.  💰  Vender producto individual");
        System.out.println("4.  🛒  Vender combo");
        System.out.println("5.  📊  Ver reporte de ganancias");
        System.out.println("6.  ➕  Agregar nuevo producto");
        System.out.println("7.  🔧  Crear nuevo combo personalizado");
        System.out.println("8.  📦  Reponer stock de producto");
        System.out.println("9.  ⚠️   Ver productos con stock bajo");
        System.out.println("10. 🔍  Buscar producto por nombre");
        System.out.println("11. 💾  Guardar datos");
        System.out.println("12. 📤 Exportar datos a CSV/Excel");
        System.out.println("0.  🚪  Salir del sistema");
    }
    
    private static void procesarOpcion(int opcion) {
        switch (opcion) {
            case 1: listarProductosIndividuales(); break;
            case 2: verCombos(); break;
            case 3: venderProductoIndividual(); break;
            case 4: venderCombo(); break;
            case 5: mostrarReporteGanancias(); break;
            case 6: agregarNuevoProducto(); break;
            case 7: crearNuevoCombo(); break;
            case 8: reponerStock(); break;
            case 9: mostrarStockBajo(); break;
            case 10: buscarProducto(); break;
            case 11: guardarDatos(); break;
            case 12: exportarTodoACSV(); break;
            case 0: salirDelSistema(); break;
            default: System.out.println("❌ Opción inválida. Intenta de nuevo.");
        }
    }
    
    // ============================================
    // OPCIÓN 1: VER PRODUCTOS INDIVIDUALES
    // ============================================
    private static void listarProductosIndividuales() {
        System.out.println("\n" + "─".repeat(80));
        System.out.println("📱 PRODUCTOS INDIVIDUALES EN INVENTARIO");
        System.out.println("─".repeat(80));
        
        List<Producto> productos = inventario.getTodosProductos();
        int contador = 0;
        
        for (Producto p : productos) {
            if (!(p instanceof Combo)) {
                System.out.println(p);
                contador++;
            }
        }
        
        if (contador == 0) {
            System.out.println("No hay productos individuales registrados.");
        } else {
            System.out.println("\n📊 Total: " + contador + " productos individuales");
        }
        
        esperarEnter();
    }
    
    // ============================================
    // OPCIÓN 2: VER COMBOS DISPONIBLES
    // ============================================
    private static void verCombos() {
        System.out.println("\n" + "🎁".repeat(30));
        System.out.println("COMBOS DISPONIBLES");
        System.out.println("🎁".repeat(30));
        
        List<Producto> productos = inventario.getTodosProductos();
        boolean hayCombos = false;
        
        for (Producto p : productos) {
            if (p instanceof Combo) {
                hayCombos = true;
                Combo combo = (Combo) p;
                
                System.out.println("\n🔹 ID: " + p.getId());
                System.out.println("🔹 Nombre: " + p.getNombre());
                System.out.println("🔹 Precio: $" + p.getPrecioVenta());
                System.out.println("🔹 Stock disponible: " + p.getStock());
                System.out.println("🔹 Ganancia por unidad: $" + String.format("%.2f", p.getGananciaUnitaria()));
                System.out.println("-".repeat(40));
            }
        }
        
        if (!hayCombos) {
            System.out.println("No hay combos disponibles en este momento.");
            System.out.println("Usa la opción 7 para crear combos.");
        } else {
            System.out.println("\n💡 Para ver detalles completos de un combo, selecciónalo al vender (opción 4).");
        }
        
        esperarEnter();
    }
    
    // ============================================
    // OPCIÓN 3: VENDER PRODUCTO INDIVIDUAL
    // ============================================
    private static void venderProductoIndividual() {
        System.out.println("\n" + "💰".repeat(25));
        System.out.println("VENTA DE PRODUCTO INDIVIDUAL");
        System.out.println("💰".repeat(25));
        
        // Mostrar productos disponibles
        listarProductosIndividuales();
        
        System.out.print("\n▶️  Ingresa el ID del producto a vender: ");
        int id = scanner.nextInt();
        
        System.out.print("▶️  Cantidad a vender: ");
        int cantidad = scanner.nextInt();
        scanner.nextLine(); // Limpiar buffer
        
        // Buscar producto
        Producto producto = inventario.buscarPorId(id);
        
        // Validaciones
        if (producto == null) {
            System.out.println("\n❌ Error: Producto no encontrado.");
            esperarEnter();
            return;
        }
        
        if (producto instanceof Combo) {
            System.out.println("\n❌ Este es un combo. Usa la opción 4 para vender combos.");
            esperarEnter();
            return;
        }
        
        if (cantidad <= 0) {
            System.out.println("\n❌ Error: La cantidad debe ser mayor a 0.");
            esperarEnter();
            return;
        }
        
        // Intentar vender
        if (producto.vender(cantidad)) {
            Venta venta = new Venta(producto, cantidad);
            ganancias.registrarVenta(venta);
            
            mostrarConfirmacionVenta(producto, cantidad, venta);
        } else {
            System.out.println("\n❌ Venta fallida. Stock insuficiente.");
            System.out.println("   Stock disponible: " + producto.getStock());
        }
        
        esperarEnter();
    }
    
    private static void mostrarConfirmacionVenta(Producto producto, int cantidad, Venta venta) {
        System.out.println("\n" + "✅".repeat(20));
        System.out.println("✅ VENTA REGISTRADA EXITOSAMENTE");
        System.out.println("✅".repeat(20));
        
        System.out.println("\n📋 DETALLES DE LA VENTA:");
        System.out.println("   Producto: " + producto.getNombre());
        System.out.println("   Cantidad: " + cantidad + " unidades");
        System.out.printf("   Precio unitario: $%.2f%n", producto.getPrecioVenta());
        System.out.printf("   Total de venta: $%.2f%n", venta.getTotal());
        System.out.printf("   Ganancia obtenida: $%.2f%n", venta.getGanancia());
        System.out.println("   Stock restante: " + producto.getStock() + " unidades");
        
        // Mostrar mensaje especial si el stock quedó bajo
        if (producto.getStock() <= 5) {
            System.out.println("\n⚠️  ALERTA: Stock bajo (" + producto.getStock() + " unidades)");
            System.out.println("   Considera reponer stock pronto.");
        }
    }
    
    // ============================================
    // OPCIÓN 4: VENDER COMBO
    // ============================================
    private static void venderCombo() {
        System.out.println("\n" + "🛒".repeat(25));
        System.out.println("VENTA DE COMBO");
        System.out.println("🛒".repeat(25));
        
        // Mostrar combos disponibles
        System.out.println("\n📋 COMBOS DISPONIBLES:");
        List<Producto> productos = inventario.getTodosProductos();
        boolean hayCombos = false;
        
        for (Producto p : productos) {
            if (p instanceof Combo) {
                hayCombos = true;
                Combo combo = (Combo) p;
                System.out.println("   [" + p.getId() + "] " + p.getNombre() + 
                                 " - Stock: " + p.getStock() + " - $" + p.getPrecioVenta());
            }
        }
        
        if (!hayCombos) {
            System.out.println("❌ No hay combos disponibles para vender.");
            esperarEnter();
            return;
        }
        
        System.out.print("\n▶️  Ingresa el ID del combo a vender: ");
        int idCombo = scanner.nextInt();
        
        System.out.print("▶️  Cantidad de combos a vender: ");
        int cantidad = scanner.nextInt();
        scanner.nextLine(); // Limpiar buffer
        
        // Buscar combo
        Producto producto = inventario.buscarPorId(idCombo);
        
        // Validaciones
        if (producto == null || !(producto instanceof Combo)) {
            System.out.println("\n❌ Error: Combo no encontrado.");
            esperarEnter();
            return;
        }
        
        Combo combo = (Combo) producto;
        
        if (cantidad <= 0) {
            System.out.println("\n❌ Error: La cantidad debe ser mayor a 0.");
            esperarEnter();
            return;
        }
        
        // Mostrar detalles del combo antes de vender
        System.out.println("\n📋 CONFIRMACIÓN DE COMPRA:");
        combo.mostrarDetalles();
        
        System.out.print("\n¿Confirmar venta? (S/N): ");
        String confirmacion = scanner.nextLine();
        
        if (!confirmacion.equalsIgnoreCase("S")) {
            System.out.println("❌ Venta cancelada.");
            esperarEnter();
            return;
        }
        
        // Intentar vender el combo
        if (combo.vender(cantidad)) {
            Venta venta = new Venta(combo, cantidad);
            ganancias.registrarVenta(venta);
            
            System.out.println("\n" + "✅".repeat(20));
            System.out.println("✅ COMBO VENDIDO EXITOSAMENTE");
            System.out.println("✅".repeat(20));
            
            System.out.println("\n📋 DETALLES DE LA VENTA:");
            System.out.println("   Combo: " + combo.getNombre());
            System.out.println("   Cantidad: " + cantidad + " combos");
            System.out.printf("   Precio por combo: $%.2f%n", combo.getPrecioVenta());
            System.out.printf("   Total de venta: $%.2f%n", venta.getTotal());
            System.out.printf("   Ganancia obtenida: $%.2f%n", venta.getGanancia());
            System.out.println("   Stock restante del combo: " + combo.getStock());
            
            // Mostrar stock actualizado de productos individuales
            System.out.println("\n📦 STOCK ACTUALIZADO DE PRODUCTOS:");
            // (Los productos se descontaron automáticamente en combo.vender())
            
        } else {
            System.out.println("\n❌ Venta fallida. Stock insuficiente de algún producto del combo.");
        }
        
        esperarEnter();
    }
    
    // ============================================
    // OPCIÓN 5: REPORTE DE GANANCIAS
    // ============================================
    private static void mostrarReporteGanancias() {
        System.out.println("\n" + "📊".repeat(25));
        System.out.println("REPORTE FINANCIERO COMPLETO");
        System.out.println("📊".repeat(25));
        
        ganancias.mostrarResumenCompleto();
        esperarEnter();
    }
    
    // ============================================
    // OPCIÓN 6: AGREGAR NUEVO PRODUCTO
    // ============================================
    private static void agregarNuevoProducto() {
        System.out.println("\n" + "➕".repeat(25));
        System.out.println("AGREGAR NUEVO PRODUCTO");
        System.out.println("➕".repeat(25));
        
        System.out.println("\n📝 Ingresa los datos del nuevo producto:");
        
        System.out.print("▶️  Nombre del producto: ");
        String nombre = scanner.nextLine();
        
        System.out.print("▶️  Categoría (ej: FUNDA, TEMPLADO, CARGADOR): ");
        String categoria = scanner.nextLine().toUpperCase();
        
        System.out.print("▶️  Precio de venta: $");
        double precioVenta = scanner.nextDouble();
        
        System.out.print("▶️  Precio de costo: $");
        double precioCosto = scanner.nextDouble();
        
        System.out.print("▶️  Stock inicial: ");
        int stock = scanner.nextInt();
        scanner.nextLine(); // Limpiar buffer
        
        // Crear y agregar el producto
        Producto nuevoProducto = new Producto(nombre, categoria, precioVenta, precioCosto, stock);
        inventario.agregarProducto(nuevoProducto);
        
        System.out.println("\n" + "✅".repeat(20));
        System.out.println("✅ PRODUCTO AGREGADO EXITOSAMENTE");
        System.out.println("✅".repeat(20));
        
        System.out.println("\n📋 DETALLES DEL PRODUCTO:");
        System.out.println("   ID asignado: " + nuevoProducto.getId());
        System.out.println("   Nombre: " + nuevoProducto.getNombre());
        System.out.println("   Categoría: " + nuevoProducto.getCategoria());
        System.out.printf("   Precio de venta: $%.2f%n", nuevoProducto.getPrecioVenta());
        System.out.printf("   Precio de costo: $%.2f%n", nuevoProducto.getPrecioCosto());
        System.out.printf("   Ganancia por unidad: $%.2f%n", nuevoProducto.getGananciaUnitaria());
        System.out.println("   Stock inicial: " + nuevoProducto.getStock());
        
        esperarEnter();
    }
    
    // ============================================
    // OPCIÓN 7: CREAR NUEVO COMBO
    // ============================================
    private static void crearNuevoCombo() {
        System.out.println("\n" + "🔧".repeat(25));
        System.out.println("CREAR NUEVO COMBO PERSONALIZADO");
        System.out.println("🔧".repeat(25));
        
        System.out.println("\n📝 Ingresa los datos básicos del combo:");
        
        System.out.print("▶️  Nombre del combo: ");
        String nombre = scanner.nextLine();
        
        System.out.print("▶️  Descripción: ");
        String descripcion = scanner.nextLine();
        
        System.out.print("▶️  Precio de venta del combo: $");
        double precioVenta = scanner.nextDouble();
        
        System.out.print("▶️  Stock inicial del combo: ");
        int stock = scanner.nextInt();
        scanner.nextLine(); // Limpiar buffer
        
        // Crear el combo
        Combo nuevoCombo = new Combo(nombre, descripcion, precioVenta, stock);
        
        // Agregar productos al combo
        System.out.println("\n🎁 AGREGAR PRODUCTOS AL COMBO:");
        boolean agregarMas = true;
        
        while (agregarMas) {
            System.out.println("\n📦 Productos disponibles:");
            listarProductosIndividuales();
            
            System.out.print("\n▶️  ID del producto a incluir (0 para terminar): ");
            int idProducto = scanner.nextInt();
            
            if (idProducto == 0) {
                agregarMas = false;
                continue;
            }
            
            System.out.print("▶️  Cantidad de este producto en el combo: ");
            int cantidadEnCombo = scanner.nextInt();
            scanner.nextLine();
            
            Producto producto = inventario.buscarPorId(idProducto);
            if (producto != null && !(producto instanceof Combo)) {
                nuevoCombo.agregarProducto(producto.getId(), cantidadEnCombo);
                System.out.println("✅ Agregado: " + producto.getNombre() + " x" + cantidadEnCombo);
            } else {
                System.out.println("❌ Producto no válido. Asegúrate de usar un producto individual.");
            }
        }
        
        // Agregar combo al inventario
        inventario.agregarProducto(nuevoCombo);
        
        System.out.println("\n" + "✅".repeat(20));
        System.out.println("✅ COMBO CREADO EXITOSAMENTE");
        System.out.println("✅".repeat(20));
        
        System.out.println("\n📋 RESUMEN DEL COMBO:");
        nuevoCombo.mostrarDetalles();
        
        esperarEnter();
    }
    
    // ============================================
    // OPCIÓN 8: REPONER STOCK
    // ============================================
    private static void reponerStock() {
        System.out.println("\n" + "📦".repeat(25));
        System.out.println("REPONER STOCK DE PRODUCTO");
        System.out.println("📦".repeat(25));
        
        listarProductosIndividuales();
        
        System.out.print("\n▶️  ID del producto a reponer: ");
        int id = scanner.nextInt();
        
        System.out.print("▶️  Cantidad a agregar al stock: ");
        int cantidad = scanner.nextInt();
        scanner.nextLine(); // Limpiar buffer
        
        Producto producto = inventario.buscarPorId(id);
        
        if (producto == null) {
            System.out.println("❌ Producto no encontrado.");
        } else if (producto instanceof Combo) {
            System.out.println("❌ No se puede reponer stock de combos directamente.");
            System.out.println("   Repone los productos individuales que lo componen.");
        } else if (cantidad <= 0) {
            System.out.println("❌ La cantidad debe ser mayor a 0.");
        } else {
            int stockAnterior = producto.getStock();
            producto.reponer(cantidad);
            
            System.out.println("\n✅ STOCK REPUESTO EXITOSAMENTE");
            System.out.println("   Producto: " + producto.getNombre());
            System.out.println("   Stock anterior: " + stockAnterior);
            System.out.println("   Cantidad agregada: " + cantidad);
            System.out.println("   Nuevo stock: " + producto.getStock());
        }
        
        esperarEnter();
    }
    
    // ============================================
    // OPCIÓN 9: PRODUCTOS CON STOCK BAJO
    // ============================================
    private static void mostrarStockBajo() {
        System.out.println("\n" + "⚠️ ".repeat(25));
        System.out.println("PRODUCTOS CON STOCK BAJO (5 unidades o menos)");
        System.out.println("⚠️ ".repeat(25));
        
        List<Producto> productos = inventario.getTodosProductos();
        boolean hayStockBajo = false;
        
        System.out.println("\n📦 PRODUCTOS INDIVIDUALES:");
        for (Producto p : productos) {
            if (!(p instanceof Combo) && p.getStock() <= 5) {
                System.out.println("   [" + p.getId() + "] " + p.getNombre() + 
                                 " - Stock: " + p.getStock() + " unidades");
                hayStockBajo = true;
            }
        }
        
        if (!hayStockBajo) {
            System.out.println("   ✅ Todos los productos tienen stock adecuado.");
        }
        
        System.out.println("\n🎁 COMBOS CON STOCK BAJO:");
        hayStockBajo = false;
        for (Producto p : productos) {
            if (p instanceof Combo && p.getStock() <= 5) {
                System.out.println("   [" + p.getId() + "] " + p.getNombre() + 
                                 " - Stock: " + p.getStock() + " combos");
                hayStockBajo = true;
            }
        }
        
        if (!hayStockBajo) {
            System.out.println("   ✅ Todos los combos tienen stock adecuado.");
        }
        
        esperarEnter();
    }
    
    // ============================================
    // OPCIÓN 10: BUSCAR PRODUCTO
    // ============================================
    private static void buscarProducto() {
        System.out.println("\n" + "🔍".repeat(25));
        System.out.println("BUSCAR PRODUCTO POR NOMBRE");
        System.out.println("🔍".repeat(25));
        
        System.out.print("\n▶️  Ingresa el nombre o parte del nombre: ");
        String busqueda = scanner.nextLine().toLowerCase();
        
        System.out.println("\n🔎 RESULTADOS DE LA BÚSQUEDA:");
        
        List<Producto> productos = inventario.getTodosProductos();
        boolean encontrado = false;
        
        for (Producto p : productos) {
            if (p.getNombre().toLowerCase().contains(busqueda)) {
                System.out.println("\n" + (p instanceof Combo ? "🎁 COMBO" : "📱 PRODUCTO") + ":");
                System.out.println("   ID: " + p.getId());
                System.out.println("   Nombre: " + p.getNombre());
                System.out.println("   Stock: " + p.getStock());
                System.out.printf("   Precio: $%.2f%n", p.getPrecioVenta());
                
                if (p instanceof Combo) {
                    System.out.println("   Tipo: Combo");
                } else {
                    System.out.println("   Categoría: " + p.getCategoria());
                }
                
                encontrado = true;
            }
        }
        
        if (!encontrado) {
            System.out.println("❌ No se encontraron productos con ese nombre.");
        }
        
        esperarEnter();
    }
    
    // ============================================
    // OPCIÓN 11: GUARDAR DATOS
    // ============================================
    private static void guardarDatos() {
        System.out.println("\n💾 Guardando datos del sistema...");
        Persistencia.guardarTodo(inventario, ganancias);
        System.out.println("✅ Datos guardados exitosamente.");
        esperarEnter();
    }
    
    // ============================================
    // OPCIÓN 0: SALIR DEL SISTEMA
    // ============================================
    private static void salirDelSistema() {
        System.out.println("\n" + "🚪".repeat(25));
        System.out.println("SALIENDO DEL SISTEMA");
        System.out.println("🚪".repeat(25));
        
        System.out.print("\n¿Deseas guardar los datos antes de salir? (S/N): ");
        String respuesta = scanner.nextLine();
        
        if (respuesta.equalsIgnoreCase("S")) {
            guardarDatos();
        }
        
        System.out.println("\n👋 ¡Gracias por usar el Sistema de Gestión!");
        System.out.println("   Hasta pronto...");
        
        scanner.close();
        System.exit(0);
    }
    
    // ============================================
    // MÉTODOS AUXILIARES
    // ============================================
    private static void cargarProductosIniciales() {
        // Productos de ejemplo (puedes modificarlos)
        inventario.agregarProducto(new Producto("Funda Silicone iPhone 11", "FUNDA", 4500, 1770, 15));
        inventario.agregarProducto(new Producto("Funda Transparente iPhone 11", "FUNDA", 3500, 1210, 20));
        inventario.agregarProducto(new Producto("Funda Silicone iPhone 13", "FUNDA", 4500, 1770, 12));
        inventario.agregarProducto(new Producto("Funda Transparente iPhone 13", "FUNDA", 3500, 1210, 18));
        inventario.agregarProducto(new Producto("Funda Silicone iPhone 16", "FUNDA", 4500, 1770, 10));
        inventario.agregarProducto(new Producto("Funda Transparente iPhone 16", "FUNDA", 4000, 1210, 15));
        inventario.agregarProducto(new Producto("Templado iPhone 11", "TEMPLADO", 2500, 700, 25));
        inventario.agregarProducto(new Producto("Templado iPhone 16", "TEMPLADO", 2500, 800, 22));
        inventario.agregarProducto(new Producto("Cargador iPhone", "CARGADOR", 11000, 5900, 30));
        inventario.agregarProducto(new Producto("Cable Lightning", "CABLE", 7000, 3500, 40));
        inventario.agregarProducto(new Producto("Audífonos Originales", "AUDIO", 25000, 15000, 8));
    }
    
    private static int contarProductosIndividuales() {
        int count = 0;
        for (Producto p : inventario.getTodosProductos()) {
            if (!(p instanceof Combo)) count++;
        }
        return count;
    }
    
    private static int contarCombos() {
        int count = 0;
        for (Producto p : inventario.getTodosProductos()) {
            if (p instanceof Combo) count++;
        }
        return count;
    }
    
    private static void esperarEnter() {
        System.out.print("\n⏎  Presiona Enter para continuar...");
        scanner.nextLine();
    }
    
    private static void exportarTodoACSV() {
        System.out.println("\n" + "📤".repeat(25));
        System.out.println("EXPORTAR TODO A CSV");
        System.out.println("📤".repeat(25));
        
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        int archivosCreados = 0;
        
        // 1. Exportar productos
        try (PrintWriter pw = new PrintWriter(new FileWriter("inventario_completo.csv"))) {
            pw.println("Tipo,ID,Nombre,Categoria,Stock,Precio_Venta,Precio_Costo,Ganancia_Unitaria,Valor_Stock");
            
            for (Producto p : inventario.getTodosProductos()) {
                String tipo = (p instanceof Combo) ? "COMBO" : "PRODUCTO";
                double valorStock = p.getPrecioCosto() * p.getStock();
                
                pw.println(String.format("%s,%d,%s,%s,%d,%.2f,%.2f,%.2f,%.2f",
                    tipo,
                    p.getId(),
                    p.getNombre(),
                    p.getCategoria(),
                    p.getStock(),
                    p.getPrecioVenta(),
                    p.getPrecioCosto(),
                    p.getGananciaUnitaria(),
                    valorStock
                ));
            }
            System.out.println("✅ inventario_completo.csv creado");
            archivosCreados++;
            
        } catch (IOException e) {
            System.out.println("❌ Error creando inventario_completo.csv");
        }
        
        // 2. Exportar ventas (si hay)
        if (!ganancias.getVentas().isEmpty()) {
            try (PrintWriter pw = new PrintWriter(new FileWriter("historial_ventas.csv"))) {
                pw.println("Fecha,Producto,Cantidad,Precio_Unitario,Total,Ganancia");
                
                for (Venta v : ganancias.getVentas()) {
                    pw.println(String.format("%s,%s,%d,%.2f,%.2f,%.2f",
                        sdf.format(v.getFecha()),
                        v.getProducto().getNombre(),
                        v.getCantidad(),
                        v.getProducto().getPrecioVenta(),
                        v.getTotal(),
                        v.getGanancia()
                    ));
                }
                System.out.println("✅ historial_ventas.csv creado");
                archivosCreados++;
                
            } catch (IOException e) {
                System.out.println("❌ Error creando historial_ventas.csv");
            }
        } else {
            System.out.println("ℹ️  No hay ventas para exportar");
        }
        
        // 3. Exportar resumen
        try (PrintWriter pw = new PrintWriter(new FileWriter("resumen_negocio.csv"))) {
            pw.println("Concepto,Valor");
            pw.println(String.format("Inversion Inicial,%.2f", ganancias.getInversionInicial()));
            pw.println(String.format("Total Ventas,%.2f", ganancias.getTotalVentas()));
            pw.println(String.format("Ganancia Total,%.2f", ganancias.getGananciaTotal()));
            pw.println(String.format("Balance Actual,%.2f", ganancias.getBalanceReal()));
            pw.println(String.format("Productos en Stock,%d", contarProductosIndividuales()));
            pw.println(String.format("Combos en Stock,%d", contarCombos()));
            
            System.out.println("✅ resumen_negocio.csv creado");
            archivosCreados++;
            
        } catch (Exception e) {
            System.out.println("❌ Error creando resumen_negocio.csv");
        }
        
        System.out.println("\n🎯 " + archivosCreados + " archivos CSV creados");
        System.out.println("\n📌 PARA SUBIR A GOOGLE SHEETS:");
        System.out.println("   1. Ve a https://sheets.new");
        System.out.println("   2. Archivo → Importar → Subir");
        System.out.println("   3. Selecciona 'inventario_completo.csv'");
        System.out.println("   4. ¡Listo! Tus datos están en la nube");
        
        esperarEnter();
    }
}