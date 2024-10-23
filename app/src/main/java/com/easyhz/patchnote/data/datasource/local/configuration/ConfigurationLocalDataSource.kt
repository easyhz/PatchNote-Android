package com.easyhz.patchnote.data.datasource.local.configuration

interface ConfigurationLocalDataSource {
    suspend fun getPassword(): Result<String?>
    suspend fun updatePassword(password: String): Unit
    suspend fun checkEnteredPassword(): Result<Boolean>
    suspend fun updateEnteredPassword(isEntered: Boolean): Unit
}