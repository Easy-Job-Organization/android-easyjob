package com.easyjob.jetpack.di

import android.content.Context
import android.util.Log
import com.easyjob.jetpack.data.store.UserPreferencesRepository
import com.easyjob.jetpack.repositories.*
import com.easyjob.jetpack.services.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import kotlinx.coroutines.flow.first
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideUserPreferencesRepository(
        @ApplicationContext context: Context
    ): UserPreferencesRepository {
        return UserPreferencesRepository(context)
    }

    // Define el AuthInterceptor que añade el token a cada solicitud
    @Provides
    @Singleton
    fun provideAuthInterceptor(userPreferencesRepository: UserPreferencesRepository): Interceptor {
        return Interceptor { chain ->
            val jwt = runBlocking {
                userPreferencesRepository.jwtFlow.first()
            }
            Log.d("AuthInterceptor", "Token JWT: $jwt")

            val request = chain.request().newBuilder()
                .apply {
                    jwt?.let {
                        addHeader("Authorization", "Bearer $it")
                    }
                }
                .build()

            chain.proceed(request)
        }
    }

    // Configura el OkHttpClient con el interceptor
    @Provides
    @Singleton
    fun provideOkHttpClient(authInterceptor: Interceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .build()
    }

    // Configura Retrofit con el OkHttpClient
    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.easyjob.com.co/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideAuthDateService(): AppointmentService {
        return AppointmentServiceImpl();
    }

    // Provee las instancias de los servicios usando Retrofit
    @Provides
    @Singleton
    fun provideAuthService(retrofit: Retrofit): AuthService {
        return retrofit.create(AuthService::class.java)
    }

    @Provides
    @Singleton
    fun provideSearchScreenService(retrofit: Retrofit): SearchScreenService {
        return retrofit.create(SearchScreenService::class.java)
    }

    @Provides
    @Singleton
    fun provideProfessionalProfileService(retrofit: Retrofit): ProfessionalProfileService {
        return retrofit.create(ProfessionalProfileService::class.java)
    }

    @Provides
    @Singleton
    fun provideEditServicesService(retrofit: Retrofit): EditServicesService {
        return retrofit.create(EditServicesService::class.java)
    }

    @Provides
    @Singleton
    fun provideEditServiceService(retrofit: Retrofit): EditServiceService {
        return retrofit.create(EditServiceService::class.java)
    }

    @Provides
    @Singleton
    fun provideChatService(): ChatsService {
        return ChatsServiceImpl()
    }

    @Provides
    @Singleton
    fun provideProfileService(): ProfileService {
        return ProfileServiceImpl()
    }



    // Repositories

    @Provides
    @Singleton
    fun provideProfileRepository(
        profileService: ProfileService
    ): ProfileRepository {
        return ProfileRepositoryImpl(profileService)
    }

    @Provides
    @Singleton
    fun provideAuthDateRepository(
        appointmentService: AppointmentService
    ): AppointmentRepository {
        return AppointmentRepositoryImpl(appointmentService)
    }

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


    @Provides
    @Singleton
    fun provideEditServiceRepository(
        editServiceService: EditServiceService
    ): EditServiceRepository {
        return EditServiceRepositoryImpl(editServiceService)
    }

    @Provides
    @Singleton
    fun provideChatRepository(
        chatsService: ChatsService,
        userPreferencesRepository: UserPreferencesRepository
    ): ChatsRepository {
        return ChatsRepositoryImpl(chatsService, userPreferencesRepository)
    }
}
