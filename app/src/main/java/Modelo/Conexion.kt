package Modelo

import java.lang.Exception
import java.sql.Connection
import java.sql.DriverManager

class Conexion {
    fun CadenaConexion(): Connection?{

        try{
            val ipEmily = "jdbc:oracle:thin:@192.168.0.13:1521:xe"
            val usuario = "NICOLE_DEV"
            val contrasena = "EMICAMPOS19"

            val conexion = DriverManager.getConnection(ipEmily, usuario, contrasena)
            return conexion
        }catch(e:Exception) {
            println("Este es tu error: $e")
            return null
        }
    }
}