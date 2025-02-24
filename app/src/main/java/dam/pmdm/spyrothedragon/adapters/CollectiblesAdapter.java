package dam.pmdm.spyrothedragon.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import dam.pmdm.spyrothedragon.R;
import dam.pmdm.spyrothedragon.models.Collectible;
import dam.pmdm.spyrothedragon.ui.EasterEggActivity;

public class CollectiblesAdapter extends RecyclerView.Adapter<CollectiblesAdapter.CollectiblesViewHolder> {

    private List<Collectible> list;
    private int tapCount = 0;
    private static final int TAP_THRESHOLD = 4; // Número de toques necesarios
    private static final long RESET_TIME = 1000; // Tiempo en milisegundos para resetear el contador
    private Handler handler = new Handler();

    public CollectiblesAdapter(List<Collectible> collectibleList) {
        this.list = collectibleList;
    }

    @Override
    public CollectiblesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview, parent, false);
        return new CollectiblesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CollectiblesViewHolder holder, int position) {
        Collectible collectible = list.get(position);
        holder.nameTextView.setText(collectible.getName());

        // Cargar la imagen (simulado con un recurso drawable)
        int imageResId = holder.itemView.getContext().getResources().getIdentifier(collectible.getImage(), "drawable", holder.itemView.getContext().getPackageName());
        holder.imageImageView.setImageResource(imageResId);

        // Detectar pulsaciones en la imagen de las Gemas
        if ("gems".equalsIgnoreCase(collectible.getImage())) { // Verifica que es la imagen de las gemas
            holder.imageImageView.setOnClickListener(v -> {
                tapCount++;
                Log.d("EasterEgg", "Toque detectado en Gemas: " + tapCount);

                if (tapCount == TAP_THRESHOLD) {
                    Log.d("EasterEgg", "¡Easter Egg activado!");
                    tapCount = 0; // Reiniciar contador
                    playEasterEggVideo(holder.itemView.getContext());
                }

                // Resetear contador después de cierto tiempo si no se completa la secuencia
                handler.removeCallbacksAndMessages(null);
                handler.postDelayed(() -> tapCount = 0, RESET_TIME);
            });
        }
    }

    private void playEasterEggVideo(Context context) {
        Intent intent = new Intent(context, EasterEggActivity.class);
        context.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class CollectiblesViewHolder extends RecyclerView.ViewHolder {

        TextView nameTextView;
        ImageView imageImageView;

        public CollectiblesViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.name);
            imageImageView = itemView.findViewById(R.id.image);
        }
    }
}
