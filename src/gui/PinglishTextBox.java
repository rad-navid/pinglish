package gui;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.widget.EditText;

public class PinglishTextBox extends EditText {

    public interface onSelectionChangedListener {
         
    	public void onSelectionChanged(int selStart, int selEnd);
        
    }
    
    public interface onKeyPressed {
        
    	public void keyPressed(int keyCode, KeyEvent event);
        
    }


	private List<onSelectionChangedListener> CursorListeners;
	private List<onKeyPressed> KeyListeners;
	
	
	public PinglishTextBox(Context context) {
	    super(context);
	}
	
	public PinglishTextBox(Context context, AttributeSet attrs){
	    super(context, attrs);
	}
	public PinglishTextBox(Context context, AttributeSet attrs, int defStyle){
	    super(context, attrs, defStyle);
	}
	
	public void addOnSelectionChangedListener(onSelectionChangedListener o){
		if(CursorListeners==null)
			CursorListeners=new ArrayList<PinglishTextBox.onSelectionChangedListener>(1);
	    CursorListeners.add(o);
	}
	
	protected void onSelectionChanged(int selStart, int selEnd){
		super.onSelectionChanged(selStart, selEnd);

		if(CursorListeners!=null)
			for (onSelectionChangedListener l : CursorListeners)
				l.onSelectionChanged(selStart, selEnd);        
	}
	public boolean onKeyDown(int keyCode,KeyEvent event)
	{
		super.onKeyDown(keyCode, event);
		try{
			if(KeyListeners!=null)
				for(onKeyPressed listener:KeyListeners)
					if(listener!=null)
						listener.keyPressed(keyCode, event);
		}
		catch(Exception e)
		{
			
		}
		return false;
	}
	
	public void setOnKeyPressedListener(onKeyPressed input)
	{
		if(KeyListeners==null)
			KeyListeners=new ArrayList<PinglishTextBox.onKeyPressed>();
		KeyListeners.add(input);
	}
}
