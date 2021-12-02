package com.fb.weathertest.di

import com.fb.weathertest.BuildConfig
import com.fb.weathertest.data.remote.WeatherRepository
import com.fb.weathertest.data.remote.api.OpenWeatherApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {

    @Provides
    @Singleton
    @Named("weather")
    fun provideRetrofit(client: OkHttpClient) = Retrofit
        .Builder()
        .baseUrl(BuildConfig.WEATHER_URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .client(client)
        .build()

    @Provides
    fun provideOkHttp() = OkHttpClient
        .Builder().also { builder ->
            if (BuildConfig.DEBUG) {
                builder.addInterceptor(
                    HttpLoggingInterceptor().also {
                        it.level = HttpLoggingInterceptor.Level.BODY
                    }
                )
            }
        }.build()

    @Provides
    @Singleton
    fun provideOpenWeatherApiService(
        @Named("weather") retrofit: Retrofit
    ) = retrofit.create(OpenWeatherApi::class.java)

    @Provides
    @Singleton
    fun provideWeatherRepository(api: OpenWeatherApi) = WeatherRepository(api)
}
