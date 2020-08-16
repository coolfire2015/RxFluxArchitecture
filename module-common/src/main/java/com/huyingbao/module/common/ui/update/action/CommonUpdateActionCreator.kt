//package com.huyingbao.module.common.ui.update.action
//
//import android.content.Context
//import com.huyingbao.core.arch.action.ActionCreator
//import com.huyingbao.core.arch.action.ActionManager
//import com.huyingbao.core.arch.dispatcher.Dispatcher
//import com.huyingbao.core.progress.DownloadApi
//import java.io.*
//import javax.inject.Inject
//
//class CommonUpdateActionCreator @Inject constructor(
//        dispatcher: Dispatcher,
//        actionManager: ActionManager
//) : ActionCreator(dispatcher, actionManager), DownloadAction {
//    @Inject
//    lateinit var downloadApi: DownloadApi
//
//    override fun downloadStart(
//            tag: String,
//            url: String,
//            local: String): Boolean {
//        val rxAction = newAction(tag)
////        TODO
////        if (hasAction(rxAction)) {
////            postError(rxAction, IllegalArgumentException("当前有下载任务，请等待..."))
////            return false
////        }
////        val httpObservable = downloadApi.download(tag, url)
////                .map<InputStream> { responseBody -> responseBody.byteStream() }
////                .observeOn(Schedulers.computation())
////                .doOnNext { inputStream -> saveFile(inputStream, File(local)) }
////                .flatMap { Observable.just(true) }
////        postHttpAction(rxAction, httpObservable)
//        return true
//    }
//}
//
///**
// * 保存文件
// */
//fun saveFile(inputStream: InputStream, file: File) {
//    if (file.exists()) {
//        file.delete()
//    }
//    try {
//        val fileOutputStream = FileOutputStream(file)
//        val bufferedInputStream = BufferedInputStream(inputStream)
//        val buffer = ByteArray(1024)
//        var len = 0
//        while ({ len = bufferedInputStream.read(buffer); len }() != -1) {
//            fileOutputStream.write(buffer, 0, len)
//        }
//        fileOutputStream.flush()
//        fileOutputStream.close()
//        bufferedInputStream.close()
//        inputStream.close()
//    } catch (e: IOException) {
//    }
//}
//
///**
// * 在外部缓存文件夹中创建文件夹[dirName]，
// *
// * 创建成功，返回自定义文件夹[dirName]，
// *
// * 创建失败，返回外部缓存文件夹[Context.getExternalCacheDir]，
// *
// * 如果[dirName]为空，直接返回[Context.getExternalCacheDir]。
// */
//fun getExternalCacheDir(
//        context: Context,
//        dirName: String? = null
//): File? {
//    dirName ?: return context.externalCacheDir
//    return context.externalCacheDir?.run {
//        val imageDir = File(context.externalCacheDir, "app")
//        if (!imageDir.mkdirs() && (!imageDir.exists() || !imageDir.isDirectory)) {
//            context.externalCacheDir
//        } else {
//            imageDir
//        }
//    }
//}