package com.sanaa.tudee_assistant.domain.exceptions

open class TaskException : Exception()

class TaskNotFoundException : TaskException()
class FailedToUpdateTaskException : TaskException()
class FailedToAddTaskException : TaskException()
class FailedToDeleteTaskException : TaskException()
class StatusFailedToUpdateException : TaskException()

open class CategoryException : Exception()

class CategoryNotFoundException : CategoryException()
class FailedToUpdateCategoryException : CategoryException()
class FailedToAddCategoryException : CategoryException()
class FailedToDeleteCategoryException : CategoryException()
class DefaultCategoryException : CategoryException()


