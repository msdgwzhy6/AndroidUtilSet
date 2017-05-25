package com.smart.androidutils.activity.spider.modle;

/**
 * @author xander on  2017/5/25.
 * @function 获取数据的业务接口，用于规范业务的逻辑行为和业务处理结果的分发
 */

public interface IHtmlModle {
    /**
     * @param htmlModuleResult 业务结果入口
     */
    void acquireData(IHtmlModuleResult htmlModuleResult);
}
