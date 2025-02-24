package dam.pmdm.spyrothedragon.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

public class CollectionAnimationView extends View {

    private Paint bocadilloPaint;
    private Paint textPaint;
    private Path bocadilloPath;
    private AlphaAnimation fadeIn, fadeOut;

    public CollectionAnimationView(Context context) {
        super(context);
        init();
    }

    public CollectionAnimationView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        // Pintura del bocadillo (morado semitransparente)
        bocadilloPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        bocadilloPaint.setStyle(Paint.Style.FILL);
        bocadilloPaint.setColor(Color.parseColor("#803A0080")); // Color morado semitransparente

        // Pintura del texto
        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(40);
        textPaint.setTextAlign(Paint.Align.CENTER);

        // Path para dibujar el bocadillo
        bocadilloPath = new Path();

        // Animación de aparición
        fadeIn = new AlphaAnimation(0.0f, 1.0f);
        fadeIn.setDuration(500); // Aparece en 0.5 segundos
        fadeIn.setFillAfter(true);

        // Animación de desvanecimiento
        fadeOut = new AlphaAnimation(1.0f, 0.0f);
        fadeOut.setDuration(1000); // Desvanece en 1 segundo
        fadeOut.setStartOffset(3000); // Se mantiene visible 3 segundos antes de desvanecerse
        fadeOut.setFillAfter(true);
        fadeOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                // Remover la vista una vez finaliza la animación
                ViewGroup parent = (ViewGroup) getParent();
                if (parent != null) {
                    parent.removeView(CollectionAnimationView.this);
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = getWidth();
        int height = getHeight();

        // **Ajuste para bajar el bocadillo y acercarlo al botón "Coleccionables"**
        float left = width * 0.50f;   // Ajustado más a la derecha
        float top = height * 0.68f;   // Más cerca del botón
        float right = width * 0.95f;  // Mantiene el ancho en la derecha
        float bottom = height * 0.88f; // Reducción para no ser muy alto

        // Dibujar el óvalo del bocadillo
        bocadilloPath.reset();
        bocadilloPath.addOval(left, top, right, bottom, Path.Direction.CW);

        // **Ajuste para la flecha**
        float arrowX = width * 0.85f; // Flecha alineada con "Coleccionables"
        float arrowY = bottom + 15;   // Flecha apuntando más cerca del botón
        bocadilloPath.moveTo(arrowX, arrowY);
        bocadilloPath.lineTo(arrowX - 30, bottom + 40);
        bocadilloPath.lineTo(arrowX + 30, bottom + 40);
        bocadilloPath.close();

        // Dibujar el bocadillo y la flecha
        canvas.drawPath(bocadilloPath, bocadilloPaint);

        // **Ajustar texto para que quede bien centrado**
        float textX = (left + right) / 2;
        float textY = (top + bottom) / 2 - 10;
        canvas.drawText("Aquí podrás ver", textX, textY, textPaint);
        canvas.drawText("todos los coleccionables", textX, textY + 40, textPaint);
        canvas.drawText("del juego de Spyro.", textX, textY + 80, textPaint);
    }

    public void startAnimation() {
        startAnimation(fadeIn);
        postDelayed(() -> startAnimation(fadeOut), 3000); // Desaparece después de 3 segundos
    }
}
