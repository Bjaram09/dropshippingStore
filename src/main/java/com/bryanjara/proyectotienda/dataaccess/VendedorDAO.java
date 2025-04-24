package com.bryanjara.proyectotienda.dataaccess;

import com.bryanjara.proyectotienda.models.Vendedor;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

public class VendedorDAO extends ServicioDB {
    private static final String INSERTAR_VENDEDOR = "{CALL DROPSHIPPING.INSERTAR_VENDEDOR(?, ?, ?, ?, ?)}";
    private static final String MODIFICAR_VENDEDOR = "{CALL DROPSHIPPING.MODIFICAR_VENDEDOR(?, ?, ?, ?, ?)}";
    private static final String ELIMINAR_VENDEDOR = "{CALL DROPSHIPPING.ELIMINAR_VENDEDOR(?)}";
    private static final String LISTAR_VENDEDOR = "SELECT DROPSHIPPING.LISTAR_VENDEDOR FROM DUAL";
    private static final String BUSCAR_VENDEDOR_POR_ID = "SELECT DROPSHIPPING.BUSCAR_VENDEDOR_POR_CEDULA(?) FROM DUAL";

    public VendedorDAO() {
        super();
    }

    private void setVendedorParameters(CallableStatement pstmt, Vendedor vendedor) throws SQLException {
        try {
            pstmt.setString(1, vendedor.getCedula());
            pstmt.setString(2, vendedor.getNombre());
            pstmt.setString(3, vendedor.getUbicacion());
            pstmt.setString(4, vendedor.getCorreoContacto());
            pstmt.setString(5, vendedor.getNumeroTelefono());
        } catch (SQLException e) {
            throw new IllegalArgumentException("Error setting parameters for CallableStatement");
        }
    }

    public void insertarVendedor(Vendedor vendedor) throws GlobalException, NoDataException, SQLException {
        try {
            conectar();
        } catch (ClassNotFoundException e) {
            throw new GlobalException("No se ha localizado el driver");
        } catch (SQLException e) {
            throw new NoDataException("La base de datos no se encuentra disponible");
        }

        try (CallableStatement pstmt = conexion.prepareCall(INSERTAR_VENDEDOR)) {
            setVendedorParameters(pstmt, vendedor);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new NoDataException("No se realizó la operación en el Vendedor.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new GlobalException("Llave duplicada");
        } finally {
            desconectar();
        }
    }

    public void modificarVendedor(Vendedor vendedor) throws GlobalException, NoDataException, SQLException {
        Vendedor vendedorExistente;
        try {
            vendedorExistente = buscarVendedor(vendedor.getCedula());
        } catch (NoDataException e) {
            throw new GlobalException("El Vendedor con ID " + vendedor.getCedula() + " no existe en la base de datos.");
        } catch (SQLException e) {
            throw new GlobalException("Error al consultar Vendedor");
        }

        try {
            conectar();
        } catch (ClassNotFoundException e) {
            throw new GlobalException("No se ha localizado el driver");
        } catch (SQLException e) {
            throw new NoDataException("La base de datos no se encuentra disponible");
        }

        try (CallableStatement pstmt = conexion.prepareCall(MODIFICAR_VENDEDOR)) {
            setVendedorParameters(pstmt, vendedor);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new NoDataException("No se realizó la modificación del Vendedor.");
            } else {
                System.out.println("Vendedor modificado exitosamente con ID: " + vendedor.getCedula());
            }
        }
        catch (SQLException e) {
            throw new GlobalException("Sentencia no valida");
        } finally {
            desconectar();
        }
    }

    public void eliminarVendedor(String cedula) throws GlobalException, NoDataException, SQLException {
        try {
            buscarVendedor(cedula);
        } catch (NoDataException e) {
            throw new GlobalException("El vendedor con ID " + cedula + " no existe en la base de datos.");
        } catch (SQLException e) {
            throw new GlobalException("Error al consultar Vendedor");
        }

        try {
            conectar();
        } catch (ClassNotFoundException e) {
            throw new GlobalException("No se ha localizado el driver");
        } catch (SQLException e) {
            throw new NoDataException("La base de datos no se encuentra disponible");
        }

        try (CallableStatement pstmt = conexion.prepareCall(ELIMINAR_VENDEDOR)) {
            pstmt.setString(1, cedula);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new NoDataException("No se eliminó el vendedor. Puede estar referenciado en otras tablas.");
            }
        } catch (SQLException e) {
            throw new SQLException("Error al eliminar Vendedor " + e);
        } finally {
            desconectar();
        }
    }

    public Collection<Vendedor> listarVendedor() throws GlobalException, NoDataException, SQLException {
        try {
            conectar();
        } catch (ClassNotFoundException e) {
            throw new GlobalException("No se ha localizado el Driver");
        } catch (SQLException e) {
            throw new NoDataException("La base de datos no se encuentra disponible: " + e.getMessage());
        }

        ArrayList<Vendedor> coleccion = new ArrayList<>();

        try (PreparedStatement pstmt = conexion.prepareStatement(LISTAR_VENDEDOR); ResultSet rs = pstmt.executeQuery()){
            while (rs.next()) {
                ResultSet vendedoresCursor = (ResultSet) rs.getObject(1);
                while (vendedoresCursor.next()) {
                    Vendedor vendedor = new Vendedor(
                            vendedoresCursor.getString("CEDULA"),
                            vendedoresCursor.getString("NOMBRE"),
                            vendedoresCursor.getString("UBICACION"),
                            vendedoresCursor.getString("CORREOCONTACTO"),
                            vendedoresCursor.getString("NUMEROTELEFONO")
                    );
                    coleccion.add(vendedor);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new GlobalException("Error al ejecutar consulta: " + e.getMessage());
        } finally {
            desconectar();
        }

        if (coleccion.isEmpty()) {
            throw new NoDataException("No hay datos disponibles.");
        }

        return coleccion;
    }

    public Vendedor buscarVendedor(String id) throws GlobalException, NoDataException, SQLException {
        try {
            conectar();
        } catch (ClassNotFoundException e) {
            throw new GlobalException("No se ha localizado el driver");
        } catch (SQLException e) {
            throw new NoDataException("La base de datos no se encuentra disponible");
        }

        Vendedor vendedor = null;

        try (PreparedStatement pstmt = conexion.prepareStatement(BUSCAR_VENDEDOR_POR_ID)) {
            pstmt.setString(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    ResultSet vendedorCursor = (ResultSet) rs.getObject(1);
                    if (vendedorCursor.next()) {
                        vendedor = new Vendedor(
                                vendedorCursor.getString("CEDULA"),
                                vendedorCursor.getString("NOMBRE"),
                                vendedorCursor.getString("UBICACION"),
                                vendedorCursor.getString("CORREOCONTACTO"),
                                vendedorCursor.getString("NUMEROTELEFONO")
                        );
                    }
                }
            }
        } catch (SQLException e) {
            throw new GlobalException("Sentencia no válida " + e);
        } finally {
            desconectar();
        }

        if (vendedor == null) {
            throw new NoDataException("No hay datos");
        }

        return vendedor;
    }
}