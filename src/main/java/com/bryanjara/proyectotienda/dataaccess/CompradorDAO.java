package com.bryanjara.proyectotienda.dataaccess;
import com.bryanjara.proyectotienda.models.Comprador;

import oracle.jdbc.internal.OracleTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class CompradorDAO extends ServicioDB {
    private static final String INSERTAR_COMPRADOR = "{CALL DROPSHIPPING.INSERTAR_COMPRADOR(?, ?, ?, ?, ?, ?)}";
    private static final String MODIFICAR_COMPRADOR = "{CALL DROPSHIPPING.MODIFICAR_COMPRADOR(?, ?, ?, ?, ?, ?)}";
    private static final String ELIMINAR_COMPRADOR = "{CALL DROPSHIPPING.ELIMINAR_COMPRADOR(?)}";
    private static final String LISTAR_COMPRADOR = "SELECT DROPSHIPPING.LISTAR_COMPRADORES FROM DUAL";
    private static final String BUSCAR_COMPRADOR_POR_ID = "SELECT DROPSHIPPING.BUSCAR_COMPRADOR_POR_CEDULA(?) FROM DUAL";
    private static final String BUSCAR_COMPRADOR_POR_NOMBRE = "SELECT DROPSHIPPING.BUSCAR_COMPRADOR_POR_NOMBREUS(?) FROM DUAL";
    private static final String BUSCAR_COMPRADOR_POR_CRITERIO = "{? = CALL DROPSHIPPING.BUSCAR_COMP_POR_CRITERIO(?)}";

    public CompradorDAO() {
        super();
    }

    private void setCompradorParameters(CallableStatement pstmt, Comprador comprador) throws SQLException {
        try {
            pstmt.setString(1, comprador.getCedulaIdentidad());
            pstmt.setString(2, comprador.getNombreUsuario());
            pstmt.setString(3, comprador.getNombreCompleto());

            System.out.println("CompradorDAO: line 32 - " + comprador.getFechaNacimiento());
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            try {
                java.util.Date utilDate = sdf.parse(comprador.getFechaNacimiento());
                java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

                System.out.println("CompradorDAO: line 38 - " + sqlDate);

                pstmt.setDate(4, sqlDate);
            } catch (ParseException e) {
                throw new IllegalArgumentException("Invalid date format. Expected dd/MM/yyyy");
            }

            pstmt.setString(5, comprador.getCorreoElectronico());
            pstmt.setString(6, comprador.getContrasenia());
        } catch (SQLException e) {
            throw new IllegalArgumentException("Error setting parameters for CallableStatement");
        }
    }

    public void insertarComprador(Comprador comprador) throws GlobalException, NoDataException, SQLException {
        try {
            conectar();
        } catch (ClassNotFoundException e) {
            throw new GlobalException("No se ha localizado el driver");
        } catch (SQLException e) {
            throw new NoDataException("La base de datos no se encuentra disponible");
        }

        try (CallableStatement pstmt = conexion.prepareCall(INSERTAR_COMPRADOR)) {
            setCompradorParameters(pstmt, comprador);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new NoDataException("No se realizó la operación en el Comprador.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new GlobalException("Llave duplicada");
        } finally {
            desconectar();
        }
    }

    public void modificarComprador(Comprador comprador) throws GlobalException, NoDataException, SQLException {
        Comprador compradorExistente;
        try {
            compradorExistente = buscarComprador(comprador.getCedulaIdentidad());
        } catch (NoDataException e) {
            throw new GlobalException("El Comprador con ID " + comprador.getCedulaIdentidad() + " no existe en la base de datos.");
        } catch (SQLException e) {
            throw new GlobalException("Error al consultar Comprador");
        }

        try {
            conectar();
        } catch (ClassNotFoundException e) {
            throw new GlobalException("No se ha localizado el driver");
        } catch (SQLException e) {
            throw new NoDataException("La base de datos no se encuentra disponible");
        }

        try (CallableStatement pstmt = conexion.prepareCall(MODIFICAR_COMPRADOR)) {
            setCompradorParameters(pstmt, comprador);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new NoDataException("No se realizó la modificación del Comprador.");
            } else {
                System.out.println("Comprador modificado exitosamente con ID: " + comprador.getCedulaIdentidad());
            }
        }
        catch (SQLException e) {
            throw new GlobalException("Sentencia no valida");
        } finally {
            desconectar();
        }
    }

    public void eliminarComprador(String cedula) throws GlobalException, NoDataException, SQLException {
        try {
            buscarComprador(cedula);
        } catch (NoDataException e) {
            throw new GlobalException("El comprador con ID " + cedula + " no existe en la base de datos.");
        } catch (SQLException e) {
            throw new GlobalException("Error al consultar Comprador");
        }

        try {
            conectar();
        } catch (ClassNotFoundException e) {
            throw new GlobalException("No se ha localizado el driver");
        } catch (SQLException e) {
            throw new NoDataException("La base de datos no se encuentra disponible");
        }

        try (CallableStatement pstmt = conexion.prepareCall(ELIMINAR_COMPRADOR)) {
            pstmt.setString(1, cedula);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new NoDataException("No se eliminó el comprador. Puede estar referenciado en otras tablas.");
            }
        } catch (SQLException e) {
            throw new SQLException("Error al eliminar Comprador " + e);
        } finally {
            desconectar();
        }
    }

    public Collection<Comprador> listarComprador() throws GlobalException, NoDataException, SQLException {
        try {
            conectar();
        } catch (ClassNotFoundException e) {
            throw new GlobalException("No se ha localizado el Driver");
        } catch (SQLException e) {
            throw new NoDataException("La base de datos no se encuentra disponible: " + e.getMessage());
        }

        ArrayList<Comprador> coleccion = new ArrayList<>();

        try (PreparedStatement pstmt = conexion.prepareStatement(LISTAR_COMPRADOR); ResultSet rs = pstmt.executeQuery()){
            while (rs.next()) {
                ResultSet compradoresCursor = (ResultSet) rs.getObject(1);
                while (compradoresCursor.next()) {
                    String fechaNacimiento = compradoresCursor.getString("FECHANACIMIENTO");
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    try {
                        Date date = new SimpleDateFormat("yyyy-MM-dd").parse(fechaNacimiento);
                        fechaNacimiento = sdf.format(date);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    Comprador comprador = new Comprador(
                            compradoresCursor.getString("NOMBREUSUARIO"),
                            compradoresCursor.getString("NOMBRECOMPLETO"),
                            compradoresCursor.getString("CEDULAIDENTIDAD"),
                            fechaNacimiento,
                            compradoresCursor.getString("CORREOELECTRONICO"),
                            compradoresCursor.getString("CONTRASENIA")
                    );
                    coleccion.add(comprador);
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

    public Comprador buscarComprador(String id) throws GlobalException, NoDataException, SQLException {
        try {
            conectar();
        } catch (ClassNotFoundException e) {
            throw new GlobalException("No se ha localizado el driver");
        } catch (SQLException e) {
            throw new NoDataException("La base de datos no se encuentra disponible");
        }

        Comprador comprador = null;

        try (PreparedStatement pstmt = conexion.prepareStatement(BUSCAR_COMPRADOR_POR_ID)) {
            pstmt.setString(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    ResultSet compradorCursor = (ResultSet) rs.getObject(1);
                    if (compradorCursor.next()) {
                        String fechaNacimiento = compradorCursor.getString("FECHANACIMIENTO");
                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                        try {
                            Date date = new SimpleDateFormat("yyyy-MM-dd").parse(fechaNacimiento);
                            fechaNacimiento = sdf.format(date);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        comprador = new Comprador(
                                compradorCursor.getString("CEDULAIDENTIDAD"),
                                compradorCursor.getString("NOMBREUSUARIO"),
                                compradorCursor.getString("NOMBRECOMPLETO"),
                                fechaNacimiento,
                                compradorCursor.getString("CORREOELECTRONICO"),
                                compradorCursor.getString("CONTRASENIA")
                        );
                    }
                }
            }
        } catch (SQLException e) {
            throw new GlobalException("Sentencia no válida " + e);
        } finally {
            desconectar();
        }

        if (comprador == null) {
            throw new NoDataException("No hay datos");
        }

        return comprador;
    }

    public Comprador buscarCompradorPorNombreUsuario(String username) throws GlobalException, NoDataException, SQLException {
        try {
            conectar();
        } catch (ClassNotFoundException e) {
            throw new GlobalException("No se ha localizado el driver");
        } catch (SQLException e) {
            throw new NoDataException("La base de datos no se encuentra disponible");
        }

        Comprador comprador = null;

        try (PreparedStatement pstmt = conexion.prepareStatement(BUSCAR_COMPRADOR_POR_NOMBRE)) {
            pstmt.setString(1, username);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    ResultSet compradorCursor = (ResultSet) rs.getObject(1);
                    if (compradorCursor.next()) {
                        String fechaNacimiento = compradorCursor.getString("FECHANACIMIENTO");
                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                        try {
                            Date date = new SimpleDateFormat("yyyy-MM-dd").parse(fechaNacimiento);
                            fechaNacimiento = sdf.format(date);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        comprador = new Comprador(
                                compradorCursor.getString("CEDULAIDENTIDAD"),
                                compradorCursor.getString("NOMBREUSUARIO"),
                                compradorCursor.getString("NOMBRECOMPLETO"),
                                fechaNacimiento,
                                compradorCursor.getString("CORREOELECTRONICO"),
                                compradorCursor.getString("CONTRASENIA")
                        );
                    }
                }
            }
        } catch (SQLException e) {
            throw new GlobalException("Sentencia no válida " + e);
        } finally {
            desconectar();
        }

        if (comprador == null) {
            throw new NoDataException("No hay datos");
        }

        return comprador;
    }

    public Collection<Comprador> buscarCompradorPorCriterio(String criterio) throws GlobalException, NoDataException, SQLException {
    try {
        conectar();
    } catch (ClassNotFoundException e) {
        throw new GlobalException("No se ha localizado el driver");
    } catch (SQLException e) {
        throw new NoDataException("La base de datos no se encuentra disponible");
    }

    try (CallableStatement stmt = conexion.prepareCall(BUSCAR_COMPRADOR_POR_CRITERIO)) {
        stmt.registerOutParameter(1, OracleTypes.CURSOR);
        stmt.setString(2, criterio);

        stmt.execute();

        try (ResultSet rs = (ResultSet) stmt.getObject(1)) {
            List<Comprador> compradores = new ArrayList<>();
            while (rs.next()) {
                String fechaNacimiento = rs.getString("FECHANACIMIENTO");
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                try {
                    Date date = new SimpleDateFormat("yyyy-MM-dd").parse(fechaNacimiento);
                    fechaNacimiento = sdf.format(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                Comprador comprador = new Comprador();
                comprador.setCedulaIdentidad(rs.getString("CEDULAIDENTIDAD"));
                comprador.setNombreUsuario(rs.getString("NOMBREUSUARIO"));
                comprador.setNombreCompleto(rs.getString("NOMBRECOMPLETO"));
                comprador.setFechaNacimiento(fechaNacimiento);
                comprador.setCorreoElectronico(rs.getString("CORREOELECTRONICO"));
                comprador.setContrasenia(rs.getString("CONTRASENIA"));

                compradores.add(comprador);
            }
            return compradores;
        }
    } catch (SQLException e) {
        throw new GlobalException("Error al buscar compradores: " + e.getMessage());
    } finally {
        desconectar();
    }
}
}