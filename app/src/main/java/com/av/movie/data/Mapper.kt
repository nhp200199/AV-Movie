package com.av.movie.data

interface Mapper<I, O> {
    fun map(input: I): O
}

//LIST MAPPER
// Non-nullable to Non-nullable
interface ListMapper<I, O>: Mapper<List<I>, List<O>>

// Nullable to Non-nullable
interface NullableInputListMapper<I, O>: Mapper<List<I>?, List<O>>

// Non-nullable to Nullable
interface NullableOutputListMapper<I, O>: Mapper<List<I>, List<O>?>