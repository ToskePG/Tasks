package llc.amplitudo.amplitudo_akademija.core.utils

sealed class NetworkResponse<T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T?) : NetworkResponse<T>(data = data)
    class Error<T>(message: String?) : NetworkResponse<T>(message = message)
    class Loading<T>(data: T?) : NetworkResponse<T>(data = data)
}
