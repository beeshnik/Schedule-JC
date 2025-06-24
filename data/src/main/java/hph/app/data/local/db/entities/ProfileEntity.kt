package hph.app.data.local.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "profiles")
data class ProfileEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val course: Int,
    val program: String,
    val name: String,
    val group: String,
    val color: Int,
    @ColumnInfo(name = "created_at") val createdAt: Long = System.currentTimeMillis()
)