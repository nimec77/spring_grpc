package com.example.spring_grpc.services

import Register
import RegistrationServiceGrpcKt
import com.google.protobuf.Int64Value
import kotlinx.coroutines.flow.*
import net.devh.boot.grpc.server.service.GrpcService
import org.slf4j.LoggerFactory
import registrationResult

@GrpcService
private class RegistrationService : RegistrationServiceGrpcKt.RegistrationServiceCoroutineImplBase() {
    private val logger = LoggerFactory.getLogger(javaClass)
    private val usersMap = mutableMapOf<Long, Register.User>()
    private val usersFlow = MutableSharedFlow<Register.User>()

    override suspend fun registerUser(request: Register.User): Register.RegistrationResult {
        logger.debug(
            "Registering userId:${request.id} user:${request.lastname} ${request.firstname} ${request.middlename}, " +
                    "age: ${request.age}, gender: ${request.gender.name}"
        )
        usersMap[request.id] = request
        usersFlow.emit(request)
        return registrationResult { succeeded = true }
    }

    override suspend fun getUserById(request: Int64Value): Register.User {
        return usersMap.getOrDefault(request.value, Register.User.getDefaultInstance())
    }

    override fun listUsers(request: Register.ListUsersInput): Flow<Register.User> {
        return usersFlow
    }
}