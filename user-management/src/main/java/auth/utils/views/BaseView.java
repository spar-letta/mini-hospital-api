package auth.utils.views;

public interface BaseView {

    interface BaseEntityListView {}

    interface BaseEntityDetailedView extends BaseEntityListView {}

    interface UserDetailedView extends BaseEntityDetailedView {}

    interface UserCreatedDetailedView extends BaseEntityDetailedView {}

    interface ProfileView {}

    interface UserProfileView {}

    interface RoleView {}

    interface privilegeView {}

    interface internalView {}
}
