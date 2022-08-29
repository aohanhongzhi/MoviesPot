package com.vlohachov.moviespot.ui.movies.upcoming

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.vlohachov.domain.usecase.UpcomingUseCase

class UpcomingMoviesViewModel(useCase: UpcomingUseCase) : ViewModel() {

    private companion object Constants {
        const val PageSize = 20
    }

    val movies = Pager(config = PagingConfig(pageSize = PageSize)) {
        UpcomingMoviesSource(useCase = useCase)
    }.flow.cachedIn(viewModelScope)
}