package william.campos.soporte

import Modelo.tickets
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class DetalleTickets : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detalle_tickets)
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
        val txtF_creadpDetalle = findViewById<TextView>(R.id.txtF_creadpDetalle)
        val txtEstadoDetalle = findViewById<TextView>(R.id.txtEstadoDetalle)
        val txtF_cerradoDetalle = findViewById<TextView>(R.id.txtF_cerradoDetalle)
        val imgRegreso=findViewById<ImageView>(R.id.imgRegreso)

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
        txtF_creadpDetalle.text = F_creado
        txtEstadoDetalle.text = Estado
        txtF_cerradoDetalle.text = F_Cerrado

        imgRegreso.setOnClickListener {
            val pantallaAtras = Intent(this,MainActivity::class.java)
            startActivity(pantallaAtras)
        }
    }
}