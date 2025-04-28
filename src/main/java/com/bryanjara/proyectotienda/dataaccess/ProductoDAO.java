package com.bryanjara.proyectotienda.dataaccess;

import com.bryanjara.proyectotienda.models.Producto;
import com.bryanjara.proyectotienda.models.Vendedor;

import oracle.jdbc.internal.OracleTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ProductoDAO extends ServicioDB {
    private static final String INSERTAR_PRODUCTO = "{CALL DROPSHIPPING.INSERTAR_PRODUCTO(?, ?, ?, ?, ?, ?, ?, ?, ?)}";
    private static final String MODIFICAR_PRODUCTO = "{CALL DROPSHIPPING.MODIFICAR_PRODUCTO(?, ?, ?, ?, ?, ?, ?, ?, ?)}";
    private static final String ELIMINAR_PRODUCTO = "{CALL DROPSHIPPING.ELIMINAR_PRODUCTO(?)}";
    private static final String LISTAR_PRODUCTO = "SELECT DROPSHIPPING.LISTAR_PRODUCTO FROM DUAL";
    private static final String BUSCAR_PRODUCTO_POR_ID = "SELECT DROPSHIPPING.BUSCAR_PRODUCTO_POR_ID(?) FROM DUAL";
    private static final String BUSCAR_PRODUCTO_POR_CRITERIO = "{? = CALL DROPSHIPPING.BUSCAR_PROD_POR_CRITERIO(?)}";

    VendedorDAO vendedorDAO;

    public ProductoDAO() {
        super();
        vendedorDAO = new VendedorDAO();
    }

    public void insertarProducto(Producto producto) throws GlobalException, NoDataException, SQLException {
        Vendedor vendedorConsultado;
        try {
            vendedorConsultado = vendedorDAO.buscarVendedor(producto.getVendedor().getCedula());
        } catch (NoDataException e) {
            throw new GlobalException("El Vendedor con ID " + producto.getVendedor().getCedula() + " no existe en la base de datos.");
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

        try (CallableStatement pstmt = conexion.prepareCall(INSERTAR_PRODUCTO)) {
            pstmt.setInt(1, producto.getID());
            pstmt.setString(2, producto.getNombre());
            pstmt.setString(3, producto.getCategoria());
            pstmt.setDouble(4, producto.getPrecio());
            pstmt.setDouble(5, producto.getPeso());
            pstmt.setString(6, producto.getDimensiones());
            pstmt.setString(7, producto.getDescripcion());
            pstmt.setInt(8, producto.getInventarioDisponible());
            pstmt.setString(9, producto.getVendedor().getCedula());

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

    public void modificarProducto(Producto producto) throws GlobalException, NoDataException, SQLException {
        Producto productoExistente;
        try {
            productoExistente = buscarProducto(producto.getID());
        } catch (NoDataException e) {
            throw new GlobalException("El producto con ID " + producto.getID() + " no existe en la base de datos.");
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

        try (CallableStatement pstmt = conexion.prepareCall(MODIFICAR_PRODUCTO)) {
            pstmt.setInt(1, producto.getID());
            pstmt.setString(2, producto.getNombre());
            pstmt.setString(3, producto.getCategoria());
            pstmt.setDouble(4, producto.getPrecio());
            pstmt.setDouble(5, producto.getPeso());
            pstmt.setString(6, producto.getDimensiones());
            pstmt.setString(7, producto.getDescripcion());
            pstmt.setInt(8, producto.getInventarioDisponible());
            pstmt.setString(9, producto.getVendedor().getCedula());

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new NoDataException("No se realizó la modificación del Producto.");
            }
        } catch (SQLException e) {
            throw new GlobalException("Error al modificar Producto: " + e.getMessage());
        } finally {
            desconectar();
        }
    }

    public void eliminarProducto(int id) throws GlobalException, NoDataException, SQLException {
        try {
            conectar();
        } catch (ClassNotFoundException e) {
            throw new GlobalException("No se ha localizado el driver");
        } catch (SQLException e) {
            throw new NoDataException("La base de datos no se encuentra disponible");
        }

        try (CallableStatement pstmt = conexion.prepareCall(ELIMINAR_PRODUCTO)) {
            pstmt.setInt(1, id);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new NoDataException("No se eliminó el producto. Puede estar referenciado en otras tablas.");
            }
        } catch (SQLException e) {
            throw new GlobalException("Error al eliminar Producto: " + e.getMessage());
        } finally {
            desconectar();
        }
    }

    public Collection<Producto> listarProductos() throws GlobalException, NoDataException, SQLException {
        try {
            conectar();
        } catch (ClassNotFoundException e) {
            throw new GlobalException("No se ha localizado el Driver");
        } catch (SQLException e) {
            throw new NoDataException("La base de datos no se encuentra disponible");
        }

        ArrayList<Producto> coleccion = new ArrayList<>();

        try (PreparedStatement pstmt = conexion.prepareStatement(LISTAR_PRODUCTO); ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    ResultSet productosCursor = (ResultSet) rs.getObject(1);
                    while(productosCursor.next()){

                        String vendedorCedula = productosCursor.getString("VENDEDORCEDULA");
                        Vendedor vendedorConsultado = vendedorDAO.buscarVendedor(vendedorCedula);

                        Producto producto = new Producto(
                            productosCursor.getInt("ID"),
                            productosCursor.getString("NOMBRE"),
                            productosCursor.getString("CATEGORIA"),
                            productosCursor.getDouble("PRECIO"),
                            productosCursor.getDouble("PESO"),
                            productosCursor.getString("DIMENSIONES"),
                            productosCursor.getString("DESCRIPCION"),
                            productosCursor.getInt("INVENTARIODISPONIBLE"),
                            vendedorConsultado
                        );
                    coleccion.add(producto);
                }
            }
        } catch (SQLException e) {
            throw new GlobalException("Error al ejecutar consulta: " + e.getMessage());
        } finally {
            desconectar();
        }

        if (coleccion.isEmpty()) {
            throw new NoDataException("No hay datos disponibles.");
        }

        return coleccion;
    }

    public Producto buscarProducto(int id) throws GlobalException, NoDataException, SQLException {
        try {
            conectar();
        } catch (ClassNotFoundException e) {
            throw new GlobalException("No se ha localizado el driver");
        } catch (SQLException e) {
            throw new NoDataException("La base de datos no se encuentra disponible");
        }

        Producto producto = null;

        try (PreparedStatement pstmt = conexion.prepareStatement(BUSCAR_PRODUCTO_POR_ID)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    ResultSet productoCursor = (ResultSet) rs.getObject(1);
                    if (productoCursor.next()) {
                        String vendedorCedula = productoCursor.getString("VENDEDORCEDULA");
                        Vendedor vendedorConsultado = vendedorDAO.buscarVendedor(vendedorCedula);

                        producto = new Producto(
                            productoCursor.getInt("ID"),
                            productoCursor.getString("NOMBRE"),
                            productoCursor.getString("CATEGORIA"),
                            productoCursor.getDouble("PRECIO"),
                            productoCursor.getDouble("PESO"),
                            productoCursor.getString("DIMENSIONES"),
                            productoCursor.getString("DESCRIPCION"),
                            productoCursor.getInt("INVENTARIODISPONIBLE"),
                            vendedorConsultado
                        );
                    }
                }
            }
        } catch (SQLException e) {
            throw new GlobalException("Sentencia no válida " + e);
        } finally {
            desconectar();
        }

        if (producto == null) {
            throw new NoDataException("No hay datos");
        }

        return producto;
    }

    public Collection<Producto> buscarProductoPorCriterio(String criterio) throws GlobalException, NoDataException, SQLException {
        try {
            conectar();
        } catch (ClassNotFoundException e) {
            throw new GlobalException("No se ha localizado el driver");
        } catch (SQLException e) {
            throw new NoDataException("La base de datos no se encuentra disponible");
        }

        try (CallableStatement stmt = conexion.prepareCall(BUSCAR_PRODUCTO_POR_CRITERIO)) {
            stmt.registerOutParameter(1, OracleTypes.CURSOR);
            stmt.setString(2, criterio); 

            stmt.execute();

            try (ResultSet rs = (ResultSet) stmt.getObject(1)) {
                List<Producto> productos = new ArrayList<>();
                while (rs.next()) {
                    String vendedorCedula = rs.getString("VENDEDORCEDULA");
                    Vendedor vendedorConsultado = vendedorDAO.buscarVendedor(vendedorCedula);

                    Producto producto = new Producto();
                    producto.setID(rs.getInt("ID"));
                    producto.setNombre(rs.getString("NOMBRE"));
                    producto.setCategoria(rs.getString("CATEGORIA"));
                    producto.setPrecio(rs.getDouble("PRECIO"));
                    producto.setPeso(rs.getDouble("PESO"));
                    producto.setDimensiones(rs.getString("DIMENSIONES"));
                    producto.setDescripcion(rs.getString("DESCRIPCION"));
                    producto.setInventarioDisponible(rs.getInt("INVENTARIODISPONIBLE"));
                    producto.setVendedor(vendedorConsultado);

                    productos.add(producto);
                }
                return productos;
            }
        } catch (SQLException e) {
            throw new GlobalException("Error al buscar productos: " + e.getMessage());
        } finally {
            desconectar();
        }
    }
}