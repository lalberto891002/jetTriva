package com.mypc.jettrivia.data

data class DataOrException<T,Boolean,E:Exception>(
    var data:T?,
    var loading:Boolean? = null,
    var e:E? = null)
