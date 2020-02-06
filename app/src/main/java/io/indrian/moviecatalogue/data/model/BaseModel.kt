package io.indrian.moviecatalogue.data.model

import java.util.*

abstract class BaseModel {

    abstract val year: String
    abstract val fiveStart: Float

    protected fun getYear(releaseDate: Date): Int {

        val calendar = Calendar.getInstance(Locale.getDefault())
        calendar.time = releaseDate

        return calendar[(Calendar.YEAR)]
    }

    protected fun calculateFiveStart(voteAverage: Double): Float {

        return (voteAverage / 2).toFloat()
    }
}