package hph.app.domain.model

data class Profile (
    val group: Group,
    val course: Course,
    val program: Program,
    val name: String
)