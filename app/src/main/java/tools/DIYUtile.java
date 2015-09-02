package tools;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.Rect;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.text.SimpleDateFormat;
import java.util.Date;

import frame.MusicAppliction;

/**
 * @author chris
 * @since 2015-08-18
 */
public class DIYUtile {

	private final static String TAG = "DIYUtile";

	/**
	 * @scene 使用场景：当与其他父控件冲突不能正常计算大小时。example:ScrollView嵌套ListView
	 * @description 设置指定listView的高度
	 * @param listView
	 */
	public static void setListViewHeightBasedOnChildren(ListView listView) {
		// 获取ListView对应的Adapter
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null || listAdapter.getCount() <= 0) {
//			ViewGroup.LayoutParams params = listView.getLayoutParams();
//			params.height = 500;
			// listView.getDividerHeight()获取子项间分隔符占用的高度
			// params.height最后得到整个ListView完整显示需要的高度
//			listView.setLayoutParams(params);
			return;
		}
		int totalHeight = 0;
		for (int i = 0, len = listAdapter.getCount(); i < len; i++) {
			// listAdapter.getCount()返回数据项的数目
			View listItem = listAdapter.getView(i, null, listView);
			// 计算子项View 的宽高
			listItem.measure(0, 0);
			// 统计所有子项的总高度
			totalHeight += listItem.getMeasuredHeight();
		}
		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight
				+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		// listView.getDividerHeight()获取子项间分隔符占用的高度
		// params.height最后得到整个ListView完整显示需要的高度
		listView.setLayoutParams(params);
	}

//	public static void setGridViewHeightBaseOnChildren(GridView gridView){
//		ListAdapter gridAdapter=gridView.getAdapter();
//		if(null==gridAdapter){
//			return;
//		}
//		int totalHeight = 0;
//		int numColums=gridView.get
//		for(int i=0;i<gridAdapter.getCount();i++){
//			View gridItem=gridAdapter.getView(i,null,gridView);
//			gridItem.measure(0,0);
//			totalHeight+=gridItem.getMeasuredHeight();
//		}
//	}

	/**
	 * 时间戳转换 1277106667 => 2010-06-21 15:51:07
	 * 
	 * @param l
	 * @return
	 */
	public static String getStandard(long l) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String date = sdf.format(new Date(l * 1000));
		return date;
	}

	/**
	 * @describe 返回图片链接是否有效
	 * @param imgurl 图片链接
	 * @return
	 * */
	public static Boolean isImgUrlValid(String imgurl){
		Boolean flag=false;

		if (null != imgurl && !"".equalsIgnoreCase(imgurl) && (imgurl.endsWith(".png") || imgurl.endsWith(".PNG")
				||imgurl.endsWith(".jpg")||imgurl.endsWith(".JPG")
				||imgurl.endsWith(".gif")||imgurl.endsWith(".GIF")
				||imgurl.endsWith(".jpge")||imgurl.endsWith(".JPGE"))) {
			flag=true;
		}

		return flag;
	}

	/**
	 * 获取两点间距离,单位：px
	 * 
	 * @formula |AB| = sqrt((X1-X2)^2 + (Y1-Y2)^2)
	 * @param x1
	 *            第一个点
	 * @param x2
	 *            第二个点
	 * @return
	 */
	public static double getDistance(Point x1, Point x2) {
		float x1Point = x1.x;
		float y1Point = x1.y;
		float x2Point = x2.x;
		float y2Point = x2.y;
		float xDValue = Math.abs(x2Point - x1Point);
		float yDValue = Math.abs(y2Point - y1Point);
		double distance = Math.sqrt(xDValue * xDValue + yDValue * yDValue);
		return distance;
	}

	/**
	 * The distance between two points
	 * 
	 * @param x
	 *            |x1-x2|
	 * @param y
	 *            |y1-y2|
	 * @return
	 */
	public static double getDistance(float x, float y) {
		double distance = Math.sqrt(x * x + y * y);
		return distance;

	}

	/**
	 * get window manager
	 * 
	 * @param context
	 * @return 返回窗口管理信息类，通过其可获得设备信息，example:屏幕高宽
	 */
	public static Display getScreenDisplay(Context context) {
		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		Display mDisplay = wm.getDefaultDisplay();
		return mDisplay;
	}

	/**
	 * 获取屏幕宽度,单位：PX
	 * 
	 * @param context
	 * @return The screen width
	 */
	public static int getScreenWidth(Context context) {
		int width = getScreenDisplay(context).getWidth();
		return width;
	}

	/**
	 * @return 返回是否有网
	 */
	public static boolean isNetWorkAvailable() {
		ConnectivityManager connectivitymanager = (ConnectivityManager) MusicAppliction
				.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivitymanager == null) {
			return false;
		}
		NetworkInfo info = connectivitymanager.getActiveNetworkInfo();
		if (info == null || !info.isAvailable()) {
			return false;
		}
		return true;
	}

	/**
	 * 获取屏幕高度，单位：px
	 * 
	 * @param context
	 * @return The screen height
	 */
	public static int getScreenHeight(Context context) {
		int height = getScreenDisplay(context).getHeight();
		return height;
	}

	/**
	 * 获取屏幕密度（DPI）
	 * 
	 * @return 屏幕密度
	 */
	public static int getScreenDensityDPI() {
		DisplayMetrics dm = new DisplayMetrics();
		int density = dm.densityDpi;
		return density;
	}

	/**
	 * 获取状态栏高度,单位：PX
	 * 
	 * @param activity
	 * @return 状态栏高度
	 */
	public static int getStatusHeight(Activity activity) {
		int statusHeight = 0;
		Rect localRect = new Rect();
		activity.getWindow().getDecorView()
				.getWindowVisibleDisplayFrame(localRect);
		statusHeight = localRect.top;
		if (0 == statusHeight) {
			Class<?> localClass;
			try {
				localClass = Class.forName("com.android.internal.R$dimen");
				Object localObject = localClass.newInstance();
				int i5 = Integer.parseInt(localClass
						.getField("status_bar_height").get(localObject)
						.toString());
				statusHeight = activity.getResources()
						.getDimensionPixelSize(i5);
			} catch (Exception e) {
//				log.e(TAG, e.toString(), null);
			}
		}
		return statusHeight;
	}

	/**
	 * 获取设备最大内存,单位为字节(B)
	 * 
	 * @return
	 */
	public static int getMaxMemory() {
		int maxMemory = (int) Runtime.getRuntime().maxMemory();
		return maxMemory;
	}

	/**
	 * 获取指定大小的位图
	 * 
	 * @source 
	 *         http://www.android-doc.com/training/displaying-bitmaps/load-bitmap
	 *         .html#read-bitmap
	 * @param res
	 * @param resId
	 * @param reqWidth
	 *            希望取得的宽度
	 * @param reqHeight
	 *            希望取得的高度
	 * @return 按指定大小压缩之后的图片
	 */
	public static Bitmap decodeSampledBitmapFromResource(Resources res,
			int resId, int reqWidth, int reqHeight) {

		// First decode with inJustDecodeBounds=true to check dimensions
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeResource(res, resId, options);

		// Calculate inSampleSize
		options.inSampleSize = calculateInSampleSize(options, reqWidth,
				reqHeight);

		// Decode bitmap with inSampleSize set
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeResource(res, resId, options);
	}

	/**
	 * 计算与指定位图的大小比例
	 * 
	 * @source 
	 *         http://www.android-doc.com/training/displaying-bitmaps/load-bitmap
	 *         .html #read-bitmap
	 * @param options
	 * @param reqWidth
	 * @param reqHeight
	 * @return 缩放的比例
	 */
	public static int calculateInSampleSize(BitmapFactory.Options options,
			int reqWidth, int reqHeight) {
		// Raw height and width of image
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {
			// 取高宽中更大一边，进行同比例缩放，这样才不会变形。
			if (width > height) {
				inSampleSize = Math.round((float) height / (float) reqHeight);
			} else {
				inSampleSize = Math.round((float) width / (float) reqWidth);
			}
		}
		return inSampleSize;
	}
}
