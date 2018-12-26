package com.huyingbao.module.wan.ui.article.model;

import java.util.ArrayList;

/**
 * Created by liujunfeng on 2018/12/24.
 */
public class Page<T> {
    /**
     * curPage : 2
     * datas : [{"apkLink":"","author":"鸿洋","chapterId":408,"chapterName":"鸿洋","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":7666,"link":"https://mp.weixin.qq.com/s/1G9J-Gyr5P_y6rymIm1Qxg","niceDate":"2018-12-17","origin":"","projectLink":"","publishTime":1544976000000,"superChapterId":408,"superChapterName":"公众号","tags":[{"name":"公众号","url":"/wxarticle/list/408/1"}],"title":"图文搞懂RecyclerView 的各部分组成 | RecyclerView进阶","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","author":"code小生","chapterId":414,"chapterName":"code小生","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":7667,"link":"https://mp.weixin.qq.com/s/zc5yAsCZYYz3-XbvLoRNmg","niceDate":"2018-12-17","origin":"","projectLink":"","publishTime":1544976000000,"superChapterId":408,"superChapterName":"公众号","tags":[{"name":"公众号","url":"/wxarticle/list/414/1"}],"title":"使用 TraceView 找到卡顿的元凶以及 StrictMode 优化代码逻辑","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","author":"郭霖","chapterId":409,"chapterName":"郭霖","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":7668,"link":"https://mp.weixin.qq.com/s/s4WgLFN0A-vO0ko0wi25mA","niceDate":"2018-12-17","origin":"","projectLink":"","publishTime":1544976000000,"superChapterId":408,"superChapterName":"公众号","tags":[{"name":"公众号","url":"/wxarticle/list/409/1"}],"title":"一起玩转Android项目中的字节码","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","author":"jeason12","chapterId":262,"chapterName":"SDK开发","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":7659,"link":"https://www.jianshu.com/c/c7515a06b524","niceDate":"2018-12-14","origin":"","projectLink":"","publishTime":1544787402000,"superChapterId":79,"superChapterName":"热门专题","tags":[],"title":"手游SDK  专题","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","author":"jeason12","chapterId":262,"chapterName":"SDK开发","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":7658,"link":"https://www.jianshu.com/p/44e844ad7308","niceDate":"2018-12-14","origin":"","projectLink":"","publishTime":1544787372000,"superChapterId":79,"superChapterName":"热门专题","tags":[],"title":"手游SDK &mdash; 第一篇（序言）","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","author":"tinycoder","chapterId":191,"chapterName":"数据采集与埋点","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":7656,"link":"https://juejin.im/post/5c0e4117518825369c566f07","niceDate":"2018-12-13","origin":"","projectLink":"","publishTime":1544631512000,"superChapterId":79,"superChapterName":"热门专题","tags":[],"title":"无埋点统计SDK实践","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","author":" liuzhibang","chapterId":375,"chapterName":"Flutter","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":7655,"link":"https://juejin.im/post/5bf2b829e51d4514df5b720d","niceDate":"2018-12-13","origin":"","projectLink":"","publishTime":1544631457000,"superChapterId":375,"superChapterName":"跨平台","tags":[],"title":"两个星期，用Flutter撸个APP","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","author":" coder-pig","chapterId":60,"chapterName":"Android Studio相关","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":7654,"link":"https://juejin.im/post/5c09f9daf265da61193ba4f2","niceDate":"2018-12-13","origin":"","projectLink":"","publishTime":1544631421000,"superChapterId":60,"superChapterName":"开发环境","tags":[],"title":"逮虾户！Android程序调试竟简单如斯","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","author":" minmin_1123","chapterId":73,"chapterName":"面试相关","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":7653,"link":"https://www.jianshu.com/p/9648e8dd5bdb","niceDate":"2018-12-13","origin":"","projectLink":"","publishTime":1544631334000,"superChapterId":79,"superChapterName":"热门专题","tags":[],"title":"2019校招Android面试题解1.0（算法篇）","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","author":"minmin_1123","chapterId":198,"chapterName":"基础概念","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":7652,"link":"https://www.jianshu.com/p/c44d7a106302","niceDate":"2018-12-13","origin":"","projectLink":"","publishTime":1544631308000,"superChapterId":168,"superChapterName":"基础知识","tags":[],"title":"文章汇总|学习Android的一点一滴","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","author":"打酱油的日光灯","chapterId":169,"chapterName":"gradle","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":7651,"link":"https://www.jianshu.com/p/2ea36da54f72","niceDate":"2018-12-13","origin":"","projectLink":"","publishTime":1544631230000,"superChapterId":60,"superChapterName":"开发环境","tags":[],"title":"浅谈build.gradle写法，促使我们更高效开发","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","author":"Ruheng","chapterId":10,"chapterName":"Activity","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":7650,"link":"https://www.jianshu.com/p/8766babc40e0","niceDate":"2018-12-13","origin":"","projectLink":"","publishTime":1544631010000,"superChapterId":10,"superChapterName":"四大组件","tags":[],"title":"简析Window、Activity、DecorView以及ViewRoot之间的错综关系","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","author":"HuYounger","chapterId":60,"chapterName":"Android Studio相关","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":7649,"link":"https://www.jianshu.com/p/aaee2d27068e","niceDate":"2018-12-13","origin":"","projectLink":"","publishTime":1544630985000,"superChapterId":60,"superChapterName":"开发环境","tags":[],"title":"Android Monitor使用介绍","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","author":"鸿洋","chapterId":408,"chapterName":"鸿洋","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":7660,"link":"https://mp.weixin.qq.com/s/g1XIJ7vPl5yj1_thuV6b9Q","niceDate":"2018-12-13","origin":"","projectLink":"","publishTime":1544630400000,"superChapterId":408,"superChapterName":"公众号","tags":[{"name":"公众号","url":"/wxarticle/list/408/1"}],"title":"一篇文章搞懂Android组件化","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","author":"奇卓社","chapterId":416,"chapterName":"奇卓社","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":7661,"link":"https://mp.weixin.qq.com/s/Sdrr0Y7rxCCmh64HsY1rKg","niceDate":"2018-12-13","origin":"","projectLink":"","publishTime":1544630400000,"superChapterId":408,"superChapterName":"公众号","tags":[{"name":"公众号","url":"/wxarticle/list/416/1"}],"title":"基于以太坊发行智能合约","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","author":"鸿洋","chapterId":408,"chapterName":"鸿洋","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":7646,"link":"https://mp.weixin.qq.com/s/Zjxa_ghmO-NyiF2r2OuIpQ","niceDate":"2018-12-12","origin":"","projectLink":"","publishTime":1544544000000,"superChapterId":408,"superChapterName":"公众号","tags":[{"name":"公众号","url":"/wxarticle/list/408/1"}],"title":"Android 如何优雅地实现@人功能？&mdash;&mdash;仿微博、仿QQ、仿微信、零入侵、高扩展性","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","author":"郭霖","chapterId":409,"chapterName":"郭霖","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":7648,"link":"https://mp.weixin.qq.com/s/-q-BqCG6PdJVpdEs6HntmQ","niceDate":"2018-12-12","origin":"","projectLink":"","publishTime":1544544000000,"superChapterId":408,"superChapterName":"公众号","tags":[{"name":"公众号","url":"/wxarticle/list/409/1"}],"title":"Android混淆你该知道的事","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","author":"longforus","chapterId":363,"chapterName":"创意汇","collect":false,"courseId":13,"desc":"adb-idea插件的升级版本,保留原有功能的基础上,添加了一些额外的小功能","envelopePic":"http://www.wanandroid.com/resources/image/pc/default_project_img.jpg","fresh":false,"id":7643,"link":"http://www.wanandroid.com/blog/show/2451","niceDate":"2018-12-11","origin":"","projectLink":"https://github.com/longforus/adb-idea","publishTime":1544543218000,"superChapterId":294,"superChapterName":"开源项目主Tab","tags":[{"name":"项目","url":"/project/list/1?cid=363"}],"title":"让ADB使用更方便,adb-idea插件升级版发布","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","author":"littledavid-tech","chapterId":294,"chapterName":"完整项目","collect":false,"courseId":13,"desc":"这个算是对Android学习总结，MVP架构+好多轮子","envelopePic":"http://wanandroid.com/blogimgs/9be242c9-e53e-4a54-9f49-d69b04b463b9.png","fresh":false,"id":7641,"link":"http://www.wanandroid.com/blog/show/2449","niceDate":"2018-12-11","origin":"","projectLink":"https://github.com/littledavid-tech/WanAndroidApp","publishTime":1544499146000,"superChapterId":294,"superChapterName":"开源项目主Tab","tags":[{"name":"项目","url":"/project/list/1?cid=294"}],"title":"我的涂鸦之作WanAndroid第三方客户端","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","author":"code小生","chapterId":414,"chapterName":"code小生","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":7645,"link":"https://mp.weixin.qq.com/s/UljVdlGQuwooVEJz_rctjw","niceDate":"2018-12-11","origin":"","projectLink":"","publishTime":1544457600000,"superChapterId":408,"superChapterName":"公众号","tags":[{"name":"公众号","url":"/wxarticle/list/414/1"}],"title":"该用路由来管理你的界面跳转了","type":0,"userId":-1,"visible":1,"zan":0}]
     * offset : 20
     * over : false
     * pageCount : 292
     * size : 20
     * total : 5824
     */

    private int curPage;
    private int offset;
    private boolean over;
    private int pageCount;
    private int size;
    private int total;
    private ArrayList<T> datas;

    public int getCurPage() {
        return curPage;
    }

    public void setCurPage(int curPage) {
        this.curPage = curPage;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public boolean isOver() {
        return over;
    }

    public void setOver(boolean over) {
        this.over = over;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public ArrayList<T> getDatas() {
        return datas;
    }

    public void setDatas(ArrayList<T> datas) {
        this.datas = datas;
    }
}
