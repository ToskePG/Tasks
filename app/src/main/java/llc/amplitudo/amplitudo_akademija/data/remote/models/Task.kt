package llc.amplitudo.amplitudo_akademija.data.remote.models

import com.google.gson.annotations.SerializedName

data class Task(
    @SerializedName("completed")
    val isCompleted: Boolean? = null,
    val id: Int? = null,
    val title: String? = null,
    val userId: Int? = null
)