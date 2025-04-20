package com.fishfromsandiego.whattodo.common.exceptions

class WhatToDoAppCaughtException(message: String) : Exception(message)

fun Exception.getUserMessage(): String =
    if (this is WhatToDoAppCaughtException && this.message != null) this.message!! else
        "Error occured"