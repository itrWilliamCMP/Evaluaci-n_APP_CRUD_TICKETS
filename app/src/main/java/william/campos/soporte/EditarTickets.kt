package william.campos.soporte

import Modelo.ClaseConexion
import Modelo.tickets
import RecyclerViewHelpers.Adaptador
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.UUID

class EditarTickets : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detalle_tickets_editar)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //mando a llamar a todos los elementos de la pantalla

        val txtUUIDDetalle = findViewById<TextView>(R.id.txtUUIDDetalle)
        val txtTituloDetalle = findViewById<TextView>(R.id.txtTituloDetalle)
        val txtNO_TicketDetalle = findViewById<TextView>(R.id.txtNO_TicketDetalle)
        val txtDescripcionDetalle = findViewById<TextView>(R.id.txtDescripcionDetalle)
        val txtF_creadoDetalle = findViewById<TextView>(R.id.txtF_creadpDetalle)
        val txtEstadoDetalle = findViewById<TextView>(R.id.txtEstadoDetalle)
        val txtF_cerradoDetalle = findViewById<TextView>(R.id.txtF_cerradoDetalle)
        val imgRegreso=findViewById<ImageView>(R.id.imgRegreso)

        val btnActualizar = findViewById<Button>(R.id.btn_actualizar)


        //recibir los valores


        val UUID_Tickets = intent.getStringExtra("uuid")
        val Titulo = intent.getStringExtra("Titulo")
        val numero_ticket = intent.getIntExtra("numeroTiquete",0)
        val descripcion = intent.getStringExtra("descripcion")
        val F_creado = intent.getStringExtra("F_creado")
        val Estado = intent.getStringExtra("Estado")
        val F_Cerrado = intent.getStringExtra("F_cerrado")

        // poner los valores recibidos en el textview

        Log.e("MisMensajes", "VALORES RECIBIDOS CON GETINTEXTRA")
        Log.e("MisMensajes", "uuid = "+UUID_Tickets)
        Log.e("MisMensajes", "titulo = "+Titulo)
        Log.e("MisMensajes", "no_ticket = "+numero_ticket)
        Log.e("MisMensajes", "descripcion = "+descripcion)
        Log.e("MisMensajes", "f_creado = "+F_creado)
        Log.e("MisMensajes", "estado = "+Estado)
        Log.e("MisMensajes", "f_cerrado = "+F_Cerrado)

        txtUUIDDetalle.text = UUID_Tickets
        txtTituloDetalle.text= Titulo
        txtNO_TicketDetalle.text=numero_ticket.toString()
        txtDescripcionDetalle.text = descripcion
        txtF_creadoDetalle.text = F_creado
        txtEstadoDetalle.text = Estado
        txtF_cerradoDetalle.text = F_Cerrado

        imgRegreso.setOnClickListener {
            val pantallaAtras = Intent(this,MainActivity::class.java)
            startActivity(pantallaAtras)
        }

        btnActualizar.setOnClickListener {
            GlobalScope.launch(Dispatchers.IO){



                //crear un objeto de la clase conexion
                val claseConexion= ClaseConexion().cadenaConexion()

                //crar una variable que contenga un preparedstatement
                val updateTicket = claseConexion?.prepareStatement("update Tickets set Titulo=?, No_ticket=?,descripcion=?,F_creado=?,Estado=?,F_cerrado=? where uuid=?")!!

                updateTicket.setString(1,txtTituloDetalle.text.toString())
                updateTicket.setInt(2,txtNO_TicketDetalle.text.toString().toInt())
                updateTicket.setString(3,txtDescripcionDetalle.text.toString())
                updateTicket.setString(4,txtF_creadoDetalle.text.toString())
                updateTicket.setString(5,txtEstadoDetalle.text.toString())
                updateTicket.setString(6,txtF_cerradoDetalle.text.toString())
                updateTicket.setString(7, txtUUIDDetalle.text.toString())
                updateTicket.executeUpdate()


            }
        }
    }
}