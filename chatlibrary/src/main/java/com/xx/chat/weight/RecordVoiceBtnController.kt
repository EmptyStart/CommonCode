package com.xx.chat.weight

import android.Manifest
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.os.Bundle
import android.os.Handler
import android.os.Process
import android.support.v4.app.ActivityCompat
import android.support.v7.widget.AppCompatButton
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.xx.chat.R
import com.xx.chat.apdater.ChatAdapter
import com.xx.common.utils.DateUtils
import com.xx.common.utils.FileUtils
import com.xx.common.utils.LogUtils
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.lang.ref.WeakReference
import java.util.*

/**
 *  按住语音
 *   录音按钮，之所以放到Controller包下，是因为涉及到文件读写、消息发送等事件
 * Created by Administrator on 2018/8/24/024.
 */
class RecordVoiceBtnController : AppCompatButton {
    var TAG = "RecordVoiceBtnController"
    private var myRecAudioFile: File? = null
    private var mChatAdapter: ChatAdapter? = null

    //依次为按下录音键坐标、手指离开屏幕坐标、手指移动坐标
    internal var mTouchY1: Float = 0.toFloat()
    internal var mTouchY2: Float = 0.toFloat()
    internal var mTouchY: Float = 0.toFloat()
    private val MIN_CANCEL_DISTANCE = 300f

    //依次为开始录音时刻，按下录音时刻，松开录音按钮时刻
    private var startTime: Long = 0
    private var time1: Long = 0
    private var time2: Long = 0

    private var recordIndicator: Dialog? = null
    private val topic: String? = null

    private var mVolumeIv: ImageView? = null
    private var mRecordHintTv: TextView? = null
    private var recorder: MediaRecorder? = null
    private var mThread: ObtainDecibelThread? = null

    private var mVolumeHandler: Handler? = null
    private var mContext: Context? = null
    private var timer: Timer = Timer()
    private var mCountTimer: Timer? = null
    private var isTimerCanceled = false
    private var mTimeUp = false
    private val HANDLER_WHAT_FOR_SEND_VOICE = 0x20
    private val myHandler = MyHandler(this)
    var isRecord = false                                                                            //false 录音结束  true 录音开始

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        this.mContext = context
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        this.mContext = context
        init()
    }

    private fun init() {
        mVolumeHandler = ShowVolumeHandler(this)
    }

    fun initConv(context: Context, adapter: ChatAdapter) {
        this.mChatAdapter = adapter
        this.mContext = context
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        this.isPressed = true
        val action = event.action
        when (action) {
            MotionEvent.ACTION_DOWN -> {
                Process.myPid()
                this.text = mContext?.getString(R.string.send_voice_hint)
                LogUtils.i(TAG,"ACTION_DOWN text=="+text)

                mIsPressed = true
                time1 = System.currentTimeMillis()
                mTouchY1 = event.y

                if (FileUtils.isSdCardExist()) {
                    if (isTimerCanceled) {
                        timer = createTimer()
                    }
                    //**************开始录音****************
                    timer!!.schedule(object : TimerTask() {
                        override fun run() {
                            val msg = myHandler.obtainMessage()
                            msg.what = START_RECORD
                            msg.sendToTarget()
                        }
                    }, 500)
                } else {
                    Toast.makeText(this.context, mContext!!.getString(R.string.sdcard_not_exist_toast), Toast.LENGTH_SHORT).show()
                    this.isPressed = false
                    this.text = mContext!!.getString(R.string.record_voice_hint)
                    mIsPressed = false
                    return false
                }
            }
            MotionEvent.ACTION_UP -> {
                this.text = mContext!!.getString(R.string.record_voice_hint)
                LogUtils.i(TAG,"ACTION_UP text=="+text)
                mTouchY2 = event.y
                stopRecord()
            }
            MotionEvent.ACTION_MOVE -> {
                mTouchY = event.y
                LogUtils.i(TAG,"ACTION_MOVE mTouchY=="+mTouchY)
                //手指上滑到超出限定后，显示松开取消发送提示
                if (mTouchY1 - mTouchY > MIN_CANCEL_DISTANCE) {
                    this.text = mContext!!.getString(R.string.cancel_record_voice_hint)
                    mVolumeHandler!!.sendEmptyMessage(CANCEL_RECORD)
                    if (mThread != null) {
                        mThread!!.exit()
                    }
                    mThread = null
                } else {
                    this.text = mContext!!.getString(R.string.send_voice_hint)
                    if (mThread == null) {
                        mThread = ObtainDecibelThread()
                        mThread?.start()
                    }
                }
            }
            MotionEvent.ACTION_CANCEL -> {  // 当手指移动到view外面，会cancel
                this.text = mContext!!.getString(R.string.record_voice_hint)
                LogUtils.i(TAG,"ACTION_CANCEL text=="+text)
                stopRecord()
                //cancelRecord()
            }
        }
        return true
    }

    fun stopRecord() : Boolean{
        if (!isRecord) return false

        mIsPressed = false
        this.isPressed = false

        time2 = System.currentTimeMillis()
        LogUtils.i(TAG,"录音的时间=="+ (time2 - time1).toString())
        if (time2 - time1 < 500) {
            LogUtils.i(TAG,"cancelTimer 0")
            cancelTimer()
        } else if (time2 - time1 < 1000) {
            LogUtils.i(TAG,"cancelRecord 1")
            cancelRecord()
        } else if (mTouchY1 - mTouchY2 > MIN_CANCEL_DISTANCE) {
            LogUtils.i(TAG,"cancelRecord 2")
            cancelRecord()
        } else if (time2 - time1 < 60000){
            LogUtils.i(TAG,"录音的结束")
            isRecord = false
            finishRecord()
        }
        return true
    }

    private fun cancelTimer() {
        if (timer != null) {
            timer?.cancel()
            timer?.purge()
            isTimerCanceled = true
        }
        if (mCountTimer != null) {
            mCountTimer?.cancel()
            mCountTimer?.purge()
        }
    }

    private fun createTimer(): Timer {
        timer = Timer()
        isTimerCanceled = false
        return timer
    }

    private fun initDialogAndStartRecord() {
        //存放录音文件目录
        var fileDir = FileUtils.getVoiceDirectory()
        var destDir = File(fileDir)
        if (!destDir.exists()) {
            destDir.mkdirs()
        }

        //录音文件的命名格式
        myRecAudioFile = File(fileDir, DateUtils.getDateYMDHMS_(System.currentTimeMillis())+".amr")
        if (myRecAudioFile == null) {
            cancelTimer()
            stopRecording()
            Toast.makeText(mContext, mContext!!.getString(R.string.create_file_failed), Toast.LENGTH_SHORT).show()
        }

        LogUtils.i(TAG, "myRecAudioFile== " + myRecAudioFile!!.absolutePath)
        recordIndicator = Dialog(context, R.style.record_voice_dialog)
        recordIndicator?.setContentView(R.layout.dialog_record_voice)
        mVolumeIv = recordIndicator?.findViewById(R.id.volume_hint_iv)
        mRecordHintTv = recordIndicator?.findViewById(R.id.record_voice_tv)
        mRecordHintTv!!.text = mContext!!.getString(R.string.move_to_cancel_hint)

        val hasPermission = PackageManager.PERMISSION_GRANTED ==  mContext!!.packageManager.checkPermission("android.permission.RECORD_AUDIO", context.packageName)
        val sdCardWritePermission = PackageManager.PERMISSION_GRANTED == mContext!!.packageManager.checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, context.packageName)
        if (hasPermission && sdCardWritePermission){
            isRecord = true
            startRecording()
            recordIndicator!!.show()
        }else{
            ActivityCompat.requestPermissions(mContext as Activity, arrayOf(Manifest.permission.RECORD_AUDIO,Manifest.permission.WRITE_EXTERNAL_STORAGE), 0)
        }
    }

    //录音完毕加载 ListView item
    fun finishRecord() {
        cancelTimer()
        stopRecording()
        if (recordIndicator != null) {
            recordIndicator?.dismiss()
        }

        val intervalTime = System.currentTimeMillis() - startTime
        if (intervalTime < MIN_INTERVAL_TIME) {
            Toast.makeText(context, mContext!!.getString(R.string.time_too_short_toast), Toast.LENGTH_SHORT).show()
            myRecAudioFile?.delete()
        } else {
            if (myRecAudioFile != null && myRecAudioFile?.exists()!!) {
                val mp = MediaPlayer()
                try {
                    val fis = FileInputStream(myRecAudioFile!!)
                    mp.setDataSource(fis.fd)
                    mp.prepare()
                } catch (e: IOException) {
                    e.printStackTrace()
                }

                //某些手机会限制录音，如果用户拒接使用录音，则需判断mp是否存在
                if (mp != null) {
                    var duration = mp.duration / 1000//即为时长 是s
                    if (duration < 1) {
                        duration = 1
                    } else if (duration > 60) {
                        duration = 60
                    }
                    try {
                        //发送语音
                        LogUtils.i(TAG,"录音完毕加载 发送语音  语音时常=="+duration)
                        if (mListener != null)
                            mListener?.onFinish(duration.toFloat(),myRecAudioFile!!.absolutePath)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }

                } else {
                    Toast.makeText(mContext, mContext!!.getString(R.string.record_voice_permission_request),
                            Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    //取消录音，清除计时
    private fun cancelRecord() {
        //可能在消息队列中还存在HandlerMessage，移除剩余消息
        mVolumeHandler!!.removeMessages(56, null)
        mVolumeHandler!!.removeMessages(57, null)
        mVolumeHandler!!.removeMessages(58, null)
        mVolumeHandler!!.removeMessages(59, null)
        mTimeUp = false
        cancelTimer()
        stopRecording()
        if (recordIndicator != null) {
            recordIndicator!!.dismiss()
        }
        if (myRecAudioFile != null) {
            myRecAudioFile!!.delete()
        }
    }

    private fun startRecording() {
        try {
            recorder = MediaRecorder()
            recorder!!.setAudioSource(MediaRecorder.AudioSource.MIC)
            recorder!!.setOutputFormat(MediaRecorder.OutputFormat.AMR_NB)
            recorder!!.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT)
            recorder!!.setOutputFile(myRecAudioFile!!.absolutePath)
            myRecAudioFile!!.createNewFile()
            recorder!!.prepare()
            recorder!!.setOnErrorListener { mediaRecorder, i, i2 -> Log.i("RecordVoiceController", "recorder prepare failed!") }
            recorder!!.start()
            startTime = System.currentTimeMillis()
            mCountTimer = Timer()
            mCountTimer!!.schedule(object : TimerTask() {
                override fun run() {
                    mTimeUp = true
                    val msg = mVolumeHandler!!.obtainMessage()
                    msg.what = 55
                    val bundle = Bundle()
                    bundle.putInt("restTime", 5)
                    msg.data = bundle
                    msg.sendToTarget()
                    mCountTimer!!.cancel()
                }
            }, 56000)

        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: RuntimeException) {
            Toast.makeText(mContext, R.string.record_voice_permission_denied, Toast.LENGTH_SHORT).show()
            cancelTimer()
            dismissDialog()
            if (mThread != null) {
                mThread!!.exit()
                mThread = null
            }
            if (myRecAudioFile != null) {
                myRecAudioFile!!.delete()
            }
            recorder!!.release()
            recorder = null
        }
        mThread = ObtainDecibelThread()
        mThread!!.start()

    }

    //停止录音，隐藏录音动画
    private fun stopRecording() {
        if (mThread != null) {
            mThread!!.exit()
            mThread = null
        }
        releaseRecorder()
    }

    fun releaseRecorder() {
        if (recorder != null) {
            try {
                recorder!!.stop()
            } catch (e: Exception) {
                Log.d("RecordVoice", "Catch exception: stop recorder failed!")
            } finally {
                recorder!!.release()
                recorder = null
            }
        }
    }

    private inner class ObtainDecibelThread : Thread() {
        @Volatile private var running = true
        fun exit() {
            running = false
        }

        override fun run() {
            while (running) {
                try {
                    Thread.sleep(200)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }

                if (recorder == null || !running) {
                    break
                }
                try {
                    val x = recorder!!.maxAmplitude
                    if (x != 0) {
                        val f = (10 * Math.log(x.toDouble()) / Math.log(10.0)).toInt()
                        if (f < 20) {
                            mVolumeHandler!!.sendEmptyMessage(0)
                        } else if (f < 26) {
                            mVolumeHandler!!.sendEmptyMessage(1)
                        } else if (f < 32) {
                            mVolumeHandler!!.sendEmptyMessage(2)
                        } else if (f < 38) {
                            mVolumeHandler!!.sendEmptyMessage(3)
                        } else {
                            mVolumeHandler!!.sendEmptyMessage(4)
                        }
                    }
                } catch (e: RuntimeException) {
                    e.printStackTrace()
                }
            }
        }
    }

    fun dismissDialog() {
        if (recordIndicator != null) {
            recordIndicator!!.dismiss()
        }
        this.text = mContext!!.getString(R.string.record_voice_hint)
    }

    /**
     * 录音动画控制
     */
    private class ShowVolumeHandler(controller: RecordVoiceBtnController) : Handler() {

        private val mController: WeakReference<RecordVoiceBtnController>

        init {
            mController = WeakReference(controller)
        }

        override fun handleMessage(msg: android.os.Message) {
            val controller = mController.get()
            if (controller != null) {
                val restTime = msg.data.getInt("restTime", -1)
                // 若restTime>0, 进入倒计时
                if (restTime > 0) {
                    controller.mTimeUp = true
                    val msg1 = controller.mVolumeHandler!!.obtainMessage()
                    msg1.what = 60 - restTime + 1
                    val bundle = Bundle()
                    bundle.putInt("restTime", restTime - 1)
                    msg1.data = bundle
                    //创建一个延迟一秒执行的HandlerMessage，用于倒计时
                    controller.mVolumeHandler!!.sendMessageDelayed(msg1, 1000)
                    controller.mRecordHintTv!!.text = String.format(controller.mContext!!.getString(R.string.rest_record_time_hint), restTime.toString() + "")
                    // 倒计时结束，发送语音, 重置状态
                } else if (restTime == 0) {
                    controller.finishRecord()
                    controller.isPressed = false
                    controller.mTimeUp = false
                    // restTime = -1, 一般情况
                } else {
                    // 没有进入倒计时状态
                    if (!controller.mTimeUp) {
                        if (msg.what < CANCEL_RECORD) {
                            controller.mRecordHintTv!!.text = controller.mContext!!
                                    .getString(R.string.move_to_cancel_hint)
                        } else {
                            controller.mRecordHintTv!!.text = controller.mContext!!
                                    .getString(R.string.cancel_record_voice_hint)
                        }
                        // 进入倒计时
                    } else {
                        if (msg.what == CANCEL_RECORD) {
                            controller.mRecordHintTv!!.text = controller.mContext!!.getString(R.string.cancel_record_voice_hint)
                            if (!mIsPressed) {
                                controller.cancelRecord()
                            }
                        }
                    }
                    controller.mVolumeIv!!.setImageResource(res[msg.what])
                }
            }
        }
    }

    private class MyHandler(controller: RecordVoiceBtnController) : Handler() {
        private val mController: WeakReference<RecordVoiceBtnController>

        init {
            mController = WeakReference(controller)
        }

        override fun handleMessage(msg: android.os.Message) {
            super.handleMessage(msg)
            val controller = mController.get()
            if (controller != null) {
                when (msg.what) {
                    SEND_CALLBACK ->{
                        var status = msg.data.getInt("status", -1)
                    }
                    START_RECORD -> {
                        if (mIsPressed) {
                            controller.initDialogAndStartRecord()
                        }
                    }
                }
            }
        }
    }

    companion object {
        private val MIN_INTERVAL_TIME = 1000// 1s
        private val CANCEL_RECORD = 5
        private val SEND_CALLBACK = 6
        private val START_RECORD = 7
        private val RECORD_DENIED_STATUS = 1000

        private val res = intArrayOf(R.mipmap.mic_1, R.mipmap.mic_2, R.mipmap.mic_3, R.mipmap.mic_4, R.mipmap.mic_5, R.mipmap.cancel_record)
        var mIsPressed = false
    }

    /**
     * 录音结束后的回调
     */
    interface AudioFinishRecorderListener {
        fun onFinish(seconds: Float, filePath: String)
    }
    internal var mListener: AudioFinishRecorderListener? = null
    fun setAudioFinishRecorderListener(listener: AudioFinishRecorderListener) {
        mListener = listener
    }
}
