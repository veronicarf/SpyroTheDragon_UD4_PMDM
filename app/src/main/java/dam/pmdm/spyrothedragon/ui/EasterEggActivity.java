package dam.pmdm.spyrothedragon.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import dam.pmdm.spyrothedragon.MainActivity;
import dam.pmdm.spyrothedragon.R;

public class EasterEggActivity extends AppCompatActivity {

    private static final int TRIM_TIME_MS = 5000; // Tiempo a recortar al final (3 segundos)
    private VideoView videoView;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_easter_egg);

        videoView = findViewById(R.id.videoView);
        String videoPath = "android.resource://" + getPackageName() + "/" + R.raw.easter_egg_video;
        Uri uri = Uri.parse(videoPath);

        videoView.setVideoURI(uri);

        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);

        videoView.setOnPreparedListener(mp -> {
            int duration = videoView.getDuration(); // Obtener duración total del video
            int stopTime = duration - TRIM_TIME_MS; // Calcular cuándo detener el video

            // Programar la detención del video antes de que termine
            handler.postDelayed(() -> {
                if (videoView.isPlaying()) {
                    videoView.stopPlayback(); // Detener el video antes del final
                    navigateToCollectibles(); // Volver a la pestaña de coleccionables
                }
            }, stopTime);
        });

        videoView.start();
    }
    private void navigateToCollectibles() {
        Intent intent = new Intent(EasterEggActivity.this, MainActivity.class);
        intent.putExtra("navigateTo", R.id.nav_collectibles); // Usar el ID correcto del menú
        startActivity(intent);
        finish();
    }

}
