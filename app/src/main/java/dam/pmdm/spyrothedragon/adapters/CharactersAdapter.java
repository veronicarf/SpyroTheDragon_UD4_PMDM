
package dam.pmdm.spyrothedragon.adapters;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import dam.pmdm.spyrothedragon.R;
import dam.pmdm.spyrothedragon.models.Character;
import dam.pmdm.spyrothedragon.ui.Fire2AnimationView;

import java.util.List;

public class CharactersAdapter extends RecyclerView.Adapter<CharactersAdapter.CharactersViewHolder> {

    private List<Character> list;
    private Fire2AnimationView fireView; // Guardamos la referencia a la llama

    public CharactersAdapter(List<Character> charactersList) {
        this.list = charactersList;
    }

    @Override
    public CharactersViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview, parent, false);
        return new CharactersViewHolder(view);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(CharactersViewHolder holder, int position) {
        Character character = list.get(position);
        holder.nameTextView.setText(character.getName());

        // Cargar la imagen (simulado con un recurso drawable)
        int imageResId = holder.itemView.getContext().getResources().getIdentifier(character.getImage(), "drawable", holder.itemView.getContext().getPackageName());
        holder.imageImageView.setImageResource(imageResId);

        // Detectar pulsación larga en Spyro para lanzar la animación
        holder.itemView.setOnTouchListener((v, event) -> {
            if ("Spyro".equalsIgnoreCase(character.getName())) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) { // Usuario toca la pantalla
                    Log.d("EasterEgg", "Pulsación en Spyro detectada");
                    startFireAnimation(holder.itemView);
                } else if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL) { // Usuario suelta la pantalla
                    Log.d("EasterEgg", "Soltando a Spyro, eliminando fuego");
                    removeFireAnimation();
                }
                return true;
            }
            return false;
        });
    }

    private void startFireAnimation(View targetView) {
        // Obtener la vista raíz del fragmento para agregar la animación
        ViewGroup rootView = (ViewGroup) targetView.getRootView().findViewById(android.R.id.content);
        if (rootView == null) {
            Log.e("FireAnimation", "No se encontró el FrameLayout principal.");
            return;
        }

        // Eliminar la llama anterior si existe
        removeFireAnimation();

        fireView = new Fire2AnimationView(targetView.getContext());

        // Obtener la imagen del personaje (Spyro)
        ImageView imageView = targetView.findViewById(R.id.image);
        if (imageView == null) {
            Log.e("FireAnimation", "ImageView no encontrado en el itemView.");
            return;
        }

        // Obtener la ubicación exacta de Spyro en pantalla
        int[] location = new int[2];
        imageView.getLocationOnScreen(location); // Obtener coordenadas absolutas en la pantalla

        Log.d("SpyroPosition", "Imagen de Spyro - X: " + location[0] + ", Y: " + location[1]);

        // **Ajustar la llama para que salga desde la boca de Spyro**
        int fireX = (int) (location[0] - (imageView.getWidth() * 0.85)); // Ajustar posición horizontal
        int fireY = (int) (location[1] - (imageView.getHeight() * 3.60)); // Ajustar para que la llama suba más

        Log.d("FirePosition", "Llama en - X: " + fireX + ", Y: " + fireY);

        fireView.setX(fireX);
        fireView.setY(fireY);

        rootView.addView(fireView); // Se agrega al FrameLayout en la raíz de la vista
        fireView.startAnimation();
    }

    private void removeFireAnimation() {
        if (fireView != null && fireView.getParent() != null) {
            ((ViewGroup) fireView.getParent()).removeView(fireView);
            fireView = null;
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class CharactersViewHolder extends RecyclerView.ViewHolder {

        TextView nameTextView;
        ImageView imageImageView;

        public CharactersViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.name);
            imageImageView = itemView.findViewById(R.id.image);
        }
    }
}
