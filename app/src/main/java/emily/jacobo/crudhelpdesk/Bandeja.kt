package emily.jacobo.crudhelpdesk

import Modelo.Conexion
import Modelo.DataClassTicket
import RecyclerViewHelpers.Adaptador
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Bandeja : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_bandeja2, container, false)


        val rcvTicket = root.findViewById<RecyclerView>(R.id.rcvTicket)
        rcvTicket.layoutManager = LinearLayoutManager(this.context)

        //Función para mostrar
        fun obtenerDatos(): List<DataClassTicket>{
            val objConexion = Conexion().CadenaConexion()

            val statement = objConexion?.createStatement()
            val resultSet = statement?.executeQuery("select * from TBTICKET")!!

            val ListadoTickets = mutableListOf<DataClassTicket>()

            //Recorro todos los datos que me trajo el select
            while (resultSet?.next() == true){
                val NUMERO_TICKET = resultSet.getString("NUMERO_TICKET")
                val TITULO_TICKET = resultSet.getString("TITULO_TICKET")
                val DESCRIPCIÓN_TICKET = resultSet.getString("DESCRIPCIÓN_TICKET")
                val RESPONSABLE_TICKET = resultSet.getString("RESPONSABLE_TICKET")
                val EMAIL_CONTACTO_AUTOR = resultSet.getString("EMAIL_CONTACTO_AUTOR")
                val TELEFONO_AUTOR = resultSet.getString("TELEFONO_AUTOR")
                val UBICACIÓN = resultSet.getString("UBICACIÓN")
                val ESTADO_TICKET = resultSet.getString("ESTADO_TICKET")

                val Ticket = DataClassTicket(NUMERO_TICKET, TITULO_TICKET, DESCRIPCIÓN_TICKET, RESPONSABLE_TICKET, EMAIL_CONTACTO_AUTOR, TELEFONO_AUTOR, UBICACIÓN, ESTADO_TICKET)
                ListadoTickets.add(Ticket)

            }
            return ListadoTickets
        }
        //Ejecutamos la función
        CoroutineScope(Dispatchers.IO).launch {
            val ejecutarFuncion = obtenerDatos()

            withContext(Dispatchers.Main){
                val Adaptador = Adaptador(ejecutarFuncion)
                rcvTicket.adapter = Adaptador
            }
        }

        return root
    }



                }

