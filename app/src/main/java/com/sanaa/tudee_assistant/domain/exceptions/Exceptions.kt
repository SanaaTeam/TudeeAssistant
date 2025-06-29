package com.sanaa.tudee_assistant.domain.exceptions


open class DataSourceAccessException(message: String) : Exception(message)
open class StoringDataFailureException(message: String) : DataSourceAccessException(message)
open class RetrievingDataFailureException(message: String) : DataSourceAccessException(message)

class NotFoundException(message: String) : RetrievingDataFailureException(message)

class FailedToUpdateException(message: String) : StoringDataFailureException(message)
class FailedToAddException(message: String) : StoringDataFailureException(message)
class FailedToDeleteException(message: String) : StoringDataFailureException(message)

class ModifyDefaultCategoryNotAllowedException(message: String) :
    StoringDataFailureException(message)