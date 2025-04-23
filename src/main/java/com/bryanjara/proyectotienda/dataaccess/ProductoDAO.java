package com.bryanjara.proyectotienda.dataaccess;

import com.bryanjara.proyectotienda.models.Producto;
import com.bryanjara.proyectotienda.models.Vendedor;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

public class ProductoDAO extends ServicioDB {
    private static final String INSERTAR_PRODUCTO = "{CALL DROPSHIPPING.INSERTAR_PRODUCTO(?, ?, ?, ?, ?, ?, ?, ?, ?)}";
    private static final String MODIFICAR_PRODUCTO = "{CALL DROPSHIPPING.MODIFICAR_PRODUCTO(?, ?, ?, ?, ?, ?, ?, ?, ?)}";
    private static final String ELIMINAR_PRODUCTO = "{CALL DROPSHIPPING.ELIMINAR_PRODUCTO(?)}";
    private static final String LISTAR_PRODUCTO = "{?=CALL DROPSHIPPING.LISTAR_PRODUCTO()}";
    private static final String BUSCAR_PRODUCTO_POR_ID = "{?=CALL DROPSHIPPING.BUSCAR_PRODUCTO_POR_ID(?)}";

    VendedorDAO vendedorDAO;

    public ProductoDAO() {
        super();
        vendedorDAO = new VendedorDAO();
    }

    public void insertarProducto(Producto producto) throws GlobalException, NoDataException, SQLException {
        try {
            conectar();
        } catch (ClassNotFoundException e) {
            throw new GlobalException("No se ha localizado el driver");
        } catch (SQLException e) {
            throw new NoDataException("La base de datos no se encuentra disponible");
        }

        // Then verify that the Vendedor exists
        Vendedor vendedorConsultado;
        try {
            vendedorConsultado = vendedorDAO.buscarVendedor(producto.getVendedor().getCedula());
        } catch (NoDataException e) {
            throw new GlobalException("El Producto con ID " + producto.getVendedor().getCedula() + " no existe en la base de datos.");
        } catch (SQLException e) {
            throw new GlobalException("Error al consultar Producto");
        }

        try (CallableStatement pstmt = conexion.prepareCall(INSERTAR_PRODUCTO)) {
            pstmt.setString(1, producto.getNombre());
            pstmt.setString(2, producto.getCategoria());
            pstmt.setDouble(3, producto.getPrecio());
            pstmt.setDouble(4, producto.getPeso());
            pstmt.setString(5, producto.getDimensiones());
            pstmt.setString(6, producto.getDescripcion());
            pstmt.setInt(7, producto.getInventarioDisponible());
            pstmt.setString(8, producto.getVendedor().getCedula());

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new NoDataException("No se realizó la inserción del Producto.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new GlobalException("Error al insertar Producto: " + e.getMessage());
        } finally {
            desconectar();
        }
    }

    /*public void modificarProducto(Producto producto) throws GlobalException, NoDataException, SQLException {
        // Make sure that a Producto exists on my DB
        Producto productoExistente;
        try {
            productoExistente = buscarProducto(producto.getID());
        } catch (NoDataException e) {
            throw new GlobalException("El Producto con ID " + producto.getId() + " no existe en la base de datos.");
        } catch (SQLException e) {
            throw new GlobalException("Error al consultar Producto");
        }

        try {
            conectar();
        } catch (ClassNotFoundException e) {
            throw new GlobalException("No se ha localizado el driver");
        } catch (SQLException e) {
            throw new NoDataException("La base de datos no se encuentra disponible");
        }

        // Verify that the Factura exists
        try {
            facturaDAO.buscarFacturaPorId(facturaId);
        } catch (NoDataException e) {
            throw new GlobalException("La Factura con ID " + facturaId + " no existe en la base de datos.");
        } catch (SQLException e) {
            throw new GlobalException("Error al consultar Factura");
        }

        // Making sure that my producto exists on my DB
        Producto productoConsultado;
        try {
            productoConsultado = productoDAO.buscarProducto(producto.getInfoProducto().getId());
        } catch (NoDataException e) {
            throw new GlobalException("El producto con ID " + producto.getInfoProducto().getId() + " no existe en la base de datos.");
        } catch (SQLException e) {
            throw new GlobalException("Error al consultar Producto");
        }

        try (CallableStatement pstmt = conexion.prepareCall(MODIFICAR_PRODUCTO)) {
            pstmt.setString(1, productoExistente.getId());
            pstmt.setString(2, facturaId);
            pstmt.setString(3, productoConsultado.getId());
            pstmt.setInt(4, productoExistente.getCantidad());

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new NoDataException("No se realizó la modificación del Producto.");
            }
        } catch (SQLException e) {
            throw new GlobalException("Error al modificar Producto");
        } finally {
            desconectar();
        }
    }

    public void eliminarProducto(String id) throws GlobalException, NoDataException, SQLException {
        try {
            conectar();
        } catch (ClassNotFoundException e) {
            throw new GlobalException("No se ha localizado el driver");
        } catch (SQLException e) {
            throw new NoDataException("La base de datos no se encuentra disponible");
        }

        try {
            buscarProducto(id);
        } catch (NoDataException e) {
            throw new GlobalException("El producto con ID " + id + " no existe en la base de datos.");
        } catch (SQLException e) {
            throw new GlobalException("Error al consultar Producto");
        }

        try (CallableStatement pstmt = conexion.prepareCall(ELIMINAR_PRODUCTO)) {
            pstmt.setString(1, id);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new NoDataException("No se eliminó el producto. Puede estar referenciado en otras tablas.");
            } else {
                System.out.println("Se eliminó el producto con ID " + id + " correctamente.");
            }
        } catch (SQLException e) {
            throw new GlobalException("Sentencia no válida");
        } finally {
            desconectar();
        }
    }

    public Collection<Producto> listarProducto() throws GlobalException, NoDataException, SQLException {
        try {
            conectar();
        } catch (ClassNotFoundException e) {
            throw new GlobalException("No se ha localizado el Driver");
        } catch (SQLException e) {
            throw new NoDataException("La base de datos no se encuentra disponible");
        }

        ArrayList<Producto> coleccion = new ArrayList<>();

        try (CallableStatement pstmt = conexion.prepareCall(LISTAR_PRODUCTO)) {
            pstmt.registerOutParameter(1, OracleTypes.CURSOR);
            pstmt.execute();
            try (ResultSet rs = (ResultSet) pstmt.getObject(1)) {
                while (rs.next()) {
                    String productoId = rs.getString("PRODUCTOID");
                    Producto productoConsultado = productoDAO.buscarProducto(productoId);

                    Producto producto = new Producto(
                            rs.getString("ID"),
                            productoConsultado,
                            rs.getInt("CANTIDAD")
                    );
                    coleccion.add(producto);
                }
            }
        } catch (SQLException e) {
            throw new GlobalException("Error al ejecutar consulta");
        } finally {
            desconectar();
        }

        if (coleccion.isEmpty()) {
            throw new NoDataException("No hay datos disponibles.");
        }

        return coleccion;
    }

    public Producto buscarProducto(String id) throws GlobalException, NoDataException, SQLException {
        try {
            conectar();
        } catch (ClassNotFoundException e) {
            throw new GlobalException("No se ha localizado el driver");
        } catch (SQLException e) {
            throw new NoDataException("La base de datos no se encuentra disponible");
        }

        Producto producto = null;
        try (CallableStatement pstmt = conexion.prepareCall(BUSCAR_PRODUCTO_POR_ID)) {
            pstmt.registerOutParameter(1, OracleTypes.CURSOR);
            pstmt.setString(2, id);
            pstmt.execute();

            try (ResultSet rs = (ResultSet) pstmt.getObject(1)) {
                if (rs.next()) {
                    String productoId = rs.getString("PRODUCTOID");
                    Producto productoConsultado = productoDAO.buscarProducto(productoId);

                    producto = new Producto(
                            rs.getString("ID"),
                            productoConsultado,
                            rs.getInt("CANTIDAD")
                    );
                }
            }
        } catch (SQLException e) {
            throw new GlobalException("Sentencia no válida");
        } finally {
            desconectar();
        }

        if (producto == null) {
            throw new NoDataException("No hay datos");
        }

        return producto;
    }*/

}
