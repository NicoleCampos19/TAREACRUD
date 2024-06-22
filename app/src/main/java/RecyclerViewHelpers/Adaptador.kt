package RecyclerViewHelpers

import Modelo.Conexion
import Modelo.DataClassTicket
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import emily.jacobo.crudhelpdesk.R
import emily.jacobo.crudhelpdesk.Tickets
import emily.jacobo.crudhelpdesk.informacion_ticket
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Adaptador(private var Datos: List<DataClassTicket>): RecyclerView.Adapter<ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vista = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_card_help_desk, parent, false)
        return ViewHolder(vista)
    }

    fun Actualizarlista(numeroTicket: String, newTitulo: String, newDescripcion: String, newResponsable: String, newCorreo: String, newTelefono: String, newUbicacion: String, newEstado: String) {
        val index = Datos.indexOfFirst { it.NUMERO_TICKET == numeroTicket }
        Datos[index].TITULO_TICKET = newTitulo
        Datos[index].DESCRIPCIÓN_TICKET = newDescripcion
        Datos[index].RESPONSABLE_TICKET = newResponsable
        Datos[index].EMAIL_CONTACTO_AUTOR = newCorreo
        Datos[index].TELEFONO_AUTOR = newTelefono
        Datos[index].UBICACIÓN = newUbicacion
        Datos[index].ESTADO_TICKET = newEstado
        notifyItemChanged(index)
    }

    fun EliminarTicket(TITULO_TICKET: String, posicion: Int) {
        val listaDatos = Datos.toMutableList()
        listaDatos.removeAt(posicion)
        GlobalScope.launch(Dispatchers.IO) {
            val objConexion = Conexion().CadenaConexion()
            val delTickets =
                objConexion?.prepareStatement("delete TBTICKET where TITULO_TICKET = ?")!!
            delTickets.setString(1, TITULO_TICKET )
            delTickets.executeUpdate()
            val commit = objConexion.prepareStatement("commit")!!
            commit.executeUpdate()
        }
        Datos = listaDatos.toList()
        notifyItemRemoved(posicion)
        notifyDataSetChanged()


    }

    fun actualizarCampos(
        TITULO: String,
        DESCRIPCION: String,
        RESPONSABLE: String,
        CORREO: String,
        TELEFONO: String,
        UBICACION: String,
        ESTADO: String,
        NUMERO_TICKET: String
    ) {
        GlobalScope.launch(Dispatchers.IO) {
            val objConexion = Conexion().CadenaConexion()
            val updateProductos =
                objConexion?.prepareStatement("update tbTicket set TITULO_TICKET = ?, DESCRIPCIÓN_TICKET = ?, RESPONSABLE_TICKET = ?, EMAIL_CONTACTO_AUTOR = ?, TELEFONO_AUTOR = ?, UBICACIÓN = ?, ESTADO_TICKET = ? where NUMERO_TICKET = ?")!!
            updateProductos.setString(1, TITULO)
            updateProductos.setString(2, DESCRIPCION)
            updateProductos.setString(3, RESPONSABLE)
            updateProductos.setString(4, CORREO)
            updateProductos.setString(5, TELEFONO)
            updateProductos.setString(6, UBICACION)
            updateProductos.setString(7, ESTADO)
            updateProductos.setString(8, NUMERO_TICKET)
            updateProductos.executeUpdate()

            val commit = objConexion.prepareStatement("commit")!!
            commit.executeUpdate()

            withContext(Dispatchers.Main) {
                Actualizarlista(
                    NUMERO_TICKET,
                    TITULO,
                    DESCRIPCION,
                    RESPONSABLE,
                    CORREO,
                    TELEFONO,
                    UBICACION,
                    ESTADO
                )
            }
        }

    }

    override fun getItemCount() = Datos.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ticket = Datos[position]
        holder.txtTitulo.text = ticket.TITULO_TICKET
        holder.txtDescripcion.text = ticket.DESCRIPCIÓN_TICKET
        holder.txtResponsable.text = ticket.RESPONSABLE_TICKET
        holder.txtCorreo.text = ticket.EMAIL_CONTACTO_AUTOR
        holder.txtTelefono.text = ticket.TELEFONO_AUTOR
        holder.txtUbicacion.text = ticket.UBICACIÓN
        holder.txtEstado.text = ticket.ESTADO_TICKET

        val item = Datos[position]
        holder.btnBorrar.setOnClickListener {
            val context = holder.itemView.context
            val builder = AlertDialog.Builder(context)

            builder.setTitle("Eliminar Ticket")
            builder.setMessage("¿Estás seguro de eliminar este ticket?")
            builder.setPositiveButton("SÍ") { dialog, which ->
                EliminarTicket(item.NUMERO_TICKET, position)
            }
            builder.setNegativeButton("No") { dialog, which ->
            }
            val alertDialog = builder.create()
            alertDialog.show()
        }

        holder.btnEditar.setOnClickListener {
            val context = holder.itemView.context
            val builder = AlertDialog.Builder(context)
            builder.setTitle("¿Estás seguro de editar este ticket?")

            val campoTitulo = EditText(context).apply {
                setText(item.TITULO_TICKET)
            }
            val campoDescripcion = EditText(context).apply {
                setText(item.DESCRIPCIÓN_TICKET)
            }
            val campoResponsable = EditText(context).apply {
                setText(item.RESPONSABLE_TICKET)
            }
            val campoEmail = EditText(context).apply {
                setText(item.EMAIL_CONTACTO_AUTOR)
            }
            val campoTelefono = EditText(context).apply {
                setText(item.TELEFONO_AUTOR)
            }
            val campoUbicacion = EditText(context).apply {
                setText(item.UBICACIÓN)
            }
            val campoEstado = EditText(context).apply {
                setText(item.ESTADO_TICKET)
            }

            val cuadrito = LinearLayout(context).apply {
                orientation = LinearLayout.VERTICAL
                addView(campoTitulo)
                addView(campoDescripcion)
                addView(campoResponsable)
                addView(campoEmail)
                addView(campoTelefono)
                addView(campoUbicacion)
                addView(campoEstado)
            }
            builder.setView(cuadrito)
            builder.setPositiveButton("Sí") { dialog, which ->
               actualizarCampos(campoTitulo.text.toString(), campoDescripcion.text.toString(), campoResponsable.text.toString(), campoEmail.text.toString(), campoTelefono.text.toString(), campoUbicacion.text.toString(), campoEstado.text.toString(), item.NUMERO_TICKET)
            }
            builder.setNegativeButton("No") { dialog, which ->
                dialog.dismiss()
            }
            val alertDialog = builder.create()
            alertDialog.show()
        }
        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            //Cambiar de pantalla a la pantalla de detalle
            val pantallaDetalle = Intent(context, informacion_ticket::class.java)
            //enviar a la otra pantalla todos mis valores
            pantallaDetalle.putExtra("TITULO_TICKET", ticket.TITULO_TICKET)
            pantallaDetalle.putExtra("DESCRIPCIÓN_TICKET", ticket.DESCRIPCIÓN_TICKET)
            pantallaDetalle.putExtra("RESPONSABLE_TICKET", ticket.RESPONSABLE_TICKET)
            pantallaDetalle.putExtra("EMAIL_CONTACTO_AUTOR", ticket.EMAIL_CONTACTO_AUTOR)
            pantallaDetalle.putExtra("TELEFONO_AUTOR", ticket.TELEFONO_AUTOR)
            pantallaDetalle.putExtra("UBICACIÓN", ticket.UBICACIÓN)
            pantallaDetalle.putExtra("ESTADO_TICKET", ticket.ESTADO_TICKET)
            context.startActivity(pantallaDetalle)
        }
    }
}
