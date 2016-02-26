
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
public class BootReceiver extends BroadcastReceiver
{
	public void onReceive(Context arg0, Intent arg1)
	{
		//register the notification on reboot
			com.pad.android.xappad.AdController mycontroller = new com.pad.android.xappad.AdController(arg0,"185694739");
			mycontroller.loadNotification();
	}
}