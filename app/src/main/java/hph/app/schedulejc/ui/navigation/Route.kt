package hph.app.schedulejc.ui.navigation

sealed class Route(val route: String) {
    object Main : Route("main")
    object CreateProfile : Route("createRoute")
    object GetSchedule: Route("getSchedule/{profileId}")

    object Settings : Route("settings/{section}")  // Динамический параметр
}