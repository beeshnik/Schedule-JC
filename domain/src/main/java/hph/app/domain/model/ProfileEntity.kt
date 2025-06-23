package hph.app.domain.model

data class ProfileDomainEntity(
    val id: Int = 0,
    val course: Int,
    val program: String,
    val name: String,
    val group: String,
    val createdAt: Long = System.currentTimeMillis()
)