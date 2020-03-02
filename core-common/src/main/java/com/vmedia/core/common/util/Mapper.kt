package com.vmedia.core.common.util

/**
 * Automatically turns simple mapper to list mapper
 *
 * @receiver simple mapper
 * @return list mapper
 */
fun <FROM, TO> Mapper<FROM, TO>.toListMapper(): ListMapper<FROM, TO> {
    return object : ListMapper<FROM, TO>() {
        override fun mapItem(from: FROM): TO = this@toListMapper.map(from)
    }
}

/**
 * Mapper interface
 * Converts an object from [FROM] type to [TO] type
 *
 * @param FROM source type
 * @param TO result type
 */
@FunctionalInterface
interface Mapper<FROM, TO> {
    fun map(from: FROM): TO
}

/**
 * List Mapper class
 * Implements [Mapper] interface
 *
 * @param FROM source type of list items
 * @param TO result type of list items
 */
abstract class ListMapper<FROM, TO> : Mapper<List<FROM>, List<TO>> {

    final override fun map(from: List<FROM>): List<TO> {
        return mutableListOf<TO>().apply {
            addAll(from.map(::mapItem))
        }
    }

    protected abstract fun mapItem(from: FROM): TO

}