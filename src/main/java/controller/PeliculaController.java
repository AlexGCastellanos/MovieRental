package controller;

import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gson.Gson;
import beans.Peliculas;
import connection.DBConnection;

public class PeliculaController implements IPeliculaController {

    @Override
    public String listar(boolean ordenar, String orden) {

        Gson gson = new Gson();

        DBConnection con = new DBConnection();
        String sql = "Select * from pelicula";

        if (ordenar == true) {
            sql += " order by genero " + orden;
        }

        List<String> peliculas = new ArrayList<String>();

        try {

            Statement st = con.getConnection().createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {

                int id = rs.getInt("id");
                String titulo = rs.getString("titulo");
                String genero = rs.getString("genero");
                String autor = rs.getString("autor");
                int copias = rs.getInt("copias");
                boolean novedad = rs.getBoolean("novedad");

                Peliculas pelicula = new Peliculas(id, titulo, genero, autor, copias, novedad);

                peliculas.add(gson.toJson(pelicula));

            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            con.Desconectar();
        }

        return gson.toJson(peliculas);

    }



}