package com.easyjob.jetpack.di

import android.content.Context
import com.easyjob.jetpack.data.store.UserPreferencesRepository
import com.easyjob.jetpack.repositories.AuthRepository
import com.easyjob.jetpack.repositories.AuthRepositoryImpl
import com.easyjob.jetpack.repositories.EditServicesRepository
import com.easyjob.jetpack.repositories.EditServicesRepositoryImpl
import com.easyjob.jetpack.repositories.ProfessionalProfileRepository
import com.easyjob.jetpack.repositories.ProfessionalProfileRepositoryImpl
import com.easyjob.jetpack.repositories.SearchScreenRepository
import com.easyjob.jetpack.repositories.SearchScreenRepositoryImpl
import com.easyjob.jetpack.services.AuthService
import com.easyjob.jetpack.services.AuthServiceImpl
import com.easyjob.jetpack.services.EditServicesService
import com.easyjob.jetpack.services.EditServicesServiceImpl
import com.easyjob.jetpack.services.ProfessionalProfileService
import com.easyjob.jetpack.services.ProfessionalProfileServiceImpl
import com.easyjob.jetpack.services.SearchScreenService
import com.easyjob.jetpack.services.SearchScreenServiceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideUserPreferencesRepository(
        @ApplicationContext context: Context // Necesitas `@ApplicationContext` aquí si es inyectado
    ): UserPreferencesRepository {
        return UserPreferencesRepository(context)
    }

    //Services

    @Provides
    @Singleton
    fun provideAuthService(): AuthService {
        return AuthServiceImpl()
    }

    @Provides
    @Singleton
    fun provideAuthSearchService(): SearchScreenService {
        return SearchScreenServiceImpl()
    }

    @Provides
    @Singleton
    fun provideProfessionalProfileService(): ProfessionalProfileService {
        return ProfessionalProfileServiceImpl()
    }

    @Provides
    @Singleton
    fun provideEditServicesService(): EditServicesService {
        return EditServicesServiceImpl()
    }

    //Repositories

    @Provides
    @Singleton
    fun provideAuthRepository(
        authService: AuthService,
        userPreferencesRepository: UserPreferencesRepository
    ): AuthRepository {
        return AuthRepositoryImpl(authService, userPreferencesRepository)
    }

    @Provides
    @Singleton
    fun provideSearchScreenRepository(
        searchScreenService: SearchScreenService
    ): SearchScreenRepository {
        return SearchScreenRepositoryImpl(searchScreenService)
    }

    @Provides
    @Singleton
    fun provideProfessionalProfileRepository(
        professionalProfileService: ProfessionalProfileService
    ): ProfessionalProfileRepository {
        return ProfessionalProfileRepositoryImpl(professionalProfileService)
    }

    @Provides
    @Singleton
    fun provideEditServicesRepository(
        editServicesService: EditServicesService
    ): EditServicesRepository {
        return EditServicesRepositoryImpl(editServicesService)
    }
}