package com.buffetapp.pro.ClasesR3

import com.google.firebase.firestore.Exclude

data class AntojitosProducto(@get:Exclude var id: String? = null,
                             var name:String? = null,
                             var description: String? = null,
                             var imgUrl: String? = null,
                             var quantity: Int = 0,
                             var price: Double = 0.0){
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as AntojitosProducto

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }
}
