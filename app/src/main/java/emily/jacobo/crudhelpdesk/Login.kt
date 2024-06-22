package emily.jacobo.crudhelpdesk

import Modelo.Conexion
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val imgAtras = findViewById<ImageView>(R.id.imgAtras)
        val btnIniciar = findViewById<Button>(R.id.btnIniciarL)
        val txtCorreoL = findViewById<EditText>(R.id.txtCorreoL)
        val txtContrasenaL = findViewById<EditText>(R.id.txtContrasenaL)

        imgAtras.setOnClickListener {
            val intent = Intent(this, Registro::class.java)
            startActivity(intent)
        }

        btnIniciar.setOnClickListener {
            val pantalla = Intent(this, MainActivity::class.java)
            GlobalScope.launch(Dispatchers.IO)
            {
                val objConexion = Conexion().CadenaConexion()
                val comprobarUsuario = objConexion?.prepareStatement("SELECT * FROM TBUSUARIOS WHERE EMAIL = ? AND CONTRASEÑA = ?")!!
                comprobarUsuario.setString(1, txtCorreoL.text.toString())
                comprobarUsuario.setString(2, txtContrasenaL.text.toString())

                val resultado = comprobarUsuario.executeQuery()
                if (resultado.next())
                {
                    startActivity(pantalla)
                }
                else
                {
                    GlobalScope.launch(Dispatchers.Main)
                    {
                        Toast.makeText(
                            this@Login,
                            "Usuario no encontrado, verificar sus credenciales",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }
}