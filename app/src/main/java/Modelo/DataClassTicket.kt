package Modelo

data class DataClassTicket (
    val NUMERO_TICKET : String,
    var TITULO_TICKET : String,
    var DESCRIPCIÓN_TICKET : String,
    var RESPONSABLE_TICKET: String,
    var EMAIL_CONTACTO_AUTOR : String,
    var TELEFONO_AUTOR : String,
    var UBICACIÓN : String,
    var ESTADO_TICKET : String
)