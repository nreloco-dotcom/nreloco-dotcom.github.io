import java.util.*;
import java.text.SimpleDateFormat;

public class SistemaGanancias {
    private List<Venta> ventas;
    private double inversionInicial;
    
    public SistemaGanancias(double inversionInicial) {
        this.ventas = new ArrayList<>();
        this.inversionInicial = inversionInicial;
    }
    
    public double getInversionInicial() {
        return inversionInicial;
    }
    
    public void registrarVenta(Venta venta) {
        if (venta != null) {
            ventas.add(venta);
        }
    }
    
    // ====================
    // REPORTES DE GANANCIAS
    // ====================
    
    public double getTotalVentas() {
        double total = 0;
        for (Venta v : ventas) total += v.getTotal();
        return total;
    }
    
    public double getGananciaTotal() {
        double total = 0;
        for (Venta v : ventas) total += v.getGanancia();
        return total;
    }
    
    public double getGananciaHoy() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String hoy = sdf.format(new Date());
        
        double total = 0;
        for (Venta v : ventas) {
            if (sdf.format(v.getFecha()).equals(hoy)) {
                total += v.getGanancia();
            }
        }
        return total;
    }
    
    public double getBalanceReal() {
        return getGananciaTotal() - inversionInicial;
    }
    
    public int getTotalVentasHoy() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String hoy = sdf.format(new Date());
        
        int count = 0;
        for (Venta v : ventas) {
            if (sdf.format(v.getFecha()).equals(hoy)) count++;
        }
        return count;
    }
    
    public void mostrarResumenCompleto() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("📊 RESUMEN FINANCIERO");
        System.out.println("=".repeat(50));
        
        System.out.printf("💰 Inversión inicial: $%.2f%n", inversionInicial);
        System.out.printf("📈 Total en ventas:   $%.2f%n", getTotalVentas());
        System.out.printf("💵 Ganancia total:    $%.2f%n", getGananciaTotal());
        System.out.printf("📅 Ganancia hoy:      $%.2f%n", getGananciaHoy());
        System.out.printf("🏦 Balance real:      $%.2f%n", getBalanceReal());
        System.out.printf("🛒 Ventas hoy:        %d transacciones%n", getTotalVentasHoy());
        
        System.out.println("-".repeat(50));
        
        if (getBalanceReal() >= 0) {
            System.out.println("✅ NEGOCIO RENTABLE");
        } else {
            System.out.println("⚠️  NEGOCIO EN PÉRDIDA");
        }
        
        mostrarTopProductos();
    }
    
    private void mostrarTopProductos() {
        if (ventas.isEmpty()) return;
        
        Map<String, Double> gananciaPorProducto = new HashMap<>();
        
        for (Venta v : ventas) {
            String nombre = v.getProducto().getNombre();
            double ganancia = v.getGanancia();
            gananciaPorProducto.put(nombre, gananciaPorProducto.getOrDefault(nombre, 0.0) + ganancia);
        }
        
        System.out.println("\n🏆 TOP PRODUCTOS MÁS RENTABLES:");
        gananciaPorProducto.entrySet().stream()
            .sorted((a, b) -> Double.compare(b.getValue(), a.getValue()))
            .limit(3)
            .forEach(entry -> {
                System.out.printf("   • %-30s: $%.2f%n", entry.getKey(), entry.getValue());
            });
    }
    
    public List<Venta> getVentas() {
        return new ArrayList<>(ventas);
    }
}