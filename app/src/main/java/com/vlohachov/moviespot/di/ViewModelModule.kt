package com.vlohachov.moviespot.di

import com.vlohachov.moviespot.ui.main.MainViewModel
import com.vlohachov.moviespot.ui.movies.now.NowPlayingMoviesViewModel
import com.vlohachov.moviespot.ui.movies.popular.PopularMoviesViewModel
import com.vlohachov.moviespot.ui.movies.top.TopRatedMoviesViewModel
import com.vlohachov.moviespot.ui.movies.upcoming.UpcomingMoviesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel {
        MainViewModel(
            upcoming = get(),
            nowPlaying = get(),
            popular = get(),
            topRated = get(),
        )
    }

    viewModel {
        UpcomingMoviesViewModel(useCase = get())
    }

    viewModel {
        NowPlayingMoviesViewModel(useCase = get())
    }

    viewModel {
        PopularMoviesViewModel(useCase = get())
    }

    viewModel {
        TopRatedMoviesViewModel(useCase = get())
    }
}