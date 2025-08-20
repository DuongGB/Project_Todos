/*
 * @ {#} TaskResponse.java   1.0     8/20/2025
 *
 * Copyright (c) 2025 IUH. All rights reserved.
 */

package vn.edu.iuh.fit.backend.dtos.response;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

/*
 * @description:
 * @author: Nguyen Tan Thai Duong
 * @date:   8/20/2025
 * @version:    1.0
 */
@Data
public class TaskResponse implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String title;
    private String description;
    private String status;
    private LocalDate deadline;
    private List<ChecklistResponse> checklist;
}

