package io.indrian.whatmovies.data.mapper

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
     * @param path : String
     * */
    protected fun getPosterPath(path: String): String {

        return if (path.isNotEmpty()) {

            "https://image.tmdb.org/t/p/w342$path"
        } else {

            ""
        }
    }

    /**
     *
     * Backdrop URL + path
     * */
    protected fun getBackdropPath(path: String): String {

        return if (path.isNotEmpty()) {

            "https://image.tmdb.org/t/p/w780$path"
        } else {

            ""
        }
    }

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

        return if (dateStr == "") {

            Date()
        } else {

            val df = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            df.parse(dateStr)

            df.calendar.time
        }
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