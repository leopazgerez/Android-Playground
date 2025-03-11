package com.lpg.lunchtrayapp.utils

import java.text.NumberFormat

fun Double.formatPrice(): String {
    return NumberFormat.getCurrencyInstance().format(this)
}