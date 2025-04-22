package com.fishfromsandiego.whattodo.common

import com.fishfromsandiego.whattodo.common.exceptions.WhatToDoAppCaughtException
import com.fishfromsandiego.whattodo.common.exceptions.getUserMessage
import org.junit.Test
import org.junit.jupiter.api.Assertions.*
import java.io.NotSerializableException

class CustomExceptionsTest {
    private val defaultMessage = "Error occured"

    @Test
    fun `app exception should preserve message on getUserMessage call`() {
//        Arrange
        val message = "Specific Exception"
        val e = WhatToDoAppCaughtException(message)

//        Act
        val userReadableMessage = e.getUserMessage()

//        Test
        assertEquals(e.message, userReadableMessage)
        assertEquals(message, userReadableMessage)
    }

    @Test
    fun `non-project exception should show default message on getUserMessage call`() {
//        Arrange
        val firstMessage = "not ${defaultMessage}"
        val secondMessage: String? = null
        val e1 = IllegalStateException(firstMessage)
        val e2 = NotSerializableException(secondMessage)

//        Act
        val userReadableFirst = e1.getUserMessage()
        val userReadableSecond = e2.getUserMessage()

//        Test
        assertNotEquals(firstMessage, userReadableFirst)
        assertNotEquals(secondMessage, userReadableSecond)
        assertEquals(userReadableFirst, defaultMessage)
        assertEquals(userReadableSecond, defaultMessage)
    }
}