package llc.amplitudo.amplitudo_akademija.data.remote

import llc.amplitudo.amplitudo_akademija.data.remote.models.BodyResponse
import llc.amplitudo.amplitudo_akademija.data.remote.models.Task
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("/todos")
    suspend fun getTasks(): Response<List<Task>>

}