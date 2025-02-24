package dam.pmdm.spyrothedragon.ui;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import dam.pmdm.spyrothedragon.R;
import dam.pmdm.spyrothedragon.databinding.FragmentTutorialStep3Binding;

public class TutorialStep3Fragment extends Fragment {

    private FragmentTutorialStep3Binding binding;
    private MediaPlayer mediaPlayer;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentTutorialStep3Binding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        NavController navController = Navigation.findNavController(view);
        playSound(R.raw.next_step);

        binding.btnNextTutorial.setOnClickListener(v -> {
            navController.navigate(R.id.fragment_tutorial_step_4);
        });

        binding.btnSkipTutorial.setOnClickListener(v -> {
            navController.navigate(R.id.navigation_characters);
        });
    }

    private void playSound(int soundResId) {
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
        mediaPlayer = MediaPlayer.create(getContext(), soundResId);
        mediaPlayer.start();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
