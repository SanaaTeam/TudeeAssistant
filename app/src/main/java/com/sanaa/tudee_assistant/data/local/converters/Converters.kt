import androidx.room.TypeConverter
import com.sanaa.tudee_assistant.domain.model.Task.TaskPriority
import com.sanaa.tudee_assistant.domain.model.Task.TaskStatus
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime

class Converters {

    @TypeConverter
    fun fromLocalDate(value: LocalDate?): String? =
        value?.toString() //  "2025-06-13"

    @TypeConverter
    fun toLocalDate(value: String?): LocalDate? =
        value?.let { LocalDate.parse(it) }

    @TypeConverter
    fun fromTaskStatus(status: TaskStatus): String = status.name

    @TypeConverter
    fun toTaskStatus(value: String): TaskStatus = TaskStatus.valueOf(value)

    @TypeConverter
    fun fromTaskPriority(priority: TaskPriority): String = priority.name

    @TypeConverter
    fun toTaskPriority(value: String): TaskPriority = TaskPriority.valueOf(value)

    @TypeConverter
    fun fromLocalDateTime(value: LocalDateTime?): String? {
        return value?.toString()
    }

    //Todo
    @TypeConverter
    fun toLocalDateTime(value: String?): LocalDateTime? {
        return value?.let { LocalDateTime.parse(it) }
    }
}
