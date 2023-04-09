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

    fun getDownloadPercentage(progress: Int, total: Int, percentageSymbol: String, doneText: String): String {
        if (progress == 0) return "0$percentageSymbol"
        if (progress == total) return doneText

        val percentage = (progress.toDouble() / total * 100).toInt()
        return "$percentage$percentageSymbol"
    }

    private val allowDash = listOf(250, 474, 718, 782, 783, 784)
    private val replaceWithDotSpace = listOf(122, 866)
    //29                 Nidoran ♀
    //32                 Nidoran ♂
    //772   type-null    Type: Null
    private val replaceWithSpace = listOf(439, 785, 786, 787, 788)

    fun convertName(id: Int, name: String): String {//remove everything after -
        if (id == 772) {
            return "Type: Null"
        }
        if (id == 29 || id == 32) {
            val symbol = if (id == 29) "♀" else "♂"
            return name.substring(0, name.indexOf("-")).firstToUpperCase() + symbol
        }
        if (!name.contains("-")) return name.firstToUpperCase()

        if (allowDash.contains(id)) {
            return replaceBetweenWith(name, "-")
        }
        if (replaceWithDotSpace.contains(id)) {
            return replaceBetweenWith(name,  ". ")
        }
        if (replaceWithSpace.contains(id)) {
            return replaceBetweenWith(name,  " ")
        }
        return name.substring(0, name.indexOf("-")).firstToUpperCase()
    }

    private fun replaceBetweenWith(name: String, replaceFor: String): String {
        val parts = name.split("-")
        val nameTransformed = StringBuffer()
        parts.forEach { nameTransformed.append(it.firstToUpperCase()).append(replaceFor) }
        val nameTransformedString = nameTransformed.toString()
        return nameTransformedString.substring(0, nameTransformedString.length-replaceFor.length)
    }
}