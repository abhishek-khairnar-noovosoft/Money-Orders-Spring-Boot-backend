package com.example.moneyorders.exceptions

class CustomExceptions {
    class UserNotFoundException(message: String) : RuntimeException(message)
    class InvalidAmountException(message: String) : RuntimeException(message)
}