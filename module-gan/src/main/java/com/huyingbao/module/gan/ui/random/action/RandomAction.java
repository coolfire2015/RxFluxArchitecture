package com.huyingbao.module.gan.ui.random.action;

/**
 * Created by liujunfeng on 2019/1/1.
 */
public interface RandomAction {
    String TO_SHOW_DATA = "toShowData";

    /**
     * 获取文章数据列表
     */
    String GET_DATA_LIST = "getDataList";

    /**
     * 获取文章数据列表
     *
     * @param category 类别
     * @param count    数目
     * @param page     页码
     * @return
     */
    void getDataList(String category, int count, int page);
}
