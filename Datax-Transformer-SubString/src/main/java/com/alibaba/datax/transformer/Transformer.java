/**
 * 
 */
package com.alibaba.datax.transformer;

import com.alibaba.datax.common.element.Record;

/**
 * @author 古何俊
 * @version 创建时间：2020年3月20日  下午4:46:46
 */
public abstract class Transformer {
	//transformerName的唯一性在datax中检查，或者提交到插件中心检查。
    private String transformerName;


    public String getTransformerName() {
        return transformerName;
    }

    public void setTransformerName(String transformerName) {
        this.transformerName = transformerName;
    }

    /**
     * @param record 行记录，UDF进行record的处理后，更新相应的record
     * @param paras  transformer函数参数
     */
    abstract public Record evaluate(Record record, Object... paras);
}
