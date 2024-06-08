package Modelo

import java.util.UUID

data class tickets(
    val uuid: String,
    var Titulo: String,
    val No_ticket: Int,
    val descripcion: String,
    val F_creado: String,
    val Estado: String,
    val F_cerrado: String

) {

}