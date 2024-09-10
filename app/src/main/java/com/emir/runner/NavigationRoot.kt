package com.emir.runner

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.auth.presentation.intro.IntroScreenRoot
import com.example.auth.presentation.login.LoginScreenRoot
import com.example.auth.presentation.register.RegisterScreenRoot
import kotlinx.serialization.Serializable

@Composable
fun NavigationRoot(
    navController: NavHostController,
) {
    NavHost(
        navController = navController, startDestination = Route.Auth
    ) {
        authGraph(navController)
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

        composable<Route.Run> {
            Text("Run")
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
}