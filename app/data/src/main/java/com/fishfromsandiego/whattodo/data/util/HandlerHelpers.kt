package com.fishfromsandiego.whattodo.data.util

import com.fishfromsandiego.whattodo.common.exceptions.WhatToDoAppCaughtException

fun getErrorFromStatusCode(code: Int): Throwable? {
    val codeStr = code.toString()
    if (codeStr.startsWith("2")) return null
    if (codeStr.startsWith("4")) return WhatToDoAppCaughtException("Content not awailable")
    if (codeStr.startsWith("5")) return WhatToDoAppCaughtException("Server error")
    return WhatToDoAppCaughtException("Error occured while downloading content")
}