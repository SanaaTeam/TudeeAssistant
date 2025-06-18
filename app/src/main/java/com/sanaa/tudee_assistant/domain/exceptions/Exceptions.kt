package com.sanaa.tudee_assistant.domain.exceptions

open class TaskException : Exception()

class TaskNotFoundException() : TaskException()
class FailedToUpdateTaskException : TaskException()
class FailedToAddTaskException : TaskException()
class FailedToDeleteTaskException : TaskException()

open class CategoryException : Exception()

class CategoryNotFoundException : CategoryException()
class FailedToUpdateCategoryException : CategoryException()
class FailedToAddCategoryException : CategoryException()
class FailedToDeleteCategoryException : CategoryException()
class DefaultCategoryException : CategoryException()



class DatabaseFailureException(
    message: String,
    cause: Throwable? = null
) : Exception(message, cause)