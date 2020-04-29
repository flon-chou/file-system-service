package com.cj.qc.model.bo;

import com.cj.qc.model.Model;
import com.cj.qc.model.Task;
import com.cj.qc.model.TaskVoiceRule;
import com.cj.qc.model.dto.RuleCombDTO;
import com.cj.qc.model.dto.TaskScopeCombDTO;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author chenzhaowen
 * @description 任务BO类
 * @date 2019/4/19 11:38
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@ApiModel(description = "任务BO类")
public class TaskBO {
  private Task task;
  private TaskScopeCombDTO taskScopeCombDTO;
  private TaskVoiceRule taskVoiceRule;
  private Model model;
  private List<RuleCombDTO> ruleCombDTOS;
}
