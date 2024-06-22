package emily.jacobo.crudhelpdesk

import Modelo.Conexion
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.UUID

class Tickets : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_tickets, container, false)

        val txtTitulo = root.findViewById<EditText>(R.id.txtTitulo)
        val txtDescripción = root.findViewById<EditText>(R.id.txtDescripcion)
        val txtResponsable = root.findViewById<EditText>(R.id.txtResponsable)
        val txtEmail = root.findViewById<EditText>(R.id.txtEmail)
        val txtTelefono = root.findViewById<EditText>(R.id.txtTelefono)
        val txtUbicacion = root.findViewById<EditText>(R.id.txtUbicacion)
        val txtEstado = root.findViewById<EditText>(R.id.txtEstado)
        val btnAgregar = root.findViewById<Button>(R.id.btnAgregar)

        //Función para limpiar campos
        fun LimpiarCampos(){
            txtTitulo.setText("")
            txtDescripción.setText("")
            txtResponsable.setText("")
            txtEmail.setText("")
            txtTelefono.setText("")
            txtUbicacion.setText("")
            txtEstado.setText("")
        }
        btnAgregar.setOnClickListener {
            if (txtEstado.text.toString() != "Activo")
                {
                GlobalScope.launch(Dispatchers.Main){Toast.makeText(context, "Únicamente puede estar en activo el estado del ticket",Toast.LENGTH_SHORT).show()}
            }else{
                GlobalScope.launch(Dispatchers.IO) {
                    try {
                        val objConexion = Conexion().CadenaConexion()

                        val addTicket = objConexion?.prepareStatement("INSERT INTO TBTICKET (NUMERO_TICKET, TITULO_TICKET, DESCRIPCIÓN_TICKET, RESPONSABLE_TICKET, EMAIL_CONTACTO_AUTOR, TELEFONO_AUTOR, UBICACIÓN, ESTADO_TICKET) values (?, ?, ?, ?, ?, ?, ?, ?)")!!
                        addTicket.setString(1, UUID.randomUUID().toString())
                        addTicket.setString(2, txtTitulo.text.toString())
                        addTicket.setString(3, txtDescripción.text.toString())
                        addTicket.setString(4, txtResponsable.text.toString())
                        addTicket.setString(5, txtEmail.text.toString())
                        addTicket.setString(6, txtTelefono.text.toString())
                        addTicket.setString(7, txtUbicacion.text.toString())
                        addTicket.setString(8, txtEstado.text.toString())
                        addTicket.executeUpdate()

                        //Llamo a la función de limpiar campos
                        LimpiarCampos()

                        withContext(Dispatchers.Main) {
                            Toast.makeText(requireContext(), "El ticket se ha generado correctamente", Toast.LENGTH_SHORT).show()
                        }
                    } catch (e: Exception) {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(requireContext(), "Error al generar el ticket: ${e.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
            }
        return root
    }

}