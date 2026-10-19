package org.example

fun readTextFromResource(resourceName: String) = ClassLoader.getSystemResource(resourceName).readText() 