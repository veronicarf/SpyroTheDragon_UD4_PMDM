package dam.pmdm.spyrothedragon.ui;

import androidx.fragment.app.FragmentActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import dam.pmdm.spyrothedragon.R;



public class TutorialManager {
    private final FragmentActivity activity;
    private final NavController navController;

    public TutorialManager(FragmentActivity activity) {
        this.activity = activity;
        this.navController = Navigation.findNavController(activity, R.id.navHostFragment);
    }

    public boolean shouldShowTutorial() {
        // FORZAR QUE SIEMPRE SE MUESTRE LA GUÍA
        return true;
    }

    public void startTutorial() {
        if (shouldShowTutorial()) {
            navController.navigate(R.id.welcomeFragment);
        }
    }
}
