import android.animation.Animator;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.view.Display;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;

import java.util.concurrent.Callable;

/**
 * Singleton class for making provided
 * animations easier to incorporate.
 *
 * @author Johnathon Theriot
 * Created by johnathontheriot on 12/16/15.
 */
public class AnimationManager {
    private static AnimationManager instance = new AnimationManager();

    public enum InterpolatorType{DECELERATE, ACCELERATEDECELERATE, ACCELERATE, BOUNCE};

    /**
     * Empty default constructor
     */
    private AnimationManager(){

    }

    /**
     * Get the singelton instance of the animation manager
     *
     * @return the singeleton instance of the animation manager
     */
    public static AnimationManager getInstance(){
        if(instance == null){
            instance = new AnimationManager();
        }
        return instance;
    }

    /**
     * Hides the keyboard in the context
     *
     * @return boolean: whether or not the context was
     *         accessible.
     */
    public boolean HideKeyboard(Context context, View view) {
        if (context != null) {
            InputMethodManager inputMethodManager = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
            return true;
        }
        return false;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public int[] TranslateView(final View view, Context context, int xDistance, int yDistance, long duration, InterpolatorType type){
        final int finalLocation[] = new int[2];
        final int currentLocation[] = new int[2];

        view.getLocationOnScreen(currentLocation);
        finalLocation[0] = currentLocation[0] + xDistance;
        finalLocation[1] = currentLocation[1] + yDistance;

        TranslateAnimation animation = Translate(view, finalLocation, currentLocation, duration, type);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.clearAnimation();
                view.setTranslationX(finalLocation[0] - currentLocation[0]);
                view.setTranslationY(finalLocation[1] - currentLocation[1]);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        view.startAnimation(animation);

        return currentLocation;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public int[] TranslateView(final View view, Context context, final int[] finalLocation, long duration, InterpolatorType type){
        final int currentLocation[] = new int[2];

        view.getLocationOnScreen(currentLocation);

        TranslateAnimation animation = Translate(view, finalLocation, currentLocation, duration, type);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.clearAnimation();
                view.setTranslationX(finalLocation[0] - currentLocation[0]);
                view.setTranslationY(finalLocation[1] - currentLocation[1]);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        view.startAnimation(animation);

        return currentLocation;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public int[] TranslateView(final View view, Context context, int xDistance, int yDistance, long duration, InterpolatorType type, final Callable<Void> onEnd){
        final int finalLocation[] = new int[2];
        final int currentLocation[] = new int[2];

        view.getLocationOnScreen(currentLocation);
        finalLocation[0] = currentLocation[0] + xDistance;
        finalLocation[1] = currentLocation[1] + yDistance;

        TranslateAnimation animation = Translate(view, finalLocation, currentLocation, duration, type);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.clearAnimation();
                view.setTranslationX(finalLocation[0] - currentLocation[0]);
                view.setTranslationY(finalLocation[1] - currentLocation[1]);
                try {
                    onEnd.call();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        view.startAnimation(animation);

        return currentLocation;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public int[] TranslateView(final View view, Context context, final int[] finalLocation, long duration, InterpolatorType type, final Callable<Void> onEnd){
        final int currentLocation[] = new int[2];

        view.getLocationOnScreen(currentLocation);

        TranslateAnimation animation = Translate(view, finalLocation, currentLocation, duration, type);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.clearAnimation();
                view.setTranslationX(finalLocation[0] - currentLocation[0]);
                view.setTranslationY(finalLocation[1] - currentLocation[1]);
                try {
                    onEnd.call();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        view.startAnimation(animation);

        return currentLocation;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private TranslateAnimation Translate(final View view, final int[] finalLocation, final int[] currentLocation, long duration, InterpolatorType type){
        TranslateAnimation animation = new TranslateAnimation(0, finalLocation[0] - currentLocation[0], 0, finalLocation[1] - currentLocation[1]);
        animation.setDuration(duration);

        setInterpolator(type, animation);
        return animation;
    }

    private void setInterpolator(InterpolatorType type, Animation animation) {
        switch (type){
            case DECELERATE:
                animation.setInterpolator(new DecelerateInterpolator());
                break;

            case ACCELERATEDECELERATE:
                animation.setInterpolator(new AccelerateDecelerateInterpolator());
                break;

            case ACCELERATE:
                animation.setInterpolator(new AccelerateInterpolator());
                break;

            case BOUNCE:
                animation.setInterpolator(new BounceInterpolator());
                break;

        }
    }

    private void setInterpolator(InterpolatorType type, Animator animation) {
        switch (type){
            case DECELERATE:
                animation.setInterpolator(new DecelerateInterpolator());
                break;

            case ACCELERATEDECELERATE:
                animation.setInterpolator(new AccelerateDecelerateInterpolator());
                break;

            case ACCELERATE:
                animation.setInterpolator(new AccelerateInterpolator());
                break;

            case BOUNCE:
                animation.setInterpolator(new BounceInterpolator());
                break;

        }
    }

    public int[] GetScreenSize(Context context){
        try {
            WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            Display display = windowManager.getDefaultDisplay();
            Point dimensions = new Point();
            display.getSize(dimensions);
            int[] rv = new int[2];
            rv[0] = dimensions.x;
            rv[1] = dimensions.y;
            return rv;
        }
        catch (Exception e){
            return null;
        }
    }

    public int[] GetCenterScreen(Context context){
        try {
            int[] dimensions = GetScreenSize(context);
            int[] center = new int[2];
            center[0] = dimensions[0] / 2;
            center[1] = dimensions[1] / 2;
            return center;
        }
        catch(Exception e){
            return null;
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void CircularReveal(View view, InterpolatorType type, long duration){
        Animator animator = ViewAnimationUtils.createCircularReveal(view, view.getWidth() / 2, view.getHeight() / 2, 0, (float) Math.hypot(view.getWidth(), view.getHeight()));
        setInterpolator(type, animator);
        animator.setDuration(duration);
        view.setVisibility(View.VISIBLE);
        animator.start();

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void CircularHide(View view, InterpolatorType type, long duration){
        Animator animator = ViewAnimationUtils.createCircularReveal(view, view.getWidth() / 2, view.getHeight() / 2, (float) Math.hypot(view.getWidth(), view.getHeight()), 0);
        setInterpolator(type, animator);
        animator.setDuration(duration);
        animator.start();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public int[] SeekCircularExpand(View seeker, final View expander, Context context, final long duration, final InterpolatorType type){
        int[] targetLocation = new int[2];
        expander.getLocationOnScreen(targetLocation);
        targetLocation[0] += (expander.getWidth() / 2) - (seeker.getWidth() / 2);
        targetLocation[1] += (expander.getHeight() / 2) - (seeker.getHeight() / 2);
        int[] returnVal = TranslateView(seeker, context, targetLocation, duration, type, new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                //CircularReveal(expander, type, duration);
                return null;
            }
        });
        android.os.Handler handler = new android.os.Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                CircularReveal(expander, type, duration);
            }
        }, Math.round((26.0f / 30.0f) * duration) );//(26 / 30) * duration);
        return returnVal;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void ReturnCircularContract(View seeker, View expander, long duration, InterpolatorType type){


    }

}
