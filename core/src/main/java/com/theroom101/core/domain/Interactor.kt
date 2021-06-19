package com.theroom101.core.domain

abstract class SuspendableUseCase<TParams, TResult> {

    abstract suspend fun execute(params: TParams): TResult
}