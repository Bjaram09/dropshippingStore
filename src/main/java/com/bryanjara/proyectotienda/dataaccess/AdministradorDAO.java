package com.bryanjara.proyectotienda.dataaccess;

import com.bryanjara.proyectotienda.models.Administrador;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AdministradorDAO extends ServicioDB{
    private static final String INSERTAR_ADMINISTRADOR = "{CALL DROPSHIPPING.INSERTAR_ADMINISTRADOR(?, ?, ?, ?, ?, ?)}";
    private static final String MODIFICAR_ADMINISTRADOR = "{CALL DROPSHIPPING.MODIFICAR_ADMINISTRADOR(?, ?, ?, ?, ?, ?)}";
    private static final String ELIMINAR_ADMINISTRADOR = "{CALL DROPSHIPPING.ELIMINAR_ADMINISTRADOR(?)}";
    private static final String LISTAR_ADMINISTRADOR = "SELECT DROPSHIPPING.LISTAR_ADMINISTRADORES FROM DUAL";
    private static final String BUSCAR_ADMINISTRADOR_POR_NOMBRE = "SELECT DROPSHIPPING.BUSCAR_ADMIN_POR_NOMBREUSUARIO(?) FROM DUAL";

    public AdministradorDAO() {
        super();
    }

    private void setAdministradorParameters(CallableStatement pstmt, Administrador administrador) throws SQLException {
        try {
            pstmt.setString(1, administrador.getCedulaIdentidad());
            pstmt.setString(2, administrador.getNombreUsuario());
            pstmt.setString(3, administrador.getNombreCompleto());

            System.out.println("AdministradorDAO: line 32 - " + administrador.getFechaNacimiento());
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            try {
                java.util.Date utilDate = sdf.parse(administrador.getFechaNacimiento());
                java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

                System.out.println("AdministradorDAO: line 38 - " + sqlDate);

                pstmt.setDate(4, sqlDate);
            } catch (ParseException e) {
                throw new IllegalArgumentException("Invalid date format. Expected dd/MM/yyyy");
            }

            pstmt.setString(5, administrador.getCorreoElectronico());
            pstmt.setString(6, administrador.getContrasenia());
        } catch (SQLException e) {
            throw new IllegalArgumentException("Error setting parameters for CallableStatement");
        }
    }

    public void insertarAdministrador(Administrador administrador) throws GlobalException, NoDataException, SQLException {
        try {
            conectar();
        } catch (ClassNotFoundException e) {
            throw new GlobalException("No se ha localizado el driver");
        } catch (SQLException e) {
            throw new NoDataException("La base de datos no se encuentra disponible");
        }

        try (CallableStatement pstmt = conexion.prepareCall(INSERTAR_ADMINISTRADOR)) {
            setAdministradorParameters(pstmt, administrador);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new NoDataException("No se realizó la operación en el Administrador.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new GlobalException("Llave duplicada");
        } finally {
            desconectar();
        }
    }

    public void modificarAdministrador(Administrador administrador) throws GlobalException, NoDataException, SQLException {
        Administrador administradorExistente;
        try {
            administradorExistente = buscarAdministrador(administrador.getCedulaIdentidad());
        } catch (NoDataException e) {
            throw new GlobalException("El Administrador con ID " + administrador.getCedulaIdentidad() + " no existe en la base de datos.");
        } catch (SQLException e) {
            throw new GlobalException("Error al consultar Administrador");
        }

        try {
            conectar();
        } catch (ClassNotFoundException e) {
            throw new GlobalException("No se ha localizado el driver");
        } catch (SQLException e) {
            throw new NoDataException("La base de datos no se encuentra disponible");
        }

        try (CallableStatement pstmt = conexion.prepareCall(MODIFICAR_ADMINISTRADOR)) {
            setAdministradorParameters(pstmt, administrador);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new NoDataException("No se realizó la modificación del Administrador.");
            } else {
                System.out.println("Administrador modificado exitosamente con ID: " + administrador.getCedulaIdentidad());
            }
        }
        catch (SQLException e) {
            throw new GlobalException("Sentencia no valida");
        } finally {
            desconectar();
        }
    }

    public void eliminarAdministrador(String cedula) throws GlobalException, NoDataException, SQLException {
        try {
            buscarAdministrador(cedula);
        } catch (NoDataException e) {
            throw new GlobalException("El administrador con ID " + cedula + " no existe en la base de datos.");
        } catch (SQLException e) {
            throw new GlobalException("Error al consultar Administrador");
        }

        try {
            conectar();
        } catch (ClassNotFoundException e) {
            throw new GlobalException("No se ha localizado el driver");
        } catch (SQLException e) {
            throw new NoDataException("La base de datos no se encuentra disponible");
        }

        try (CallableStatement pstmt = conexion.prepareCall(ELIMINAR_ADMINISTRADOR)) {
            pstmt.setString(1, cedula);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new NoDataException("No se eliminó el administrador. Puede estar referenciado en otras tablas.");
            }
        } catch (SQLException e) {
            throw new SQLException("Error al eliminar Administrador " + e);
        } finally {
            desconectar();
        }
    }

    public Collection<Administrador> listarAdministrador() throws GlobalException, NoDataException, SQLException {
        try {
            conectar();
        } catch (ClassNotFoundException e) {
            throw new GlobalException("No se ha localizado el Driver");
        } catch (SQLException e) {
            throw new NoDataException("La base de datos no se encuentra disponible: " + e.getMessage());
        }

        ArrayList<Administrador> coleccion = new ArrayList<>();

        try (PreparedStatement pstmt = conexion.prepareStatement(LISTAR_ADMINISTRADOR); ResultSet rs = pstmt.executeQuery()){
            while (rs.next()) {
                ResultSet administradoresCursor = (ResultSet) rs.getObject(1);
                while (administradoresCursor.next()) {
                    String fechaNacimiento = administradoresCursor.getString("FECHANACIMIENTO");
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    try {
                        Date date = new SimpleDateFormat("yyyy-MM-dd").parse(fechaNacimiento);
                        fechaNacimiento = sdf.format(date);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    
                    Administrador administrador = new Administrador(
                            administradoresCursor.getString("CEDULAIDENTIDAD"),
                            administradoresCursor.getString("NOMBREUSUARIO"),
                            administradoresCursor.getString("NOMBRECOMPLETO"),
                            fechaNacimiento,
                            administradoresCursor.getString("CORREOELECTRONICO"),
                            administradoresCursor.getString("CONTRASENIA")
                    );
                    coleccion.add(administrador);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new GlobalException("Error al ejecutar consulta: " + e.getMessage());
        } finally {
            desconectar();
        }

        return coleccion;
    }

    public Administrador buscarAdministrador(String username) throws GlobalException, NoDataException, SQLException {
        try {
            conectar();
        } catch (ClassNotFoundException e) {
            throw new GlobalException("No se ha localizado el driver");
        } catch (SQLException e) {
            throw new NoDataException("La base de datos no se encuentra disponible");
        }

        Administrador administrador = null;

        try (PreparedStatement pstmt = conexion.prepareStatement(BUSCAR_ADMINISTRADOR_POR_NOMBRE)) {
            pstmt.setString(1, username);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    ResultSet administradorCursor = (ResultSet) rs.getObject(1);
                    if (administradorCursor.next()) {
                        String fechaNacimiento = administradorCursor.getString("FECHANACIMIENTO");
                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                        try {
                            Date date = new SimpleDateFormat("yyyy-MM-dd").parse(fechaNacimiento);
                            fechaNacimiento = sdf.format(date);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        administrador = new Administrador(
                                administradorCursor.getString("CEDULAIDENTIDAD"),
                                administradorCursor.getString("NOMBREUSUARIO"),
                                administradorCursor.getString("NOMBRECOMPLETO"),
                                fechaNacimiento,
                                administradorCursor.getString("CORREOELECTRONICO"),
                                administradorCursor.getString("CONTRASENIA")
                        );
                    }
                }
            }
        } catch (SQLException e) {
            throw new GlobalException("Sentencia no válida " + e);
        } finally {
            desconectar();
        }

        if (administrador == null) {
            throw new NoDataException("No hay datos");
        }

        return administrador;
    }
}