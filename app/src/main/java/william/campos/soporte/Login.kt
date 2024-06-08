package william.campos.soporte

import Modelo.ClaseConexion
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //Explicacion del profesor:
        //1- Mandamos a traer a todos los elementos de la vista
        val txtCorreoElectronico = findViewById<EditText>(R.id.txtCorreoElectronico)
        val txtContrasena = findViewById<EditText>(R.id.txtContrasena)
        val btnEntrar = findViewById<Button>(R.id.btnEntrar)
        val btnRegistrarse = findViewById<Button>(R.id.btnRegistrarse)

        //2- Se programa los botones
        btnEntrar.setOnClickListener {
            //Se preparo el intent para cambiar a la pantalla de MainActivity
            val pantallaPrincipal = Intent(this, MainActivity::class.java)
            //Dentro de una corrutina hago un select en la base de datos
            GlobalScope.launch(Dispatchers.IO) {
                //1-Creo un objeto de la clase conexion
                val objConexion = ClaseConexion().cadenaConexion()
                //2- Se crea una variable que contenga un PrepareStatement
                //Se hace un select where el correo y la contraseña sean iguales a
                //los que el usuario escribe
                //Si el select encuentra un resultado es por que el usuario y contraseña si están
                //en la base de datos, si se equivoca al escribir algo, no encontrará nada el select
                val verificarUsuario = objConexion?.prepareStatement("SELECT * FROM Usuarios1 WHERE correoElectronico = ? AND contrasena = ?")!!
                verificarUsuario.setString(1, txtCorreoElectronico.text.toString())
                verificarUsuario.setString(2, txtContrasena.text.toString())
                val resultado = verificarUsuario.executeQuery()
                //Si encuentra un resultado
                if (resultado.next()) {
                    startActivity(pantallaPrincipal)
                } else {
                    println("Usuario no encontrado")
                }
            }
        }

        btnRegistrarse.setOnClickListener {
            //Cambio de pantalla
            val pantallaRegistrarse = Intent(this, Registrar::class.java)
            startActivity(pantallaRegistrarse)
        }
    }
}