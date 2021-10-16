package com.univalle.agroAppMovil

class PuestoVenta {
    var namePuesto: String
    var cedula: String
    var password: String
    var celular: String
    var email: String

    constructor(namePuesto: String, cedula:String, password: String, celular: String, email: String) {
        this.namePuesto = namePuesto
        this.cedula = cedula
        this.password = password
        this.celular = celular
        this.email = email

    }
}

