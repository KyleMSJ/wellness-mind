package ifsp.project.welnessmind

import EditScreen
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ifsp.project.welnessmind.presentation.theme.WelnessMindTheme
import ifsp.project.welnessmind.presentation.ui.ConditionalScreen
import ifsp.project.welnessmind.presentation.ui.FirstScreen
import ifsp.project.welnessmind.presentation.ui.recovery.RecoveryScreen
import ifsp.project.welnessmind.presentation.ui.sign_in.SignInScreen
import ifsp.project.welnessmind.presentation.ui.sign_up.SignUpScreen


@Composable
fun WelnessMindApp() {
    WelnessMindTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            val appState = rememberAppState()

            Scaffold { innerPaddingModifier ->
                NavHost(
                    navController = appState.navController,
                    startDestination = FIRST_SCREEN,
                    modifier = Modifier.padding(innerPaddingModifier)
                ) {
                    appGraph(appState)
                }
            }
        }
    }
}

@Composable
fun rememberAppState(navController: NavHostController = rememberNavController()) =
    remember(navController) {
        WelnessMindAppState(navController)
    }

fun NavGraphBuilder.appGraph(appState: WelnessMindAppState) {
    composable(FIRST_SCREEN) { FirstScreen(navController = appState.navController) }
    composable(CONDITIONAL_SCREEN) { ConditionalScreen(navController = appState.navController) }
    composable(SIGN_IN_SCREEN) {
        SignInScreen(openAndPopUp = { route, popUp ->
            appState.navigateAndPopUp(
                route,
                popUp
            )
        })
    }
    composable(SIGN_UP_SCREEN) {
        SignUpScreen(openAndPopUp = { route, popUp ->
            appState.navigateAndPopUp(
                route,
                popUp
            )
        })
    }
    composable(EDIT_SCREEN) { EditScreen(openAndPopUp = { route, popUp ->
        appState.navigateAndPopUp(
            route,
            popUp
        )
    }) }

    composable(RECOVERY_SCREEN) { RecoveryScreen(openAndPopUp = { route, popUp ->
        appState.navigateAndPopUp(
            route,
            popUp
        )
    }) }
}