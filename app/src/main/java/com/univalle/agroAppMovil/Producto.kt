package com.univalle.agroAppMovil

data class Producto (var codigo:String, var name:String, var precio:String, var item:String, var url:String) {
    fun getFullCodigo():String = "$codigo"
    fun getFullName():String = "$name"
    fun getFullPrice():String = "$precio"
    fun getFullItem():String = "$item"
    fun getFullUrl():String = "$url"
}