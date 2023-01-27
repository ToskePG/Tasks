package llc.amplitudo.amplitudo_akademija.data.remote.repos

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import llc.amplitudo.amplitudo_akademija.core.utils.NetworkResponse
import llc.amplitudo.amplitudo_akademija.data.remote.RetrofitInstance
import llc.amplitudo.amplitudo_akademija.data.remote.models.Task
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException

class TaskRepository {

    suspend fun getTasks(): Flow<NetworkResponse<List<Task>>> {
        return flow {
            emit(NetworkResponse.Loading(null))
            try {
                val response = RetrofitInstance.apiService.getTasks()
                val data = response.body()
                if (response.isSuccessful && response.body() != null) {
                    emit(NetworkResponse.Success(data = data))
                } else {
                    emit(NetworkResponse.Error(message = "An error occurred during communication with server. "))
                }
            } catch (e: IOException) {
                Timber.e("Request failed for the following reason: ${e.message}")
                emit(NetworkResponse.Error(message = "Request failed for the following reason: ${e.message}"))
            } catch (e: HttpException) {
                Timber.e("Request failed for the following reason: ${e.message}")
                emit(NetworkResponse.Error(message = "Request failed for the following reason: ${e.message}"))
            }
        }
    }
}
