package com.example.jetweatherforecast.data
// generics
class DataorException<T,Boolean, E: Exception> (
    var data : T? = null,
    var loading: kotlin.Boolean? = null,
    var e: E? = null)
