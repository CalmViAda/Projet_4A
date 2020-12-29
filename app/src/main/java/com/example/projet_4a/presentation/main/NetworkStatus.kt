package com.example.projet_4a.presentation.main

enum class Status {
    RUNNING,
    SUCCESS,
    FAILED
}

class NetworkStatus(val status: Status, val message: String) {
    companion object{
        val LOADED: NetworkStatus
        val LOADING: NetworkStatus
        val ERROR: NetworkStatus
        val ENDOFLIST: NetworkStatus

        init {
            LOADED =
                NetworkStatus(
                    Status.SUCCESS,
                    "Success"
                )

            LOADING =
                NetworkStatus(
                    Status.RUNNING,
                    "Running"
                )

            ERROR =
                NetworkStatus(
                    Status.FAILED,
                    "Failed"
                )

            ENDOFLIST =
                NetworkStatus(
                    Status.FAILED,
                    "End of list"
                )
        }
    }
}