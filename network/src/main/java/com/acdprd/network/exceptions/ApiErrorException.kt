package com.acdprd.network.exceptions

class ApiErrorException(var apiError: ApiError) : RuntimeException(apiError.getError())