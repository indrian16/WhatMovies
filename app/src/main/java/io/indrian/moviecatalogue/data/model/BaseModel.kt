package io.indrian.moviecatalogue.data.model

import java.util.*

abstract class BaseModel {

    abstract val year: String

    protected fun getYear(releaseDate: Date): Int {

        val calendar = Calendar.getInstance(Locale.getDefault())
        calendar.time = releaseDate

        return calendar[(Calendar.YEAR)]
    }
}