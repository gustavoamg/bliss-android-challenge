package pt.viabilize.blissandroidchallenge.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import kotlinx.serialization.Serializable
import pt.viabilize.blissandroidchallenge.ui.avatarlistscreen.AvatarListScreen
import pt.viabilize.blissandroidchallenge.ui.emojilistscreen.EmojiListScreen
import pt.viabilize.blissandroidchallenge.ui.mainscreen.MainScreen

@Serializable
sealed interface NavDestination: NavKey {
    @Serializable
    data object MainScreen : NavDestination

    @Serializable
    data object EmojiListScreen : NavDestination

    @Serializable
    data object AvatarListScreen :  NavDestination
}

@Composable
fun AppNavigation(
    modifier: Modifier = Modifier
) {
    val backStack = rememberNavBackStack(NavDestination.MainScreen)

    NavDisplay(
        modifier = modifier,
        backStack = backStack,
        entryDecorators = listOf (
            rememberSaveableStateHolderNavEntryDecorator(), //Default decorator
            rememberViewModelStoreNavEntryDecorator() //ViewModel decorator
        ),
        entryProvider = entryProvider {
            entry<NavDestination.MainScreen> {
                MainScreen(
                    onNavigateToEmojiList = {
                        backStack.add(NavDestination.EmojiListScreen)
                    },
                    onNavigateToAvatarList = {
                        backStack.add(NavDestination.AvatarListScreen)
                    }
                )
            }
            entry<NavDestination.EmojiListScreen> {
                EmojiListScreen()
            }
            entry<NavDestination.AvatarListScreen> {
                AvatarListScreen()
            }
        }
    )
}