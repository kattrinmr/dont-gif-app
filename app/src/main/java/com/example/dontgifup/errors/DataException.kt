package com.example.dontgifup.errors

abstract class DataException(message: String) : Exception(message) {}

class  ServerException(val code: Int, message: String) : DataException(message) {}

class  CacheException(message: String) : DataException(message) {}