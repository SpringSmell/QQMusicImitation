package widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.ListView;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorListenerAdapter;
import com.nineoldandroids.animation.ValueAnimator;
import com.nineoldandroids.view.ViewHelper;
import com.nineoldandroids.view.ViewPropertyAnimator;
/**
 * @blog http://blog.csdn.net/xiaanming
 * 
 * @author xiaanming
 * 
 *
 */
public class SwipeDismissListView extends ListView implements OnScrollListener {
	/**
	 * ��Ϊ���û���������С����
	 */
	private int mSlop;
	/**
	 * ��������С�ٶ�
	 */
	private int mMinFlingVelocity;
	/**
	 * ����������ٶ�
	 */
	private int mMaxFlingVelocity;
	/**
	 * ִ�ж�����ʱ��
	 */
	protected long mAnimationTime = 150;
	/**
	 * ��������û��Ƿ����ڻ�����
	 */
	private boolean mSwiping;
	/**
	 * �����ٶȼ����
	 */
	private VelocityTracker mVelocityTracker;
	/**
	 * ��ָ���µ�position
	 */
	private int mDownPosition;
	/**
	 * ���µ�item��Ӧ��View
	 */
	private View mDownView;
	private float mDownX;
	private float mDownY;
	/**
	 * item�Ŀ��
	 */
	private int mViewWidth;
	/**
	 * ��ListView��Item��������ص��Ľӿ�
	 */
	private OnDismissCallback onDismissCallback;
	/**
	 * ��ǰ�б���ʾ�ĵ�һ��item
	 */
	private int firstVisibleItem=0;

	/**
	 * ���ö���ʱ��
	 * 
	 * @param mAnimationTime
	 */
	public void setmAnimationTime(long mAnimationTime) {
		this.mAnimationTime = mAnimationTime;
	}

	/**
	 * ����ɾ���ص��ӿ�
	 * 
	 * @param onDismissCallback
	 */
	public void setOnDismissCallback(OnDismissCallback onDismissCallback) {
		this.onDismissCallback = onDismissCallback;
	}

	public SwipeDismissListView(Context context) {
		this(context, null);
	}

	public SwipeDismissListView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public SwipeDismissListView(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);

		ViewConfiguration vc = ViewConfiguration.get(context);
		mSlop = vc.getScaledTouchSlop();
		mMinFlingVelocity = vc.getScaledMinimumFlingVelocity() * 8; //��ȡ��������С�ٶ�
		mMaxFlingVelocity = vc.getScaledMaximumFlingVelocity();  //��ȡ����������ٶ�
	}

	
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			handleActionDown(ev);
			break;
		case MotionEvent.ACTION_MOVE:
			return handleActionMove(ev);
		case MotionEvent.ACTION_UP:
			handleActionUp(ev);
			break;
		}
		return super.onTouchEvent(ev);
	}

	/**
	 * �����¼�����
	 * 
	 * @param ev
	 * @return
	 */
	private void handleActionDown(MotionEvent ev) {
		mDownX = ev.getX();
		mDownY = ev.getY();
		
		mDownPosition = pointToPosition((int) mDownX, (int) mDownY);
		//pointToPosition(),���ݵ�ǰ����������жϰ��µ����ĸ�item
		if (mDownPosition == AdapterView.INVALID_POSITION) {
			return;
		}
		//AdapterView.INVALID_POSITION��������Чλ��
		mDownView = getChildAt(mDownPosition - getFirstVisiblePosition());
		//getChildAt()������ָ������ͼ
		//getFirstVisiblePosition():�������ݼ���һ���λ�ã�һ�������0
		//
		if (mDownView != null) {
			mViewWidth = mDownView.getWidth();
		}

		//�����ٶȼ��
		mVelocityTracker = VelocityTracker.obtain();
		//VelocityTracker.obtain()����һ��VelocityTracker����
		mVelocityTracker.addMovement(ev);
	}
	

	/**
	 * ������ָ�����ķ���
	 * 
	 * @param ev
	 * @return
	 */
	private boolean handleActionMove(MotionEvent ev) {
		if (mVelocityTracker == null || mDownView == null) {
			return super.onTouchEvent(ev);
		}

		float deltaX = ev.getX() - mDownX;
		float deltaY = ev.getY() - mDownY;

		// X���򻬶��ľ������mSlop����Y���򻬶��ľ���С��mSlop����ʾ���Ի���
		if (Math.abs(deltaX) > mSlop && Math.abs(deltaY) < mSlop) {
			mSwiping = true;
			
			//����ָ����item,ȡ��item�ĵ���¼�����Ȼ���ǻ���ItemҲ������item����¼��ķ���
			MotionEvent cancelEvent = MotionEvent.obtain(ev);
            cancelEvent.setAction(MotionEvent.ACTION_CANCEL |
                       (ev.getActionIndex()<< MotionEvent.ACTION_POINTER_INDEX_SHIFT));
            onTouchEvent(cancelEvent);
		}

		if (mSwiping) {
			// ������ָ�ƶ�item
			ViewHelper.setTranslationX(mDownView, deltaX);
			// ͸���Ƚ���
			ViewHelper.setAlpha(mDownView, Math.max(0f, Math.min(1f, 1f - 2f * Math.abs(deltaX)/ mViewWidth)));
//			Log.i("SwipeDismissListView", deltaX+"");
			// ��ָ������ʱ��,����true����ʾSwipeDismissListView�Լ�����onTouchEvent,�����ľͽ�������������
			return true;
		}

		return super.onTouchEvent(ev);

	}

	/**
	 * ��ָ̧����¼�����
	 * @param ev
	 */
	private void handleActionUp(MotionEvent ev) {
		if (mVelocityTracker == null || mDownView == null|| !mSwiping) {
			return;
		}

		float deltaX = ev.getX() - mDownX;
		
		//ͨ�������ľ�������X,Y������ٶ�
		mVelocityTracker.computeCurrentVelocity(1000);
		float velocityX = Math.abs(mVelocityTracker.getXVelocity());
		float velocityY = Math.abs(mVelocityTracker.getYVelocity());
		
		boolean dismiss = false; //item�Ƿ�Ҫ������Ļ
		boolean dismissRight = false;//�Ƿ����ұ�ɾ��
		
		//���϶�item�ľ������item��һ�룬item������Ļ
		if (Math.abs(deltaX) > mViewWidth / 2) {
			dismiss = true;
			dismissRight = deltaX > 0;
			
			//��ָ����Ļ�������ٶ���ĳ����Χ�ڣ�Ҳʹ��item������Ļ
		} else if (mMinFlingVelocity <= velocityX
				&& velocityX <= mMaxFlingVelocity && velocityY < velocityX) {
			dismiss = true;
			dismissRight = mVelocityTracker.getXVelocity() > 0;
		}

		if (dismiss) {
			
//			AnimatorSet set = new AnimatorSet();
//			set.playTogether(ObjectAnimator.ofFloat(mDownView, "translationX", dismissRight ? mViewWidth : -mViewWidth), 
//							ObjectAnimator.ofFloat(mDownView, "alpha", 0));
//			set.setDuration(mAnimationTime).start();
//			set.addListener(new AnimatorListenerAdapter() {
//						@Override
//						public void onAnimationEnd(Animator animation) {
//							//Item��������֮��ִ��ɾ��
//							performDismiss(mDownView, mDownPosition);
//						}
//					});
			
			ViewPropertyAnimator.animate(mDownView)
					.translationX(dismissRight ? mViewWidth : -mViewWidth)//X�᷽����ƶ�����
					.alpha(0)
					.setDuration(mAnimationTime)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							//Item��������֮��ִ��ɾ��
							performDismiss(mDownView, mDownPosition);
						}
					});
		} else {
			//��item��������ʼλ��
			ViewPropertyAnimator.animate(mDownView)
			.translationX(0)
			.alpha(1)	
			.setDuration(mAnimationTime).setListener(null);
		}
		
		//�Ƴ��ٶȼ��
		if(mVelocityTracker != null){
			mVelocityTracker.recycle();
			mVelocityTracker = null;
		}
		
		mSwiping = false;
	}
	

	
	/**
	 * �ڴ˷�����ִ��itemɾ��֮��������item���ϻ������¹����Ķ��������ҽ�position�ص�������onDismiss()��
	 * @param dismissView
	 * @param dismissPosition
	 */
	private void performDismiss(final View dismissView, final int dismissPosition) {
		final ViewGroup.LayoutParams lp = dismissView.getLayoutParams();//��ȡitem�Ĳ��ֲ���
		final int originalHeight = dismissView.getHeight();//item�ĸ߶�

		ValueAnimator animator = ValueAnimator.ofInt(originalHeight, 0).setDuration(mAnimationTime);
		animator.start();

		
		animator.addListener(new AnimatorListenerAdapter() {
			@Override
			public void onAnimationEnd(Animator animation) {
				if (onDismissCallback != null) {
					onDismissCallback.onDismiss(dismissPosition);
				}

				//��δ������Ҫ����Ϊ���ǲ�û�н�item��ListView���Ƴ������ǽ�item�ĸ߶�����Ϊ0
				//���������ڶ���ִ�����֮��item���û���
				ViewHelper.setAlpha(dismissView, 1f);
				ViewHelper.setTranslationX(dismissView, 0);
				ViewGroup.LayoutParams lp = dismissView.getLayoutParams();
				lp.height = originalHeight;
				dismissView.setLayoutParams(lp);

			}
		});

		animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
			@Override
			public void onAnimationUpdate(ValueAnimator valueAnimator) {
				//��δ����Ч����ListViewɾ��ĳitem֮��������item���ϻ�����Ч��
				lp.height = (Integer) valueAnimator.getAnimatedValue();
				dismissView.setLayoutParams(lp);
			}
		});

	}

	/**
	 * ɾ���Ļص��ӿ�
	 * 
	 * @author xiaanming
	 * 
	 */
	public interface OnDismissCallback {
		public void onDismiss(int dismissPosition);
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		this.firstVisibleItem=firstVisibleItem;
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub
		
	}

}
