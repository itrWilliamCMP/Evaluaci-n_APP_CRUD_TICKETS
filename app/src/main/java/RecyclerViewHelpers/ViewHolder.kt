package RecyclerViewHelpers

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.viewmodel.savedstate.R
import androidx.recyclerview.widget.RecyclerView

class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {    val textView: TextView = view.findViewById(
    william.campos.soporte.R.id.txt_ticketCard)
    val imgEditar:ImageView=view.findViewById(william.campos.soporte.R.id.img_editar)
    val imgBorrar: ImageView=view.findViewById(william.campos.soporte.R.id.img_borrar)
    }