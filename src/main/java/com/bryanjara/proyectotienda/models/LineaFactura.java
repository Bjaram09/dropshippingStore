package Modelo;

/**
 *
 * @author Fio
 */
public class LineaFactura{
    private int id;
    private ItemCarrito itemCarrito;
    private double montoTotal;
    
    public LineaFactura(){
        this.id = 0;
        this.itemCarrito = new ItemCarrito();
        this.montoTotal = 0;
    }
    
    public LineaFactura(int id, ItemCarrito itemCarrito){
        this.id = id;
        this.itemCarrito = itemCarrito;
        double precio = itemCarrito.getProducto().getPrecio();
        int cantidad = itemCarrito.getCantidad();
        this.montoTotal = cantidad * precio;
    }
    public int getId(){
        return id;
    }
    
    public void setId(int id){
        this.id = id;
    }
    
    public ItemCarrito getItemCarrito(){
        return itemCarrito;
    }
    
    public void setItemCarrito(ItemCarrito itemCarrito){
        this.itemCarrito = itemCarrito;
    }
    
    public double getMontoTotal(){
        return montoTotal;
    }
    
    public void setMontoTotal(double montoTotal){
        this.montoTotal = montoTotal;
    }
    
    @Override
    public String toString(){
        return """
               LineaFactura
               Id:  """ + id +
        " \n Items registrados en el carrito: " + itemCarrito +
        " \n Monto Total: " + montoTotal +
        ' ';
   
    }
}
