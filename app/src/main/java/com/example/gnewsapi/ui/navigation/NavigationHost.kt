package com.example.gnewsapi.ui.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.gnewsapi.model.Article
import com.example.gnewsapi.ui.screen.components.LoadingOverlay
import com.example.gnewsapi.ui.screen.fullarticle.FullArticleScreen
import com.example.gnewsapi.ui.screen.toparticles.TopArticlesScreen
import kotlin.reflect.typeOf

@Composable
fun NavigationHost(
    navController: NavHostController,
    articles: List<Article>,
    columns: Int,
    isLoading: Boolean,
) {
    NavHost(
        navController = navController,
        startDestination = TopArticlesRoute(
            articles = articles,
            columns = columns,
        ),
        enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Start, tween(500)) },
        exitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Start, tween(500)) },
        popEnterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.End, tween(500)) },
        popExitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.End, tween(500)) },
    ) {
        composable<TopArticlesRoute>(
            typeMap = mapOf(
                typeOf<Article>() to CustomNavType.ArticleType,
                typeOf<List<Article>>() to CustomNavType.ArticleListType,
            )
        ) {
            LoadingOverlay(isLoading = isLoading) {
                TopArticlesScreen(
                    articles = articles,
                    columns = columns,
                    onArticleClick = { article ->
                        navController.navigate(
                            route = FullArticleRoute(
                                article = article,
                            ),
                        )
                    }
                )
            }
        }
        composable<FullArticleRoute>(
            typeMap = mapOf(
                typeOf<Article>() to CustomNavType.ArticleType,
                typeOf<List<Article>>() to CustomNavType.ArticleListType,
            ),
        ) {
            val args = it.toRoute<FullArticleRoute>()
            FullArticleScreen(
                article = args.article,
                onBackClick = {
                    navController.popBackStack()
                },
            )
        }
    }
}