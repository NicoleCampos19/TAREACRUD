package emily.jacobo.crudhelpdesk

import Modelo.Conexion
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.UUID

class Registro : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registro)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val txtInicioSesion = findViewById<TextView>(R.id.txtActInicio)
        val txtNombres = findViewById<EditText>(R.id.txtNombresR)
        val txtApellidos = findViewById<EditText>(R.id.txtApellidosR)
        val txtEmail = findViewById<EditText>(R.id.txtCorreoR)
        val txtContraseña = findViewById<EditText>(R.id.txtContrasenaR)
        val btnRegistrarse = findViewById<Button>(R.id.btnRegistrarse)

        //Pasar a la pantalla de Login
        txtInicioSesion.setOnClickListener {
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }

        btnRegistrarse.setOnClickListener {
            GlobalScope.launch(Dispatchers.IO) {
                val objConexion = Conexion().CadenaConexion()
                val crearUsuario =
                    objConexion?.prepareStatement("INSERT INTO TBUSUARIOS (UUID_USUARIO, NOMBRES, APELLIDOS, EMAIL, CONTRASEÑA) VALUES (?, ?, ?, ?, ?)")!!
                crearUsuario.setString(1, UUID.randomUUID().toString())
                crearUsuario.setString(2, txtNombres.text.toString())
                crearUsuario.setString(3, txtApellidos.text.toString())
                crearUsuario.setString(4, txtEmail.text.toString())
                crearUsuario.setString(5, txtContraseña.text.toString())
                crearUsuario.executeUpdate()
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        this@Registro,
                        "El usuario se ha creado exitosamente",
                        Toast.LENGTH_SHORT
                    ).show()
                    txtNombres.setText("")
                    txtApellidos.setText("")
                    txtEmail.setText("")
                    txtContraseña.setText("")
                }
            }
        }
    }
}
