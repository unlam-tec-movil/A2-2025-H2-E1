package ar.edu.unlam.mobile.scaffolding.data.datasources.network.request

data class UpdateProfileRequest(
    val name: String,
    val avatar_url:String
)

data class SendTuistRequest(
    val message: String
)