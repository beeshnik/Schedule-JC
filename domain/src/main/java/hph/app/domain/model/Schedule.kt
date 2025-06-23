package hph.app.domain.model

import java.time.DayOfWeek
import java.time.LocalDate

data class Lesson(
    val subject: String,
    val subGroup: Int?,
    val time: ScheduledTime,
    val lecturer: String?,
    val places: List<LessonPlace>? = null,
    val links: List<String>? = null,
    val additionalInfo: List<String>? = null,
    val lessonType: LessonType,
    val parentScheduleType: ScheduleType,
)  {
    fun isOnline(): Boolean {
        if (links?.isNotEmpty() == true) return true
        if (places == null) return false
        return (places.all { it.building == null } || places.all { it.building == 0 }) && lessonType != LessonType.ENGLISH
    }
}

enum class LessonType(val type: String) {
    LECTURE("Лекция") {
        override fun reformatSubject(subject: String): String {
            return subject
                .replace("(лекция)", "")
                .replace("(лекции)", "")
                .replace("(лек.)", "")
                .trim()
        }
    },
    SEMINAR("Семинар") {
        override fun reformatSubject(subject: String): String {
            return subject
                .replace("(семинар)", "")
                .replace("(практ.)", "")
                .trim()
        }
    },
    EXAM("Экзамен") {
        override fun reformatSubject(subject: String): String {
            return subject.replace("ЭКЗАМЕН", "").trim()
        }
    },
    INDEPENDENT_EXAM("Независимый экзамен"),
    TEST("Зачёт") {
        override fun reformatSubject(subject: String): String {
            return subject.replace("ЗАЧЕТ", "").trim()
        }
    },
    PRACTICE("Практика") {
        override fun reformatSubject(subject: String): String {
            return if (subject == "ПРАКТИКА") ""
            else subject
        }
    },
    COMMON_MINOR("Майнор"),
    COMMON_ENGLISH("Английский"),
    ENGLISH("Английский"),
    STATEMENT("Ведомость"),
    UNDEFINED_AED("ДОЦ по выбору"),
    AED("ДОЦ") {
        override fun reformatSubject(subject: String): String {
            return subject
                .replace("[", "")
                .replace("]", "")
                .trim()
        }
    },
    CONSULT("Консультация"),
    EVENT("Мероприятие");

    open fun reformatSubject(subject: String): String {
        return subject
    }
}

data class LessonPlace(
    val office: String?,
    val building: Int?,
)

enum class ScheduleType {
    QUARTER_SCHEDULE,
    WEEK_SCHEDULE,
    SESSION_SCHEDULE,
}

data class ScheduledTime(
    val dayOfWeek: DayOfWeek,
    val date: String,
    val startTime: String,
    val endTime: String,
)