package com.vlohachov.shared.domain.usecase.movie

import com.vlohachov.shared.domain.Result
import com.vlohachov.shared.domain.asResult
import com.vlohachov.shared.domain.core.UseCase
import com.vlohachov.shared.domain.model.movie.keyword.Keyword
import com.vlohachov.shared.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow

public class LoadKeywords(
    private val repository: MovieRepository,
) : UseCase<LoadKeywords.Param, List<Keyword>> {

    public data class Param(val id: Long)

    override fun invoke(param: Param): Flow<Result<List<Keyword>>> =
        repository.getMovieKeywords(id = param.id)
            .asResult()

}
