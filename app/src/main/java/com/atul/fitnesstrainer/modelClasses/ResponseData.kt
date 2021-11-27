package com.atul.fitnesstrainer.modelClasses

data class ResponseData(
    var success: Boolean,
    var message: String,
    var data: List<Program>
)
