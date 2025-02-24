package dam.pmdm.spyrothedragon.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import java.util.Random;

public class Fire2AnimationView extends View {

    private Paint paintRed, paintOrange, paintYellow;
    private Path pathRed, pathOrange, pathYellow;
    private Random random = new Random();
    private Handler handler = new Handler();

    public Fire2AnimationView(Context context) {
        super(context);
        init();
    }

    public Fire2AnimationView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        paintRed = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintRed.setStyle(Paint.Style.FILL);
        paintRed.setColor(Color.RED);

        paintOrange = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintOrange.setStyle(Paint.Style.FILL);
        paintOrange.setColor(Color.parseColor("#FF4500"));

        paintYellow = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintYellow.setStyle(Paint.Style.FILL);
        paintYellow.setColor(Color.YELLOW);

        pathRed = new Path();
        pathOrange = new Path();
        pathYellow = new Path();

        startAnimation();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = getWidth();
        int height = getHeight();

        float centerX = width * 0.5f;
        float centerY = height * 0.7f;

        float offsetX = (random.nextFloat() - 0.5f) * 10;
        float offsetY = (random.nextFloat() - 0.5f) * 15;
        float sizeVariation = (random.nextFloat() * 0.2f) + 0.8f;

        float flameDirection = -1;

        pathRed.reset();
        pathRed.moveTo(centerX, centerY);
        pathRed.quadTo(centerX + 60 * sizeVariation + offsetX, centerY - 100 * sizeVariation + offsetY * flameDirection,
                centerX, centerY - 150 * sizeVariation * flameDirection);
        pathRed.quadTo(centerX - 60 * sizeVariation - offsetX, centerY - 100 * sizeVariation - offsetY * flameDirection,
                centerX, centerY);
        canvas.drawPath(pathRed, paintRed);

        pathOrange.reset();
        pathOrange.moveTo(centerX, centerY);
        pathOrange.quadTo(centerX + 40 * sizeVariation + offsetX, centerY - 80 * sizeVariation + offsetY * flameDirection,
                centerX, centerY - 130 * sizeVariation * flameDirection);
        pathOrange.quadTo(centerX - 40 * sizeVariation - offsetX, centerY - 80 * sizeVariation - offsetY * flameDirection,
                centerX, centerY);
        canvas.drawPath(pathOrange, paintOrange);

        pathYellow.reset();
        pathYellow.moveTo(centerX, centerY);
        pathYellow.quadTo(centerX + 20 * sizeVariation + offsetX, centerY - 50 * sizeVariation + offsetY * flameDirection,
                centerX, centerY - 90 * sizeVariation * flameDirection);
        pathYellow.quadTo(centerX - 20 * sizeVariation - offsetX, centerY - 50 * sizeVariation - offsetY * flameDirection,
                centerX, centerY);
        canvas.drawPath(pathYellow, paintYellow);
    }

    public void startAnimation() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                invalidate();
                handler.postDelayed(this, 100);
            }
        }, 100);
    }
}
