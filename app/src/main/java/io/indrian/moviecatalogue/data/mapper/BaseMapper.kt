package io.indrian.moviecatalogue.data.mapper

/**
 *
 * @param E is a Entity Data from json Service
 * @param M is a Model Object Model
 * */
interface BaseMapper<E, M> {

    /**
     *
     * Entity Mapper to Model
     * @param entity
     * */
    fun toModel(entity: E): M
}