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

public class FireAnimationView extends View {

    private Paint bocadilloPaint;
    private Paint textPaint;
    private Path bocadilloPath;
    private AlphaAnimation fadeIn, fadeOut;

    public FireAnimationView(Context context) {
        super(context);
        init();
    }

    public FireAnimationView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        // Pintura del bocadillo (morado semitransparente)
        bocadilloPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        bocadilloPaint.setStyle(Paint.Style.FILL);
        bocadilloPaint.setColor(Color.parseColor("#803A0080")); // Morado semitransparente

        // Pintura del texto
        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(38); // Tamaño del texto ajustado
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
                ViewGroup parent = (ViewGroup) getParent();
                if (parent != null) {
                    parent.removeView(FireAnimationView.this);
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

        // Posición ajustada con la misma separación del bocadillo de "Personajes"
        float left = width * 0.05f;
        float top = height * 0.68f;
        float right = width * 0.50f;
        float bottom = height * 0.83f;

        // Dibujar el óvalo del bocadillo
        bocadilloPath.reset();
        bocadilloPath.addOval(left, top, right, bottom, Path.Direction.CW);

        // Ajuste para la flecha alineada con "Personajes"
        float arrowX = width * 0.15f;
        float arrowY = bottom + 10;
        bocadilloPath.moveTo(arrowX, arrowY);
        bocadilloPath.lineTo(arrowX - 30, bottom + 40);
        bocadilloPath.lineTo(arrowX + 30, bottom + 40);
        bocadilloPath.close();

        // Dibujar el bocadillo y la flecha
        canvas.drawPath(bocadilloPath, bocadilloPaint);

        // Dibujar el texto centrado dentro del bocadillo
        float textX = (left + right) / 2;
        float textY = (top + bottom) / 2;
        canvas.drawText("Aquí podrás explorar", textX, textY - 20, textPaint);
        canvas.drawText("a todos los personajes", textX, textY + 20, textPaint);
        canvas.drawText("del mundo de Spyro.", textX, textY + 60, textPaint);
    }

    public void startAnimation() {
        startAnimation(fadeIn);
        postDelayed(() -> startAnimation(fadeOut), 3000); // Desaparece después de 3s
    }
}
