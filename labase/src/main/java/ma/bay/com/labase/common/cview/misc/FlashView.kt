package ma.bay.com.labase.common.cview.misc

import android.animation.ValueAnimator
import android.animation.ValueAnimator.INFINITE
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.ViewTreeObserver
import ma.bay.com.labase.R

class FlashView : View {
    private var mGradient: Shader? = null
    private var mGradientMatrix: Matrix? = null
    private lateinit var mPaint: Paint
    private var mCornerRadius = 0f
    private var mViewWidth = 0
    private var mViewHeight = 0
    private var mTranslateX = 0f
    private var mTranslateY = 0f
    private var mAnimating = false
    private lateinit var rectF: RectF
    private lateinit var valueAnimator: ValueAnimator
    private var autoRun = true //是否自动运行动画

    constructor(context: Context) : super(context) {
        init(context, null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context, attrs)
    }

    private fun init(context: Context, attrs: AttributeSet?) {
        val a = context.obtainStyledAttributes(attrs, R.styleable.FlashView)
        mCornerRadius = a.getDimensionPixelSize(R.styleable.FlashView_corner_radius, 0).toFloat();
        rectF = RectF()
        mPaint = Paint()
        initGradientAnimator()
        a.recycle()
    }

    fun setAutoRun(autoRun: Boolean) {
        this.autoRun = autoRun
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        rectF.set(0.toFloat(), 0.toFloat(), width.toFloat(), height.toFloat())
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        if (mViewWidth == 0) {
            mViewWidth = width
            mViewHeight = height
            if (mViewWidth > 0) {
                //亮光闪过
//                mGradient = LinearGradient(0f, 0f, (mViewWidth / 2).toFloat(), mViewHeight.toFloat(),
//                        intArrayOf(0x00ffffff, 0x73ffffff, 0x00ffffff, -0x66000001, 0x00ffffff),
//                        floatArrayOf(0.2f, 0.35f, 0.5f, 0.7f, 1f),
//                        Shader.TileMode.CLAMP)
                mGradient = LinearGradient(0f, 0f, mViewWidth.toFloat(), mViewHeight.toFloat(),
                        intArrayOf(0x00ffffff, 0x73ffffff, 0x00ffffff),
                        floatArrayOf(0.2f, 0.35f, 0.5f),
                        Shader.TileMode.CLAMP)
                mPaint.shader = mGradient
                mPaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.LIGHTEN)
                mGradientMatrix = Matrix()
                mGradientMatrix?.setTranslate((-2 * mViewWidth).toFloat(), mViewHeight.toFloat())
                mGradient?.setLocalMatrix(mGradientMatrix)
                rectF.set(0.toFloat(), 0.toFloat(), w.toFloat(), h.toFloat())
            }
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (mAnimating && mGradientMatrix != null) {
            canvas.drawRoundRect(rectF, mCornerRadius, mCornerRadius, mPaint)
        }
    }

    private fun initGradientAnimator() {
        valueAnimator = ValueAnimator.ofFloat(0f, 1f)
        valueAnimator.duration = ANIMATION_TIME
        valueAnimator.addUpdateListener { animation ->
            val v = animation.animatedValue as Float
            //❶ 改变每次动画的平移x、y值，范围是[-2mViewWidth, 2mViewWidth]
            mTranslateX = 4f * mViewWidth.toFloat() * v - mViewWidth * 2
            mTranslateY = mViewHeight * v
            //❷ 平移matrix, 设置平移量
            mGradientMatrix?.setTranslate(mTranslateX, mTranslateY)
            //❸ 设置线性变化的matrix
            if (mGradient != null) {
                mGradient!!.setLocalMatrix(mGradientMatrix)
            }
            //❹ 重绘
            invalidate()
        }
        if (autoRun) {
            valueAnimator.repeatCount = INFINITE
            viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {

                override fun onGlobalLayout() {
                    viewTreeObserver.removeOnGlobalLayoutListener(this)
                    mAnimating = true
                    valueAnimator.start()
                }
            })
        }
    }

    //停止动画
    fun stopAnimation() {
        if (mAnimating) {
            mAnimating = false
            valueAnimator.cancel()
            invalidate()
        }
    }

    //开始动画
    fun startAnimation() {
        if (!mAnimating) {
            mAnimating = true
            valueAnimator.start()
        }
    }

    companion object {
        const val ANIMATION_TIME = 2000L
    }
}