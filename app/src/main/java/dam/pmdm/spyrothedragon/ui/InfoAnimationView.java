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

public class InfoAnimationView extends View {

    private Paint bocadilloPaint;
    private Paint textPaint;
    private Path bocadilloPath;
    private AlphaAnimation fadeIn, fadeOut;

    public InfoAnimationView(Context context) {
        super(context);
        init();
    }

    public InfoAnimationView(Context context, AttributeSet attrs) {
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
        fadeIn.setDuration(500);
        fadeIn.setFillAfter(true);

        // Animación de desvanecimiento
        fadeOut = new AlphaAnimation(1.0f, 0.0f);
        fadeOut.setDuration(1000);
        fadeOut.setStartOffset(3000);
        fadeOut.setFillAfter(true);
        fadeOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                ViewGroup parent = (ViewGroup) getParent();
                if (parent != null) {
                    parent.removeView(InfoAnimationView.this);
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

        // **Ajustar posición del bocadillo más cerca del botón de info**
        float left = width * 0.60f;   // Mover más a la izquierda
        float top = height * 0.10f;   // Más cerca del icono de info
        float right = width * 0.95f;  // Mantiene el ancho
        float bottom = height * 0.21f; // Bocadillo más pequeño

        // Dibujar el óvalo del bocadillo
        bocadilloPath.reset();
        bocadilloPath.addOval(left, top, right, bottom, Path.Direction.CW);

        // **Ajustar la flecha arriba del bocadillo, apuntando hacia abajo**
        float arrowX = width * 0.85f; // Centrada con el icono de info
        float arrowY = top - 10;      // Arriba del bocadillo
        bocadilloPath.moveTo(arrowX, arrowY); // Punto superior de la flecha
        bocadilloPath.lineTo(arrowX - 20, top); // Esquina izquierda de la flecha
        bocadilloPath.lineTo(arrowX + 20, top); // Esquina derecha de la flecha
        bocadilloPath.close();

        // Dibujar el bocadillo y la flecha
        canvas.drawPath(bocadilloPath, bocadilloPaint);

        // **Centrar mejor el texto dentro del bocadillo**
        float textX = (left + right) / 2;
        float textY = (top + bottom) / 2 + 10;
        canvas.drawText("Información acerca", textX, textY - 15, textPaint);
        canvas.drawText("de la aplicación", textX, textY + 25, textPaint);
    }

    public void startAnimation() {
        startAnimation(fadeIn);
        postDelayed(() -> startAnimation(fadeOut), 3000);
    }
}
