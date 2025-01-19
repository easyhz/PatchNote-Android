package com.easyhz.patchnote.data.di.config

import javax.inject.Qualifier


@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class UserDataStore

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ConfigurationDataStore

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ReceptionSettingDataStore
