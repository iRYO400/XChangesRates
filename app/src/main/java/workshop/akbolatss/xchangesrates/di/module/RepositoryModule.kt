package workshop.akbolatss.xchangesrates.di.module

import org.koin.dsl.module
import workshop.akbolatss.xchangesrates.data.repository.ChartRepositoryImpl
import workshop.akbolatss.xchangesrates.data.repository.ExchangeRepositoryImpl
import workshop.akbolatss.xchangesrates.domain.repository.ChartRepository
import workshop.akbolatss.xchangesrates.domain.repository.ExchangeRepository

val repositoryModule = module {

    single {
        ChartRepositoryImpl(get(), get()) as ChartRepository
    }

    single {
        ExchangeRepositoryImpl(get(), get()) as ExchangeRepository
    }
}