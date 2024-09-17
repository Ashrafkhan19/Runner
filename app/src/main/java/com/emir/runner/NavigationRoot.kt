package com.emir.runner

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.auth.presentation.intro.IntroScreenRoot
import com.example.auth.presentation.login.LoginScreenRoot
import com.example.auth.presentation.register.RegisterScreenRoot
import com.example.run.presentation.active_run.ActiveRunScreenRoot
import com.example.run.presentation.run_overview.RunOverviewScreenRoot
import kotlinx.serialization.Serializable

@Composable
fun NavigationRoot(
    navController: NavHostController,
    isLoggedIn: Boolean,
) {
    NavHost(
        navController = navController, startDestination = if (isLoggedIn) Route.Run else Route.Auth
    ) {
        authGraph(navController)
        runGraph(navController)
    }
}

private fun NavGraphBuilder.authGraph(navController: NavHostController) {

    navigation<Route.Auth>(
        startDestination = Route.Intro,
    ) {

        composable<Route.Intro> {
            IntroScreenRoot(onSignUpClick = {
                navController.navigate(Route.Register)
            }, onSignInClick = {
                navController.navigate(Route.Login)
            })
        }
        composable<Route.Register> {
            RegisterScreenRoot(onSignInClick = {
                navController.navigate(Route.Login) {
                    popUpTo(Route.Register) {
                        inclusive = true
                        saveState = true
                    }
                    restoreState = true
                }
            }, onSuccessfulRegistration = {
                navController.navigate(Route.Login)
            })
        }

        composable<Route.Login> {
            LoginScreenRoot(
                onLoginSuccess = {
                    navController.navigate(Route.Run) {
                        popUpTo<Route.Auth>()
                    }
                },
                onSignUpClick = {
                    navController.navigate(Route.Register) {
                        popUpTo<Route.Login> {
                            inclusive = true
                            saveState = true

                        }
                        restoreState = true
                    }
                },
            )
        }
    }
}

private fun NavGraphBuilder.runGraph(navController: NavHostController) {
    navigation<Route.Run>(
        startDestination = Route.RunOverview,
    ) {
        composable<Route.RunOverview> {
            RunOverviewScreenRoot(
                onStartRunClick = {
                    navController.navigate(Route.ActiveRun)
                }
            )
        }

        composable<Route.ActiveRun>() {
            ActiveRunScreenRoot()
        }
    }
}

@Serializable
sealed interface Route {

    @Serializable
    data object Auth : Route

    @Serializable
    data object Intro : Route

    @Serializable
    data object Login : Route

    @Serializable
    data object Register : Route

    @Serializable
    data object Run : Route

    @Serializable
    data object RunOverview : Route

    @Serializable
    data object ActiveRun : Route
}