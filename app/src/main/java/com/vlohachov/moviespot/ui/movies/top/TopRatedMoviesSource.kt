package com.vlohachov.moviespot.ui.movies.top

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.vlohachov.domain.Result
import com.vlohachov.domain.model.Movie
import com.vlohachov.domain.model.PaginatedData
import com.vlohachov.domain.usecase.TopRatedUseCase
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class TopRatedMoviesSource(private val useCase: TopRatedUseCase) : PagingSource<Int, Movie>() {

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let { position ->
            state.closestPageToPosition(anchorPosition = position)?.run {
                prevKey?.plus(1) ?: nextKey?.minus(1)
            }
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return try {
            val page = params.key ?: 1
            val result = loadPage(page = page)
            LoadResult.Page(
                data = result.data,
                prevKey = if (page == 1) null else page.minus(1),
                nextKey = result.page.plus(1),
            )
        } catch (e: Throwable) {
            LoadResult.Error(e)
        }
    }

    private suspend fun loadPage(page: Int): PaginatedData<Movie> =
        useCase.resultFlow(param = TopRatedUseCase.Param(page = page))
            .filter { result -> result is Result.Success }
            .map { result -> (result as Result.Success).value }
            .first()
}