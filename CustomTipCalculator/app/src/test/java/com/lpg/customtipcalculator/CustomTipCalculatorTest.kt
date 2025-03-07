package com.lpg.customtipcalculator

import org.junit.Test

import org.junit.Assert.*
import java.text.NumberFormat


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 *
 * Los test no usan logica como los métodos regulares. No les interesa como están  implementando las cosas
 * Prueban estrictamente una salida esperada para una determinada entrada.
 *
 * Usualmente terminan con una asercion "assert", lo que es usado para asegurarse que una condicion es conseguida.
 */
//Esto esta mal hecho ya que deberia sacarlo desde el codigo original a los metodo para testear
class CustomTipCalculatorTest {
    @Test
    fun calculateTip_20PercentNoRoundup() {
        val amount = 10.00
        val tipPercent = 20.00
        val expectedTip = NumberFormat.getCurrencyInstance().format(2)
        val actualTip = calculateTip2(amount = amount, tipPercent = tipPercent, false)
        assertEquals(expectedTip, actualTip)
    }

    private fun calculateTip2(amount: Double, tipPercent: Double = 15.0, roundUp: Boolean): String {
        var tip = tipPercent / 100 * amount
        if (roundUp) {
            tip = kotlin.math.ceil(tip)
        }
        return NumberFormat.getCurrencyInstance().format(tip)
    }
}
