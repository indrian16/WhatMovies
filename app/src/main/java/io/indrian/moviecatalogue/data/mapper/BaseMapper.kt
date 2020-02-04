package io.indrian.moviecatalogue.data.mapper

import java.text.SimpleDateFormat
import java.util.*

/**
 * @author INDRIAN (rg16)
 * @param E is a Entity Data from json Service
 * @param M is a Model Object Model
 * */
abstract class BaseMapper<E, M> {

    /**
     *
     * Poster URL + path
     * */
    protected val posterUrl = "https://image.tmdb.org/t/p/w342"

    /**
     *
     * Backdrop URL + path
     * */
    protected val backdropUrl = "https://image.tmdb.org/t/p/w780"

    /**
     *
     * From Entity to Model
     * @param entity : E
     * */
    abstract fun toModel(entity: E): M

    /**
     *
     * From Model to Entity
     * @param model : M
     * */
    abstract fun toEntity(model: M): E

    /**
     *
     * Parse Date From String
     * @param dateStr : String
     * */
    protected fun parseDate(dateStr: String): Date {

        val df = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        df.parse(dateStr)

        return df.calendar.time
    }

    /**
     *
     * Give a message if there is an empty overview
     * @param overview : String | from Entity
     * */
    protected fun safeOverview(overview: String):String {

        return if (overview.isEmpty()) {

            "Not Support this language"
        } else {

            overview
        }
    }
}