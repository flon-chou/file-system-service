package com.cj.qc.ruleengine.util;

import com.cj.qc.ruleengine.buffer.BorderLocationBuffer;
import com.cj.qc.ruleengine.buffer.ContainBuffer;
import com.cj.qc.ruleengine.buffer.LocationBuffer;
import com.cj.qc.ruleengine.config.BorderEnum;
import com.cj.qc.ruleengine.resolver.ContainResolver;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author chenzhaowen
 * @description 规则分析器：1.字符串是否包含某个条件
 * @date 2019/3/19 17:47
 */
public class RuleUtil {

  /**
   * <列表>返回满足一定范围内匹配时的位置信息，适用于如 w1 bf w2 ，w1 af w2 ，w1 ne w2
   * @param leftMatchStr w1
   * @param rightMatchStr w2
   * @param source 源字符串
   * @return
   */
  public static List<BorderLocationBuffer> borderEvaluate(String leftMatchStr, String rightMatchStr, String source, BorderEnum borderEnum, int range){
    List<BorderLocationBuffer> borderLocationBuffers=new ArrayList<>();
    //判断source中是否含有rightMatchStr
    ContainBuffer rightContainBuffer= ContainResolver.resolve(rightMatchStr, source);
    //判断source中是否含有leftMatchStr
    ContainBuffer leftContainBuffer= ContainResolver.resolve(leftMatchStr, source);
    if(!rightContainBuffer.isValid() || !leftContainBuffer.isValid()){
      return borderLocationBuffers;
    }

    //提取左面匹配结果结束位置（闭）与右边匹配结果开始位置（开）
    List<LocationBuffer> leftLocationBuffers= leftContainBuffer.getLocationBuffers();
    List<LocationBuffer> rightLocationBuffers= rightContainBuffer.getLocationBuffers();

    //bf
    if(borderEnum==BorderEnum.BEFORE || borderEnum==BorderEnum.NEAR){
      List<Integer> lefts = leftLocationBuffers.stream().map(LocationBuffer::getEnd).collect(Collectors.toList());
      List<Integer> rights = rightLocationBuffers.stream().map(LocationBuffer::getStart).collect(Collectors.toList());

      for (int i = 0; i < rights.size(); i++) {
        for (int j = 0; j < lefts.size(); j++) {
          int tRange=rights.get(i)-lefts.get(j);
          if (0<=tRange && tRange<=range) {
            borderLocationBuffers.add(new BorderLocationBuffer(
                    leftLocationBuffers.get(j),
                    rightLocationBuffers.get(i),
                    new LocationBuffer(leftLocationBuffers.get(j).getStart(),rightLocationBuffers.get(i).getEnd())));
          }
        }
      }
    }

    //af
    if(borderEnum==BorderEnum.AFTER || borderEnum==BorderEnum.NEAR){
      List<Integer> lefts = rightLocationBuffers.stream().map(LocationBuffer::getEnd).collect(Collectors.toList());
      List<Integer> rights = leftLocationBuffers.stream().map(LocationBuffer::getStart).collect(Collectors.toList());

      for (int i = 0; i < rights.size(); i++) {
        for (int j = 0; j < lefts.size(); j++) {
          int tRange=rights.get(i)-lefts.get(j);
          if (0<=tRange && tRange<=range) {
            borderLocationBuffers.add(new BorderLocationBuffer(
                    leftLocationBuffers.get(i),
                    rightLocationBuffers.get(j),
                    new LocationBuffer(rightLocationBuffers.get(j).getStart(),leftLocationBuffers.get(i).getEnd())));
          }
        }
      }
    }

    return borderLocationBuffers;
  }

  /**
   * 计算匹配内容位置
   * @param locations
   * @param matchContent
   * @param source
   * @param offset
   * @param count
   */
  public static List<LocationBuffer> locationEvaluate (List<LocationBuffer> locations, String matchContent, String source, int offset, int count )
  {
    offset = source.indexOf (matchContent, offset);
    if (offset != -1)
    {
      locations.add(new LocationBuffer(offset,offset+matchContent.length()));
      locationEvaluate (locations,matchContent, source, ++offset, ++count);
    }
    return locations;
  }

  public static void main ( String[] args )
  {
    //todo:测试完成后删除
    //test:获取匹配内容位置信息
//    String source = "Ljava buddy, [][]{}{}【】【】**U got work hard and put 我 to 我 java, once U learned the heart of java, I can guarantee that U win//.";
//    String pattern = "【";
  }
}
