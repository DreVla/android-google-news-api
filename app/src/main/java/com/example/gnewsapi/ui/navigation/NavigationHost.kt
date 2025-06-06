package com.example.gnewsapi.ui.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import androidx.paging.compose.LazyPagingItems
import com.example.gnewsapi.model.Article
import com.example.gnewsapi.ui.screen.fullarticle.FullArticleScreen
import com.example.gnewsapi.ui.screen.toparticles.TopArticlesScreen
import kotlin.reflect.typeOf

@Composable
fun NavigationHost(
    navController: NavHostController,
    articles: LazyPagingItems<Article>,
    columns: Int,
    isConnected: Boolean,
    isSaved: Boolean,
    onCheckIfSaved: (Article) -> Unit,
    onSaveArticle: (Article) -> Unit,
) {
    NavHost(
        navController = navController,
        startDestination = TopArticlesRoute(
            columns = columns,
        ),
        enterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Start,
                tween(500)
            )
        },
        exitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Start,
                tween(500)
            )
        },
        popEnterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.End,
                tween(500)
            )
        },
        popExitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.End,
                tween(500)
            )
        },
    ) {
        composable<TopArticlesRoute> {
            TopArticlesScreen(
                articles = articles,
                columns = columns,
                isConnected = isConnected,
                onArticleClick = { article ->
                    navController.navigate(
                        route = FullArticleRoute(
                            article = article,
                        ),
                    )
                }
            )
        }
        composable<FullArticleRoute>(
            typeMap = mapOf(
                typeOf<Article>() to CustomNavType.ArticleType,
            ),
        ) {
            val args = it.toRoute<FullArticleRoute>()

            LaunchedEffect(args.article) {
                onCheckIfSaved(args.article)
            }

            FullArticleScreen(
                article = args.article,
                isSaved = isSaved,
                onBackClick = {
                    navController.popBackStack()
                },
                onSaveClick = {
                    onSaveArticle(args.article)
                },
            )
        }
    }
}