package customfonts;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

/**
 * Created by Miura on 10/29/2017.
 */

public class MyEditText extends android.support.v7.widget.AppCompatEditText {
    public MyEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public MyEditText(Context context, AttributeSet attrs){
        super(context, attrs);
        init();
    }

    public MyEditText(Context context){
        super(context);
        init();
    }

    private void init(){
        if(!isInEditMode()){
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/Lato-Light.ttf");
            setTypeface(tf);
        }
    }
}
