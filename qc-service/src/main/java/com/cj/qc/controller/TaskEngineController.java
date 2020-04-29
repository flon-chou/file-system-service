package com.cj.qc.controller;

import com.cj.qc.base.ApiDataResult;
import com.cj.qc.base.ApiResult;
import com.cj.qc.base.ResultCode;
import com.cj.qc.base.ResultUtil;
import com.cj.qc.service.TaskEngineService;
import com.cj.qc.tool.DateTool;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author chenzhaowen
 * @description 任务引擎Controller
 * @date 2019/4/18 14:40
 */
@Slf4j
@RestController
@RequestMapping("/taskEngine")
@Api(tags = "任务引擎API")
public class TaskEngineController {

  @Resource
  private TaskEngineService taskEngineService;

  @GetMapping("/test")
  @ApiOperation(value = "测试接口:自动质检任务队列")
  public ApiResult getOrderCount(){
    taskEngineService.generateAutoTaskForDay();
    return ResultUtil.getResponse(ResultCode.HTTP_CODE_SUCCESS);
  }

  @GetMapping("/taskQueueAutoByDay")
  @ApiOperation(value = "执行指定日期下的自动质检任务队列")
  @ApiImplicitParams({
          @ApiImplicitParam(name = "beforeDate", value = "指定日期字符串：format->yyyy-MM-dd",required = true,dataType = "String"),
  })
  public ApiResult getTaskQueueAutoByDay(@RequestParam String beforeDate){
    taskEngineService.executeAutoTaskForDay(beforeDate);
    return ResultUtil.getResponse(ResultCode.HTTP_CODE_SUCCESS);
  }
  /**
   * 生成每日自动质检任务
   */
//  @Scheduled(cron="0 */1 * * * ? ")   //每1分钟执行一次
  @Scheduled(cron="0 0 0 * * ? ")   //每日0点执行一次
  public void generateAutoTaskForDay(){
    log.info("-------------------生成每日自动质检任务,执行时间：{}--------------------------",
            DateTool.dateToStr(new Date(),"yyyy-MM-dd HH:mm:ss"));
    taskEngineService.generateAutoTaskForDay();
  }
}
