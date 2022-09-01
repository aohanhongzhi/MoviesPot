package com.vlohachov.moviespot.ui.components.movie

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import coil.compose.rememberAsyncImagePainter
import com.vlohachov.domain.model.movie.Movie

@Composable
fun MoviesPaginatedGrid(
    columns: GridCells,
    movies: LazyPagingItems<Movie>,
    onError: (error: Throwable) -> Unit,
    modifier: Modifier = Modifier,
    onClick: ((movie: Movie) -> Unit)? = null,
    state: LazyGridState = rememberLazyGridState(),
    contentPadding: PaddingValues = MoviesPaginatedGridDefaults.ContentPadding,
    verticalArrangement: Arrangement.Vertical = MoviesPaginatedGridDefaults.VerticalArrangement,
    horizontalArrangement: Arrangement.Horizontal = MoviesPaginatedGridDefaults.HorizontalArrangement,
) {
    val itemModifier = Modifier
        .fillMaxWidth()
        .aspectRatio(ratio = .75f)

    LazyVerticalGrid(
        modifier = modifier,
        state = state,
        columns = columns,
        contentPadding = contentPadding,
        verticalArrangement = verticalArrangement,
        horizontalArrangement = horizontalArrangement,
    ) {
        items(count = movies.itemCount) { index ->
            movies[index]?.let { movie ->
                if (onClick != null) {
                    Poster(
                        modifier = itemModifier,
                        painter = rememberAsyncImagePainter(model = movie.posterPath),
                        contentDescription = movie.title,
                        onClick = { onClick(movie) },
                    )
                } else {
                    Poster(
                        modifier = itemModifier,
                        painter = rememberAsyncImagePainter(model = movie.posterPath),
                        contentDescription = movie.title,
                    )
                }
            }
        }

        movies.apply {
            when {
                loadState.append is LoadState.Loading ->
                    item { Progress(modifier = itemModifier) }
                loadState.refresh is LoadState.Error ->
                    onError((loadState.refresh as LoadState.Error).error)
                loadState.append is LoadState.Error ->
                    onError((loadState.append as LoadState.Error).error)
            }
        }
    }
}

@Composable
private fun Progress(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator()
    }
}

object MoviesPaginatedGridDefaults {
    private val ItemsSpace: Dp = 16.dp

    val ContentPadding: PaddingValues = PaddingValues(all = ItemsSpace)

    val VerticalArrangement: Arrangement.Vertical = Arrangement.spacedBy(space = ItemsSpace)

    val HorizontalArrangement: Arrangement.Horizontal = Arrangement.spacedBy(space = ItemsSpace)
}