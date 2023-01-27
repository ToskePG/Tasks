package llc.amplitudo.amplitudo_akademija.data.remote.models

data class BodyResponse<T>(
    val data: T? = null,
    val message: String? = null,
    val statusCode: Int? = null
)
