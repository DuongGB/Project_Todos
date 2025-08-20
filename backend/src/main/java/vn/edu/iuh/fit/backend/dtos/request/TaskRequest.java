/*
 * @ {#} TaskRequest.java   1.0     8/20/2025
 *
 * Copyright (c) 2025 IUH. All rights reserved.
 */

package vn.edu.iuh.fit.backend.dtos.request;

import lombok.Data;
import vn.edu.iuh.fit.backend.enums.TaskStatus;

import java.time.LocalDate;
import java.util.List;

/*
 * @description:
 * @author: Nguyen Tan Thai Duong
 * @date:   8/20/2025
 * @version:    1.0
 */
@Data
public class TaskRequest {
    private String title;
    private String description;
    private TaskStatus status;
    private LocalDate deadline;
    private List<ChecklistRequest> checklist;
}

