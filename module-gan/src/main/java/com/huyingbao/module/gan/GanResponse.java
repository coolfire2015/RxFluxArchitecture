package com.huyingbao.module.gan;

import java.util.ArrayList;

/**
 * Created by liujunfeng on 2018/12/17.
 */
public class GanResponse<T> {

    /**
     * error : false
     * results : [{"_id":"5b196d0b421aa910ab3d6b3c","createdAt":"2018-06-08T01:36:11.740Z","desc":"2018-06-08","publishedAt":"2018-06-08T00:00:00.0Z","source":"web","type":"福利","url":"http://ww1.sinaimg.cn/large/0065oQSqly1fs34w0jx9jj30j60ootcn.jpg","used":true,"who":"lijinshanmx"},{"_id":"585096f2421aa93437406727","createdAt":"2016-12-14T08:48:50.506Z","desc":"12-14","publishedAt":"2016-12-14T11:39:22.686Z","source":"chrome","type":"福利","url":"http://ww2.sinaimg.cn/large/610dc034gw1faq15nnc0xj20u00u0wlq.jpg","used":true,"who":"代码家"},{"_id":"58569ab6421aa97237bca8c5","createdAt":"2016-12-18T22:18:30.807Z","desc":"12-18","publishedAt":"2016-12-19T11:57:16.232Z","source":"chrome","type":"福利","url":"http://ww4.sinaimg.cn/large/610dc034jw1favb116hm2j20u00u0gqi.jpg","used":true,"who":"daimajia"},{"_id":"598bb8f0421aa90ca3bb6c01","createdAt":"2017-08-10T09:37:52.684Z","desc":"8-10","publishedAt":"2017-08-11T14:05:53.749Z","source":"chrome","type":"福利","url":"https://ws1.sinaimg.cn/large/610dc034ly1fiednrydq8j20u011itfz.jpg","used":true,"who":"带马甲"},{"_id":"56cc6d1d421aa95caa707739","createdAt":"2015-07-07T02:15:30.906Z","desc":"7.7","publishedAt":"2015-07-07T03:57:26.482Z","type":"福利","url":"http://ww1.sinaimg.cn/large/7a8aed7bgw1ettzpowndgj216g0s4dkg.jpg","used":true,"who":"张涵宇"},{"_id":"59c1b3e0421aa9727ddd19a8","createdAt":"2017-09-20T08:18:40.702Z","desc":"9-20","publishedAt":"2017-09-20T13:17:38.709Z","source":"chrome","type":"福利","url":"https://ws1.sinaimg.cn/large/610dc034ly1fjppsiclufj20u011igo5.jpg","used":true,"who":"带马甲"},{"_id":"56cc6d29421aa95caa7081f5","createdAt":"2016-01-25T01:59:46.62Z","desc":"1.25","publishedAt":"2016-01-25T06:59:09.1Z","type":"福利","url":"http://ww1.sinaimg.cn/large/7a8aed7bjw1f0bifjrh39j20v018gwtj.jpg","used":true,"who":"张涵宇"},{"_id":"572aa3ea67765974fca830f4","createdAt":"2016-05-05T09:37:46.142Z","desc":"5.5","publishedAt":"2016-05-05T12:14:21.156Z","source":"chrome","type":"福利","url":"http://ww1.sinaimg.cn/large/7a8aed7bjw1f3k9dp8r9qj20dw0jljtd.jpg","used":true,"who":"张涵宇"},{"_id":"571c841e67765974f885bf73","createdAt":"2016-04-24T16:30:22.634Z","desc":"4.25","publishedAt":"2016-04-25T11:24:01.704Z","source":"chrome","type":"福利","url":"http://ww4.sinaimg.cn/large/7a8aed7bjw1f37vhovzlnj20f00evabt.jpg","used":true,"who":"张涵宇"},{"_id":"57e319fd421aa95bc338986a","createdAt":"2016-09-22T07:38:37.240Z","desc":"9-22","publishedAt":"2016-09-22T11:44:08.156Z","source":"chrome","type":"福利","url":"http://ww4.sinaimg.cn/large/610dc034jw1f820oxtdzzj20hs0hsdhl.jpg","used":true,"who":"代码家"},{"_id":"56cc6d23421aa95caa707c52","createdAt":"2015-08-07T01:21:06.112Z","desc":"8.7\u2014\u2014（1）","publishedAt":"2015-08-07T03:57:47.310Z","type":"福利","url":"http://ww2.sinaimg.cn/large/7a8aed7bgw1eutscfcqtcj20dw0i0q4l.jpg","used":true,"who":"张涵宇"},{"_id":"590bce25421aa90c7d49ad3c","createdAt":"2017-05-05T08:58:13.502Z","desc":"5-5","publishedAt":"2017-05-05T11:56:35.629Z","source":"chrome","type":"福利","url":"http://7xi8d6.com1.z0.glb.clouddn.com/2017-05-05-18251898_1013302395468665_8734429858911748096_n.jpg","used":true,"who":"daimajia"},{"_id":"599e2220421aa901b9dc462d","createdAt":"2017-08-24T08:47:28.949Z","desc":"8-24","publishedAt":"2017-08-24T12:43:10.124Z","source":"chrome","type":"福利","url":"https://ws1.sinaimg.cn/large/610dc034ly1fiuiw5givwj20u011h79a.jpg","used":true,"who":"daimajia"},{"_id":"56cc6d29421aa95caa7082aa","createdAt":"2016-02-05T13:10:47.694Z","desc":"2.6","publishedAt":"2016-02-15T03:49:24.352Z","type":"福利","url":"http://ww4.sinaimg.cn/large/7a8aed7bgw1f0orab74l4j20go0p0jw5.jpg","used":true,"who":"张涵宇"},{"_id":"577348a5421aa95e542023e8","createdAt":"2016-06-29T12:03:49.269Z","desc":"6.29","publishedAt":"2016-06-29T12:08:28.744Z","source":"chrome","type":"福利","url":"http://ww3.sinaimg.cn/large/610dc034jw1f5byokn81tj20dw0hiwfe.jpg","used":true,"who":"daimajia"},{"_id":"57facc74421aa95de3b8ab6b","createdAt":"2016-10-10T07:02:12.35Z","desc":"10-10","publishedAt":"2016-10-10T11:41:33.500Z","source":"chrome","type":"福利","url":"http://ww3.sinaimg.cn/large/610dc034jw1f8mssipb9sj20u011hgqk.jpg","used":true,"who":"daimajia"},{"_id":"56cc6d23421aa95caa707c26","createdAt":"2015-08-14T03:05:40.175Z","desc":"8.14","publishedAt":"2015-08-14T03:56:36.768Z","type":"福利","url":"http://ww4.sinaimg.cn/large/7a8aed7bgw1ev1yplngebj20hs0qogq0.jpg","used":true,"who":"张涵宇"},{"_id":"5bf22fd69d21223ddba8ca25","createdAt":"2018-11-19T03:36:54.950Z","desc":"2018-11-19","publishedAt":"2018-11-19T00:00:00.0Z","source":"web","type":"福利","url":"https://ws1.sinaimg.cn/large/0065oQSqgy1fxd7vcz86nj30qo0ybqc1.jpg","used":true,"who":"lijinshanmx"},{"_id":"592f5798421aa92c769a8bf1","createdAt":"2017-06-01T07:54:00.225Z","desc":"6-1","publishedAt":"2017-06-01T14:35:22.88Z","source":"chrome","type":"福利","url":"https://ws1.sinaimg.cn/large/610dc034ly1fg5dany6uzj20u011iq60.jpg","used":true,"who":"daimajia"},{"_id":"56cc6d26421aa95caa70804c","createdAt":"2015-12-14T03:19:20.363Z","desc":"12.15","publishedAt":"2015-12-22T04:05:25.155Z","type":"福利","url":"http://ww1.sinaimg.cn/large/7a8aed7bgw1eyz0pe9m1nj20ol0gwdka.jpg","used":true,"who":"张涵宇"}]
     */

    private boolean error;
    private ArrayList<T> results;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public ArrayList<T> getResults() {
        return results;
    }

    public void setResults(ArrayList<T> results) {
        this.results = results;
    }
}
