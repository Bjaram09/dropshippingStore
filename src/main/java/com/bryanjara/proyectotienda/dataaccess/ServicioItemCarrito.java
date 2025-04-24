/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dataaccess;

import Modelo.ItemCarrito;
import Modelo.Producto;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import oracle.jdbc.OracleTypes;

/**
 *
 * @author Fio
 */
public class ServicioItemCarrito extends Servicio{
    
    private static final String insertar_item_carrito = "{call insertar_item_carrito(?,?,?,?)}";
    private static final String listar_item_carrito = "{?=call listar_item_carrito()}";
    private static final String buscar_item_carrito_por_id = "{?=call buscar_item_carrito_por_id(?)}";
    private static final String modificar_item_carrito = "{call modificar_item_carrito(?,?,?,?)}";
    private static final String eliminar_item_carrito = "{call eliminar_item_carrito(?)}";
    
    public ItemCarrito[] listarItemCarrito() throws GlobalException, NoDataException, SQLException {
        ArrayList<ItemCarrito> lista = new ArrayList<>();
        ResultSet rs = null;
        CallableStatement pstmt = null;

        try {
            conectar();
            pstmt = conexion.prepareCall(listar_item_carrito);
            pstmt.registerOutParameter(1, OracleTypes.CURSOR);
            pstmt.execute();
            rs = (ResultSet) pstmt.getObject(1);

            while (rs.next()) {
                // Producto
                Producto producto = new Producto();
                producto.setId(rs.getInt("producto_id"));
                producto.setNombre(rs.getString("producto_nombre"));
                producto.setPrecio(rs.getDouble("producto_precio"));

                // ItemCarrito
                ItemCarrito item = new ItemCarrito();
                item.setId(rs.getInt("itemcarrito_id"));
                item.setProducto(producto);
                item.setCantidad(rs.getInt("cantidad"));

                lista.add(item);
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new GlobalException("Error al listar los ítems del carrito");
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                desconectar();
            } catch (SQLException e) {
                throw new GlobalException("Error al cerrar recursos");
            }
        }

        if (lista.isEmpty()) {
            throw new NoDataException("No hay ítems en el carrito.");
        }

        return lista.toArray(ItemCarrito[]::new);
    }
}
