package com.vlohachov.moviespot.ui.components.movie

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.vlohachov.domain.model.movie.Movie
import com.vlohachov.moviespot.core.DummyMovies
import com.vlohachov.moviespot.ui.theme.MoviesPotTheme

@Composable
fun MoviesLazyRow(
    movies: List<Movie>,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(all = 16.dp),
    onClick: ((movie: Movie) -> Unit)? = null,
) {
    val itemModifier = Modifier
        .fillMaxHeight()
        .aspectRatio(
            ratio = .75f,
            matchHeightConstraintsFirst = true,
        )
    LazyRow(
        modifier = modifier,
        contentPadding = contentPadding,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        items(movies) { movie ->
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
}

@Preview(showBackground = true)
@Composable
fun MoviesLazyRowPreview() {
    MoviesPotTheme {
        MoviesLazyRow(
            modifier = Modifier
                .height(height = 160.dp)
                .fillMaxWidth(),
            movies = DummyMovies,
        )
    }
}