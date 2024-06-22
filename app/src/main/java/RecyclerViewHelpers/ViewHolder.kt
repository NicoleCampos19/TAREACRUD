package RecyclerViewHelpers

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import emily.jacobo.crudhelpdesk.R

class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
    val txtTitulo = view.findViewById<TextView>(R.id.txtTitulo)
    val txtDescripcion = view.findViewById<TextView>(R.id.txtDescripcion)
    val txtResponsable = view.findViewById<TextView>(R.id.txtResponsable)
    val txtCorreo = view.findViewById<TextView>(R.id.txtCorreo)
    val txtTelefono = view.findViewById<TextView>(R.id.txtTelefono)
    val txtUbicacion = view.findViewById<TextView>(R.id.txtUbicacion)
    val txtEstado = view.findViewById<TextView>(R.id.txtEstado)
    val btnEditar : ImageView = view.findViewById(R.id.imgEditar)
    val btnBorrar : ImageView = view.findViewById(R.id.imgBorrar)
}