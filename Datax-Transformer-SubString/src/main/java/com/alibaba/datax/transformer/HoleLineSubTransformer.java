/**
 * 
 */
package com.alibaba.datax.transformer;

import java.util.Arrays;

import com.alibaba.datax.common.element.Column;
import com.alibaba.datax.common.element.Record;
import com.alibaba.datax.common.element.StringColumn;
import com.alibaba.datax.common.exception.DataXException;

/**
 * @author 古何俊
 * @version 创建时间：2020年3月20日  下午4:45:25
 */
public class HoleLineSubTransformer extends Transformer {
	String columnIndex;
	int startIndex;
	int length;
	public HoleLineSubTransformer() {
		setTransformerName("hole_substr");
	}

	@Override
	public Record evaluate(Record record, Object... paras) {

		try {
			if (paras.length != 3) {
				throw new RuntimeException("hole_substr paras must be 3");
			}
			columnIndex = (String) paras[0];
			startIndex = Integer.valueOf((String) paras[1]);
			length = Integer.valueOf((String) paras[2]);

		} catch (Exception e) {
			throw DataXException.asDataXException(TransformerErrorCode.TRANSFORMER_ILLEGAL_PARAMETER, "paras:" + Arrays.asList(paras).toString() + " => " + e.getMessage());
		}
		int recordNu = record.getColumnNumber();
		try {
			for(int i = 0; i < recordNu; i++) {
				Column column = record.getColumn(i);
				String oriValue = column.asString();
				// 如果字段为空，跳过subStr处理
				if (oriValue == null) {
					return record;
				}
				String newValue;
				if (startIndex > oriValue.length()) {
					throw new RuntimeException(String.format("hole_substr startIndex(%s) out of range(%s)", startIndex, oriValue.length()));
				}
				if (startIndex + length >= oriValue.length()) {
					newValue = oriValue.substring(startIndex, oriValue.length());
				} else {
					newValue = oriValue.substring(startIndex, startIndex + length);
				}

				record.setColumn(i, new StringColumn(newValue));

			} 
			}catch (Exception e) {
				throw DataXException.asDataXException(TransformerErrorCode.TRANSFORMER_RUN_EXCEPTION, e.getMessage(), e);
			}
			
		return record;
	}
}
