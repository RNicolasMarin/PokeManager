package com.pokemanager.utils

object VisualUtils {

    /**
     * Expected values and results
     * 69	6,9 kg
     * 905	90,5 kg
     * 3520	352,0 kg
     * **/
    fun convertWeight(weight: Int): String {
        val weightDouble: Double = weight.toDouble() / 10
        var weightConverted = weightDouble.toString()
        weightConverted = weightConverted.replace('.', ',')
        return "$weightConverted kg"
    }

    /**
     * Expected values and results
     * 7	0,7 m
     * 17	1,7 m
     * 45	4,5 m
     * **/
    fun convertHeight(height: Int): String {
        val heightDouble: Double = height.toDouble() / 10
        var heightConverted = heightDouble.toString()
        heightConverted = heightConverted.replace('.', ',')
        return "$heightConverted m"
    }
}