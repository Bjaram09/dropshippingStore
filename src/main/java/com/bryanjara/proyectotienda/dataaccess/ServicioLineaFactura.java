package com.bryanjara.proyectotienda.dataaccess;

import com.bryanjara.proyectotienda.models.*;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import oracle.jdbc.internal.OracleTypes;
import java.sql.Types;

/**
 *
 * @author Fio
 */
public class ServicioLineaFactura extends ServicioDB {

    private static final String insertar_linea_factura = "{call insertar_linea_factura (?,?,?)}";
    private static final String listar_lineas_factura = "{?=call listar_lineas_factura()}";
    private static final String buscar_linea_factura_por_id = "{?=call buscar_linea_factura_por_id(?)}";
    private static final String modificar_linea_factura = "{call modificar_linea_factura (?,?,?)}";
    private static final String eliminar_linea_factura = "{call eliminar_linea_factura(?)}";

    /**
     * Creates a new instance of ServicioLibro
     */
    public ServicioLineaFactura() {
    }

    public Collection listar_lineas_factura() throws GlobalException, NoDataException {
        try {
            conectar();
        } catch (ClassNotFoundException ex) {
            throw new GlobalException("No se ha localizado el Driver");
        } catch (SQLException e) {
            throw new NoDataException("La base de datos no se encuentra disponible");
        }

        ResultSet rs = null;
        ArrayList<LineaFactura> coleccion = new ArrayList<>();
        CallableStatement pstmt = null;

        try {
            pstmt = conexion.prepareCall(listar_lineas_factura);
            pstmt.registerOutParameter(1, OracleTypes.CURSOR);
            pstmt.execute();
            rs = (ResultSet) pstmt.getObject(1);

            while (rs.next()) {
                // Construir Producto
                Producto producto = new Producto();
                producto.setNombre(rs.getString("producto_nombre"));
                producto.setPrecio(rs.getDouble("precio_individual"));

                // Construir ItemCarrito
                ItemCarrito itemCarrito = new ItemCarrito();
                itemCarrito.setProducto(producto);
                itemCarrito.setCantidad(rs.getInt("cantidad"));

                // Construir LineaFactura
                LineaFactura lineaFactura = new LineaFactura();
                lineaFactura.setId(rs.getInt("linea_id"));
                lineaFactura.setItemCarrito(itemCarrito);
                lineaFactura.setMontoTotal(rs.getDouble("montototal"));

                coleccion.add(lineaFactura);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new GlobalException("Sentencia no válida");
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
                desconectar();
            } catch (SQLException e) {
                throw new GlobalException("Estatutos inválidos o nulos");
            }
        }

        if (coleccion.isEmpty()) {
            throw new NoDataException("No hay datos");
        }

        return coleccion;
    }

    public void insertarLineaFactura(LineaFactura lineaFactura) throws GlobalException, NoDataException, ClassNotFoundException {
    CallableStatement pstmt = null;

    try {
        conectar();

        pstmt = conexion.prepareCall(insertar_linea_factura);

        pstmt.setInt(1, lineaFactura.getItemCarrito().getId());
        pstmt.setDouble(2, lineaFactura.getMontoTotal());
        pstmt.registerOutParameter(3, Types.INTEGER); 

        pstmt.execute();

        int idGenerado = pstmt.getInt(3);
        lineaFactura.setId(idGenerado);

    } catch (SQLException | ClassNotFoundException e) {
        e.printStackTrace();
        throw new GlobalException("Error al insertar línea de factura: " + e.getMessage());
    } finally {
        try {
            if (pstmt != null) {
                pstmt.close();
            }
            desconectar();
        } catch (SQLException e) {
            throw new GlobalException("Error al cerrar conexión");
        }
    }
}

    public void eliminarLineaFactura(int id) throws GlobalException, NoDataException {
        try {
            conectar();
        } catch (ClassNotFoundException e) {
            throw new GlobalException("No se ha localizado el driver");
        } catch (SQLException e) {
            throw new NoDataException("La base de datos no se encuentra disponible");
        }

        CallableStatement pstmt = null;
        try {
            pstmt = conexion.prepareCall(eliminar_linea_factura);
            pstmt.setInt(1, id);

            int resultado = pstmt.executeUpdate();

            if (resultado == 0) {
                throw new NoDataException("No se realizo el borrado");
            } else {
                System.out.println("\nEliminacion Satisfactoria!");
            }
        } catch (SQLException e) {
            throw new GlobalException("Sentencia no valida");
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                desconectar();
            } catch (SQLException e) {
                throw new GlobalException("Estatutos invalidos o nulos");
            }
        }
    }

    public LineaFactura buscarLineaFactura(int id) throws GlobalException, NoDataException {
        try {
            conectar();
        } catch (ClassNotFoundException e) {
            throw new GlobalException("No se ha localizado el driver");
        } catch (SQLException e) {
            throw new NoDataException("La base de datos no se encuentra disponible");
        }

        ResultSet rs = null;
        LineaFactura lineaFactura = null;
        CallableStatement pstmt = null;

        try {
            pstmt = conexion.prepareCall(buscar_linea_factura_por_id);
            pstmt.registerOutParameter(1, OracleTypes.CURSOR);
            pstmt.setInt(2, id);
            pstmt.execute();

            rs = (ResultSet) pstmt.getObject(1);
            if (rs.next()) {
                Producto producto = new Producto();
                producto.setNombre(rs.getString("producto_nombre"));
                producto.setPrecio(rs.getDouble("precio_individual"));

                ItemCarrito itemCarrito = new ItemCarrito();
                itemCarrito.setProducto(producto);
                itemCarrito.setCantidad(rs.getInt("cantidad"));

                lineaFactura = new LineaFactura();
                lineaFactura.setId(rs.getInt("linea_id"));
                lineaFactura.setItemCarrito(itemCarrito);
                lineaFactura.setMontoTotal(rs.getDouble("montototal"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new GlobalException("Sentencia no válida");
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
                desconectar();
            } catch (SQLException e) {
                throw new GlobalException("Estatutos inválidos o nulos");
            }
        }

        return lineaFactura;
    }
}
