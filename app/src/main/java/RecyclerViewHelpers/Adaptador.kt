package RecyclerViewHelpers

import Modelo.ClaseConexion
import Modelo.tickets
import android.app.AlertDialog
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import william.campos.soporte.DetalleTickets
import william.campos.soporte.EditarTickets
import william.campos.soporte.MainActivity
import william.campos.soporte.R

class Adaptador(private var Datos: List<tickets>) : RecyclerView.Adapter<ViewHolder>() {

    fun actualizarLista(nuevaLista:List<tickets>){
        Datos=nuevaLista
        notifyDataSetChanged()
    }

    //funcion parar actualizar el reciler view cuando actualizo los datos

    fun actualizarListaDespuesDeActualizarDatos(uuid: String,nuevoTitulo:String){
        val index=Datos.indexOfFirst { it.uuid==uuid }
        Datos[index].Titulo=nuevoTitulo
        notifyItemChanged(index)

    }
    fun eliminarRegistro(Titulo:String,position: Int){

        //quitar el elementpo de la lista
        val listaDatos = Datos .toMutableList()
        listaDatos.removeAt(position)

        //quitar de la base de datos
        GlobalScope.launch(Dispatchers.IO) {

            //crear un objeto e la clase conexion
            val objConexion=ClaseConexion().cadenaConexion()

            val deleteticket = objConexion?.prepareStatement("delete Tickets where Titulo=?")!!
            deleteticket.setString( 1,Titulo)
            deleteticket.executeUpdate()

            val commit = objConexion.prepareStatement( "commit")!!
            commit.executeUpdate()
        }
        Datos=listaDatos.toList()
        notifyItemRemoved(position)
        notifyDataSetChanged()

    }

    fun actualizarTiquete(Titulo:String , uuid:String){
        //crear na co rrutinan
        GlobalScope.launch(Dispatchers.IO){
            //creo un objeto de la clase conexion

            val objConexion = ClaseConexion().cadenaConexion()

            //variable que contenga prepared sttement
            val updateTitulo = objConexion?.prepareStatement("update Tickets set Titulo = ? where uuid = ?")!!

            updateTitulo.setString(1,Titulo)
            updateTitulo.setString(2,uuid)
            updateTitulo.executeUpdate()

            val commit = objConexion.prepareStatement("commit")
            commit.executeUpdate()

            withContext(Dispatchers.Main){
                actualizarListaDespuesDeActualizarDatos(uuid,Titulo )
            }

        }

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.activity_item_card, parent, false)
        return ViewHolder(vista)
    }

    override fun getItemCount() = Datos.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val Ticket = Datos[position]
        holder.textView.text = Ticket.Titulo

        val item =Datos[position]


        holder.imgBorrar.setOnClickListener {
            //craeamos una alaerta

            //invocamos  el contexto
            val context = holder.itemView.context

            //CREO LA ALERTA

            val builder = AlertDialog.Builder(context)

            //le ponemos titulo a la alerta

            builder.setTitle("¿estas seguro?")

            //ponerle mendsaje a la alerta

            builder.setMessage("Deseas en verdad eliminar el registro")

            //agrgamos los botones

            builder.setPositiveButton("si"){dialog,wich ->
                eliminarRegistro(item.Titulo,position)
            }

            builder.setNegativeButton("no"){dialog,wich ->

            }

            //cramos la alerta
            val alertDialog=builder.create()

            //mostramos la alerta

            alertDialog.show()

        }

        holder.imgEditar.setOnClickListener {
            //invoco el contexto
            val context = holder.itemView.context

            //cambiamos de pantalla
            //abro pantalla detalle tiquetes
            val pantallaEditarTiquete = Intent(context, EditarTickets::class.java)
            //aqui antes de abrir la nueva pantalla le abro los parametros


            pantallaEditarTiquete.putExtra("uuid",item.uuid)
            pantallaEditarTiquete.putExtra("Titulo",item.Titulo)
            pantallaEditarTiquete.putExtra("numeroTiquete",item.No_ticket)
            pantallaEditarTiquete.putExtra("descripcion",item.descripcion)
            pantallaEditarTiquete.putExtra("F_creado",item.F_creado)
            pantallaEditarTiquete.putExtra("Estado",item.Estado)
            pantallaEditarTiquete.putExtra("F_cerrado",item.F_cerrado)

            context.startActivity(pantallaEditarTiquete)
        }

        //darle clic a la card
        holder.itemView.setOnClickListener {
            //invoco el contexto
            val context = holder.itemView.context

            //cambiamos de pantalla
            //abro pantalla detalle tiquetes
            val pantallaDetalles = Intent(context, DetalleTickets::class.java)
            //aqui antes de abrir la nueva pantalla le abro los parametros


            pantallaDetalles.putExtra("uuid",item.uuid)
            pantallaDetalles.putExtra("Titulo",item.Titulo)
            pantallaDetalles.putExtra("numeroTiquete",item.No_ticket)
            pantallaDetalles.putExtra("descripcion",item.descripcion)
            pantallaDetalles.putExtra("F_creado",item.F_creado)
            pantallaDetalles.putExtra("Estado",item.Estado)
            pantallaDetalles.putExtra("F_cerrado",item.F_cerrado)

            context.startActivity(pantallaDetalles)
        }

    }
}