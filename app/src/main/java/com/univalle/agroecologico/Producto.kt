package com.univalle.agroecologico

data class Producto (var name:String, var precio:String, var item:String) {
    fun getFullName():String = "$name $precio"
}