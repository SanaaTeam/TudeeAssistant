import androidx.room.TypeConverter
import com.sanaa.tudee_assistant.domain.model.Task.TaskPriority
import com.sanaa.tudee_assistant.domain.model.Task.TaskStatus

class Converters {

    @TypeConverter
    fun fromTaskStatus(status: TaskStatus): String = status.name

    @TypeConverter
    fun toTaskStatus(value: String): TaskStatus = TaskStatus.valueOf(value)

    @TypeConverter
    fun fromTaskPriority(priority: TaskPriority): String = priority.name

    @TypeConverter
    fun toTaskPriority(value: String): TaskPriority = TaskPriority.valueOf(value)
}
