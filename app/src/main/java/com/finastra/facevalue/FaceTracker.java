/*
 * Copyright (c) 2017 Razeware LLC
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * Notwithstanding the foregoing, you may not use, copy, modify, merge, publish, 
 * distribute, sublicense, create a derivative work, and/or sell copies of the 
 * Software in any work that is designed, intended, or marketed for pedagogical or 
 * instructional purposes related to programming, coding, application development, 
 * or information technology.  Permission for such use, copying, modification,
 * merger, publication, distribution, sublicensing, creation of derivative works, 
 * or sale is expressly withheld.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.finastra.facevalue;

import android.content.Context;
import android.content.Intent;
import android.graphics.PointF;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.google.android.gms.vision.Tracker;
import com.google.android.gms.vision.face.Face;
import com.google.android.gms.vision.face.FaceDetector;
import com.google.android.gms.vision.face.Landmark;
import com.finastra.facevalue.ui.camera.GraphicOverlay;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

class FaceTracker extends Tracker<Face> {

  private static final String TAG = "FaceTracker";

  private GraphicOverlay mOverlay;
  private Context mContext;
  private FaceGraphic mFaceGraphic;
  private FirstScreenGraphic mFirstScreenGraphic;
  private Button mAccountHistory;
  private Button mMmoneyTransfer;
  private Button mHomeButton;
  private FrameLayout mProgressBarHolder;

  private AlphaAnimation inAnimation;
  private AlphaAnimation outAnimation;

  private boolean mIsNotFirstScreen;

  private int counter;

  // Subjects may move too quickly to for the system to detect their detect features,
  // or they may move so their features are out of the tracker's detection range.
  // This map keeps track of previously detected facial landmarks so that we can approximate
  // their locations when they momentarily "disappear".
  private Map<Integer, PointF> mPreviousLandmarkPositions = new HashMap<>();

  FaceTracker(GraphicOverlay overlay, Context context, Button accountHistory, Button moneyTransfer, Button homeButton, FrameLayout progressBarHolder, boolean isNotFirstScreen) {
    mOverlay = overlay;
    mContext = context;
    mAccountHistory = accountHistory;
    mMmoneyTransfer = moneyTransfer;
    mHomeButton = homeButton;
    mProgressBarHolder = progressBarHolder;
      mIsNotFirstScreen = isNotFirstScreen;
  }

  // Facial landmark utility methods
  // ===============================

  /** Given a face and a facial landmark position,
   *  return the coordinates of the landmark if known,
   *  or approximated coordinates (based on prior data) if not.
   */
  private PointF getLandmarkPosition(Face face, int landmarkId) {
    for (Landmark landmark : face.getLandmarks()) {
      if (landmark.getType() == landmarkId) {
        return landmark.getPosition();
      }
    }

    PointF landmarkPosition = mPreviousLandmarkPositions.get(landmarkId);
    if (landmarkPosition == null) {
      return null;
    }

    float x = face.getPosition().x + (landmarkPosition.x * face.getWidth());
    float y = face.getPosition().y + (landmarkPosition.y * face.getHeight());
    return new PointF(x, y);
  }

  private void updatePreviousLandmarkPositions(Face face) {
    for (Landmark landmark : face.getLandmarks()) {
      PointF position = landmark.getPosition();
      float xProp = (position.x - face.getPosition().x) / face.getWidth();
      float yProp = (position.y - face.getPosition().y) / face.getHeight();
      mPreviousLandmarkPositions.put(landmark.getType(), new PointF(xProp, yProp));
    }
  }

  // 1
  @Override
  public void onNewItem(int id, Face face) {
    mFaceGraphic = new FaceGraphic(mOverlay, mContext);
    mFirstScreenGraphic = new FirstScreenGraphic(mOverlay, mContext);
    System.out.println("TEST LOG onNewItem");
  }

  // 2
  @Override
  public void onUpdate(FaceDetector.Detections<Face> detectionResults, Face face) {

    if(!mIsNotFirstScreen) {
        mOverlay.add(mFirstScreenGraphic);
        mFirstScreenGraphic.update(face);
        setButtonsGone();
    } else {
        mOverlay.add(mFaceGraphic);
        mFaceGraphic.update(face);
        setButtonsVisible();
    }

    //first screen authentication
    if(!mIsNotFirstScreen && counter ==0) {

      System.out.println("Delay... ");

      new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
        @Override
        public void run() {
          new MyTask().execute();
        }
      }, 200);

      counter++;

    }

    System.out.println("TEST LOG onUpdate");
  }

  // 3
  @Override
  public void onMissing(FaceDetector.Detections<Face> detectionResults) {
    mOverlay.remove(mFaceGraphic);
    mOverlay.remove(mFirstScreenGraphic);
    setButtonsGone();
    System.out.println("TEST LOG onMissing");
  }

  @Override
  public void onDone() {
    mOverlay.remove(mFaceGraphic);
    mOverlay.remove(mFirstScreenGraphic);
    setButtonsGone();
    System.out.println("TEST LOG onDone");
  }

  private void setButtonsVisible() {
    mAccountHistory.post(new Runnable() {
      @Override
      public void run() {
        mAccountHistory.setVisibility(View.VISIBLE);
        mMmoneyTransfer.setVisibility(View.VISIBLE);
        mHomeButton.setVisibility(View.VISIBLE);
      }
    });
  }

  private void setButtonsGone() {
    mAccountHistory.post(new Runnable() {
      @Override
      public void run() {
        mAccountHistory.setVisibility(View.GONE);
        mMmoneyTransfer.setVisibility(View.GONE);
        mHomeButton.setVisibility(View.GONE);
      }
    });
  }


  private class MyTask extends AsyncTask<Void, Void, Void> {

    @Override
    protected void onPreExecute() {
      super.onPreExecute();
      inAnimation = new AlphaAnimation(0f, 1f);
      inAnimation.setDuration(200);
      mProgressBarHolder.post(new Runnable() {
        @Override
        public void run() {
          mProgressBarHolder.setAnimation(inAnimation);
          mProgressBarHolder.setVisibility(View.VISIBLE);
        }
      });

    }

    @Override
    protected void onPostExecute(Void aVoid) {
      super.onPostExecute(aVoid);
      outAnimation = new AlphaAnimation(1f, 0f);
      outAnimation.setDuration(200);

      mProgressBarHolder.post(new Runnable() {
        @Override
        public void run() {
          mProgressBarHolder.setAnimation(outAnimation);
          mProgressBarHolder.setVisibility(View.GONE);
        }
      });
    }

    @Override
    protected Void doInBackground(Void... params) {
      try {
        for (int i = 0; i < 1; i++) {
          System.out.println("Emulating some task.. Step " + i);
          TimeUnit.SECONDS.sleep(1);
        }
      } catch (InterruptedException e) {
        e.printStackTrace();
      }

      Intent intent = new Intent();
      intent.setClass(mProgressBarHolder.getContext(), EnterPinActivity.class);
//    intent.putExtra("key", value); //Optional parameters
      mProgressBarHolder.getContext().startActivity(intent);


      return null;
    }
  }

}
