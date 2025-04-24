package com.bryanjara.proyectotienda.dataaccess;


import com.bryanjara.proyectotienda.models.Factura;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;

public class FacturaDAO extends ServicioDB{
    private static final String insertar_factura = "{call insertar_factura(?,?,?)}";
    private static final String listar_facturas = "{?=call listar_facturas()}";
    private static final String buscar_factura_por_id = "{?=call buscar_factura_por_id(?)}";
    private static final String modificar_factura = "{call modificar_factura(?,?,?)}";
    private static final String eliminar_factura = "{call eliminar_factura(?)}";

    public FacturaDAO() {}

    public void insertarFactura(Factura factura) throws GlobalException, NoDataException, ClassNotFoundException, SQLException {
        CallableStatement pstmt = null;

        try {
            conectar();
            pstmt = conexion.prepareCall(insertar_factura);

            pstmt.setDouble(1, factura.getImpuesto());
            pstmt.setDouble(2, factura.getTotal());
            pstmt.registerOutParameter(3, Types.INTEGER);

            pstmt.execute();
            int idGenerado = pstmt.getInt(3);
            System.out.println("Factura insertada con ID: " + idGenerado);

        } catch (SQLException e) {
            e.printStackTrace();
            throw new GlobalException("Error al insertar factura: " + e.getMessage());
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                desconectar();
            } catch (SQLException e) {
                throw new GlobalException("Error al cerrar conexi贸n");
            }
        }
    }

    public Collection<Factura> listarFacturas() throws GlobalException, NoDataException {
        ResultSet rs = null;
        CallableStatement pstmt = null;
        ArrayList<Factura> lista = new ArrayList<>();

        try {
            conectar();
            pstmt = conexion.prepareCall(listar_facturas);
            pstmt.registerOutParameter(1, OracleTypes.CURSOR);
            pstmt.execute();
            rs = (ResultSet) pstmt.getObject(1);

            while (rs.next()) {
                Factura f = new Factura();
                f.setImpuesto(rs.getDouble("impuesto"));
                f.setTotal(rs.getDouble("total"));
                lista.add(f);
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new GlobalException("Error al listar facturas: " + e.getMessage());
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
            throw new NoDataException("No hay facturas disponibles");
        }

        return lista;
    }

    public Factura buscarFactura(int id) throws GlobalException, NoDataException {
        ResultSet rs = null;
        CallableStatement pstmt = null;
        Factura factura = null;

        try {
            conectar();
            pstmt = conexion.prepareCall(buscar_factura_por_id);
            pstmt.registerOutParameter(1, OracleTypes.CURSOR);
            pstmt.setInt(2, id);
            pstmt.execute();
            rs = (ResultSet) pstmt.getObject(1);

            if (rs.next()) {
                factura = new Factura();
                factura.setImpuesto(rs.getDouble("impuesto"));
                factura.setTotal(rs.getDouble("total"));
            }

        } catch (SQLException | ClassNotFoundException e) {
            throw new GlobalException("Error al buscar factura: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                desconectar();
            } catch (SQLException e) {
                throw new GlobalException("Error al cerrar conexi贸n");
            }
        }
        return factura;
    }

    public void modificarFactura(int id, double impuesto, double total) throws GlobalException, NoDataException {
        CallableStatement pstmt = null;
        try {
            conectar();
            pstmt = conexion.prepareCall(modificar_factura);
            pstmt.setInt(1, id);
            pstmt.setDouble(2, impuesto);
            pstmt.setDouble(3, total);

            int resultado = pstmt.executeUpdate();
            if (resultado == 0) {
                throw new NoDataException("No se realizo la modificacion");
            }

        } catch (SQLException | ClassNotFoundException e) {
            throw new GlobalException("Error al modificar factura: " + e.getMessage());
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                desconectar();
            } catch (SQLException e) {
                throw new GlobalException("Error al cerrar conexi贸n");
            }
        }
    }

    public void eliminarFactura(int id) throws GlobalException, NoDataException {
        CallableStatement pstmt = null;
        try {
            conectar();
            pstmt = conexion.prepareCall(eliminar_factura);
            pstmt.setInt(1, id);

            int resultado = pstmt.executeUpdate();
            if (resultado == 0) {
                throw new NoDataException("No se realizo la eliminacion");
            }

        } catch (SQLException | ClassNotFoundException e) {
            throw new GlobalException("Error al eliminar factura: " + e.getMessage());
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                desconectar();
            } catch (SQLException e) {
                throw new GlobalException("Error al cerrar conexi贸n");
            }
        }
    }
}
