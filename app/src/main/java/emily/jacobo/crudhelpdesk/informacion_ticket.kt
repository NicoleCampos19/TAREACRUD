package emily.jacobo.crudhelpdesk

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class informacion_ticket : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_informacion_ticket)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val TITU_RECIBIDO = intent.getStringExtra("TITULO_TICKET")
        val DESC_RECIBIDO = intent.getStringExtra("DESCRIPCIÓN_TICKET")
        val RES_RECIBIDO = intent.getStringExtra("RESPONSABLE_TICKET")
        val EMAIL_RECIBIDO = intent.getStringExtra("EMAIL_CONTACTO_AUTOR")
        val TEL_RECIBIDO = intent.getStringExtra("TELEFONO_AUTOR")
        val UBI_RECIBIDO = intent.getStringExtra("UBICACIÓN")
        val ESTADO_RECIBIDO = intent.getStringExtra("ESTADO_TICKET")

        val txtTitulo = findViewById<TextView>(R.id.txtTituloI)
        val txtDescripcion = findViewById<TextView>(R.id.txtDescripcionI)
        val txtResponsable = findViewById<TextView>(R.id.txtResponsableI)
        val txtEmail = findViewById<TextView>(R.id.txtEmailI)
        val txtTelefono = findViewById<TextView>(R.id.txtTelefonoI)
        val txtUbicacion = findViewById<TextView>(R.id.txtUbicacionI)
        val txtEstado = findViewById<TextView>(R.id.txtEstadoI)
        val btnAtras = findViewById<ImageButton>(R.id.btnAtrasI)

        txtTitulo.text = TITU_RECIBIDO
        txtDescripcion.text = DESC_RECIBIDO
        txtResponsable.text = RES_RECIBIDO
        txtEmail.text = EMAIL_RECIBIDO
        txtTelefono.text = TEL_RECIBIDO
        txtUbicacion.text = UBI_RECIBIDO
        txtEstado.text = ESTADO_RECIBIDO

        btnAtras.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("volver", true)
            startActivity(intent)
        }
    }
}