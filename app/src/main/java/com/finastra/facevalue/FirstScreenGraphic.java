package com.finastra.facevalue;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;

import com.finastra.facevalue.R;
import com.finastra.facevalue.ui.camera.GraphicOverlay;
import com.google.android.gms.vision.face.Face;

public class FirstScreenGraphic extends GraphicOverlay.Graphic {

    private static final float DOT_RADIUS = 3.0f;
    private static final float TEXT_OFFSET_Y = -30.0f;

    private volatile Face mFace;

    private Paint mHintTextPaint;
    private Paint mHintOutlinePaint;

    FirstScreenGraphic(GraphicOverlay overlay, Context context) {
        super(overlay);
        Resources resources = context.getResources();
        initializePaints(resources);
    }

    private void initializePaints(Resources resources) {
        mHintTextPaint = new Paint();
        mHintTextPaint.setColor(resources.getColor(R.color.overlayHint));
        mHintTextPaint.setTextSize(resources.getDimension(R.dimen.textSize));

        mHintOutlinePaint = new Paint();
        mHintOutlinePaint.setColor(resources.getColor(R.color.overlayHint));
        mHintOutlinePaint.setStyle(Paint.Style.STROKE);
        mHintOutlinePaint.setStrokeWidth(resources.getDimension(R.dimen.hintStroke));
    }

    // 1
    void update(Face face) {
        mFace = face;
        postInvalidate(); // Trigger a redraw of the graphic (i.e. cause draw() to be called).
    }

    @Override
    public void draw(Canvas canvas) {
        // 2
        // Confirm that the face and its features are still visible
        // before drawing any graphics over it.
        Face face = mFace;
        if (face == null) {
            return;
        }

        // 3
        float centerX = translateX(face.getPosition().x + face.getWidth() / 2.0f);
        float centerY = translateY(face.getPosition().y + face.getHeight() / 2.0f);
        float offsetX = scaleX(face.getWidth() / 2.0f);
        float offsetY = scaleY(face.getHeight() / 2.0f);

        // 4
        // Draw a box around the face.
        float left = centerX - offsetX;
        float right = centerX + offsetX;
        float top = centerY - offsetY;
        float bottom = centerY + offsetY;

        // 5
        canvas.drawRect(left, top, right, bottom, mHintOutlinePaint);

        // 6
        // Draw the face's id.
        canvas.drawText(String.format("id: %d", face.getId()), left, top, mHintTextPaint);


    }

}

