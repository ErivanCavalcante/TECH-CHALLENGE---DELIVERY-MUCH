package com.erivan.santos.deliverymuchtest.presentation.viewmodel

//Verifica se o evento do view model ja foi handled
class Event<out T>(private val content: T) {
    //Guarda se foi handled
    private var handled = false

    fun pegaContentSeNaoFoiHandled() : T? {
        return if (handled) null else {
            handled = true
            content
        }
    }
}