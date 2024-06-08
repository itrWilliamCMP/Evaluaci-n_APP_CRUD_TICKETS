package william.campos.soporte

import Modelo.ClaseConexion
import Modelo.tickets
import RecyclerViewHelpers.Adaptador
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.UUID
import android.util.Log


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //mandar a llamar a todos los elementos de la pantalla

        val txtTitulo =findViewById<EditText>(R.id.txtTitulo)
        val txtNO_Ticket = findViewById<EditText>(R.id.txtNO_Ticket)
        val txtDescripcion = findViewById<EditText>(R.id.txtDescripcion)
        val txtFecha_Creado = findViewById<EditText>(R.id.txtFecha_Creado)
        val txtEstado = findViewById<EditText>(R.id.txtEstado)
        val txtF_cerrado = findViewById<EditText>(R.id.txtFecha_cerrado)
        val btnAgregar = findViewById<Button>(R.id.btnAgregar)

        fun limpiar(){
            txtTitulo.setText("")
            txtNO_Ticket.setText("")
            txtDescripcion.setText("")
            txtFecha_Creado.setText("")
            txtEstado.setText("")
            txtF_cerrado.setText("")
        }

        ////////////////////////////////TODO:mostrar datos ////////////////////////

        val rcvtikets = findViewById<RecyclerView>(R.id.rcvTickets)

        //asignar un layout al reciledview

        rcvtikets.layoutManager= LinearLayoutManager(this)

        //funcion para obtener datos
        fun obtenerDatos():List<tickets>{
            val objConexion=ClaseConexion().cadenaConexion()

            val statement = objConexion?.createStatement()
            val resultSet=statement?.executeQuery("select * from Tickets")!!

            val Tickets = mutableListOf<tickets>()

            while (resultSet.next()){
                val uuid=resultSet.getString("uuid")
                val Titulo = resultSet.getString("Titulo")
                val numeroTiquete = resultSet.getInt("no_ticket")
                val descripcion = resultSet.getString("descripcion")
                val fecha_creado = resultSet.getString("F_creado")
                val Estado = resultSet.getString("Estado")
                val fecha_cerrado = resultSet.getString("F_cerrado")
                val tickets = tickets(uuid,Titulo,numeroTiquete, descripcion, fecha_creado, Estado, fecha_cerrado)
                Tickets.add(tickets)

                Log.d("MisMensajes", "uuid = "+uuid)
                Log.d("MisMensajes", "titulo = "+Titulo)
                Log.d("MisMensajes", "no_ticket = "+numeroTiquete)
                Log.d("MisMensajes", "descripcion = "+descripcion)
                Log.d("MisMensajes", "f_creado = "+fecha_creado)
                Log.d("MisMensajes", "estado = "+Estado)
                Log.d("MisMensajes", "f_ceraado = "+fecha_cerrado)

            }
            return Tickets
        }

        //asignar un adaptador

        CoroutineScope ( Dispatchers.IO) .launch {
            val tickets=obtenerDatos()
            withContext(Dispatchers.Main){
                val miAdapter = Adaptador(tickets)
                rcvtikets.adapter=miAdapter
            }
        }
        ///////////////////////////////// TODO:GUARDAR Tickets////////////////////////////////////
        // programar el boton
        btnAgregar.setOnClickListener {
            GlobalScope.launch(Dispatchers.IO){

                //guardar datos

                //crear un objeto de la clase conexion
                val claseConexion=ClaseConexion().cadenaConexion()

                //crar una variable que contenga un preparedstatement

                val addTickets=claseConexion?.prepareStatement("insert into Tickets(uuid ,Titulo,No_ticket,descripcion,F_creado,Estado,F_cerrado)values(?,?,?,?,?,?,?)")!!

                addTickets.setString(1, UUID.randomUUID().toString())
                addTickets.setString(2,txtTitulo.text.toString())
                addTickets.setInt(3,txtNO_Ticket.text.toString().toInt())
                addTickets.setString(4,txtDescripcion.text.toString())
                addTickets.setString(5,txtFecha_Creado.text.toString())
                addTickets.setString(6,txtEstado.text.toString())
                addTickets.setString(7,txtF_cerrado.text.toString())
                addTickets.executeUpdate()

                val nuevosTickets=obtenerDatos()

                withContext(Dispatchers.Main){
                    (rcvtikets.adapter as? Adaptador)?.actualizarLista(nuevosTickets)
                }
            }
        }

    }
}